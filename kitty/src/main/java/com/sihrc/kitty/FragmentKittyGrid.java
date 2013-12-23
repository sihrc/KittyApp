package com.sihrc.kitty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by chris on 12/22/13.
 */
public class FragmentKittyGrid extends Fragment{
    //List of kitties to show
    ArrayList<String> kitties;

    //Public Constructor to decide the kitties
    public FragmentKittyGrid(ArrayList<String> kitties){
        this.kitties = kitties;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_kitty_grid, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateGridView();
    }

    private void populateGridView(){
        
    }
}
