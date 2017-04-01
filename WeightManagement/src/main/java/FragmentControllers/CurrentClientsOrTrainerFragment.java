package FragmentControllers;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.ViewControllers.TrainerViewController;

import java.util.List;

import ConfigClasses.MyProfilePictureView;
import ConfigClasses.ParseAdapterCustomList;
import Models.User;

/**
 * Created by Dylan Castanhinha on 3/31/2017.
 */

public class CurrentClientsOrTrainerFragment extends Fragment {

    TextView labelTV;
    ListView listview;
    CurrentClients adapter;
    User currentUser;
    SwipeRefreshLayout swipeContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_current_clients, container, false);
        swipeContainer = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeContainer);
        currentUser = (User) ParseUser.getCurrentUser();
        listview = (ListView) rootView.findViewById(R.id.current_client_list_view);
        adapter = new CurrentClients(getActivity());
        labelTV = new TextView(getActivity());
        labelTV.setText(currentUser.getFirstName() + "'s "+"Clients");
        listview.addHeaderView(labelTV);
        listview.setAdapter(adapter);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.loadObjects();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_green_light);
        return rootView;
    }


    class CurrentClients extends ParseAdapterCustomList implements ParseQueryAdapter.OnQueryLoadListener {
        Context context;

        public CurrentClients(Context context) {
            super(context, new ParseQueryAdapter.QueryFactory<User>() {
                public ParseQuery<User> create() {
                    ParseQuery<User> query = ParseQuery.getQuery(User.class);
                    query.whereEqualTo("trainerstatus", false);
                    query.whereNotEqualTo("objectId", ParseUser.getCurrentUser().getObjectId());
                    return query;
                }

            });
            this.context = context;
            addOnQueryLoadListener(this);
        }

        @Override
        public void onLoading() {
            swipeContainer.setRefreshing(true);
        }

        @Override
        public void onLoaded(List objects, Exception e) {
            swipeContainer.setRefreshing(false);
        }


        @Override
        public View getItemView(final User user, View v, ViewGroup parent){
            if (v == null){
                v = View.inflate(getContext(), R.layout.list_layout_current_clients, null);
            }
            super.getItemView(user, v, parent);

            //Add the title view
            TextView nameTextView = (TextView) v.findViewById(R.id.current_client_text_view_name);
            nameTextView.setText(user.getFullName());

            //Add the objectid
            TextView objectId = (TextView) v.findViewById(R.id.current_client_object_id);
            objectId.setText(user.getLocation());

            //Add the image
            MyProfilePictureView imageView = (MyProfilePictureView) v.findViewById(R.id.imageView3);
            imageView.setImageBitmap(imageView.getRoundedBitmap(user.getProfilePicture()));

            //On click listener for selection
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Intent intent = new Intent(context, ClientProfileController.class);
                    //intent.putExtra("objectId", user.getObjectId());
                    //context.startActivity(intent);
                    ((TrainerViewController) getActivity()).onUserSelected(user.getObjectId());

                }
            });
            return v;
        }
    }
}

