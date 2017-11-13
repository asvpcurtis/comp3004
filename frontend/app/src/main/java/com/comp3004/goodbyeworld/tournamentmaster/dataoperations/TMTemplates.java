package com.comp3004.goodbyeworld.tournamentmaster.dataoperations;

import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.TMDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Michael Souter on 2017-11-12.
 * Templates for creation and editing of data.
 */

public class TMTemplates {
    public static ArrayList<TMDataSet> get(String type) {
        ArrayList<TMDataSet> template = new ArrayList<>();
        template.add(new TMDataSet("{}", null, null));
        template.add(new TMDataSet("","name", null));
        return template;
    }

    public static ArrayList<TMDataSet> editFields(JSONObject data, String type) {
        ArrayList<TMDataSet> template = new ArrayList<>();
        template.add(new TMDataSet(data.toString(), null, null));
        try {
            template.add(new TMDataSet(data.getString("name"), "name", null));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return template;
    }
}
