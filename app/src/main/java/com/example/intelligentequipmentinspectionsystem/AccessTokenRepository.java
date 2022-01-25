//package com.example.intelligentequipmentinspectionsystem;
//
//import com.google.gson.Gson;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class AccessTokenRepository {
//
//    public AccessTokenRepository() {
//    }
//
//    public String getAccessToken() {
//
//        return GlobalVariable.accessToken;
//    }
//
//    public List<String> loginAndGetTokens(String username, String password) {
//        List<String> tokens = new ArrayList<>();
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"username\": \"" + username + "\",\r\n    \"password\": \"" + password + "\"\r\n}");
//        Request request = new Request.Builder()
//                .url(GlobalVariable.BASE_URL + "login/")
//                .method("POST", body)
//                .addHeader("Content-Type", "application/json")
//                .build();
//        try {
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//
//                }
//
//                @Override
//                public void onResponse(Call call, Response response) throws IOException {
//
//                }
//            });
//            //                    client.newCall(request).enqueue(new Callback() {
////                        @Override
////                        public void onFailure(@NonNull Call call, @NonNull IOException e) {
////                            System.out.println("onFailure");
////                            e.printStackTrace();
////                        }
////
////                        @Override
////                        public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
////                            System.out.println("It went in");
////                            if (response.isSuccessful()) {
////                                try {
////                                    json = new JSONObject(response.body().string());
////                                    response.close();
////                                    System.out.println("refreshToken: " + json.get("refresh") + "\naccessToken: " + json.get("access"));
////                                    refreshToken = json.get("refresh").toString();
////                                    accessToken = json.get("access").toString();
////                                    saveTokens(refreshToken, accessToken);
////                                    GlobalVariable.refreshToken = refreshToken;
////                                    GlobalVariable.accessToken = accessToken;
////                                    saveUsername(username.getText().toString());
////                                    finish();
////                                } catch (JSONException e) {
////                                    e.printStackTrace();
////                                }
////                            } else {
////                                System.out.println("Wrong username or password");
////
////                                // needs to be on UI thread to use toast
////                                Handler handler = new Handler(Looper.getMainLooper());
////                                handler.post(new Runnable() {
////                                    @Override
////                                    public void run() {
////                                        Toast toast = Toast.makeText(Login.this, "Wrong username or password", Toast.LENGTH_SHORT);
////                                        toast.show();
////                                    }
////                                });
////
////                            }
////                        }
////                    });
//        } catch (IOException ioException) {
//
//        }
//
//        return tokens;
//    }
//
//    public String refreshAccessToken() {
//        String refreshToken = GlobalVariable.refreshToken;
//        String accessToken = "";
//
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"refresh\": \"" + refreshToken + "\"\r\n}");
//        ;
//        Request request = new Request.Builder()
//                .url("http://127.0.0.1:8000/api/refresh/")
//                .method("POST", body)
//                .addHeader("refresh", refreshToken)
//                .addHeader("Authorization", "Basic YWRtaW46YWRtaW4=")
//                .addHeader("Content-Type", "application/json")
//                .build();
//        try {
//            Response response = client.newCall(request).execute();
//            // Turn the response into a JSONArray
//            JSONArray array = new JSONArray(response.body().string());
//            // Turn the JSONArray into a JSONObject
//            JSONObject object = array.getJSONObject(0);
//            // Get value from that JSONObject
//            accessToken = object.getString("access");
//        } catch (IOException | JSONException ioException) {
//            System.out.println(ioException);
//        }
//        GlobalVariable.accessToken = accessToken;
//        return accessToken;
//    }
//}
