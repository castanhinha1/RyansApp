package com.parse.starter.ViewControllers;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.parse.starter.R;

import java.util.ArrayList;
import java.util.List;

import ConfigClasses.NewClientCustomList;
import Loaders.ClientLoader;
import Models.Client;

public class NewClient extends FragmentActivity implements LoaderManager.LoaderCallbacks<List<Client>> {

    NewClientCustomList clientAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_client);

        clientAdapter = new NewClientCustomList(this, new ArrayList<Client>());
        ListView listView = (ListView) findViewById(R.id.newClientsListView);
        listView.setAdapter(clientAdapter);
        getSupportLoaderManager().initLoader(1, null, this).forceLoad();
    }


    @Override
    public Loader<List<Client>> onCreateLoader(int id, Bundle args) {
        return new ClientLoader(NewClient.this, clientAdapter);
    }

    @Override
    public void onLoadFinished(Loader<List<Client>> loader, List<Client> data) {
        clientAdapter.setClients(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Client>> loader) {
        clientAdapter.setClients(new ArrayList<Client>());
    }

//    public void getUserType(){
//
//        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        query.whereEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
//        query.getFirstInBackground(new GetCallback<ParseUser>() {
//            @Override
//            public void done(ParseUser object, ParseException e) {
//                if (object == null) {
//                    labelTV.setText(Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName() + "'s" + " Trainer");
//                } else {
//                    if ((boolean) object.get("isTrainer")) {
//                        labelTV.setText(Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName() + "'s" + " Clients");
//                    } else {
//                        labelTV.setText(Profile.getCurrentProfile().getFirstName() + " " + Profile.getCurrentProfile().getLastName() + "'s" + " Trainer");
//                    }
//                }
//            }
//        });
//
//    }

}
