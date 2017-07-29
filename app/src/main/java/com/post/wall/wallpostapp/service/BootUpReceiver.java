package com.post.wall.wallpostapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.post.wall.wallpostapp.HomeActivity;
import com.post.wall.wallpostapp.utility.PreferenceManager;
import com.post.wall.wallpostapp.utility.Utility;

/**
 * Created by ved_pc on 2/17/2017.
 */

public class BootUpReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {

        if(!PreferenceManager.getJoin(context) || !PreferenceManager.getLogin(context) ){
            Log.d("WifiReceiver", "onReceive");
            ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conMan.getActiveNetworkInfo();
            if (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                String currentSSID = Utility.getCurrentSsid(context);
                Log.d("WifiReceiver", "Have Wifi Connection............."+ currentSSID );
                if(currentSSID != null){
                    Utility.sendNotification(context);
//                context.unregisterReceiver(this);
//                Utility.unregisterReceiverFromManifest(BootUpReceiver.this, context);
                }
            }
            else
                Log.d("WifiReceiver", "Don't have Wifi Connection");
        }else{
            try {
                Intent i = new Intent(context, HomeActivity.class);
                context.startActivity(i);
            }catch(Exception e){

            }

//            context.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        }






    }

}