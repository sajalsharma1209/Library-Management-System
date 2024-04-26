package com.shashank.platform.classroomappui.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shashank.platform.classroomappui.LoginScreen;
import com.shashank.platform.classroomappui.MyProfile;
import com.shashank.platform.classroomappui.R;
import com.shashank.platform.classroomappui.ui.plans.ViewPlans;
import com.shashank.platform.classroomappui.ui.students.ViewStudents;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    LinearLayout profile;
    TextView profileName, profileEmail;
    TextView libraryName, totalCollection, totalDues;

    TextView liveMembers, totalMembers, expirenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        profile = header.findViewById(R.id.profile);
        profile.setOnClickListener(this);

        profileName = header.findViewById(R.id.profilename);
        profileName.setText("test");

        profileEmail = header.findViewById(R.id.profilemail);
        profileEmail.setText("test@gmail.com");

        libraryName = findViewById(R.id.libraryName);
        libraryName.setText("test");

        totalCollection = findViewById(R.id.incomeLbl);

        totalDues = findViewById(R.id.duesLbl);

        totalMembers = findViewById(R.id.totalMemberNumber);
        liveMembers = findViewById(R.id.liveMemberNumber);
        expirenumber = findViewById(R.id.expireMemberNumber);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent intent = new Intent(getApplicationContext(), Home.class);
            startActivity(intent);
        } else if (id == R.id.nav_students) {
            Intent intent = new Intent(getApplicationContext(), ViewStudents.class);
            startActivity(intent);
        } else if (id == R.id.nav_plans) {
            Intent intent = new Intent(getApplicationContext(), ViewPlans.class);
            startActivity(intent);
        }
        // else if (id == R.id.nav_settings) {
//            Intent intent = new Intent(getApplicationContext(), Settings.class);
//            startActivity(intent);
//
//        }
        else if (id == R.id.nav_logout) {


            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Logout Confirmation");
            builder.setMessage("Are you sure you want to logout?");

// Add the buttons
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear(); // Clear all data from SharedPreferences
                    editor.apply(); // Commit the changes
                    finish();
                    startActivity(new Intent(Home.this, LoginScreen.class));
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked No button
                    // Dismiss the dialog
                    dialog.dismiss();
                }
            });

            // Create and show the AlertDialog
            AlertDialog dialog = builder.create();
            dialog.show();

        }
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_rate) {
//
//        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.profile) {
            Intent intent = new Intent(getApplicationContext(), MyProfile.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
