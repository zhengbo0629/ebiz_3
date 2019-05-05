package com.ebiz.model;

import java.util.ArrayList;
import java.util.List;

public class EbizCompanyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public EbizCompanyExample() {
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

        public Criteria andOwnerNameIsNull() {
            addCriterion("OwnerName is null");
            return (Criteria) this;
        }

        public Criteria andOwnerNameIsNotNull() {
            addCriterion("OwnerName is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerNameEqualTo(String value) {
            addCriterion("OwnerName =", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotEqualTo(String value) {
            addCriterion("OwnerName <>", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameGreaterThan(String value) {
            addCriterion("OwnerName >", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameGreaterThanOrEqualTo(String value) {
            addCriterion("OwnerName >=", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameLessThan(String value) {
            addCriterion("OwnerName <", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameLessThanOrEqualTo(String value) {
            addCriterion("OwnerName <=", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameLike(String value) {
            addCriterion("OwnerName like", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotLike(String value) {
            addCriterion("OwnerName not like", value, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameIn(List<String> values) {
            addCriterion("OwnerName in", values, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotIn(List<String> values) {
            addCriterion("OwnerName not in", values, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameBetween(String value1, String value2) {
            addCriterion("OwnerName between", value1, value2, "ownerName");
            return (Criteria) this;
        }

        public Criteria andOwnerNameNotBetween(String value1, String value2) {
            addCriterion("OwnerName not between", value1, value2, "ownerName");
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

        public Criteria andPermisionIsNull() {
            addCriterion("Permision is null");
            return (Criteria) this;
        }

        public Criteria andPermisionIsNotNull() {
            addCriterion("Permision is not null");
            return (Criteria) this;
        }

        public Criteria andPermisionEqualTo(String value) {
            addCriterion("Permision =", value, "permision");
            return (Criteria) this;
        }

        public Criteria andPermisionNotEqualTo(String value) {
            addCriterion("Permision <>", value, "permision");
            return (Criteria) this;
        }

        public Criteria andPermisionGreaterThan(String value) {
            addCriterion("Permision >", value, "permision");
            return (Criteria) this;
        }

        public Criteria andPermisionGreaterThanOrEqualTo(String value) {
            addCriterion("Permision >=", value, "permision");
            return (Criteria) this;
        }

        public Criteria andPermisionLessThan(String value) {
            addCriterion("Permision <", value, "permision");
            return (Criteria) this;
        }

        public Criteria andPermisionLessThanOrEqualTo(String value) {
            addCriterion("Permision <=", value, "permision");
            return (Criteria) this;
        }

        public Criteria andPermisionLike(String value) {
            addCriterion("Permision like", value, "permision");
            return (Criteria) this;
        }

        public Criteria andPermisionNotLike(String value) {
            addCriterion("Permision not like", value, "permision");
            return (Criteria) this;
        }

        public Criteria andPermisionIn(List<String> values) {
            addCriterion("Permision in", values, "permision");
            return (Criteria) this;
        }

        public Criteria andPermisionNotIn(List<String> values) {
            addCriterion("Permision not in", values, "permision");
            return (Criteria) this;
        }

        public Criteria andPermisionBetween(String value1, String value2) {
            addCriterion("Permision between", value1, value2, "permision");
            return (Criteria) this;
        }

        public Criteria andPermisionNotBetween(String value1, String value2) {
            addCriterion("Permision not between", value1, value2, "permision");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNull() {
            addCriterion("Balance is null");
            return (Criteria) this;
        }

        public Criteria andBalanceIsNotNull() {
            addCriterion("Balance is not null");
            return (Criteria) this;
        }

        public Criteria andBalanceEqualTo(Double value) {
            addCriterion("Balance =", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotEqualTo(Double value) {
            addCriterion("Balance <>", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThan(Double value) {
            addCriterion("Balance >", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceGreaterThanOrEqualTo(Double value) {
            addCriterion("Balance >=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThan(Double value) {
            addCriterion("Balance <", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceLessThanOrEqualTo(Double value) {
            addCriterion("Balance <=", value, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceIn(List<Double> values) {
            addCriterion("Balance in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotIn(List<Double> values) {
            addCriterion("Balance not in", values, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceBetween(Double value1, Double value2) {
            addCriterion("Balance between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andBalanceNotBetween(Double value1, Double value2) {
            addCriterion("Balance not between", value1, value2, "balance");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("CreateTime is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("CreateTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(String value) {
            addCriterion("CreateTime =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(String value) {
            addCriterion("CreateTime <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(String value) {
            addCriterion("CreateTime >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(String value) {
            addCriterion("CreateTime >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(String value) {
            addCriterion("CreateTime <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(String value) {
            addCriterion("CreateTime <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLike(String value) {
            addCriterion("CreateTime like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotLike(String value) {
            addCriterion("CreateTime not like", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<String> values) {
            addCriterion("CreateTime in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<String> values) {
            addCriterion("CreateTime not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(String value1, String value2) {
            addCriterion("CreateTime between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(String value1, String value2) {
            addCriterion("CreateTime not between", value1, value2, "createTime");
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

        public Criteria andPayPeriodIsNull() {
            addCriterion("PayPeriod is null");
            return (Criteria) this;
        }

        public Criteria andPayPeriodIsNotNull() {
            addCriterion("PayPeriod is not null");
            return (Criteria) this;
        }

        public Criteria andPayPeriodEqualTo(String value) {
            addCriterion("PayPeriod =", value, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayPeriodNotEqualTo(String value) {
            addCriterion("PayPeriod <>", value, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayPeriodGreaterThan(String value) {
            addCriterion("PayPeriod >", value, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("PayPeriod >=", value, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayPeriodLessThan(String value) {
            addCriterion("PayPeriod <", value, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayPeriodLessThanOrEqualTo(String value) {
            addCriterion("PayPeriod <=", value, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayPeriodLike(String value) {
            addCriterion("PayPeriod like", value, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayPeriodNotLike(String value) {
            addCriterion("PayPeriod not like", value, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayPeriodIn(List<String> values) {
            addCriterion("PayPeriod in", values, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayPeriodNotIn(List<String> values) {
            addCriterion("PayPeriod not in", values, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayPeriodBetween(String value1, String value2) {
            addCriterion("PayPeriod between", value1, value2, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayPeriodNotBetween(String value1, String value2) {
            addCriterion("PayPeriod not between", value1, value2, "payPeriod");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNull() {
            addCriterion("PayTime is null");
            return (Criteria) this;
        }

        public Criteria andPayTimeIsNotNull() {
            addCriterion("PayTime is not null");
            return (Criteria) this;
        }

        public Criteria andPayTimeEqualTo(String value) {
            addCriterion("PayTime =", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotEqualTo(String value) {
            addCriterion("PayTime <>", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThan(String value) {
            addCriterion("PayTime >", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeGreaterThanOrEqualTo(String value) {
            addCriterion("PayTime >=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThan(String value) {
            addCriterion("PayTime <", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLessThanOrEqualTo(String value) {
            addCriterion("PayTime <=", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeLike(String value) {
            addCriterion("PayTime like", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotLike(String value) {
            addCriterion("PayTime not like", value, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeIn(List<String> values) {
            addCriterion("PayTime in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotIn(List<String> values) {
            addCriterion("PayTime not in", values, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeBetween(String value1, String value2) {
            addCriterion("PayTime between", value1, value2, "payTime");
            return (Criteria) this;
        }

        public Criteria andPayTimeNotBetween(String value1, String value2) {
            addCriterion("PayTime not between", value1, value2, "payTime");
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

        public Criteria andEmailPasswordIsNull() {
            addCriterion("EmailPassword is null");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordIsNotNull() {
            addCriterion("EmailPassword is not null");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordEqualTo(String value) {
            addCriterion("EmailPassword =", value, "emailPassword");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordNotEqualTo(String value) {
            addCriterion("EmailPassword <>", value, "emailPassword");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordGreaterThan(String value) {
            addCriterion("EmailPassword >", value, "emailPassword");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("EmailPassword >=", value, "emailPassword");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordLessThan(String value) {
            addCriterion("EmailPassword <", value, "emailPassword");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordLessThanOrEqualTo(String value) {
            addCriterion("EmailPassword <=", value, "emailPassword");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordLike(String value) {
            addCriterion("EmailPassword like", value, "emailPassword");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordNotLike(String value) {
            addCriterion("EmailPassword not like", value, "emailPassword");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordIn(List<String> values) {
            addCriterion("EmailPassword in", values, "emailPassword");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordNotIn(List<String> values) {
            addCriterion("EmailPassword not in", values, "emailPassword");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordBetween(String value1, String value2) {
            addCriterion("EmailPassword between", value1, value2, "emailPassword");
            return (Criteria) this;
        }

        public Criteria andEmailPasswordNotBetween(String value1, String value2) {
            addCriterion("EmailPassword not between", value1, value2, "emailPassword");
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

        public Criteria andUserManualIsNull() {
            addCriterion("UserManual is null");
            return (Criteria) this;
        }

        public Criteria andUserManualIsNotNull() {
            addCriterion("UserManual is not null");
            return (Criteria) this;
        }

        public Criteria andUserManualEqualTo(String value) {
            addCriterion("UserManual =", value, "userManual");
            return (Criteria) this;
        }

        public Criteria andUserManualNotEqualTo(String value) {
            addCriterion("UserManual <>", value, "userManual");
            return (Criteria) this;
        }

        public Criteria andUserManualGreaterThan(String value) {
            addCriterion("UserManual >", value, "userManual");
            return (Criteria) this;
        }

        public Criteria andUserManualGreaterThanOrEqualTo(String value) {
            addCriterion("UserManual >=", value, "userManual");
            return (Criteria) this;
        }

        public Criteria andUserManualLessThan(String value) {
            addCriterion("UserManual <", value, "userManual");
            return (Criteria) this;
        }

        public Criteria andUserManualLessThanOrEqualTo(String value) {
            addCriterion("UserManual <=", value, "userManual");
            return (Criteria) this;
        }

        public Criteria andUserManualLike(String value) {
            addCriterion("UserManual like", value, "userManual");
            return (Criteria) this;
        }

        public Criteria andUserManualNotLike(String value) {
            addCriterion("UserManual not like", value, "userManual");
            return (Criteria) this;
        }

        public Criteria andUserManualIn(List<String> values) {
            addCriterion("UserManual in", values, "userManual");
            return (Criteria) this;
        }

        public Criteria andUserManualNotIn(List<String> values) {
            addCriterion("UserManual not in", values, "userManual");
            return (Criteria) this;
        }

        public Criteria andUserManualBetween(String value1, String value2) {
            addCriterion("UserManual between", value1, value2, "userManual");
            return (Criteria) this;
        }

        public Criteria andUserManualNotBetween(String value1, String value2) {
            addCriterion("UserManual not between", value1, value2, "userManual");
            return (Criteria) this;
        }

        public Criteria andAddressName1IsNull() {
            addCriterion("AddressName1 is null");
            return (Criteria) this;
        }

        public Criteria andAddressName1IsNotNull() {
            addCriterion("AddressName1 is not null");
            return (Criteria) this;
        }

        public Criteria andAddressName1EqualTo(String value) {
            addCriterion("AddressName1 =", value, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressName1NotEqualTo(String value) {
            addCriterion("AddressName1 <>", value, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressName1GreaterThan(String value) {
            addCriterion("AddressName1 >", value, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressName1GreaterThanOrEqualTo(String value) {
            addCriterion("AddressName1 >=", value, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressName1LessThan(String value) {
            addCriterion("AddressName1 <", value, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressName1LessThanOrEqualTo(String value) {
            addCriterion("AddressName1 <=", value, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressName1Like(String value) {
            addCriterion("AddressName1 like", value, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressName1NotLike(String value) {
            addCriterion("AddressName1 not like", value, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressName1In(List<String> values) {
            addCriterion("AddressName1 in", values, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressName1NotIn(List<String> values) {
            addCriterion("AddressName1 not in", values, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressName1Between(String value1, String value2) {
            addCriterion("AddressName1 between", value1, value2, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressName1NotBetween(String value1, String value2) {
            addCriterion("AddressName1 not between", value1, value2, "addressName1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1IsNull() {
            addCriterion("AddressDetail1 is null");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1IsNotNull() {
            addCriterion("AddressDetail1 is not null");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1EqualTo(String value) {
            addCriterion("AddressDetail1 =", value, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1NotEqualTo(String value) {
            addCriterion("AddressDetail1 <>", value, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1GreaterThan(String value) {
            addCriterion("AddressDetail1 >", value, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1GreaterThanOrEqualTo(String value) {
            addCriterion("AddressDetail1 >=", value, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1LessThan(String value) {
            addCriterion("AddressDetail1 <", value, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1LessThanOrEqualTo(String value) {
            addCriterion("AddressDetail1 <=", value, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1Like(String value) {
            addCriterion("AddressDetail1 like", value, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1NotLike(String value) {
            addCriterion("AddressDetail1 not like", value, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1In(List<String> values) {
            addCriterion("AddressDetail1 in", values, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1NotIn(List<String> values) {
            addCriterion("AddressDetail1 not in", values, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1Between(String value1, String value2) {
            addCriterion("AddressDetail1 between", value1, value2, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressDetail1NotBetween(String value1, String value2) {
            addCriterion("AddressDetail1 not between", value1, value2, "addressDetail1");
            return (Criteria) this;
        }

        public Criteria andAddressName2IsNull() {
            addCriterion("AddressName2 is null");
            return (Criteria) this;
        }

        public Criteria andAddressName2IsNotNull() {
            addCriterion("AddressName2 is not null");
            return (Criteria) this;
        }

        public Criteria andAddressName2EqualTo(String value) {
            addCriterion("AddressName2 =", value, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressName2NotEqualTo(String value) {
            addCriterion("AddressName2 <>", value, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressName2GreaterThan(String value) {
            addCriterion("AddressName2 >", value, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressName2GreaterThanOrEqualTo(String value) {
            addCriterion("AddressName2 >=", value, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressName2LessThan(String value) {
            addCriterion("AddressName2 <", value, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressName2LessThanOrEqualTo(String value) {
            addCriterion("AddressName2 <=", value, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressName2Like(String value) {
            addCriterion("AddressName2 like", value, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressName2NotLike(String value) {
            addCriterion("AddressName2 not like", value, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressName2In(List<String> values) {
            addCriterion("AddressName2 in", values, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressName2NotIn(List<String> values) {
            addCriterion("AddressName2 not in", values, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressName2Between(String value1, String value2) {
            addCriterion("AddressName2 between", value1, value2, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressName2NotBetween(String value1, String value2) {
            addCriterion("AddressName2 not between", value1, value2, "addressName2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2IsNull() {
            addCriterion("AddressDetail2 is null");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2IsNotNull() {
            addCriterion("AddressDetail2 is not null");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2EqualTo(String value) {
            addCriterion("AddressDetail2 =", value, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2NotEqualTo(String value) {
            addCriterion("AddressDetail2 <>", value, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2GreaterThan(String value) {
            addCriterion("AddressDetail2 >", value, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2GreaterThanOrEqualTo(String value) {
            addCriterion("AddressDetail2 >=", value, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2LessThan(String value) {
            addCriterion("AddressDetail2 <", value, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2LessThanOrEqualTo(String value) {
            addCriterion("AddressDetail2 <=", value, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2Like(String value) {
            addCriterion("AddressDetail2 like", value, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2NotLike(String value) {
            addCriterion("AddressDetail2 not like", value, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2In(List<String> values) {
            addCriterion("AddressDetail2 in", values, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2NotIn(List<String> values) {
            addCriterion("AddressDetail2 not in", values, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2Between(String value1, String value2) {
            addCriterion("AddressDetail2 between", value1, value2, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressDetail2NotBetween(String value1, String value2) {
            addCriterion("AddressDetail2 not between", value1, value2, "addressDetail2");
            return (Criteria) this;
        }

        public Criteria andAddressName3IsNull() {
            addCriterion("AddressName3 is null");
            return (Criteria) this;
        }

        public Criteria andAddressName3IsNotNull() {
            addCriterion("AddressName3 is not null");
            return (Criteria) this;
        }

        public Criteria andAddressName3EqualTo(String value) {
            addCriterion("AddressName3 =", value, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressName3NotEqualTo(String value) {
            addCriterion("AddressName3 <>", value, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressName3GreaterThan(String value) {
            addCriterion("AddressName3 >", value, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressName3GreaterThanOrEqualTo(String value) {
            addCriterion("AddressName3 >=", value, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressName3LessThan(String value) {
            addCriterion("AddressName3 <", value, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressName3LessThanOrEqualTo(String value) {
            addCriterion("AddressName3 <=", value, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressName3Like(String value) {
            addCriterion("AddressName3 like", value, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressName3NotLike(String value) {
            addCriterion("AddressName3 not like", value, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressName3In(List<String> values) {
            addCriterion("AddressName3 in", values, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressName3NotIn(List<String> values) {
            addCriterion("AddressName3 not in", values, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressName3Between(String value1, String value2) {
            addCriterion("AddressName3 between", value1, value2, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressName3NotBetween(String value1, String value2) {
            addCriterion("AddressName3 not between", value1, value2, "addressName3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3IsNull() {
            addCriterion("AddressDetail3 is null");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3IsNotNull() {
            addCriterion("AddressDetail3 is not null");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3EqualTo(String value) {
            addCriterion("AddressDetail3 =", value, "addressDetail3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3NotEqualTo(String value) {
            addCriterion("AddressDetail3 <>", value, "addressDetail3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3GreaterThan(String value) {
            addCriterion("AddressDetail3 >", value, "addressDetail3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3GreaterThanOrEqualTo(String value) {
            addCriterion("AddressDetail3 >=", value, "addressDetail3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3LessThan(String value) {
            addCriterion("AddressDetail3 <", value, "addressDetail3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3LessThanOrEqualTo(String value) {
            addCriterion("AddressDetail3 <=", value, "addressDetail3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3Like(String value) {
            addCriterion("AddressDetail3 like", value, "addressDetail3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3NotLike(String value) {
            addCriterion("AddressDetail3 not like", value, "addressDetail3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3In(List<String> values) {
            addCriterion("AddressDetail3 in", values, "addressDetail3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3NotIn(List<String> values) {
            addCriterion("AddressDetail3 not in", values, "addressDetail3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3Between(String value1, String value2) {
            addCriterion("AddressDetail3 between", value1, value2, "addressDetail3");
            return (Criteria) this;
        }

        public Criteria andAddressDetail3NotBetween(String value1, String value2) {
            addCriterion("AddressDetail3 not between", value1, value2, "addressDetail3");
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