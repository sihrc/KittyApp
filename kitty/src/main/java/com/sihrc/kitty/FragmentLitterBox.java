package com.sihrc.kitty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by chris on 12/22/13.
 */
public class FragmentLitterBox extends Fragment {
    /**
     * Database
     */
    HandlerDatabase db;     //Database Handler

    /**
     * Handles the List of Kitties
     */
    AdapterImage kittyAdapter;
    ListView kittyList;

    //When the View is first created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setHasOptionsMenu(true); //Options Menu
        //Return the appropriate Fragment View
        return inflater.inflate(R.layout.fragment_litterbox, null);
    }

    //When the Activity is done being created
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Grab the Database Handler from the Activity
        db = ((ActivityMain) getActivity()).db;

        //Setup the ListView
        kittyList = (ListView) getView().findViewById(R.id.fragment_kitty_listView);

        //ListView Adapter
        kittyAdapter = new AdapterImage(getActivity(), db.getOwnedKitties());
        kittyList.setAdapter(kittyAdapter);
    }
}
