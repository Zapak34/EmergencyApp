package com.nightvisionmedia.emergencyapp.appconfig;

import android.app.Application;

import com.orm.SugarContext;

/**
 * Created by Omar (GAZAMAN) Myers on 6/29/2017.
 */

public class AppConfig extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        SugarContext.terminate();
    }
}
