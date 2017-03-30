package com.parse.starter.ViewControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;

public class ClientProfileController extends AppCompatActivity {

    String objectId;
    ParseObject currentClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile);

        Intent i = getIntent();
        objectId = i.getStringExtra("objectId");
        TextView nameTV = (TextView) findViewById(R.id.client_profile_nameTV);
        nameTV.setText(objectId);

//        ParseQuery<ParseUser> query = ParseQuery.getQuery("_User");
//        query.whereEqualTo("objectId", objectId);
//        query.getFirstInBackground(new GetCallback<ParseUser>() {
//            @Override
//            public void done(ParseUser user, ParseException e) {
//                if (user == null){
//                    Log.i("AppInfo", "No user with that objectid");
//                } else {
//                    Log.i("AppInfo", user.get("username").toString());
//                    currentClient = user;
//                }
//            }
//        });
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        query.whereEqualTo("objectId", objectId);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (object == null){
                    Log.i("AppInfo", "No user with that objectid");
                } else {
                    Log.i("AppInfo", object.get("username").toString());
                    currentClient = object;
                }
            }
        });

    }

    public void addClient(View view){
        Log.i("AppInfo", "Add client button clicked");
        ParseObject clientTrainerRelation = new ParseObject("ClientTrainerRelation");
        clientTrainerRelation.put("trainer", ParseUser.getCurrentUser().getObjectId());
        clientTrainerRelation.put("client", currentClient.getObjectId());
        clientTrainerRelation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Log.i("AppInfo", "Relation Saved");
                } else {
                    Log.i("AppInfo", e.getMessage());
                }
            }
        });

    }
}
