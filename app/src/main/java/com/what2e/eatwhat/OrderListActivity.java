package com.what2e.eatwhat;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.what2e.eatwhat.bean.BaseResult;
import com.what2e.eatwhat.bean.OrderList;
import com.what2e.eatwhat.net.Api;
import com.what2e.eatwhat.util.UserUtils;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderListActivity extends AppCompatActivity {

    private BaseQuickAdapter<OrderList, BaseViewHolder> adapter;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        findViewById(R.id.ll).setOnClickListener(v -> finish());
        RecyclerView recyclerView = findViewById(R.id.rv);
        adapter = new BaseQuickAdapter<OrderList, BaseViewHolder>(R.layout.order_list_item, null) {

            @Override
            protected void convert(BaseViewHolder helper, OrderList item) {
                helper.setText(R.id.tv_name, item.getOrderDesc().get(0).getFoodName() + "等 " + item.getOrderDesc().size() + "件商品");
                helper.setText(R.id.tv_price, "￥" + item.getOrderPrice());
                switch (item.getOrderType()) {
                    case 1:
                        helper.setText(R.id.tv_name, "待付款");
                        break;
                    case 2:
                        helper.setText(R.id.tv_name, "接单中");
                        break;
                    case 3:
                        helper.setText(R.id.tv_name, "烹饪中");
                        break;
                    case 4:
                        helper.setText(R.id.tv_name, "配送中");
                        break;
                    case 5:
                        helper.setText(R.id.tv_name, "待评论");
                        break;
                    case 6:
                        helper.setText(R.id.tv_name, "已完成");
                        break;
                    case 7:
                        helper.setText(R.id.tv_name, "已取消");
                        break;
                }
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        Api.api.getOrderList(UserUtils.getUserId(), UserUtils.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBaseResult -> {
                    if (listBaseResult == null || !"1000".equals(listBaseResult.getCode())) {
                        Toast.makeText(this, listBaseResult.getMsg(), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    adapter.setNewData(listBaseResult.getResult());
                }, throwable -> {
                    Toast.makeText(this, "网络异常", Toast.LENGTH_SHORT).show();
                    throwable.printStackTrace();
                });
    }
}
