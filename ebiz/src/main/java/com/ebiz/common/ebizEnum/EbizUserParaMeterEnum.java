package com.ebiz.common.ebizEnum;

/**
 * 用户登录的基本信息
 */
public enum EbizUserParaMeterEnum {
    //UID("UID"),
    FirstName("First Name"),
    MiddleName("Middle Name"),
    LastName("Last Name"),
    Balance("Banlance"),
    Introducer("Introducer"),
    LoginCounter("LoginCounter"),
    LastLoginTime("lastLoginTime");

    private final String columnName;

    EbizUserParaMeterEnum(String columnName) {
        this.columnName = columnName;
    }

    public String getName() {
        return columnName;
    }

}
