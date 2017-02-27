package com.parse.starter.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.starter.R;

public class HomeController extends AppCompatActivity {

    String calories;
    TextView caloriesTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        caloriesTV = (TextView) findViewById(R.id.dailycalories);

        calories = getIntent().getStringExtra("calories");
        Log.i("AppInfo", calories);
        caloriesTV.setText("You should be intaking "+calories +" calories per day.");
    }
}
