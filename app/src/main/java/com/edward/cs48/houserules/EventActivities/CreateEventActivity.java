package com.edward.cs48.houserules.EventActivities;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;
import com.edward.cs48.houserules.HouseRulesUser.houseRulesUser;
import com.edward.cs48.houserules.MainActivity;
import com.edward.cs48.houserules.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;



public class CreateEventActivity extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    Calendar dateTime = Calendar.getInstance();
    private houseRulesEvent newEvent = new houseRulesEvent();
    private EditText eventName;
    private EditText eventAddress;
    private EditText eventDate;
    private EditText eventTime;
    private EditText houseRules;
    private CheckBox makePublic;
    private Button createEventBtn;
    private Map<String, Object> childUpdates = new HashMap<>();

    private FirebaseAuth auth;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private DatabaseReference userReference;
    private houseRulesUser ourUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        userReference = userDatabase.getReference("user/userdatabase/"+ auth.getCurrentUser().getUid() + "/");

        setContentView(R.layout.activity_create_event);


        eventName = (EditText) findViewById(R.id.event_name);
        eventAddress = (EditText) findViewById(R.id.event_address);
        eventDate = (EditText) findViewById(R.id.event_date);
        eventTime = (EditText) findViewById(R.id.event_time);
        houseRules = ((EditText) findViewById(R.id.event_rules));
        makePublic = (CheckBox) findViewById(R.id.event_privacy);
        createEventBtn = (Button) findViewById(R.id.submit_btn);

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                 ourUser = dataSnapshot.getValue(houseRulesUser.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to load user", "loadPost:onCancelled", databaseError.toException());
            }
        };
        userReference.addValueEventListener(userListener);

        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            makeEvent();
            ourUser.addHostEvent(newEvent);
            userReference.setValue(ourUser);
            if (newEvent.getPrivacy()){
                myRef = userDatabase.getReference("publicEvents/user/"+ auth.getCurrentUser().getUid()+ "/" + newEvent.getName());
                myRef.setValue(newEvent);
            }
            startActivity(new Intent(com.edward.cs48.houserules.EventActivities.CreateEventActivity.this,MainActivity.class));
            }
        });

        makePublic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.isChecked()){
              newEvent.setPrivacy(true);
            }
            else{
                newEvent.setPrivacy(false);
            }
            }
        });

    }

    private void makeEvent(){
        if(validateForm()){
            newEvent.setName(eventName.getText().toString());
            newEvent.setAddress(eventName.getText().toString());
            newEvent.setDate(eventDate.getText().toString());
            newEvent.setTime(eventTime.getText().toString());
            newEvent.setHouseRules(houseRules.getText().toString());
            newEvent.setHostName(auth.getCurrentUser().getDisplayName());
        }

        else{
            return;
        }

    }

    private boolean validateForm() {
        boolean valid = true;

        String name = eventName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            eventName.setError("Required.");
            valid = false;
        } else {
            eventName.setError(null);
        }


        String addy = eventAddress.getText().toString();
        if (TextUtils.isEmpty(addy)) {
            eventAddress.setError("Required.");
            valid = false;
        } else {
            eventAddress.setError(null);
        }

        return valid;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
