package com.housing.housecheap.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkConnection{
	
	public boolean isNetworkAvailable(Context ctx) {
	    ConnectivityManager connectivityManager  = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

}
