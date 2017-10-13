package com.comp3004.goodbyeworld.tournamentmaster.data;

/**
 * Created by Michael Souter
 */

public class Tournament {
    private Round[] rounds;
    private int id;
    private int orgID;
    private String format;
    private String startDate;
    private boolean ongoing;

    public int getId() {
        return id;
    }
    public int getOrgID() {
        return orgID;
    }
    public String getFormat() {
        return format;
    }
    public String getStartDate() {
        return startDate;
    }
    public boolean isOngoing() {
        return ongoing;
    }

    // Initialize a Tournament
    public Tournament(int i, int o, String f, String s) {
        id = i;
        orgID = o;
        format = f;
        startDate = s;
        ongoing = false;
    }

    // Populate rounds from database
    public int populateRounds() {
        return 1;
    }

    // Add a new round
    public int addRound() {
        return 1;
    }

    // Start a tournament. Returns false if the tournament is already running.
    public boolean startTournament() {
        if (!ongoing) {
            ongoing = true;
            return true;
        }
        return false;
    }

    // End a tournament. Returns false if the tournament is not currently ongoing.
    public boolean endTournament() {
        if (ongoing) {
            ongoing = false;
            return true;
        }
        return false;
    }
}
