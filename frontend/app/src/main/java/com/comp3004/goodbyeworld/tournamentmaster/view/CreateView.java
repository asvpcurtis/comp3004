package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3004.goodbyeworld.tournamentmaster.R;
import com.comp3004.goodbyeworld.tournamentmaster.data.DataHandler;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CreateView extends AppCompatActivity {

    private String viewType;
    private String viewID;
    private LinkedHashMap<String, String> infoArray;
    private LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_view);
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
                infoArray = new LinkedHashMap<String, String>();
                setView(viewType);
            } else {
                setView("ERR");
            }
        } else {
            setView("ERR");
        }
    }
    private void setView(String v) {
        if (viewType.equals("USER")) {
            // Title
            ((TextView)findViewById(R.id.textViewTitle)).setText("Create Organization");
            // Fields
            EditText editText = new EditText(this);
            editText.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
            editText.setText("");
            editText.setHint("name");
            contentLayout.addView(editText);
        } else if (viewType.equals("ORG")) {
            // Title
            ((TextView)findViewById(R.id.textViewTitle)).setText("Add New Competitor");
            // Fields
            ArrayList<String> fields = new ArrayList<String>();
            fields.add("first");
            fields.add("last");
            fields.add("email");
            for (String x: fields) {
                EditText editText = new EditText(this);
                editText.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
                editText.setText("");
                editText.setHint(x);
                contentLayout.addView(editText);
            }
        }
    }

    public void saveEdit(View v) {
        for (int i=0; i<contentLayout.getChildCount(); i++) {
            View editBox = contentLayout.getChildAt(i);
            if (editBox instanceof EditText) {
                infoArray.put(((EditText)editBox).getHint().toString(), ((EditText)editBox).getText().toString());
            }
        }
        if (viewType.equals("USER")) {
            boolean results = DataHandler.setCreate(this, viewType, viewID, infoArray);
            if (results) {
                Toast.makeText(getApplicationContext(), "Organization created!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, null);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Creation failed!", Toast.LENGTH_LONG).show();
            }
        } else if (viewType.equals("ORG")) {
            boolean results = DataHandler.addCompetitor(this, viewType, viewID, infoArray);
            if (results) {
                Toast.makeText(getApplicationContext(), "Competitor added!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK, null);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Creation failed!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void cancelEdit(View v) {
        Toast.makeText(getApplicationContext(), "Creation canceled!", Toast.LENGTH_LONG).show();
        setResult(RESULT_CANCELED, null);
        finish();
    }
}
