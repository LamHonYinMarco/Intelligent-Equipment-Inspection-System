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
    public static final String roomUrl = "https://mocki.io/v1/c869f977-264d-465c-ba21-769372f86240";

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
        void onResponse(List<String> data);
    }

    public void getRoomName(VolleyResponseListener volleyResponseListener){
        String url = roomUrl;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> roomNames = new ArrayList<>();
                        try {
                            for (int i=0; i<response.length(); i++){
                                roomNames.add(response.getJSONObject(i).getString("roomName"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        System.out.println("DataService Room Names: " + roomNames);
                        volleyResponseListener.onResponse(roomNames);
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