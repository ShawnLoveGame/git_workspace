/*
 * Copyright(C) 20xx-20xx
 * All rights reserved.
 * -----------------------------------------------
 * 2017-04-01 Created
 */
package com.he.exam.model.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuestionsExample {

    protected String orderByClause;
    protected boolean distinct;
    protected List<Criteria> oredCriteria;

    private int start;
    private int rows;
    private boolean needCount;



    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public boolean isNeedCount() {
        return needCount;
    }

    public void setNeedCount(boolean needCount) {
        this.needCount = needCount;
    }

    public QuestionsExample() {
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
     * 试题
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
        public Criteria andAnswerTimeIsNull() {
            addCriterion("answer_time is null");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeIsNotNull() {
            addCriterion("answer_time is not null");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeEqualTo(String value) {
            addCriterion("answer_time =", value, "answerTime");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeNotEqualTo(String value) {
            addCriterion("answer_time <>", value, "answerTime");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeGreaterThan(String value) {
            addCriterion("answer_time >", value, "answerTime");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeGreaterThanOrEqualTo(String value) {
            addCriterion("answer_time >=", value, "answerTime");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeLessThan(String value) {
            addCriterion("answer_time <", value, "answerTime");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeLessThanOrEqualTo(String value) {
            addCriterion("answer_time <=", value, "answerTime");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeLike(String value) {
            addCriterion("answer_time like", value, "answerTime");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeNotLike(String value) {
            addCriterion("answer_time not like", value, "answerTime");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeIn(List<String> values) {
            addCriterion("answer_time in", values, "answerTime");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeNotIn(List<String> values) {
            addCriterion("answer_time not in", values, "answerTime");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeBetween(String value1, String value2) {
            addCriterion("answer_time between", value1, value2, "answerTime");
            return (Criteria) this;
        }
        public Criteria andAnswerTimeNotBetween(String value1, String value2) {
            addCriterion("answer_time not between", value1, value2, "answerTime");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeIsNull() {
            addCriterion("question_type is null");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeIsNotNull() {
            addCriterion("question_type is not null");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeEqualTo(Integer value) {
            addCriterion("question_type =", value, "questionType");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeNotEqualTo(Integer value) {
            addCriterion("question_type <>", value, "questionType");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeGreaterThan(Integer value) {
            addCriterion("question_type >", value, "questionType");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("question_type >=", value, "questionType");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeLessThan(Integer value) {
            addCriterion("question_type <", value, "questionType");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeLessThanOrEqualTo(Integer value) {
            addCriterion("question_type <=", value, "questionType");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeIn(List<Integer> values) {
            addCriterion("question_type in", values, "questionType");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeNotIn(List<Integer> values) {
            addCriterion("question_type not in", values, "questionType");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeBetween(Integer value1, Integer value2) {
            addCriterion("question_type between", value1, value2, "questionType");
            return (Criteria) this;
        }
        public Criteria andQuestionTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("question_type not between", value1, value2, "questionType");
            return (Criteria) this;
        }
        public Criteria andLibraryIdIsNull() {
            addCriterion("library_id is null");
            return (Criteria) this;
        }
        public Criteria andLibraryIdIsNotNull() {
            addCriterion("library_id is not null");
            return (Criteria) this;
        }
        public Criteria andLibraryIdEqualTo(Long value) {
            addCriterion("library_id =", value, "libraryId");
            return (Criteria) this;
        }
        public Criteria andLibraryIdNotEqualTo(Long value) {
            addCriterion("library_id <>", value, "libraryId");
            return (Criteria) this;
        }
        public Criteria andLibraryIdGreaterThan(Long value) {
            addCriterion("library_id >", value, "libraryId");
            return (Criteria) this;
        }
        public Criteria andLibraryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("library_id >=", value, "libraryId");
            return (Criteria) this;
        }
        public Criteria andLibraryIdLessThan(Long value) {
            addCriterion("library_id <", value, "libraryId");
            return (Criteria) this;
        }
        public Criteria andLibraryIdLessThanOrEqualTo(Long value) {
            addCriterion("library_id <=", value, "libraryId");
            return (Criteria) this;
        }
        public Criteria andLibraryIdIn(List<Long> values) {
            addCriterion("library_id in", values, "libraryId");
            return (Criteria) this;
        }
        public Criteria andLibraryIdNotIn(List<Long> values) {
            addCriterion("library_id not in", values, "libraryId");
            return (Criteria) this;
        }
        public Criteria andLibraryIdBetween(Long value1, Long value2) {
            addCriterion("library_id between", value1, value2, "libraryId");
            return (Criteria) this;
        }
        public Criteria andLibraryIdNotBetween(Long value1, Long value2) {
            addCriterion("library_id not between", value1, value2, "libraryId");
            return (Criteria) this;
        }
        public Criteria andQuestionContentIsNull() {
            addCriterion("question_content is null");
            return (Criteria) this;
        }
        public Criteria andQuestionContentIsNotNull() {
            addCriterion("question_content is not null");
            return (Criteria) this;
        }
        public Criteria andQuestionContentEqualTo(String value) {
            addCriterion("question_content =", value, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionContentNotEqualTo(String value) {
            addCriterion("question_content <>", value, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionContentGreaterThan(String value) {
            addCriterion("question_content >", value, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionContentGreaterThanOrEqualTo(String value) {
            addCriterion("question_content >=", value, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionContentLessThan(String value) {
            addCriterion("question_content <", value, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionContentLessThanOrEqualTo(String value) {
            addCriterion("question_content <=", value, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionContentLike(String value) {
            addCriterion("question_content like", value, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionContentNotLike(String value) {
            addCriterion("question_content not like", value, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionContentIn(List<String> values) {
            addCriterion("question_content in", values, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionContentNotIn(List<String> values) {
            addCriterion("question_content not in", values, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionContentBetween(String value1, String value2) {
            addCriterion("question_content between", value1, value2, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionContentNotBetween(String value1, String value2) {
            addCriterion("question_content not between", value1, value2, "questionContent");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsIsNull() {
            addCriterion("question_options is null");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsIsNotNull() {
            addCriterion("question_options is not null");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsEqualTo(String value) {
            addCriterion("question_options =", value, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsNotEqualTo(String value) {
            addCriterion("question_options <>", value, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsGreaterThan(String value) {
            addCriterion("question_options >", value, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsGreaterThanOrEqualTo(String value) {
            addCriterion("question_options >=", value, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsLessThan(String value) {
            addCriterion("question_options <", value, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsLessThanOrEqualTo(String value) {
            addCriterion("question_options <=", value, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsLike(String value) {
            addCriterion("question_options like", value, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsNotLike(String value) {
            addCriterion("question_options not like", value, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsIn(List<String> values) {
            addCriterion("question_options in", values, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsNotIn(List<String> values) {
            addCriterion("question_options not in", values, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsBetween(String value1, String value2) {
            addCriterion("question_options between", value1, value2, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andQuestionOptionsNotBetween(String value1, String value2) {
            addCriterion("question_options not between", value1, value2, "questionOptions");
            return (Criteria) this;
        }
        public Criteria andAnswersIsNull() {
            addCriterion("answers is null");
            return (Criteria) this;
        }
        public Criteria andAnswersIsNotNull() {
            addCriterion("answers is not null");
            return (Criteria) this;
        }
        public Criteria andAnswersEqualTo(String value) {
            addCriterion("answers =", value, "answers");
            return (Criteria) this;
        }
        public Criteria andAnswersNotEqualTo(String value) {
            addCriterion("answers <>", value, "answers");
            return (Criteria) this;
        }
        public Criteria andAnswersGreaterThan(String value) {
            addCriterion("answers >", value, "answers");
            return (Criteria) this;
        }
        public Criteria andAnswersGreaterThanOrEqualTo(String value) {
            addCriterion("answers >=", value, "answers");
            return (Criteria) this;
        }
        public Criteria andAnswersLessThan(String value) {
            addCriterion("answers <", value, "answers");
            return (Criteria) this;
        }
        public Criteria andAnswersLessThanOrEqualTo(String value) {
            addCriterion("answers <=", value, "answers");
            return (Criteria) this;
        }
        public Criteria andAnswersLike(String value) {
            addCriterion("answers like", value, "answers");
            return (Criteria) this;
        }
        public Criteria andAnswersNotLike(String value) {
            addCriterion("answers not like", value, "answers");
            return (Criteria) this;
        }
        public Criteria andAnswersIn(List<String> values) {
            addCriterion("answers in", values, "answers");
            return (Criteria) this;
        }
        public Criteria andAnswersNotIn(List<String> values) {
            addCriterion("answers not in", values, "answers");
            return (Criteria) this;
        }
        public Criteria andAnswersBetween(String value1, String value2) {
            addCriterion("answers between", value1, value2, "answers");
            return (Criteria) this;
        }
        public Criteria andAnswersNotBetween(String value1, String value2) {
            addCriterion("answers not between", value1, value2, "answers");
            return (Criteria) this;
        }
        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }
        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }
        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }
        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }
        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }
        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }
        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
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
    }

    public static class Criteria extends GeneratedCriteria {


        protected Criteria() {
            super();
        }
    }

    /**
     * 试题
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