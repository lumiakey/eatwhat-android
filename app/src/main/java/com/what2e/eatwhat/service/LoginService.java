package com.what2e.eatwhat.service;

import android.util.Log;

import com.what2e.eatwhat.request.Request;
import com.what2e.eatwhat.request.RequestManager;
import com.what2e.eatwhat.util.Util;

/**
 * @author lumike
 */
public class LoginService {


    private static RequestManager requestManager = new Request();
    private static String requestURl = Util.Url;

    public static String checkAccount(String phoneNumber,String password) {
        String postForm = null;
        try {
            postForm = requestManager.postForm(
                    requestURl + "user/login",
                    "phoneNumber", phoneNumber,
                    "password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return postForm;
    }

}
