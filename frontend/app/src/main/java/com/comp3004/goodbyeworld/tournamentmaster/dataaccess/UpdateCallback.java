package com.comp3004.goodbyeworld.tournamentmaster.dataaccess;

import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.TMDataSet;

import java.util.ArrayList;

/**
 * Created by Michael Souter on 2017-11-12.
 */

public interface UpdateCallback {
    void updateData(ArrayList<TMDataSet> data);
}
