package com.sihrc.kitty;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

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
            ModelDatabase.KITTY_URL,
            ModelDatabase.KITTY_NAME,
            ModelDatabase.KITTY_VISIBLE,
            ModelDatabase.KITTY_FAVORITE,
            ModelDatabase.KITTY_CATEGORY,
            ModelDatabase.KITTY_STATUS,
            ModelDatabase.KITTY_IMAGE
    };

    //Public Constructor - create connection to Database
    public HandlerDatabase(Context context){
        model = new ModelDatabase(context);
    }

    /**
     * Add
     */
    public void addKittyToDatabase(String url, byte[] image, String cat){
        ContentValues values = new ContentValues();
            values.put(ModelDatabase.KITTY_URL, url);
            values.put(ModelDatabase.KITTY_NAME, "");
            values.put(ModelDatabase.KITTY_VISIBLE, "true");
            values.put(ModelDatabase.KITTY_FAVORITE, "false");
            values.put(ModelDatabase.KITTY_CATEGORY, cat);
            values.put(ModelDatabase.KITTY_STATUS, "N/A");
            values.put(ModelDatabase.KITTY_IMAGE, image);
        database.insertWithOnConflict(ModelDatabase.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }
    public void updateKitty(Kitty kitty){
        ContentValues values = new ContentValues();
            values.put(ModelDatabase.KITTY_URL, kitty.url);
            values.put(ModelDatabase.KITTY_NAME, kitty.name);
            values.put(ModelDatabase.KITTY_VISIBLE, kitty.visible);
            values.put(ModelDatabase.KITTY_FAVORITE, kitty.favorite);
            values.put(ModelDatabase.KITTY_CATEGORY, kitty.category);
            values.put(ModelDatabase.KITTY_STATUS, kitty.status);
            values.put(ModelDatabase.KITTY_IMAGE, kitty.image);
        database.update(ModelDatabase.TABLE_NAME, values, ModelDatabase.KITTY_URL + " like '%" + kitty.url + "%'", null);
    }

    /**
     * Get
     */
    public ArrayList<Kitty> getAllKitties(){
        return sweepCursor(database.query(ModelDatabase.TABLE_NAME, allColumns, ModelDatabase.KITTY_VISIBLE + " like '%true%'", null, null, null, null));
    }
    public ArrayList<Kitty> getKittiesByCategory(String cat){
        return sweepCursor(database.query(
                ModelDatabase.TABLE_NAME,
                allColumns,
                ModelDatabase.KITTY_CATEGORY + " like '%" + cat + "%' AND " + ModelDatabase.KITTY_FAVORITE + " like '%false%' AND " + ModelDatabase.KITTY_VISIBLE + " like '%true%'",
                null, null, null, null, null
        ));
    }
    public ArrayList<Kitty> getOwnedKitties(){
        return sweepCursor(database.query(
                ModelDatabase.TABLE_NAME,
                allColumns,
                ModelDatabase.KITTY_FAVORITE + " like '%true%' AND " + ModelDatabase.KITTY_VISIBLE + " like '%true%'",
                null, null, null,
                ModelDatabase.KITTY_CATEGORY));
    }
    public Kitty getKittyById(String id){
        return sweepCursor(database.query(
                ModelDatabase.TABLE_NAME,
                allColumns,
                ModelDatabase.KITTY_URL + " like '%" + id + "%'",
                null, null, null, null
        )).get(0);
    }

    /**
     * Delete
     */
    public void deleteKittiesByCategory(String cat){
        database.delete(
                ModelDatabase.TABLE_NAME,
                ModelDatabase.KITTY_CATEGORY + " like '%" + cat + "%' AND " + ModelDatabase.KITTY_FAVORITE + " like '%false%'",
                null
        );
    }
    public void deleteKittyById(String id){
        database.delete(
                ModelDatabase.TABLE_NAME,
                ModelDatabase.KITTY_URL + " like '%" + id + "%'",
                null
        );
    }

    /**
     * Additional Helpers
     */
    //Sweep Through Cursor and return a List of Kitties
    private ArrayList<Kitty> sweepCursor(Cursor cursor){
        ArrayList<Kitty> kitties = new ArrayList<Kitty>();

        //Get to the beginning of the cursor
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            //Get the Kitty
            Kitty kitty = new Kitty(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getBlob(6)
            );
            //Add the Kitty
            kitties.add(kitty);
            //Go on to the next Kitty
            cursor.moveToNext();
        }
        return kitties;
    }

     //Get Writable Database - open the database
    public void open(){
        database = model.getWritableDatabase();
    }
}
