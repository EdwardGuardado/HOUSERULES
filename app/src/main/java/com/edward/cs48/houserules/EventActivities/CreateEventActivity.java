package com.edward.cs48.houserules.EventActivities;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;
import com.edward.cs48.houserules.HouseRulesUser.houseRulesUser;
import com.edward.cs48.houserules.MainActivity;
import com.edward.cs48.houserules.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;


import java.util.Calendar;


public class CreateEventActivity extends AppCompatActivity  implements OnConnectionFailedListener {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    Calendar dateTime = Calendar.getInstance();
    private houseRulesEvent newEvent = new houseRulesEvent();
    private EditText eventName;
    private EditText eventDate;
    private EditText eventTime;
    private EditText houseRules;
    private CheckBox makePublic;
    private Button createEventBtn, pickAPlaceButton;
    private int PLACE_PICKER_REQUEST = 1;
    private Place geoLocation = null;



    private FirebaseAuth auth;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private DatabaseReference userReference;
    private houseRulesUser ourUser;
    private GoogleApiClient mGoogleApiClient;
    private PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        auth = FirebaseAuth.getInstance();
        userReference = userDatabase.getReference("user/userdatabase/"+ auth.getCurrentUser().getUid() + "/");

        setContentView(R.layout.activity_create_event);



        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();



        eventName = (EditText) findViewById(R.id.event_name);
        eventDate = (EditText) findViewById(R.id.event_date);
        pickAPlaceButton = (Button) findViewById(R.id.place_picker_create);
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


        pickAPlaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        startActivityForResult(builder.build(CreateEventActivity.this), PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException e) {
                        e.printStackTrace();
                    } catch (GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
            }

        });

        createEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            makeEvent();
            ourUser.addHostEvent(newEvent);
            userReference.setValue(ourUser);
            if (newEvent.getPrivacy()){
                myRef = userDatabase.getReference("publicEvents/"+auth.getCurrentUser().getUid()+newEvent.hashCode()+"/");
                myRef.setValue(newEvent);
            }
            startActivity(new Intent(CreateEventActivity.this,MainActivity.class));
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
            newEvent.setDate(eventDate.getText().toString());
            newEvent.setTime(eventTime.getText().toString());
            newEvent.setHouseRules(houseRules.getText().toString());
            newEvent.setHostName(auth.getCurrentUser().getDisplayName());
            newEvent.setHostID(auth.getCurrentUser().getUid());
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


        return valid;
    }


    private void placePickerSetup(){

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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                newEvent.setAddress(place.getAddress().toString());
                newEvent.setLat(place.getLatLng().latitude);
                newEvent.setLon(place.getLatLng().longitude);
                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }



}
