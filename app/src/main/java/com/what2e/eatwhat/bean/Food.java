package com.what2e.eatwhat.bean;

import java.util.Date;

public class Food {
    private Integer foodId;

    private String foodName;

    private String foodPrice;

    private String foodPicture;

    private Integer inventory;

    private String foodLocation;

    private String timeTips;

    private String foodDesc;

    private Integer foodStateFlag;

    private Date inTime;

    private Date putTime;

    private Date downTime;

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName == null ? null : foodName.trim();
    }

    public String getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(String foodPrice) {
        this.foodPrice = foodPrice == null ? null : foodPrice.trim();
    }

    public String getFoodPicture() {
        return foodPicture;
    }

    public void setFoodPicture(String foodPicture) {
        this.foodPicture = foodPicture == null ? null : foodPicture.trim();
    }

    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    public String getFoodLocation() {
        return foodLocation;
    }

    public void setFoodLocation(String foodLocation) {
        this.foodLocation = foodLocation == null ? null : foodLocation.trim();
    }

    public String getTimeTips() {
        return timeTips;
    }

    public void setTimeTips(String timeTips) {
        this.timeTips = timeTips == null ? null : timeTips.trim();
    }

    public String getFoodDesc() {
        return foodDesc;
    }

    public void setFoodDesc(String foodDesc) {
        this.foodDesc = foodDesc == null ? null : foodDesc.trim();
    }

    public Integer getFoodStateFlag() {
        return foodStateFlag;
    }

    public void setFoodStateFlag(Integer foodStateFlag) {
        this.foodStateFlag = foodStateFlag;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    public Date getPutTime() {
        return putTime;
    }

    public void setPutTime(Date putTime) {
        this.putTime = putTime;
    }

    public Date getDownTime() {
        return downTime;
    }

    public void setDownTime(Date downTime) {
        this.downTime = downTime;
    }
}