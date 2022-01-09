//package com.example.intelligentequipmentinspectionsystem;
//import java.util.List;
//
//public class Question {
//    private int questionId;
//    private String questionTitle;
//    private List<String> options;
//    private String answer;
//    public enum Type {
//        Radio,
//        Checkbox
//    }
//    private Type type;
//    public Question() {
//    }
//
//    public Question(int questionId, String questionTitle, List<String> options, String answer, Type type) {
//        this.questionId = questionId;
//        this.questionTitle = questionTitle;
//        this.options = options;
//        this.answer = answer;
//        this.type = type;
//    }
//
//    public int getQuestionId() {
//        return questionId;
//    }
//
//    public void setQuestionId(int questionId) {
//        this.questionId = questionId;
//    }
//
//    public String getQuestionTitle() {
//        return questionTitle;
//    }
//
//    public void setQuestionTitle(String questionTitle) {
//        this.questionTitle = questionTitle;
//    }
//
//    public List<String> getOptions() {
//        return options;
//    }
//
//    public void setOptions(List<String> options) {
//        this.options = options;
//    }
//
//    public String getAnswer() {
//        return answer;
//    }
//
//    public void setAnswer(String answer) {
//        this.answer = answer;
//    }
//
//    public Type getType() {
//        return type;
//    }
//
//    public void setType(Type type) {
//        this.type = type;
//    }
//
//    @Override
//    public String toString() {
//        return "Question{" +
//                "questionId=" + questionId +
//                ", questionTitle='" + questionTitle + '\'' +
//                ", options=" + options +
//                ", answer='" + answer + '\'' +
//                ", type=" + type +
//                '}';
//    }
//}
