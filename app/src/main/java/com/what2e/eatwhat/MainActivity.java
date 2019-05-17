package com.what2e.eatwhat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.what2e.eatwhat.adapter.AppFragmentPageAdapter;
import com.what2e.eatwhat.base.BaseActivity;
import com.what2e.eatwhat.bean.Json;
import com.what2e.eatwhat.bean.User;
import com.what2e.eatwhat.fragment.FifthFragment;
import com.what2e.eatwhat.fragment.FirstFragment;
import com.what2e.eatwhat.fragment.FourthlyFragment;
import com.what2e.eatwhat.fragment.ScendFragment;
import com.what2e.eatwhat.fragment.ThirdlyFragment;
import com.what2e.eatwhat.service.UserService;
import com.what2e.eatwhat.tools.dialog.ProgressDialog;
import com.what2e.eatwhat.util.AddressUtil;
import com.what2e.eatwhat.util.GetJsonDataUtil;
import com.what2e.eatwhat.util.GetUserData;
import com.what2e.eatwhat.util.Util;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lumike
 * @version v1.0
 * @title MainActivity
 * @date 19-2-22
 * @Description 主activity
 **/

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView firstText, scendText, thirdlyText, fourthlyText, fifthText, loginRegisterTextView, userNameTextView;
    private ImageButton locationImageButton, loginRegistImageButtom;
    private Button settingButton;
    private ViewPager viewPager;            //实现滑动效果
    private NavigationView navigationView;
    private ArrayList<Fragment> fragmentArrayList; //存放fragment
    private ArrayList<TextView> textViewArrayList; //存放textView
    private FragmentManager fragmentManager;    //管理fragment
    private Thread thread;
    private String userName;
    private final String EXIT = "退出登录！";
    public List<Json> options1Items = new ArrayList<>();
    public ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    public ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private boolean isRefresh = true;//控制底部导航栏加载次数，否则再次刷新会重复
    private ProgressDialog progressDialog;

    public User user;
    public String userId, address, name, email, sex, avatar, phoneNumber;

    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private static boolean isLoaded = false;

    public int loginStatus = 0; //登录标志 0代表未登录 1代表已经登录

    private int mDefaultColor = Color.BLACK;
    private int mActiveColor = Color.RED;

    private final int requestCode_USERINFO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initNavView();  //加载侧边栏
        initStaticView(); //加载静态资源
        getIntentData();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        Util.showToast(MainActivity.this, "Begin Parse Data");
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 子线程中解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    Util.showToast(MainActivity.this, "Parse Succeed");
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    Util.showToast(MainActivity.this, "Parse Failed");
                    break;
            }
        }
    };

    //初始化 位置按钮
    public void initHeadMenu() {
        locationImageButton = findViewById(R.id.toolbar_button);
        locationImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoaded) {
                    showPickerView();
                } else {
                    Util.showToast(MainActivity.this, "Please waiting until the data is parsed");
                }
            }
        });

    }

    private void showPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String opt1tx = options1Items.size() > 0 ?
                        options1Items.get(options1).getPickerViewText() : "";

                String opt2tx = options2Items.size() > 0
                        && options2Items.get(options1).size() > 0 ?
                        options2Items.get(options1).get(options2) : "";

                String opt3tx = options2Items.size() > 0
                        && options3Items.get(options1).size() > 0
                        && options3Items.get(options1).get(options2).size() > 0 ?
                        options3Items.get(options1).get(options2).get(options3) : "";

                String tx = opt1tx + opt2tx + opt3tx;
                AddressUtil.setLocationCode(tx);
                Util.showToast(MainActivity.this, tx);
            }
        })

                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<Json> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> cityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String cityName = jsonBean.get(i).getCityList().get(c).getName();
                cityList.add(cityName);//添加城市
                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                province_AreaList.add(city_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList);

            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<Json> parseData(String jsonData) {//Gson 解析
        ArrayList<Json> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(jsonData);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                Json entity = gson.fromJson(data.optJSONObject(i).toString(), Json.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Util.showToast(this, "解析Json数据失败");
        }
        return detail;
    }

    //加载侧边栏
    public void initNavView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(" ");

        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //侧边导航栏
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadNavMentAndHeadView();
    }

    /**
     * 加载侧边的菜单按钮点击事件和头部登录点击事件
     */
    public void loadNavMentAndHeadView() {
        View headView = navigationView.getHeaderView(0);
        loginRegistImageButtom = (ImageButton) headView.findViewById(R.id.imageButton_Login);
        loginRegisterTextView = (TextView) headView.findViewById(R.id.textView_LoginAndRegist);
        userNameTextView = (TextView) headView.findViewById(R.id.textView_UserName);
        settingButton = (Button) headView.findViewById(R.id.button_Setting);
        //登录成功的状态
        if (loginStatus == 1) {
            if (!userName.isEmpty()) {
                userNameTextView.setText(userName);
            }
            loginRegisterTextView.setText(EXIT);
            userNameTextView.setOnClickListener(new View.OnClickListener() {
                //进入用户信息设置
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, UserInformationActivity.class);
                    intent.putExtra("id", userId);
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("phoneNumber", phoneNumber);
                    intent.putExtra("address", address);
                    intent.putExtra("sex", sex);
                    intent.putExtra("avatar", avatar);
                    startActivityForResult(intent, requestCode_USERINFO);
                }
            });
            loginRegistImageButtom.setOnClickListener(new View.OnClickListener() {
                //进入用户信息设置
                @Override
                public void onClick(View v) {

                }
            });
            loginRegisterTextView.setOnClickListener(new View.OnClickListener() {
                //退出登录
                @Override
                public void onClick(View v) {

                }
            });
        } else { //未登录
            loginRegistImageButtom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            loginRegisterTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            userNameTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        //设置
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.actionStart(MainActivity.this);
            }
        });

    }

    public void initStaticView() {
        InitTextView();
        InitFragment();
        InitViewPager();
        InitBaseDate(); //加载基础数据
        initHeadMenu(); //加载顶部菜单栏
    }

    public void InitBaseDate() {
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    public void InitTextView() {
        firstText = findViewById(R.id.textView_today);
        scendText = findViewById(R.id.textView_future);
        thirdlyText = findViewById(R.id.textView_recommend);
        fourthlyText = findViewById(R.id.textView_firstView);
        fifthText = findViewById(R.id.textView_foodTraceability);
        textViewArrayList = new ArrayList<TextView>();
        textViewArrayList.add(firstText);
        textViewArrayList.add(scendText);
        textViewArrayList.add(thirdlyText);
        textViewArrayList.add(fourthlyText);
        textViewArrayList.add(fifthText);
        textViewArrayList.get(0).setTextColor(mActiveColor);
    }

    public void InitFragment() {
        fragmentArrayList = new ArrayList<Fragment>();
        fragmentArrayList.add(new FirstFragment());
        fragmentArrayList.add(new ScendFragment());
        fragmentArrayList.add(new ThirdlyFragment());
        fragmentArrayList.add(new FourthlyFragment());
        fragmentArrayList.add(new FifthFragment());
        fragmentManager = getSupportFragmentManager();
    }

    public void InitViewPager() {
        viewPager = findViewById(R.id.mainViewPage);
        viewPager.setAdapter(new AppFragmentPageAdapter(fragmentManager, fragmentArrayList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for (TextView textView : textViewArrayList) {  //把未选中的设为默认颜色
                    textView.setTextColor(mDefaultColor);
                }
                textViewArrayList.get(i).setTextColor(mActiveColor);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


    /**
     * startActivityForResult(Intent intent, int requestCode)方法打开新的Activity，
     * 新的Activity 关闭后会向前面的Activity传回数据，为了得到传回的数据，必须在前面的Activity中
     * 重写onActivityResult(int requestCode, int resultCode, Intent data)方法。
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            /*case REQUEST_CODE_SCAN:
                // 扫描二维码/条码回传
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String content = data.getStringExtra(DECODED_CONTENT_KEY);
                        Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);
                        if (content.indexOf("-") != -1) {
                            String foodId = interceptString(content);//获得食物id
                            Activity_FoodsDetails.actionStart(context, Integer.parseInt(foodId.trim()));
                        } else {
                            final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Main.this);
                            builder.setTitle("请扫描懒人外卖专用二维码,你扫描到的内容是：");
                            builder.setCancelable(false);
                            builder.setMessage(content);
                            builder.setPositiveButton(android.R.string.ok,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog,
                                                            int which) {

                                        }
                                    });
                            builder.create().show();
                        }
                    }
                }
                break;*/
            case requestCode_USERINFO:
                if (resultCode == RESULT_OK) {
                    loginStatus = data.getIntExtra("statusCode", 0);
                    if (loginStatus != 0) {
                        //不刷新底部导航
                        isRefresh = false;
                        getData();
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 获取fragment数据
     */
    private void getData() {
        progressDialog.setMessage("数据加载中...");
        progressDialog.show();
        if (loginStatus == 1) {
            new Thread(getUserInfo).start();
        }
        new Thread(getFirstFragment).start();
        new Thread(getOtherFragment).start();
    }

    private Runnable getFirstFragment = new Runnable() {
        @Override
        public void run() {


        }
    };

    private Runnable getOtherFragment = new Runnable() {
        @Override
        public void run() {

        }
    };

    private Runnable getUserInfo = new Runnable() {
        @Override
        public void run() {
            try {
                user = UserService.getUserInfoForPhoneNumber(phoneNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_scan) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //订单
        if (id == R.id.nav_camera) {
            startActivity(new Intent(this, OrderListActivity.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * @param context
     * @param phoneNumber 电话号码
     * @param statusCode  登录状态码
     */
    public static void actionStart(Context context, String phoneNumber, int statusCode) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("phoneNumber", phoneNumber);
        intent.putExtra("statusCode", statusCode);
        context.startActivity(intent);
    }

    private void getIntentData() {
//        Intent intent = getIntent();
//        phoneNumber = intent.getStringExtra("phoneNumber");
//        statusCode = intent.getIntExtra("statusCode", -1);
        // 轻量级的存储类，用来保存应用的一些常用配置 getSharedPreferences()获取
        SharedPreferences preferences = getSharedPreferences("LoginInfo", Activity.MODE_PRIVATE);
        loginStatus = preferences.getInt("statusCode", 0);
        phoneNumber = preferences.getString("phoneNumber", "");

        GetUserData data = new GetUserData();
        user = data.getUser(MainActivity.this);
        userName = user.getName();
    }
}
