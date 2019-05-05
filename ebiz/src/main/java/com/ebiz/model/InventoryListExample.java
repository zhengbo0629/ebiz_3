package com.ebiz.model;

import java.util.ArrayList;
import java.util.List;

public class InventoryListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InventoryListExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNull() {
            addCriterion("CompanyName is null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNotNull() {
            addCriterion("CompanyName is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameEqualTo(String value) {
            addCriterion("CompanyName =", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotEqualTo(String value) {
            addCriterion("CompanyName <>", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThan(String value) {
            addCriterion("CompanyName >", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("CompanyName >=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThan(String value) {
            addCriterion("CompanyName <", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("CompanyName <=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLike(String value) {
            addCriterion("CompanyName like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotLike(String value) {
            addCriterion("CompanyName not like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIn(List<String> values) {
            addCriterion("CompanyName in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotIn(List<String> values) {
            addCriterion("CompanyName not in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameBetween(String value1, String value2) {
            addCriterion("CompanyName between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotBetween(String value1, String value2) {
            addCriterion("CompanyName not between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andModelIsNull() {
            addCriterion("Model is null");
            return (Criteria) this;
        }

        public Criteria andModelIsNotNull() {
            addCriterion("Model is not null");
            return (Criteria) this;
        }

        public Criteria andModelEqualTo(String value) {
            addCriterion("Model =", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotEqualTo(String value) {
            addCriterion("Model <>", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThan(String value) {
            addCriterion("Model >", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelGreaterThanOrEqualTo(String value) {
            addCriterion("Model >=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThan(String value) {
            addCriterion("Model <", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLessThanOrEqualTo(String value) {
            addCriterion("Model <=", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelLike(String value) {
            addCriterion("Model like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotLike(String value) {
            addCriterion("Model not like", value, "model");
            return (Criteria) this;
        }

        public Criteria andModelIn(List<String> values) {
            addCriterion("Model in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotIn(List<String> values) {
            addCriterion("Model not in", values, "model");
            return (Criteria) this;
        }

        public Criteria andModelBetween(String value1, String value2) {
            addCriterion("Model between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andModelNotBetween(String value1, String value2) {
            addCriterion("Model not between", value1, value2, "model");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("Name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("Name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("Name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("Name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("Name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("Name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("Name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("Name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("Name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("Name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("Name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("Name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("Name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("Name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andProductConditionIsNull() {
            addCriterion("ProductCondition is null");
            return (Criteria) this;
        }

        public Criteria andProductConditionIsNotNull() {
            addCriterion("ProductCondition is not null");
            return (Criteria) this;
        }

        public Criteria andProductConditionEqualTo(String value) {
            addCriterion("ProductCondition =", value, "productCondition");
            return (Criteria) this;
        }

        public Criteria andProductConditionNotEqualTo(String value) {
            addCriterion("ProductCondition <>", value, "productCondition");
            return (Criteria) this;
        }

        public Criteria andProductConditionGreaterThan(String value) {
            addCriterion("ProductCondition >", value, "productCondition");
            return (Criteria) this;
        }

        public Criteria andProductConditionGreaterThanOrEqualTo(String value) {
            addCriterion("ProductCondition >=", value, "productCondition");
            return (Criteria) this;
        }

        public Criteria andProductConditionLessThan(String value) {
            addCriterion("ProductCondition <", value, "productCondition");
            return (Criteria) this;
        }

        public Criteria andProductConditionLessThanOrEqualTo(String value) {
            addCriterion("ProductCondition <=", value, "productCondition");
            return (Criteria) this;
        }

        public Criteria andProductConditionLike(String value) {
            addCriterion("ProductCondition like", value, "productCondition");
            return (Criteria) this;
        }

        public Criteria andProductConditionNotLike(String value) {
            addCriterion("ProductCondition not like", value, "productCondition");
            return (Criteria) this;
        }

        public Criteria andProductConditionIn(List<String> values) {
            addCriterion("ProductCondition in", values, "productCondition");
            return (Criteria) this;
        }

        public Criteria andProductConditionNotIn(List<String> values) {
            addCriterion("ProductCondition not in", values, "productCondition");
            return (Criteria) this;
        }

        public Criteria andProductConditionBetween(String value1, String value2) {
            addCriterion("ProductCondition between", value1, value2, "productCondition");
            return (Criteria) this;
        }

        public Criteria andProductConditionNotBetween(String value1, String value2) {
            addCriterion("ProductCondition not between", value1, value2, "productCondition");
            return (Criteria) this;
        }

        public Criteria andUPCIsNull() {
            addCriterion("UPC is null");
            return (Criteria) this;
        }

        public Criteria andUPCIsNotNull() {
            addCriterion("UPC is not null");
            return (Criteria) this;
        }

        public Criteria andUPCEqualTo(String value) {
            addCriterion("UPC =", value, "UPC");
            return (Criteria) this;
        }

        public Criteria andUPCNotEqualTo(String value) {
            addCriterion("UPC <>", value, "UPC");
            return (Criteria) this;
        }

        public Criteria andUPCGreaterThan(String value) {
            addCriterion("UPC >", value, "UPC");
            return (Criteria) this;
        }

        public Criteria andUPCGreaterThanOrEqualTo(String value) {
            addCriterion("UPC >=", value, "UPC");
            return (Criteria) this;
        }

        public Criteria andUPCLessThan(String value) {
            addCriterion("UPC <", value, "UPC");
            return (Criteria) this;
        }

        public Criteria andUPCLessThanOrEqualTo(String value) {
            addCriterion("UPC <=", value, "UPC");
            return (Criteria) this;
        }

        public Criteria andUPCLike(String value) {
            addCriterion("UPC like", value, "UPC");
            return (Criteria) this;
        }

        public Criteria andUPCNotLike(String value) {
            addCriterion("UPC not like", value, "UPC");
            return (Criteria) this;
        }

        public Criteria andUPCIn(List<String> values) {
            addCriterion("UPC in", values, "UPC");
            return (Criteria) this;
        }

        public Criteria andUPCNotIn(List<String> values) {
            addCriterion("UPC not in", values, "UPC");
            return (Criteria) this;
        }

        public Criteria andUPCBetween(String value1, String value2) {
            addCriterion("UPC between", value1, value2, "UPC");
            return (Criteria) this;
        }

        public Criteria andUPCNotBetween(String value1, String value2) {
            addCriterion("UPC not between", value1, value2, "UPC");
            return (Criteria) this;
        }

        public Criteria andASINIsNull() {
            addCriterion("ASIN is null");
            return (Criteria) this;
        }

        public Criteria andASINIsNotNull() {
            addCriterion("ASIN is not null");
            return (Criteria) this;
        }

        public Criteria andASINEqualTo(String value) {
            addCriterion("ASIN =", value, "ASIN");
            return (Criteria) this;
        }

        public Criteria andASINNotEqualTo(String value) {
            addCriterion("ASIN <>", value, "ASIN");
            return (Criteria) this;
        }

        public Criteria andASINGreaterThan(String value) {
            addCriterion("ASIN >", value, "ASIN");
            return (Criteria) this;
        }

        public Criteria andASINGreaterThanOrEqualTo(String value) {
            addCriterion("ASIN >=", value, "ASIN");
            return (Criteria) this;
        }

        public Criteria andASINLessThan(String value) {
            addCriterion("ASIN <", value, "ASIN");
            return (Criteria) this;
        }

        public Criteria andASINLessThanOrEqualTo(String value) {
            addCriterion("ASIN <=", value, "ASIN");
            return (Criteria) this;
        }

        public Criteria andASINLike(String value) {
            addCriterion("ASIN like", value, "ASIN");
            return (Criteria) this;
        }

        public Criteria andASINNotLike(String value) {
            addCriterion("ASIN not like", value, "ASIN");
            return (Criteria) this;
        }

        public Criteria andASINIn(List<String> values) {
            addCriterion("ASIN in", values, "ASIN");
            return (Criteria) this;
        }

        public Criteria andASINNotIn(List<String> values) {
            addCriterion("ASIN not in", values, "ASIN");
            return (Criteria) this;
        }

        public Criteria andASINBetween(String value1, String value2) {
            addCriterion("ASIN between", value1, value2, "ASIN");
            return (Criteria) this;
        }

        public Criteria andASINNotBetween(String value1, String value2) {
            addCriterion("ASIN not between", value1, value2, "ASIN");
            return (Criteria) this;
        }

        public Criteria andSKUIsNull() {
            addCriterion("SKU is null");
            return (Criteria) this;
        }

        public Criteria andSKUIsNotNull() {
            addCriterion("SKU is not null");
            return (Criteria) this;
        }

        public Criteria andSKUEqualTo(String value) {
            addCriterion("SKU =", value, "SKU");
            return (Criteria) this;
        }

        public Criteria andSKUNotEqualTo(String value) {
            addCriterion("SKU <>", value, "SKU");
            return (Criteria) this;
        }

        public Criteria andSKUGreaterThan(String value) {
            addCriterion("SKU >", value, "SKU");
            return (Criteria) this;
        }

        public Criteria andSKUGreaterThanOrEqualTo(String value) {
            addCriterion("SKU >=", value, "SKU");
            return (Criteria) this;
        }

        public Criteria andSKULessThan(String value) {
            addCriterion("SKU <", value, "SKU");
            return (Criteria) this;
        }

        public Criteria andSKULessThanOrEqualTo(String value) {
            addCriterion("SKU <=", value, "SKU");
            return (Criteria) this;
        }

        public Criteria andSKULike(String value) {
            addCriterion("SKU like", value, "SKU");
            return (Criteria) this;
        }

        public Criteria andSKUNotLike(String value) {
            addCriterion("SKU not like", value, "SKU");
            return (Criteria) this;
        }

        public Criteria andSKUIn(List<String> values) {
            addCriterion("SKU in", values, "SKU");
            return (Criteria) this;
        }

        public Criteria andSKUNotIn(List<String> values) {
            addCriterion("SKU not in", values, "SKU");
            return (Criteria) this;
        }

        public Criteria andSKUBetween(String value1, String value2) {
            addCriterion("SKU between", value1, value2, "SKU");
            return (Criteria) this;
        }

        public Criteria andSKUNotBetween(String value1, String value2) {
            addCriterion("SKU not between", value1, value2, "SKU");
            return (Criteria) this;
        }

        public Criteria andBrandIsNull() {
            addCriterion("Brand is null");
            return (Criteria) this;
        }

        public Criteria andBrandIsNotNull() {
            addCriterion("Brand is not null");
            return (Criteria) this;
        }

        public Criteria andBrandEqualTo(String value) {
            addCriterion("Brand =", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotEqualTo(String value) {
            addCriterion("Brand <>", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThan(String value) {
            addCriterion("Brand >", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThanOrEqualTo(String value) {
            addCriterion("Brand >=", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThan(String value) {
            addCriterion("Brand <", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThanOrEqualTo(String value) {
            addCriterion("Brand <=", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLike(String value) {
            addCriterion("Brand like", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotLike(String value) {
            addCriterion("Brand not like", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandIn(List<String> values) {
            addCriterion("Brand in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotIn(List<String> values) {
            addCriterion("Brand not in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandBetween(String value1, String value2) {
            addCriterion("Brand between", value1, value2, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotBetween(String value1, String value2) {
            addCriterion("Brand not between", value1, value2, "brand");
            return (Criteria) this;
        }

        public Criteria andPriceIsNull() {
            addCriterion("Price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("Price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(Double value) {
            addCriterion("Price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(Double value) {
            addCriterion("Price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(Double value) {
            addCriterion("Price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("Price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(Double value) {
            addCriterion("Price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(Double value) {
            addCriterion("Price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<Double> values) {
            addCriterion("Price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<Double> values) {
            addCriterion("Price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(Double value1, Double value2) {
            addCriterion("Price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(Double value1, Double value2) {
            addCriterion("Price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andReceivedIsNull() {
            addCriterion("Received is null");
            return (Criteria) this;
        }

        public Criteria andReceivedIsNotNull() {
            addCriterion("Received is not null");
            return (Criteria) this;
        }

        public Criteria andReceivedEqualTo(Integer value) {
            addCriterion("Received =", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedNotEqualTo(Integer value) {
            addCriterion("Received <>", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedGreaterThan(Integer value) {
            addCriterion("Received >", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedGreaterThanOrEqualTo(Integer value) {
            addCriterion("Received >=", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedLessThan(Integer value) {
            addCriterion("Received <", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedLessThanOrEqualTo(Integer value) {
            addCriterion("Received <=", value, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedIn(List<Integer> values) {
            addCriterion("Received in", values, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedNotIn(List<Integer> values) {
            addCriterion("Received not in", values, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedBetween(Integer value1, Integer value2) {
            addCriterion("Received between", value1, value2, "received");
            return (Criteria) this;
        }

        public Criteria andReceivedNotBetween(Integer value1, Integer value2) {
            addCriterion("Received not between", value1, value2, "received");
            return (Criteria) this;
        }

        public Criteria andInStockIsNull() {
            addCriterion("InStock is null");
            return (Criteria) this;
        }

        public Criteria andInStockIsNotNull() {
            addCriterion("InStock is not null");
            return (Criteria) this;
        }

        public Criteria andInStockEqualTo(Integer value) {
            addCriterion("InStock =", value, "inStock");
            return (Criteria) this;
        }

        public Criteria andInStockNotEqualTo(Integer value) {
            addCriterion("InStock <>", value, "inStock");
            return (Criteria) this;
        }

        public Criteria andInStockGreaterThan(Integer value) {
            addCriterion("InStock >", value, "inStock");
            return (Criteria) this;
        }

        public Criteria andInStockGreaterThanOrEqualTo(Integer value) {
            addCriterion("InStock >=", value, "inStock");
            return (Criteria) this;
        }

        public Criteria andInStockLessThan(Integer value) {
            addCriterion("InStock <", value, "inStock");
            return (Criteria) this;
        }

        public Criteria andInStockLessThanOrEqualTo(Integer value) {
            addCriterion("InStock <=", value, "inStock");
            return (Criteria) this;
        }

        public Criteria andInStockIn(List<Integer> values) {
            addCriterion("InStock in", values, "inStock");
            return (Criteria) this;
        }

        public Criteria andInStockNotIn(List<Integer> values) {
            addCriterion("InStock not in", values, "inStock");
            return (Criteria) this;
        }

        public Criteria andInStockBetween(Integer value1, Integer value2) {
            addCriterion("InStock between", value1, value2, "inStock");
            return (Criteria) this;
        }

        public Criteria andInStockNotBetween(Integer value1, Integer value2) {
            addCriterion("InStock not between", value1, value2, "inStock");
            return (Criteria) this;
        }

        public Criteria andPreSendIsNull() {
            addCriterion("PreSend is null");
            return (Criteria) this;
        }

        public Criteria andPreSendIsNotNull() {
            addCriterion("PreSend is not null");
            return (Criteria) this;
        }

        public Criteria andPreSendEqualTo(Integer value) {
            addCriterion("PreSend =", value, "preSend");
            return (Criteria) this;
        }

        public Criteria andPreSendNotEqualTo(Integer value) {
            addCriterion("PreSend <>", value, "preSend");
            return (Criteria) this;
        }

        public Criteria andPreSendGreaterThan(Integer value) {
            addCriterion("PreSend >", value, "preSend");
            return (Criteria) this;
        }

        public Criteria andPreSendGreaterThanOrEqualTo(Integer value) {
            addCriterion("PreSend >=", value, "preSend");
            return (Criteria) this;
        }

        public Criteria andPreSendLessThan(Integer value) {
            addCriterion("PreSend <", value, "preSend");
            return (Criteria) this;
        }

        public Criteria andPreSendLessThanOrEqualTo(Integer value) {
            addCriterion("PreSend <=", value, "preSend");
            return (Criteria) this;
        }

        public Criteria andPreSendIn(List<Integer> values) {
            addCriterion("PreSend in", values, "preSend");
            return (Criteria) this;
        }

        public Criteria andPreSendNotIn(List<Integer> values) {
            addCriterion("PreSend not in", values, "preSend");
            return (Criteria) this;
        }

        public Criteria andPreSendBetween(Integer value1, Integer value2) {
            addCriterion("PreSend between", value1, value2, "preSend");
            return (Criteria) this;
        }

        public Criteria andPreSendNotBetween(Integer value1, Integer value2) {
            addCriterion("PreSend not between", value1, value2, "preSend");
            return (Criteria) this;
        }

        public Criteria andShippingIsNull() {
            addCriterion("Shipping is null");
            return (Criteria) this;
        }

        public Criteria andShippingIsNotNull() {
            addCriterion("Shipping is not null");
            return (Criteria) this;
        }

        public Criteria andShippingEqualTo(Integer value) {
            addCriterion("Shipping =", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingNotEqualTo(Integer value) {
            addCriterion("Shipping <>", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingGreaterThan(Integer value) {
            addCriterion("Shipping >", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingGreaterThanOrEqualTo(Integer value) {
            addCriterion("Shipping >=", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingLessThan(Integer value) {
            addCriterion("Shipping <", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingLessThanOrEqualTo(Integer value) {
            addCriterion("Shipping <=", value, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingIn(List<Integer> values) {
            addCriterion("Shipping in", values, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingNotIn(List<Integer> values) {
            addCriterion("Shipping not in", values, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingBetween(Integer value1, Integer value2) {
            addCriterion("Shipping between", value1, value2, "shipping");
            return (Criteria) this;
        }

        public Criteria andShippingNotBetween(Integer value1, Integer value2) {
            addCriterion("Shipping not between", value1, value2, "shipping");
            return (Criteria) this;
        }

        public Criteria andTotalShippedIsNull() {
            addCriterion("TotalShipped is null");
            return (Criteria) this;
        }

        public Criteria andTotalShippedIsNotNull() {
            addCriterion("TotalShipped is not null");
            return (Criteria) this;
        }

        public Criteria andTotalShippedEqualTo(Integer value) {
            addCriterion("TotalShipped =", value, "totalShipped");
            return (Criteria) this;
        }

        public Criteria andTotalShippedNotEqualTo(Integer value) {
            addCriterion("TotalShipped <>", value, "totalShipped");
            return (Criteria) this;
        }

        public Criteria andTotalShippedGreaterThan(Integer value) {
            addCriterion("TotalShipped >", value, "totalShipped");
            return (Criteria) this;
        }

        public Criteria andTotalShippedGreaterThanOrEqualTo(Integer value) {
            addCriterion("TotalShipped >=", value, "totalShipped");
            return (Criteria) this;
        }

        public Criteria andTotalShippedLessThan(Integer value) {
            addCriterion("TotalShipped <", value, "totalShipped");
            return (Criteria) this;
        }

        public Criteria andTotalShippedLessThanOrEqualTo(Integer value) {
            addCriterion("TotalShipped <=", value, "totalShipped");
            return (Criteria) this;
        }

        public Criteria andTotalShippedIn(List<Integer> values) {
            addCriterion("TotalShipped in", values, "totalShipped");
            return (Criteria) this;
        }

        public Criteria andTotalShippedNotIn(List<Integer> values) {
            addCriterion("TotalShipped not in", values, "totalShipped");
            return (Criteria) this;
        }

        public Criteria andTotalShippedBetween(Integer value1, Integer value2) {
            addCriterion("TotalShipped between", value1, value2, "totalShipped");
            return (Criteria) this;
        }

        public Criteria andTotalShippedNotBetween(Integer value1, Integer value2) {
            addCriterion("TotalShipped not between", value1, value2, "totalShipped");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNull() {
            addCriterion("CreatedTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIsNotNull() {
            addCriterion("CreatedTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeEqualTo(String value) {
            addCriterion("CreatedTime =", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotEqualTo(String value) {
            addCriterion("CreatedTime <>", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThan(String value) {
            addCriterion("CreatedTime >", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CreatedTime >=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThan(String value) {
            addCriterion("CreatedTime <", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLessThanOrEqualTo(String value) {
            addCriterion("CreatedTime <=", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeLike(String value) {
            addCriterion("CreatedTime like", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotLike(String value) {
            addCriterion("CreatedTime not like", value, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeIn(List<String> values) {
            addCriterion("CreatedTime in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotIn(List<String> values) {
            addCriterion("CreatedTime not in", values, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeBetween(String value1, String value2) {
            addCriterion("CreatedTime between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andCreatedTimeNotBetween(String value1, String value2) {
            addCriterion("CreatedTime not between", value1, value2, "createdTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("UpdateTime is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("UpdateTime is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(String value) {
            addCriterion("UpdateTime =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(String value) {
            addCriterion("UpdateTime <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(String value) {
            addCriterion("UpdateTime >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("UpdateTime >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(String value) {
            addCriterion("UpdateTime <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(String value) {
            addCriterion("UpdateTime <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLike(String value) {
            addCriterion("UpdateTime like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotLike(String value) {
            addCriterion("UpdateTime not like", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<String> values) {
            addCriterion("UpdateTime in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<String> values) {
            addCriterion("UpdateTime not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(String value1, String value2) {
            addCriterion("UpdateTime between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(String value1, String value2) {
            addCriterion("UpdateTime not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("Note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("Note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("Note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("Note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("Note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("Note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("Note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("Note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("Note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("Note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("Note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("Note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("Note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("Note not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andWareHouseIsNull() {
            addCriterion("WareHouse is null");
            return (Criteria) this;
        }

        public Criteria andWareHouseIsNotNull() {
            addCriterion("WareHouse is not null");
            return (Criteria) this;
        }

        public Criteria andWareHouseEqualTo(String value) {
            addCriterion("WareHouse =", value, "wareHouse");
            return (Criteria) this;
        }

        public Criteria andWareHouseNotEqualTo(String value) {
            addCriterion("WareHouse <>", value, "wareHouse");
            return (Criteria) this;
        }

        public Criteria andWareHouseGreaterThan(String value) {
            addCriterion("WareHouse >", value, "wareHouse");
            return (Criteria) this;
        }

        public Criteria andWareHouseGreaterThanOrEqualTo(String value) {
            addCriterion("WareHouse >=", value, "wareHouse");
            return (Criteria) this;
        }

        public Criteria andWareHouseLessThan(String value) {
            addCriterion("WareHouse <", value, "wareHouse");
            return (Criteria) this;
        }

        public Criteria andWareHouseLessThanOrEqualTo(String value) {
            addCriterion("WareHouse <=", value, "wareHouse");
            return (Criteria) this;
        }

        public Criteria andWareHouseLike(String value) {
            addCriterion("WareHouse like", value, "wareHouse");
            return (Criteria) this;
        }

        public Criteria andWareHouseNotLike(String value) {
            addCriterion("WareHouse not like", value, "wareHouse");
            return (Criteria) this;
        }

        public Criteria andWareHouseIn(List<String> values) {
            addCriterion("WareHouse in", values, "wareHouse");
            return (Criteria) this;
        }

        public Criteria andWareHouseNotIn(List<String> values) {
            addCriterion("WareHouse not in", values, "wareHouse");
            return (Criteria) this;
        }

        public Criteria andWareHouseBetween(String value1, String value2) {
            addCriterion("WareHouse between", value1, value2, "wareHouse");
            return (Criteria) this;
        }

        public Criteria andWareHouseNotBetween(String value1, String value2) {
            addCriterion("WareHouse not between", value1, value2, "wareHouse");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}