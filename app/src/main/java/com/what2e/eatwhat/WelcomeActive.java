package com.what2e.eatwhat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.widget.TextView;

import com.what2e.eatwhat.application.EatWhatApplication;
import com.what2e.eatwhat.base.BasePermissionActivity;
import com.what2e.eatwhat.tools.NetworkTools;
import com.what2e.eatwhat.util.DialogButtonListener;
import com.what2e.eatwhat.util.Util;

public class WelcomeActive extends BasePermissionActivity {
    private IntentFilter intentFilter;
    private EatWhatApplication eatWhatApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        eatWhatApplication = (EatWhatApplication) getApplication();
        TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
        versionNumber.setText("吃啥" + getLocalVersionName(WelcomeActive.this));
        eatWhatApplication.versionName = getLocalVersionName(WelcomeActive.this);
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
        if (NetworkTools.checkNetwork(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Util.showDialog(this, "网络故障", "请检查网络设置", checkNetworkButtonListener);
        }

    }

    /**
     * 检查网络设置
     */
    private DialogButtonListener checkNetworkButtonListener = new DialogButtonListener() {
        @Override
        public void onDialogOkButtonClick() {
            Intent intent = new Intent(Settings.ACTION_SETTINGS);
            startActivity(intent);
        }

        @Override
        public void onDialogCancelButtonClick() {
            finish();
        }
    };


}
