package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
        if (viewType.equals("USER")) {
            ((TextView) findViewById(R.id.textViewTitle)).setText(((TMDataSet) infoArray.get(0)).getData());
            ((TextView) findViewById(R.id.textViewID)).setText(((TMDataSet) infoArray.get(0)).getID());
            TextView header = new TextView(this);
            header.setText("Organizations");
            header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            header.setGravity(Gravity.CENTER_HORIZONTAL);
            header.setBackgroundResource(R.color.colorListTop);
            header.setTextColor(Color.WHITE);
            header.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            contentLayout.addView(header);

            for (int i = 1; i < infoArray.size(); i++) {
                TextView clickText = new TextView(this);
                clickText.setText(((TMDataSet) infoArray.get(i)).getData());
                clickText.setTextColor(Color.WHITE);
                clickText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                clickText.setOnClickListener(new OnClickWithTypes(this, ((TMDataSet) infoArray.get(i)).getDataType(), ((TMDataSet) infoArray.get(i)).getID()));
                contentLayout.addView(clickText);
            }
        } else if (viewType.equals("COMPETITOR")) {
            ((Button) findViewById(R.id.buttonCreate)).setEnabled(false);
            ((Button) findViewById(R.id.buttonCreate)).setVisibility(View.INVISIBLE);
            ((ImageView) findViewById(R.id.imageViewUserIcon)).setVisibility(View.INVISIBLE);

            ((TextView) findViewById(R.id.textViewTitle)).setText(((TMDataSet) infoArray.get(1)).getData());
            ((TextView) findViewById(R.id.textViewID)).setText(((TMDataSet) infoArray.get(0)).getID());
            TextView clickText = new TextView(this);
            clickText.setText(((TMDataSet) infoArray.get(2)).getData());
            clickText.setTextColor(Color.WHITE);
            clickText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            contentLayout.addView(clickText);
        } else if (viewType.equals("ORG")) {
            ((Button)findViewById(R.id.buttonCreate)).setText("Add New Competitor");
            ((ImageView)findViewById(R.id.imageViewUserIcon)).setVisibility(View.INVISIBLE);
            ((TextView) findViewById(R.id.textViewTitle)).setText(((TMDataSet) infoArray.get(0)).getData());
            ((TextView) findViewById(R.id.textViewID)).setText(((TMDataSet) infoArray.get(0)).getID());
            TextView header = new TextView(this);
            header.setText("Tournaments");
            header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            header.setGravity(Gravity.CENTER_HORIZONTAL);
            header.setBackgroundResource(R.color.colorListTop);
            header.setTextColor(Color.WHITE);
            header.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
            contentLayout.addView(header);

            for (int i = 1; i < infoArray.size(); i++) {
                TextView clickText = new TextView(this);
                clickText.setText(((TMDataSet) infoArray.get(i)).getData());
                clickText.setTextColor(Color.WHITE);
                clickText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                clickText.setOnClickListener(new OnClickWithTypes(this, ((TMDataSet) infoArray.get(i)).getDataType(), ((TMDataSet) infoArray.get(i)).getID()));
                contentLayout.addView(clickText);
            }
            TextView header2 = new TextView(this);
            header2.setText("Competitors");
            header2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            header2.setGravity(Gravity.CENTER_HORIZONTAL);
            header2.setBackgroundResource(R.color.colorListTop);
            header2.setTextColor(Color.WHITE);
            header2.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
            contentLayout.addView(header2);

            infoArray = DataHandler.getCompetitors(this, viewID);
            for (int i = 0; i < infoArray.size(); i++) {
                TextView clickText = new TextView(this);
                clickText.setText(((TMDataSet) infoArray.get(i)).getData());
                clickText.setTextColor(Color.WHITE);
                clickText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                clickText.setOnClickListener(new OnClickWithTypes(this, ((TMDataSet) infoArray.get(i)).getDataType(), ((TMDataSet) infoArray.get(i)).getID()));
                contentLayout.addView(clickText);
            }
        } else {
            ((Button)findViewById(R.id.buttonCreate)).setEnabled(false);
            ((Button)findViewById(R.id.buttonCreate)).setVisibility(View.INVISIBLE);
            ((ImageView)findViewById(R.id.imageViewUserIcon)).setVisibility(View.INVISIBLE);
            // setup view for others
            ((TextView) findViewById(R.id.textViewTitle)).setText(((TMDataSet) infoArray.get(0)).getData());
            ((TextView) findViewById(R.id.textViewID)).setText(((TMDataSet) infoArray.get(0)).getID());
            TextView header = new TextView(this);
            if (viewType.equals("TOURN")) {
                header.setText("Rounds");
            } else if (viewType.equals("ROUND")) {
                header.setText("Pairings");
            } else if (viewType.equals("PAIR")) {
                header.setText("Competitors");
            }
            header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            header.setGravity(Gravity.CENTER_HORIZONTAL);
            header.setBackgroundResource(R.color.colorListTop);
            header.setTextColor(Color.WHITE);
            header.setTextSize(TypedValue.COMPLEX_UNIT_SP,24);
            contentLayout.addView(header);

            for (int i = 1; i < infoArray.size(); i++) {
                TextView clickText = new TextView(this);
                clickText.setText(((TMDataSet) infoArray.get(i)).getData());
                clickText.setTextColor(Color.WHITE);
                clickText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                clickText.setOnClickListener(new OnClickWithTypes(this, ((TMDataSet) infoArray.get(i)).getDataType(), ((TMDataSet) infoArray.get(i)).getID()));
                contentLayout.addView(clickText);
            }
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
        Intent intent = new Intent(this, EditView.class);
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
            setResult(RESULT_OK, null);
            init();
        }
    }
}
