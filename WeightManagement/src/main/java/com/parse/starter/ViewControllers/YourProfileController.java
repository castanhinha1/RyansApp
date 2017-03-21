package com.parse.starter.ViewControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;


import org.json.JSONException;
import org.json.JSONObject;

import ConfigClasses.ProfilePictureView;


public class YourProfileController extends AppCompatActivity {

    TextView nameTV;
    TextView locationTV;
    ProfilePictureView profilePictureView;
    CheckBox trainerCheckbox;
    boolean isTrainer;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_profile);

        profilePictureView = (ProfilePictureView) findViewById(R.id.profile_picture);
        profilePictureView.setProfileId(Profile.getCurrentProfile().getId());

        nameTV = (TextView) findViewById(R.id.nameTV);
        nameTV.setText(Profile.getCurrentProfile().getFirstName()+" "+Profile.getCurrentProfile().getLastName());

        locationTV = (TextView) findViewById(R.id.locationTV);

        trainerCheckbox = (CheckBox) findViewById(R.id.trainerCheckBox);

        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                try {
                    location = object.getJSONObject("location").getString("name");
                    locationTV.setText(location);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, location");
        request.setParameters(parameters);
        request.executeAsync();


        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (object == null) {
                    isTrainer = false;
                    trainerCheckbox.setChecked(false);
                } else {
                    if ((boolean) object.get("isTrainer")) {
                        isTrainer = true;
                        trainerCheckbox.setChecked(true);
                    } else {
                        isTrainer = false;
                        trainerCheckbox.setChecked(false);
                    }
                }
            }
        });



        //Listener for trainer check box and updates database
        trainerCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    final ParseUser currentUser = ParseUser.getCurrentUser();
                    currentUser.put("isTrainer", true);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.i("App Info", "Info Saved");
                            } else {
                                currentUser.saveEventually();
                            }
                        }
                    });

                } else {

                    final ParseUser currentUser = ParseUser.getCurrentUser();
                    currentUser.put("isTrainer", false);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Log.i("App Info", "Info Saved");
                            } else {
                                currentUser.saveEventually();
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
