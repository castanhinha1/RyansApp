package com.parse.starter.ViewControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.facebook.Profile;
import com.parse.ParseUser;
import com.parse.starter.R;

import ConfigClasses.ProfilePictureView;


public class YourProfile extends AppCompatActivity {

    TextView nameTV;
    ProfilePictureView profilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_profile);

        profilePictureView = (ProfilePictureView) findViewById(R.id.profile_picture);
        profilePictureView.setProfileId(Profile.getCurrentProfile().getId());

        nameTV = (TextView) findViewById(R.id.nameTV);
        nameTV.setText(Profile.getCurrentProfile().getFirstName()+" "+Profile.getCurrentProfile().getLastName());


    }

    public void logoutButtonClicked(View view){

        ParseUser.getCurrentUser().logOut();
        Intent intent = new Intent(getApplicationContext(), LoginController.class);
        startActivity(intent);

    }

}
