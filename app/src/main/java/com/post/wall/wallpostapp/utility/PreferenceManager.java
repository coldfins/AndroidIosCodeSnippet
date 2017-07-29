package com.post.wall.wallpostapp.utility;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ved_pc on 2/18/2017.
 */

public class PreferenceManager {

    public static void putJoin(boolean value, Context context) {
        SharedPreferences prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isJoin", value);
        editor.commit();
    }

    public static boolean getJoin(Context context) {
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("isJoin", false);
    }

    public static void putLogin(boolean value, Context context) {
        SharedPreferences prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("Login", value);
        editor.commit();
    }

    public static boolean getLogin(Context context) {
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean("Login", false);
    }

    public static void putLoggedUser(String value, Context context) {
        SharedPreferences prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LoggedUser", value);
        editor.commit();
    }

    public static String getLoggedUser(Context context) {
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("LoggedUser", null);
    }

}
