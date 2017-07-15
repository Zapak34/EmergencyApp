package com.nightvisionmedia.emergencyapp.sugar_models;


import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

/**
 * Created by Omar (GAZAMAN) Myers on 7/2/2017.
 */

@Table (name = "happenings_favorites")
public class HappeningsFavorites extends SugarRecord {
    @Column(name = "happen_id")
    private int happenID;

    @Column(name = "happen_title")
    private String happenTitle;

    @Column(name = "happen_content")
    private String happenContent;

    @Column(name = "happen_image_url")
    private String  happenImageURL;

    @Column(name = "happen_time_posted")
    private String happenPostedTime;

    public int getHappenID() {
        return happenID;
    }

    public void setHappenID(int happenID) {
        this.happenID = happenID;
    }

    public String getHappenTitle() {
        return happenTitle;
    }

    public void setHappenTitle(String happenTitle) {
        this.happenTitle = happenTitle;
    }

    public String getHappenContent() {
        return happenContent;
    }

    public void setHappenContent(String happenContent) {
        this.happenContent = happenContent;
    }

    public String getHappenImageURL() {
        return happenImageURL;
    }

    public void setHappenImageURL(String happenImageURL) {
        this.happenImageURL = happenImageURL;
    }

    public String getHappenPostedTime() {
        return happenPostedTime;
    }

    public void setHappenPostedTime(String happenPostedTime) {
        this.happenPostedTime = happenPostedTime;
    }
}
