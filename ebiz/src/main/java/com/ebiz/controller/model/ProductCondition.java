package com.ebiz.controller.model;

public class ProductCondition {
    private Integer pageSize;
    private Integer pageIndex;
    private String status;
    private String model;
    private String productName;
    private String brand;
    private String price;
    private String warePrice;
    private String UPC;
    private String id;


    private String companyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWarePrice() {
        return warePrice;
    }

    public void setWarePrice(String warePrice) {
        this.warePrice = warePrice;
    }

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    @Override
    public String toString() {
        return "ProductCondition{" +
                "pageSize=" + pageSize +
                ", pageIndex=" + pageIndex +
                ", status='" + status + '\'' +
                ", model='" + model + '\'' +
                ", productName='" + productName + '\'' +
                ", brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                ", warePrice='" + warePrice + '\'' +
                ", UPC='" + UPC + '\'' +
                ", id='" + id + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
