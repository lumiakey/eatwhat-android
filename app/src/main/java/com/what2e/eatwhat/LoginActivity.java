package com.what2e.eatwhat;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.what2e.eatwhat.base.ActivityCollector;
import com.what2e.eatwhat.base.BaseActivity;
import com.what2e.eatwhat.bean.Food;
import com.what2e.eatwhat.bean.LoginStatus;
import com.what2e.eatwhat.bean.RequestResult;
import com.what2e.eatwhat.bean.User;
import com.what2e.eatwhat.net.Api;
import com.what2e.eatwhat.service.LoginService;
import com.what2e.eatwhat.tools.VerificationCode;
import com.what2e.eatwhat.util.Util;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lumike
 * @version v1.0
 * @title LoginActivity
 * @date 19-4-14 下午10:50
 * @Description 登录activity
 **/
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Handler loginHandler = new Handler();
    private Button btn_Exchange;
    private ImageView Image_Code;
    private ProgressDialog progress;
    private TextView other_login, tv_register, tv_forget_password;
    private Button login;
    private EditText etPhoneNumber, etPwd, VF_Code;
    private String phoneNumber, password,token;
    private User user;

    private int statusCode = 0;
    String postForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        other_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        login.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
    }

    private void initView() {
        etPwd = (EditText) findViewById(R.id.etPassword);
        etPhoneNumber = (EditText) findViewById(R.id.etUserName);
        other_login = (TextView) findViewById(R.id.other_login);
        tv_register = (TextView) findViewById(R.id.register);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        login = (Button) findViewById(R.id.login);
        VF_Code = (EditText) findViewById(R.id.Code);
        Image_Code = (ImageView) findViewById(R.id.Image_Code);
        btn_Exchange = (Button) findViewById(R.id.Exchange);
        btn_Exchange.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        Image_Code.setImageBitmap(VerificationCode.getVerificationCode());

        btn_Exchange.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Image_Code.setImageBitmap(VerificationCode
                        .getVerificationCode());
            }
        });

        Image_Code.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Image_Code.setImageBitmap(VerificationCode
                        .getVerificationCode());
            }
        });


        SharedPreferences preferences = getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE);
        String phone = preferences.getString("phoneNumber", "");
        String pwd = preferences.getString("password", "");
        if (phone != null && !phone.isEmpty()) {
            etPhoneNumber.setText(phone);
        }
        if (pwd != null && !pwd.isEmpty()) {
            etPwd.setText(pwd);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.other_login:
                /*menuWindow = new BottomMenu(Activity_Login.this, clickListener);
                menuWindow.show();*/
                break;
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_forget_password:
                Util.showToast(getApplication(), "敬请期待...");
                break;
            case R.id.login:
                progress = ProgressDialog.show(this, "请稍候", "正在登录...", true);
                progress.setCancelable(false);
                if (VerificationCode.checkVerificationCode(VF_Code.getText()
                        .toString())) {
                    phoneNumber = etPhoneNumber.getText().toString();
                    password = etPwd.getText().toString();
                    if (!Util.checkNetwork(this)) {
                        progress.dismiss();
                        return;
                    }
                    if (phoneNumber.toString().length() == 0) {
                        progress.dismiss();
                        Util.showToast(LoginActivity.this, "您的手机号码不能为空，请注意输入!");
                        return;
                    } else if (password.toString().length() == 0) {
                        progress.dismiss();
                        Util.showToast(LoginActivity.this, "您的密码不能为空，请注意输入!");
                        return;
                    } else if (password.toString().length() < 6) {
                        progress.dismiss();
                        Util.showToast(LoginActivity.this, "您的密码位数不能少于6位");
                        return;
                    } else if (password.toString().length() > 16) {
                        progress.dismiss();
                        Util.showToast(LoginActivity.this, "您的密码位数不能多于16位");
                        return;
                    } else if(phoneNumber.toString().length() == 0 && password.toString().length() == 0){
                        progress.dismiss();
                        Util.showToast(LoginActivity.this, "您的账户和密码不能为空，请注意输入!");
                        return;
                    }else {
                        login();
                    }
                } else {
                    progress.dismiss();
                    Toast.makeText(LoginActivity.this, "验证码错误，请重新输入！",
                            Toast.LENGTH_LONG).show();
                    Image_Code.setImageBitmap(VerificationCode
                            .getVerificationCode());
                    VF_Code.setText("");
                }
                break;
            default:
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        new Thread() {
            @Override
            public void run() {
                try {
                    postForm = LoginService.checkAccount(phoneNumber, password);//返回state_code
                    loginHandler.post(runnableLogin);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Runnable runnableLogin = new Runnable() {

        @Override
        public void run() {
            Gson gson = new Gson();
            if (postForm == null || postForm.isEmpty() || postForm.length() > 1000) {
                Util.showToast(LoginActivity.this, "服务器出错，请求失败！");
                progress.dismiss();
                return;
            } else {
                RequestResult requestResult = gson.fromJson(postForm, RequestResult.class);
                LoginStatus loginStatus = gson.fromJson(requestResult.getResult().toString(), LoginStatus.class);
                statusCode = loginStatus.getStatusCode();
                token = loginStatus.getStatusDescription();
                if (statusCode == 200) {
                    //获取用户信息
                    SharedPreferences sharedPreferences = getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editors = sharedPreferences.edit();//获取编辑器
                    editors.putString("phoneNumber", phoneNumber);
                    editors.putString("password", password);
                    editors.putString("token", token);
                    editors.putInt("statusCode", statusCode);
                    editors.commit();//提交修改
                    MainActivity.actionStart(LoginActivity.this, phoneNumber, statusCode);
                }
                Util.showToast(LoginActivity.this, "登陆成功");
            }
            progress.dismiss();
        }
    };

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
           /* switch (v.getId()) {
                case R.id.btn_wxLogin://微信登录
                    Util.showToast(getApplication(), "敬请期待...");
                    break;
                case R.id.btn_msgCheckLogin://短信验证码登录
                    Util.showToast(getApplication(), "敬请期待...");
                    break;
                default:
                    break;
            }*/
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果是返回键
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            ActivityCollector.finishAll();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
