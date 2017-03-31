package com.parse.starter.ViewControllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.List;

import Models.User;

public class HomeController extends AppCompatActivity {

    TextView caloriesTV;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        currentUser = (User) ParseUser.getCurrentUser();
        caloriesTV = (TextView) findViewById(R.id.dailycalories);
        caloriesTV.setText(String.valueOf(currentUser.getCalories()));
    }
}
