package com.comp3004.goodbyeworld.tournamentmaster.data;

/**
 * Created by Michael Souter on 2017-10-28.
 *
 * This class is to provide an object that will give the views
 * a string to display, as well as an associated data type and
 * backend ID for linking.
 */

public class TMDataSet {
    private String data;
    private String dataType;
    private String iD;

    public String getData() { return data; }
    public String getDataType() { return dataType; }
    public String getID() { return iD; }

    public TMDataSet(String d, String t, String i) {
        data = d;
        dataType = t;
        iD = i;
    }
}
