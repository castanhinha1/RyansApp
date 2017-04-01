package com.parse.starter.ViewControllers;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


import com.parse.starter.R;

import FragmentControllers.CurrentClientsOrTrainerFragment;
import FragmentControllers.SelectedUserDetailsFragment;

public class TrainerViewController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_view);

        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            CurrentClientsOrTrainerFragment firstFragment = new CurrentClientsOrTrainerFragment();

            // Add the fragment to the 'fragment_container' FrameLayout
            getFragmentManager().beginTransaction().add(R.id.fragment_container, firstFragment).commit();
        }
    }
    public void onUserSelected(String objectId) {
        Log.i("AppInfo", "The User that was selected is: "+objectId);
        SelectedUserDetailsFragment secondFragment = new SelectedUserDetailsFragment();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, secondFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
