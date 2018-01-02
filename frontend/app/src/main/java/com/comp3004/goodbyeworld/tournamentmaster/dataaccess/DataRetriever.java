package com.comp3004.goodbyeworld.tournamentmaster.dataaccess;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.comp3004.goodbyeworld.tournamentmaster.auth.Tokens;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

/**
 * Created by Michael Souter on 2017-11-10.
 * Handles calls to the backend API
 */

class DataRetriever {
    // Local from emulator
    //private static String url = "http://10.0.2.2/api";
    // Local from device on network (Mike)
    private static String url = "http://192.168.0.101/api";
    // Amazon EC2
    //private static String url = "http://ec2-35-182-254-130.ca-central-1.compute.amazonaws.com/api";

    static void getID(Context c, final VolleyCallback callback) {
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " +Tokens.getIDToken());
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(stringRequest);
    }

    static void getDataArray(Context c, String urlAdd, final VolleyCallback callback) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url + urlAdd, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                callback.onFailure(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " +Tokens.getIDToken());
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(jsonArrayRequest);
    }
    static void getData(Context c, String urlAdd, final VolleyCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url + urlAdd, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " +Tokens.getIDToken());
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(jsonObjectRequest);
    }

    static void postData(Context c, String urlAdd, JSONObject data, final VolleyCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url + urlAdd, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " +Tokens.getIDToken());
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(jsonObjectRequest);
    }

    static void putData(Context c, String urlAdd, JSONObject data, final VolleyCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url + urlAdd, data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                //temp because of null return
                callback.onSuccess(null);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " +Tokens.getIDToken());
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(jsonObjectRequest);
    }

    static void deleteData(Context c, String urlAdd, final VolleyCallback callback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url + urlAdd, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " +Tokens.getIDToken());
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(c);
        queue.add(jsonObjectRequest);
    }
}
