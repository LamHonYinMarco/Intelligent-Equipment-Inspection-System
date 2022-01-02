package com.example.intelligentequipmentinspectionsystem;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public interface VolleyResponseListener {
        void onError(String message);

        void onResponse(List<String> data1, List<String> data2, List<String> id);
    }

    public void getRoomName(VolleyResponseListener volleyResponseListener) {
        String url = roomUrl;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<String> roomNames = new ArrayList<>();
                List<String> roomLocation = new ArrayList<>();
                List<String> roomId = new ArrayList<>();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        roomNames.add(response.getJSONObject(i).getString("roomName"));
                        roomLocation.add(response.getJSONObject(i).getString("location"));
                        roomId.add(response.getJSONObject(i).getString("roomId"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("DataService getRoomName Room Names: " + roomNames);
                System.out.println("DataService getRoomName Room Location: " + roomLocation);
                System.out.println("DataService getRoomName Room ID: " + roomId);
                volleyResponseListener.onResponse(roomNames, roomLocation, roomId);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                System.out.println("DataService Error: " + error);
                volleyResponseListener.onError("" + error);
            }
        });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public void getEquipment(String id,VolleyResponseListener volleyResponseListener) {
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
                volleyResponseListener.onResponse(equipmentNames, equipmentModifiedAt, equipmentId);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                System.out.println("DataService Error: " + error);
                volleyResponseListener.onError("" + error);
            }
        });
        // Access the RequestQueue through your singleton class.
        MySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }
}