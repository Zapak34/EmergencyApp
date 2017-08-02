package com.nightvisionmedia.emergencyapp.sugar_models;


import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

/**
 * Created by Omar (GAZAMAN) Myers on 7/2/2017.
 */

@Table (name = "temporary_favorites")
public class TemporaryFavorites extends SugarRecord {
    @Column(name = "temporary_id")
    private int temporaryID;

    @Column(name = "temporary_title")
    private String temporaryTitle;

    @Column(name = "temporary_content")
    private String temporaryContent;

    @Column(name = "temporary_image_url")
    private String  temporaryImageURL;

    @Column(name = "temporary_time_posted")
    private String temporaryPostedTime;


    public int getTemporaryID() {
        return temporaryID;
    }

    public void setTemporaryID(int temporaryID) {
        this.temporaryID = temporaryID;
    }

    public String getTemporaryTitle() {
        return temporaryTitle;
    }

    public void setTemporaryTitle(String temporaryTitle) {
        this.temporaryTitle = temporaryTitle;
    }

    public String getTemporaryContent() {
        return temporaryContent;
    }

    public void setTemporaryContent(String temporaryContent) {
        this.temporaryContent = temporaryContent;
    }

    public String getTemporaryImageURL() {
        return temporaryImageURL;
    }

    public void setTemporaryImageURL(String temporaryImageURL) {
        this.temporaryImageURL = temporaryImageURL;
    }

    public String getTemporaryPostedTime() {
        return temporaryPostedTime;
    }

    public void setTemporaryPostedTime(String temporaryPostedTime) {
        this.temporaryPostedTime = temporaryPostedTime;
    }
}
