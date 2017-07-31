package utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckInternetisAvailable {

    static Context context;
    private static CheckInternetisAvailable instance = new CheckInternetisAvailable();

    public static CheckInternetisAvailable getInstance(Context ctx) {
        context = ctx;
        return instance;
    }

    public boolean isConnectingToInternet() {
        if (context != null) {

            ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null)
                    for (int i = 0; i < info.length; i++)
                        if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }

            }
        }

        return false;
    }

    public Boolean check_internet() {
        if (isConnectingToInternet()) {
            return true;

        } else {
            return false;
        }
    }
}