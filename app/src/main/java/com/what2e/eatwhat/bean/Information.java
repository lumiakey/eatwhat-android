package com.what2e.eatwhat.bean;

public class Information {
    public String getInfoPicture() {
        return infoPicture;
    }

    public void setInfoPicture(String infoPicture) {
        this.infoPicture = infoPicture;
    }

    public String getInfoUrl() {
        return infoUrl;
    }

    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    String infoPicture; //消息图片
    String infoUrl;//消息链接
}
