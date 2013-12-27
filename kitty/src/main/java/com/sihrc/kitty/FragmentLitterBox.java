package com.sihrc.kitty;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
        Log.d("FragmentLitterBox", "OnCreateView");
        return inflater.inflate(R.layout.fragment_litterbox, null);
    }

    //When the Activity is done being created
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("FragmentLitterBox", "OnActivityCreated");
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
                    Toast.makeText(getActivity(), curKitty.name + " says hi!", Toast.LENGTH_SHORT).show();
                    refreshFragment();
                }
            }
        });

        kittyList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Kitty curKitty = (Kitty) parent.getItemAtPosition(position);
                Toast.makeText(getActivity(), "I WAS LONG CLICKED", Toast.LENGTH_SHORT).show();
                if (curKitty != null){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("View details for " + curKitty.name + "?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent in = new Intent(getActivity(), ActivityKittenDetails.class);
                                    in.putExtra("kittyId", curKitty.url);
                                    dialog.dismiss();
                                    startActivity(in);
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
        Log.d("DEBUGGER", kittyAdapter.toString());
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshFragment();
        Log.d("FragmentLitterBox", "OnStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshFragment();
        Log.d("FragmentLitterBox", "OnResume");
    }
}
