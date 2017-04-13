package FragmentControllers;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;

import Models.User;

/**
 * Created by Dylan Castanhinha on 4/13/2017.
 */

public class ChangeDetailsFragment extends DialogFragment {

    int position;
    TextView cancelButton;
    TextView label;
    TextView saveButton;
    User currentUser;
    RelativeLayout photoRR;
    RelativeLayout textViewRR;
    RelativeLayout spinnerRR;
    EditText changeText;
    DismissEditDialogListener activityCallback;

    public ChangeDetailsFragment(){
    }

    public static ChangeDetailsFragment newInstance(int position){
        ChangeDetailsFragment frag = new ChangeDetailsFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        frag.setArguments(args);
        return frag;
    }

    public interface DismissEditDialogListener{
        public void onEditDialogDismissal();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_change_details, container, false);
        position = getArguments().getInt("position");
        currentUser = (User) ParseUser.getCurrentUser();
        cancelButton = (TextView) rootView.findViewById(R.id.change_details_cancelButtton);
        label = (TextView) rootView.findViewById(R.id.change_details_label);
        saveButton = (TextView) rootView.findViewById(R.id.change_details_save_button);
        photoRR = (RelativeLayout) rootView.findViewById(R.id.change_details_photo_relative_layout);
        textViewRR = (RelativeLayout) rootView.findViewById(R.id.change_details_text_view_relative_layout);
        spinnerRR = (RelativeLayout) rootView.findViewById(R.id.change_details_spinner_relative_layout);
        photoRR.setVisibility(View.INVISIBLE);
        textViewRR.setVisibility(View.INVISIBLE);
        spinnerRR.setVisibility(View.INVISIBLE);
        changeText = (EditText) rootView.findViewById(R.id.change_details_edit_text);
        chooseCorrectRelativeLayout();
        cancelButton.setOnClickListener(new CancelClickListener());
        saveButton.setOnClickListener(new SaveButtonClickListener());
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activityCallback = (DismissEditDialogListener) context;
    }

    public void chooseCorrectRelativeLayout() {
        switch (position) {
            case 0: {
                photoRR.setVisibility(View.VISIBLE);
                break;
            }
            case 1: {
                textViewRR.setVisibility(View.VISIBLE);
                break;
            }
            case 2: {
                spinnerRR.setVisibility(View.VISIBLE);
                break;
            }
            case 3: {
                spinnerRR.setVisibility(View.VISIBLE);
                break;
            }
            case 4: {
                spinnerRR.setVisibility(View.VISIBLE);
                break;
            }
            case 5: {
                spinnerRR.setVisibility(View.VISIBLE);
                break;
            }
            case 6: {
                spinnerRR.setVisibility(View.VISIBLE);
                break;
            }
            case 7: {
                ///Relcaculate
                break;
            }
            case 8: {
                //Show trainer view
                break;
            }
        }
    }

    private class CancelClickListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            dismiss();
        }
    }
    private class SaveButtonClickListener implements TextView.OnClickListener{
        @Override
        public void onClick(View v) {
            currentUser.setFullName(String.valueOf(changeText.getText()));
            currentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        Log.i("AppInfo", "Saved");
                    } else {
                        Log.i("AppInfo", e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }
}
