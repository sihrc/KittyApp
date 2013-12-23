package com.sihrc.kitty;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by chris on 12/22/13.
 */
public class AdapterImage extends BaseAdapter{
    //ArrayList Containing the Image Urls
    ArrayList<String> urls;

    //Parent Activity
    Activity activity;

    //Public Constructor
    public AdapterImage(Activity activity, ArrayList<String> urls){
        this.urls = urls;
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image;
        if (convertView == null){ //Grab View if haven't already
            image = new ImageView(activity.getApplicationContext());
            image.setLayoutParams(new GridView.LayoutParams(180,180));
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
            image.setPadding(8, 8, 8, 8);
        } else { //Cast ImageView if view not null
            image = (ImageView) convertView;
        }

        //Set Drawable based on URL
        image.setImageDrawable(LoadImageFromURL(urls.get(position)));
        return convertView;
    }

    //Grab Image from Web as Drawable
    private Drawable LoadImageFromURL(String url) {
        try {
            return Drawable.createFromStream((InputStream) new URL(url).getContent(), "src name");
        } catch (Exception e) {
            Log.d("LoadImageFromURL Exception:", "Image could not be loaded");
            return activity.getResources().getDrawable(R.drawable.kittynotfound);
        }
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
}
