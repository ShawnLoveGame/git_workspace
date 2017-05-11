/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-01 Created
 */
package com.he.exam.webapp.model.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamDetailExample {

    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;

    public ExamDetailExample() {
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
     * @version 1.0 2017-04-01
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
        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }
        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }
        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }
        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }
        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }
        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }
        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }
        public Criteria andRuleLevelIsNull() {
            addCriterion("rule_level is null");
            return (Criteria) this;
        }
        public Criteria andRuleLevelIsNotNull() {
            addCriterion("rule_level is not null");
            return (Criteria) this;
        }
        public Criteria andRuleLevelEqualTo(Integer value) {
            addCriterion("rule_level =", value, "ruleLevel");
            return (Criteria) this;
        }
        public Criteria andRuleLevelNotEqualTo(Integer value) {
            addCriterion("rule_level <>", value, "ruleLevel");
            return (Criteria) this;
        }
        public Criteria andRuleLevelGreaterThan(Integer value) {
            addCriterion("rule_level >", value, "ruleLevel");
            return (Criteria) this;
        }
        public Criteria andRuleLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("rule_level >=", value, "ruleLevel");
            return (Criteria) this;
        }
        public Criteria andRuleLevelLessThan(Integer value) {
            addCriterion("rule_level <", value, "ruleLevel");
            return (Criteria) this;
        }
        public Criteria andRuleLevelLessThanOrEqualTo(Integer value) {
            addCriterion("rule_level <=", value, "ruleLevel");
            return (Criteria) this;
        }
        public Criteria andRuleLevelIn(List<Integer> values) {
            addCriterion("rule_level in", values, "ruleLevel");
            return (Criteria) this;
        }
        public Criteria andRuleLevelNotIn(List<Integer> values) {
            addCriterion("rule_level not in", values, "ruleLevel");
            return (Criteria) this;
        }
        public Criteria andRuleLevelBetween(Integer value1, Integer value2) {
            addCriterion("rule_level between", value1, value2, "ruleLevel");
            return (Criteria) this;
        }
        public Criteria andRuleLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("rule_level not between", value1, value2, "ruleLevel");
            return (Criteria) this;
        }
        public Criteria andExamIdIsNull() {
            addCriterion("exam_id is null");
            return (Criteria) this;
        }
        public Criteria andExamIdIsNotNull() {
            addCriterion("exam_id is not null");
            return (Criteria) this;
        }
        public Criteria andExamIdEqualTo(Long value) {
            addCriterion("exam_id =", value, "examId");
            return (Criteria) this;
        }
        public Criteria andExamIdNotEqualTo(Long value) {
            addCriterion("exam_id <>", value, "examId");
            return (Criteria) this;
        }
        public Criteria andExamIdGreaterThan(Long value) {
            addCriterion("exam_id >", value, "examId");
            return (Criteria) this;
        }
        public Criteria andExamIdGreaterThanOrEqualTo(Long value) {
            addCriterion("exam_id >=", value, "examId");
            return (Criteria) this;
        }
        public Criteria andExamIdLessThan(Long value) {
            addCriterion("exam_id <", value, "examId");
            return (Criteria) this;
        }
        public Criteria andExamIdLessThanOrEqualTo(Long value) {
            addCriterion("exam_id <=", value, "examId");
            return (Criteria) this;
        }
        public Criteria andExamIdIn(List<Long> values) {
            addCriterion("exam_id in", values, "examId");
            return (Criteria) this;
        }
        public Criteria andExamIdNotIn(List<Long> values) {
            addCriterion("exam_id not in", values, "examId");
            return (Criteria) this;
        }
        public Criteria andExamIdBetween(Long value1, Long value2) {
            addCriterion("exam_id between", value1, value2, "examId");
            return (Criteria) this;
        }
        public Criteria andExamIdNotBetween(Long value1, Long value2) {
            addCriterion("exam_id not between", value1, value2, "examId");
            return (Criteria) this;
        }
        public Criteria andQuestionIdIsNull() {
            addCriterion("question_id is null");
            return (Criteria) this;
        }
        public Criteria andQuestionIdIsNotNull() {
            addCriterion("question_id is not null");
            return (Criteria) this;
        }
        public Criteria andQuestionIdEqualTo(Long value) {
            addCriterion("question_id =", value, "questionId");
            return (Criteria) this;
        }
        public Criteria andQuestionIdNotEqualTo(Long value) {
            addCriterion("question_id <>", value, "questionId");
            return (Criteria) this;
        }
        public Criteria andQuestionIdGreaterThan(Long value) {
            addCriterion("question_id >", value, "questionId");
            return (Criteria) this;
        }
        public Criteria andQuestionIdGreaterThanOrEqualTo(Long value) {
            addCriterion("question_id >=", value, "questionId");
            return (Criteria) this;
        }
        public Criteria andQuestionIdLessThan(Long value) {
            addCriterion("question_id <", value, "questionId");
            return (Criteria) this;
        }
        public Criteria andQuestionIdLessThanOrEqualTo(Long value) {
            addCriterion("question_id <=", value, "questionId");
            return (Criteria) this;
        }
        public Criteria andQuestionIdIn(List<Long> values) {
            addCriterion("question_id in", values, "questionId");
            return (Criteria) this;
        }
        public Criteria andQuestionIdNotIn(List<Long> values) {
            addCriterion("question_id not in", values, "questionId");
            return (Criteria) this;
        }
        public Criteria andQuestionIdBetween(Long value1, Long value2) {
            addCriterion("question_id between", value1, value2, "questionId");
            return (Criteria) this;
        }
        public Criteria andQuestionIdNotBetween(Long value1, Long value2) {
            addCriterion("question_id not between", value1, value2, "questionId");
            return (Criteria) this;
        }
        public Criteria andDetailIndexIsNull() {
            addCriterion("detail_index is null");
            return (Criteria) this;
        }
        public Criteria andDetailIndexIsNotNull() {
            addCriterion("detail_index is not null");
            return (Criteria) this;
        }
        public Criteria andDetailIndexEqualTo(Integer value) {
            addCriterion("detail_index =", value, "detailIndex");
            return (Criteria) this;
        }
        public Criteria andDetailIndexNotEqualTo(Integer value) {
            addCriterion("detail_index <>", value, "detailIndex");
            return (Criteria) this;
        }
        public Criteria andDetailIndexGreaterThan(Integer value) {
            addCriterion("detail_index >", value, "detailIndex");
            return (Criteria) this;
        }
        public Criteria andDetailIndexGreaterThanOrEqualTo(Integer value) {
            addCriterion("detail_index >=", value, "detailIndex");
            return (Criteria) this;
        }
        public Criteria andDetailIndexLessThan(Integer value) {
            addCriterion("detail_index <", value, "detailIndex");
            return (Criteria) this;
        }
        public Criteria andDetailIndexLessThanOrEqualTo(Integer value) {
            addCriterion("detail_index <=", value, "detailIndex");
            return (Criteria) this;
        }
        public Criteria andDetailIndexIn(List<Integer> values) {
            addCriterion("detail_index in", values, "detailIndex");
            return (Criteria) this;
        }
        public Criteria andDetailIndexNotIn(List<Integer> values) {
            addCriterion("detail_index not in", values, "detailIndex");
            return (Criteria) this;
        }
        public Criteria andDetailIndexBetween(Integer value1, Integer value2) {
            addCriterion("detail_index between", value1, value2, "detailIndex");
            return (Criteria) this;
        }
        public Criteria andDetailIndexNotBetween(Integer value1, Integer value2) {
            addCriterion("detail_index not between", value1, value2, "detailIndex");
            return (Criteria) this;
        }
        public Criteria andRecordIsNull() {
            addCriterion("record is null");
            return (Criteria) this;
        }
        public Criteria andRecordIsNotNull() {
            addCriterion("record is not null");
            return (Criteria) this;
        }
        public Criteria andRecordEqualTo(Integer value) {
            addCriterion("record =", value, "record");
            return (Criteria) this;
        }
        public Criteria andRecordNotEqualTo(Integer value) {
            addCriterion("record <>", value, "record");
            return (Criteria) this;
        }
        public Criteria andRecordGreaterThan(Integer value) {
            addCriterion("record >", value, "record");
            return (Criteria) this;
        }
        public Criteria andRecordGreaterThanOrEqualTo(Integer value) {
            addCriterion("record >=", value, "record");
            return (Criteria) this;
        }
        public Criteria andRecordLessThan(Integer value) {
            addCriterion("record <", value, "record");
            return (Criteria) this;
        }
        public Criteria andRecordLessThanOrEqualTo(Integer value) {
            addCriterion("record <=", value, "record");
            return (Criteria) this;
        }
        public Criteria andRecordIn(List<Integer> values) {
            addCriterion("record in", values, "record");
            return (Criteria) this;
        }
        public Criteria andRecordNotIn(List<Integer> values) {
            addCriterion("record not in", values, "record");
            return (Criteria) this;
        }
        public Criteria andRecordBetween(Integer value1, Integer value2) {
            addCriterion("record between", value1, value2, "record");
            return (Criteria) this;
        }
        public Criteria andRecordNotBetween(Integer value1, Integer value2) {
            addCriterion("record not between", value1, value2, "record");
            return (Criteria) this;
        }
        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }
        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }
        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }
        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
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
     * @version 1.0 2017-04-01
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