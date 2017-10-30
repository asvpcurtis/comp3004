package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GetDetailsHandler;

import com.comp3004.goodbyeworld.tournamentmaster.data.AppHelper;
import com.comp3004.goodbyeworld.tournamentmaster.R;
import com.comp3004.goodbyeworld.tournamentmaster.data.DataHandler;

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
        AppHelper.getPool().getUser(username).getSessionInBackground(authenticationHandler);
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

    AuthenticationHandler authenticationHandler = new AuthenticationHandler() {
        @Override
        public void onSuccess(CognitoUserSession userSession, CognitoDevice newDevice) {
            TextView token = findViewById(R.id.textViewIdToken);
            token.setText(userSession.getIdToken().getJWTToken().toString());
            Log.i("ID TOKEN", userSession.getIdToken().getJWTToken().toString());
        }

        @Override
        public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {

        }

        @Override
        public void getMFACode(MultiFactorAuthenticationContinuation continuation) {

        }

        @Override
        public void authenticationChallenge(ChallengeContinuation continuation) {

        }

        @Override
        public void onFailure(Exception exception) {

        }
    };

    public void signOut (View view) {
        DataHandler.clearLocal(this);
        user.signOut();
        NavUtils.navigateUpFromSameTask(this);
    }

    public void continueActivity (View view) {
        Intent intent = new Intent(this, UserView.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", "USER");
        bundle.putString("id", "00001");
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
