package com.ebiz.controller.model;

public class PackageCondition {
    private Integer pageSize;
    private Integer pageIndex;

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

    private String createdTime;

    private String updateTime;

    private Integer tickets;

    private Integer limitPerPerson;

    private String operatingStatus;

    private String operationRecord;

    private String parameterString;

    private String URI;

    private String userNote;

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

    public String getUPC() {
        return UPC;
    }

    public void setUPC(String UPC) {
        this.UPC = UPC;
    }

    public String getASIN() {
        return ASIN;
    }

    public void setASIN(String ASIN) {
        this.ASIN = ASIN;
    }

    public String getSKU() {
        return SKU;
    }

    public void setSKU(String SKU) {
        this.SKU = SKU;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
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
        this.status = status;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getTickets() {
        return tickets;
    }

    public void setTickets(Integer tickets) {
        this.tickets = tickets;
    }

    public Integer getLimitPerPerson() {
        return limitPerPerson;
    }

    public void setLimitPerPerson(Integer limitPerPerson) {
        this.limitPerPerson = limitPerPerson;
    }

    public String getOperatingStatus() {
        return operatingStatus;
    }

    public void setOperatingStatus(String operatingStatus) {
        this.operatingStatus = operatingStatus;
    }

    public String getOperationRecord() {
        return operationRecord;
    }

    public void setOperationRecord(String operationRecord) {
        this.operationRecord = operationRecord;
    }

    public String getParameterString() {
        return parameterString;
    }

    public void setParameterString(String parameterString) {
        this.parameterString = parameterString;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    @Override
    public String toString() {
        return "PackageCondition{" +
                "pageSize=" + pageSize +
                ", pageIndex=" + pageIndex +
                ", id=" + id +
                ", companyName='" + companyName + '\'' +
                ", model='" + model + '\'' +
                ", productName='" + productName + '\'' +
                ", UPC='" + UPC + '\'' +
                ", ASIN='" + ASIN + '\'' +
                ", SKU='" + SKU + '\'' +
                ", brand='" + brand + '\'' +
                ", weight=" + weight +
                ", length=" + length +
                ", width=" + width +
                ", height=" + height +
                ", price=" + price +
                ", promotQuantity=" + promotQuantity +
                ", promotPrice=" + promotPrice +
                ", warehousePrice=" + warehousePrice +
                ", warehousePromotQuantity=" + warehousePromotQuantity +
                ", warehousePromotePrice=" + warehousePromotePrice +
                ", status='" + status + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", tickets=" + tickets +
                ", limitPerPerson=" + limitPerPerson +
                ", operatingStatus='" + operatingStatus + '\'' +
                ", operationRecord='" + operationRecord + '\'' +
                ", parameterString='" + parameterString + '\'' +
                ", URI='" + URI + '\'' +
                ", userNote='" + userNote + '\'' +
                '}';
    }
}
