package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;

import com.comp3004.goodbyeworld.tournamentmaster.data.AppHelper;
import com.comp3004.goodbyeworld.tournamentmaster.R;

public class WelcomeActivity extends AppCompatActivity {
    // Cognito user objects
    private String username;
    private CognitoUser user;
    private CognitoUserSession session;
    private CognitoUserDetails details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    protected void init() {
        username = AppHelper.getCurrUser();
        user = AppHelper.getPool().getUser(username);
        AppHelper.getPool().getUser(username).getDetailsInBackground(detailsHandler);
        TextView welcomeText = findViewById(R.id.textView2);
        welcomeText.setText("Welcome " + username);

    }

    GetDetailsHandler detailsHandler = new GetDetailsHandler() {
        @Override
        public void onSuccess(CognitoUserDetails cognitoUserDetails) {
            // Store details in the AppHandler
            AppHelper.setUserDetails(cognitoUserDetails);
        }

        @Override
        public void onFailure(Exception exception) {
            //
        }
    };

    public void signOut (View view) {
        user.signOut();
        NavUtils.navigateUpFromSameTask(this);
    }


}
