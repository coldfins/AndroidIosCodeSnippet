package com.medical.firebase;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.medical.doctfin.R;
import com.medical.doctfin.UserHomeActivity;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by ved_pc on 7/6/2017.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "TTTT DoctFin";
    Random r = new Random();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Displaying data in log
        //It is optional
        Log.d(TAG, "remoteMessage: " + remoteMessage);
        Log.d(TAG, "From: " + remoteMessage.getFrom());
//        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());

        //Calling method to generate notification
        sendNotification(remoteMessage.getData());
    }

    //This method is only generating push notification
    //It is same as we did in earlier posts
    private void sendNotification(Map<String, String> messageBody) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
        boolean isActivityFound = false;

        if (services.get(0).topActivity.getPackageName().toString().equalsIgnoreCase(getPackageName().toString())) {
            isActivityFound = true;
        }

        if (isActivityFound) {
//            PendingIntent contentIntent = PendingIntent.getActivity(this, r.nextInt(), i, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentTitle("DoctFin")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody.get("doctAddressString")));

//            mBuilder.setContentIntent(contentIntent);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setAutoCancel(true);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(r.nextInt(), mBuilder.build());
        } else {
            Intent activityIntent = new Intent(this, UserHomeActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, r.nextInt(), activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.bestdoct_splash)
                    .setContentTitle("DoctFin")
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody.get("doctAddressString")));

            mBuilder.setContentIntent(contentIntent);
            mBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mBuilder.setAutoCancel(true);
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(r.nextInt(), mBuilder.build());
        }
    }
}
