package com.parse.starter.ViewControllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

import ListControllers.NewClients;
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
