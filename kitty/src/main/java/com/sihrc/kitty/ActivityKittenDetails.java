package com.sihrc.kitty;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by chris on 12/22/13.
 */
public class ActivityKittenDetails extends FragmentActivity{
    /**
     * Swipe View Variables
     */
    ViewPager pager;
    AdapterFragmentCollection fragmentAdapter;
    ActionBar actionBar;

    /**
     * Database
     */
    HandlerDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * Database
         */

        db = new HandlerDatabase(this);
        db.open();

        /**
         * ViewPager
         */

        //Pass the fragment manager to the collection adapter
        fragmentAdapter = new AdapterFragmentCollection(
                getSupportFragmentManager()
        );

        //Get the ViewPager View from XML
        pager = (ViewPager) findViewById(R.id.activity_main_pager);

        //Set the view pager's adapter
        pager.setAdapter(fragmentAdapter);

        //When Page Changes - pager should change accordingly
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {}

            @Override
            public void onPageSelected(int i) {
                if (actionBar != null){
                    actionBar.setSelectedNavigationItem(i);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });

        /**
         * ActionBar Tabs
         */
        //Get the ActionBar
        actionBar = getActionBar();
        if (actionBar != null){
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
            Log.d("DEBUGGER", "is null");
        }
        //Create the Tab Listener
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
        };

        //Create Tabs and Set Listener
        //Tab 1
        actionBar.addTab(actionBar.newTab().setText("New Kitties").setTabListener(tabListener));
        //Tab 2
        actionBar.addTab(actionBar.newTab().setText("Litter Box").setTabListener(tabListener));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
