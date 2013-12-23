package com.sihrc.kitty;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by chris on 12/22/13.
 */
public class ActivityMain extends FragmentActivity{
    /**
     * Swipe View Variables
     */
    ViewPager pager;
    AdapterFragmentCollection fragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Pass the fragment manager to the collection adapter
        fragmentAdapter = new AdapterFragmentCollection(
                getSupportFragmentManager()
        );

        //Get the ViewPager View from XML
        pager = (ViewPager) findViewById(R.id.activity_main_pager);

        //Set the view pager's adapter
        pager.setAdapter(fragmentAdapter);
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
