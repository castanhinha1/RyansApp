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

import ConfigClasses.ClientCustomList;
import Models.Client;

public class NewClientController extends Activity {

    ListView listview;
    List<ParseObject> ob;
    ProgressDialog mProgressDialog;
    ClientCustomList adapter;
    private List<Client> clientList = null;
    private int currentListView = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        new ClientLoader().execute();
    }

    private class ClientLoader extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(NewClientController.this);
            mProgressDialog.setTitle("Finding new clients");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            clientList = new ArrayList<Client>();
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
            query.whereEqualTo("isTrainer", false);
            query.findInBackground(new FindCallback<ParseUser>() {
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
            listview = (ListView) findViewById(R.id.newClientsListView);
            adapter = new ClientCustomList(NewClientController.this, clientList, currentListView);
            listview.setAdapter(adapter);
            mProgressDialog.dismiss();
        }
    }

}
