package com.nightvisionmedia.emergencyapp.sugar_models;


import com.orm.SugarRecord;
import com.orm.dsl.Column;
import com.orm.dsl.Table;

/**
 * Created by Omar (GAZAMAN) Myers on 7/2/2017.
 */

@Table (name = "alerts_favorites")
public class AlertsFavorites extends SugarRecord {
    @Column(name = "alert_id")
    private int alertID;

    @Column(name = "alert_title")
    private String alertTitle;

    @Column(name = "alert_content")
    private String alertContent;

    @Column(name = "alert_image_url")
    private String  alertImageURL;

    @Column(name = "alert_time_posted")
    private String alertPostedTime;

    public int getAlertID() {
        return alertID;
    }

    public void setAlertID(int alertID) {
        this.alertID = alertID;
    }

    public String getAlertTitle() {
        return alertTitle;
    }

    public void setAlertTitle(String alertTitle) {
        this.alertTitle = alertTitle;
    }

    public String getAlertContent() {
        return alertContent;
    }

    public void setAlertContent(String alertContent) {
        this.alertContent = alertContent;
    }

    public String getAlertImageURL() {
        return alertImageURL;
    }

    public void setAlertImageURL(String alertImageURL) {
        this.alertImageURL = alertImageURL;
    }

    public String getAlertPostedTime() {
        return alertPostedTime;
    }

    public void setAlertPostedTime(String alertPostedTime) {
        this.alertPostedTime = alertPostedTime;
    }
}
