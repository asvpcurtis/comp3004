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
 * Provides methods for converting between the JSONObject
 * and JSONArrays of the backend to TMDataSets for the
 * frontend.
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
        try {
            switch (type) {
                case "Organization":
                case "Tournament":
                    result.add(new TMDataSet(o.getString("name"), type, o.getString("id")));
                    break;
                case "Competitor":
                    result.add(new TMDataSet(o.getString("firstName") + " " + o.getString("lastName"), type, o.getString("id")));
                    break;
                case "Round":
                    result.add(new TMDataSet("Round " + o.get("roundNumber").toString(), type, o.getString("id")));
                    break;
                case "Pairing":
                    result.add(new TMDataSet("Pairing", type, o.getString("id")));
                    break;
            }
        } catch (JSONException e) {
            Log.e("ERROR:", "JSON not readable");
        }

        return result;
    }

    public static ArrayList<TMDataSet> convertFull(JSONObject o, String type) {
        ArrayList<TMDataSet> result = new ArrayList<>();
        try {
            switch (type) {
                case "Organization":
                case "Tournament":
                    result.add(new TMDataSet(o.getString("name"), type, o.getString("id")));
                    break;
                case "Competitor":
                    result.add(new TMDataSet(o.getString("firstName") + " " + o.getString("lastName"), type, o.getString("id")));
                    break;
                case "Round":
                    result.add(new TMDataSet("Round " + o.get("roundNumber").toString(), type, o.getString("id")));
                    break;
                case "Pairing":
                    result.add(new TMDataSet("Pairing", type, o.getString("id")));
                    break;
            }
            Iterator<?> keys = o.keys();
            while (keys.hasNext()) {
                String key = (String)keys.next();
                TMDataSet toAdd = infoKey(key, o.get(key));
                if (toAdd != null) {
                    result.add(toAdd);
                }
            }
        } catch (JSONException e) {
            Log.e("ERROR:", "JSON not readable");
        }

        return result;
    }

    public static ArrayList<TMDataSet> convertCompetitors(JSONArray a) {
        ArrayList<TMDataSet> result = new ArrayList<>();
        for (int i=0; i<a.length(); i++) {
            try {
                JSONObject obj = a.getJSONObject(i);
                result.add(new TMDataSet(obj.getString("firstName") + " " + obj.getString("lastName"), obj.toString(), obj.getString("id")));
            } catch (JSONException e) {
                Log.e("ERROR:", "JSON not readable");
            }
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

    public static JSONObject convertTournament(ArrayList<TMDataSet> a) {
        JSONObject newData = null;
        JSONObject tournamentData;
        try {
            newData = new JSONObject(a.get(0).getData());
            tournamentData = new JSONObject(a.get(0).getData());
            a.remove(0);
            for (TMDataSet i : a) {
                if (i.getDataType().equals("competitors")) {
                    JSONArray arr = new JSONArray(i.getData());
                    newData.put(i.getDataType(), arr);
                } else {
                    tournamentData.put(i.getDataType(), i.getData());
                }
            }
            newData.put("tournament", tournamentData);
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

    private static TMDataSet infoKey(String k, Object v) {
        TMDataSet result = null;
        switch (k) {
            case "onGoing":
                if ((boolean)v) {
                    result = new TMDataSet("Yes" , "On Going : ", null);
                } else {
                    result = new TMDataSet("No", "On Going : ", null);
                }
                break;
            case "startDate":
                result = new TMDataSet(v.toString(),"Start Date : ", null);
                break;
            case "format":
                result = new TMDataSet( "Elimination", "Format : ", null);
                break;
            case "email":
                result = new TMDataSet(v.toString(), "E-Mail : ", null);
                break;
            case "rating":
                result = new TMDataSet(v.toString(), "Rating : ", null);
                break;
            case "gender":
                result = new TMDataSet(v.toString(), "Gender : ", null);
                break;
        }
        return result;
    }
}
