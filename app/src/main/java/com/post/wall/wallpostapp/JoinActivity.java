package com.post.wall.wallpostapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.post.wall.wallpostapp.utility.Constants;
import com.post.wall.wallpostapp.utility.PreferenceManager;
import com.post.wall.wallpostapp.utility.Utility;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by ved_pc on 2/17/2017.
 */

public class JoinActivity extends Activity{
    LinearLayout linearJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Log.d("WifiReceiver", PreferenceManager.getJoin(JoinActivity.this)  + "....JOin StartServiceActivity onReceive already login LOGIN...." + PreferenceManager.getLogin(JoinActivity.this));
        if(PreferenceManager.getJoin(JoinActivity.this) & PreferenceManager.getLogin(JoinActivity.this) ){
            Log.d("WifiReceiver", "StartServiceActivity onReceive already login");
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

            finish();
        }

        setContentView(R.layout.join_view);


       isWifiConnected();

        linearJoin = (LinearLayout) findViewById(R.id.linearJoin);
        linearJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isWifiConnected()){
                    PreferenceManager. putJoin(true, JoinActivity.this);
                    Log.v("TTT", "LoginActivity START");
                    Intent myIntent = new Intent(JoinActivity.this, LoginActivity.class);
                    startActivity(myIntent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }else{
                    Toast.makeText(JoinActivity.this, "Wifi not connected, Please connect wifi", Toast.LENGTH_SHORT).show();
                }


            }
        });

        PulsatorLayout pulsator = (PulsatorLayout)findViewById(R.id.pulsator);
        pulsator.start();

    }

    public boolean isWifiConnected(){

            int permission = Utility.checkExternalPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int permission1 = Utility.checkExternalPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (permission != PackageManager.PERMISSION_GRANTED && permission1 != PackageManager.PERMISSION_GRANTED) {
                Log.d("WifiReceiver", "StartServiceActivity onReceive permission not granted");
                ActivityCompat.requestPermissions(JoinActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, Constants.WRITE_EXTERNAL_REQUEST_CODE);
            } else {
                Log.d("WifiReceiver", "StartServiceActivity onReceive permission granted");
                ConnectivityManager conMan = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMan.getActiveNetworkInfo();
                if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    String currentSSID = Utility.getCurrentSsid(JoinActivity.this);
                    Log.d("WifiReceiver", "StartServiceActivity Have Wifi Connection............."+ currentSSID );
                    if(currentSSID != null){
                        //Utility.sendNotification(JoinActivity.this);
//                context.unregisterReceiver(this);
                        return true;
                    }
                }
                else
                    Log.d("WifiReceiver", "StartServiceActivity Don't have Wifi Connection");
            }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Log.d("WifiReceiver", "StartServiceActivity onRequestPermissionsResult permission............."+ requestCode );
        switch (requestCode) {
            case Constants.WRITE_EXTERNAL_REQUEST_CODE: {
                if (grantResults.length == 0 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i("TTTTTTT", "**************Permission has been denied by user");
                    Utility.toastExternalStorage(JoinActivity.this);
                } else {
                    Log.d("WifiReceiver", "isWifiConnected,,,...." + isWifiConnected());
                }

            }
            return;

        }
    }

}
