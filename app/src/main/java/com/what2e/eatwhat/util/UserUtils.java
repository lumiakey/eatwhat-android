package com.what2e.eatwhat.util;

import android.app.Activity;
import android.content.SharedPreferences;

import com.what2e.eatwhat.application.EatWhatApplication;

public class UserUtils {
    private static SharedPreferences sharedPreferences;

    static {
        sharedPreferences = EatWhatApplication.sContext.getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE);
    }

    public static String getToken() {
        return sharedPreferences.getString("token", null);
    }

    public static void insert(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getUserId() {
        return sharedPreferences.getString("uid", "");
    }

    public static String getName() {
        return sharedPreferences.getString("uName", "用户名");
    }
}
