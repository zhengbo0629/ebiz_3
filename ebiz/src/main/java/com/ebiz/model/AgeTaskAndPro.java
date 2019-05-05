/**
 * @(#)com.ebiz.model.AgeTaskAndPro Copyright (c) 2014-2018 ...
 * <p>
 * DESC:
 */
package com.ebiz.model;

/**
 *
 * @author 王润松
 * @version 1.0  2018/11/6 0006
 */
public class AgeTaskAndPro {
    private Integer ATid;//任务ID
    private String productName;//产品名称
    private String brand;//品牌
    private String model;//型号
    private Double price;//现在价格
    private Integer promotQuantity;//数量
    private Double promotPrice;//加价价格
    private String buyerName;//收货人
    private String buyerTel;//收货人电话
    private String buyerAddress;//收货地址
    private String buyerLeaveMag;//留言
    private String productId;
    private String buyerId;//当前登入的用户Id
    private String taskStatus;//1:已发布 2：已被领取未完成 3：已完成 4:被推荐的产品
    public Integer getATid() {
        return ATid;
    }

    public void setATid(Integer ATid) {
        this.ATid = ATid;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    private String createBy;//领取人

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }



    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerTel() {
        return buyerTel;
    }

    public void setBuyerTel(String buyerTel) {
        this.buyerTel = buyerTel;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerLeaveMag() {
        return buyerLeaveMag;
    }

    public void setBuyerLeaveMag(String buyerLeaveMag) {
        this.buyerLeaveMag = buyerLeaveMag;
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
}
