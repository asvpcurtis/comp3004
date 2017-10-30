package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.comp3004.goodbyeworld.tournamentmaster.R;
import com.comp3004.goodbyeworld.tournamentmaster.data.DataHandler;
import com.comp3004.goodbyeworld.tournamentmaster.data.TMDataSet;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class editviewdemo extends AppCompatActivity {
    private String viewType;
    private String viewID;
    private LinkedHashMap<String, String> infoArray;
    private LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editviewdemo);
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
                infoArray = DataHandler.getEdit(this, viewType, viewID);
                setView(viewType);
            } else {
                setView("ERR");
            }
        } else {
            setView("ERR");
        }
    }
    private void setView(String v) {
        ((TextView)findViewById(R.id.textViewTitle)).setText(viewType + " Edit Options");
        for (String key : infoArray.keySet()) {
            EditText editText = new EditText(this);
            editText.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.MATCH_PARENT, android.widget.LinearLayout.LayoutParams.WRAP_CONTENT));
            editText.setText(infoArray.get(key));
            editText.setHint(key);
            contentLayout.addView(editText);
        }
    }

    public void saveEdit(View v) {
        for (int i=0; i<contentLayout.getChildCount(); i++) {
            View editBox = contentLayout.getChildAt(i);
            if (editBox instanceof EditText) {
                infoArray.put(((EditText)editBox).getHint().toString(), ((EditText)editBox).getText().toString());
            }
        }
        boolean results = DataHandler.setEdit(this, viewType, viewID, infoArray);
        if (results) {
            Toast.makeText(getApplicationContext(), viewType + " updated!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, dataviewdemo.class);
            Bundle bundle = new Bundle();
            bundle.putString("type", viewType);
            bundle.putString("id", viewID);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), viewType + " update failed!", Toast.LENGTH_LONG).show();
        }
    }

    public void cancelEdit(View v) {
        Toast.makeText(getApplicationContext(), viewType + " update canceled!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, dataviewdemo.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", viewType);
        bundle.putString("id", viewID);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
