package com.post.wall.wallpostapp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.post.wall.wallpostapp.LoginActivity;
import com.post.wall.wallpostapp.R;

import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by ved_pc on 2/17/2017.
 */

public class MyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("TTT", "Start Started");
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        String currentWifi = getCurrentSsid(this);
        Log.v("TTT", "Current WIFI..." + getCurrentSsid(this));
        if(currentWifi != null){
            sendNotification();
        }
        return super.onStartCommand(intent,flags,startId);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void sendNotification(){

        // Send Notification
        String notificationTitle = "WIFI";
        String notificationText = "Join Wifi";
//        final String myBlog = "http://www.cs.dartmouth.edu/~campbell/cs65/cs65.html";
//        Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(myBlog));
        Intent myIntent = new Intent(this, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, myIntent, Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification notification = new Notification.Builder(this)
                .setContentTitle(notificationTitle)
                .setContentText(notificationText).setSmallIcon(R.drawable.device_info)
                .setContentIntent(pendingIntent).build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notification.flags = notification.flags
                | Notification.FLAG_ONGOING_EVENT;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, notification);

    }


    public boolean isWifiEnable(){
        WifiManager wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()){
            return true;
        }else
            return false;
    }

    public String getCurrentSsid(Context context) {

        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !(connectionInfo.getSSID().equals(""))) {
                //if (connectionInfo != null && !StringUtil.isBlank(connectionInfo.getSSID())) {
                ssid = connectionInfo.getSSID();
            }
            // Get WiFi status MARAKANA
            WifiInfo info = wifiManager.getConnectionInfo();
            String textStatus = "";
            textStatus += "\n\nWiFi Status: " + info.toString();
            String BSSID = info.getBSSID();
            String MAC = info.getMacAddress();

            List<ScanResult> results = wifiManager.getScanResults();
            ScanResult bestSignal = null;
            int count = 1;
            String etWifiList = "";
            for (ScanResult result : results) {
                etWifiList += count++ + ". " + result.SSID + " : " + result.level + "\n" +
                        result.BSSID + "\n" + result.capabilities +"\n" +
                        "\n=======================\n";
            }
            Log.v("TTT", "from SO: \n"+etWifiList);

            // List stored networks
            List<WifiConfiguration> configs = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration config : configs) {
                textStatus+= "\n\n" + config.toString();
            }
            Log.v("TTT","from marakana: \n"+textStatus);
        }
        return ssid;
    }

}
