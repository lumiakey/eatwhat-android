package com.what2e.eatwhat.util;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.airsaid.pickerviewlibrary.CityPickerView;
import com.airsaid.pickerviewlibrary.listener.OnSimpleCitySelectListener;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.what2e.eatwhat.MainActivity;
import com.what2e.eatwhat.bean.Json;


import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AddressUtil {
    private static String locationCode = "00000";
    private static String locationName = "";




    public static void setLocationCode(String code) {
        locationCode = code;
    }

    public String getLocationCode(){
        return locationCode;
    }
}
