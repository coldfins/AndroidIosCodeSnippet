package com.post.wall.wallpostapp.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Checking internet Connectivity in device
 *
 */
public class checkInternetisAvailable {

	private static checkInternetisAvailable instance = new checkInternetisAvailable();
	static Context context;
	ConnectivityManager connectivityManager;
	NetworkInfo wifiInfo, mobileInfo;
	boolean connected = false;

	public static checkInternetisAvailable getInstance(Context ctx) {
		context = ctx;
		return instance;
	}

	/**
	 * check if internet is available or not in device
	 *	@return true or false based on internet connection
	 */
	public boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null)
				for (int i = 0; i < info.length; i++)
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	/**
	 * check if internet is ON then return true and if OFF then false
	 * @return true or false based on internet connection
	 */
	public Boolean check_internet() {
		if (isConnectingToInternet()) {
			return true;

		} else {
			// Internet connection is not present
			// Ask user to connect to Internet
			return false;
		}
	}

	/**
	 * check if internet is ON then return true and if OFF then false
	 * @return true or false based on network connection
	 */
	public boolean isOnline(Context con) {
		try {
			connectivityManager = (ConnectivityManager) con
					.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();
			connected = networkInfo != null && networkInfo.isAvailable()
					&& networkInfo.isConnected();
			return connected;

		} catch (Exception e) {
			System.out
					.println("CheckConnectivity Exception: " + e.getMessage());
			Log.v("connectivity", e.toString());
		}
		return connected;
	}
}