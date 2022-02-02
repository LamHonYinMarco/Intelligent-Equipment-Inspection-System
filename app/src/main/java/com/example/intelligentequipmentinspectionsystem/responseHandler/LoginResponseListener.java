package com.example.intelligentequipmentinspectionsystem.responseHandler;

import com.example.intelligentequipmentinspectionsystem.AccessTokenAuthenticator;
import com.example.intelligentequipmentinspectionsystem.AccessTokenInterceptor;
import com.example.intelligentequipmentinspectionsystem.GlobalVariable;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginResponseListener implements  ResponseHandler{
    @Override
    public void onError() {

    }

    @Override
    public Boolean onResponse(Boolean login) {
            return login;
    }
}
