package FragmentControllers;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseUser;
import com.parse.starter.R;

import ListControllers.CurrentClients;
import Models.User;

/**
 * Created by Dylan Castanhinha on 3/31/2017.
 */

public class CurrentClientsOrTrainerFragment extends Fragment {

    TextView labelTV;
    ListView listview;
    CurrentClients adapter;
    User currentUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_current_clients, container, false);
        currentUser = (User) ParseUser.getCurrentUser();
        labelTV = (TextView) rootView.findViewById(R.id.current_client_text_view_name);
        listview = (ListView) rootView.findViewById(R.id.current_client_list_view);
        adapter = new CurrentClients(getActivity());
        listview.setAdapter(adapter);
        return rootView;
    }
}

