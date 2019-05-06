package com.what2e.eatwhat.net;

import com.what2e.eatwhat.bean.Food;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    Api api = new Retrofit.Builder()
            .baseUrl("http://www.betayao.tech/")
            .client(new OkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.class);

    /**
     * 获取首页食物数据
     */
    @FormUrlEncoded
    @POST("firstFragment/getFoodList")
    Observable<Food> fetchFoods(@Field("locationCode") String locationCode, @Field("time") String time);
}
