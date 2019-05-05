package com.ebiz.model;

/**
 * 通用数据类 用于服务端与客户端传输数据
 */
public class ResultData {
    /** 默认结果 */
    public static final ResultData SUCCESS = new ResultData(null, ResultState.SUCCESS);
    /** 返回数据 */
    private Object data;
    /** 状态码 */
    private String state;
    /** 状态信息 */
    private String message;

    public ResultData(Object data) {
        this.data = data;
        this.state = ResultState.SUCCESS.getState();
        this.message = ResultState.SUCCESS.getMessage();
    }

    public ResultData(ResultState state) {
        this.state = state.getState();
        this.message = state.getMessage();
    }

    public ResultData(Object data, ResultState state) {
        this.state = state.getState();
        this.message = state.getMessage();
        this.data = data;
    }

    public ResultData(Object data, String state) {
        this.data = data;
        this.state = state;
    }

    public ResultData(ResultState state, String message) {
        this.state = state.getState();
        this.message = message;
    }

    public ResultData(Object data, ResultState state, String message) {
        this.data = data;
        this.state = state.getState();
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
