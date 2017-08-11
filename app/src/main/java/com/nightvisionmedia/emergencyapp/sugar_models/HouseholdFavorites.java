package com.nightvisionmedia.emergencyapp.sugar_models;


import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

@Table (name = "household_favorites")
public class HouseholdFavorites extends SugarRecord {
    @Column(name = "household_id")
    private int householdID;

    @Column(name = "household_title")
    private String householdTitle;

    @Column(name = "household_content")
    private String householdContent;

    @Column(name = "household_image_url")
    private String  householdImageURL;

    @Column(name = "household_time_posted")
    private String householdPostedTime;


    public int getHouseholdID() {
        return householdID;
    }

    public void setHouseholdID(int householdID) {
        this.householdID = householdID;
    }

    public String getHouseholdTitle() {
        return householdTitle;
    }

    public void setHouseholdTitle(String householdTitle) {
        this.householdTitle = householdTitle;
    }

    public String getHouseholdContent() {
        return householdContent;
    }

    public void setHouseholdContent(String householdContent) {
        this.householdContent = householdContent;
    }

    public String getHouseholdImageURL() {
        return householdImageURL;
    }

    public void setHouseholdImageURL(String householdImageURL) {
        this.householdImageURL = householdImageURL;
    }

    public String getHouseholdPostedTime() {
        return householdPostedTime;
    }

    public void setHouseholdPostedTime(String householdPostedTime) {
        this.householdPostedTime = householdPostedTime;
    }
}
