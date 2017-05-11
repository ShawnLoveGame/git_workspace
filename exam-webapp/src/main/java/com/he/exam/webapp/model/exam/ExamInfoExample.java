/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-03-29 Created
 */
package com.he.exam.webapp.model.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExamInfoExample {

    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;

    public ExamInfoExample() {
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
     * 考试
     * 
     * @author  ${user}
     * @version 1.0 2017-03-29
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
        public Criteria andExamTitleIsNull() {
            addCriterion("exam_title is null");
            return (Criteria) this;
        }
        public Criteria andExamTitleIsNotNull() {
            addCriterion("exam_title is not null");
            return (Criteria) this;
        }
        public Criteria andExamTitleEqualTo(String value) {
            addCriterion("exam_title =", value, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamTitleNotEqualTo(String value) {
            addCriterion("exam_title <>", value, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamTitleGreaterThan(String value) {
            addCriterion("exam_title >", value, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamTitleGreaterThanOrEqualTo(String value) {
            addCriterion("exam_title >=", value, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamTitleLessThan(String value) {
            addCriterion("exam_title <", value, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamTitleLessThanOrEqualTo(String value) {
            addCriterion("exam_title <=", value, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamTitleLike(String value) {
            addCriterion("exam_title like", value, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamTitleNotLike(String value) {
            addCriterion("exam_title not like", value, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamTitleIn(List<String> values) {
            addCriterion("exam_title in", values, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamTitleNotIn(List<String> values) {
            addCriterion("exam_title not in", values, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamTitleBetween(String value1, String value2) {
            addCriterion("exam_title between", value1, value2, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamTitleNotBetween(String value1, String value2) {
            addCriterion("exam_title not between", value1, value2, "examTitle");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeIsNull() {
            addCriterion("exam_start_time is null");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeIsNotNull() {
            addCriterion("exam_start_time is not null");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeEqualTo(Date value) {
            addCriterion("exam_start_time =", value, "examStartTime");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeNotEqualTo(Date value) {
            addCriterion("exam_start_time <>", value, "examStartTime");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeGreaterThan(Date value) {
            addCriterion("exam_start_time >", value, "examStartTime");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("exam_start_time >=", value, "examStartTime");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeLessThan(Date value) {
            addCriterion("exam_start_time <", value, "examStartTime");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("exam_start_time <=", value, "examStartTime");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeIn(List<Date> values) {
            addCriterion("exam_start_time in", values, "examStartTime");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeNotIn(List<Date> values) {
            addCriterion("exam_start_time not in", values, "examStartTime");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeBetween(Date value1, Date value2) {
            addCriterion("exam_start_time between", value1, value2, "examStartTime");
            return (Criteria) this;
        }
        public Criteria andExamStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("exam_start_time not between", value1, value2, "examStartTime");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeIsNull() {
            addCriterion("exam_end_time is null");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeIsNotNull() {
            addCriterion("exam_end_time is not null");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeEqualTo(Date value) {
            addCriterion("exam_end_time =", value, "examEndTime");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeNotEqualTo(Date value) {
            addCriterion("exam_end_time <>", value, "examEndTime");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeGreaterThan(Date value) {
            addCriterion("exam_end_time >", value, "examEndTime");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("exam_end_time >=", value, "examEndTime");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeLessThan(Date value) {
            addCriterion("exam_end_time <", value, "examEndTime");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("exam_end_time <=", value, "examEndTime");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeIn(List<Date> values) {
            addCriterion("exam_end_time in", values, "examEndTime");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeNotIn(List<Date> values) {
            addCriterion("exam_end_time not in", values, "examEndTime");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeBetween(Date value1, Date value2) {
            addCriterion("exam_end_time between", value1, value2, "examEndTime");
            return (Criteria) this;
        }
        public Criteria andExamEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("exam_end_time not between", value1, value2, "examEndTime");
            return (Criteria) this;
        }
        public Criteria andExamTimesIsNull() {
            addCriterion("exam_times is null");
            return (Criteria) this;
        }
        public Criteria andExamTimesIsNotNull() {
            addCriterion("exam_times is not null");
            return (Criteria) this;
        }
        public Criteria andExamTimesEqualTo(Integer value) {
            addCriterion("exam_times =", value, "examTimes");
            return (Criteria) this;
        }
        public Criteria andExamTimesNotEqualTo(Integer value) {
            addCriterion("exam_times <>", value, "examTimes");
            return (Criteria) this;
        }
        public Criteria andExamTimesGreaterThan(Integer value) {
            addCriterion("exam_times >", value, "examTimes");
            return (Criteria) this;
        }
        public Criteria andExamTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("exam_times >=", value, "examTimes");
            return (Criteria) this;
        }
        public Criteria andExamTimesLessThan(Integer value) {
            addCriterion("exam_times <", value, "examTimes");
            return (Criteria) this;
        }
        public Criteria andExamTimesLessThanOrEqualTo(Integer value) {
            addCriterion("exam_times <=", value, "examTimes");
            return (Criteria) this;
        }
        public Criteria andExamTimesIn(List<Integer> values) {
            addCriterion("exam_times in", values, "examTimes");
            return (Criteria) this;
        }
        public Criteria andExamTimesNotIn(List<Integer> values) {
            addCriterion("exam_times not in", values, "examTimes");
            return (Criteria) this;
        }
        public Criteria andExamTimesBetween(Integer value1, Integer value2) {
            addCriterion("exam_times between", value1, value2, "examTimes");
            return (Criteria) this;
        }
        public Criteria andExamTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("exam_times not between", value1, value2, "examTimes");
            return (Criteria) this;
        }
        public Criteria andExamTypeIsNull() {
            addCriterion("exam_type is null");
            return (Criteria) this;
        }
        public Criteria andExamTypeIsNotNull() {
            addCriterion("exam_type is not null");
            return (Criteria) this;
        }
        public Criteria andExamTypeEqualTo(Integer value) {
            addCriterion("exam_type =", value, "examType");
            return (Criteria) this;
        }
        public Criteria andExamTypeNotEqualTo(Integer value) {
            addCriterion("exam_type <>", value, "examType");
            return (Criteria) this;
        }
        public Criteria andExamTypeGreaterThan(Integer value) {
            addCriterion("exam_type >", value, "examType");
            return (Criteria) this;
        }
        public Criteria andExamTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("exam_type >=", value, "examType");
            return (Criteria) this;
        }
        public Criteria andExamTypeLessThan(Integer value) {
            addCriterion("exam_type <", value, "examType");
            return (Criteria) this;
        }
        public Criteria andExamTypeLessThanOrEqualTo(Integer value) {
            addCriterion("exam_type <=", value, "examType");
            return (Criteria) this;
        }
        public Criteria andExamTypeIn(List<Integer> values) {
            addCriterion("exam_type in", values, "examType");
            return (Criteria) this;
        }
        public Criteria andExamTypeNotIn(List<Integer> values) {
            addCriterion("exam_type not in", values, "examType");
            return (Criteria) this;
        }
        public Criteria andExamTypeBetween(Integer value1, Integer value2) {
            addCriterion("exam_type between", value1, value2, "examType");
            return (Criteria) this;
        }
        public Criteria andExamTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("exam_type not between", value1, value2, "examType");
            return (Criteria) this;
        }
        public Criteria andTotalScoreIsNull() {
            addCriterion("total_score is null");
            return (Criteria) this;
        }
        public Criteria andTotalScoreIsNotNull() {
            addCriterion("total_score is not null");
            return (Criteria) this;
        }
        public Criteria andTotalScoreEqualTo(Integer value) {
            addCriterion("total_score =", value, "totalScore");
            return (Criteria) this;
        }
        public Criteria andTotalScoreNotEqualTo(Integer value) {
            addCriterion("total_score <>", value, "totalScore");
            return (Criteria) this;
        }
        public Criteria andTotalScoreGreaterThan(Integer value) {
            addCriterion("total_score >", value, "totalScore");
            return (Criteria) this;
        }
        public Criteria andTotalScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_score >=", value, "totalScore");
            return (Criteria) this;
        }
        public Criteria andTotalScoreLessThan(Integer value) {
            addCriterion("total_score <", value, "totalScore");
            return (Criteria) this;
        }
        public Criteria andTotalScoreLessThanOrEqualTo(Integer value) {
            addCriterion("total_score <=", value, "totalScore");
            return (Criteria) this;
        }
        public Criteria andTotalScoreIn(List<Integer> values) {
            addCriterion("total_score in", values, "totalScore");
            return (Criteria) this;
        }
        public Criteria andTotalScoreNotIn(List<Integer> values) {
            addCriterion("total_score not in", values, "totalScore");
            return (Criteria) this;
        }
        public Criteria andTotalScoreBetween(Integer value1, Integer value2) {
            addCriterion("total_score between", value1, value2, "totalScore");
            return (Criteria) this;
        }
        public Criteria andTotalScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("total_score not between", value1, value2, "totalScore");
            return (Criteria) this;
        }
        public Criteria andPassScoreIsNull() {
            addCriterion("pass_score is null");
            return (Criteria) this;
        }
        public Criteria andPassScoreIsNotNull() {
            addCriterion("pass_score is not null");
            return (Criteria) this;
        }
        public Criteria andPassScoreEqualTo(Integer value) {
            addCriterion("pass_score =", value, "passScore");
            return (Criteria) this;
        }
        public Criteria andPassScoreNotEqualTo(Integer value) {
            addCriterion("pass_score <>", value, "passScore");
            return (Criteria) this;
        }
        public Criteria andPassScoreGreaterThan(Integer value) {
            addCriterion("pass_score >", value, "passScore");
            return (Criteria) this;
        }
        public Criteria andPassScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("pass_score >=", value, "passScore");
            return (Criteria) this;
        }
        public Criteria andPassScoreLessThan(Integer value) {
            addCriterion("pass_score <", value, "passScore");
            return (Criteria) this;
        }
        public Criteria andPassScoreLessThanOrEqualTo(Integer value) {
            addCriterion("pass_score <=", value, "passScore");
            return (Criteria) this;
        }
        public Criteria andPassScoreIn(List<Integer> values) {
            addCriterion("pass_score in", values, "passScore");
            return (Criteria) this;
        }
        public Criteria andPassScoreNotIn(List<Integer> values) {
            addCriterion("pass_score not in", values, "passScore");
            return (Criteria) this;
        }
        public Criteria andPassScoreBetween(Integer value1, Integer value2) {
            addCriterion("pass_score between", value1, value2, "passScore");
            return (Criteria) this;
        }
        public Criteria andPassScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("pass_score not between", value1, value2, "passScore");
            return (Criteria) this;
        }
        public Criteria andExamContentIsNull() {
            addCriterion("exam_content is null");
            return (Criteria) this;
        }
        public Criteria andExamContentIsNotNull() {
            addCriterion("exam_content is not null");
            return (Criteria) this;
        }
        public Criteria andExamContentEqualTo(String value) {
            addCriterion("exam_content =", value, "examContent");
            return (Criteria) this;
        }
        public Criteria andExamContentNotEqualTo(String value) {
            addCriterion("exam_content <>", value, "examContent");
            return (Criteria) this;
        }
        public Criteria andExamContentGreaterThan(String value) {
            addCriterion("exam_content >", value, "examContent");
            return (Criteria) this;
        }
        public Criteria andExamContentGreaterThanOrEqualTo(String value) {
            addCriterion("exam_content >=", value, "examContent");
            return (Criteria) this;
        }
        public Criteria andExamContentLessThan(String value) {
            addCriterion("exam_content <", value, "examContent");
            return (Criteria) this;
        }
        public Criteria andExamContentLessThanOrEqualTo(String value) {
            addCriterion("exam_content <=", value, "examContent");
            return (Criteria) this;
        }
        public Criteria andExamContentLike(String value) {
            addCriterion("exam_content like", value, "examContent");
            return (Criteria) this;
        }
        public Criteria andExamContentNotLike(String value) {
            addCriterion("exam_content not like", value, "examContent");
            return (Criteria) this;
        }
        public Criteria andExamContentIn(List<String> values) {
            addCriterion("exam_content in", values, "examContent");
            return (Criteria) this;
        }
        public Criteria andExamContentNotIn(List<String> values) {
            addCriterion("exam_content not in", values, "examContent");
            return (Criteria) this;
        }
        public Criteria andExamContentBetween(String value1, String value2) {
            addCriterion("exam_content between", value1, value2, "examContent");
            return (Criteria) this;
        }
        public Criteria andExamContentNotBetween(String value1, String value2) {
            addCriterion("exam_content not between", value1, value2, "examContent");
            return (Criteria) this;
        }
        public Criteria andOpTimeIsNull() {
            addCriterion("op_time is null");
            return (Criteria) this;
        }
        public Criteria andOpTimeIsNotNull() {
            addCriterion("op_time is not null");
            return (Criteria) this;
        }
        public Criteria andOpTimeEqualTo(Date value) {
            addCriterion("op_time =", value, "opTime");
            return (Criteria) this;
        }
        public Criteria andOpTimeNotEqualTo(Date value) {
            addCriterion("op_time <>", value, "opTime");
            return (Criteria) this;
        }
        public Criteria andOpTimeGreaterThan(Date value) {
            addCriterion("op_time >", value, "opTime");
            return (Criteria) this;
        }
        public Criteria andOpTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("op_time >=", value, "opTime");
            return (Criteria) this;
        }
        public Criteria andOpTimeLessThan(Date value) {
            addCriterion("op_time <", value, "opTime");
            return (Criteria) this;
        }
        public Criteria andOpTimeLessThanOrEqualTo(Date value) {
            addCriterion("op_time <=", value, "opTime");
            return (Criteria) this;
        }
        public Criteria andOpTimeIn(List<Date> values) {
            addCriterion("op_time in", values, "opTime");
            return (Criteria) this;
        }
        public Criteria andOpTimeNotIn(List<Date> values) {
            addCriterion("op_time not in", values, "opTime");
            return (Criteria) this;
        }
        public Criteria andOpTimeBetween(Date value1, Date value2) {
            addCriterion("op_time between", value1, value2, "opTime");
            return (Criteria) this;
        }
        public Criteria andOpTimeNotBetween(Date value1, Date value2) {
            addCriterion("op_time not between", value1, value2, "opTime");
            return (Criteria) this;
        }
        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }
        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }
        public Criteria andIsDeletedEqualTo(Integer value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }
        public Criteria andIsDeletedNotEqualTo(Integer value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }
        public Criteria andIsDeletedGreaterThan(Integer value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }
        public Criteria andIsDeletedGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }
        public Criteria andIsDeletedLessThan(Integer value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }
        public Criteria andIsDeletedLessThanOrEqualTo(Integer value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }
        public Criteria andIsDeletedIn(List<Integer> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }
        public Criteria andIsDeletedNotIn(List<Integer> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }
        public Criteria andIsDeletedBetween(Integer value1, Integer value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }
        public Criteria andIsDeletedNotBetween(Integer value1, Integer value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }
        public Criteria andExamStatusIsNull() {
            addCriterion("exam_status is null");
            return (Criteria) this;
        }
        public Criteria andExamStatusIsNotNull() {
            addCriterion("exam_status is not null");
            return (Criteria) this;
        }
        public Criteria andExamStatusEqualTo(Integer value) {
            addCriterion("exam_status =", value, "examStatus");
            return (Criteria) this;
        }
        public Criteria andExamStatusNotEqualTo(Integer value) {
            addCriterion("exam_status <>", value, "examStatus");
            return (Criteria) this;
        }
        public Criteria andExamStatusGreaterThan(Integer value) {
            addCriterion("exam_status >", value, "examStatus");
            return (Criteria) this;
        }
        public Criteria andExamStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("exam_status >=", value, "examStatus");
            return (Criteria) this;
        }
        public Criteria andExamStatusLessThan(Integer value) {
            addCriterion("exam_status <", value, "examStatus");
            return (Criteria) this;
        }
        public Criteria andExamStatusLessThanOrEqualTo(Integer value) {
            addCriterion("exam_status <=", value, "examStatus");
            return (Criteria) this;
        }
        public Criteria andExamStatusIn(List<Integer> values) {
            addCriterion("exam_status in", values, "examStatus");
            return (Criteria) this;
        }
        public Criteria andExamStatusNotIn(List<Integer> values) {
            addCriterion("exam_status not in", values, "examStatus");
            return (Criteria) this;
        }
        public Criteria andExamStatusBetween(Integer value1, Integer value2) {
            addCriterion("exam_status between", value1, value2, "examStatus");
            return (Criteria) this;
        }
        public Criteria andExamStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("exam_status not between", value1, value2, "examStatus");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {


        protected Criteria() {
            super();
        }
    }

    /**
     * 考试
     * 
     * @author  ${user}
     * @version 1.0 2017-03-29
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