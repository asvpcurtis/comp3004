package com.comp3004.goodbyeworld.tournamentmaster.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Michael Souter on 2017-10-28.
 * Handles the calls to backend and presentation of data
 * to the view.
 */

public class DataHandler {

    /**
     * getData returns an ArrayList of TMDataSets
     * The first element of the data set returned is always the object it was called
     * with. The remaining members depend on the type. Data sets consist of a string
     * to display(data), the type of information returned(dataType, ie: PAIR or
     * COMPETITOR), and that datas ID#(iD)
     *
     * The type and ID of data requested are used to select which API call to make
     */

    public static ArrayList getData(Context c, String type, String iD) {
        ArrayList info = new ArrayList();
        String s = null;

        try {
            InputStream in = c.getAssets().open("testdata.JSON");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            s = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        try {
            JSONObject obj = new JSONObject(s);

            // User a special condition since test user data is not an array
            if (type.equals("USER")) {
                JSONObject data = obj.getJSONObject(type);
                info.add(new TMDataSet(data.getString("name"), type, iD));
                JSONArray list = data.getJSONArray("orgs");
                for (int i=0; i<list.length(); i++)  {
                    JSONObject x = list.getJSONObject(i);
                    info.add(new TMDataSet(x.getString("name"), "ORG", x.getString("id")));
                }
            } else {
                JSONArray arr = obj.getJSONArray(type);
                if (type.equals("ORG")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            // Organization
                            info.add(new TMDataSet(data.getString("name"), type, iD));
                            // returns tournaments the organization hosts
                            JSONArray subList = data.getJSONArray("tourn");
                            for (int j = 0; j < subList.length(); j++) {
                                JSONObject subData = subList.getJSONObject(j);
                                info.add(new TMDataSet(subData.getString("name"), "TOURN", subData.getString("id")));
                            }
                        }
                    }
                } else if (type.equals("TOURN")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            // Tournament
                            info.add(new TMDataSet(data.getString("name"), type, iD));
                            // returns rounds in the tournament
                            JSONArray subList = data.getJSONArray("rounds");
                            for (int j = 0; j < subList.length(); j++) {
                                JSONObject subData = subList.getJSONObject(j);
                                info.add(new TMDataSet(subData.getString("name"), "ROUND", subData.getString("id")));
                            }
                        }
                    }
                } else if (type.equals("ROUND")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            // Round
                            info.add(new TMDataSet(data.getString("name"), type, iD));
                            // returns pairings in the round
                            JSONArray subList = data.getJSONArray("pairs");
                            for (int j = 0; j < subList.length(); j++) {
                                JSONObject subData = subList.getJSONObject(j);
                                info.add(new TMDataSet(subData.getString("name"), "PAIR", subData.getString("id")));
                            }
                        }
                    }
                } else if (type.equals("PAIR")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            // Pairing
                            info.add(new TMDataSet(data.getString("name"), type, iD));
                            // returns competitors in the pairing
                            JSONArray subList = data.getJSONArray("competitors");
                            for (int j = 0; j < subList.length(); j++) {
                                JSONObject subData = subList.getJSONObject(j);
                                info.add(new TMDataSet(subData.getString("name"), "COMPETITOR", subData.getString("id")));
                            }
                        }
                    }
                } else if (type.equals("COMPETITOR")) {
                    // Competitor
                    info.add(new TMDataSet("Competitor", type, iD));
                    // returns non-linking competitor information
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            info.add(new TMDataSet(data.getString("first") + " " + data.getString("last"), "NONE", "NONE"));
                            info.add(new TMDataSet(data.getString("email"), "NONE", "NONE"));
                        }
                    }
                } else {
                    // Test Data
                    info.add(new TMDataSet("Test Data", type, iD));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        return info;

    }
}
