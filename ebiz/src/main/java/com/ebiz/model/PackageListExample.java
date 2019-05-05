package com.ebiz.model;

import java.util.ArrayList;
import java.util.List;

public class PackageListExample extends PageSplitHelper {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PackageListExample() {
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

        public Criteria andTrackingNumberIsNull() {
            addCriterion("TrackingNumber is null");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberIsNotNull() {
            addCriterion("TrackingNumber is not null");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberEqualTo(String value) {
            addCriterion("TrackingNumber =", value, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberNotEqualTo(String value) {
            addCriterion("TrackingNumber <>", value, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberGreaterThan(String value) {
            addCriterion("TrackingNumber >", value, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberGreaterThanOrEqualTo(String value) {
            addCriterion("TrackingNumber >=", value, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberLessThan(String value) {
            addCriterion("TrackingNumber <", value, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberLessThanOrEqualTo(String value) {
            addCriterion("TrackingNumber <=", value, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberLike(String value) {
            addCriterion("TrackingNumber like", value, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberNotLike(String value) {
            addCriterion("TrackingNumber not like", value, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberIn(List<String> values) {
            addCriterion("TrackingNumber in", values, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberNotIn(List<String> values) {
            addCriterion("TrackingNumber not in", values, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberBetween(String value1, String value2) {
            addCriterion("TrackingNumber between", value1, value2, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andTrackingNumberNotBetween(String value1, String value2) {
            addCriterion("TrackingNumber not between", value1, value2, "trackingNumber");
            return (Criteria) this;
        }

        public Criteria andShipIDIsNull() {
            addCriterion("ShipID is null");
            return (Criteria) this;
        }

        public Criteria andShipIDIsNotNull() {
            addCriterion("ShipID is not null");
            return (Criteria) this;
        }

        public Criteria andShipIDEqualTo(String value) {
            addCriterion("ShipID =", value, "shipID");
            return (Criteria) this;
        }

        public Criteria andShipIDNotEqualTo(String value) {
            addCriterion("ShipID <>", value, "shipID");
            return (Criteria) this;
        }

        public Criteria andShipIDGreaterThan(String value) {
            addCriterion("ShipID >", value, "shipID");
            return (Criteria) this;
        }

        public Criteria andShipIDGreaterThanOrEqualTo(String value) {
            addCriterion("ShipID >=", value, "shipID");
            return (Criteria) this;
        }

        public Criteria andShipIDLessThan(String value) {
            addCriterion("ShipID <", value, "shipID");
            return (Criteria) this;
        }

        public Criteria andShipIDLessThanOrEqualTo(String value) {
            addCriterion("ShipID <=", value, "shipID");
            return (Criteria) this;
        }

        public Criteria andShipIDLike(String value) {
            addCriterion("ShipID like", value, "shipID");
            return (Criteria) this;
        }

        public Criteria andShipIDNotLike(String value) {
            addCriterion("ShipID not like", value, "shipID");
            return (Criteria) this;
        }

        public Criteria andShipIDIn(List<String> values) {
            addCriterion("ShipID in", values, "shipID");
            return (Criteria) this;
        }

        public Criteria andShipIDNotIn(List<String> values) {
            addCriterion("ShipID not in", values, "shipID");
            return (Criteria) this;
        }

        public Criteria andShipIDBetween(String value1, String value2) {
            addCriterion("ShipID between", value1, value2, "shipID");
            return (Criteria) this;
        }

        public Criteria andShipIDNotBetween(String value1, String value2) {
            addCriterion("ShipID not between", value1, value2, "shipID");
            return (Criteria) this;
        }

        public Criteria andModelNumberIsNull() {
            addCriterion("ModelNumber is null");
            return (Criteria) this;
        }

        public Criteria andModelNumberIsNotNull() {
            addCriterion("ModelNumber is not null");
            return (Criteria) this;
        }

        public Criteria andModelNumberEqualTo(String value) {
            addCriterion("ModelNumber =", value, "modelNumber");
            return (Criteria) this;
        }

        public Criteria andModelNumberNotEqualTo(String value) {
            addCriterion("ModelNumber <>", value, "modelNumber");
            return (Criteria) this;
        }

        public Criteria andModelNumberGreaterThan(String value) {
            addCriterion("ModelNumber >", value, "modelNumber");
            return (Criteria) this;
        }

        public Criteria andModelNumberGreaterThanOrEqualTo(String value) {
            addCriterion("ModelNumber >=", value, "modelNumber");
            return (Criteria) this;
        }

        public Criteria andModelNumberLessThan(String value) {
            addCriterion("ModelNumber <", value, "modelNumber");
            return (Criteria) this;
        }

        public Criteria andModelNumberLessThanOrEqualTo(String value) {
            addCriterion("ModelNumber <=", value, "modelNumber");
            return (Criteria) this;
        }

        public Criteria andModelNumberLike(String value) {
            addCriterion("ModelNumber like", value, "modelNumber");
            return (Criteria) this;
        }

        public Criteria andModelNumberNotLike(String value) {
            addCriterion("ModelNumber not like", value, "modelNumber");
            return (Criteria) this;
        }

        public Criteria andModelNumberIn(List<String> values) {
            addCriterion("ModelNumber in", values, "modelNumber");
            return (Criteria) this;
        }

        public Criteria andModelNumberNotIn(List<String> values) {
            addCriterion("ModelNumber not in", values, "modelNumber");
            return (Criteria) this;
        }

        public Criteria andModelNumberBetween(String value1, String value2) {
            addCriterion("ModelNumber between", value1, value2, "modelNumber");
            return (Criteria) this;
        }

        public Criteria andModelNumberNotBetween(String value1, String value2) {
            addCriterion("ModelNumber not between", value1, value2, "modelNumber");
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

        public Criteria andBasePriceIsNull() {
            addCriterion("BasePrice is null");
            return (Criteria) this;
        }

        public Criteria andBasePriceIsNotNull() {
            addCriterion("BasePrice is not null");
            return (Criteria) this;
        }

        public Criteria andBasePriceEqualTo(Double value) {
            addCriterion("BasePrice =", value, "basePrice");
            return (Criteria) this;
        }

        public Criteria andBasePriceNotEqualTo(Double value) {
            addCriterion("BasePrice <>", value, "basePrice");
            return (Criteria) this;
        }

        public Criteria andBasePriceGreaterThan(Double value) {
            addCriterion("BasePrice >", value, "basePrice");
            return (Criteria) this;
        }

        public Criteria andBasePriceGreaterThanOrEqualTo(Double value) {
            addCriterion("BasePrice >=", value, "basePrice");
            return (Criteria) this;
        }

        public Criteria andBasePriceLessThan(Double value) {
            addCriterion("BasePrice <", value, "basePrice");
            return (Criteria) this;
        }

        public Criteria andBasePriceLessThanOrEqualTo(Double value) {
            addCriterion("BasePrice <=", value, "basePrice");
            return (Criteria) this;
        }

        public Criteria andBasePriceIn(List<Double> values) {
            addCriterion("BasePrice in", values, "basePrice");
            return (Criteria) this;
        }

        public Criteria andBasePriceNotIn(List<Double> values) {
            addCriterion("BasePrice not in", values, "basePrice");
            return (Criteria) this;
        }

        public Criteria andBasePriceBetween(Double value1, Double value2) {
            addCriterion("BasePrice between", value1, value2, "basePrice");
            return (Criteria) this;
        }

        public Criteria andBasePriceNotBetween(Double value1, Double value2) {
            addCriterion("BasePrice not between", value1, value2, "basePrice");
            return (Criteria) this;
        }

        public Criteria andPromPriceIsNull() {
            addCriterion("PromPrice is null");
            return (Criteria) this;
        }

        public Criteria andPromPriceIsNotNull() {
            addCriterion("PromPrice is not null");
            return (Criteria) this;
        }

        public Criteria andPromPriceEqualTo(Double value) {
            addCriterion("PromPrice =", value, "promPrice");
            return (Criteria) this;
        }

        public Criteria andPromPriceNotEqualTo(Double value) {
            addCriterion("PromPrice <>", value, "promPrice");
            return (Criteria) this;
        }

        public Criteria andPromPriceGreaterThan(Double value) {
            addCriterion("PromPrice >", value, "promPrice");
            return (Criteria) this;
        }

        public Criteria andPromPriceGreaterThanOrEqualTo(Double value) {
            addCriterion("PromPrice >=", value, "promPrice");
            return (Criteria) this;
        }

        public Criteria andPromPriceLessThan(Double value) {
            addCriterion("PromPrice <", value, "promPrice");
            return (Criteria) this;
        }

        public Criteria andPromPriceLessThanOrEqualTo(Double value) {
            addCriterion("PromPrice <=", value, "promPrice");
            return (Criteria) this;
        }

        public Criteria andPromPriceIn(List<Double> values) {
            addCriterion("PromPrice in", values, "promPrice");
            return (Criteria) this;
        }

        public Criteria andPromPriceNotIn(List<Double> values) {
            addCriterion("PromPrice not in", values, "promPrice");
            return (Criteria) this;
        }

        public Criteria andPromPriceBetween(Double value1, Double value2) {
            addCriterion("PromPrice between", value1, value2, "promPrice");
            return (Criteria) this;
        }

        public Criteria andPromPriceNotBetween(Double value1, Double value2) {
            addCriterion("PromPrice not between", value1, value2, "promPrice");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNull() {
            addCriterion("Quantity is null");
            return (Criteria) this;
        }

        public Criteria andQuantityIsNotNull() {
            addCriterion("Quantity is not null");
            return (Criteria) this;
        }

        public Criteria andQuantityEqualTo(Integer value) {
            addCriterion("Quantity =", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotEqualTo(Integer value) {
            addCriterion("Quantity <>", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThan(Integer value) {
            addCriterion("Quantity >", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("Quantity >=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThan(Integer value) {
            addCriterion("Quantity <", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("Quantity <=", value, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityIn(List<Integer> values) {
            addCriterion("Quantity in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotIn(List<Integer> values) {
            addCriterion("Quantity not in", values, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityBetween(Integer value1, Integer value2) {
            addCriterion("Quantity between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("Quantity not between", value1, value2, "quantity");
            return (Criteria) this;
        }

        public Criteria andPromQuantityIsNull() {
            addCriterion("PromQuantity is null");
            return (Criteria) this;
        }

        public Criteria andPromQuantityIsNotNull() {
            addCriterion("PromQuantity is not null");
            return (Criteria) this;
        }

        public Criteria andPromQuantityEqualTo(Integer value) {
            addCriterion("PromQuantity =", value, "promQuantity");
            return (Criteria) this;
        }

        public Criteria andPromQuantityNotEqualTo(Integer value) {
            addCriterion("PromQuantity <>", value, "promQuantity");
            return (Criteria) this;
        }

        public Criteria andPromQuantityGreaterThan(Integer value) {
            addCriterion("PromQuantity >", value, "promQuantity");
            return (Criteria) this;
        }

        public Criteria andPromQuantityGreaterThanOrEqualTo(Integer value) {
            addCriterion("PromQuantity >=", value, "promQuantity");
            return (Criteria) this;
        }

        public Criteria andPromQuantityLessThan(Integer value) {
            addCriterion("PromQuantity <", value, "promQuantity");
            return (Criteria) this;
        }

        public Criteria andPromQuantityLessThanOrEqualTo(Integer value) {
            addCriterion("PromQuantity <=", value, "promQuantity");
            return (Criteria) this;
        }

        public Criteria andPromQuantityIn(List<Integer> values) {
            addCriterion("PromQuantity in", values, "promQuantity");
            return (Criteria) this;
        }

        public Criteria andPromQuantityNotIn(List<Integer> values) {
            addCriterion("PromQuantity not in", values, "promQuantity");
            return (Criteria) this;
        }

        public Criteria andPromQuantityBetween(Integer value1, Integer value2) {
            addCriterion("PromQuantity between", value1, value2, "promQuantity");
            return (Criteria) this;
        }

        public Criteria andPromQuantityNotBetween(Integer value1, Integer value2) {
            addCriterion("PromQuantity not between", value1, value2, "promQuantity");
            return (Criteria) this;
        }

        public Criteria andStoreNameIsNull() {
            addCriterion("StoreName is null");
            return (Criteria) this;
        }

        public Criteria andStoreNameIsNotNull() {
            addCriterion("StoreName is not null");
            return (Criteria) this;
        }

        public Criteria andStoreNameEqualTo(String value) {
            addCriterion("StoreName =", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameNotEqualTo(String value) {
            addCriterion("StoreName <>", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameGreaterThan(String value) {
            addCriterion("StoreName >", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameGreaterThanOrEqualTo(String value) {
            addCriterion("StoreName >=", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameLessThan(String value) {
            addCriterion("StoreName <", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameLessThanOrEqualTo(String value) {
            addCriterion("StoreName <=", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameLike(String value) {
            addCriterion("StoreName like", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameNotLike(String value) {
            addCriterion("StoreName not like", value, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameIn(List<String> values) {
            addCriterion("StoreName in", values, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameNotIn(List<String> values) {
            addCriterion("StoreName not in", values, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameBetween(String value1, String value2) {
            addCriterion("StoreName between", value1, value2, "storeName");
            return (Criteria) this;
        }

        public Criteria andStoreNameNotBetween(String value1, String value2) {
            addCriterion("StoreName not between", value1, value2, "storeName");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("UserName is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("UserName is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("UserName =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("UserName <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("UserName >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("UserName >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("UserName <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("UserName <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("UserName like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("UserName not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("UserName in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("UserName not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("UserName between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("UserName not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andShippingAddressIsNull() {
            addCriterion("ShippingAddress is null");
            return (Criteria) this;
        }

        public Criteria andShippingAddressIsNotNull() {
            addCriterion("ShippingAddress is not null");
            return (Criteria) this;
        }

        public Criteria andShippingAddressEqualTo(String value) {
            addCriterion("ShippingAddress =", value, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andShippingAddressNotEqualTo(String value) {
            addCriterion("ShippingAddress <>", value, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andShippingAddressGreaterThan(String value) {
            addCriterion("ShippingAddress >", value, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andShippingAddressGreaterThanOrEqualTo(String value) {
            addCriterion("ShippingAddress >=", value, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andShippingAddressLessThan(String value) {
            addCriterion("ShippingAddress <", value, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andShippingAddressLessThanOrEqualTo(String value) {
            addCriterion("ShippingAddress <=", value, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andShippingAddressLike(String value) {
            addCriterion("ShippingAddress like", value, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andShippingAddressNotLike(String value) {
            addCriterion("ShippingAddress not like", value, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andShippingAddressIn(List<String> values) {
            addCriterion("ShippingAddress in", values, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andShippingAddressNotIn(List<String> values) {
            addCriterion("ShippingAddress not in", values, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andShippingAddressBetween(String value1, String value2) {
            addCriterion("ShippingAddress between", value1, value2, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andShippingAddressNotBetween(String value1, String value2) {
            addCriterion("ShippingAddress not between", value1, value2, "shippingAddress");
            return (Criteria) this;
        }

        public Criteria andEmailIsNull() {
            addCriterion("Email is null");
            return (Criteria) this;
        }

        public Criteria andEmailIsNotNull() {
            addCriterion("Email is not null");
            return (Criteria) this;
        }

        public Criteria andEmailEqualTo(String value) {
            addCriterion("Email =", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotEqualTo(String value) {
            addCriterion("Email <>", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThan(String value) {
            addCriterion("Email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("Email >=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThan(String value) {
            addCriterion("Email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("Email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLike(String value) {
            addCriterion("Email like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotLike(String value) {
            addCriterion("Email not like", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("Email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("Email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("Email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("Email not between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIsNull() {
            addCriterion("PhoneNumber is null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIsNotNull() {
            addCriterion("PhoneNumber is not null");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberEqualTo(String value) {
            addCriterion("PhoneNumber =", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotEqualTo(String value) {
            addCriterion("PhoneNumber <>", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberGreaterThan(String value) {
            addCriterion("PhoneNumber >", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberGreaterThanOrEqualTo(String value) {
            addCriterion("PhoneNumber >=", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLessThan(String value) {
            addCriterion("PhoneNumber <", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLessThanOrEqualTo(String value) {
            addCriterion("PhoneNumber <=", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberLike(String value) {
            addCriterion("PhoneNumber like", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotLike(String value) {
            addCriterion("PhoneNumber not like", value, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberIn(List<String> values) {
            addCriterion("PhoneNumber in", values, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotIn(List<String> values) {
            addCriterion("PhoneNumber not in", values, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberBetween(String value1, String value2) {
            addCriterion("PhoneNumber between", value1, value2, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andPhoneNumberNotBetween(String value1, String value2) {
            addCriterion("PhoneNumber not between", value1, value2, "phoneNumber");
            return (Criteria) this;
        }

        public Criteria andReceiverIsNull() {
            addCriterion("Receiver is null");
            return (Criteria) this;
        }

        public Criteria andReceiverIsNotNull() {
            addCriterion("Receiver is not null");
            return (Criteria) this;
        }

        public Criteria andReceiverEqualTo(String value) {
            addCriterion("Receiver =", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotEqualTo(String value) {
            addCriterion("Receiver <>", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverGreaterThan(String value) {
            addCriterion("Receiver >", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverGreaterThanOrEqualTo(String value) {
            addCriterion("Receiver >=", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverLessThan(String value) {
            addCriterion("Receiver <", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverLessThanOrEqualTo(String value) {
            addCriterion("Receiver <=", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverLike(String value) {
            addCriterion("Receiver like", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotLike(String value) {
            addCriterion("Receiver not like", value, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverIn(List<String> values) {
            addCriterion("Receiver in", values, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotIn(List<String> values) {
            addCriterion("Receiver not in", values, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverBetween(String value1, String value2) {
            addCriterion("Receiver between", value1, value2, "receiver");
            return (Criteria) this;
        }

        public Criteria andReceiverNotBetween(String value1, String value2) {
            addCriterion("Receiver not between", value1, value2, "receiver");
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

        public Criteria andCreditCardNumberIsNull() {
            addCriterion("CreditCardNumber is null");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberIsNotNull() {
            addCriterion("CreditCardNumber is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberEqualTo(String value) {
            addCriterion("CreditCardNumber =", value, "creditCardNumber");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberNotEqualTo(String value) {
            addCriterion("CreditCardNumber <>", value, "creditCardNumber");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberGreaterThan(String value) {
            addCriterion("CreditCardNumber >", value, "creditCardNumber");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberGreaterThanOrEqualTo(String value) {
            addCriterion("CreditCardNumber >=", value, "creditCardNumber");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberLessThan(String value) {
            addCriterion("CreditCardNumber <", value, "creditCardNumber");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberLessThanOrEqualTo(String value) {
            addCriterion("CreditCardNumber <=", value, "creditCardNumber");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberLike(String value) {
            addCriterion("CreditCardNumber like", value, "creditCardNumber");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberNotLike(String value) {
            addCriterion("CreditCardNumber not like", value, "creditCardNumber");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberIn(List<String> values) {
            addCriterion("CreditCardNumber in", values, "creditCardNumber");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberNotIn(List<String> values) {
            addCriterion("CreditCardNumber not in", values, "creditCardNumber");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberBetween(String value1, String value2) {
            addCriterion("CreditCardNumber between", value1, value2, "creditCardNumber");
            return (Criteria) this;
        }

        public Criteria andCreditCardNumberNotBetween(String value1, String value2) {
            addCriterion("CreditCardNumber not between", value1, value2, "creditCardNumber");
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

        public Criteria andCheckStatusIsNull() {
            addCriterion("CheckStatus is null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIsNotNull() {
            addCriterion("CheckStatus is not null");
            return (Criteria) this;
        }

        public Criteria andCheckStatusEqualTo(String value) {
            addCriterion("CheckStatus =", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotEqualTo(String value) {
            addCriterion("CheckStatus <>", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThan(String value) {
            addCriterion("CheckStatus >", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusGreaterThanOrEqualTo(String value) {
            addCriterion("CheckStatus >=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThan(String value) {
            addCriterion("CheckStatus <", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLessThanOrEqualTo(String value) {
            addCriterion("CheckStatus <=", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusLike(String value) {
            addCriterion("CheckStatus like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotLike(String value) {
            addCriterion("CheckStatus not like", value, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusIn(List<String> values) {
            addCriterion("CheckStatus in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotIn(List<String> values) {
            addCriterion("CheckStatus not in", values, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusBetween(String value1, String value2) {
            addCriterion("CheckStatus between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckStatusNotBetween(String value1, String value2) {
            addCriterion("CheckStatus not between", value1, value2, "checkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckerIsNull() {
            addCriterion("Checker is null");
            return (Criteria) this;
        }

        public Criteria andCheckerIsNotNull() {
            addCriterion("Checker is not null");
            return (Criteria) this;
        }

        public Criteria andCheckerEqualTo(String value) {
            addCriterion("Checker =", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotEqualTo(String value) {
            addCriterion("Checker <>", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThan(String value) {
            addCriterion("Checker >", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerGreaterThanOrEqualTo(String value) {
            addCriterion("Checker >=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThan(String value) {
            addCriterion("Checker <", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLessThanOrEqualTo(String value) {
            addCriterion("Checker <=", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerLike(String value) {
            addCriterion("Checker like", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotLike(String value) {
            addCriterion("Checker not like", value, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerIn(List<String> values) {
            addCriterion("Checker in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotIn(List<String> values) {
            addCriterion("Checker not in", values, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerBetween(String value1, String value2) {
            addCriterion("Checker between", value1, value2, "checker");
            return (Criteria) this;
        }

        public Criteria andCheckerNotBetween(String value1, String value2) {
            addCriterion("Checker not between", value1, value2, "checker");
            return (Criteria) this;
        }

        public Criteria andLabelStatusIsNull() {
            addCriterion("LabelStatus is null");
            return (Criteria) this;
        }

        public Criteria andLabelStatusIsNotNull() {
            addCriterion("LabelStatus is not null");
            return (Criteria) this;
        }

        public Criteria andLabelStatusEqualTo(String value) {
            addCriterion("LabelStatus =", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusNotEqualTo(String value) {
            addCriterion("LabelStatus <>", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusGreaterThan(String value) {
            addCriterion("LabelStatus >", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusGreaterThanOrEqualTo(String value) {
            addCriterion("LabelStatus >=", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusLessThan(String value) {
            addCriterion("LabelStatus <", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusLessThanOrEqualTo(String value) {
            addCriterion("LabelStatus <=", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusLike(String value) {
            addCriterion("LabelStatus like", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusNotLike(String value) {
            addCriterion("LabelStatus not like", value, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusIn(List<String> values) {
            addCriterion("LabelStatus in", values, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusNotIn(List<String> values) {
            addCriterion("LabelStatus not in", values, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusBetween(String value1, String value2) {
            addCriterion("LabelStatus between", value1, value2, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelStatusNotBetween(String value1, String value2) {
            addCriterion("LabelStatus not between", value1, value2, "labelStatus");
            return (Criteria) this;
        }

        public Criteria andLabelerIsNull() {
            addCriterion("Labeler is null");
            return (Criteria) this;
        }

        public Criteria andLabelerIsNotNull() {
            addCriterion("Labeler is not null");
            return (Criteria) this;
        }

        public Criteria andLabelerEqualTo(String value) {
            addCriterion("Labeler =", value, "labeler");
            return (Criteria) this;
        }

        public Criteria andLabelerNotEqualTo(String value) {
            addCriterion("Labeler <>", value, "labeler");
            return (Criteria) this;
        }

        public Criteria andLabelerGreaterThan(String value) {
            addCriterion("Labeler >", value, "labeler");
            return (Criteria) this;
        }

        public Criteria andLabelerGreaterThanOrEqualTo(String value) {
            addCriterion("Labeler >=", value, "labeler");
            return (Criteria) this;
        }

        public Criteria andLabelerLessThan(String value) {
            addCriterion("Labeler <", value, "labeler");
            return (Criteria) this;
        }

        public Criteria andLabelerLessThanOrEqualTo(String value) {
            addCriterion("Labeler <=", value, "labeler");
            return (Criteria) this;
        }

        public Criteria andLabelerLike(String value) {
            addCriterion("Labeler like", value, "labeler");
            return (Criteria) this;
        }

        public Criteria andLabelerNotLike(String value) {
            addCriterion("Labeler not like", value, "labeler");
            return (Criteria) this;
        }

        public Criteria andLabelerIn(List<String> values) {
            addCriterion("Labeler in", values, "labeler");
            return (Criteria) this;
        }

        public Criteria andLabelerNotIn(List<String> values) {
            addCriterion("Labeler not in", values, "labeler");
            return (Criteria) this;
        }

        public Criteria andLabelerBetween(String value1, String value2) {
            addCriterion("Labeler between", value1, value2, "labeler");
            return (Criteria) this;
        }

        public Criteria andLabelerNotBetween(String value1, String value2) {
            addCriterion("Labeler not between", value1, value2, "labeler");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNull() {
            addCriterion("PayStatus is null");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("PayStatus is not null");
            return (Criteria) this;
        }

        public Criteria andPayStatusEqualTo(String value) {
            addCriterion("PayStatus =", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotEqualTo(String value) {
            addCriterion("PayStatus <>", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThan(String value) {
            addCriterion("PayStatus >", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(String value) {
            addCriterion("PayStatus >=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThan(String value) {
            addCriterion("PayStatus <", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(String value) {
            addCriterion("PayStatus <=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLike(String value) {
            addCriterion("PayStatus like", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotLike(String value) {
            addCriterion("PayStatus not like", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusIn(List<String> values) {
            addCriterion("PayStatus in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotIn(List<String> values) {
            addCriterion("PayStatus not in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusBetween(String value1, String value2) {
            addCriterion("PayStatus between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotBetween(String value1, String value2) {
            addCriterion("PayStatus not between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayerIsNull() {
            addCriterion("Payer is null");
            return (Criteria) this;
        }

        public Criteria andPayerIsNotNull() {
            addCriterion("Payer is not null");
            return (Criteria) this;
        }

        public Criteria andPayerEqualTo(String value) {
            addCriterion("Payer =", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotEqualTo(String value) {
            addCriterion("Payer <>", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerGreaterThan(String value) {
            addCriterion("Payer >", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerGreaterThanOrEqualTo(String value) {
            addCriterion("Payer >=", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerLessThan(String value) {
            addCriterion("Payer <", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerLessThanOrEqualTo(String value) {
            addCriterion("Payer <=", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerLike(String value) {
            addCriterion("Payer like", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotLike(String value) {
            addCriterion("Payer not like", value, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerIn(List<String> values) {
            addCriterion("Payer in", values, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotIn(List<String> values) {
            addCriterion("Payer not in", values, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerBetween(String value1, String value2) {
            addCriterion("Payer between", value1, value2, "payer");
            return (Criteria) this;
        }

        public Criteria andPayerNotBetween(String value1, String value2) {
            addCriterion("Payer not between", value1, value2, "payer");
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