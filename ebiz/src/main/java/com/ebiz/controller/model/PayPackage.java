package com.ebiz.controller.model;

import java.io.File;

public class PayPackage {
    private String uidNewString;//ids处理之后的值
    private String packageIDStrings;//直接传过来的参数值
    private String userName;
    private String email;
    private double balance;//账户余额
    private double value;//商品价值
    private double shouldpay;//应该支付
    private double nowPay;//实际支付
    private String creditString;//信用卡信息
    private String packageListInfor;//包裹信息
    private String emailContent;//邮件内容
    private String confirmCode;//确认码
    private File[] files ;//附件
    private String fileNames;//文件名称

    public String getUidNewString() {
        return uidNewString;
    }

    public void setUidNewString(String uidNewString) {
        this.uidNewString = uidNewString;
    }

    public String getPackageIDStrings() {
        return packageIDStrings;
    }

    public void setPackageIDStrings(String packageIDStrings) {
        this.packageIDStrings = packageIDStrings;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getShouldpay() {
        return shouldpay;
    }

    public void setShouldpay(double shouldpay) {
        this.shouldpay = shouldpay;
    }

    public double getNowPay() {
        return nowPay;
    }

    public void setNowPay(double nowPay) {
        this.nowPay = nowPay;
    }

    public String getCreditString() {
        return creditString;
    }

    public void setCreditString(String creditString) {
        this.creditString = creditString;
    }

    public String getPackageListInfor() {
        return packageListInfor;
    }

    public void setPackageListInfor(String packageListInfor) {
        this.packageListInfor = packageListInfor;
    }

    public String getEmailContent() {
        return emailContent;
    }

    public void setEmailContent(String emailContent) {
        this.emailContent = emailContent;
    }

    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    public File[] getFiles() {
        return files;
    }

    public void setFiles(File[] files) {
        this.files = files;
    }

    public String getFileNames() {
        return fileNames;
    }

    public void setFileNames(String fileNames) {
        this.fileNames = fileNames;
    }
}
