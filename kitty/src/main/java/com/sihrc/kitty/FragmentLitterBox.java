package com.sihrc.kitty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by chris on 12/22/13.
 */
public class FragmentLitterBox extends FragmentOnSelectRefresh {
    /**
     * Database
     */
    HandlerDatabase db;     //Database Handler

    /**
     * Handles the List of Kitties
     */
    AdapterOwned kittyAdapter;
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
        kittyList = (ListView) getView().findViewById(R.id.fragment_litterbox_listview);

        //ListView Adapter
        kittyAdapter = new AdapterOwned(getActivity(), db.getOwnedKitties(), false);
        kittyList.setAdapter(kittyAdapter);

        kittyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Kitty curKitty = (Kitty) parent.getItemAtPosition(position);
                if (curKitty != null){
                    Intent in = new Intent(getActivity(), ActivityKittenDetails.class);
                    in.putExtra("kittyId", curKitty.url);
                    startActivity(in);
                    refreshFragment();
                }
            }
        });

        kittyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Kitty curKitty = (Kitty) parent.getItemAtPosition(position);
                if (curKitty != null){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Remove " + curKitty.name + "?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    curKitty.favorite = "false";
                                    db.updateKitty(curKitty);
                                    refreshFragment();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                return false;
            }
        });
    }

    /**
     * Syncs the database with the listview
     */
    public void refreshFragment(){
        kittyAdapter.clear();
        kittyAdapter.addAll(db.getOwnedKitties());
        kittyAdapter.notifyDataSetChanged();
        kittyList.invalidate();
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFragment();
    }
}
