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
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.DataHandler;
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.TMDataSet;
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.UpdateCallback;

import java.util.ArrayList;

public class CreateView extends AppCompatActivity {

    private String viewType;
    private LinearLayout contentLayout;
    private TMDataSet baseData;

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
