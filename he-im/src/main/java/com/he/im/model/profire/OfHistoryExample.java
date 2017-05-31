/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-25 Created
 */
package com.he.im.model.profire;

import java.util.ArrayList;
import java.util.List;

public class OfHistoryExample {

    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;

    private boolean needCount;

    public boolean isNeedCount() {
        return needCount;
    }

    public void setNeedCount(boolean needCount) {
        this.needCount = needCount;
    }

    public OfHistoryExample() {
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

    /**
     * 
     * 
     * @author  ${user}
     * @version 1.0 2017-04-25
     */
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
        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }
        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }
        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }
        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }
        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }
        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }
        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }
        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }
        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }
        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }
        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }
        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }
        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }
        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }
        public Criteria andMessageidIsNull() {
            addCriterion("messageID is null");
            return (Criteria) this;
        }
        public Criteria andMessageidIsNotNull() {
            addCriterion("messageID is not null");
            return (Criteria) this;
        }
        public Criteria andMessageidEqualTo(Integer value) {
            addCriterion("messageID =", value, "messageid");
            return (Criteria) this;
        }
        public Criteria andMessageidNotEqualTo(Integer value) {
            addCriterion("messageID <>", value, "messageid");
            return (Criteria) this;
        }
        public Criteria andMessageidGreaterThan(Integer value) {
            addCriterion("messageID >", value, "messageid");
            return (Criteria) this;
        }
        public Criteria andMessageidGreaterThanOrEqualTo(Integer value) {
            addCriterion("messageID >=", value, "messageid");
            return (Criteria) this;
        }
        public Criteria andMessageidLessThan(Integer value) {
            addCriterion("messageID <", value, "messageid");
            return (Criteria) this;
        }
        public Criteria andMessageidLessThanOrEqualTo(Integer value) {
            addCriterion("messageID <=", value, "messageid");
            return (Criteria) this;
        }
        public Criteria andMessageidIn(List<Integer> values) {
            addCriterion("messageID in", values, "messageid");
            return (Criteria) this;
        }
        public Criteria andMessageidNotIn(List<Integer> values) {
            addCriterion("messageID not in", values, "messageid");
            return (Criteria) this;
        }
        public Criteria andMessageidBetween(Integer value1, Integer value2) {
            addCriterion("messageID between", value1, value2, "messageid");
            return (Criteria) this;
        }
        public Criteria andMessageidNotBetween(Integer value1, Integer value2) {
            addCriterion("messageID not between", value1, value2, "messageid");
            return (Criteria) this;
        }
        public Criteria andCreationdateIsNull() {
            addCriterion("creationDate is null");
            return (Criteria) this;
        }
        public Criteria andCreationdateIsNotNull() {
            addCriterion("creationDate is not null");
            return (Criteria) this;
        }
        public Criteria andCreationdateEqualTo(String value) {
            addCriterion("creationDate =", value, "creationdate");
            return (Criteria) this;
        }
        public Criteria andCreationdateNotEqualTo(String value) {
            addCriterion("creationDate <>", value, "creationdate");
            return (Criteria) this;
        }
        public Criteria andCreationdateGreaterThan(String value) {
            addCriterion("creationDate >", value, "creationdate");
            return (Criteria) this;
        }
        public Criteria andCreationdateGreaterThanOrEqualTo(String value) {
            addCriterion("creationDate >=", value, "creationdate");
            return (Criteria) this;
        }
        public Criteria andCreationdateLessThan(String value) {
            addCriterion("creationDate <", value, "creationdate");
            return (Criteria) this;
        }
        public Criteria andCreationdateLessThanOrEqualTo(String value) {
            addCriterion("creationDate <=", value, "creationdate");
            return (Criteria) this;
        }
        public Criteria andCreationdateLike(String value) {
            addCriterion("creationDate like", value, "creationdate");
            return (Criteria) this;
        }
        public Criteria andCreationdateNotLike(String value) {
            addCriterion("creationDate not like", value, "creationdate");
            return (Criteria) this;
        }
        public Criteria andCreationdateIn(List<String> values) {
            addCriterion("creationDate in", values, "creationdate");
            return (Criteria) this;
        }
        public Criteria andCreationdateNotIn(List<String> values) {
            addCriterion("creationDate not in", values, "creationdate");
            return (Criteria) this;
        }
        public Criteria andCreationdateBetween(String value1, String value2) {
            addCriterion("creationDate between", value1, value2, "creationdate");
            return (Criteria) this;
        }
        public Criteria andCreationdateNotBetween(String value1, String value2) {
            addCriterion("creationDate not between", value1, value2, "creationdate");
            return (Criteria) this;
        }
        public Criteria andMessagesizeIsNull() {
            addCriterion("messageSize is null");
            return (Criteria) this;
        }
        public Criteria andMessagesizeIsNotNull() {
            addCriterion("messageSize is not null");
            return (Criteria) this;
        }
        public Criteria andMessagesizeEqualTo(Integer value) {
            addCriterion("messageSize =", value, "messagesize");
            return (Criteria) this;
        }
        public Criteria andMessagesizeNotEqualTo(Integer value) {
            addCriterion("messageSize <>", value, "messagesize");
            return (Criteria) this;
        }
        public Criteria andMessagesizeGreaterThan(Integer value) {
            addCriterion("messageSize >", value, "messagesize");
            return (Criteria) this;
        }
        public Criteria andMessagesizeGreaterThanOrEqualTo(Integer value) {
            addCriterion("messageSize >=", value, "messagesize");
            return (Criteria) this;
        }
        public Criteria andMessagesizeLessThan(Integer value) {
            addCriterion("messageSize <", value, "messagesize");
            return (Criteria) this;
        }
        public Criteria andMessagesizeLessThanOrEqualTo(Integer value) {
            addCriterion("messageSize <=", value, "messagesize");
            return (Criteria) this;
        }
        public Criteria andMessagesizeIn(List<Integer> values) {
            addCriterion("messageSize in", values, "messagesize");
            return (Criteria) this;
        }
        public Criteria andMessagesizeNotIn(List<Integer> values) {
            addCriterion("messageSize not in", values, "messagesize");
            return (Criteria) this;
        }
        public Criteria andMessagesizeBetween(Integer value1, Integer value2) {
            addCriterion("messageSize between", value1, value2, "messagesize");
            return (Criteria) this;
        }
        public Criteria andMessagesizeNotBetween(Integer value1, Integer value2) {
            addCriterion("messageSize not between", value1, value2, "messagesize");
            return (Criteria) this;
        }
        public Criteria andStanzaIsNull() {
            addCriterion("stanza is null");
            return (Criteria) this;
        }
        public Criteria andStanzaIsNotNull() {
            addCriterion("stanza is not null");
            return (Criteria) this;
        }
        public Criteria andStanzaEqualTo(String value) {
            addCriterion("stanza =", value, "stanza");
            return (Criteria) this;
        }
        public Criteria andStanzaNotEqualTo(String value) {
            addCriterion("stanza <>", value, "stanza");
            return (Criteria) this;
        }
        public Criteria andStanzaGreaterThan(String value) {
            addCriterion("stanza >", value, "stanza");
            return (Criteria) this;
        }
        public Criteria andStanzaGreaterThanOrEqualTo(String value) {
            addCriterion("stanza >=", value, "stanza");
            return (Criteria) this;
        }
        public Criteria andStanzaLessThan(String value) {
            addCriterion("stanza <", value, "stanza");
            return (Criteria) this;
        }
        public Criteria andStanzaLessThanOrEqualTo(String value) {
            addCriterion("stanza <=", value, "stanza");
            return (Criteria) this;
        }
        public Criteria andStanzaLike(String value) {
            addCriterion("stanza like", value, "stanza");
            return (Criteria) this;
        }
        public Criteria andStanzaNotLike(String value) {
            addCriterion("stanza not like", value, "stanza");
            return (Criteria) this;
        }
        public Criteria andStanzaIn(List<String> values) {
            addCriterion("stanza in", values, "stanza");
            return (Criteria) this;
        }
        public Criteria andStanzaNotIn(List<String> values) {
            addCriterion("stanza not in", values, "stanza");
            return (Criteria) this;
        }
        public Criteria andStanzaBetween(String value1, String value2) {
            addCriterion("stanza between", value1, value2, "stanza");
            return (Criteria) this;
        }
        public Criteria andStanzaNotBetween(String value1, String value2) {
            addCriterion("stanza not between", value1, value2, "stanza");
            return (Criteria) this;
        }
        public Criteria andTonameIsNull() {
            addCriterion("toname is null");
            return (Criteria) this;
        }
        public Criteria andTonameIsNotNull() {
            addCriterion("toname is not null");
            return (Criteria) this;
        }
        public Criteria andTonameEqualTo(String value) {
            addCriterion("toname =", value, "toname");
            return (Criteria) this;
        }
        public Criteria andTonameNotEqualTo(String value) {
            addCriterion("toname <>", value, "toname");
            return (Criteria) this;
        }
        public Criteria andTonameGreaterThan(String value) {
            addCriterion("toname >", value, "toname");
            return (Criteria) this;
        }
        public Criteria andTonameGreaterThanOrEqualTo(String value) {
            addCriterion("toname >=", value, "toname");
            return (Criteria) this;
        }
        public Criteria andTonameLessThan(String value) {
            addCriterion("toname <", value, "toname");
            return (Criteria) this;
        }
        public Criteria andTonameLessThanOrEqualTo(String value) {
            addCriterion("toname <=", value, "toname");
            return (Criteria) this;
        }
        public Criteria andTonameLike(String value) {
            addCriterion("toname like", value, "toname");
            return (Criteria) this;
        }
        public Criteria andTonameNotLike(String value) {
            addCriterion("toname not like", value, "toname");
            return (Criteria) this;
        }
        public Criteria andTonameIn(List<String> values) {
            addCriterion("toname in", values, "toname");
            return (Criteria) this;
        }
        public Criteria andTonameNotIn(List<String> values) {
            addCriterion("toname not in", values, "toname");
            return (Criteria) this;
        }
        public Criteria andTonameBetween(String value1, String value2) {
            addCriterion("toname between", value1, value2, "toname");
            return (Criteria) this;
        }
        public Criteria andTonameNotBetween(String value1, String value2) {
            addCriterion("toname not between", value1, value2, "toname");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {


        protected Criteria() {
            super();
        }
    }

    /**
     * 
     * 
     * @author  ${user}
     * @version 1.0 2017-04-25
     */
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