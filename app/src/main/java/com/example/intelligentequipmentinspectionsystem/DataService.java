package com.example.intelligentequipmentinspectionsystem;

import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataService {
    JSONArray jsonArray;
    JSONObject jsonObject;
    public DataService() {
    }

    private interface ReturnJsonArray {
        void onError(String message);

        void onResponse(JSONArray jsonArray);
    }

    public interface ReturnJsonObject {
        void onError(String message);

        void onResponse(JSONObject jsonObject);
    }

    public interface ResponseListenerForList {
        void onError(String message);

        void onResponse(List<String> data1, List<String> data2, List<String> id);
    }

    public interface ResponseListenerFor4ListString {
        void onError(String message);

        void onResponse(List<String> data1, List<String> data2, List<String> date, List<String> id);
    }

    public interface ResponseListenerForSingle {
        void onError(String message);

        void onResponse(String data1, String data2);
    }

    public interface ResponseListenerForQuestions {
        void onError(String message);

        void onResponse(List<Question> questions);
    }

    public interface ResponseListenerForFormId {
        void onError(String message);

        void onResponse(HashMap<String, String> equipmentIdForFormId);
    }

    public interface ResponseListenerForSuccess {
        void onError(String message);

        void onResponse(boolean successful);
    }

    public void getRooms(ResponseListenerForList responseListenerForList) {

        List<String> roomNames = new ArrayList<>();
        List<String> roomLocations = new ArrayList<>();
        List<String> roomIds = new ArrayList<>();
        getJSONArray("room", new ReturnJsonArray() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(JSONArray jsonArray) {
//                System.out.println("getRooms jsonArray: " + jsonArray);
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        roomNames.add(jsonArray.getJSONObject(i).getString("room_name"));
                        roomLocations.add(jsonArray.getJSONObject(i).getString("location"));
                        roomIds.add(jsonArray.getJSONObject(i).getString("id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("DataService getRoomName Room Names: " + roomNames);
                System.out.println("DataService getRoomName Room Locations: " + roomLocations);
                System.out.println("DataService getRoomName Room IDs: " + roomIds);
                responseListenerForList.onResponse(roomNames, roomLocations, roomIds);
            }
        });

    }

    public void getForms(ResponseListenerForFormId responseListenerForFormId) {
        HashMap<String,String> equipmentIdForFormId = new HashMap<String,String>();

        getJSONArray("form", new ReturnJsonArray() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(JSONArray jsonArray) {
//                System.out.println("getForms jsonArray: " + jsonArray);
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj1 = jsonArray.getJSONObject(i);
                        JSONArray array = obj1.getJSONArray("equipments");
                        JSONObject obj2 = array.getJSONObject(0);
                        equipmentIdForFormId.put(obj2.getString("equipment_id"),obj1.getString("form_id"));

                        // set answerId
                        GlobalVariable.globalQuestions.get(i).setAnswerId(jsonArray.getJSONObject(0).getString("unique_id"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("DataService getForms equipmentIdForFormId: " + equipmentIdForFormId);
                responseListenerForFormId.onResponse(equipmentIdForFormId);
            }
        });

    }

    public void getQuestions(ResponseListenerForQuestions responseListenerForQuestions) {
        List<Question> questions = new ArrayList<>();
        getJSONArray("question", new ReturnJsonArray() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Question question = new Question();
                        question.setQuestionTitle(jsonArray.getJSONObject(i).getString("question_text"));
                        question.setQuestionId(jsonArray.getJSONObject(i).getString("id"));
                        questions.add(question);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("DataService getQuestions Questions: " + questions.toString());
                responseListenerForQuestions.onResponse(questions);
            }
        });
    }

    public void getEquipmentsByRoomId(String id, ResponseListenerForList responseListenerForList) {

        List<String> equipmentNames = new ArrayList<>();
        List<String> equipmentCode = new ArrayList<>();
        List<String> equipmentId = new ArrayList<>();
        System.out.println("DataService getEquipment Room ID: " + id);
        getJSONArray("equipment", new ReturnJsonArray() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if (jsonArray.getJSONObject(i).getString("room_id").equals(id)) {
                            equipmentNames.add(jsonArray.getJSONObject(i).getString("equipment_name"));

                            // TODO: change to form modifiedAt
                            equipmentCode.add(jsonArray.getJSONObject(i).getString("equipment_code"));

                            equipmentId.add(jsonArray.getJSONObject(i).getString("equipment_id"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("DataService Equipment Names: " + equipmentNames);
                System.out.println("DataService Equipment Code: " + equipmentCode);
                System.out.println("DataService Equipment ID: " + equipmentId);
                responseListenerForList.onResponse(equipmentNames, equipmentCode, equipmentId);
            }
        });
    }

    public void getAnswers(ResponseListenerFor4ListString responseListenerFor4ListString) {
        List<String> roomNames = new ArrayList<>();
        List<String> roomLocations = new ArrayList<>();
        List<String> dates = new ArrayList<>();
        List<String> answerIds = new ArrayList<>();
        getJSONArray("answer", new ReturnJsonArray() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(JSONArray jsonArray) {
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        roomNames.add(jsonArray.getJSONObject(i).getString("room_name"));
                        roomLocations.add(jsonArray.getJSONObject(i).getString("room_location"));
                        answerIds.add(jsonArray.getJSONObject(i).getString("id"));
                        dates.add(jsonArray.getJSONObject(i).getString("created_at").substring(0,10));
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
                System.out.println("DataService getAnswers Names: " + roomNames);
                System.out.println("DataService getAnswers Locations: " + roomLocations);
                System.out.println("DataService getAnswers Dates: " + dates);
                System.out.println("DataService getAnswers ID: " + answerIds);
                responseListenerFor4ListString.onResponse(roomNames, roomLocations, dates,answerIds);
            }
        });
    }
//    public void getRoomById(String id, ResponseListenerForSingle responseListenerForSingle) {
//        temporaryGet("room/" + id, new ReturnJsonObject() {
//            @Override
//            public void onError(String message) {
//            }
//
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                try {
//                    String roomName = jsonObject.getString("room_name");
//                    String roomLocation = jsonObject.getString("location");
//                    responseListenerForSingle.onResponse(roomName, roomLocation);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//        // Access the RequestQueue through your singleton class.
////        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
//    }

//    public void getEquipmentById(String id, ResponseListenerForSingle responseListenerForSingle) {
//        System.out.println("DataService getEquipment Room ID: " + id);
//        temporaryGet("equipment/" + id, new ReturnJsonObject() {
//            @Override
//            public void onError(String message) {
//
//            }
//
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                try {
//                    String equipmentName = jsonObject.getString("equipment_name");
//                    String equipmentCode = jsonObject.getString("equipment_code");
//                    responseListenerForSingle.onResponse(equipmentName, equipmentCode);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        // Access the RequestQueue through your singleton class.
////        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
//    }

//    public void getQuestionsByEquipmentId(String id, VolleyResponseListenerForQuestions volleyResponseListenerForQuestions) {
//        String url;
//
//    }


    public void getJSONArray(String url, ReturnJsonArray returnJsonArray) {

        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient client = okHttpClient.newBuilder()
                .authenticator(new AccessTokenAuthenticator())
                .addInterceptor(new AccessTokenInterceptor())
                .build();
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
        Request request = new Request.Builder()
                .url(GlobalVariable.BASE_URL + url + "/")
                .method("GET", null)
//                .addHeader("Authorization", "Basic YWRtaW46YWRtaW4=")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    try {
                        jsonArray = new JSONArray(response.body().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    returnJsonArray.onResponse(jsonArray);
                } else {
                    System.out.println("getJSONArray failed");
                }
            }
        });
    }

    public void getJSONObject(String url,String id, ReturnJsonObject returnJsonObject) {

        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient client = okHttpClient.newBuilder()
                .authenticator(new AccessTokenAuthenticator())
                .addInterceptor(new AccessTokenInterceptor())
                .build();
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .build();
        Request request = new Request.Builder()
                .url(GlobalVariable.BASE_URL + url + "/" + id)
                .method("GET", null)
//                .addHeader("Authorization", "Basic YWRtaW46YWRtaW4=")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    try {
                        jsonObject = new JSONObject(response.body().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    returnJsonObject.onResponse(jsonObject);
                } else {
                    System.out.println("getJSONObject failed");
                }
            }
        });
    }

    public void postAnswer(Question question, boolean hasImage) {

        OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient client = okHttpClient.newBuilder()
                .authenticator(new AccessTokenAuthenticator())
                .addInterceptor(new AccessTokenInterceptor())
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body;
        if (!question.getFollowUpAction().equals("")){
            question.setNormalOrDefective(question.getFollowUpAction());
        }
        System.out.println("Post Question: " + question);
        if (hasImage){
            body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("answer_text",question.getNormalOrDefective())
                    .addFormDataPart("image", Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Download/equipment.jpg",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Download/equipment.jpg")))
                    .addFormDataPart("form",question.getFormId())
                    .addFormDataPart("created_by",GlobalVariable.userId)
                    .addFormDataPart("is_active","true")
                    .addFormDataPart("question",question.getQuestionId())
                    .addFormDataPart("signature",Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Download/signature.jpg",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Download/signature.jpg")))
                    .addFormDataPart("unique_id",GlobalVariable.globalQuestions.get(0).getAnswerId())
                    .build();
        } else {
            body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("answer_text",question.getNormalOrDefective())
                    .addFormDataPart("form",question.getFormId())
                    .addFormDataPart("created_by",GlobalVariable.userId)
                    .addFormDataPart("is_active","true")
                    .addFormDataPart("question",question.getQuestionId())
                    .addFormDataPart("signature",Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Download/signature.jpg",
                            RequestBody.create(MediaType.parse("application/octet-stream"),
                                    new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Download/signature.jpg")))
                    .addFormDataPart("unique_id",GlobalVariable.globalQuestions.get(0).getAnswerId())
                    .build();
        }
        Request request = new Request.Builder()
                .url(GlobalVariable.BASE_URL + "answer/")
                .method("POST", body)
//                .addHeader("Authorization", "Basic c3RhZmY6c3RhZmY=")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    System.out.println(response);
                }
            }
        });
    }

//    public void temporaryGet(String url, ReturnJsonObject returnJsonObject) {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        OkHttpClient client = okHttpClient.newBuilder()
//                .authenticator(new AccessTokenAuthenticator())
//                .addInterceptor(new AccessTokenInterceptor())
//                .build();
////        OkHttpClient client = new OkHttpClient().newBuilder()
////                .build();
//        Request request = new Request.Builder()
//                .url(GlobalVariable.BASE_URL + url)
//                .method("GET", null)
////                .addHeader("Authorization", "Basic YWRtaW46YWRtaW4=")
//                .build();
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (response.isSuccessful()){
//                    try {
//                        jsonObject = new JSONObject(response.body().string());
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    returnJsonObject.onResponse(jsonObject);
//                } else {
//                    System.out.println("jsonObject failed");
//                }
//            }
//        });
//    }

}