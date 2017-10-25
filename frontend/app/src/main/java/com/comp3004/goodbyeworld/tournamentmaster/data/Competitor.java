package com.comp3004.goodbyeworld.tournamentmaster.data;

/**
 * Created by Michael Souter
 */

public class Competitor {
    private String firstName;
    private String lastName;
    private String eMail;
    private int rating;
    private int id;
    private int orgID;
    private char gender;

    // Get methods for properties
    public String getName() {
        return (firstName + " " + lastName);
    }
    public String getEMail() {
        return eMail;
    }
    public int getRating() {
        return rating;
    }
    public int getID() {
        return id;
    }
    public int getOrgID() {
        return orgID;
    }
    public char getGender() {
        return gender;
    }

    // Initialize a Competitor
    public Competitor(String f, String l, String e, int r, int i, int o, char g) {
        firstName = f;
        lastName = l;
        eMail = e;
        rating = r;
        id = i;
        orgID = o;
        gender = g;
    }

    // Set a rating to a new value
    public int setRating(int newRating) {
        rating = newRating;
        return rating;
    }

    // Modify a rating with a new value (+ or -)
    public int modifyRating(int modifier) {
        rating += modifier;
        return rating;
    }

}
