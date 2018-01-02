package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;


import com.comp3004.goodbyeworld.tournamentmaster.R;
import com.comp3004.goodbyeworld.tournamentmaster.auth.AppHelper;
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
    private ArrayList<TMDataSet> pairingCompetitors = null;
    private String selectedCompetitor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
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
            public void updateData(ArrayList<TMDataSet> data) {
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
                findViewById(R.id.buttonPrintTourn).setEnabled(true);
                findViewById(R.id.buttonPrintTourn).setVisibility(View.VISIBLE);
                ((ImageView) findViewById(R.id.imageViewUserIcon)).setImageResource(R.drawable.tourn);
                break;
            case "Round":
                ((ImageView) findViewById(R.id.imageViewUserIcon)).setImageResource(R.drawable.tourn);
                break;
            case "Pairing":
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

        if (viewType.equals("Pairing")) {
            pairingCompetitors = new ArrayList<>();
            final Context myCtx = this;
            DataHandler.getEdit(this, viewType, viewID, new UpdateCallback() {
                @Override
                public void updateData(ArrayList<TMDataSet> data) {
                    final TMDataSet baseData = data.get(0);
                    if (data.get(4).getData().equals("null")) {
                        ArrayList<String> spinList = new ArrayList<>();
                        spinList.add(data.get(1).getData());
                        pairingCompetitors.add(data.get(2));
                        spinList.add(data.get(2).getData());
                        pairingCompetitors.add(data.get(3));
                        spinList.add(data.get(3).getData());
                        Spinner spinner = new Spinner(myCtx);
                        ArrayAdapter<String> spinAA = new ArrayAdapter<>(myCtx, android.R.layout.simple_spinner_dropdown_item, spinList);
                        spinner.setAdapter(spinAA);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if (position != 0) {
                                    selectedCompetitor = pairingCompetitors.get(position - 1).getID();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        contentLayout.addView(spinner);
                        Button saveResult = new Button(myCtx);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        saveResult.setText("Update Results");
                        saveResult.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (selectedCompetitor != null) {
                                    ArrayList<TMDataSet> newData = new ArrayList<>();
                                    newData.add(baseData);
                                    newData.add(new TMDataSet(selectedCompetitor, "result", null));
                                    DataHandler.setEdit(myCtx, viewType, viewID, newData, new UpdateCallback() {
                                        @Override
                                        public void updateData(ArrayList<TMDataSet> data) {
                                            Toast.makeText(getApplicationContext(), viewType + " updated!", Toast.LENGTH_LONG).show();
                                            setResult(RESULT_OK, null);
                                            finish();
                                        }
                                    });
                                } else {
                                    Toast.makeText(getApplicationContext(), "Winner Not Selected", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                        contentLayout.addView(saveResult, params);
                    }
                }
            });
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
        ((TextView) pairLayout.findViewById(R.id.textViewCompetitorOne)).setText(c1);
        ((TextView) pairLayout.findViewById(R.id.textViewCompetitorTwo)).setText(c2);
        ((TextView) pairLayout.findViewById(R.id.textViewStatus)).setText(r);
        pairLayout.setOnClickListener(new OnClickWithTypes(this, type, iD));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 8, 8, 8);
        pairLayout.setLayoutParams(params);
        return pairLayout;
    }

    public void buttonPrint(View view) {
        DataHandler.getTournamentData(this, viewID, new UpdateCallback() {
            @Override
            public void updateData(ArrayList<TMDataSet> data) {
                makePrintableTournament(data);
            }
        });

    }

    public void makePrintableTournament(final ArrayList<TMDataSet> data) {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View printLayout = inflater.inflate(R.layout.printlayout, null, false);

        int count = 2;

        GridLayout pairGrid = null;
        while (count < data.size()) {
            if (data.get(count).getDataType().equals("Round")) {
                TextView header = new TextView(this);
                header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                header.setGravity(Gravity.CENTER_HORIZONTAL);
                header.setBackgroundResource(R.color.colorListTop);
                header.setTextColor(Color.WHITE);
                header.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                header.setText(data.get(count).getData());
                LinearLayout layout = printLayout.findViewById(R.id.linearPrintContent);
                LinearLayout breaker = new LinearLayout(this);
                breaker.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                breaker.setMinimumHeight(20);
                layout.addView(breaker);
                layout.addView(header);
                pairGrid = new GridLayout(this);
                pairGrid.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                pairGrid.setColumnCount(9);
                breaker = new LinearLayout(this);
                breaker.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                breaker.setMinimumHeight(20);
                layout.addView(breaker);
                layout.addView(pairGrid);
            } else {
                View pair = makeClickablePairing(data.get(count).getData(), data.get(count).getDataType(), data.get(count).getID());
                LinearLayout breaker = new LinearLayout(this);
                breaker.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                breaker.setMinimumWidth(20);
                pairGrid.addView(breaker);
                pair.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                pair.setMinimumWidth(460);
                pairGrid.addView(pair);
                breaker = new LinearLayout(this);
                breaker.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                breaker.setMinimumWidth(20);
                pairGrid.addView(breaker);
            }
            count++;
        }

        AbstractViewRenderer page = new AbstractViewRenderer(this, printLayout) {
            private String _text;

            public void setText(String text) {
                _text = text;
            }

            @Override
            protected void initView(View view) {
                JSONObject tournament = null;
                String organization = null;
                try {
                    tournament = new JSONObject(data.get(0).getData());
                    ((TextView)view.findViewById(R.id.textViewTournamentName)).setText(tournament.getString("name"));
                    ((TextView)view.findViewById(R.id.textViewStartDate)).setText(tournament.getString("startDate"));
                    if (tournament.getInt("format") == 1) {
                        ((TextView)view.findViewById(R.id.textViewFormat)).setText("Elimination");
                    } else {
                        ((TextView)view.findViewById(R.id.textViewFormat)).setText("Round Robin");
                    }

                    organization = data.get(1).getData();
                    ((TextView)view.findViewById(R.id.textViewOrgName)).setText(organization);

                    ((TextView)view.findViewById(R.id.textViewUsername)).setText(AppHelper.getCurrUser());



                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                File newfilepath = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DOWNLOADS), "Tournaments");
                File newfile = new File(newfilepath, viewID + ".pdf");

                try {
                    FileInputStream in = new FileInputStream(file);
                    FileOutputStream fos = new FileOutputStream(newfile);
                    byte[] b = new byte[8];
                    int i;
                    while ((i = in.read(b)) != -1) {
                        fos.write(b, 0, i);
                    }
                    fos.flush();
                    fos.close();
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri path = Uri.fromFile(newfile);
                Intent objIntent = new Intent(Intent.ACTION_VIEW);
                objIntent.setDataAndType(path, "application/pdf");
                objIntent.setFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(objIntent);//Staring the pdf viewer
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

    public void addClick(View view) {
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
            for (int i = 0; i < contentLayout.getChildCount(); i++) {
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
