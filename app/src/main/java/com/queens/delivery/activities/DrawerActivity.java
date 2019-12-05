package com.queens.delivery.activities;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.navigation.NavigationView;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.queens.delivery.R;
import com.queens.delivery.fragments.*;
import com.queens.delivery.models.Rider;
import com.queens.delivery.shared.SessionHandler;
import com.queens.delivery.constants.NavigationDrawerConstants;


public class DrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private View navHeader;
    private SessionHandler session;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session=new SessionHandler(getApplicationContext());
        Rider rider = session.getRiderDetails();

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navHeader = navigationView.getHeaderView(0);

        TextView navUsername = navHeader.findViewById(R.id.textView1);
        navUsername.setText(rider.getFULLNAME());

        TextView navUserEmail = navHeader.findViewById(R.id.textView2);
        navUserEmail.setText(rider.getEMAIL());

        ImageView profileImage = navHeader.findViewById(R.id.image_header);
        Glide.with(this).load(NavigationDrawerConstants.PROFILE_URL).thumbnail(0.5f).into(profileImage);

        ImageView navBackground = navHeader.findViewById(R.id.img_header_bg);
        Glide.with(this).load(NavigationDrawerConstants.BACKGROUND_URL).thumbnail(0.5f).into(navBackground);

        navigationView.setCheckedItem(R.id.nav_home);
        Fragment fragment = new HomeFragment();
        displaySelectedFragment(fragment);
    }

    @Override
    public void onBackPressed(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_refresh:
                Snackbar.make(drawer, getString(R.string.action_refreshs), Snackbar.LENGTH_LONG).show();
                break;
            case R.id.action_settings:
                Log.d("Action","Logging Out");
                //Snackbar.make(drawer, getString(R.string.action_settings), Snackbar.LENGTH_LONG).show();
                session.logoutRider();
                Intent intent = new Intent(DrawerActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();
        Fragment fragment = null;

        switch (id){
            case R.id.nav_home:
                fragment = new HomeFragment();
                displaySelectedFragment(fragment);
                break;
            case R.id.nav_orders:
                fragment = new OrdersFragment();
                displaySelectedFragment(fragment);
                break;
            case R.id.nav_express:
                fragment = new ExpressFragment();
                displaySelectedFragment(fragment);
                break;
            case R.id.nav_exchange:
                fragment = new ExchangesFragment();
                displaySelectedFragment(fragment);
                break;
            case R.id.nav_delivered:
                fragment =  new DeliveredFragment();
                displaySelectedFragment(fragment);
                break;
            case R.id.nav_undelivered:
                fragment =  new UndeliveredFragment();
                displaySelectedFragment(fragment);
                break;
            case R.id.nav_rejected:
                fragment = new RejectedFragment();
                displaySelectedFragment(fragment);
                break;
            case R.id.nav_returned:
                fragment = new ReturnedFragment();
                displaySelectedFragment(fragment);
                break;
            case R.id.nav_parcels:
                fragment = new ParcelFragment();
                displaySelectedFragment(fragment);
                break;
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void displaySelectedFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }


}
