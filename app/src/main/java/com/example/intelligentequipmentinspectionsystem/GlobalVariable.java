package com.example.intelligentequipmentinspectionsystem;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariable {
    public final static String BASE_URL = "http://10.0.2.2:8000/api/";
    public static List<Question> globalQuestions;
    public static String username = "";
    public static String refreshToken = "";
    public static String accessToken = "";
    public static boolean backPressed = false;
}
