package com.comp3004.goodbyeworld.tournamentmaster.dataoperations;

import android.util.Log;

import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.TMDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Michael Souter on 2017-11-12.
 */

public class FieldTranslate {
    public static ArrayList<TMDataSet> convert(JSONArray a, String type) {
        ArrayList<TMDataSet> result = new ArrayList<>();
        for (int i=0; i<a.length(); i++) {
            try {
                JSONObject obj = a.getJSONObject(i);
                result.addAll(convert(obj, type));
            } catch (JSONException e) {
                Log.e("ERROR:", "JSON not readable");
            }
        }
        return result;
    }

    public static ArrayList<TMDataSet> convert(JSONObject o, String type) {
        ArrayList<TMDataSet> result = new ArrayList<>();
        type = "/" + type;
        try {
            result.add(new TMDataSet(o.getString("name"), convertType(type), o.getString("id")));
            Iterator<?> keys = o.keys();
            while (keys.hasNext()) {
                String key = (String)keys.next();
                if (convertType(key)!=null) {
                    result.addAll(convert(o.getJSONArray(key), "/"+key));
                }
            }
        } catch (JSONException e) {
            Log.e("ERROR:", "JSON not readable");
        }

        return result;
    }

    public static JSONObject convert(ArrayList<TMDataSet> a) {
        JSONObject newData = null;
        try {
            newData = new JSONObject(a.get(0).getData());
            a.remove(0);
            for (TMDataSet i : a) {
                newData.put(i.getDataType(), i.getData());
            }
        } catch (JSONException e) {
            Log.e("ERROR:", "JSON not created");
        }
        return newData;
    }

    public static String convertType(String t) {
        String result = null;
        switch (t) {
            case "Organization":
                result = "/organizations";
                break;
            case "/organizations":
                result = "Organization";
                break;
            case "Tournament":
                result = "/tournaments";
                break;
            case "/tournaments":
                result = "Tournament";
                break;
            case "Round":
                result = "/rounds";
                break;
            case "/round":
                result = "Round";
                break;
            case "Pairing":
                result = "/pairings";
                break;
            case "/pairing":
                result = "Pairing";
                break;
            case "Competitor":
                result = "/competitors";
                break;
            case "/competitors":
                result = "Competitor";
                break;
        }

        return result;
    }

    public static String convertType(String t, String i) {
        return convertType(t) + "/" + i;
    }
}
