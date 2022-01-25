//package com.example.intelligentequipmentinspectionsystem;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//
//import org.json.JSONException;
//
//import java.io.IOException;
//
//import okhttp3.Authenticator;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.Route;
//
//public class TokenAuthenticator implements Authenticator {
//    private Context context;
//    private MyServiceHolder myServiceHolder;
//
//    public TokenAuthenticator(Context context, MyServiceHolder myServiceHolder) {
//        this.context = context;
//        this.myServiceHolder = myServiceHolder;
//    }
//
//    @Override
//    public Request authenticate(Route route, Response response) throws IOException {
//        if (myServiceHolder == null) {
//            return null;
//        }
//
//        SharedPreferences settings = context.getSharedPreferences("TokenPref", 0);
//
//        String refreshToken = settings.getString("refreshToken", "");
//        String accessToken = settings.getString("accessToken", "");
//
//        retrofit2.Response retrofitResponse = myServiceHolder.get().refreshToken(accessToken, refreshToken).execute();
//
//        if (retrofitResponse != null) {
//            RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse();
//
//            String newAccessToken = "";
//            try {
//                newAccessToken = refreshTokenResponse.getToken();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return response.request().newBuilder()
//                    .header("Authorization", "Bearer "+newAccessToken)
//                    .build();
//        }
//
//        return null;
//    }
//
////    private void saveTokens(String refreshToken, String accessToken) {
////        SharedPreferences sharedPreferences = context.getSharedPreferences("TokenPref", 0);
////        SharedPreferences.Editor editor = sharedPreferences.edit();
////        editor.putString("refreshToken", refreshToken);
////        editor.putString("accessToken", accessToken);
////        editor.apply();
////    }
////private String getRefreshToken() {
////    SharedPreferences sharedPreferences = getSharedPreferences("TokenPref", 0);
////    String refreshToken = sharedPreferences.getString("refreshToken", "");
////    return refreshToken;
////}
////
////    private String getAccessToken() {
////        SharedPreferences sharedPreferences = getSharedPreferences("TokenPref", 0);
////        String accessToken = sharedPreferences.getString("accessToken", "");
////        return accessToken;
////    }
//}