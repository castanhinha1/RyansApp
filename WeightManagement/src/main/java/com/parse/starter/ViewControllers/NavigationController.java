package com.parse.starter.ViewControllers;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
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

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Log Out?")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ParseUser.getCurrentUser().logOut();
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
    public void onHomeButtonClick(View view) {
        //Log.i("Info", "Home Button Clicked");
        Intent intent = new Intent(getApplicationContext(), HomeController.class);
        startActivity(intent);
    }

    public void onProfileButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), YourProfileController.class);
        startActivity(intent);
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
        Intent intent = new Intent(getApplicationContext(), TrainerViewController.class);
        startActivity(intent);
    }

    public void onCalendarButtonClick(View view) {
        Log.i("Info", "Calendar Button Button Clicked");
    }

    public void onSettingsButtonClick(View view) {
        Log.i("Info", "Settings Button Clicked");
    }

}
