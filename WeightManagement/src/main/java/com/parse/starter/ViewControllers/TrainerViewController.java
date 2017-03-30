package com.parse.starter.ViewControllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

import ConfigClasses.ClientCustomList;
import Models.Client;

public class TrainerViewController extends AppCompatActivity {

    TextView labelTV;

    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ClientCustomList adapter;
    ArrayList<String> clientsObjectIds;
    int currentListView = 0;
    private List<Client> clientList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_view);

        getUserType();
        labelTV = (TextView) findViewById(R.id.clientsTextView);
        new CurrentClientLoader().execute();
    }

    public void getUserType(){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if (object == null) {
                    labelTV.setText(Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName() + "'s" + " Trainer");
                } else {
                    if ((boolean) object.get("isTrainer")) {
                        labelTV.setText(Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName() + "'s" + " Clients");
                    } else {
                        labelTV.setText(Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName() + "'s" + " Trainer");
                    }
                }
            }
        });
    }

    public void addNewClient(View view) {
        Intent intent = new Intent(getApplicationContext(), NewClientController.class);
        startActivity(intent);
    }

    private class CurrentClientLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(TrainerViewController.this);
            mProgressDialog.setTitle("Getting your clients");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Find trainers clients
            clientsObjectIds = new ArrayList<>();
            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("ClientTrainerRelation");
            query1.whereEqualTo("trainer", ParseUser.getCurrentUser().getObjectId());
            query1.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects != null && e == null){
                        for (int i = 0; i < objects.size(); i++) {
                            String objectId = objects.get(i).getString("client");
                            clientsObjectIds.add(objectId);
                            Log.i("AppInfo", clientsObjectIds.toString());
                            Client client = ParseObject.createWithoutData(Client.class, objectId);
                            //clientList.add(client);
                        }
                    }
                }
            });

            //Get those clients
            clientList = new ArrayList<Client>();
            ParseQuery<ParseUser> query2 = ParseUser.getQuery();
            query2.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
            query2.whereEqualTo("isTrainer", false);
            query2.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> userObjects, ParseException error) {
                    if (userObjects != null) {
                        for (int i = 0; i < userObjects.size(); i++) {
                            Client client = new Client();
                            client.setObjectId(userObjects.get(i).getObjectId());
                            client.setName(userObjects.get(i).get("username").toString());
                            clientList.add(client);
                        }
                    }
                }
            });

            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            listview = (ListView) findViewById(R.id.ClientsListView);
            adapter = new ClientCustomList(TrainerViewController.this, clientList, currentListView);
            listview.setAdapter(adapter);
            mProgressDialog.dismiss();
        }
    }

}


////Get those clients
//clientList = new ArrayList<Client>();
//        ParseQuery<ParseUser> query2 = ParseUser.getQuery();
//        query2.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
//        query2.whereEqualTo("isTrainer", false);
//        query2.findInBackground(new FindCallback<ParseUser>() {
//@Override
//public void done(List<ParseUser> userObjects, ParseException error) {
//        if (userObjects != null) {
//        for (int i = 0; i < userObjects.size(); i++) {
//        Client client = new Client();
//        client.setObjectId(userObjects.get(i).getObjectId());
//        client.setName(userObjects.get(i).get("username").toString());
//        clientList.add(client);
//        }
//        }
//        }
//        });