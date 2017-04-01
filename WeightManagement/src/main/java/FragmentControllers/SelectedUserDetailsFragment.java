package FragmentControllers;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseUser;
import com.parse.starter.R;

import Models.User;

/**
 * Created by Dylan Castanhinha on 3/31/2017.
 */

public class SelectedUserDetailsFragment extends Fragment {

    User currentUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_selected_user_details, container, false);
        currentUser = (User) ParseUser.getCurrentUser();
        return rootView;
    }
}
