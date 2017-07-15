package com.nightvisionmedia.emergencyapp.utils;

/**
 * Created by Lennis on 1/2/2017.
 */

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.Html;

import com.nightvisionmedia.emergencyapp.R;
import com.nightvisionmedia.emergencyapp.activities.HomeScreenActivity;
import com.nightvisionmedia.emergencyapp.activities.ShowMainContentActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;


public class NotificationManager {

    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;

    private Context mCtx;
    private static final String USER_EMAIL_KEY = "user_email";
    private static final String USER_FNAME_KEY = "user_fname";
    private static final String USER_PASSWORD_KEY = "user_password";
    private static final String USER_PARISH_KEY = "user_parish";
    private static final String USER_MNAME_KEY = "user_mname";
    private static final String USER_LNAME_KEY = "user_lname";
    private static final String USER_DOB_KEY = "user_dob";
    private static final String USER_AGE_KEY = "user_age";
    private static final String USER_CELL_KEY = "user_cell_phone";
    private static final String USER_TELE_KEY = "user_home_phone";
    private static final String USER_ADDRESS_KEY = "user_address";
    public NotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    //the method will show a big notification with an image
    //parameters are title for message title, message for message text, url of the big image and an intent that will open
    //when you will tap on the notification
    public void alertsBigNotification(String title, String content, String url) {
        int m = (int)((new Date().getTime()/1000L) % Integer.MAX_VALUE);
        String newContent = content.replace("~","'");
//        String newContent = message.replace("[fname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_FNAME_KEY))
//                .replace("[mname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_MNAME_KEY)).replace("[lname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_LNAME_KEY))
//                .replace("[dob]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_DOB_KEY)).replace("[age]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_AGE_KEY))
//                .replace("[address]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_ADDRESS_KEY).replace("_"," ")).replace("[email]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_EMAIL_KEY));

        Intent intent1 =  new Intent(mCtx,ShowMainContentActivity.class);
        intent1.putExtra("NOTIFY_ID",m);
        intent1.putExtra("title",title.replace("~","'"));
        intent1.putExtra("content",newContent);
        intent1.putExtra("image_url",url.replace("~","'"));
        intent1.putExtra("isNotification",true);
        //intent1.putExtra("isSmall",false);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(mCtx.getApplicationContext());
        taskStackBuilder.addParentStack(HomeScreenActivity.class);
        taskStackBuilder.addNextIntent(intent1);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(m, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(content).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);

        mBuilder.setSmallIcon(R.drawable.ic_alert_35dp).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(title.replace("~","'"))
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.drawable.ic_sos_warning_35dp)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.ic_sos_warning_35dp))
                .setContentText(newContent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[] {100, 1500, 500, 1500});

        Notification notification = mBuilder.build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, notification);
    }

    //the method will show a small notification
    //parameters are title for message title, message for message text and an intent that will open
    //when you will tap on the notification
    public void alertsSmallNotification(String title, String content) {
        int m = (int)((new Date().getTime()/1000L) % Integer.MAX_VALUE);
        String newContent = content.replace("~","'");
        /*String newContent = message.replace("[fname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_FNAME_KEY))
                .replace("[mname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_MNAME_KEY)).replace("[lname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_LNAME_KEY))
                .replace("[dob]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_DOB_KEY)).replace("[age]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_AGE_KEY))
                .replace("[address]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_ADDRESS_KEY).replace("_"," ")).replace("[email]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_EMAIL_KEY));*/

        Intent intent1 =  new Intent(mCtx,ShowMainContentActivity.class);
        intent1.putExtra("NOTIFY_ID",m);
        intent1.putExtra("title",title.replace("~","'"));
        intent1.putExtra("content",newContent);
        intent1.putExtra("isNotification",true);
        //intent1.putExtra("isSmall",true);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(mCtx.getApplicationContext());
        taskStackBuilder.addParentStack(HomeScreenActivity.class);
        taskStackBuilder.addNextIntent(intent1);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(m, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
//        Notification notification;
        mBuilder.setSmallIcon(R.drawable.ic_alert_35dp)
                .setTicker(title)
                .setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_sos_warning_35dp)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.drawable.ic_sos_warning_35dp))
                .setContentText(newContent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[] {100, 1500, 500, 1500});


        Notification notification = mBuilder.build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, notification);
    }



    /*public void reminderSmallNotification(String title, String message) {
        int m = (int)((new Date().getTime()/1000L) % Integer.MAX_VALUE);
        String newContent = message.replace("[fname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_FNAME_KEY))
                .replace("[mname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_MNAME_KEY)).replace("[lname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_LNAME_KEY))
                .replace("[dob]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_DOB_KEY)).replace("[age]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_AGE_KEY))
                .replace("[address]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_ADDRESS_KEY).replace("_"," ")).replace("[email]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_EMAIL_KEY));

        Intent intent1 =  new Intent(mCtx,HomeScreenActivity.class);
        intent1.putExtra("NOTIFY_ID",m);
        intent1.putExtra("title",title);
        intent1.putExtra("message",message);
        intent1.putExtra("isSmall",true);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(mCtx.getApplicationContext());
        taskStackBuilder.addParentStack(HomeScreenActivity.class);
        taskStackBuilder.addNextIntent(intent1);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(m, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
//        Notification notification;
        mBuilder.setSmallIcon(R.mipmap.app_icon)
                .setTicker(title)
                .setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.nys_app_logo)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.reminder))
                .setContentText(newContent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[] {100, 1500, 500, 1500});


        Notification notification = mBuilder.build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, notification);
    }


    public void reminderBigNotification(String title, String message, String url) {
        int m = (int)((new Date().getTime()/1000L) % Integer.MAX_VALUE);
        String newContent = message.replace("[fname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_FNAME_KEY))
                .replace("[mname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_MNAME_KEY)).replace("[lname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_LNAME_KEY))
                .replace("[dob]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_DOB_KEY)).replace("[age]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_AGE_KEY))
                .replace("[address]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_ADDRESS_KEY).replace("_"," ")).replace("[email]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_EMAIL_KEY));

        Intent intent1 =  new Intent(mCtx,HomeScreenActivity.class);
        intent1.putExtra("NOTIFY_ID",m);
        intent1.putExtra("title",title);
        intent1.putExtra("message",message);
        intent1.putExtra("url",url);
        intent1.putExtra("isSmall",false);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(mCtx.getApplicationContext());
        taskStackBuilder.addParentStack(HomeScreenActivity.class);
        taskStackBuilder.addNextIntent(intent1);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(m, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(newContent).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);

        mBuilder.setSmallIcon(R.mipmap.app_icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.mipmap.app_icon)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.reminder))
                .setContentText(newContent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[] {100, 1500, 500, 1500});

        Notification notification = mBuilder.build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, notification);
    }

    public void alertsSmallNotification(String title, String message) {
        int m = (int)((new Date().getTime()/1000L) % Integer.MAX_VALUE);
        String newContent = message.replace("[fname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_FNAME_KEY))
                .replace("[mname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_MNAME_KEY)).replace("[lname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_LNAME_KEY))
                .replace("[dob]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_DOB_KEY)).replace("[age]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_AGE_KEY))
                .replace("[address]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_ADDRESS_KEY).replace("_"," ")).replace("[email]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_EMAIL_KEY));
        Intent intent1 =  new Intent(mCtx,NotificationOpened.class);
        intent1.putExtra("NOTIFY_ID",m);
        intent1.putExtra("title",title);
        intent1.putExtra("message",message);
        intent1.putExtra("isSmall",true);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(mCtx.getApplicationContext());
        taskStackBuilder.addParentStack(NotificationOpened.class);
        taskStackBuilder.addNextIntent(intent1);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(m, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
//        Notification notification;
        mBuilder.setSmallIcon(R.mipmap.app_icon)
                .setTicker(title)
                .setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.nys_app_logo)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.inbox_colored))
                .setContentText(newContent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[] {100, 1500, 500, 1500});


        Notification notification = mBuilder.build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        android.app.NotificationManager notificationManager = (android.app.NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, notification);
    }


    public void alertsBigNotification(String title, String message, String url) {
        int m = (int)((new Date().getTime()/1000L) % Integer.MAX_VALUE);
        String newContent = message.replace("[fname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_FNAME_KEY))
                .replace("[mname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_MNAME_KEY)).replace("[lname]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_LNAME_KEY))
                .replace("[dob]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_DOB_KEY)).replace("[age]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_AGE_KEY))
                .replace("[address]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_ADDRESS_KEY).replace("_"," ")).replace("[email]",SharedPrefManager.getInstance(mCtx).appGetUserSaveData(USER_EMAIL_KEY));

        Intent intent1 =  new Intent(mCtx,NotificationOpened.class);
        intent1.putExtra("NOTIFY_ID",m);
        intent1.putExtra("title",title);
        intent1.putExtra("message",message);
        intent1.putExtra("url",url);
        intent1.putExtra("isSmall",false);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(mCtx.getApplicationContext());
        taskStackBuilder.addParentStack(NotificationOpened.class);
        taskStackBuilder.addNextIntent(intent1);

        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(m, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(newContent).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);

        mBuilder.setSmallIcon(R.mipmap.app_icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setContentTitle(title)
                .setStyle(bigPictureStyle)
                .setSmallIcon(R.mipmap.app_icon)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.inbox_colored))
                .setContentText(newContent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setVibrate(new long[] {100, 1500, 500, 1500});

        Notification notification = mBuilder.build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(m, notification);
    }*/


    //The method will return Bitmap from an image URL
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}