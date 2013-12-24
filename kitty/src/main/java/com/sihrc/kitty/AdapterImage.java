package com.sihrc.kitty;

import android.app.Activity;
import android.graphics.BitmapFactory;
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
    ArrayList<Kitty> kitties;

    //Parent Activity
    Activity activity;

    //Public Constructor
    public AdapterImage(Activity activity, ArrayList<Kitty> kitties){
        this.kitties = kitties;
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

        //Set Image based on BitMap
        Kitty kitty = kitties.get(position);
        image.setImageBitmap(BitmapFactory.decodeByteArray(kitty.image, 0, kitty.image.length));
        Log.d("CheckPoint", "AdapterImage");
        return image;
    }

    @Override
    public int getCount() {
        return kitties.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(kitties.get(position).id);
    }

    @Override
    public Object getItem(int position) {
        return kitties.get(position);
    }
}
