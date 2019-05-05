package com.ebiz.controller.model;

import java.io.File;

public class LabelPackage {
    private String packageListInfor;
    private String uidNewString;//ids处理之后的值
    private String packageIDStrings;//直接传过来的参数值
    private String userName;
    private String email;
    private String shippingAddress;
    private String lastName;
    private String firstName;

    public String getUidNewString() {
        return uidNewString;
    }

    public void setUidNewString(String uidNewString) {
        this.uidNewString = uidNewString;
    }

    public String getPackageListInfor() {
        return packageListInfor;
    }

    public void setPackageListInfor(String packageListInfor) {
        this.packageListInfor = packageListInfor;
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

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
