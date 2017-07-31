package com.nightvisionmedia.emergencyapp.sugar_models;


import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

/**
 * Created by Omar (GAZAMAN) Myers on 7/2/2017.
 */

@Table (name = "disaster_favorites")
public class DisasterFavorites extends SugarRecord {
    @Column(name = "disaster_id")
    private int disasterID;

    @Column(name = "disaster_title")
    private String disasterTitle;

    @Column(name = "disaster_content")
    private String disasterContent;

    @Column(name = "disaster_image_url")
    private String  disasterImageURL;

    @Column(name = "disaster_time_posted")
    private String disasterPostedTime;


    public int getDisasterID() {
        return disasterID;
    }

    public void setDisasterID(int disasterID) {
        this.disasterID = disasterID;
    }

    public String getDisasterTitle() {
        return disasterTitle;
    }

    public void setDisasterTitle(String disasterTitle) {
        this.disasterTitle = disasterTitle;
    }

    public String getDisasterContent() {
        return disasterContent;
    }

    public void setDisasterContent(String disasterContent) {
        this.disasterContent = disasterContent;
    }

    public String getDisasterImageURL() {
        return disasterImageURL;
    }

    public void setDisasterImageURL(String disasterImageURL) {
        this.disasterImageURL = disasterImageURL;
    }

    public String getDisasterPostedTime() {
        return disasterPostedTime;
    }

    public void setDisasterPostedTime(String disasterPostedTime) {
        this.disasterPostedTime = disasterPostedTime;
    }
}
