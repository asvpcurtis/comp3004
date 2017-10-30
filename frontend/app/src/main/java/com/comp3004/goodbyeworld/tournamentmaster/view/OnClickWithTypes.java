package com.comp3004.goodbyeworld.tournamentmaster.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Rynmik on 2017-10-29.
 */

public class OnClickWithTypes implements View.OnClickListener {
    private String type;
    private String id;
    private AppCompatActivity parent;

    public OnClickWithTypes(AppCompatActivity p, String t, String i) {
        type = t;
        id = i;
        parent = p;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(parent, dataviewdemo.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", type);
        bundle.putString("id", id);
        intent.putExtras(bundle);
        parent.startActivityForResult(intent, 1);
    }

}
