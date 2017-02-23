package com.edward.cs48.houserules.LoginActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.widget.ImageView;

import com.firebase.ui.auth.*;
import com.edward.cs48.houserules.R;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.edward.cs48.houserules.MainActivity;
import com.edward.cs48.houserules.HouseRulesUser.*;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


public class AuthenticationActivity extends AppCompatActivity {
    private static final String TAG = "AuthenticaitonActivity";
    private static final String FIREBASE_TOS_URL = "https://firebase.google.com/terms/";
    private static final int RC_SIGN_IN = 100;
    private static houseRulesUser user;
    private ImageView imageView4;

    private FirebaseAuth auth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
        }
        setContentView(R.layout.activity_authentication);

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setLogo(R.drawable.houseruleslogo)
                        .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .build(),
                RC_SIGN_IN);
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                user.setEmail(auth.getCurrentUser().getEmail());
                user.setFullName(auth.getCurrentUser().getDisplayName());
                startActivity(new Intent(this, MainActivity.class));
                finish();
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showSnackbar(R.string.unknown_error);
                    return;
                }
            }

            showSnackbar(R.string.unknown_sign_in_response);
        }
    }

        @MainThread
        private void showSnackbar(@StringRes int errorMessageRes) {
            Snackbar.make(imageView4, errorMessageRes, Snackbar.LENGTH_LONG).show();
        }


    }
