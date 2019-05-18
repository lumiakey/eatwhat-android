package com.what2e.eatwhat.net;

import com.what2e.eatwhat.bean.BaseResult;
import com.what2e.eatwhat.bean.Food;
import com.what2e.eatwhat.bean.Information;
import com.what2e.eatwhat.bean.OrderList;
import com.what2e.eatwhat.bean.OrderRequest;
import com.what2e.eatwhat.bean.OrderResult;
import com.what2e.eatwhat.bean.User;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
     * 获取用户信息
     */
    @FormUrlEncoded
    @POST("user/getUserInfo")
    Observable<BaseResult<User>> getUserInfo(@Field("locationCode") String phonenumber, @Field("token") String token);

    /**
     * 获取信息列表
     */
    @FormUrlEncoded
    @POST("information/getInformationList")
    Observable<BaseResult<Information>> getInformation(@Field("locationCode") String locationCode, @Field("time") String time);


    /**
     * 获取首页食物数据
     */
    @FormUrlEncoded
    @POST("food/getFoodList")
    Observable<BaseResult<List<Food>>> fetchFoods(@Field("locationCode") String locationCode, @Field("time") String time);

    /**
     * 提交订单
     */
    @FormUrlEncoded
    @POST("order/submitOrders")
    Observable<BaseResult<OrderResult>> submitOrders(@Field("order") String order, @Field("token") String token);

    /**
     * 付款
     */
    @FormUrlEncoded
    @POST("/order/payment")
    Observable<BaseResult> payment(@Field("orderId") String orderId, @Field("paymentType") int paymentType, @Field("token") String token);

    /**
     * 获取订单列表
     */
    @FormUrlEncoded
    @POST("order/getOrderList")
    Observable<BaseResult<List<OrderList>>> getOrderList(@Field("userId") String userId, @Field("token") String token);
}
