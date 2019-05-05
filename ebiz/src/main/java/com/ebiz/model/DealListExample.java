package com.ebiz.model;

import java.util.ArrayList;
import java.util.List;

public class DealListExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DealListExample() {
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

        public Criteria andProductNameIsNull() {
            addCriterion("ProductName is null");
            return (Criteria) this;
        }

        public Criteria andProductNameIsNotNull() {
            addCriterion("ProductName is not null");
            return (Criteria) this;
        }

        public Criteria andProductNameEqualTo(String value) {
            addCriterion("ProductName =", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotEqualTo(String value) {
            addCriterion("ProductName <>", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThan(String value) {
            addCriterion("ProductName >", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameGreaterThanOrEqualTo(String value) {
            addCriterion("ProductName >=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThan(String value) {
            addCriterion("ProductName <", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLessThanOrEqualTo(String value) {
            addCriterion("ProductName <=", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameLike(String value) {
            addCriterion("ProductName like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotLike(String value) {
            addCriterion("ProductName not like", value, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameIn(List<String> values) {
            addCriterion("ProductName in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotIn(List<String> values) {
            addCriterion("ProductName not in", values, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameBetween(String value1, String value2) {
            addCriterion("ProductName between", value1, value2, "productName");
            return (Criteria) this;
        }

        public Criteria andProductNameNotBetween(String value1, String value2) {
            addCriterion("ProductName not between", value1, value2, "productName");
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

        public Criteria andWeightIsNull() {
            addCriterion("Weight is null");
            return (Criteria) this;
        }

        public Criteria andWeightIsNotNull() {
            addCriterion("Weight is not null");
            return (Criteria) this;
        }

        public Criteria andWeightEqualTo(Double value) {
            addCriterion("Weight =", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotEqualTo(Double value) {
            addCriterion("Weight <>", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThan(Double value) {
            addCriterion("Weight >", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightGreaterThanOrEqualTo(Double value) {
            addCriterion("Weight >=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThan(Double value) {
            addCriterion("Weight <", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightLessThanOrEqualTo(Double value) {
            addCriterion("Weight <=", value, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightIn(List<Double> values) {
            addCriterion("Weight in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotIn(List<Double> values) {
            addCriterion("Weight not in", values, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightBetween(Double value1, Double value2) {
            addCriterion("Weight between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andWeightNotBetween(Double value1, Double value2) {
            addCriterion("Weight not between", value1, value2, "weight");
            return (Criteria) this;
        }

        public Criteria andLengthIsNull() {
            addCriterion("Length is null");
            return (Criteria) this;
        }

        public Criteria andLengthIsNotNull() {
            addCriterion("Length is not null");
            return (Criteria) this;
        }

        public Criteria andLengthEqualTo(Double value) {
            addCriterion("Length =", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotEqualTo(Double value) {
            addCriterion("Length <>", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthGreaterThan(Double value) {
            addCriterion("Length >", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthGreaterThanOrEqualTo(Double value) {
            addCriterion("Length >=", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthLessThan(Double value) {
            addCriterion("Length <", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthLessThanOrEqualTo(Double value) {
            addCriterion("Length <=", value, "length");
            return (Criteria) this;
        }

        public Criteria andLengthIn(List<Double> values) {
            addCriterion("Length in", values, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotIn(List<Double> values) {
            addCriterion("Length not in", values, "length");
            return (Criteria) this;
        }

        public Criteria andLengthBetween(Double value1, Double value2) {
            addCriterion("Length between", value1, value2, "length");
            return (Criteria) this;
        }

        public Criteria andLengthNotBetween(Double value1, Double value2) {
            addCriterion("Length not between", value1, value2, "length");
            return (Criteria) this;
        }

        public Criteria andWidthIsNull() {
            addCriterion("Width is null");
            return (Criteria) this;
        }

        public Criteria andWidthIsNotNull() {
            addCriterion("Width is not null");
            return (Criteria) this;
        }

        public Criteria andWidthEqualTo(Double value) {
            addCriterion("Width =", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotEqualTo(Double value) {
            addCriterion("Width <>", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthGreaterThan(Double value) {
            addCriterion("Width >", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthGreaterThanOrEqualTo(Double value) {
            addCriterion("Width >=", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthLessThan(Double value) {
            addCriterion("Width <", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthLessThanOrEqualTo(Double value) {
            addCriterion("Width <=", value, "width");
            return (Criteria) this;
        }

        public Criteria andWidthIn(List<Double> values) {
            addCriterion("Width in", values, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotIn(List<Double> values) {
            addCriterion("Width not in", values, "width");
            return (Criteria) this;
        }

        public Criteria andWidthBetween(Double value1, Double value2) {
            addCriterion("Width between", value1, value2, "width");
            return (Criteria) this;
        }

        public Criteria andWidthNotBetween(Double value1, Double value2) {
            addCriterion("Width not between", value1, value2, "width");
            return (Criteria) this;
        }

        public Criteria andHeightIsNull() {
            addCriterion("Height is null");
            return (Criteria) this;
        }

        public Criteria andHeightIsNotNull() {
            addCriterion("Height is not null");
            return (Criteria) this;
        }

        public Criteria andHeightEqualTo(Double value) {
            addCriterion("Height =", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotEqualTo(Double value) {
            addCriterion("Height <>", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThan(Double value) {
            addCriterion("Height >", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThanOrEqualTo(Double value) {
            addCriterion("Height >=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThan(Double value) {
            addCriterion("Height <", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThanOrEqualTo(Double value) {
            addCriterion("Height <=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightIn(List<Double> values) {
            addCriterion("Height in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotIn(List<Double> values) {
            addCriterion("Height not in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightBetween(Double value1, Double value2) {
            addCriterion("Height between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotBetween(Double value1, Double value2) {
            addCriterion("Height not between", value1, value2, "height");
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

        public Criteria andPromotQuantityIsNull() {
            addCriterion("PromotQuantity is null");
            return (Criteria) this;
        }

        public Criteria andPromotQuantityIsNotNull() {
            addCriterion("PromotQuantity is not null");
            return (Criteria) this;
        }

        public Criteria andPromotQuantityEqualTo(Integer value) {
            addCriterion("PromotQuantity =", value, "promotQuantity");
            return (Criteria) this;
        }

        public Criteria andPromotQuantityNotEqualTo(Integer value) {
            addCriterion("PromotQuantity <>", value, "promotQuantity");
            return (Criteria) this;
        }

        public Criteria andPromotQuantityGreaterThan(Integer value) {
            addCriterion("PromotQuantity >", value, "promotQuantity");
            return (Criteria) this;
        }

        public Criteria andPromotQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("PromotQuantity >=", value, "promotQuantity");
            return (Criteria) this;
        }

        public Criteria andPromotQuantityLessThan(Integer value) {
            addCriterion("PromotQuantity <", value, "promotQuantity");
            return (Criteria) this;
        }

        public Criteria andPromotQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("PromotQuantity <=", value, "promotQuantity");
            return (Criteria) this;
        }

        public Criteria andPromotQuantityIn(List<Integer> values) {
            addCriterion("PromotQuantity in", values, "promotQuantity");
            return (Criteria) this;
        }

        public Criteria andPromotQuantityNotIn(List<Integer> values) {
            addCriterion("PromotQuantity not in", values, "promotQuantity");
            return (Criteria) this;
        }

        public Criteria andPromotQuantityBetween(Integer value1, Integer value2) {
            addCriterion("PromotQuantity between", value1, value2, "promotQuantity");
            return (Criteria) this;
        }

        public Criteria andPromotQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("PromotQuantity not between", value1, value2, "promotQuantity");
            return (Criteria) this;
        }

        public Criteria andPromotPriceIsNull() {
            addCriterion("PromotPrice is null");
            return (Criteria) this;
        }

        public Criteria andPromotPriceIsNotNull() {
            addCriterion("PromotPrice is not null");
            return (Criteria) this;
        }

        public Criteria andPromotPriceEqualTo(Double value) {
            addCriterion("PromotPrice =", value, "promotPrice");
            return (Criteria) this;
        }

        public Criteria andPromotPriceNotEqualTo(Double value) {
            addCriterion("PromotPrice <>", value, "promotPrice");
            return (Criteria) this;
        }

        public Criteria andPromotPriceGreaterThan(Double value) {
            addCriterion("PromotPrice >", value, "promotPrice");
            return (Criteria) this;
        }

        public Criteria andPromotPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("PromotPrice >=", value, "promotPrice");
            return (Criteria) this;
        }

        public Criteria andPromotPriceLessThan(Double value) {
            addCriterion("PromotPrice <", value, "promotPrice");
            return (Criteria) this;
        }

        public Criteria andPromotPriceLessThanOrEqualTo(Double value) {
            addCriterion("PromotPrice <=", value, "promotPrice");
            return (Criteria) this;
        }

        public Criteria andPromotPriceIn(List<Double> values) {
            addCriterion("PromotPrice in", values, "promotPrice");
            return (Criteria) this;
        }

        public Criteria andPromotPriceNotIn(List<Double> values) {
            addCriterion("PromotPrice not in", values, "promotPrice");
            return (Criteria) this;
        }

        public Criteria andPromotPriceBetween(Double value1, Double value2) {
            addCriterion("PromotPrice between", value1, value2, "promotPrice");
            return (Criteria) this;
        }

        public Criteria andPromotPriceNotBetween(Double value1, Double value2) {
            addCriterion("PromotPrice not between", value1, value2, "promotPrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceIsNull() {
            addCriterion("WarehousePrice is null");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceIsNotNull() {
            addCriterion("WarehousePrice is not null");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceEqualTo(Double value) {
            addCriterion("WarehousePrice =", value, "warehousePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceNotEqualTo(Double value) {
            addCriterion("WarehousePrice <>", value, "warehousePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceGreaterThan(Double value) {
            addCriterion("WarehousePrice >", value, "warehousePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceGreaterThanOrEqualTo(Double value) {
            addCriterion("WarehousePrice >=", value, "warehousePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceLessThan(Double value) {
            addCriterion("WarehousePrice <", value, "warehousePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceLessThanOrEqualTo(Double value) {
            addCriterion("WarehousePrice <=", value, "warehousePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceIn(List<Double> values) {
            addCriterion("WarehousePrice in", values, "warehousePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceNotIn(List<Double> values) {
            addCriterion("WarehousePrice not in", values, "warehousePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceBetween(Double value1, Double value2) {
            addCriterion("WarehousePrice between", value1, value2, "warehousePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePriceNotBetween(Double value1, Double value2) {
            addCriterion("WarehousePrice not between", value1, value2, "warehousePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityIsNull() {
            addCriterion("WarehousePromotQuantity is null");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityIsNotNull() {
            addCriterion("WarehousePromotQuantity is not null");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityEqualTo(Integer value) {
            addCriterion("WarehousePromotQuantity =", value, "warehousePromotQuantity");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityNotEqualTo(Integer value) {
            addCriterion("WarehousePromotQuantity <>", value, "warehousePromotQuantity");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityGreaterThan(Integer value) {
            addCriterion("WarehousePromotQuantity >", value, "warehousePromotQuantity");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("WarehousePromotQuantity >=", value, "warehousePromotQuantity");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityLessThan(Integer value) {
            addCriterion("WarehousePromotQuantity <", value, "warehousePromotQuantity");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("WarehousePromotQuantity <=", value, "warehousePromotQuantity");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityIn(List<Integer> values) {
            addCriterion("WarehousePromotQuantity in", values, "warehousePromotQuantity");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityNotIn(List<Integer> values) {
            addCriterion("WarehousePromotQuantity not in", values, "warehousePromotQuantity");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityBetween(Integer value1, Integer value2) {
            addCriterion("WarehousePromotQuantity between", value1, value2, "warehousePromotQuantity");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("WarehousePromotQuantity not between", value1, value2, "warehousePromotQuantity");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceIsNull() {
            addCriterion("WarehousePromotePrice is null");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceIsNotNull() {
            addCriterion("WarehousePromotePrice is not null");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceEqualTo(Double value) {
            addCriterion("WarehousePromotePrice =", value, "warehousePromotePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceNotEqualTo(Double value) {
            addCriterion("WarehousePromotePrice <>", value, "warehousePromotePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceGreaterThan(Double value) {
            addCriterion("WarehousePromotePrice >", value, "warehousePromotePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceGreaterThanOrEqualTo(Double value) {
            addCriterion("WarehousePromotePrice >=", value, "warehousePromotePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceLessThan(Double value) {
            addCriterion("WarehousePromotePrice <", value, "warehousePromotePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceLessThanOrEqualTo(Double value) {
            addCriterion("WarehousePromotePrice <=", value, "warehousePromotePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceIn(List<Double> values) {
            addCriterion("WarehousePromotePrice in", values, "warehousePromotePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceNotIn(List<Double> values) {
            addCriterion("WarehousePromotePrice not in", values, "warehousePromotePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceBetween(Double value1, Double value2) {
            addCriterion("WarehousePromotePrice between", value1, value2, "warehousePromotePrice");
            return (Criteria) this;
        }

        public Criteria andWarehousePromotePriceNotBetween(Double value1, Double value2) {
            addCriterion("WarehousePromotePrice not between", value1, value2, "warehousePromotePrice");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("Status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("Status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("Status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("Status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("Status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("Status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("Status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("Status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("Status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("Status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("Status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("Status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("Status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("Status not between", value1, value2, "status");
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

        public Criteria andTicketsIsNull() {
            addCriterion("Tickets is null");
            return (Criteria) this;
        }

        public Criteria andTicketsIsNotNull() {
            addCriterion("Tickets is not null");
            return (Criteria) this;
        }

        public Criteria andTicketsEqualTo(Integer value) {
            addCriterion("Tickets =", value, "tickets");
            return (Criteria) this;
        }

        public Criteria andTicketsNotEqualTo(Integer value) {
            addCriterion("Tickets <>", value, "tickets");
            return (Criteria) this;
        }

        public Criteria andTicketsGreaterThan(Integer value) {
            addCriterion("Tickets >", value, "tickets");
            return (Criteria) this;
        }

        public Criteria andTicketsGreaterThanOrEqualTo(Integer value) {
            addCriterion("Tickets >=", value, "tickets");
            return (Criteria) this;
        }

        public Criteria andTicketsLessThan(Integer value) {
            addCriterion("Tickets <", value, "tickets");
            return (Criteria) this;
        }

        public Criteria andTicketsLessThanOrEqualTo(Integer value) {
            addCriterion("Tickets <=", value, "tickets");
            return (Criteria) this;
        }

        public Criteria andTicketsIn(List<Integer> values) {
            addCriterion("Tickets in", values, "tickets");
            return (Criteria) this;
        }

        public Criteria andTicketsNotIn(List<Integer> values) {
            addCriterion("Tickets not in", values, "tickets");
            return (Criteria) this;
        }

        public Criteria andTicketsBetween(Integer value1, Integer value2) {
            addCriterion("Tickets between", value1, value2, "tickets");
            return (Criteria) this;
        }

        public Criteria andTicketsNotBetween(Integer value1, Integer value2) {
            addCriterion("Tickets not between", value1, value2, "tickets");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonIsNull() {
            addCriterion("LimitPerPerson is null");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonIsNotNull() {
            addCriterion("LimitPerPerson is not null");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonEqualTo(Integer value) {
            addCriterion("LimitPerPerson =", value, "limitPerPerson");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonNotEqualTo(Integer value) {
            addCriterion("LimitPerPerson <>", value, "limitPerPerson");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonGreaterThan(Integer value) {
            addCriterion("LimitPerPerson >", value, "limitPerPerson");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonGreaterThanOrEqualTo(Integer value) {
            addCriterion("LimitPerPerson >=", value, "limitPerPerson");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonLessThan(Integer value) {
            addCriterion("LimitPerPerson <", value, "limitPerPerson");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonLessThanOrEqualTo(Integer value) {
            addCriterion("LimitPerPerson <=", value, "limitPerPerson");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonIn(List<Integer> values) {
            addCriterion("LimitPerPerson in", values, "limitPerPerson");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonNotIn(List<Integer> values) {
            addCriterion("LimitPerPerson not in", values, "limitPerPerson");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonBetween(Integer value1, Integer value2) {
            addCriterion("LimitPerPerson between", value1, value2, "limitPerPerson");
            return (Criteria) this;
        }

        public Criteria andLimitPerPersonNotBetween(Integer value1, Integer value2) {
            addCriterion("LimitPerPerson not between", value1, value2, "limitPerPerson");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusIsNull() {
            addCriterion("OperatingStatus is null");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusIsNotNull() {
            addCriterion("OperatingStatus is not null");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusEqualTo(String value) {
            addCriterion("OperatingStatus =", value, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusNotEqualTo(String value) {
            addCriterion("OperatingStatus <>", value, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusGreaterThan(String value) {
            addCriterion("OperatingStatus >", value, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusGreaterThanOrEqualTo(String value) {
            addCriterion("OperatingStatus >=", value, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusLessThan(String value) {
            addCriterion("OperatingStatus <", value, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusLessThanOrEqualTo(String value) {
            addCriterion("OperatingStatus <=", value, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusLike(String value) {
            addCriterion("OperatingStatus like", value, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusNotLike(String value) {
            addCriterion("OperatingStatus not like", value, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusIn(List<String> values) {
            addCriterion("OperatingStatus in", values, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusNotIn(List<String> values) {
            addCriterion("OperatingStatus not in", values, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusBetween(String value1, String value2) {
            addCriterion("OperatingStatus between", value1, value2, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperatingStatusNotBetween(String value1, String value2) {
            addCriterion("OperatingStatus not between", value1, value2, "operatingStatus");
            return (Criteria) this;
        }

        public Criteria andOperationRecordIsNull() {
            addCriterion("OperationRecord is null");
            return (Criteria) this;
        }

        public Criteria andOperationRecordIsNotNull() {
            addCriterion("OperationRecord is not null");
            return (Criteria) this;
        }

        public Criteria andOperationRecordEqualTo(String value) {
            addCriterion("OperationRecord =", value, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andOperationRecordNotEqualTo(String value) {
            addCriterion("OperationRecord <>", value, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andOperationRecordGreaterThan(String value) {
            addCriterion("OperationRecord >", value, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andOperationRecordGreaterThanOrEqualTo(String value) {
            addCriterion("OperationRecord >=", value, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andOperationRecordLessThan(String value) {
            addCriterion("OperationRecord <", value, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andOperationRecordLessThanOrEqualTo(String value) {
            addCriterion("OperationRecord <=", value, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andOperationRecordLike(String value) {
            addCriterion("OperationRecord like", value, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andOperationRecordNotLike(String value) {
            addCriterion("OperationRecord not like", value, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andOperationRecordIn(List<String> values) {
            addCriterion("OperationRecord in", values, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andOperationRecordNotIn(List<String> values) {
            addCriterion("OperationRecord not in", values, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andOperationRecordBetween(String value1, String value2) {
            addCriterion("OperationRecord between", value1, value2, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andOperationRecordNotBetween(String value1, String value2) {
            addCriterion("OperationRecord not between", value1, value2, "operationRecord");
            return (Criteria) this;
        }

        public Criteria andParameterStringIsNull() {
            addCriterion("ParameterString is null");
            return (Criteria) this;
        }

        public Criteria andParameterStringIsNotNull() {
            addCriterion("ParameterString is not null");
            return (Criteria) this;
        }

        public Criteria andParameterStringEqualTo(String value) {
            addCriterion("ParameterString =", value, "parameterString");
            return (Criteria) this;
        }

        public Criteria andParameterStringNotEqualTo(String value) {
            addCriterion("ParameterString <>", value, "parameterString");
            return (Criteria) this;
        }

        public Criteria andParameterStringGreaterThan(String value) {
            addCriterion("ParameterString >", value, "parameterString");
            return (Criteria) this;
        }

        public Criteria andParameterStringGreaterThanOrEqualTo(String value) {
            addCriterion("ParameterString >=", value, "parameterString");
            return (Criteria) this;
        }

        public Criteria andParameterStringLessThan(String value) {
            addCriterion("ParameterString <", value, "parameterString");
            return (Criteria) this;
        }

        public Criteria andParameterStringLessThanOrEqualTo(String value) {
            addCriterion("ParameterString <=", value, "parameterString");
            return (Criteria) this;
        }

        public Criteria andParameterStringLike(String value) {
            addCriterion("ParameterString like", value, "parameterString");
            return (Criteria) this;
        }

        public Criteria andParameterStringNotLike(String value) {
            addCriterion("ParameterString not like", value, "parameterString");
            return (Criteria) this;
        }

        public Criteria andParameterStringIn(List<String> values) {
            addCriterion("ParameterString in", values, "parameterString");
            return (Criteria) this;
        }

        public Criteria andParameterStringNotIn(List<String> values) {
            addCriterion("ParameterString not in", values, "parameterString");
            return (Criteria) this;
        }

        public Criteria andParameterStringBetween(String value1, String value2) {
            addCriterion("ParameterString between", value1, value2, "parameterString");
            return (Criteria) this;
        }

        public Criteria andParameterStringNotBetween(String value1, String value2) {
            addCriterion("ParameterString not between", value1, value2, "parameterString");
            return (Criteria) this;
        }

        public Criteria andURIIsNull() {
            addCriterion("URI is null");
            return (Criteria) this;
        }

        public Criteria andURIIsNotNull() {
            addCriterion("URI is not null");
            return (Criteria) this;
        }

        public Criteria andURIEqualTo(String value) {
            addCriterion("URI =", value, "URI");
            return (Criteria) this;
        }

        public Criteria andURINotEqualTo(String value) {
            addCriterion("URI <>", value, "URI");
            return (Criteria) this;
        }

        public Criteria andURIGreaterThan(String value) {
            addCriterion("URI >", value, "URI");
            return (Criteria) this;
        }

        public Criteria andURIGreaterThanOrEqualTo(String value) {
            addCriterion("URI >=", value, "URI");
            return (Criteria) this;
        }

        public Criteria andURILessThan(String value) {
            addCriterion("URI <", value, "URI");
            return (Criteria) this;
        }

        public Criteria andURILessThanOrEqualTo(String value) {
            addCriterion("URI <=", value, "URI");
            return (Criteria) this;
        }

        public Criteria andURILike(String value) {
            addCriterion("URI like", value, "URI");
            return (Criteria) this;
        }

        public Criteria andURINotLike(String value) {
            addCriterion("URI not like", value, "URI");
            return (Criteria) this;
        }

        public Criteria andURIIn(List<String> values) {
            addCriterion("URI in", values, "URI");
            return (Criteria) this;
        }

        public Criteria andURINotIn(List<String> values) {
            addCriterion("URI not in", values, "URI");
            return (Criteria) this;
        }

        public Criteria andURIBetween(String value1, String value2) {
            addCriterion("URI between", value1, value2, "URI");
            return (Criteria) this;
        }

        public Criteria andURINotBetween(String value1, String value2) {
            addCriterion("URI not between", value1, value2, "URI");
            return (Criteria) this;
        }

        public Criteria andUserNoteIsNull() {
            addCriterion("UserNote is null");
            return (Criteria) this;
        }

        public Criteria andUserNoteIsNotNull() {
            addCriterion("UserNote is not null");
            return (Criteria) this;
        }

        public Criteria andUserNoteEqualTo(String value) {
            addCriterion("UserNote =", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteNotEqualTo(String value) {
            addCriterion("UserNote <>", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteGreaterThan(String value) {
            addCriterion("UserNote >", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteGreaterThanOrEqualTo(String value) {
            addCriterion("UserNote >=", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteLessThan(String value) {
            addCriterion("UserNote <", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteLessThanOrEqualTo(String value) {
            addCriterion("UserNote <=", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteLike(String value) {
            addCriterion("UserNote like", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteNotLike(String value) {
            addCriterion("UserNote not like", value, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteIn(List<String> values) {
            addCriterion("UserNote in", values, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteNotIn(List<String> values) {
            addCriterion("UserNote not in", values, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteBetween(String value1, String value2) {
            addCriterion("UserNote between", value1, value2, "userNote");
            return (Criteria) this;
        }

        public Criteria andUserNoteNotBetween(String value1, String value2) {
            addCriterion("UserNote not between", value1, value2, "userNote");
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