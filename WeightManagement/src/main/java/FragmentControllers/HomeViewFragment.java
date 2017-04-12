package FragmentControllers;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.starter.R;

import Models.User;

/**
 * Created by Dylan Castanhinha on 4/12/2017.
 */

public class HomeViewFragment extends Fragment {

    ImageView profileImage;
    ImageButton editdetailsbutton;
    TextView nametv;
    TextView agesextv;
    TextView weighttv;
    TextView heighttv;
    TextView bodyfattv;
    TextView dailycaloriestv;
    TextView yourtrainertv;
    User currentUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        currentUser = (User) ParseUser.getCurrentUser();
        profileImage = (ImageView) rootView.findViewById(R.id.home_profile_picture);
        editdetailsbutton = (ImageButton) rootView.findViewById(R.id.home_edit_button);
        nametv = (TextView) rootView.findViewById(R.id.home_name_tv);
        agesextv = (TextView) rootView.findViewById(R.id.home_age_sex_tv);
        weighttv = (TextView) rootView.findViewById(R.id.home_weight_tv);
        heighttv = (TextView) rootView.findViewById(R.id.home_height_tv);
        bodyfattv = (TextView) rootView.findViewById(R.id.home_bodyfat_tv);
        dailycaloriestv = (TextView) rootView.findViewById(R.id.home_daily_calories_tv);
        yourtrainertv = (TextView) rootView.findViewById(R.id.home_your_trainer_tv);
        setUserData();
        return rootView;
    }

    public void setUserData(){
        profileImage.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
        nametv.setText("Dylan Castanhinha");
        agesextv.setText("Male, 22");
        weighttv.setText("180");
        heighttv.setText("5`9``");
        bodyfattv.setText("20%");
        dailycaloriestv.setText("2000 Calories");
        yourtrainertv.setText("Ryan Castanhinha");
    }
}
