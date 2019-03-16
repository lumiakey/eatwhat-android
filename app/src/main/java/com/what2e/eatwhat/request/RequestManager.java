package com.what2e.eatwhat.request;

import android.content.Context;
import android.view.View;

import com.what2e.eatwhat.bean.Food;

import java.lang.reflect.Type;
import java.util.List;

public interface RequestManager {
    //对象请求
    <T>T request(String url, Type type, String... params)throws Exception;
    //图片请求
    void getImage(Context context, View view, String url)throws Exception;
    //表单提交
    String postForm(String url, String... args)throws Exception;

    String postForms(String url, String ProductsName, List<Food> Products, String OrderAmountName, double OrderAmount, String CountName, int Count , String... args)throws Exception;
}
