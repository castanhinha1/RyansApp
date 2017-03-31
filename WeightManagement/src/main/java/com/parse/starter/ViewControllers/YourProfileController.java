package com.parse.starter.ViewControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;

import ConfigClasses.MyProfilePictureView;
import Models.User;


public class YourProfileController extends AppCompatActivity {

    TextView nameTV;
    TextView locationTV;
    CheckBox trainerCheckbox;
    User currentUser;
    MyProfilePictureView profilepicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);

        currentUser = (User) ParseUser.getCurrentUser();
        profilepicture = (MyProfilePictureView) findViewById(R.id.profile_picture);
        profilepicture.setImageBitmap(profilepicture.getRoundedBitmap(currentUser.getProfilePicture()));
        nameTV = (TextView) findViewById(R.id.nameTV);
        nameTV.setText(currentUser.getFullName());
        locationTV = (TextView) findViewById(R.id.locationTV);
        locationTV.setText(currentUser.getLocation());

        trainerCheckbox = (CheckBox) findViewById(R.id.trainerCheckBox);
        if (currentUser.getTrainerStatus()){
            trainerCheckbox.setChecked(true);
        } else {
            trainerCheckbox.setChecked(false);
        }
        //Listener for trainer check box and updates database
        trainerCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    currentUser.put("trainerstatus", true);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.i("AppInfo", "Trainer status updated");
                            } else {
                                Log.i("AppInfo", e.getMessage());
                            }
                        }
                    });
                } else {
                    currentUser.put("trainerstatus", false);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.i("AppInfo", "Trainer status updated");
                            } else {
                                Log.i("AppInfo", e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }
    public void logoutButtonClicked(View view){
        ParseUser.getCurrentUser().logOut();
        Intent intent = new Intent(getApplicationContext(), LoginController.class);
        startActivity(intent);
    }
}
