package com.edward.cs48.houserules;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.edward.cs48.houserules.EventActivities.AttendEventsActivity;
import com.edward.cs48.houserules.EventActivities.CreateEditEvents.CreateEventActivity;
import com.edward.cs48.houserules.EventActivities.MyEventsActivity;
import com.edward.cs48.houserules.EventActivities.PublicEventsMapActivity;
import com.edward.cs48.houserules.Inviting.MyInvitesActivity;
import com.edward.cs48.houserules.LoginActivities.AuthenticationActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnCreateEvent, btnMyEvents, btnMyInvites, btnPublicEvents, btnAttendingEvents;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;


    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User Info");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
            return;
        }
        btnCreateEvent = (Button) findViewById(R.id.button_create_event);
        btnMyEvents = (Button) findViewById(R.id.button_my_events);
        btnPublicEvents = (Button) findViewById(R.id.button_public_events);


        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.edward.cs48.houserules.MainActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });

        btnMyEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.edward.cs48.houserules.MainActivity.this, MyEventsActivity.class);
                startActivity(intent);
            }
        });


        btnPublicEvents.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(com.edward.cs48.houserules.MainActivity.this, PublicEventsMapActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out:
                signOut();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return true;
        }
    }




    public void signOut(){
                AuthUI.getInstance()
                        .signOut(MainActivity.this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                // user is now signed out
                                startActivity(new Intent(MainActivity.this, AuthenticationActivity.class));
                                finish();
                            }
                        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
