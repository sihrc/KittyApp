package com.sihrc.kitty;

/**
 * Created by chris on 12/23/13.
 */
public class Kitty {
    String url, name, seen, favorite, category;
    byte[] image;

    //Public Constructor to create a kitty
    public Kitty(String url, String name, String seen, String favorite, String category, byte[] image){
        this.url = url;
        this.name = name;
        this.seen = seen;
        this.favorite = favorite;
        this.category = category;
        this.image = image;
    }
}
