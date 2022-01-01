package com.example.intelligentequipmentinspectionsystem;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Form{
    private int formId;
    private LocalDateTime date;
    private FormTemplate formTemplate;
    private String inspectorName;

    public Form() {
    }

    public Form(int formId, LocalDateTime date, FormTemplate formTemplate, String inspectorName) {
        this.formId = formId;
        this.date = date;
        this.formTemplate = formTemplate;
        this.inspectorName = inspectorName;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public FormTemplate getFormTemplate() {
        return formTemplate;
    }

    public void setFormTemplate(FormTemplate formTemplate) {
        this.formTemplate = formTemplate;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    @Override
    public String toString() {
        return "Form{" +
                "formId=" + formId +
                ", date=" + date +
                ", formTemplate=" + formTemplate +
                ", inspectorName='" + inspectorName + '\'' +
                '}';
    }
}
