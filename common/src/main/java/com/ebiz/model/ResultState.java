package com.ebiz.model;

/**
 * 结果状态枚举类
 */
public enum ResultState {
    /** 成功 */
    SUCCESS("000000", "OK"),
    /** 失败 */
    BIZ_FAIL("000001", "业务执行失败"),
    /** 失败 */
    FAIL("999999", "ERROR"),
    AUTHENTICATION("100000", "权限验证失败！"),
    /** 需要登录 */
    REQUIRED_LOGIN("100001", "请登录！"),
    REQUIRED_ACTIVE("100002", "请联系管理员激活！"),
    ALREADY_LOGIN("100003", "用户已登录！");
    /** 状态 */
    private String state;
    /** 状态信息 */
    private String message;

    ResultState(String state, String message) {
        this.state = state;
        this.message = message;
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
