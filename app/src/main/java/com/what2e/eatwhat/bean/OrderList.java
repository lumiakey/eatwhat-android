package com.what2e.eatwhat.bean;

import java.util.List;

public class OrderList {
    /**
     * oderId : 1
     * orderType : 1
     * orderRemarks : 订单备注
     * orderPrice : 16
     * address : 贵州贵阳修文
     * createTime : May 10, 2019 12:00:01 AM
     * orderDesc : [{"orderDescId":1,"orderId":1,"foodId":1,"foodName":"?????","foodPrice":"16","orderAmount":1}]
     */

    private int oderId;
    private int orderType;
    private String orderRemarks;
    private String orderPrice;
    private String address;
    private String createTime;
    private List<OrderDescBean> orderDesc;

    public int getOderId() {
        return oderId;
    }

    public void setOderId(int oderId) {
        this.oderId = oderId;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public String getOrderRemarks() {
        return orderRemarks;
    }

    public void setOrderRemarks(String orderRemarks) {
        this.orderRemarks = orderRemarks;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<OrderDescBean> getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(List<OrderDescBean> orderDesc) {
        this.orderDesc = orderDesc;
    }

    public static class OrderDescBean {
        /**
         * orderDescId : 1
         * orderId : 1
         * foodId : 1
         * foodName : ?????
         * foodPrice : 16
         * orderAmount : 1
         */

        private int orderDescId;
        private int orderId;
        private int foodId;
        private String foodName;
        private String foodPrice;
        private int orderAmount;

        public int getOrderDescId() {
            return orderDescId;
        }

        public void setOrderDescId(int orderDescId) {
            this.orderDescId = orderDescId;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getFoodId() {
            return foodId;
        }

        public void setFoodId(int foodId) {
            this.foodId = foodId;
        }

        public String getFoodName() {
            return foodName;
        }

        public void setFoodName(String foodName) {
            this.foodName = foodName;
        }

        public String getFoodPrice() {
            return foodPrice;
        }

        public void setFoodPrice(String foodPrice) {
            this.foodPrice = foodPrice;
        }

        public int getOrderAmount() {
            return orderAmount;
        }

        public void setOrderAmount(int orderAmount) {
            this.orderAmount = orderAmount;
        }
    }
}
