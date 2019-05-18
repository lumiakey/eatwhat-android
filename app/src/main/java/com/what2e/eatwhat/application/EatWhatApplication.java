package com.what2e.eatwhat.application;

import android.content.Context;

import com.what2e.eatwhat.bean.User;
import com.what2e.eatwhat.util.AssetsCopyUtil;

import org.litepal.LitePalApplication;

/**
 * @author lumike
 * @version v1.0
 * @title EatWhat
 * @date 19-4-15 下午4:50
 * @Description application类
 **/
public class EatWhatApplication extends LitePalApplication {
    public String versionName;
    private User user;

    public static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        AssetsCopyUtil.copyEmbassy2Databases(this, "data/data/" + this.getPackageName() + "/databases/",
                "location.db");

        sContext = this;
    }
}
