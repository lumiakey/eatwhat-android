package com.what2e.eatwhat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.what2e.eatwhat.R;
import com.what2e.eatwhat.base.BasePermissionActivity;

public class WelcomeActive extends BasePermissionActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
        versionNumber.setText("吃啥" + getLocalVersionName(WelcomeActive.this));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                requestPermission(new String[]{
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                });
            }
        }, 2000);
    }

    /**
     * 获取本地软件版本号名称
     */
    public static String getLocalVersionName(Context context) {
        String localVersion = "";
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            localVersion = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return localVersion;
    }

    @Override
    protected void permissionSuccess() {
        skipActivity();
    }

    @Override
    protected void permissionFail() {
        finish();
    }

    private void skipActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
