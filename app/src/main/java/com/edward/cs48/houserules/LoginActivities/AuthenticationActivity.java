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

import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;
import com.firebase.ui.auth.*;
import com.edward.cs48.houserules.R;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.edward.cs48.houserules.MainActivity;
import com.edward.cs48.houserules.HouseRulesUser.*;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;


public class AuthenticationActivity extends AppCompatActivity {
    private static final String TAG = "AuthenticaitonActivity";
    private static final String FIREBASE_TOS_URL = "https://firebase.google.com/terms/";
    private static final int RC_SIGN_IN = 100;
    private ImageView imageView4;
    private FirebaseAuth auth;
    private houseRulesUser user = new houseRulesUser();
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private houseRulesEvent testEvent = new houseRulesEvent();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        myRef = userDatabase.getReference("user");
        testEvent.setAddress("5613 Aspen Ct.");
        testEvent.setName("Fuck Gio");
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
                        .setLogo(R.mipmap.houseruleslogo)
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
                user.setEmail(auth.getCurrentUser().getEmail().toString());
                user.setFullName(auth.getCurrentUser().getDisplayName().toString());
                user.setScreenName(auth.getCurrentUser().getDisplayName().toString());
                user.setAttendEventList(new ArrayList<houseRulesEvent>());
                user.setHostEventList(new ArrayList<houseRulesEvent>());
                user.setInvitedEventList(new ArrayList<houseRulesEvent>());
                user.getHostEventList().add(testEvent);
                myRef.child("user").child(auth.getCurrentUser().getProviderId()).setValue(user);
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
