//package com.example.intelligentequipmentinspectionsystem;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class RefreshTokenResponse {
//
//    public String getToken() throws IOException, JSONException {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
//        MediaType mediaType = MediaType.parse("application/json");
//        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"refresh\": \""+GlobalVariable.refreshToken+"\"\r\n}");
//        Request request = new Request.Builder()
//                .url(GlobalVariable.BASE_URL + "refresh/")
//                .method("POST", body)
//                .addHeader("refresh", GlobalVariable.refreshToken)
//                .addHeader("Authorization", "Basic YWRtaW46YWRtaW4=")
//                .addHeader("Content-Type", "application/json")
//                .build();
//        Response response = client.newCall(request).execute();
//        JSONObject json = new JSONObject(response.body().string());
//        response.close();
//        System.out.println("refreshToken: " + json.get("refresh"));
//        return json.get("refresh").toString();
//    }
////    private RefreshTokenResponseData data;
////
////
////    public class RefreshTokenResponseData {
////
////        private String token;
////
////        private SignInResponseUser user;
////    }
//
//}