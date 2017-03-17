package com.edward.cs48.houserules.EventActivities.MyEvents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edward.cs48.houserules.LoginActivities.AuthenticationActivity;
import com.edward.cs48.houserules.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyEventDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MyEventDetailsActivity";
    private TextView mEventName, mEventHost, mEventLocation, mEventDate, mEventTime;
    private Button btnEditEvent, btnSendInvites;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private String eventName;


    private FirebaseAuth auth;
    private FirebaseUser firebaseUser;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("User Info");




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_details);
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
            return;
        }
        mEventName = (TextView) findViewById(R.id.my_event_details_name);
        mEventHost = (TextView) findViewById(R.id.my_event_details_host);
        mEventLocation = (TextView) findViewById(R.id.my_event_details_location);
        mEventDate = (TextView) findViewById(R.id.my_event_details_date);
        mEventTime = (TextView) findViewById(R.id.my_event_details_time);
        btnEditEvent = (Button) findViewById(R.id.my_event_details_edit_event);
        btnSendInvites = (Button) findViewById(R.id.my_event_details_send_invites);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            eventName = b.getString("My_Event");
        }

        btnEditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyEventDetailsActivity.this, com.edward.cs48.houserules.EventActivities.CreateEditEvents.EditEventActivity.class);
                Bundle this_event_bundle = new Bundle();
                this_event_bundle.putString("eventShared", eventName);
                intent.putExtras(this_event_bundle);
                startActivity(intent);
            }
        });

        btnSendInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyEventDetailsActivity.this, com.edward.cs48.houserules.MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}

