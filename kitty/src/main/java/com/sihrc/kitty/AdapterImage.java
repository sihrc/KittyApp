package com.sihrc.kitty;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

/**
 * Created by chris on 12/22/13.
 */
public class AdapterImage extends ArrayAdapter{
    //Show CheckBox?
    Boolean checkbox;

    //ArrayList Containing the Image Urls
    ArrayList<Kitty> kitties;

    //Parent Activity
    Activity activity;

    //Public Constructor
    public AdapterImage(Activity activity, ArrayList<Kitty> kitties, Boolean checkbox){
        super(activity, R.layout.listitem_kitty, kitties);
        this.kitties = kitties;
        this.activity = activity;
        this.checkbox = checkbox;
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
        if (convertView == null){ //Grab View if haven't already
            convertView = activity.getLayoutInflater().inflate(R.layout.listitem_kitty,null);
        }

        //Set Image based on BitMap
        Kitty kitty = kitties.get(position);

        ResizableImageView image = (ResizableImageView) convertView.findViewById(R.id.listitem_kitty_image);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setImageBitmap(BitmapFactory.decodeByteArray(kitty.image, 0, kitty.image.length));

        if (checkbox){
            convertView.findViewById(R.id.checkbox).setVisibility(View.VISIBLE);
        }

        return convertView;
    }
}
