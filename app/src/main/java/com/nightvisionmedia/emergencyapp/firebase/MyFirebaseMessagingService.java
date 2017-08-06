package com.nightvisionmedia.emergencyapp.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.nightvisionmedia.emergencyapp.utils.NotificationManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Omar (GAZAMAN) Myers on 6/16/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "FROM: "+remoteMessage.getFrom());

        //Check if message contains data
//        if(remoteMessage.getData().size() > 0){
//            Log.d(TAG, "Message data: "+remoteMessage.getData());
//        }

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
        //Check if message contains notification
//        if(remoteMessage.getNotification() != null){
//            Log.d(TAG, "Message Body: "+remoteMessage.getNotification().getBody());
//            sendNotification(remoteMessage.getNotification().getBody());
//        }
    }

    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");
            String notifyType = data.getString("type");


            if(notifyType.equals("alert")){
                //creating MyNotificationManager object
                NotificationManager mNotificationManager = new NotificationManager(getApplicationContext());

                //creating an intent for the notification
//            Intent intent = new Intent(getApplicationContext(), YuteTalkZone.class);

                //if there is no image
                if(imageUrl.equals("null") || imageUrl.equals("")){
                    //displaying small notification
                    mNotificationManager.alertsSmallNotification(title, message);
                }else{
                    //if there is an image
                    //displaying a big notification
                    mNotificationManager.alertsBigNotification(title, message,imageUrl);
                }


            }/*else if(notifyType.equals("reminder")){


                //creating MyNotificationManager object
                MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

                //creating an intent for the notification
//            Intent intent = new Intent(getApplicationContext(), YuteTalkZone.class);

                //if there is no image
                if(imageUrl.equals("null")){
                    //displaying small notification
                    mNotificationManager.reminderSmallNotification(title, message);
                }else{
                    //if there is an image
                    //displaying a big notification
                    mNotificationManager.reminderBigNotification(title, message, imageUrl);
                }


            }else if(notifyType.equals("alert")){
                //creating MyNotificationManager object
                MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

                //creating an intent for the notification
//            Intent intent = new Intent(getApplicationContext(), YuteTalkZone.class);

                //if there is no image
                if(imageUrl.equals("null")){
                    //displaying small notification
                    mNotificationManager.alertsSmallNotification(title, message);
                }else{
                    //if there is an image
                    //displaying a big notification
                    mNotificationManager.alertsBigNotification(title, message, imageUrl);
                }
            }*/

        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    //Display the notification
//    private void sendNotification(String body) {
//        Intent intent = new Intent(this, HomeScreenActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0/*request code*/ , intent, PendingIntent.FLAG_ONE_SHOT);
//
//        //Set sound of notification
//        Uri notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notifiBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle("Firebase Cloud Messaging")
//                .setContentText(body)
//                .setAutoCancel(true)
//                .setSound(notificationSound)
//                .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(0/*ID of notification*/, notifiBuilder.build());
//    }
}
