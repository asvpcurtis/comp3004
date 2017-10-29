package com.comp3004.goodbyeworld.tournamentmaster.data;

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
    public ArrayList getData(String type, String iD) {
        //call backend and create data set
        ArrayList info = new ArrayList();

        if (type.equals("ORG")) {
            // Organization
            info.add(new TMDataSet("Test Data", type, iD));
        } else if (type.equals("TOURN")) {
            // Tournament
            info.add(new TMDataSet("Tournament", type, iD));
            // returns rounds in the tournament
            info.add(new TMDataSet("Round 1", "ROUND", "0123456789"));
            info.add(new TMDataSet("Round 2", "ROUND", "0123456789"));
        } else if (type.equals("USER")) {
            // User
            info.add(new TMDataSet("User", type, iD));
        } else if (type.equals("ROUND")) {
            // Round
            info.add(new TMDataSet("Round", type, iD));
            // returns pairings in that round
            info.add(new TMDataSet("Pairing 1", "PAIR", "0123456789"));
            info.add(new TMDataSet("Pairing 2", "PAIR", "0123456789"));
            info.add(new TMDataSet("Pairing 3", "PAIR", "0123456789"));
            info.add(new TMDataSet("Pairing 4", "PAIR", "0123456789"));
        } else if (type.equals("PAIR")) {
            // Pairing
            info.add(new TMDataSet("Pairing", type, iD));
            // returns competitors in that pairing
            info.add(new TMDataSet("Competitor 1", "COMPETITOR", "0123456789"));
            info.add(new TMDataSet("Competitor 2", "COMPETITOR", "0123456789"));
        } else if (type.equals("COMPETITOR")) {
            // Competitor
            info.add(new TMDataSet("Competitor", type, iD));
            // returns non-linking competitor information
            info.add(new TMDataSet("Firstname Lastname", "OTHER", "NONE"));
            info.add(new TMDataSet("username@email.com", "OTHER", "NONE"));
        } else {
            // Test Data
            info.add(new TMDataSet("Test Data", type, iD));
        }

        //return
        return info;
    }
}
