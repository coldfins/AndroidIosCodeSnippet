package com.post.wall.wallpostapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.ViewTreeObserver;

import com.post.wall.wallpostapp.model.AlbumImages;
import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.ImageGridAdapter;
import com.post.wall.wallpostapp.utility.PreferenceManager;
import com.post.wall.wallpostapp.utility.Utility;

import java.util.ArrayList;

/**
 * Created by ved_pc on 2/17/2017.
 */

public class StartServiceActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("WifiReceiver", "Start Service");
//        startService(new Intent(StartServiceActivity.this, MyService.class));

        if(!PreferenceManager.getJoin(StartServiceActivity.this) || !PreferenceManager.getLogin(StartServiceActivity.this) ){
            Log.d("WifiReceiver", "StartServiceActivity onReceive Service start");

            int permission = Utility.checkExternalPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int permission1 = Utility.checkExternalPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission != PackageManager.PERMISSION_GRANTED && permission1 != PackageManager.PERMISSION_GRANTED) {
                Log.d("WifiReceiver", "StartServiceActivity onReceive permission not granted");
                ActivityCompat.requestPermissions(StartServiceActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, Constants.WRITE_EXTERNAL_REQUEST_CODE);
            } else {
                Log.d("WifiReceiver", "StartServiceActivity onReceive permission granted");
                ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMan.getActiveNetworkInfo();
                if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    String currentSSID = Utility.getCurrentSsid(StartServiceActivity.this);
                    Log.d("WifiReceiver", "StartServiceActivity Have Wifi Connection............."+ currentSSID );
                    if(currentSSID != null){
                        Utility.sendNotification(StartServiceActivity.this);
//                context.unregisterReceiver(this);
                    }
                }
                else
                    Log.d("WifiReceiver", "StartServiceActivity Don't have Wifi Connection");

                finish();
            }

        }else{
            Log.d("WifiReceiver", "StartServiceActivity onReceive already login");
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            finish();
        }



//        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("WifiReceiver", "StartServiceActivity onRequestPermissionsResult permission............."+ requestCode );
        switch (requestCode) {
            case Constants.WRITE_EXTERNAL_REQUEST_CODE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TTTTTTT", "**************Permission has been denied by user");
                    Utility.toastExternalStorage(StartServiceActivity.this);

                } else {
                    ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo netInfo = conMan.getActiveNetworkInfo();
                    if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                        String currentSSID = Utility.getCurrentSsid(StartServiceActivity.this);
                        Log.d("WifiReceiver", "StartServiceActivity Have Wifi Connection............."+ currentSSID );
                        if(currentSSID != null){
                            Utility.sendNotification(StartServiceActivity.this);
//                context.unregisterReceiver(this);
                        }
                    }
                    else
                        Log.d("WifiReceiver", "StartServiceActivity Don't have Wifi Connection");
                }


                finish();
            }
            return;


        }
    }

}
