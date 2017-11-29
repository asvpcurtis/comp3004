package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;


import com.comp3004.goodbyeworld.tournamentmaster.R;
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.DataHandler;
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.TMDataSet;
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.UpdateCallback;
import com.hendrix.pdfmyxml.PdfDocument;
import com.hendrix.pdfmyxml.viewRenderer.AbstractViewRenderer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.util.ArrayList;

public class UserView extends AppCompatActivity {
    private LinearLayout contentLayout;
    private String viewType = null;
    private String viewID = null;
    private PdfDocument doc = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        init();
    }

    private void init() {
        Intent contextInfo = getIntent();
        Bundle contextBundle = contextInfo.getExtras();
        assert contextBundle != null;
        viewType = contextBundle.getString("type");
        viewID = contextBundle.getString("id");
        contentLayout = findViewById(R.id.layoutContent);

        DataHandler.getData(this, viewType, viewID, new UpdateCallback() {
            @Override
            public void updateData(ArrayList<TMDataSet> data){
                populateView(data);
            }
        });
    }

    private void populateView(ArrayList<TMDataSet> data) {
        // Kill the wheel!
        findViewById(R.id.progressBarLoading).setVisibility(View.GONE);

        // check if data is null -- i.e. an access error
        if (data == null) {
            View view = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(view, R.string.access_error, Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction(R.string.back, new OnClickListener() {
                public void onClick(View v) {
                    finish();
                }
            });
            snackbar.show();

            return;
        }

        // First Element of data is the main subject
        ((TextView) findViewById(R.id.textViewTitle)).setText(data.get(0).getData());
        ((TextView) findViewById(R.id.textViewID)).setText(data.get(0).getID());

        // Get info layouts
        LinearLayout orgLayout = findViewById(R.id.layoutOrganizations);
        LinearLayout tournLayout = findViewById(R.id.layoutTournaments);
        LinearLayout roundLayout = findViewById(R.id.layoutRounds);
        LinearLayout pairLayout = findViewById(R.id.layoutPairings);
        LinearLayout compLayout = findViewById(R.id.layoutCompetitors);
        LinearLayout infoLayout = findViewById(R.id.layoutInformation);

        // Set Button Visibility
        findViewById(R.id.buttonEdit).setEnabled(false);
        findViewById(R.id.buttonEdit).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonAddAccount).setEnabled(false);
        findViewById(R.id.buttonAddAccount).setVisibility(View.INVISIBLE);
        findViewById(R.id.buttonPrintTourn).setEnabled(false);
        findViewById(R.id.buttonPrintTourn).setVisibility(View.INVISIBLE);
        switch (viewType) {
            case "Account":
                String orgLabel = "Create Organization";
                ((Button) findViewById(R.id.buttonCreate)).setText(orgLabel);
                findViewById(R.id.buttonCreate).setEnabled(true);
                findViewById(R.id.buttonCreate).setVisibility(View.VISIBLE);
                ((ImageView) findViewById(R.id.imageViewUserIcon)).setImageResource(R.drawable.user);
                break;
            case "Organization":
                String tournLabel = "Create Tournament";
                String competitorLabel = "Add Competitor";
                ((Button) findViewById(R.id.buttonCreate)).setText(tournLabel);
                ((Button) findViewById(R.id.buttonCreateCompetitor)).setText(competitorLabel);
                findViewById(R.id.buttonCreate).setEnabled(true);
                findViewById(R.id.buttonCreate).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonCreateCompetitor).setEnabled(true);
                findViewById(R.id.buttonCreateCompetitor).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonAddAccount).setEnabled(true);
                findViewById(R.id.buttonAddAccount).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonEdit).setEnabled(true);
                findViewById(R.id.buttonEdit).setVisibility(View.VISIBLE);
                ((ImageView) findViewById(R.id.imageViewUserIcon)).setImageResource(R.drawable.org);
                break;
            case "Tournament":
                ((ImageView) findViewById(R.id.imageViewUserIcon)).setImageResource(R.drawable.tourn);
                break;
            case "Round":
                ((ImageView) findViewById(R.id.imageViewUserIcon)).setImageResource(R.drawable.tourn);
                break;
            case "Pairing":
                findViewById(R.id.buttonEdit).setEnabled(true);
                findViewById(R.id.buttonEdit).setVisibility(View.VISIBLE);
                ((ImageView) findViewById(R.id.imageViewUserIcon)).setImageResource(R.drawable.tourn);
                break;
            case "Competitor":
                ((ImageView) findViewById(R.id.imageViewUserIcon)).setImageResource(R.drawable.competitor);
                break;
        }

        data.remove(0);

        // Populate Information
        for (TMDataSet i : data) {
            switch (i.getDataType()) {
                case "Organization":
                    if (orgLayout.getChildCount() == 0) {
                        orgLayout.addView(makeHeader("Organizations"));
                    }
                    orgLayout.addView(makeClickable(i.getData(), i.getDataType(), i.getID()));
                    break;
                case "Tournament":
                    if (tournLayout.getChildCount() == 0) {
                        tournLayout.addView(makeHeader("Tournaments"));
                    }
                    tournLayout.addView(makeClickable(i.getData(), i.getDataType(), i.getID()));
                    break;
                case "Round":
                    if (roundLayout.getChildCount() == 0) {
                        roundLayout.addView(makeHeader("Rounds"));
                    }
                    roundLayout.addView(makeClickable(i.getData(), i.getDataType(), i.getID()));
                    break;
                case "Pairing":
                    if (pairLayout.getChildCount() == 0) {
                        pairLayout.addView(makeHeader("Pairings"));
                    }
                    pairLayout.addView(makeClickablePairing(i.getData(), i.getDataType(), i.getID()));
                    break;
                case "Competitor":
                    if (compLayout.getChildCount() == 0) {
                        compLayout.addView(makeHeader("Competitors"));
                    }
                    compLayout.addView(makeClickable(i.getData(), i.getDataType(), i.getID()));
                    break;
                default:
                    if (infoLayout.getChildCount() == 0) {
                        infoLayout.addView(makeHeader("Information"));
                    }
                    TextView toAdd = new TextView(this);
                    toAdd.setText(String.format("%s%s", i.getDataType(), i.getData()));
                    toAdd.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    infoLayout.addView(toAdd);
                    break;
            }
        }
    }

    private TextView makeHeader(String text) {
        TextView header = new TextView(this);
        header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setGravity(Gravity.CENTER_HORIZONTAL);
        header.setBackgroundResource(R.color.colorListTop);
        header.setTextColor(Color.WHITE);
        header.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        header.setText(text);
        return header;
    }

    private TextView makeClickable(String text, String type, String iD) {
        TextView clickText = new TextView(this);
        clickText.setText(text);
        clickText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        clickText.setOnClickListener(new OnClickWithTypes(this, type, iD));
        return clickText;
    }

    private View makeClickablePairing(String text, String type, String iD) {
        View pairLayout = LayoutInflater.from(this).inflate(R.layout.pairlayout, null, false);
        String c1 = null;
        String c2 = null;
        String r = null;
        try {
            JSONObject obj = new JSONObject(text);
            c1 = obj.getString("competitorOne");
            c2 = obj.getString("competitorTwo");
            r = obj.getString("result");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((TextView)pairLayout.findViewById(R.id.textViewCompetitorOne)).setText(c1);
        ((TextView)pairLayout.findViewById(R.id.textViewCompetitorTwo)).setText(c2);
        ((TextView)pairLayout.findViewById(R.id.textViewStatus)).setText(r);
        pairLayout.setOnClickListener(new OnClickWithTypes(this, type, iD));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8,8,8,8);
        pairLayout.setLayoutParams(params);
        return pairLayout;
    }

    public void makePrintableTournament(View view) {
        final Context ctx = this;
        AbstractViewRenderer page = new AbstractViewRenderer(this, R.layout.printlayout) {
            private String _text;

            public void setText(String text) {
                _text = text;
            }

            @Override
            protected void initView(View view) {

            }
        };

        doc = new PdfDocument(this);

        doc.addPage(page);

        doc.setRenderWidth(2115);
        doc.setRenderHeight(1500);
        doc.setOrientation(PdfDocument.A4_MODE.LANDSCAPE);
        doc.setProgressTitle(R.string.gen_please_wait);
        doc.setProgressMessage(R.string.gen_pdf_file);
        doc.setFileName("test");
        doc.setInflateOnMainThread(false);
        doc.setListener(new PdfDocument.Callback() {
            @Override
            public void onComplete(File file) {
                Log.i(PdfDocument.TAG_PDF_MY_XML, "Complete");
            }

            @Override
            public void onError(Exception e) {
                Log.i(PdfDocument.TAG_PDF_MY_XML, "Error");
            }
        });

        doc.createPdf(this);
    }

    public void createClick(View view) {
        Intent intent = new Intent(this, CreateView.class);
        Bundle bundle = new Bundle();
        if (viewType.equals("Account")) {
            bundle.putString("type", "Organization");
        } else if (viewType.equals("Organization")) {
            bundle.putString("type", "Tournament");
        }
        bundle.putString("id", viewID);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivityForResult(intent, 1);
    }

    public void createCompetitorClick(View view) {
        Intent intent = new Intent(this, CreateView.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", "Competitor");
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

    public void addClick( View view) {
        Intent intent = new Intent(this, EditView.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", "addAccount");
        bundle.putString("id", viewID);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            for (int i=0; i<contentLayout.getChildCount(); i++) {
                View layout = contentLayout.getChildAt(i);
                if (layout instanceof LinearLayout) {
                    ((LinearLayout) layout).removeAllViewsInLayout();
                }
            }
            setResult(RESULT_OK, null);
            init();
        } else if (resultCode == RESULT_FIRST_USER) {
            setResult(RESULT_OK, null);
            finish();
        }
    }
}
