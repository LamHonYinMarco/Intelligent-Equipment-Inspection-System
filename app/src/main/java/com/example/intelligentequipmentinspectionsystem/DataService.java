package com.example.intelligentequipmentinspectionsystem;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class DataService {
    public static final String roomUrl = "http://10.0.2.2:8000/api/room/";
    public static final String equipmentUrl = "http://10.0.2.2:8000/api/equipment/";
    Context context;
    //    public List<String> getRoom(){
//
//        return
//    }

    public DataService(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListenerForList {
        void onError(String message);

        void onResponse(List<String> data1, List<String> data2, List<String> id);
    }

    public interface VolleyResponseListenerForSingle {
        void onError(String message);

        void onResponse(String data1, String data2);
    }

    public interface VolleyResponseListenerForQuestions {
        void onError(String message);

        void onResponse(List<String> questionTitles, List<String> questionIds);
    }

    public void getRooms(VolleyResponseListenerForList volleyResponseListenerForList) {
        String url = roomUrl;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<String> roomNames = new ArrayList<>();
                List<String> roomLocations = new ArrayList<>();
                List<String> roomIds = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        roomNames.add(response.getJSONObject(i).getString("roomName"));
                        roomLocations.add(response.getJSONObject(i).getString("location"));
                        roomIds.add(response.getJSONObject(i).getString("roomId"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("DataService getRoomName Room Names: " + roomNames);
                System.out.println("DataService getRoomName Room Locations: " + roomLocations);
                System.out.println("DataService getRoomName Room IDs: " + roomIds);
                volleyResponseListenerForList.onResponse(roomNames, roomLocations, roomIds);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("DataService Error: " + error);
                volleyResponseListenerForList.onError("" + error);
            }
        });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void getEquipmentsByRoomId(String id, VolleyResponseListenerForList volleyResponseListenerForList) {
        String url = equipmentUrl;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<String> equipmentNames = new ArrayList<>();
                List<String> equipmentModifiedAt = new ArrayList<>();
                List<String> equipmentId = new ArrayList<>();
                System.out.println("DataService getEquipment Room ID: " + id);
                try {
                    for (int i = 0; i < response.length(); i++) {
                        if(response.getJSONObject(i).getString("room").equals(id)){
                            equipmentNames.add(response.getJSONObject(i).getString("equipmentName"));

                            // TODO: change to form modifiedAt
                            equipmentModifiedAt.add(response.getJSONObject(i).getString("modifiedAt"));

                            equipmentId.add(response.getJSONObject(i).getString("equipmentId"));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("DataService Equipment Names: " + equipmentNames);
                System.out.println("DataService Equipment Modified At: " + equipmentModifiedAt);
                System.out.println("DataService Equipment ID: " + equipmentId);
                volleyResponseListenerForList.onResponse(equipmentNames, equipmentModifiedAt, equipmentId);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("DataService Error: " + error);
                volleyResponseListenerForList.onError("" + error);
            }
        });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void getRoomById(String id, VolleyResponseListenerForSingle volleyResponseListenerForSingle) {
        String url = roomUrl;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String roomName = "";
                String roomLocation = "";
                try {
                    for (int i = 0; i < response.length(); i++) {
                        if(response.getJSONObject(i).getString("roomId").equals(id)){

                        }
                        roomName = response.getJSONObject(i).getString("roomName");
                        roomLocation = response.getJSONObject(i).getString("location");

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListenerForSingle.onResponse(roomName, roomLocation);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("DataService Error: " + error);
                volleyResponseListenerForSingle.onError("" + error);
            }
        });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void getEquipmentById(String id, VolleyResponseListenerForSingle volleyResponseListenerForSingle) {
        String url = equipmentUrl;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                String equipmentName = "";
                String equipmentModifiedAt = "";
                System.out.println("DataService getEquipment Room ID: " + id);
                try {
                    for (int i = 0; i < response.length(); i++) {
                        if(response.getJSONObject(i).getString("equipmentId").equals(id)){
                            equipmentName = response.getJSONObject(i).getString("equipmentName");

                            // TODO: change to equipment code
                            equipmentModifiedAt = response.getJSONObject(i).getString("modifiedAt");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                volleyResponseListenerForSingle.onResponse(equipmentName, equipmentModifiedAt);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("DataService Error: " + error);
                volleyResponseListenerForSingle.onError("" + error);
            }
        });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void getQuestionsByEquipmentId(String id, VolleyResponseListenerForQuestions volleyResponseListenerForQuestions) {
        String url;

    }
}