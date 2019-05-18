package com.what2e.eatwhat.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.squareup.picasso.Picasso;
import com.what2e.eatwhat.MainActivity;
import com.what2e.eatwhat.OrderListActivity;
import com.what2e.eatwhat.R;
import com.what2e.eatwhat.bean.BaseResult;
import com.what2e.eatwhat.bean.Food;
import com.what2e.eatwhat.bean.OrderRequest;
import com.what2e.eatwhat.bean.OrderResult;
import com.what2e.eatwhat.net.Api;
import com.what2e.eatwhat.util.UserUtils;
import com.what2e.eatwhat.util.Util;
import com.what2e.eatwhat.widget.ShoppingCountView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class FirstFragment extends Fragment {

    private RecyclerView recyclerView;
    private BaseQuickAdapter<Food.FoodListBean, BaseViewHolder> adapter;
    private BaseQuickAdapter<Food.FoodListBean, BaseViewHolder> bottomDialogAdapter;
    private TextView tvBottomCount;
    private TextView tvBottomPrice;
    private boolean zhifubaoed;
    private String totalPrice;
    private AlertDialog payLoadDialog;
    private BottomSheetDialog bottomSheetDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.first_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(view1 -> {
            showBottomSheet();
        });

        adapter = new BaseQuickAdapter<Food.FoodListBean, BaseViewHolder>(R.layout.food_item, null) {
            @Override
            protected void convert(BaseViewHolder helper, Food.FoodListBean item) {
                helper.setText(R.id.tv_time_tips, item.getTimeTips());
                helper.setText(R.id.name, item.getFoodName());
                Picasso.with(helper.itemView.getContext())
                        .load(item.getFoodPicture())
                        .into((ImageView) helper.getView(R.id.food_picture));

                ShoppingCountView shoppingCountView = helper.getView(R.id.add_shopping_trolley);
                shoppingCountView.setShoppingCount(item.getCount());
                shoppingCountView.setOnShoppingClickListener(new ShoppingCountView.ShoppingClickListener() {
                    @Override
                    public void onAddClick(int num) {
                        item.pushCount();
                    }

                    @Override
                    public void onMinusClick(int num) {
                        item.popCount();
                    }
                });
            }
        };
        recyclerView.setAdapter(adapter);
        getData();
    }

    private void showBottomSheet() {
        final List<Food.FoodListBean> select = getSelect(adapter.getData());
        if (select == null || select.size() == 0) {
            Toast.makeText(getContext(), "请点餐", Toast.LENGTH_SHORT).show();
            return;
        }
        bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);
        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.rv);
        tvBottomCount = bottomSheetDialog.findViewById(R.id.tv_count);
        tvBottomPrice = bottomSheetDialog.findViewById(R.id.price);
        bottomSheetDialog.findViewById(R.id.jiesuan).setOnClickListener(v -> {
            List<Food.FoodListBean> data = getSelect(adapter.getData());
            if (data == null || data.size() == 0) {
                Toast.makeText(getContext(), "请点餐", Toast.LENGTH_SHORT).show();
                return;
            }
            pay(adapter.getData());
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bottomDialogAdapter = new BaseQuickAdapter<Food.FoodListBean, BaseViewHolder>(R.layout.bootm_sheet_item, select) {

            @Override
            protected void convert(BaseViewHolder helper, Food.FoodListBean item) {
                helper.setText(R.id.name, item.getFoodName());
                helper.setText(R.id.price, item.getFoodPrice());
                ShoppingCountView shoppingCountView = helper.getView(R.id.count);
                shoppingCountView.setShoppingCount(item.getCount());
                shoppingCountView.setOnShoppingClickListener(new ShoppingCountView.ShoppingClickListener() {
                    @Override
                    public void onAddClick(int num) {
                        item.pushCount();
                        updateBottomTotalPriceAndCount();
                    }

                    @Override
                    public void onMinusClick(int num) {
                        item.popCount();
                        updateBottomTotalPriceAndCount();
                    }
                });
            }
        };
        recyclerView.setAdapter(bottomDialogAdapter);
        updateBottomTotalPriceAndCount();
        bottomSheetDialog.show();
        bottomSheetDialog.setOnDismissListener(dialog -> adapter.notifyDataSetChanged());
    }

    private void pay(List<Food.FoodListBean> data) {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.pay_dialog, null);
        View zhifubao = inflate.findViewById(R.id.pay_zhifubao);
        View weixin = inflate.findViewById(R.id.pay_weixin);
        zhifubaoed = true;
        inflate.findViewById(R.id.zhifubao).setOnClickListener(v -> {
            zhifubaoed = true;
            zhifubao.setVisibility(View.VISIBLE);
            weixin.setVisibility(View.GONE);
        });

        inflate.findViewById(R.id.weixin).setOnClickListener(v -> {
            zhifubaoed = false;
            zhifubao.setVisibility(View.GONE);
            weixin.setVisibility(View.VISIBLE);
        });
        new AlertDialog.Builder(getContext())
                .setView(inflate)
                .setTitle("选择支付方式")
                .setNegativeButton("确定", (dialog, which) -> {
                    commitOrder(data, zhifubaoed ? 1 : 2);
                })
                .show();
    }

    /**
     * 提交订单
     *
     * @param data    订单数据
     * @param payType 支付方式
     */
    private void commitOrder(List<Food.FoodListBean> data, int payType) {
        OrderRequest order = new OrderRequest();
        order.setUserId(UserUtils.getUserId());
        order.setAddressId("地址");
        order.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        order.setOrderRemarks("备注");
        List<OrderRequest.OrderDescBean> orderDesc = new ArrayList<>();
        for (Food.FoodListBean datum : data) {
            OrderRequest.OrderDescBean orderDescBean = new OrderRequest.OrderDescBean();
            orderDescBean.setFoodId(datum.getCount());
            orderDescBean.setFoodName(datum.getFoodName());
            orderDescBean.setFoodPrice(datum.getFoodPrice());
            orderDescBean.setOrderAmount(datum.getCount());
        }
        order.setOrderDesc(orderDesc);
        order.setOrderPrice(totalPrice);
        payLoadDialog = new AlertDialog.Builder(getContext())
                .setView(R.layout.pay_loading)
                .setCancelable(false)
                .show();
        //提交订单
        Api.api.submitOrders(order, UserUtils.getToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(orderResultBaseResult -> {
                    if ("1000".equals(orderResultBaseResult.getCode())) {

                        //付款
                        Api.api.payment(UserUtils.getUserId(), payType, UserUtils.getToken())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(baseResult -> {
                                    payLoadDialog.dismiss();
                                    if ("1000".equals(baseResult.getCode())) {
                                        Toast.makeText(getContext(), "支付成功", Toast.LENGTH_SHORT).show();
                                        bottomSheetDialog.dismiss();
                                        startActivity(new Intent(getContext(), OrderListActivity.class));
                                    } else {
                                        Toast.makeText(getContext(), baseResult.getMsg(), Toast.LENGTH_LONG).show();
                                    }
                                }, throwable -> {
                                    payLoadDialog.dismiss();
                                    Toast.makeText(getContext(), "支付失败：网络异常", Toast.LENGTH_SHORT).show();
                                });

                    } else {
                        Toast.makeText(getContext(), orderResultBaseResult.getMsg(), Toast.LENGTH_LONG).show();
                        payLoadDialog.dismiss();
                    }
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getContext(), "网络异常", Toast.LENGTH_LONG).show();
                    payLoadDialog.dismiss();
                });
    }

    void updateBottomTotalPriceAndCount() {
        if (bottomDialogAdapter == null) return;
        tvBottomCount.setText(getCount(bottomDialogAdapter.getData()) + "");
        totalPrice = getTotalPrice(bottomDialogAdapter.getData());
        tvBottomPrice.setText(totalPrice);
    }

    private String getTotalPrice(List<Food.FoodListBean> data) {
        float price = 0;
        for (Food.FoodListBean bean : data) {
            price += bean.getCount() * Float.parseFloat(bean.getFoodPrice());
        }
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return decimalFormat.format(price);
    }

    private List<Food.FoodListBean> getSelect(List<Food.FoodListBean> data) {
        if (data == null) return null;
        List<Food.FoodListBean> lists = new ArrayList<>();
        for (Food.FoodListBean datum : data) {
            if (datum.getCount() > 0) {
                lists.add(datum);
            }
        }
        return lists;
    }

    private int getCount(List<Food.FoodListBean> data) {
        if (data == null) return 0;
        int count = 0;
        for (Food.FoodListBean datum : data) {
            count += datum.getCount();
        }
        return count;
    }

    @SuppressLint("CheckResult")
    private void getData() {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Api.api.fetchFoods("贵州贵阳", "2019-05-10 00:00:01")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(food -> {
                    if (food == null) return false;
                    if ("1000".equals(food.getCode())) {
                        Food result = food.getResult();
                        if (result == null) return false;
                        List<Food.FoodListBean> foodList = result.getFoodList();
                        if (foodList == null || foodList.size() == 0) {
                            throw new Exception("暂无数据");
                        }
                        return true;
                    } else {
                        throw new Exception(food.getMsg());
                    }
                })
                .map(food -> food.getResult().getFoodList())
                .subscribe(foodListBeans -> {
                    adapter.setNewData(foodListBeans);
                }, throwable -> {
                    throwable.printStackTrace();
                    Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
