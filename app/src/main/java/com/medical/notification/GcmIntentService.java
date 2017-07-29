package com.medical.notification;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.medical.doctfin.R;
import com.medical.doctfin.UserHomeActivity;

import java.util.List;

public class GcmIntentService extends IntentService {

	public static final int NOTIFICATION_ID = 1;
	private NotificationManager mNotificationManager;
	private final static String TAG = "GcmIntentService";

	// @Override
	// public void onMessageReceived(String from, Bundle data) {
	// // TODO Auto-generated method stub
	// String message = data.getString("message");
	// Log.i("****bundle data", data+"");
	// String alert=data.getString("registration_id");
	// Log.i("****bundle regid", alert+"");
	// super.onMessageReceived(from, data);
	// }

	public GcmIntentService() {
		super(GcmIntentService.class.getSimpleName());
	}

	//
	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle extras = intent.getExtras();
		Log.d(TAG, "PatientNotification Data Json :" + extras.getString("message"));

		GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
		String messageType = gcm.getMessageType(intent);
		Log.d(TAG, "MessageType:" + messageType);

		if (!extras.isEmpty()) {
			if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
				sendNotification("Send error: " + extras.toString(), intent);
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
				sendNotification("Deleted messages on server: " + extras.toString(), intent); // If it's a regular GCM message,
				// do some work.
			} else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

				// This loop represents the service doing some work.
				for (int i = 0; i < 5; i++) {
					Log.d(TAG, " Working... " + (i + 1) + "/5 @ " + SystemClock.elapsedRealtime());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
				Log.i(TAG, "Completed work @ " + SystemClock.elapsedRealtime());
				sendNotification(extras.getString("message"), intent);
			}
		} // Release the wake lock provided by the WakefulBroadcastReceiver.
		GcmBroadcastReceiver.completeWakefulIntent(intent);
	} // Put the message into a notification and post it.

	// This is just one simple example of what you might choose to do with
	// a GCM message.
	private void sendNotification(String msg, Intent i) {

		ActivityManager activityManager = (ActivityManager) getApplicationContext()
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> services = activityManager
				.getRunningTasks(Integer.MAX_VALUE);
		boolean isActivityFound = false;

		if (services.get(0).topActivity
				.getPackageName()
				.toString()
				.equalsIgnoreCase(
						getApplicationContext().getPackageName().toString())) {
			isActivityFound = true;
		}

		if (isActivityFound) {
			mNotificationManager = (NotificationManager) this
					.getSystemService(Context.NOTIFICATION_SERVICE);
			// PendingIntent contentIntent = PendingIntent.getActivity(this,
			// 0,i, 0);

			PendingIntent contentIntent = PendingIntent.getActivity(this, 0, i,
					PendingIntent.FLAG_UPDATE_CURRENT);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					this)
					.setSmallIcon(R.drawable.app_icon)
					.setContentTitle("DoctFin")
					.setStyle(
							new NotificationCompat.BigTextStyle().bigText(msg))
					.setContentText(msg)
					.setDefaults(
							Notification.DEFAULT_SOUND
									| Notification.DEFAULT_VIBRATE);

			mBuilder.setContentIntent(contentIntent);
			mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		} else {
			mNotificationManager = (NotificationManager) this
					.getSystemService(Context.NOTIFICATION_SERVICE);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
					new Intent(this, UserHomeActivity.class), 0);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					this)
					.setSmallIcon(R.drawable.app_icon)
					.setContentTitle("DoctFin")
					.setStyle(
							new NotificationCompat.BigTextStyle().bigText(msg))
					.setContentText(msg)
					.setDefaults(
							Notification.DEFAULT_SOUND
									| Notification.DEFAULT_VIBRATE);

			mBuilder.setContentIntent(contentIntent);
			mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
		}

	}

}
