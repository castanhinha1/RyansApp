package com.parse.starter.ViewControllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;

import ConfigClasses.CurrentClientsCustomList;

public class TrainerView extends AppCompatActivity {

    TextView labelTV;

    private ListView listView;
    private String names[] = {
            "Client 1",
            "Client 2",
            "Client 3",
            "Client 4"
    };

    private String desc[] = {
            "Location 1",
            "Location 2",
            "Location 3",
            "Location 4"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trainer_view);

        getUserType();
        labelTV = (TextView) findViewById(R.id.clientsTextView);
        CurrentClientsCustomList customList = new CurrentClientsCustomList(this, names, desc);

        listView = (ListView) findViewById(R.id.ClientsListView);
        listView.setAdapter(customList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"You Clicked "+names[i],Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        Intent intent = new Intent(getApplicationContext(), NewClient.class);
        startActivity(intent);

    }

}
