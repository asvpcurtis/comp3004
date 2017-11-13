package com.comp3004.goodbyeworld.tournamentmaster.dataaccess;

import android.content.Context;

import com.comp3004.goodbyeworld.tournamentmaster.auth.AppHelper;
import com.comp3004.goodbyeworld.tournamentmaster.dataoperations.FieldTranslate;
import com.comp3004.goodbyeworld.tournamentmaster.dataoperations.TMTemplates;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Michael Souter on 2017-10-28.
 * Interface for the view to access data.
 * Directs by delegating calls for data to the appropriate
 * function in the appropriate data type.
 */

public class DataHandler {
    // Function to be called when data is updated
    private static UpdateCallback updateCallback = null;

    /**
     * updateActivity calls the current update method of an activity when
     * the data is ready for it to be updated
     */
    private static void updateActivity(ArrayList<TMDataSet> data) {
        updateCallback.updateData(data);
    }

    /**
     * getData retrieves an ArrayList of TMDataSets
     * The first element of the array will always be the item itself
     * which was used to make the request. The following items will be
     * the associated children of that item, including its name, id,
     * and related types.
     */
    public static void getData(Context c, String type, String iD, UpdateCallback u) {
        updateCallback = u;
        final String fieldType = type;
        if (type.equals("Account")) {
            String urlAdd = "/organizations";
            DataRetriever.getDataArray(c, urlAdd, new VolleyCallback() {
                @Override
                public void onSuccess(Object result) {
                    ArrayList<TMDataSet> info = new ArrayList<>();
                    info.add(new TMDataSet(AppHelper.getCurrUser(), "Account", "1"));
                    info.addAll(FieldTranslate.convert((JSONArray) result, "organizations"));
                    updateActivity(info);
                }
            });
        } else {
            String urlAdd = FieldTranslate.convertType(type, iD);
            DataRetriever.getData(c, urlAdd, new VolleyCallback() {
                @Override
                public void onSuccess(Object result) {
                    ArrayList<TMDataSet> info = new ArrayList<>();
                    info.addAll(FieldTranslate.convert((JSONObject) result, fieldType));
                    updateActivity(info);
                }
            });
        }
    }

    /**
     * getCreate retrieves the fields required to create a new entity
     */
    public static ArrayList<TMDataSet> getCreate(String type) {
        return TMTemplates.get(type);
    }

    /**
     * setCreate accepts data to be posted to backend
     */
    public static void setCreate(Context c, String type, ArrayList<TMDataSet> data, UpdateCallback u) {
        updateCallback = u;
        String urlAdd = FieldTranslate.convertType(type);
        JSONObject newData = FieldTranslate.convert(data);
        DataRetriever.postData(c, urlAdd, newData, new VolleyCallback() {
           @Override
            public void onSuccess(Object result) {
               updateActivity(null);
           }
        });
    }

    /**
     * getEdit is currently a masked call to getData that will
     * respond with only the needed info.
     */
    public static void getEdit(Context c, String type, String iD, UpdateCallback u) {
        updateCallback = u;
        final String fieldType = type;
        String urlAdd = FieldTranslate.convertType(type, iD);
        DataRetriever.getData(c, urlAdd, new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                ArrayList<TMDataSet> info = new ArrayList<>();
                info.addAll(TMTemplates.editFields((JSONObject)result, fieldType));
                updateActivity(info);
            }
        });
    }

    /**
     * setEdit accepts data to be modified on backend
     */
    public static void setEdit(Context c, String type, String iD, ArrayList<TMDataSet> data, UpdateCallback u) {
        updateCallback = u;
        String urlAdd = FieldTranslate.convertType(type, iD);
        JSONObject newData = FieldTranslate.convert(data);
        DataRetriever.putData(c, urlAdd, newData, new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                updateActivity(null);
            }
        });
    }

    public static void setDelete(Context c, String type, String iD, UpdateCallback u) {
        updateCallback = u;
        String urlAdd = FieldTranslate.convertType(type, iD);
        DataRetriever.deleteData(c, urlAdd, new VolleyCallback() {
            @Override
            public void onSuccess(Object result) {
                updateActivity(null);
            }
        });
    }
}
