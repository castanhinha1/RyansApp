package com.parse.starter.ViewControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;

import java.util.List;

import ConfigClasses.BMRCalculator;
import Models.User;

import static android.util.Log.d;

public class CurrentDetailsController extends AppCompatActivity {

    EditText weightTF;
    EditText bodyFatTF;
    Button calculateButton;
    RadioGroup radioGroup;
    RadioGroup radioGroup2;
    User currentUser;
    double dailyActivityMultplier;
    double sexFactor;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkFieldsForEmptyValues();
        }
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_details);

        currentUser = (User) ParseUser.getCurrentUser();
        weightTF = (EditText) findViewById(R.id.weightTextField);
        bodyFatTF = (EditText) findViewById(R.id.bodyFatTextField);
        calculateButton = (Button) findViewById(R.id.calculateButton);
        calculateButton.setEnabled(false);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup2 = (RadioGroup) findViewById(R.id.radioGroup2);


        weightTF.addTextChangedListener(textWatcher);
        bodyFatTF.addTextChangedListener(textWatcher);
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
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double userWeightInLb = Double.parseDouble(weightTF.getText().toString());
                double userBodyFat = Double.parseDouble(bodyFatTF.getText().toString());
                double userSexFactor = sexFactor;
                double userDailyActvityMultiplier = dailyActivityMultplier;
                BMRCalculator theBMRCalculator = new BMRCalculator(userWeightInLb, userBodyFat, userSexFactor, userDailyActvityMultiplier);
                currentUser.setBodyFat(String.valueOf(bodyFatTF.getText()));
                currentUser.setWeight(String.valueOf(weightTF.getText()));
                currentUser.saveInBackground();
                Intent intent = new Intent(getBaseContext(), NavigationController.class);
                startActivity(intent);
            }
        });
    }

    private void checkFieldsForEmptyValues(){
        if(weightTF.equals("") && bodyFatTF.equals("")){
            calculateButton.setEnabled(false);
            Log.i("AppInfo", "Here 1");
        } else if(!(weightTF.equals("")) && (bodyFatTF.equals(""))){
            calculateButton.setEnabled(false);
            Log.i("AppInfo", "Here 2");
        } else if(!(bodyFatTF.equals("")) && (weightTF.equals(""))){
            calculateButton.setEnabled(false);
        } else{
            calculateButton.setEnabled(true);
            Log.i("AppInfo", "Here 3");
        }
    }
}
