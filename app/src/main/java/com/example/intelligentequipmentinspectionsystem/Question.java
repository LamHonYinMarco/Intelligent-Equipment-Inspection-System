package com.example.intelligentequipmentinspectionsystem;
import java.util.List;

public class Question {
    private String questionId;
    private String questionTitle;
    private String goodOrBad = "null";
    private String reason;

    public Question() {
    }

    public Question(String questionId, String questionTitle, String goodOrBad, String reason) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.goodOrBad = goodOrBad;
        this.reason = reason;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getGoodOrBad() {
        return goodOrBad;
    }

    public void setGoodOrBad(String goodOrBad) {
        this.goodOrBad = goodOrBad;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
