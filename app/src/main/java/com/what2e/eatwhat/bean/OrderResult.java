package com.what2e.eatwhat.bean;

public class OrderResult {
    /**
     * statusCode : 200
     * statusDescription : orderId:5
     */

    private int statusCode;
    private String statusDescription;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
