package com.parse.starter.ViewControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.parse.ParseUser;
import com.parse.starter.R;

import FragmentControllers.CurrentClientsOrTrainerFragment;
import ListControllers.CurrentClients;
import Models.User;

public class TrainerViewController extends AppCompatActivity {
    TextView labelTV;
    ListView listview;
    CurrentClients adapter;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_view);

//        currentUser = (User) ParseUser.getCurrentUser();
//        labelTV = (TextView) findViewById(R.id.clientsTextView);
//        setLabels();
//        listview = (ListView) findViewById(R.id.ClientsListView);
//        adapter = new CurrentClients(TrainerViewController.this);
//        listview.setAdapter(adapter);

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


    public void setLabels() {
        if (currentUser.getTrainerStatus()) {
            labelTV.setText(currentUser.getFirstName() + "'s " + "Clients");
        } else {
            labelTV.setText("Hello, " + currentUser.getFirstName() + "!");
        }
    }

    public void addNewClient(View view) {
        Intent intent = new Intent(getApplicationContext(), NewClientController.class);
        startActivity(intent);
    }

}
