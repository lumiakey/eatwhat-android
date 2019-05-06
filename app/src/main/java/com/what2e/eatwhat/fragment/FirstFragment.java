package com.what2e.eatwhat.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.what2e.eatwhat.R;
import com.what2e.eatwhat.bean.Food;
import com.what2e.eatwhat.net.Api;
import com.what2e.eatwhat.util.Util;
import com.what2e.eatwhat.widget.ShoppingCountView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class FirstFragment extends Fragment {

    private RecyclerView recyclerView;
    private BaseQuickAdapter<Food.ResultBean.FoodListBean, BaseViewHolder> adapter;
    private BaseQuickAdapter<Food.ResultBean.FoodListBean, BaseViewHolder> bottomDialogAdapter;
    private TextView tvBottomCount;
    private TextView tvBottomPrice;

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
            Util.showToast(getContext(), "购物车");
            showBottomSheet();
        });

        adapter = new BaseQuickAdapter<Food.ResultBean.FoodListBean, BaseViewHolder>(R.layout.food_item, null) {
            @Override
            protected void convert(BaseViewHolder helper, Food.ResultBean.FoodListBean item) {
                helper.setText(R.id.tv_time_tips, item.getTimeTips());
                helper.setText(R.id.name, item.getFood_name());
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
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_sheet);
        RecyclerView recyclerView = bottomSheetDialog.findViewById(R.id.rv);
        tvBottomCount = bottomSheetDialog.findViewById(R.id.tv_count);
        tvBottomPrice = bottomSheetDialog.findViewById(R.id.price);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bottomDialogAdapter = new BaseQuickAdapter<Food.ResultBean.FoodListBean, BaseViewHolder>(R.layout.bootm_sheet_item, getSelect(adapter.getData())) {

            @Override
            protected void convert(BaseViewHolder helper, Food.ResultBean.FoodListBean item) {
                helper.setText(R.id.name, item.getFood_name());
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

    void updateBottomTotalPriceAndCount() {
        if (bottomDialogAdapter == null) return;
        tvBottomCount.setText(getCount(bottomDialogAdapter.getData()) + "");
        tvBottomPrice.setText(getTotalPrice(bottomDialogAdapter.getData()));
    }

    private String getTotalPrice(List<Food.ResultBean.FoodListBean> data) {
        float price = 0;
        for (Food.ResultBean.FoodListBean bean : data) {
            price += bean.getCount() * Float.parseFloat(bean.getFoodPrice());
        }
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        return decimalFormat.format(price);
    }

    private List<Food.ResultBean.FoodListBean> getSelect(List<Food.ResultBean.FoodListBean> data) {
        if (data == null) return null;
        List<Food.ResultBean.FoodListBean> lists = new ArrayList<>();
        for (Food.ResultBean.FoodListBean datum : data) {
            if (datum.getCount() > 0) {
                lists.add(datum);
            }
        }
        return lists;
    }

    private int getCount(List<Food.ResultBean.FoodListBean> data) {
        if (data == null) return 0;
        int count = 0;
        for (Food.ResultBean.FoodListBean datum : data) {
            count += datum.getCount();
        }
        return count;
    }

    @SuppressLint("CheckResult")
    private void getData() {
        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Api.api.fetchFoods("", time)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(food -> {
                    if (food == null) return false;
                    if ("1000".equals(food.getCode())) {
                        Food.ResultBean result = food.getResult();
                        if (result == null) return false;
                        List<Food.ResultBean.FoodListBean> foodList = result.getFoodList();
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
