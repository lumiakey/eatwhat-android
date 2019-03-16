package com.what2e.eatwhat.tool;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class CheckNet {
    public final static int NET_NONE = 0;
    public final static int NET_WIFI = 1;
    public final static int NET_MOBILE = 2;
    public static int getNetState(Context context)
    {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo == null)
            return NET_NONE;
        int type = networkInfo.getType();
        if(type == ConnectivityManager.TYPE_MOBILE)
            return NET_MOBILE;
        else if(type == ConnectivityManager.TYPE_WIFI)
            return NET_WIFI;
        return NET_MOBILE;
    }
}
