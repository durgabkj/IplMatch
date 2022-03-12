package com.ottego.iplHub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    Context context;
    MaterialToolbar mtbMatch;
    NavigationView nvHeader;
    DrawerLayout dlMainActivity;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        tabLayout = findViewById(R.id.tlMatch);
        viewPager = findViewById(R.id.vpMatch);
        mtbMatch = findViewById(R.id.mtbMatch);
        nvHeader = findViewById(R.id.nvHeader);
        dlMainActivity = findViewById(R.id.dlMainActivity);

        tabLayout.addTab(tabLayout.newTab().setText("Today"));
        tabLayout.addTab(tabLayout.newTab().setText("All Match"));
        tabLayout.addTab(tabLayout.newTab().setText("News"));


        setSupportActionBar(mtbMatch);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPageAdapter adapter = new ViewPageAdapter(this, getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, dlMainActivity, mtbMatch, R.string.navigation_open, R.string.navigation_close);
        dlMainActivity.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //when an item is selected from menu


        nvHeader.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = (item.getItemId());

                if (id == R.id.nav_Teams) {
                    Intent intent = new Intent(context, TeamActivity.class);
                    startActivity(intent);
                    dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_PointTable) {
                    Intent intent = new Intent(context, PointTableActivity.class);
                    startActivity(intent);
                    dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.nav_about) {
                    Intent intent = new Intent(context, About_us.class);
                    startActivity(intent);
                    dlMainActivity.closeDrawer(GravityCompat.START);
                    return true;
                } else if (id == R.id.mnuShare) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String shareSubText = "WhatsApp - The Great Chat App";
                    String shareBodyText = "https://play.google.com/store/apps/details?id=com.whatsapp&hl=en";
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubText);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBodyText);
                    startActivity(Intent.createChooser(shareIntent, "Share With"));
                    return true;
                }
                return false;
            }
        });

    }


}



