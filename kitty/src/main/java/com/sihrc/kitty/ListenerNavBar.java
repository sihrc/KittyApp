package com.sihrc.kitty;



import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.Fragment;

/**
 * Created by chris on 12/24/13.
 */
public class ListenerNavBar implements ActionBar.TabListener {
    //Current Fragment
    Fragment curFragment;

    //Public Constructor
    public ListenerNavBar(Fragment curFragment){
        this.curFragment = curFragment;
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.add(R.id.activity_main_fragmentContainer, curFragment).commit();
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        ft.add(R.id.activity_main_fragmentContainer, curFragment).commit();
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
