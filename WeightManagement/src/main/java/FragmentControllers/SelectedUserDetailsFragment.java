package FragmentControllers;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.ViewControllers.TrainerViewController;

import java.util.List;

import ConfigClasses.MyProfilePictureView;
import Models.User;


public class SelectedUserDetailsFragment extends Fragment {

    String objectId;
    User displayedUser;
    User currentUser;
    TextView nameTV;
    TextView locationTV;
    Button addRemovebutton;
    MyProfilePictureView profilePictureView;
    boolean isClient;


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selected_user_details, container, false);
        objectId = getArguments().getString("userId");
        Log.i("AppInfo", "Getting created with: "+objectId);
        currentUser = (User) ParseUser.getCurrentUser();
        nameTV = (TextView) rootView.findViewById(R.id.client_profile_nameTV);
        locationTV = (TextView) rootView.findViewById(R.id.client_profile_locationTV);
        profilePictureView = (MyProfilePictureView) rootView.findViewById(R.id.client_profile_profile_picture);
        getUser(objectId);
        addRemovebutton = (Button) rootView.findViewById(R.id.client_profile_addRemove);
        addRemovebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClient) {
                    ParseRelation<User> relation = currentUser.getRelation("client");
                    relation.remove(displayedUser);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                addRemovebutton.setText("Add Client");
                                isClient = false;
                            } else {
                                Log.i("AppInfo", e.getMessage());
                            }
                        }
                    });
                } else {
                    ParseRelation<User> relation = currentUser.getRelation("client");
                    relation.add(displayedUser);
                    currentUser.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                addRemovebutton.setText("Delete Client");
                                isClient = true;
                            } else {
                                Log.i("AppInfo", e.getMessage());
                            }
                        }
                    });
                }
            }
        });

        return rootView;
    }

    public void getUser(String userId){
        ParseQuery<User> query = ParseQuery.getQuery(User.class);
        query.whereEqualTo("objectId", objectId);
        query.getFirstInBackground(new GetCallback<User>() {
            @Override
            public void done(User object, ParseException e) {
                if (object == null){
                } else {
                    displayedUser = object;
                    setView();
                    try {
                        displayedUser.fetchIfNeeded();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void setView(){
        //Determine if selected user is a current client or not
        ParseRelation<User> relation = currentUser.getRelation("client");
        ParseQuery<User> query = relation.getQuery();
        query.whereEqualTo("objectId", displayedUser.getObjectId());
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> objects, ParseException e) {
                if(objects.size() != 0){
                    isClient = true;
                    addRemovebutton.setText("Delete Client");
                } else {
                    isClient = false;
                    addRemovebutton.setText("Add Client");
                }
            }
        });
        nameTV.setText(displayedUser.getFullName());
        locationTV.setText(displayedUser.getLocation());
        profilePictureView.setImageBitmap(profilePictureView.getRoundedBitmap(displayedUser.getProfilePicture()));
    }
}
