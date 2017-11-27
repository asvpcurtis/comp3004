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
        switch (type) {
            case "Organization":
                template.add(new TMDataSet("{}", null, null));
                template.add(new TMDataSet("", "name", null));
                break;
            case "Competitor":
                template.add(new TMDataSet("{}", null, null));
                template.add(new TMDataSet("", "firstName", null));
                template.add(new TMDataSet("", "lastName", null));
                template.add(new TMDataSet("", "email", null));
                template.add(new TMDataSet("", "gender", null));
                break;
            case "Tournament":
                template.add(new TMDataSet("{}", null, null));
                template.add(new TMDataSet("", "name", null));
                break;
        }

        return template;
    }

    public static ArrayList<TMDataSet> editFields(JSONObject data, String type) {
        ArrayList<TMDataSet> template = new ArrayList<>();
        template.add(new TMDataSet(data.toString(), null, null));
        try {
            switch (type) {
                case "Organization":
                    template.add(new TMDataSet(data.getString("name"), "name", null));
                    break;
                case "Pairing":
                    template.add(new TMDataSet("Select Winner", "result", null));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return template;
    }
}
