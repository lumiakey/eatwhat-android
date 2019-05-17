package com.what2e.eatwhat.bean;

import java.util.List;

public class Food {
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
    }
}