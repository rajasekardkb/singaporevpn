package com.vpnmastersm.singaporevpnmaster;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class Activity_Nav extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    private AlertDialog RateAppDialog,AboutDialog;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_navigation);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


//        MobileAds.initialize(this, "ca-app-pub-8886222729959093~8938162699");
        drawerLayout = findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        drawerLayout.addDrawerListener(drawerToggle);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectItem(menuItem);
                    return true;
                });
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
    }

    public void selectItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        switch (menuItem.getItemId()) {


            case R.id.aboutus_nav:
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About Us");
                builder.setMessage("VPN Master Team  by SMkoki");
                builder.setPositiveButton("Ok", (dialog, which) -> dialog.dismiss());

                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
                AboutDialog = builder.create();
                AboutDialog.show();
                break;
            case R.id.rateapp_nav:
                RateAppDialog();
               // Toast.makeText(this, "url needed", Toast.LENGTH_SHORT).show();

                break;


            case R.id.shareapp_nav:

                final String shareurl = "https://play.google.com/store/apps/details?id=com.vpnmastersm.singaporevpn";


                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");

                String sharebody = "Singapore VPN - Free & Unlimited VPN for Android, with the Best VPN service and Fastest speed. \n" +
                        "\n" +
                        "https://play.google.com/store/apps/details?id=com.vpnmastersm.singaporevpn\n";
                sharingIntent.putExtra(Intent.EXTRA_TEXT, sharebody);
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareurl);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
//                Toast.makeText(this, "url needed", Toast.LENGTH_SHORT).show();


                break;

            case R.id.privacypolicy_nav:

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://smkoki2020.blogspot.com/2020/10/vpn-master-team.html")));
                break;

            case R.id.feedback_nav:
                Intent intent = new Intent(this, FeedbackActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }


        drawerLayout.closeDrawers();
    }

    private void RateAppDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Rate Us");
        builder.setMessage("Please take a moment to Rate our Application");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.vpnmastersm.singaporevpn")));

            }
        });

        builder.setNegativeButton("Later", (dialog, which) -> dialog.dismiss());


        RateAppDialog = builder.create();
        RateAppDialog.show();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

}
