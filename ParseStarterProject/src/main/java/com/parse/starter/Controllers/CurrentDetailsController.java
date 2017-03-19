package com.parse.starter.Controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.List;

import static android.util.Log.d;

public class CurrentDetailsController extends AppCompatActivity {


    EditText weightTF;
    EditText bodyFatTF;
    RadioGroup radioGroup;
    RadioGroup radioGroup2;

    int bodyWeightInLB;
    double bodyWeightInKG;
    double leanFactorMultiplier;
    double equation192;
    double dailyActivityMultplier;
    double sexFactor;
    double bodyFat;
    double BMR;
    double dailyCarloricExpenditure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_details);

        weightTF = (EditText) findViewById(R.id.weightTextField);
        bodyFatTF = (EditText) findViewById(R.id.bodyFatTextField);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.buttonVeryLight:
                        dailyActivityMultplier = 1.30;
                        break;
                    case R.id.buttonLight:
                        dailyActivityMultplier = 1.55;
                        break;
                    case R.id.buttonModerate:
                        dailyActivityMultplier = 1.65;
                        break;
                    case R.id.buttonHeavy:
                        dailyActivityMultplier = 1.80;
                        break;
                    case R.id.buttonVeryHeavy:
                        dailyActivityMultplier = 2.00;
                        break;
                }
            }
        });

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male:
                        sexFactor = 1.0;
                        break;
                    case R.id.female:
                        sexFactor = 0.9;
                        break;
                }
            }
        });

    }


    public void calculateBMR(View view){

        if(weightTF != null && bodyFatTF != null && radioGroup.getCheckedRadioButtonId() != -1 && radioGroup2.getCheckedRadioButtonId() != -1) {
            convertWeightToKG();
            convertBodyFatToDouble();
            calculateEquation192();
            calculateLeanFactorMultiplier();
            BMR = leanFactorMultiplier * equation192;
            calculateDailyCaloricExpenditures(BMR);
        } else {
            Log.i("Info", "Need to make selection");
        }
    }

    private void calculateDailyCaloricExpenditures(double bmr) {
        dailyCarloricExpenditure = bmr * dailyActivityMultplier;
        int caloriesInt = ((int) dailyCarloricExpenditure);
        String caloriesString = String.valueOf(caloriesInt);
        saveToParse(caloriesInt);
    }

    private void saveToParse(final int calories){

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Calories");
        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null && objects.isEmpty() == false) {

                    ParseObject object = objects.get(0);
                    object.put("calories", calories);
                    object.saveInBackground();
                    Intent intent = new Intent(getBaseContext(), HomeController.class);
                    startActivity(intent);

                } else if (objects.isEmpty() && e == null){
                    ParseObject totalCalories = new ParseObject("Calories");
                    totalCalories.put("calories", calories);
                    totalCalories.put("user", ParseUser.getCurrentUser());
                    totalCalories.saveInBackground();
                    Intent intent = new Intent(getBaseContext(), HomeController.class);
                    startActivity(intent);
                }
                else {

                    Log.i("AppInfo", "Something went wrong");

                }
            }
        });

    }

    private void calculateLeanFactorMultiplier() {

        if(sexFactor != 0 && dailyActivityMultplier != 0) {

            if(sexFactor == 1.0) {

                if(bodyFat >=10 && bodyFat <=14){
                    leanFactorMultiplier = 1.0;
                } else if(bodyFat >=15 && bodyFat <=20) {
                    leanFactorMultiplier = .95;
                } else if(bodyFat >=21 && bodyFat <= 28) {
                    leanFactorMultiplier = .90;
                } else if(bodyFat >=28) {
                    leanFactorMultiplier = .85;
                }

            } else if(sexFactor == 0.9){
                if(bodyFat >=14 && bodyFat <=18){
                    leanFactorMultiplier = 1.0;
                } else if(bodyFat >=19 && bodyFat <=28) {
                    leanFactorMultiplier = .95;
                } else if(bodyFat >=29 && bodyFat <=38) {
                    leanFactorMultiplier = .90;
                } else if(bodyFat >=38){
                    leanFactorMultiplier = .85;
                }
            }

        } else {

            Log.i("Info", "Please enter details");

        }

    }

    private void convertBodyFatToDouble() {
        bodyFat = Integer.parseInt(bodyFatTF.getText().toString());

    }

    private void calculateEquation192() {

        equation192 = sexFactor * bodyWeightInKG * 24;

    }

    private void convertWeightToKG() {

        bodyWeightInLB = Integer.parseInt(weightTF.getText().toString());
        double weightLB = (double) bodyWeightInLB;
        bodyWeightInKG = (weightLB / 2.2);

    }


}
