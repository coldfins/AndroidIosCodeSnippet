package com.medical.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserSharedPreference {
	private SharedPreferences sharedpreferences;
	private Context context;
	private int PRIVATE_MODE = 0;
	private Editor editor;
	public static final String IS_USER_LOGIN = "IsUserLoggedIn";

	public boolean checkUserLogin() {
		if (!this.isUserLoggedIn()) {
			return false;
		}
		return true;

	}

	public UserSharedPreference(Context context) {
		super();
		this.context = context;
		sharedpreferences = context.getSharedPreferences(
				TagClass.UserArrayList, PRIVATE_MODE);
		editor = sharedpreferences.edit();
	}

	public boolean isUserLoggedIn() {
		return sharedpreferences.getBoolean(IS_USER_LOGIN, false);
	}

	public void createUserLoginSession(String userModelString) {
		// Storing login value as TRUE
		editor.putBoolean(IS_USER_LOGIN, true);

		// Storing name in preferences
		editor.putString(TagClass.UserArrayList, userModelString);

		// Storing email in preferences

		// commit changes
		editor.commit();
	}

}
