package com.example.intelligentequipmentinspectionsystem;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.inspectionFragment, R.id.historyFragment, R.id.cameraFragment, R.id.settingsFragment)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }


    private String getRefreshToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenPref", 0);
        String refreshToken = sharedPreferences.getString("refreshToken", "");
        return refreshToken;
    }

    private String getAccessToken() {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenPref", 0);
        String accessToken = sharedPreferences.getString("accessToken", "");
        return accessToken;
    }

    @Override
    public boolean onSupportNavigateUp() {
        GlobalVariable.backPressed = true;
        NavController navController = Navigation.findNavController(this,R.id.nav_host_fragment);
        return navController.navigateUp();
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onBackPressed() {
        GlobalVariable.backPressed = true;
        super.onBackPressed();
    }


}