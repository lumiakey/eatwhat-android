package com.what2e.eatwhat.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.what2e.eatwhat.bean.User;


/**
 * Created by xch on 2017/5/17.
 */

public class GetUserData {

    private Integer userId;
    private String  name, validity , uPicture , sex,  phoneNumber;
    private int statusCode;
    private SharedPreferences sharedPreferences;
    public User user = new User();

    public User getUser(Context context) {

        //读取xml文件到
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        userId = sharedPreferences.getInt("userId", 0);
        name = sharedPreferences.getString("name", "");
        uPicture = sharedPreferences.getString("uPicture", "");
        sex = sharedPreferences.getString("sex", "");
        validity = sharedPreferences.getString("validity", "");
        phoneNumber = sharedPreferences.getString("phoneNumber", "");

        if (sharedPreferences != null) {
            user.setuId(userId);
            user.setuName(name);
            user.setPhonenumber(phoneNumber);
            user.setuPicture(uPicture);
            user.setValidity(validity);
            user.setSex(sex);
        }

        return user;
    }

    public int getStatusCode(Context context) {
        statusCode = 0;
        //读取xml文件到
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        statusCode = sharedPreferences.getInt("statusCode", 0);
        return statusCode;
    }
}
