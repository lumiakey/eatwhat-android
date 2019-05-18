package com.what2e.eatwhat.bean;

public class Food {
    /**
     * foodId : 2
     * foodName : 豆浆油条
     * foodPrice : 6.9
     * foodPicture : https://pic1.zhimg.com/v2-2f58a3a468eb3ec6573ab6af889f62bf_1200x500.jpg
     * timeTips : 早餐
     * foodDesc : 经典早餐
     */

    private int foodId;
    private String foodName;
    private String foodPrice;
    private String foodPicture;
    private String timeTips;
    private String foodDesc;

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

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture;
    }

    public String getTimeTips() {
        return timeTips;
    }

    public void setTimeTips(String timeTips) {
        this.timeTips = timeTips;
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc;
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