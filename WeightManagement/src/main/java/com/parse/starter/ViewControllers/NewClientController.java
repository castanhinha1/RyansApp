package com.parse.starter.ViewControllers;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.parse.ParseUser;
import com.parse.starter.R;

import Models.User;

public class NewClientController extends Activity {

    User currentUser;
    ListView listview;
    NewClients adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        currentUser = (User) ParseUser.getCurrentUser();
        listview = (ListView) findViewById(R.id.newClientsListView);
        adapter = new NewClients(NewClientController.this);
        listview.setAdapter(adapter);
    }

}
