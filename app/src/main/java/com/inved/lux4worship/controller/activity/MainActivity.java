package com.inved.lux4worship.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.inved.lux4worship.controller.fragment.NotesFragment;
import com.inved.lux4worship.R;
import com.inved.lux4worship.controller.fragment.SongsFragment;
import com.inved.lux4worship.controller.fragment.TeamFragment;
import com.inved.lux4worship.base.BaseActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Declaration for Navigation Drawer
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private static final int SIGN_OUT_TASK = 10;
    MenuItem logout;
    TextView navFirstname;
    TextView navWorshipTeam;
    ImageView navProfileImage;

    //Declaration for fragments
    BottomNavigationView bottomNavigationView;

    private ArrayList<String> arrayListFragment = new ArrayList<>();


    private static final int NUM_PAGES = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configureToolBar();
        //Bottom Navigation View
        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //NavigationDrawer
        logout=findViewById(R.id.activity_main_drawer_logout);


        //Configuration navigation view header
        NavigationView mNavigationView = findViewById(R.id.activity_main_nav_view);
        navFirstname = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_FirstName);
        navWorshipTeam = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_worship_team);
        navProfileImage = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_profile_image);

        this.initializationView();
        this.configureDrawerLayout();
        this.configureNavigationView();
    }

    private void initializationView() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_main_frame_layout, TeamFragment.newInstance());
        transaction.commit();
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.activity_main;
    }


    // ------------------------
    // TOOLBAR & DRAWER LAYOUT
    // ------------------------

    // Configure Toolbar
    private void configureToolBar(){
        this.toolbar = findViewById(R.id.activity_main_toolbar);
//        setSupportActionBar(toolbar);
       // setTitle(getString(R.string.toolbar_title_main_activity));
    }

    // Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    // Configure NavigationView
    private void configureNavigationView(){
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    //Navigation drawer
    @Override
    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //Navigation drawer
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // 4 - Handle Navigation Item Click
        int id = item.getItemId();

        switch (id){
            case R.id.activity_main_drawer_profile :this.startProfileActivity();
                break;
            case R.id.activity_main_drawer_dates: this.startMyDatesActivity();
                break;
            case R.id.activity_main_drawer_logout:signOutUserFromFirebase();
                break;
            default:
                break;
        }

        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    // ------------------------
    // BOTTOM NAVIGATION
    // ------------------------

    //Configuration of the Bottom Navigation View on click
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.bottom_navigation_menu_team:
                    selectedFragment = TeamFragment.newInstance();
                //    toolbar.setTitle(getString(R.string.bottom_navigation_menu_team));
                    break;
                case R.id.bottom_navigation_menu_songs:
                    selectedFragment = SongsFragment.newInstance();
                  //  toolbar.setTitle(getString(R.string.bottom_navigation_menu_songs));
                    break;
                case R.id.bottom_navigation_menu_notes:
                    selectedFragment = NotesFragment.newInstance();
                 //   toolbar.setTitle(getString(R.string.bottom_navigation_menu_notes));
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }
    };

    // -------------------
    // NAVIGATION
    // -------------------









    // Launch Profile Activity
    private void startProfileActivity(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    // Launch Main Activity
    private void startMyDatesActivity(){
        Intent intent = new Intent(this, MyDatesActivity.class);
        startActivity(intent);
    }

    //Signout

    //   @OnClick(R.id.activity_main_drawer_logout)
    public void signOutUserFromFirebase(){
        AuthUI.getInstance()

                .signOut(this);
             //   .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));


    }

    // Create OnCompleteListener called after tasks ended
  /*  private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return aVoid -> {
            if (origin == SIGN_OUT_TASK) {
                startMainActivity();
                finish();
            }
        };
    }*/


}
