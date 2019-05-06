package com.what2e.eatwhat.bean;

import java.util.List;

public class Food {

    /**
     * success : 1
     * code : 1000
     * msg : 获取菜单成功
     * result : {"foodList":[{"food_id":"1","food_name":"豆浆油条","timeTips":"早餐","foodPicture":"http://img4.imgtn.bdimg.com/it/u=2153937626,1074119156&fm=26&gp=0.jpg","foodDesc":"经典","foodPrice":"6.6"},{"food_id":"2","food_name":"农家小炒肉","timeTips":"晚餐","foodPicture":"http://img4.imgtn.bdimg.com/it/u=2153937626,1074119156&fm=26&gp=0.jpg","foodDesc":"经典湘菜，无辣不欢","foodPrice":"16.8"},{"food_id":"3","food_name":"皮蛋豆花","timeTips":"午餐","foodPicture":"http://img4.imgtn.bdimg.com/it/u=2153937626,1074119156&fm=26&gp=0.jpg","foodDesc":"经典","foodPrice":"9.9"}]}
     */

    private String success;
    private String code;
    private String msg;
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<FoodListBean> foodList;

        public List<FoodListBean> getFoodList() {
            return foodList;
        }

        public void setFoodList(List<FoodListBean> foodList) {
            this.foodList = foodList;
        }

        public static class FoodListBean {
            /**
             * food_id : 1
             * food_name : 豆浆油条
             * timeTips : 早餐
             * foodPicture : http://img4.imgtn.bdimg.com/it/u=2153937626,1074119156&fm=26&gp=0.jpg
             * foodDesc : 经典
             * foodPrice : 6.6
             */

            private String food_id;
            private String food_name;
            private String timeTips;
            private String foodPicture;
            private String foodDesc;
            private String foodPrice;
            private int count;

            public int getCount() {
                return count;
            }

            public void pushCount() {
                count++;
            }

            public void popCount() {
                if (count > 0) {
                    count--;
                }
            }

            public String getFood_id() {
                return food_id;
            }

            public void setFood_id(String food_id) {
                this.food_id = food_id;
            }

            public String getFood_name() {
                return food_name;
            }

            public void setFood_name(String food_name) {
                this.food_name = food_name;
            }

            public String getTimeTips() {
                return timeTips;
            }

            public void setTimeTips(String timeTips) {
                this.timeTips = timeTips;
            }

            public String getFoodPicture() {
                return foodPicture;
            }

            public void setFoodPicture(String foodPicture) {
                this.foodPicture = foodPicture;
            }

            public String getFoodDesc() {
                return foodDesc;
            }

            public void setFoodDesc(String foodDesc) {
                this.foodDesc = foodDesc;
            }

            public String getFoodPrice() {
                return foodPrice;
            }

            public void setFoodPrice(String foodPrice) {
                this.foodPrice = foodPrice;
            }
        }
    }
}