package com.medical.firebase;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by ved_pc on 7/6/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "TTTT DoctFin";

    @Override
    public void onTokenRefresh() {

        //Getting registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

//        Splash_screenActivity.regid = refreshedToken;
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);//getSharedPreferences("regpref", Context.MODE_PRIVATE);
//				pref = getPreferences(0);
        SharedPreferences.Editor edt = pref.edit();
        edt.putString("regid", refreshedToken);
        edt.commit();

        //Displaying token on logcat
        Log.d(TAG, "Refreshed token: " + refreshedToken);

    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}
