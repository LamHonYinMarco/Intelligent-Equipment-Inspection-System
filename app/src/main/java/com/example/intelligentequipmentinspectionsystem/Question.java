package com.example.intelligentequipmentinspectionsystem;

public class Question {
    private String questionId;
    private String questionTitle;
    private String normalOrDefective = "null";
    private String followUpAction;

    public Question() {
    }

    public Question(String questionId, String questionTitle, String normalOrDefective, String followUpAction) {
        this.questionId = questionId;
        this.questionTitle = questionTitle;
        this.normalOrDefective = normalOrDefective;
        this.followUpAction = followUpAction;
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

    public String getNormalOrDefective() {
        return normalOrDefective;
    }

    public void setNormalOrDefective(String normalOrDefective) {
        this.normalOrDefective = normalOrDefective;
    }

    public String getFollowUpAction() {
        return followUpAction;
    }

    public void setFollowUpAction(String followUpAction) {
        this.followUpAction = followUpAction;
    }
}
