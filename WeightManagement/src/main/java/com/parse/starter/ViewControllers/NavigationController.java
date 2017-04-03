package com.parse.starter.ViewControllers;

import android.app.Activity;
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
import android.widget.TabHost;

import com.parse.ParseUser;
import com.parse.starter.R;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import FragmentControllers.CurrentClientsOrTrainerFragment;
import FragmentControllers.YourProfileFragment;

public class NavigationController extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        //Toolbar (Top)
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

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
                    Log.i("AppInfo", "Calendar button pressed");
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
                    Log.i("AppInfo", "Calendar button pressed");
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
            fragmentTransaction.replace(R.id.fragment_container, firstFragment);
            fragmentTransaction.commit();
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
            fragmentTransaction.replace(R.id.fragment_container, profileFragment);
            fragmentTransaction.commit();
        }

    }
}

