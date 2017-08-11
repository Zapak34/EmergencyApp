package com.nightvisionmedia.emergencyapp.sugar_models;


import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

/**
 * Created by Omar (GAZAMAN) Myers on 7/2/2017.
 */

@Table (name = "automotive_favorites")
public class AutomotiveRoadSafetyFavorites extends SugarRecord {
    @Column(name = "automotive_id")
    private int automotiveID;

    @Column(name = "automotive_title")
    private String automotiveTitle;

    @Column(name = "automotive_content")
    private String automotiveContent;

    @Column(name = "automotive_image_url")
    private String  automotiveImageURL;

    @Column(name = "automotive_time_posted")
    private String automotivePostedTime;


    public int getAutomotiveID() {
        return automotiveID;
    }

    public void setAutomotiveID(int automotiveID) {
        this.automotiveID = automotiveID;
    }

    public String getAutomotiveTitle() {
        return automotiveTitle;
    }

    public void setAutomotiveTitle(String automotiveTitle) {
        this.automotiveTitle = automotiveTitle;
    }

    public String getAutomotiveContent() {
        return automotiveContent;
    }

    public void setAutomotiveContent(String automotiveContent) {
        this.automotiveContent = automotiveContent;
    }

    public String getAutomotiveImageURL() {
        return automotiveImageURL;
    }

    public void setAutomotiveImageURL(String automotiveImageURL) {
        this.automotiveImageURL = automotiveImageURL;
    }

    public String getAutomotivePostedTime() {
        return automotivePostedTime;
    }

    public void setAutomotivePostedTime(String automotivePostedTime) {
        this.automotivePostedTime = automotivePostedTime;
    }
}
