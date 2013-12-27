package com.sihrc.kitty;

/**
 * Created by chris on 12/23/13.
 */
public class Kitty {
    String url, name, visible, favorite, category, status;
    byte[] image;

    //Public Constructor to create a kitty
    public Kitty(String url, String name, String visible, String favorite, String category, String status, byte[] image){
        this.url = url;
        this.name = name;
        this.visible = visible;
        this.favorite = favorite;
        this.category = category;
        this.image = image;
        this.status = status;
    }

    /**
     * Set Fields
     */
    public void setName(String value){
        this.name = toTitleCase(value);
    }

    public void setCategory(String value){
        this.category = toTitleCase(value);
    }

    /**
     * Title Case Method
     */
    public String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
