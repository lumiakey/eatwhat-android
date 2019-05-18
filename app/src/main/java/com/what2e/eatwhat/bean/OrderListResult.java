package com.what2e.eatwhat.bean;

import java.util.List;

public class OrderListResult {
    /**
     * success : 1
     * code : 1000
     * msg : 请求成功
     * result : [{"oderId":1,"orderType":1,"orderRemarks":"订单备注","orderPrice":"16","address":"贵州贵阳修文","createTime":"May 10, 2019 12:00:01 AM","orderDesc":[{"orderDescId":1,"orderId":1,"foodId":1,"foodName":"?????","foodPrice":"16","orderAmount":1}]},{"oderId":3,"orderType":1,"orderRemarks":"订单备注","orderPrice":"16","address":"贵州贵阳修文","createTime":"May 10, 2019 12:00:01 AM","orderDesc":[{"orderDescId":3,"orderId":3,"foodId":1,"foodName":"农家小炒肉","foodPrice":"16","orderAmount":1}]},{"oderId":4,"orderType":1,"orderRemarks":"订单备注","orderPrice":"16","address":"贵州贵阳修文","createTime":"May 10, 2019 12:00:01 AM","orderDesc":[{"orderDescId":4,"orderId":4,"foodId":1,"foodName":"农家小炒肉","foodPrice":"16","orderAmount":1}]},{"oderId":5,"orderType":1,"orderRemarks":"订单备注","orderPrice":"16","address":"贵州贵阳修文","createTime":"May 10, 2019 12:00:01 AM","orderDesc":[{"orderDescId":5,"orderId":5,"foodId":1,"foodName":"农家小炒肉","foodPrice":"16","orderAmount":1}]},{"oderId":6,"orderType":2,"orderRemarks":"订单备注","orderPrice":"16","address":"贵州贵阳修文","createTime":"May 10, 2019 12:00:01 AM","orderDesc":[{"orderDescId":6,"orderId":6,"foodId":1,"foodName":"农家小炒肉","foodPrice":"16","orderAmount":1},{"orderDescId":7,"orderId":6,"foodId":1,"foodName":"农家小炒肉","foodPrice":"16","orderAmount":1}]},{"oderId":7,"orderType":1,"orderRemarks":"订单备注","orderPrice":"16","address":"1","createTime":"May 10, 2019 12:00:01 AM","orderDesc":[{"orderDescId":8,"orderId":7,"foodId":1,"foodName":"农家小炒肉","foodPrice":"16","orderAmount":1}]},{"oderId":8,"orderType":1,"orderRemarks":"备注","orderPrice":"26.80","address":"地址","createTime":"May 18, 2019 2:55:51 PM","orderDesc":[{"orderDescId":9,"orderId":8,"foodId":1,"foodName":"豆浆油条","foodPrice":"6.9","orderAmount":1},{"orderDescId":10,"orderId":8,"foodId":1,"foodName":"水煮肉片","foodPrice":"19.9","orderAmount":1}]},{"oderId":9,"orderType":1,"orderRemarks":"备注","orderPrice":"26.80","address":"地址","createTime":"May 18, 2019 2:55:51 PM","orderDesc":[{"orderDescId":11,"orderId":9,"foodId":1,"foodName":"豆浆油条","foodPrice":"6.9","orderAmount":1},{"orderDescId":12,"orderId":9,"foodId":1,"foodName":"水煮肉片","foodPrice":"19.9","orderAmount":1}]},{"oderId":10,"orderType":1,"orderRemarks":"备注","orderPrice":"26.80","address":"地址","createTime":"May 18, 2019 2:55:51 PM","orderDesc":[{"orderDescId":13,"orderId":10,"foodId":1,"foodName":"豆浆油条","foodPrice":"6.9","orderAmount":1},{"orderDescId":14,"orderId":10,"foodId":1,"foodName":"水煮肉片","foodPrice":"19.9","orderAmount":1}]},{"oderId":11,"orderType":1,"orderRemarks":"备注","orderPrice":"26.80","address":"地址","createTime":"May 18, 2019 2:55:51 PM","orderDesc":[{"orderDescId":15,"orderId":11,"foodId":1,"foodName":"豆浆油条","foodPrice":"6.9","orderAmount":1},{"orderDescId":16,"orderId":11,"foodId":1,"foodName":"水煮肉片","foodPrice":"19.9","orderAmount":1}]},{"oderId":12,"orderType":1,"orderRemarks":"备注","orderPrice":"26.80","address":"地址","createTime":"May 18, 2019 2:55:51 PM","orderDesc":[{"orderDescId":17,"orderId":12,"foodId":1,"foodName":"豆浆油条","foodPrice":"6.9","orderAmount":1},{"orderDescId":18,"orderId":12,"foodId":1,"foodName":"水煮肉片","foodPrice":"19.9","orderAmount":1}]},{"oderId":13,"orderType":1,"orderRemarks":"备注","orderPrice":"26.80","address":"地址","createTime":"May 18, 2019 2:55:51 PM","orderDesc":[{"orderDescId":19,"orderId":13,"foodId":1,"foodName":"豆浆油条","foodPrice":"6.9","orderAmount":1},{"orderDescId":20,"orderId":13,"foodId":1,"foodName":"水煮肉片","foodPrice":"19.9","orderAmount":1}]}]
     */

    private String success;
    private String code;
    private String msg;
    private List<ResultBean> result;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
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
}
