package com.comp3004.goodbyeworld.tournamentmaster.data;

/**
 * Created by Michael Souter
 */

public class Pairing {
    private int id;
    private int competitorOneID;
    private int competitorTwoID;
    private int roundID;
    private int result;

    // Get methods for properties
    public int getID() {
        return id;
    }
    public int getCompetitorOneID() {
        return competitorOneID;
    }
    public int getCompetitorTwoID() {
        return competitorTwoID;
    }
    public int getRoundID() {
        return roundID;
    }
    public int getResult() {
        return result;
    }

    // Initialize a Pairing
    public Pairing initCompetitor(int i, int one, int two, int round, int res) {
        id = i;
        competitorOneID = one;
        competitorTwoID = two;
        roundID = round;
        result = res;

        return this;
    }

    // Update result, confirming winner is one of the competitors
    // Returns 0 if not
    public int updateResult(int winnerID) {
        if (winnerID==competitorOneID || winnerID==competitorTwoID) {
            result = winnerID;
            return result;
        }
        return 0;
    }
}