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

public class HomeController extends AppCompatActivity {

    String calories;
    TextView caloriesTV;
    int intCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Calories");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    intCalories = (int) objects.get(0).get("calories");
                    setTextView(intCalories);

                } else {

                    Log.i("AppInfo", "Something went wrong");

                }
            }
        });

    }

    public void setTextView(int calories){

        caloriesTV = (TextView) findViewById(R.id.dailycalories);
        caloriesTV.setText("You should be intaking "+calories +" calories per day.");

    }
}
