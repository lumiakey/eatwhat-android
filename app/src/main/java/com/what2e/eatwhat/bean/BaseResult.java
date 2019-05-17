package com.what2e.eatwhat.bean;

public class BaseResult<DATA> {
    /**
     * success : 1
     * code : 1000
     * msg : 提交成功
     */

    private String success;
    private String code;
    private String msg;
    private DATA result;

    public DATA getResult() {
        return result;
    }

    public void setResult(DATA result) {
        this.result = result;
    }

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
}
