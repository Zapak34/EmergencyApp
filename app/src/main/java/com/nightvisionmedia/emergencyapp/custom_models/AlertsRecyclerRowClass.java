package com.nightvisionmedia.emergencyapp.custom_models;

/**
 * Created by Omar (GAZAMAN) Myers on 6/29/2017.
 */

public class AlertsRecyclerRowClass {
    private int id;
    private String title, content, image_link, time_posted;

    public AlertsRecyclerRowClass(int id, String title, String content, String image_link, String time_posted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.image_link = image_link;
        this.time_posted = time_posted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content.replace("~","'");
    }

    public void setContent(String message) {
        this.content = message.replace("~","'");
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

    public String getTime_posted() {
        return time_posted;
    }

    public void setTime_posted(String time_posted) {
        this.time_posted = time_posted;
    }
}
