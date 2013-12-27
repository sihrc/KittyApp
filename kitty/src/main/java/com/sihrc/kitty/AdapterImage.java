package com.sihrc.kitty;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import java.util.ArrayList;

/**
 * Created by chris on 12/22/13.
 */
public class AdapterImage extends ArrayAdapter{
    //ArrayList Containing the Image Urls
    ArrayList<Kitty> kitties;

    //Parent Activity
    Activity activity;

    //Public Constructor
    public AdapterImage(Activity activity, ArrayList<Kitty> kitties){
        super(activity, R.layout.listitem_kitty, kitties);
        this.kitties = kitties;
        this.activity = activity;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Kitty kitty: this.kitties) {
            sb.append("Kitty ");
            sb.append(kitty.url);
            sb.append(" ");
            sb.append(kitty.category);
            sb.append("\n");
        }
        return sb.toString();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView image;
        if (convertView == null){ //Grab View if haven't already
            image = (ImageView) activity.getLayoutInflater().inflate(R.layout.listitem_kitty,null);
            image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else { //Cast ImageView if view not null
            image = (ImageView) convertView;
        }

        //Set Image based on BitMap
        Kitty kitty = kitties.get(position);
        image.setImageBitmap(BitmapFactory.decodeByteArray(kitty.image, 0, kitty.image.length));
        return image;
    }
}
