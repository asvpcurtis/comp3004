package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.content.Intent;
import android.os.Bundle;
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

import com.comp3004.goodbyeworld.tournamentmaster.auth.AppHelper;
import com.comp3004.goodbyeworld.tournamentmaster.R;
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.DataHandler;
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.TMDataSet;
import com.comp3004.goodbyeworld.tournamentmaster.dataaccess.UpdateCallback;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {
    private CognitoUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Tournament Master");
        setSupportActionBar(toolbar);

        init();
    }

    protected void init() {
        String username = AppHelper.getCurrUser();
        user = AppHelper.getPool().getUser(username);
        AppHelper.getPool().getUser(username).getDetailsInBackground(detailsHandler);
        AppHelper.getPool().getUser(username).getSessionInBackground(authenticationHandler);
        TextView welcomeText = findViewById(R.id.textView2);
        String welcomeMessage = username + " is now successfully logged in!";
        welcomeText.setText(welcomeMessage);

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
            Log.i("ID TOKEN", userSession.getIdToken().getJWTToken());
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
        user.signOut();
        NavUtils.navigateUpFromSameTask(this);
    }

    public void proceedToMain(String iD) {
        Intent intent = new Intent(this, UserView.class);
        Bundle bundle = new Bundle();
        bundle.putString("type", "Account");
        bundle.putString("id", iD);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void continueActivity (View view) {
        DataHandler.getMyID(this, new UpdateCallback() {
            @Override
            public void updateData(ArrayList<TMDataSet> data) {
                proceedToMain(data.get(0).getData());
            }
        });
    }
}
