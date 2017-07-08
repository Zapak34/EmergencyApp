package com.nightvisionmedia.emergencyapp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Omar (GAZAMAN) Myers on 6/13/2017.
 */

public class Message {
    public static void longToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void shortToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
