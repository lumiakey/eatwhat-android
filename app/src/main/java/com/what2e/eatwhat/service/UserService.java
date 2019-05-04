package com.what2e.eatwhat.service;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.what2e.eatwhat.bean.User;
import com.what2e.eatwhat.request.RequestManager;
import com.what2e.eatwhat.util.Util;
import com.what2e.eatwhat.request.Request;
import java.util.List;

/**
 * @author lumike
 * @version v1.0
 * @title UserService
 * @date 19-4-16 上午1:30
 * @Description 用户service 管理获取、修改用户信息
 **/
public class UserService {
    private static User user;
    private static String requestURL = Util.Url;
    private static RequestManager requestManager = new Request();

    public static User getUserInfoForPhoneNumber(String phoneNumber) {
        try {
            Log.e("UserService",requestURL+"User/getUserInfo?PhoneNumber="+phoneNumber);
            user = requestManager.request(requestURL + "User/getUserInfo?PhoneNumber="
                    + phoneNumber, new TypeToken<User>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}
