package com.sihrc.kitty;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by chris on 12/23/13.
 */
public class ModelDatabase extends SQLiteOpenHelper {
    //Table Name
    public static final String TABLE_NAME = "kitties";

    //Table Fields
    public static final String KITTY_URL = "url";
    public static final String KITTY_NAME = "name";
    public static final String KITTY_SEEN = "seen";
    public static final String KITTY_FAVORITE = "favorite";
    public static final String KITTY_CATEGORY = "category";
    public static final String KITTY_IMAGE = "image";

    //Database Info
    private static final String DATABASE_NAME = "KittyAppDatabase";
    private static final int DATABASE_VERSION = 1;

    // ModelDatabase creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "("
            + KITTY_URL + " TEXT NOT NULL UNIQUE, "
            + KITTY_NAME + " TEXT NOT NULL, "
            + KITTY_SEEN + " TEXT NOT NULL, "
            + KITTY_FAVORITE + " TEXT NOT NULL, "
            + KITTY_CATEGORY + " TEXT NOT NULL, "
            + KITTY_IMAGE + " BLOB );";

    //Default Constructor
    public ModelDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    //OnCreate Method - creates the ModelDatabase
    public void onCreate(SQLiteDatabase database){
        database.execSQL(DATABASE_CREATE);

    }
    @Override
    //OnUpgrade Method - upgrades ModelDatabase if applicable
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(ModelDatabase.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }
}
