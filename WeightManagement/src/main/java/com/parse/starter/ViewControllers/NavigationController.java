package com.parse.starter.ViewControllers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import FragmentControllers.AddNewClientsOrTrainerFragment;
import FragmentControllers.CurrentClientsOrTrainerFragment;
import FragmentControllers.SelectedUserDetailsFragment;
import FragmentControllers.YourProfileFragment;
import Models.User;

public class NavigationController extends AppCompatActivity implements CurrentClientsOrTrainerFragment.OnAddNewUserButtonClicked, AddNewClientsOrTrainerFragment.OnUserSelected, SelectedUserDetailsFragment.DismissDialogListener {

    private Toolbar toolbar;
    Bundle savedInstanceState1;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        this.savedInstanceState1 = savedInstanceState;
        //Toolbar (Top)
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //Navigation Bar (Bottom)
        final BottomBar navigationBar = (BottomBar) findViewById(R.id.bottomBar);
        navigationBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.home) {
                    Log.i("AppInfo", "Home button pressed");
                } else if (tabId == R.id.goal) {
                    Log.i("AppInfo", "Goal button pressed");
                } else if (tabId == R.id.calendar) {
                    calendar(savedInstanceState);
                } else if (tabId == R.id.trainer) {
                    trainer(savedInstanceState);
                } else if (tabId == R.id.profile) {
                    profile(savedInstanceState);
                } else {
                    Log.i("AppInfo", "Home button pressed");
                }
            }
        });
        navigationBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                if (tabId == R.id.home) {
                    Log.i("AppInfo", "Home button pressed");
                } else if (tabId == R.id.goal) {
                    Log.i("AppInfo", "Goal button pressed");
                } else if (tabId == R.id.calendar) {
                    calendar(savedInstanceState);
                } else if (tabId == R.id.trainer) {
                    trainer(savedInstanceState);
                } else if (tabId == R.id.profile) {
                    profile(savedInstanceState);
                } else {
                    Log.i("AppInfo", "Home button pressed");
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Log Out?")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ParseUser.getCurrentUser().logOut();
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    public void calendar(Bundle savedInstanceState){
        Log.i("AppInfo", "Calendar Button Clicked");
    }

    @Override
    public void onAddUserClicked() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState1 != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            AddNewClientsOrTrainerFragment addNewClientsOrTrainerFragment = new AddNewClientsOrTrainerFragment();
            // Add the fragment to the 'fragment_container' FrameLayout
            fragmentTransaction
                    .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right, R.animator.slide_in_right, R.animator.slide_out_left)
                    .replace(R.id.fragment_container, addNewClientsOrTrainerFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onDialogDismissal() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState1 != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            CurrentClientsOrTrainerFragment firstFragment = new CurrentClientsOrTrainerFragment();
            // Add the fragment to the 'fragment_container' FrameLayout
            fragmentTransaction.replace(R.id.fragment_container, firstFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onUserSelected(String userId) {
        FragmentManager fm = getFragmentManager();
        SelectedUserDetailsFragment selectedUserDetailsFragment = SelectedUserDetailsFragment.newInstance(userId);
        selectedUserDetailsFragment.show(fm, "fragment_selected_user");
    }

    public void trainer(Bundle savedInstanceState) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            CurrentClientsOrTrainerFragment firstFragment = new CurrentClientsOrTrainerFragment();
            // Add the fragment to the 'fragment_container' FrameLayout
            fragmentTransaction
                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                    .replace(R.id.fragment_container, firstFragment)
                    .commit();
        }
    }

    public void profile(Bundle savedInstanceState) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        if (findViewById(R.id.fragment_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // Create a new Fragment to be placed in the activity layout
            YourProfileFragment profileFragment = new YourProfileFragment();
            // Add the fragment to the 'fragment_container' FrameLayout
            fragmentTransaction
                    .setCustomAnimations(R.animator.fade_in, R.animator.fade_out)
                    .replace(R.id.fragment_container, profileFragment)
                    .commit();
        }

    }
}

