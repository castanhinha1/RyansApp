package com.parse.starter.ViewControllers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

import ConfigClasses.UserCustomList;
import Models.User;

public class TrainerViewController extends AppCompatActivity {
    TextView labelTV;
    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    UserCustomList adapter;
    int currentListView = 0;
    User currentUser;
    private List<User> userList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer_view);

        currentUser = (User) ParseUser.getCurrentUser();
        labelTV = (TextView) findViewById(R.id.clientsTextView);
        setLabels();
        new CurrentUserListLoader().execute();
    }

    public void setLabels(){
        if (currentUser.getTrainerStatus()){
            labelTV.setText(currentUser.getFirstName() + "'s "+"Clients");
        } else {
            labelTV.setText("Hello, "+currentUser.getFirstName()+"!");
        }
    }

    public void addNewClient(View view) {
        Intent intent = new Intent(getApplicationContext(), NewClientController.class);
        startActivity(intent);
    }

    private class CurrentUserListLoader extends AsyncTask<Void, Void, Void> {

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
            userList = new ArrayList<User>();
            ParseQuery<User> query = ParseQuery.getQuery(User.class);
            query.whereEqualTo("trainerstatus", false);
            query.whereNotEqualTo("objectId", currentUser.getObjectId());
            query.findInBackground(new FindCallback<User>() {
                @Override
                public void done(List<User> objects, ParseException e) {
                    if (objects != null) {
                        for (int i = 0; i < objects.size(); i++){
                            userList.add(objects.get(i));
                        }
                    }
                }
            });

            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            listview = (ListView) findViewById(R.id.ClientsListView);
            adapter = new UserCustomList(TrainerViewController.this, userList, currentListView);
            listview.setAdapter(adapter);
            mProgressDialog.dismiss();
        }
    }

}
