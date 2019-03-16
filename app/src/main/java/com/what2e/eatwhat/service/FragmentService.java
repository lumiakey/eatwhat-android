package com.what2e.eatwhat.service;

import com.what2e.eatwhat.bean.Food;
import com.what2e.eatwhat.bean.Information;

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
    public static Map<String,List> getFirstFragmentInfo(String locationCode, String time) {
        Map<String,List> firstFragmentInfo = new HashMap<String,List>();
        List<Food> foodList = new ArrayList<>();
        List<Information> informationList = new ArrayList<Information>();
        firstFragmentInfo.put("foodList",foodList);
        firstFragmentInfo.put("informationList",informationList);
        return firstFragmentInfo;
    }


}
