package com.parse.starter.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

}
