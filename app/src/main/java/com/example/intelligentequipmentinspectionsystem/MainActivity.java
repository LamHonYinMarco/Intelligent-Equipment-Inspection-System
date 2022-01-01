package com.example.intelligentequipmentinspectionsystem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(bottomNav, navController);

        Question question1 = new Question();
        question1.setQuestionId(1);
        question1.setQuestionTitle("How are you?");
        List<String> options1 = new ArrayList<>(Arrays.asList("Good", "fine", "Bad"));
        question1.setOptions(options1);
        question1.setType(Question.Type.Radio);

        Question question2 = new Question();
        question2.setQuestionId(2);
        question2.setQuestionTitle("What is this?");
        List<String> options2 = new ArrayList<>(Arrays.asList("idk", "fish?", "your mom"));
        question2.setOptions(options2);
        question2.setType(Question.Type.Radio);

        Question question3 = new Question();
        question3.setQuestionId(3);
        question3.setQuestionTitle("What do you want?");
        List<String> options3 = new ArrayList<>(Arrays.asList("this", "that", "those"));
        question3.setOptions(options3);
        question3.setType(Question.Type.Checkbox);

        EquipmentType equipmentTypeA = new EquipmentType();
        equipmentTypeA.setEquipmentTypeId(1);
        equipmentTypeA.setEquipmentTypeName("A");

        Equipment equipment1 = new Equipment();
        equipment1.setEquipmentId(1);
        equipment1.setEquipmentType(equipmentTypeA);

        FormTemplate formTemplate1 = new FormTemplate();
        formTemplate1.setFormTemplateId(1);
        List<Question> questionSet1 = new ArrayList<>();
        questionSet1.add(question1);
        questionSet1.add(question2);
        questionSet1.add(question3);
        System.out.println("questionSet1: " + questionSet1);
        formTemplate1.setQuestions(questionSet1);
        System.out.println("formTemplate1.getQuestions(): " + formTemplate1.getQuestions());
        formTemplate1.setEquipment(equipment1);

        Form form1 = new Form();
        form1.setFormId(1);
        LocalDateTime ldt = LocalDateTime.now();
        form1.setDate(ldt);
        form1.setFormTemplate(formTemplate1);

        form1.getFormTemplate().getQuestions().get(0).setAnswer("0");
        form1.getFormTemplate().getQuestions().get(1).setAnswer("2");
        form1.getFormTemplate().getQuestions().get(2).setAnswer("0,1,2");

        equipment1.addForm(form1);

        System.out.println("Form1: " + form1);
    }

}