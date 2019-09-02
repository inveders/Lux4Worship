package com.inved.lux4worship;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.inved.lux4worship.base.BaseActivity;

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
    ViewPager viewPager;

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

        //Viewpager
        viewPager = findViewById(R.id.viewpager_fragment); //Init Viewpager
        setupFm(getSupportFragmentManager(), viewPager); //Setup Fragment
        viewPager.setCurrentItem(0); //Set Currrent Item When Activity Start
        viewPager.setOnPageChangeListener(new PageChange()); //Listeners For Viewpager When Page Changed

        //Configuration navigation view header
        NavigationView mNavigationView = findViewById(R.id.activity_main_nav_view);
        navFirstname = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_FirstName);
        navWorshipTeam = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_worship_team);
        navProfileImage = mNavigationView.getHeaderView(0).findViewById(R.id.nav_header_profile_image);

        this.configureDrawerLayout();
        this.configureNavigationView();
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
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.toolbar_title_main_activity));
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
            switch (item.getItemId()) {
                case R.id.bottom_navigation_menu_team:
                    viewPager.setCurrentItem(0);
                    toolbar.setTitle(getString(R.string.toolbar_title_main_activity));
                    return true;
                case R.id.bottom_navigation_menu_songs:
                    viewPager.setCurrentItem(1);
                    toolbar.setTitle(getString(R.string.toolbar_title_main_activity));
                    return true;
                case R.id.bottom_navigation_menu_notes:
                    viewPager.setCurrentItem(2);
                    toolbar.setTitle(getString(R.string.toolbar_title_main_activity));
                    return true;
            }
            return false;
        }
    };

    // -------------------
    // NAVIGATION
    // -------------------

    //Viewpager


    public static void setupFm(FragmentManager fragmentManager, ViewPager viewPager){

        FragmentPagerAdapter Adapter2 = new FragmentPagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return null ;
            }

            @Override
            public int getCount() {
                return NUM_PAGES;
            }
        };
      /*  FragmentAdapter Adapter = new FragmentAdapter(fragmentManager);
        //Add All Fragment To List
        Adapter2..add(new TeamFragment(), "Page Team");
        Adapter.add(new SongsFragment(), "Page Songs");
        Adapter.add(new NotesFragment(), "Page Notes");
        viewPager.setAdapter(Adapter);*/
    }






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

    public class PageChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_menu_team);
                    break;
                case 1:
                    bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_menu_songs);
                    break;
                case 2:
                    bottomNavigationView.setSelectedItemId(R.id.bottom_navigation_menu_notes);
                    break;
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
