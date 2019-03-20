package com.what2e.eatwhat.service;

import com.what2e.eatwhat.MainActivity;
import com.what2e.eatwhat.bean.Food;
import com.what2e.eatwhat.bean.Information;
import com.what2e.eatwhat.request.Request;
import com.what2e.eatwhat.request.RequestManager;
import com.what2e.eatwhat.util.AddressUtil;
import com.what2e.eatwhat.util.DateUtil;
import com.what2e.eatwhat.util.Util;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * FragmentService 用于加载各页的数据
 */
public class FragmentService {
    private static List<Food> foodList = new ArrayList<>();
    private static List<Information> informationList = new ArrayList<Information>();
    private static RequestManager requestManager = new Request();
    private static String requestUrl = Util.Url;
    private static Map<String,List> firstFragmentInfo;

    public static Map<String,List> getFirstFragmentInfo(String locationCode, String nowDate) {
        firstFragmentInfo = new HashMap<String,List>();
        try {
            firstFragmentInfo = requestManager.request(requestUrl+"first/init/getFirstFragmentInfo",
                    new TypeToken<Map<String,List>>(){}.getType(),
                    locationCode,
                    nowDate);
        }catch (Exception e) {
            e.printStackTrace();
        }
/*
        firstFragmentInfo.put("foodList",foodList);
        firstFragmentInfo.put("informationList",informationList);*/

        return firstFragmentInfo;
    }


}
