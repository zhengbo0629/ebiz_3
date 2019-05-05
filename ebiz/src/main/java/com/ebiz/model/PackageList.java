package com.ebiz.model;

public class PackageList {
    private Integer id;

    private String companyName;

    private String trackingNumber;

    private String shipID;

    private String modelNumber;

    private String productName;

    private String productCondition;

    private String UPC;

    private String ASIN;

    private String SKU;

    private String brand;

    private Double price;

    private Double basePrice;

    private Double promPrice;

    private Integer quantity;

    private Integer promQuantity;

    private String storeName;

    private String userName;

    private String shippingAddress;

    private String email;

    private String phoneNumber;

    private String receiver;

    private String note;

    private String createdTime;

    private String updateTime;

    private String creditCardNumber;

    private String status;

    private String checkStatus;

    private String checker;

    private String labelStatus;

    private String labeler;

    private String payStatus;

    private String payer;

    private String userNote;

    public PackageList() {
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

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber == null ? null : trackingNumber.trim();
    }

    public String getShipID() {
        return shipID;
    }

    public void setShipID(String shipID) {
        this.shipID = shipID == null ? null : shipID.trim();
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber == null ? null : modelNumber.trim();
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public String getProductCondition() {
        return productCondition;
    }

    public void setProductCondition(String productCondition) {
        this.productCondition = productCondition == null ? null : productCondition.trim();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Double getPromPrice() {
        return promPrice;
    }

    public void setPromPrice(Double promPrice) {
        this.promPrice = promPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getPromQuantity() {
        return promQuantity;
    }

    public void setPromQuantity(Integer promQuantity) {
        this.promQuantity = promQuantity;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName == null ? null : storeName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress == null ? null : shippingAddress.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver == null ? null : receiver.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime == null ? null : createdTime.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber == null ? null : creditCardNumber.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker == null ? null : checker.trim();
    }

    public String getLabelStatus() {
        return labelStatus;
    }

    public void setLabelStatus(String labelStatus) {
        this.labelStatus = labelStatus == null ? null : labelStatus.trim();
    }

    public String getLabeler() {
        return labeler;
    }

    public void setLabeler(String labeler) {
        this.labeler = labeler == null ? null : labeler.trim();
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer == null ? null : payer.trim();
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote == null ? null : userNote.trim();
    }

    @Override
    public String toString() {
        return "PackageList{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", trackingNumber='" + trackingNumber + '\'' +
                ", shipID='" + shipID + '\'' +
                ", modelNumber='" + modelNumber + '\'' +
                ", productName='" + productName + '\'' +
                ", productCondition='" + productCondition + '\'' +
                ", UPC='" + UPC + '\'' +
                ", ASIN='" + ASIN + '\'' +
                ", SKU='" + SKU + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", basePrice=" + basePrice +
                ", promPrice=" + promPrice +
                ", quantity=" + quantity +
                ", promQuantity=" + promQuantity +
                ", storeName='" + storeName + '\'' +
                ", userName='" + userName + '\'' +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", receiver='" + receiver + '\'' +
                ", note='" + note + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", creditCardNumber='" + creditCardNumber + '\'' +
                ", status='" + status + '\'' +
                ", checkStatus='" + checkStatus + '\'' +
                ", checker='" + checker + '\'' +
                ", labelStatus='" + labelStatus + '\'' +
                ", labeler='" + labeler + '\'' +
                ", payStatus='" + payStatus + '\'' +
                ", payer='" + payer + '\'' +
                ", userNote='" + userNote + '\'' +
                '}';
    }
}