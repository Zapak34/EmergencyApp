package com.nightvisionmedia.emergencyapp.utils;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by Omar (GAZAMAN) Myers on 6/28/2017.
 */

public class App {
    public static void finishActivity(AppCompatActivity appCompatActivity){
        appCompatActivity.finish();
    }

    public static void refreshActivity(AppCompatActivity appCompatActivity){
        appCompatActivity.recreate();
    }
}
