package com.parse.starter.ViewControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

import ConfigClasses.MyProfilePictureView;
import Models.User;

public class ClientProfileController extends AppCompatActivity {

    String objectId;
    User displayedUser;
    User currentUser;
    TextView nameTV;
    TextView locationTV;
    Button addRemovebutton;
    MyProfilePictureView profilePictureView;
    private List<User> clientList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        currentUser = (User) ParseUser.getCurrentUser();
        clientList = new ArrayList<User>();
        clientList.addAll(currentUser.getClients());
        nameTV = (TextView) findViewById(R.id.client_profile_nameTV);
        locationTV = (TextView) findViewById(R.id.client_profile_locationTV);
        profilePictureView = (MyProfilePictureView) findViewById(R.id.client_profile_profile_picture);
        addRemovebutton = (Button) findViewById(R.id.client_profile_addRemove);
        Intent i = getIntent();
        objectId = i.getStringExtra("objectId");
        getUser();

        addRemovebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientList = new ArrayList<User>();
                clientList.add(displayedUser);
                currentUser.setClients(clientList);
                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null){
                            Log.i("AppInfo", "New client saved");
                        } else {
                            Log.i("AppInfo", e.getMessage());
                        }
                    }
                });
            }
        });
    }

    public void getUser(){
        ParseQuery<User> query = ParseQuery.getQuery(User.class);
        query.whereEqualTo("objectId", objectId);
        query.getFirstInBackground(new GetCallback<User>() {
            @Override
            public void done(User object, ParseException e) {
                if (object == null){
                    Log.i("AppInfo", "No user with that objectid");
                } else {
                    displayedUser = object;
                    setView();
                    try {
                        displayedUser.fetchIfNeeded();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void setView(){
        for(int i = 0; i < clientList.size(); i++){
            if (clientList.get(i).getObjectId().equals(displayedUser.getObjectId())){
                addRemovebutton.setText("Remove Client");
            } else {
                addRemovebutton.setText("Add");
                Log.i("AppInfo", "Getting here: "+ displayedUser.getObjectId()+ ", second: "+clientList.get(i).getObjectId());
            }
        }
        nameTV.setText(displayedUser.getFullName());
        locationTV.setText(displayedUser.getLocation());
        profilePictureView.setImageBitmap(profilePictureView.getRoundedBitmap(displayedUser.getProfilePicture()));
    }
}