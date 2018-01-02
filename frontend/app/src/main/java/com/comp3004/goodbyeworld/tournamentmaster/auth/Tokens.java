package com.comp3004.goodbyeworld.tournamentmaster.auth;

/**
 * Created by Michael Souter on 2017-11-12.
 */

public class Tokens {
    public static String getIDToken() {
        return AppHelper.getCurrSession().getIdToken().getJWTToken();
    }
}
