package com.what2e.eatwhat.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.what2e.eatwhat.util.Util;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NetworkTools {
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
    public static boolean checkNetwork(Context context) {
        if(getNetState(context) == NetworkTools.NET_NONE) {
            Log.e("网络状态：","网络不通");
            Util.showToast(context,"没网，完犊子");
            return false;
        }else {
            Log.e("网络状态：","网络畅通");
            Util.showToast(context,"网络畅通");
            return true;
        }
    }
}
