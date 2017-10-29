package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.comp3004.goodbyeworld.tournamentmaster.R;
import com.comp3004.goodbyeworld.tournamentmaster.data.DataHandler;
import com.comp3004.goodbyeworld.tournamentmaster.data.TMDataSet;

import java.util.ArrayList;

public class dataviewdemo extends AppCompatActivity {

    private String viewType;
    private String viewID;
    private ArrayList infoArray;
    private LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dataviewdemo);

        init();
    }

    private void init() {
        Intent contextInfo = getIntent();
        Bundle contextBundle = contextInfo.getExtras();
        contentLayout = (LinearLayout)findViewById(R.id.layoutContent);

        if (contextBundle != null) {
            if (contextBundle.containsKey("type") && contextBundle.containsKey("id")) {
                viewType = contextBundle.getString("type");
                viewID = contextBundle.getString("id");
                infoArray = DataHandler.getData(this, viewType, viewID);
                setView(viewType);
            } else {
                setView("ERR");
            }
        } else {
            setView("ERR");
        }
    }

    private void setView(String v) {
        if (v.equals("USER")) {
            // setup view for user
            ((TextView)findViewById(R.id.textViewTitle)).setText(((TMDataSet)infoArray.get(0)).getData());
            ((TextView)findViewById(R.id.textViewSubtitle)).setText("Organizations");
            for (int i=1; i<infoArray.size(); i++) {
                TextView clickText = new TextView(this);
                clickText.setText(((TMDataSet)infoArray.get(i)).getData());
                clickText.setOnClickListener(new OnClickWithTypes(this, ((TMDataSet)infoArray.get(i)).getDataType(),((TMDataSet)infoArray.get(i)).getID()));
                contentLayout.addView(clickText);
            }
        } else if (v.equals("ORG")) {
            // setup view for organization
            ((TextView)findViewById(R.id.textViewTitle)).setText(((TMDataSet)infoArray.get(0)).getData());
            ((TextView)findViewById(R.id.textViewSubtitle)).setText("Tournaments");
            for (int i=1; i<infoArray.size(); i++) {
                TextView clickText = new TextView(this);
                clickText.setText(((TMDataSet)infoArray.get(i)).getData());
                clickText.setOnClickListener(new OnClickWithTypes(this, ((TMDataSet)infoArray.get(i)).getDataType(),((TMDataSet)infoArray.get(i)).getID()));
                contentLayout.addView(clickText);
            }
        } else if (v.equals("TOURN")) {
            // setup view for organization
            ((TextView)findViewById(R.id.textViewTitle)).setText(((TMDataSet)infoArray.get(0)).getData());
            ((TextView)findViewById(R.id.textViewSubtitle)).setText("Rounds");
            for (int i=1; i<infoArray.size(); i++) {
                TextView clickText = new TextView(this);
                clickText.setText(((TMDataSet)infoArray.get(i)).getData());
                clickText.setOnClickListener(new OnClickWithTypes(this, ((TMDataSet)infoArray.get(i)).getDataType(),((TMDataSet)infoArray.get(i)).getID()));
                contentLayout.addView(clickText);
            }
        } else if (v.equals("ROUND")) {
            // setup view for organization
            ((TextView)findViewById(R.id.textViewTitle)).setText(((TMDataSet)infoArray.get(0)).getData());
            ((TextView)findViewById(R.id.textViewSubtitle)).setText("Pairings");
            for (int i=1; i<infoArray.size(); i++) {
                TextView clickText = new TextView(this);
                clickText.setText(((TMDataSet)infoArray.get(i)).getData());
                clickText.setOnClickListener(new OnClickWithTypes(this, ((TMDataSet)infoArray.get(i)).getDataType(),((TMDataSet)infoArray.get(i)).getID()));
                contentLayout.addView(clickText);
            }
        } else if (v.equals("PAIR")) {
            // setup view for organization
            ((TextView)findViewById(R.id.textViewTitle)).setText(((TMDataSet)infoArray.get(0)).getData());
            ((TextView)findViewById(R.id.textViewSubtitle)).setText("Competitors");
            for (int i=1; i<infoArray.size(); i++) {
                TextView clickText = new TextView(this);
                clickText.setText(((TMDataSet)infoArray.get(i)).getData());
                clickText.setOnClickListener(new OnClickWithTypes(this, ((TMDataSet)infoArray.get(i)).getDataType(),((TMDataSet)infoArray.get(i)).getID()));
                contentLayout.addView(clickText);
            }
        } else if (v.equals("COMPETITOR")) {
            // setup view for organization
            ((TextView)findViewById(R.id.textViewTitle)).setText(((TMDataSet)infoArray.get(1)).getData());
            ((TextView)findViewById(R.id.textViewSubtitle)).setText(((TMDataSet)infoArray.get(2)).getData());
        } else if (v.equals("ERR")) {
            // No data supplied in intent
            ((TextView)findViewById(R.id.textViewTitle)).setText("Intent not specified");
        } else {
            // Unknown type
            ((TextView)findViewById(R.id.textViewTitle)).setText("Data type unknown");
        }
    }
}
