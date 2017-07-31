package com.nightvisionmedia.emergencyapp.custom_models;

/**
 * Created by Omar (GAZAMAN) Myers on 6/29/2017.
 */

public class SOSTitleRecyclerViewRowClass {
    private int id;
    private String title, image_link;

    public SOSTitleRecyclerViewRowClass(int id, String title, String image_link) {
        this.id = id;
        this.title = title;
        this.image_link = image_link;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage_link() {
        return image_link.replace("~","'");
    }

    public void setImage_link(String image_link) {
        this.image_link = image_link;
    }

    public String getTitle() {
        return title.replace("~","'");
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
