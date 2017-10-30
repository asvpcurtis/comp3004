package com.comp3004.goodbyeworld.tournamentmaster.data;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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

        //Setup Test Data
        s = setupTest(c, s);

        try {
            JSONObject obj = new JSONObject(s);

            // User a special condition since test user data is not an array
            // User gives the users information and associated organizations
            if (type.equals("USER")) {
                JSONObject data = obj.getJSONObject(type);
                info.add(new TMDataSet(data.getString("name"), type, iD));
                JSONArray list = data.getJSONArray("orgs");
                for (int i=0; i<list.length(); i++)  {
                    JSONObject x = list.getJSONObject(i);
                    info.add(new TMDataSet(((TMDataSet)((getData(c,"ORG",x.getString("id"))).get(0))).getData(), "ORG", x.getString("id")));
                }
            } else {
                // get methods for the rest of the data types
                JSONArray arr = obj.getJSONArray(type);


                if (type.equals("ORG")) {
                    // Organization returns itself and associated data
                    // currently just tournaments
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            // Organization
                            info.add(new TMDataSet(data.getString("name"), type, iD));
                            // returns tournaments the organization hosts
                            JSONArray subList = data.getJSONArray("tourn");
                            for (int j = 0; j < subList.length(); j++) {
                                JSONObject subData = subList.getJSONObject(j);
                                info.add(new TMDataSet(((TMDataSet)((getData(c,"TOURN",subData.getString("id"))).get(0))).getData(), "TOURN", subData.getString("id")));
                            }
                        }
                    }
                } else if (type.equals("TOURN")) {
                    // Tournament returns itself and its rounds
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            // Tournament
                            info.add(new TMDataSet(data.getString("name"), type, iD));
                            // returns rounds in the tournament
                            JSONArray subList = data.getJSONArray("rounds");
                            for (int j = 0; j < subList.length(); j++) {
                                JSONObject subData = subList.getJSONObject(j);
                                info.add(new TMDataSet(((TMDataSet)((getData(c,"ROUND",subData.getString("id"))).get(0))).getData(), "ROUND", subData.getString("id")));
                            }
                        }
                    }
                } else if (type.equals("ROUND")) {
                    // Round returns itself and its pairings
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            // Round
                            info.add(new TMDataSet(data.getString("name"), type, iD));
                            // returns pairings in the round
                            JSONArray subList = data.getJSONArray("pairs");
                            for (int j = 0; j < subList.length(); j++) {
                                JSONObject subData = subList.getJSONObject(j);
                                info.add(new TMDataSet(((TMDataSet)((getData(c,"PAIR",subData.getString("id"))).get(0))).getData(), "PAIR", subData.getString("id")));
                            }
                        }
                    }
                } else if (type.equals("PAIR")) {
                    // Pair returns iteself and its competitors
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            // Pairing
                            info.add(new TMDataSet(data.getString("name"), type, iD));
                            // returns competitors in the pairing
                            JSONArray subList = data.getJSONArray("competitors");
                            for (int j = 0; j < subList.length(); j++) {
                                JSONObject subData = subList.getJSONObject(j);
                                info.add(new TMDataSet(((TMDataSet)((getData(c,"COMPETITOR",subData.getString("id"))).get(1))).getData(), "COMPETITOR", subData.getString("id")));
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

    /**
     * getEdit returns a LinkedHashMap
     *
     * The map contains key-value pairs that correspond to
     * modifiable fields in the object provided by the
     * parameters.
     */
    public static LinkedHashMap getEdit(Context c, String type, String iD) {
        LinkedHashMap info = new LinkedHashMap();
        String s = null;

        try {
            File f = c.getFileStreamPath("testdata.JSON");
            InputStream in = new FileInputStream(f);
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
                info.put("name", data.getString("name"));
            } else {
                JSONArray arr = obj.getJSONArray(type);
                if (type.equals("ORG")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            info.put("name", data.getString("name"));
                        }
                    }
                } else if (type.equals("TOURN")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            info.put("name", data.getString("name"));
                        }
                    }
                } else if (type.equals("ROUND")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            info.put("name", data.getString("name"));
                        }
                    }
                } else if (type.equals("PAIR")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            info.put("name", data.getString("name"));
                        }
                    }
                } else if (type.equals("COMPETITOR")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            info.put("first", data.getString("first"));
                            info.put("last", data.getString("last"));
                            info.put("email", data.getString("email"));
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return info;
    }

    /**
     * setEdit returns true on success
     *
     * The map contains key-value pairs that correspond to
     * modifiable fields in the object provided by the
     * parameters.
     */
    public static boolean setEdit(Context c, String type, String iD, LinkedHashMap<String, String> newData) {
        String s = null;

        try {
            File f = c.getFileStreamPath("testdata.JSON");
            InputStream in = new FileInputStream(f);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            s = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        try {
            JSONObject obj = new JSONObject(s);

            // User a special condition since test user data is not an array
            if (type.equals("USER")) {
                JSONObject data = obj.getJSONObject(type);
                for (String key : newData.keySet()) {
                    data.put(key, newData.get(key));
                }
                obj.put(type, data);
            } else {
                JSONArray arr = obj.getJSONArray(type);
                if (type.equals("ORG")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            for (String key : newData.keySet()) {
                                data.put(key, newData.get(key));
                            }
                            arr.put(i, data);
                        }
                    }
                } else if (type.equals("TOURN")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            for (String key : newData.keySet()) {
                                data.put(key, newData.get(key));
                            }
                            arr.put(i, data);
                        }
                    }
                } else if (type.equals("ROUND")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            for (String key : newData.keySet()) {
                                data.put(key, newData.get(key));
                            }
                            arr.put(i, data);
                        }
                    }
                } else if (type.equals("PAIR")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            for (String key : newData.keySet()) {
                                data.put(key, newData.get(key));
                            }
                            arr.put(i, data);
                        }
                    }
                } else if (type.equals("COMPETITOR")) {
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject data = arr.getJSONObject(i);
                        if (data.getString("id").equals(iD)) {
                            for (String key : newData.keySet()) {
                                data.put(key, newData.get(key));
                            }
                            arr.put(i, data);
                        }
                    }
                }
                obj.put(type, arr);
            }

            s = obj.toString();

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        try {
            File f = c.getFileStreamPath("testdata.JSON");
            FileOutputStream out = new FileOutputStream(f);
            byte[] buffer = s.getBytes();
            out.write(buffer);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean setCreate(Context c, String type, String iD, LinkedHashMap<String, String> newData) {
        //only works because we have one user in test data
        ArrayList createTest = getData(c, type, iD);
        String newID = Integer.toString((Integer.parseInt(((TMDataSet)createTest.get(createTest.size() - 1)).getID()) + 1));

        String s = null;

        try {
            File f = c.getFileStreamPath("testdata.JSON");
            InputStream in = new FileInputStream(f);
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            in.close();
            s = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }

        try {
            JSONObject obj = new JSONObject(s);
            JSONObject data = obj.getJSONObject(type);
            JSONArray arr = data.getJSONArray("orgs");
            JSONObject newobj = new JSONObject();
            newobj.put("id", newID);
            arr.put(newobj);
            data.put("orgs", arr);
            arr = obj.getJSONArray("ORG");
            data = new JSONObject();
            data.put("name", newData.get("name"));
            data.put("id", newID);
            arr.put(data);
            obj.put("ORG",arr);

            s = obj.toString();

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        try {
            File f = c.getFileStreamPath("testdata.JSON");
            FileOutputStream out = new FileOutputStream(f);
            byte[] buffer = s.getBytes();
            out.write(buffer);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static String setupTest(Context c, String s){
        File f = c.getFileStreamPath("testdata.JSON");
        if (!f.exists()) {
            try {
                InputStream in = c.getAssets().open("testdata.JSON");
                OutputStream out = new FileOutputStream(f);
                int size = in.available();
                byte[] buffer = new byte[size];
                in.read(buffer);
                out.write(buffer);
                s = new String(buffer, "UTF-8");
                in.close();
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        } else {
            try {
                InputStream in = new FileInputStream(f);
                int size = in.available();
                byte[] buffer = new byte[size];
                in.read(buffer);
                in.close();
                s = new String(buffer, "UTF-8");
            } catch (IOException ex) {
                ex.printStackTrace();
                return null;
            }
        }

        return s;
    }

    public static void clearLocal(Context c) {
        File f = c.getFileStreamPath("testdata.JSON");
        if (f.exists()) {
            f.delete();
        }
    }


}
