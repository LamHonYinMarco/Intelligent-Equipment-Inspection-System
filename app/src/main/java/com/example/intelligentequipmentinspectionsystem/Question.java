package com.example.intelligentequipmentinspectionsystem;
import java.util.List;

public class Question {
    private int questionId;
    private String questionTitle;
    private boolean goodOrBad;
    private String reason;

    public Question() {
    }

    public Question(int questionId, String questionTitle, boolean goodOrBad, String reason) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.goodOrBad = goodOrBad;
        this.reason = reason;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public boolean isGoodOrBad() {
        return goodOrBad;
    }

    public void setGoodOrBad(boolean goodOrBad) {
        this.goodOrBad = goodOrBad;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
