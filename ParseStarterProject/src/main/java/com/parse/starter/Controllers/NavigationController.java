package com.parse.starter.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.parse.ParseUser;
import com.parse.starter.R;

public class NavigationController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    public void logOut(View view) {

        ParseUser.getCurrentUser().logOut();
        Intent intent = new Intent(getApplicationContext(), LoginController.class);
        startActivity(intent);

    }

    public void onHomeButtonClick(View view) {
        //Log.i("Info", "Home Button Clicked");
        Intent intent = new Intent(getApplicationContext(), HomeController.class);
        startActivity(intent);
    }

    public void onProfileButtonClick(View view) {
        Log.i("Info", "Profile Button Clicked");
    }

    public void onCurrentDetailsButtonClick(View view) {
        Log.i("Info", "Current Details Button Clicked");
        Intent intent = new Intent(getApplicationContext(), CurrentDetailsController.class);
        startActivity(intent);
    }

    public void onNewGoalButtonClick(View view) {
        Log.i("Info", "New Goal Button Clicked");
    }

    public void onFindTrainerButtonClick(View view) {
        Log.i("Info", "Find Trainer Button Clicked");
    }

    public void onCalendarButtonClick(View view) {
        Log.i("Info", "Calendar Button Button Clicked");
    }

    public void onSettingsButtonClick(View view) {
        Log.i("Info", "Settings Button Clicked");
    }

}
