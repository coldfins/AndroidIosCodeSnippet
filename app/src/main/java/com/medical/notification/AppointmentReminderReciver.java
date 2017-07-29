package com.medical.notification;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.medical.doctfin.R;
import com.medical.doctfin.UserHomeActivity;

import java.util.List;
import java.util.Random;

public class AppointmentReminderReciver extends BroadcastReceiver {
	private String docNameString, doctAddressString, doctAppDateTimeString;
	Random r = new Random();

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle b = intent.getExtras();
		docNameString = b.getString("docNameString");
		doctAddressString = b.getString("doctAddressString");
		doctAppDateTimeString = b.getString("doctAppDateTimeString");
		showNotification(context, docNameString, doctAddressString, doctAppDateTimeString, intent);
	}

	private void showNotification(Context context, String doctName, String doctAddress, String doctAppDateTime, Intent i) {

		// Intent i = new Intent(context, NotificationReceiverPage.class);
		// i.putExtra("date", date);
		// i.putExtra("time", time);
		// i.putExtra("id", alid);
		// final int _id = (int) System.currentTimeMillis();

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> services = activityManager
				.getRunningTasks(Integer.MAX_VALUE);
		boolean isActivityFound = false;

		if (services.get(0).topActivity.getPackageName().toString()
				.equalsIgnoreCase(context.getPackageName().toString())) {
			isActivityFound = true;
		}

		if (isActivityFound) {
			// NotificationCompat.InboxStyle inboxStyle = new
			// NotificationCompat.InboxStyle();
			// String[] events = new String[2];
			// events[0] = new String(doctAddress);
			// events[1] = new String(doctAppDateTime);
			//
			// Intent activityIntent = new Intent(context,
			// UserHomeActivity.class);
			//
			// // Sets a title for the Inbox style big view
			// inboxStyle.setBigContentTitle("Appointment With " + doctName);
			//
			// // Moves events into the big view
			// for (int j = 0; j < events.length; j++) {
			// inboxStyle.addLine(events[j]);
			// }

			PendingIntent contentIntent = PendingIntent.getActivity(context,
					r.nextInt(), i, PendingIntent.FLAG_UPDATE_CURRENT);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					context)
					.setSmallIcon(R.drawable.app_icon)
					.setContentTitle("DoctFin")

					.setStyle(
							new NotificationCompat.BigTextStyle()
									.bigText(doctAddress));

			mBuilder.setContentIntent(contentIntent);
			mBuilder.setDefaults(Notification.DEFAULT_SOUND);
			mBuilder.setAutoCancel(true);
			NotificationManager mNotificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(r.nextInt(), mBuilder.build());
		} else {
			// write your code to build a notification.
			// return the notification you built here

			// NotificationCompat.InboxStyle inboxStyle = new
			// NotificationCompat.InboxStyle();
			//
			// String[] events = new String[2];
			// events[0] = new String(doctAddress);
			// events[1] = new String(doctAppDateTime);

			Intent activityIntent = new Intent(context, UserHomeActivity.class);

			// Sets a title for the Inbox style big view
			// inboxStyle.setBigContentTitle("Appointment With " + doctName);
			//
			// // Moves events into the big view
			// for (int j = 0; j < events.length; j++) {
			// inboxStyle.addLine(events[j]);
			// }

			PendingIntent contentIntent = PendingIntent.getActivity(context,
					r.nextInt(), activityIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);

			NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
					context)
					.setSmallIcon(R.drawable.bestdoct_splash)
					.setContentTitle("DoctFin")
					.setStyle(
							new NotificationCompat.BigTextStyle()
									.bigText(doctAddress));

			mBuilder.setContentIntent(contentIntent);
			mBuilder.setDefaults(Notification.DEFAULT_SOUND);
			mBuilder.setAutoCancel(true);
			NotificationManager mNotificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(r.nextInt(), mBuilder.build());
		}
	}

}
