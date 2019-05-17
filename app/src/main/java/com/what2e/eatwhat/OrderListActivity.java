package com.what2e.eatwhat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.what2e.eatwhat.bean.OrderList;

public class OrderListActivity extends AppCompatActivity {

    private BaseQuickAdapter<OrderList, BaseViewHolder> adapter;

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
                helper.setText(R.id.tv_price, "￥"+item.getOrderPrice());
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
        recyclerView.setAdapter(adapter);
    }
}
