package com.comp3004.goodbyeworld.tournamentmaster.view;

import android.content.DialogInterface;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.comp3004.goodbyeworld.tournamentmaster.R;
import com.comp3004.goodbyeworld.tournamentmaster.data.AppHelper;

public class RegisterActivity extends AppCompatActivity {

    private AlertDialog userDialog;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void registerClick(View view) {
        registerNewUser();
    }

    public void confirmClick(View view) {
        confirmNewUser();
    }

    private void registerNewUser() {
        CognitoUserAttributes cognitoUserAttributes = new CognitoUserAttributes();
        EditText usernameIn = findViewById(R.id.editTextUsername);
        username = usernameIn.getText().toString();

        EditText passwordIn = findViewById(R.id.editTextPassword);
        String password = passwordIn.getText().toString();

        // add names
        EditText input = findViewById(R.id.editTextFirstName);
        cognitoUserAttributes.addAttribute("given_name", input.getText().toString());
        input = findViewById(R.id.editTextLastName);
        cognitoUserAttributes.addAttribute("family_name", input.getText().toString());

        // add email
        input = findViewById(R.id.editTextEMail);
        cognitoUserAttributes.addAttribute("email", input.getText().toString());

        // add phone number
        //input = findViewById(R.id.editTextPhoneNumber);
        //cognitoUserAttributes.addAttribute("phone_number", input.getText().toString());

        AppHelper.getPool().signUpInBackground(username, password, cognitoUserAttributes, null, signUpHandler);

    }

    private void confirmNewUser() {
        username = ((TextView)findViewById(R.id.editTextUsername)).getText().toString();
        String confirmCode = ((TextView)findViewById(R.id.editTextConfCode)).getText().toString();
        AppHelper.getPool().getUser(username).confirmSignUpInBackground(confirmCode, true, confHandler);
    }

    GenericHandler confHandler = new GenericHandler() {
        @Override
        public void onSuccess() {
            showDialogMessage("Success!",username+" has been confirmed!", true);
            exit();
        }

        @Override
        public void onFailure(Exception exception) {
            showDialogMessage("Confirmation failed", AppHelper.formatException(exception), false);
        }
    };

    SignUpHandler signUpHandler = new SignUpHandler() {
        @Override
        public void onSuccess(CognitoUser user, boolean signUpConfirmationState,
                              CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
            // Check signUpConfirmationState to see if the user is already confirmed
            Boolean regState = signUpConfirmationState;
            if (signUpConfirmationState) {
                // User is already confirmed
                // Return to login screen
            }
            else {
                // User is not confirmed
                // Proceed to confirmation
            }
        }

        @Override
        public void onFailure(Exception exception) {
            showDialogMessage("Sign up failed",AppHelper.formatException(exception),false);
        }


    };
    private void showDialogMessage(String title, String body, final boolean exit) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title).setMessage(body).setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    userDialog.dismiss();
                    if(exit) {
                        //exit(username);
                    }
                } catch (Exception e) {
                    if(exit) {
                        //exit(username);
                    }
                }
            }
        });
        userDialog = builder.create();
        userDialog.show();
    }

    private void exit() {
        NavUtils.navigateUpFromSameTask(this);
    }
}
