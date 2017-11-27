package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3004.goodbyeworld.tournamentmaster.R;
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.DataHandler;
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.TMDataSet;
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.UpdateCallback;

import java.util.ArrayList;

public class CreateView extends AppCompatActivity {

    private String viewType;
    private String viewID;
    private LinearLayout contentLayout;
    private TMDataSet baseData;
    private ArrayList<TMDataSet> competitorList;
    private ArrayList<String> nameList;
    private ArrayList<TMDataSet> seedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_view);
        init();
    }

    private void init() {
        Intent contextInfo = getIntent();
        Bundle contextBundle = contextInfo.getExtras();
        assert contextBundle != null;
        viewType = contextBundle.getString("type");
        viewID = contextBundle.getString("id");
        contentLayout = findViewById(R.id.layoutContent);

        populateView(DataHandler.getCreate(viewType));
    }

    private void populateView(ArrayList<TMDataSet> data) {
        baseData = data.get(0);
        String title = "Create " + viewType;
        ((TextView)findViewById(R.id.textViewTitle)).setText(title);
        for (int i=1; i<data.size(); i++) {
            EditText editText = new EditText(this);
            editText.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
            editText.setText("");
            editText.setHint(data.get(i).getDataType());
            contentLayout.addView(editText);
        }
        if (viewType.equals("Tournament")) {
            competitorList = null;
            DataHandler.getCompetitors(this, viewID, new UpdateCallback() {
                @Override
                public void updateData(ArrayList<TMDataSet> data){
                    buildCompetitorList(data);
                }
            });
        }
    }

    private void buildCompetitorList(ArrayList<TMDataSet> data) {
        // Kill the wheel!
        findViewById(R.id.progressBarLoading).setVisibility(View.GONE);
        // Build the lists!
        competitorList = data;
        seedList = new ArrayList<>();
        nameList = new ArrayList<>();
        nameList.add("Select Competitor...");
        for (TMDataSet i : data) {
            nameList.add(i.getData());
        }

        Button addButton = findViewById(R.id.buttonAddPairing);
        addButton.setVisibility(View.VISIBLE);
        addButton.setEnabled(true);
    }

    public void pairBuild(View v) {
        View line = new View(this);
        line.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
        line.setBackgroundColor(Color.BLACK);
        contentLayout.addView(line);
        Button save = findViewById(R.id.buttonSave);
        save.setEnabled(false);
        save.getBackground().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinAA = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nameList);
        spinner.setAdapter(spinAA);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0) {
                    String name = parent.getItemAtPosition(position).toString();
                    seedList.add(competitorList.remove(position-1));
                    nameList.remove(position);
                    // Create Text of Player One
                    TextView playerOne = new TextView(parent.getContext());
                    playerOne.setText(name);
                    playerOne.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    contentLayout.addView(playerOne);
                    // Create VS
                    TextView vs = new TextView(parent.getContext());
                    vs.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    vs.setGravity(Gravity.CENTER_HORIZONTAL);
                    vs.setTextColor(Color.DKGRAY);
                    vs.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                    String vsText = "--Versus--";
                    vs.setText(vsText);
                    contentLayout.addView(vs);

                    parent.setVisibility(View.GONE);
                    pairBuildSecond();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        contentLayout.addView(spinner);
    }

    private void pairBuildSecond() {
        Spinner spinner = new Spinner(this);
        ArrayAdapter<String> spinAA = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nameList);
        spinner.setAdapter(spinAA);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position!=0) {
                    String name = parent.getItemAtPosition(position).toString();
                    seedList.add(competitorList.remove(position-1));
                    nameList.remove(position);
                    // Create Text of Player Two
                    TextView playerTwo = new TextView(parent.getContext());
                    playerTwo.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    playerTwo.setGravity(Gravity.END);
                    playerTwo.setText(name);
                    playerTwo.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    contentLayout.addView(playerTwo);
                    Button save = findViewById(R.id.buttonSave);
                    save.setEnabled(true);
                    save.getBackground().setColorFilter(null);
                    parent.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        contentLayout.addView(spinner);
    }


    public void saveEdit(View v) {
        ArrayList<TMDataSet> newData = new ArrayList<>();
        newData.add(baseData);
        for (int i=0; i<contentLayout.getChildCount(); i++) {
            View editBox = contentLayout.getChildAt(i);
            if (editBox instanceof EditText) {
                newData.add(new TMDataSet(((EditText)editBox).getText().toString(), ((EditText)editBox).getHint().toString(), null));
            }
        }
        if (viewType.equals("Competitor")) {
            newData.add(new TMDataSet("1200", "rating", null));
            newData.add(new TMDataSet(viewID, "organizationId", null));
        } else if (viewType.equals("Tournament")) {
            newData.add(new TMDataSet("2009-02-15T00:00:00", "startDate", null));
            newData.add(new TMDataSet("1", "format", null));
            newData.add(new TMDataSet("true", "onGoing", null));
            newData.add(new TMDataSet(viewID, "organizationId", null));
            newData.add(new TMDataSet(DataHandler.makeCompetitorList(seedList), "competitors", null));
        }
        DataHandler.setCreate(this, viewType, newData, new UpdateCallback() {
            @Override
            public void updateData(ArrayList<TMDataSet> data) {
                Toast.makeText(getApplicationContext(), viewType + " created!", Toast.LENGTH_LONG).show();
                setResult(RESULT_OK, null);
                finish();
            }
        });
    }

    public void cancelEdit(View v) {
        Toast.makeText(getApplicationContext(), "Creation canceled!", Toast.LENGTH_LONG).show();
        setResult(RESULT_CANCELED, null);
        finish();
    }
}
