package com.ebiz.model;

public class WarehouseProductList {
    private Integer id;

    private String companyName;

    private String model;

    private String productName;

    private String UPC;

    private String ASIN;

    private String SKU;

    private String brand;

    private Double weight;

    private Double length;

    private Double width;

    private Double height;

    private Double price;

    private Integer promotQuantity;

    private Double promotPrice;

    private Double warehousePrice;

    private Integer warehousePromotQuantity;

    private Double warehousePromotePrice;

    private String status;

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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model == null ? null : model.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC == null ? null : UPC.trim();
    }

    public String getASIN() {
        return ASIN;
    }

    public void setASIN(String ASIN) {
        this.ASIN = ASIN == null ? null : ASIN.trim();
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU == null ? null : SKU.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getPromotQuantity() {
        return promotQuantity;
    }

    public void setPromotQuantity(Integer promotQuantity) {
        this.promotQuantity = promotQuantity;
    }

    public Double getPromotPrice() {
        return promotPrice;
    }

    public void setPromotPrice(Double promotPrice) {
        this.promotPrice = promotPrice;
    }

    public Double getWarehousePrice() {
        return warehousePrice;
    }

    public void setWarehousePrice(Double warehousePrice) {
        this.warehousePrice = warehousePrice;
    }

    public Integer getWarehousePromotQuantity() {
        return warehousePromotQuantity;
    }

    public void setWarehousePromotQuantity(Integer warehousePromotQuantity) {
        this.warehousePromotQuantity = warehousePromotQuantity;
    }

    public Double getWarehousePromotePrice() {
        return warehousePromotePrice;
    }

    public void setWarehousePromotePrice(Double warehousePromotePrice) {
        this.warehousePromotePrice = warehousePromotePrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
}