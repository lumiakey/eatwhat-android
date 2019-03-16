package com.what2e.eatwhat;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;

import com.what2e.eatwhat.R;
import com.what2e.eatwhat.adapter.AppFragmentPageAdapter;
import com.what2e.eatwhat.fragment.FifthFragment;
import com.what2e.eatwhat.fragment.FirstFragment;
import com.what2e.eatwhat.fragment.FourthlyFragment;
import com.what2e.eatwhat.fragment.ScendFragment;
import com.what2e.eatwhat.fragment.ThirdlyFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private TextView firstText,scendText,thirdlyText,fourthlyText,fifthText;
    private ViewPager viewPager;            //实现滑动效果
    private ArrayList<Fragment> fragmentArrayList; //存放fragment
    private ArrayList<TextView> textViewArrayList; //存放textView
    private FragmentManager fragmentManager;    //管理fragment
    private int mDefaultColor= Color.BLACK;

    private int mActiveColor=Color.RED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();  //加载侧边栏
        initStaticView(); //加载静态资源


    }

    //加载侧边栏
    public void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle(" ");

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void initStaticView() {
        InitTextView();
        InitFragment();
        InitViewPager();
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
        viewPager = findViewById(R.id.mainViewPage) ;
        viewPager.setAdapter(new AppFragmentPageAdapter(fragmentManager, fragmentArrayList));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                for(TextView textView : textViewArrayList) {  //把未选中的设为默认颜色
                    textView.setTextColor(mDefaultColor);
                }
                textViewArrayList.get(i).setTextColor(mActiveColor);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
