package com.ebiz.vo;

public class EmailLabelVo {

    private String uidNewString;
    private String userName;
    private String email;
    private String lastName;
    private String firstName;
    private String packageListInfor;
    private String shippingAddress;

    private Integer currentPage;//当前页数
    private Integer pageSize;//每页显示的数量
    private Integer totalPage;//一共多少页
    private Integer totalCount;//一共多少条数据

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }


    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getUidNewString() {
        return uidNewString;
    }

    public void setUidNewString(String uidNewString) {
        this.uidNewString = uidNewString;
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

    public String getPackageListInfor() {
        return packageListInfor;
    }

    public void setPackageListInfor(String packageListInfor) {
        this.packageListInfor = packageListInfor;
    }
}
