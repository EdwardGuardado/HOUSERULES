package com.edward.cs48.houserules.EventActivities.PublicEvents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;
import com.edward.cs48.houserules.HouseRulesUser.houseRulesUser;
import com.edward.cs48.houserules.LoginActivities.AuthenticationActivity;
import com.edward.cs48.houserules.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PublicEventDetails extends AppCompatActivity {

    private static final String TAG = "MyEventDetailsActivity";
    private TextView mEventName, mEventHost, mEventLocation, mEventDate, mEventTime, houseRules;
    private Button btnAdd;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private String eventName;
    private houseRulesEvent eventFor;
    private houseRulesUser  ourUser;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference eventsRef, userRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_event_details);

        eventName = getIntent().getExtras().getString("eventName");
        // Initialize Firebase Auth
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            // Not signed in, launch the Sign In activity
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
            return;
        }

        eventsRef = userDatabase.getReference("publicEvents/"+eventName+"/");
        userRef = userDatabase.getReference("user/userdatabase/"+ mFirebaseAuth.getCurrentUser().getUid() + "/");

        mEventName = (TextView) findViewById(R.id.pub_event_details_name);
        mEventHost = (TextView) findViewById(R.id.pub_event_details_host);
        mEventLocation = (TextView) findViewById(R.id.pub_event_details_location);
        mEventDate = (TextView) findViewById(R.id.pub_event_details_date);
        mEventTime = (TextView) findViewById(R.id.pub_event_details_time);
        houseRules = (TextView) findViewById(R.id.pub_event_details_houserules);
        btnAdd = (Button) findViewById(R.id.add_event);
        setup();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void setup(){
        //Reading Nodes of public event dataBase
        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                eventFor = dataSnapshot.getValue(houseRulesEvent.class);
                mEventHost.setText(eventFor.getHostName());
                mEventLocation.setText(eventFor.getAddress());
                mEventName.setText(eventFor.getName());
                mEventDate.setText(eventFor.getDate());
                mEventTime.setText(eventFor.getTime());
                houseRules.setText(eventFor.getHouseRules());

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ourUser = dataSnapshot.getValue(houseRulesUser.class);
                        setUpClick();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    private void setUpClick(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ourUser.addAttendEvent(eventFor);
                userRef.setValue(ourUser);
                Intent intent = new Intent(PublicEventDetails.this, com.edward.cs48.houserules.EventActivities.AttendEventsActivity.class);
                startActivity(intent);
            }
        });
    }

}

