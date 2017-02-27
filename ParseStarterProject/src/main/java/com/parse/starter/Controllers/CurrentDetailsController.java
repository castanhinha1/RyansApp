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

import com.parse.starter.R;

import static android.util.Log.d;

public class CurrentDetailsController extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioGroup radioGroup2;
    private RadioButton buttonVeryLight;
    private RadioButton buttonLight;
    private RadioButton buttonModerate;
    private RadioButton buttonHeavy;
    private RadioButton buttonVeryLHeavy;
    private RadioButton maleButton;
    private RadioButton femaleButton;
    EditText weightTF;
    EditText bodyFatTF;

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
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);

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
                        Log.i("AppInfo", String.valueOf(sexFactor));
                        break;
                    case R.id.female:
                        sexFactor = 0.9;
                        break;
                }
            }
        });

    }


    public void calculateBMR(View view){

        convertWeightToKG();
        convertBodyFatToDouble();
        calculateEquation192();
        calculateLeanFactorMultiplier();

        BMR = leanFactorMultiplier * equation192;
        calculateDailyCaloricExpenditures(BMR);
    }

    private void calculateDailyCaloricExpenditures(double bmr) {
        dailyCarloricExpenditure = bmr * dailyActivityMultplier;
        int caloriesInt = ((int) dailyCarloricExpenditure);
        String caloriesString = String.valueOf(caloriesInt);
        //Log.i("Calories", String.valueOf(calories));
        Intent intent = new Intent(getBaseContext(), HomeController.class);
        intent.putExtra("calories", caloriesString);
        startActivity(intent);
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
        Log.i("Lean Factor Info", String.valueOf(leanFactorMultiplier));

    }

    private void convertWeightToKG() {

        bodyWeightInLB = Integer.parseInt(weightTF.getText().toString());
        double weightLB = (double) bodyWeightInLB;
        bodyWeightInKG = (weightLB / 2.2);
        Log.i("Info", String.valueOf(bodyWeightInKG));

    }


}
