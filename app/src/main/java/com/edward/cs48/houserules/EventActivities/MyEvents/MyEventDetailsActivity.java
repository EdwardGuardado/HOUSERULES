package com.edward.cs48.houserules.EventActivities.MyEvents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;
import com.edward.cs48.houserules.HouseRulesUser.houseRulesUser;
import com.edward.cs48.houserules.LoginActivities.AuthenticationActivity;
import com.edward.cs48.houserules.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MyEventDetailsActivity extends AppCompatActivity {

    private static final String TAG = "MyEventDetailsActivity";
    private TextView mEventName, mEventHost, mEventLocation, mEventDate, mEventTime, mEventRules;
    private Button btnEditEvent, btnSendInvites;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth auth;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userReference;
    private houseRulesUser ourUser;
    private houseRulesEvent ourEvent;
    private String eventName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_details);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        mFirebaseUser = auth.getCurrentUser();
        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
            return;
        }

        userReference = userDatabase.getReference("user/userdatabase/"+ auth.getCurrentUser().getUid() + "/");
        mEventName = (TextView) findViewById(R.id.my_event_details_name);
        mEventHost = (TextView) findViewById(R.id.my_event_details_host);
        mEventLocation = (TextView) findViewById(R.id.my_event_details_location);
        mEventDate = (TextView) findViewById(R.id.my_event_details_date);
        mEventTime = (TextView) findViewById(R.id.my_event_details_time);
        mEventRules = (TextView) findViewById(R.id.my_event_details_rules);
        btnEditEvent = (Button) findViewById(R.id.my_event_details_edit_event);
        btnSendInvites = (Button) findViewById(R.id.my_event_details_send_invites);
        Bundle b = getIntent().getExtras();
        if (b != null) {
            eventName = b.getString("My_Event");
        }

        setUpValues();

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
                Toast.makeText(MyEventDetailsActivity.this, "Feature Coming Soon...", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyEventDetailsActivity.this, com.edward.cs48.houserules.MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void setUpValues(){
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ourUser = dataSnapshot.getValue(houseRulesUser.class);
                ourEvent = ourUser.getHostEventMap().get(eventName);
                setUpTextValues();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    protected void setUpTextValues() {
        mEventName.setText(ourEvent.getName());
        mEventHost.setText(ourEvent.getHostName());
        mEventLocation.setText(ourEvent.getAddress());
        mEventDate.setText(ourEvent.getDate());
        mEventTime.setText(ourEvent.getTime());
        mEventRules.setText(ourEvent.getHouseRules());
    }
}

