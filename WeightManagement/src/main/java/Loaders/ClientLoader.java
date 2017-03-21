package Loaders;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.ViewControllers.NewClient;

import java.util.ArrayList;
import java.util.List;

import ConfigClasses.NewClientCustomList;
import Models.Client;

/**
 * Created by Dylan Castanhinha on 3/20/2017.
 */

public class ClientLoader extends AsyncTaskLoader<List<Client>> {

    List<Client> list;
    NewClientCustomList clientAdapter;

    public ClientLoader(Context context, NewClientCustomList clientAdapter) {
        super(context);
        this.clientAdapter = clientAdapter;
    }

    @Override
    public List<Client> loadInBackground() {
        list = new ArrayList<Client>();
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> userObjects, ParseException error) {
                if (userObjects != null) {
                    for (int i = 0; i < userObjects.size(); i++) {
                        //list.add(new Client("emp1", "Brahma"));
                        list.add(new Client(userObjects.get(i).getObjectId(), userObjects.get(i).get("username").toString()));
                        Log.i("AppInfo", "Getting here");

                    }
                }
            }
        });
        return list;
    }


}
