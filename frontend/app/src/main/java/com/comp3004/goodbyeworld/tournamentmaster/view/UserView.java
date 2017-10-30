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

public class UserView extends AppCompatActivity {
    private String viewType;
    private String viewID;
    private ArrayList infoArray;
    private LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        init();
    }

    private void init() {
        Intent contextInfo = getIntent();
        Bundle contextBundle = contextInfo.getExtras();
        contentLayout = (LinearLayout) findViewById(R.id.layoutContent);

        if (contextBundle != null) {
            if (contextBundle.containsKey("type") && contextBundle.containsKey("id")) {
                viewType = contextBundle.getString("type");
                viewID = contextBundle.getString("id");
                infoArray = DataHandler.getData(this, viewType, viewID);
                setView();
            } else {
                // error
            }
        } else {
            // error
        }
    }

    private void setView() {
        ((TextView)findViewById(R.id.textViewUser)).setText(((TMDataSet)infoArray.get(0)).getData());
        ((TextView)findViewById(R.id.textViewID)).setText(((TMDataSet)infoArray.get(0)).getID());
        for (int i = 1; i < infoArray.size(); i++) {
            TextView clickText = new TextView(this);
            clickText.setText(((TMDataSet) infoArray.get(i)).getData());
            clickText.setOnClickListener(new OnClickWithTypes(this, ((TMDataSet)infoArray.get(i)).getDataType(), ((TMDataSet)infoArray.get(i)).getID()));
            contentLayout.addView(clickText);
        }
    }

    public void createClick(View view) {
        Intent intent = new Intent(this, CreateView.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", viewType);
        bundle.putString("id", viewID);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivityForResult(intent, 1);
    }

    public void editClick(View view) {
        Intent intent = new Intent(this, editviewdemo.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", viewType);
        bundle.putString("id", viewID);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        if (resultCode == RESULT_OK) {
            contentLayout.removeAllViewsInLayout();
            init();
        }
    }
}
