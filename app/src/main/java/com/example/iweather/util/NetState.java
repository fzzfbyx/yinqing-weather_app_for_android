package com.example.iweather.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetState {
    public static boolean isNetworkAvailable(Context paramContext) {
        ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService(paramContext.CONNECTIVITY_SERVICE);
        if(localConnectivityManager != null) {
            NetworkInfo[] arrayOfNetworkInfo = localConnectivityManager.getAllNetworkInfo();
            if(arrayOfNetworkInfo != null) {
                int j = arrayOfNetworkInfo.length;

                for(int k = 0; k < j; ++k) {
                    if(arrayOfNetworkInfo[k].getState() == NetworkInfo.State.CONNECTED
                            && !arrayOfNetworkInfo[k].getTypeName().equals("VPN")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
