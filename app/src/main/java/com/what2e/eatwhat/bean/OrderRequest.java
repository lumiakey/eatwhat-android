package com.what2e.eatwhat.bean;

import java.util.List;

public class OrderRequest {

    /**
     * userId : 1
     * orderRemarks : 订单备注
     * orderPrice : 16
     * addressId : 1
     * createTime : 2019-05-10 00:00:01
     * orderDesc : [{"foodId":1,"foodName":"农家小炒肉","foodPrice":"16","orderAmount":1}]
     */

    private String userId;
    private String orderRemarks;
    private String orderPrice;
    private String address;
    private String createTime;
    private List<OrderDescBean> orderDesc;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
         * foodId : 1
         * foodName : 农家小炒肉
         * foodPrice : 16
         * orderAmount : 1
         */

        private int foodId;
        private String foodName;
        private String foodPrice;
        private int orderAmount;

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
