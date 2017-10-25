package com.comp3004.goodbyeworld.tournamentmaster.data;

/**
 * Created by Shukri on 2017-09-29.
 */

public class Org {

    /** id not yet assigned by database */
    public static final long UNDEFINED = -1;

    long id;
    String name;
    int icon;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Org(String name, int icon) {

        id = UNDEFINED;
        this.name = name;
        icon = -1;
    }

    public Org(long id, String name, int icon) {

        this.id = id;
        this.name = name;
        this.icon = icon;
    }

    @Override
    public String toString() {
        return name;
    }
}
