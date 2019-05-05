package com.ebiz.model;

import java.util.HashSet;

public class EbizCompany {
    private Integer id;

    private String companyName;

    private String ownerName;

    private String status;

    private String permision;

    private Double balance;

    private String createTime;

    private String updateTime;

    private String note;

    private String payPeriod;

    private String payTime;

    private String email;

    private String emailPassword;

    private String phoneNumber;

    private String userManual;

    private String addressName1;

    private String addressDetail1;

    private String addressName2;

    private String addressDetail2;

    private String addressName3;

    private String addressDetail3;
    public HashSet<String> userPermissions = new HashSet<>();

    public void setPermissions(String permissions) {
        this.permision = permissions;
        userPermissions.clear();
        if (permissions == null)
            return;
        String[] strings = permissions.split(",");
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].length() > 0) {
                userPermissions.add(strings[i]);
            }
        }
    }

    public void addPermissions(String permission) {
        if (!userPermissions.contains(permission)) {
            userPermissions.add(permission);
            this.permision = this.permision + "," + permission;
        }
    }

    public HashSet<String> getPermissions() {
        return userPermissions;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getPermision() {
        return permision;
    }

    public void setPermision(String permision) {
        this.permision = permision == null ? null : permision.trim();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod == null ? null : payPeriod.trim();
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime == null ? null : payTime.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getEmailPassword() {
        return emailPassword;
    }

    public void setEmailPassword(String emailPassword) {
        this.emailPassword = emailPassword == null ? null : emailPassword.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getUserManual() {
        return userManual;
    }

    public void setUserManual(String userManual) {
        this.userManual = userManual == null ? null : userManual.trim();
    }

    public String getAddressName1() {
        return addressName1;
    }

    public void setAddressName1(String addressName1) {
        this.addressName1 = addressName1 == null ? null : addressName1.trim();
    }

    public String getAddressDetail1() {
        return addressDetail1;
    }

    public void setAddressDetail1(String addressDetail1) {
        this.addressDetail1 = addressDetail1 == null ? null : addressDetail1.trim();
    }

    public String getAddressName2() {
        return addressName2;
    }

    public void setAddressName2(String addressName2) {
        this.addressName2 = addressName2 == null ? null : addressName2.trim();
    }

    public String getAddressDetail2() {
        return addressDetail2;
    }

    public void setAddressDetail2(String addressDetail2) {
        this.addressDetail2 = addressDetail2 == null ? null : addressDetail2.trim();
    }

    public String getAddressName3() {
        return addressName3;
    }

    public void setAddressName3(String addressName3) {
        this.addressName3 = addressName3 == null ? null : addressName3.trim();
    }

    public String getAddressDetail3() {
        return addressDetail3;
    }

    public void setAddressDetail3(String addressDetail3) {
        this.addressDetail3 = addressDetail3 == null ? null : addressDetail3.trim();
    }
}