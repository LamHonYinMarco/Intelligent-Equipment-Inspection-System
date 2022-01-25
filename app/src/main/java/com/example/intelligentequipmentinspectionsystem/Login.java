package com.example.intelligentequipmentinspectionsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.http2.Header;

public class Login extends AppCompatActivity {
    EditText username, password;
    Button loginButton;
    String refreshToken = "";
    String accessToken = "";
    JSONObject json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        loginButton = (Button) findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (username.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(Login.this, "Please Enter Username and Password", Toast.LENGTH_SHORT);
                    toast.show();
                } else {

                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .build();
                    MediaType mediaType = MediaType.parse("application/json");
                    RequestBody body = RequestBody.create(mediaType, "{\r\n    \"username\": \"" + username.getText().toString() + "\",\r\n    \"password\": \"" + password.getText().toString() + "\"\r\n}");
                    Request request = new Request.Builder()
                            .url(GlobalVariable.BASE_URL + "login/")
                            .method("POST", body)
                            .addHeader("Content-Type", "application/json")
                            .build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            System.out.println("onFailure");
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                            System.out.println("It went in");
                            if (response.isSuccessful()) {
                                try {
                                    json = new JSONObject(response.body().string());
                                    response.close();
                                    System.out.println("refreshToken: " + json.get("refresh") + "\naccessToken: " + json.get("access"));
                                    refreshToken = json.get("refresh").toString();
                                    accessToken = json.get("access").toString();
                                    saveTokens(refreshToken, accessToken);
                                    GlobalVariable.refreshToken = refreshToken;
                                    GlobalVariable.accessToken = accessToken;
                                    saveUsername(username.getText().toString());
                                    finish();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                System.out.println("Wrong username or password");

                                // needs to be on UI thread to use toast
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast toast = Toast.makeText(Login.this, "Wrong username or password", Toast.LENGTH_SHORT);
                                        toast.show();
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });

    }

    private void saveTokens(String refreshToken, String accessToken) {
        SharedPreferences sharedPreferences = getSharedPreferences("TokenPref", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("refreshToken", refreshToken);
        editor.putString("accessToken", accessToken);
        editor.apply();
    }

    private void saveUsername(String username) {
        SharedPreferences sharedPreferences = getSharedPreferences("UsernamePref", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }

    // close keyboard when tap outside
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
    }
}