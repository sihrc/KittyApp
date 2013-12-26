package com.sihrc.kitty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by chris on 12/23/13.
 */
public class HandlerDatabase {
    //Database Model
    private ModelDatabase model;

    //Database
    private SQLiteDatabase database;

    //All Fields
    private String[] allColumns = {
            ModelDatabase.KITTY_ID,
            ModelDatabase.KITTY_NAME,
            ModelDatabase.KITTY_SEEN,
            ModelDatabase.KITTY_FAVORITE,
            ModelDatabase.KITTY_CATEGORY,
            ModelDatabase.KITTY_IMAGE
    };

    //Public Constructor - create connection to Database
    public HandlerDatabase(Context context){
        model = new ModelDatabase(context);
    }

    //Adds New Kitty to the Database
    public void addKittyToDatabase(byte[] image){
        ContentValues values = new ContentValues();
        values.put(ModelDatabase.KITTY_NAME, "");
        values.put(ModelDatabase.KITTY_SEEN, "never");
        values.put(ModelDatabase.KITTY_FAVORITE, "false");
        values.put(ModelDatabase.KITTY_CATEGORY, "none");
        values.put(ModelDatabase.KITTY_IMAGE, image);
        Log.d("ImageByteArray", Arrays.toString(image));
        database.insert(ModelDatabase.TABLE_NAME, null, values);
    }

    //Get all Kitties from the Database
    public ArrayList<Kitty> getAllKitties(){
        return sweepCursor(database.query(ModelDatabase.TABLE_NAME, allColumns, null, null, null, null, null));
    }

    //Sweep Through Cursor and return a List of Kitties
    private ArrayList<Kitty> sweepCursor(Cursor cursor){
        ArrayList<Kitty> kitties = new ArrayList<Kitty>();

        //Get to the beginning of the cursor
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            //Get the Kitty
            Kitty kitty = new Kitty(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getBlob(5)
            );
            //Set the Kitty Id
            kitty.id = cursor.getString(0);
            //Add the Kitty
            kitties.add(kitty);
            //Go on to the next Kitty
            cursor.moveToNext();
        }
        return kitties;
    }

    //Delete all Kitties
    public void delete(){
        database.delete(ModelDatabase.TABLE_NAME, ModelDatabase.KITTY_ID + " like '%" + "%'", null);
    }
    //Get Writable Database - open the database
    public void open(){
        database = model.getWritableDatabase();
    }
}
