package com.edward.cs48.houserules.EventActivities.CreateEditEvents;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.edward.cs48.houserules.EventActivities.MyEventsActivity;
import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;
import com.edward.cs48.houserules.HouseRulesUser.houseRulesUser;
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

import java.util.Calendar;
import java.util.Map;

public class EditEventActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    Calendar dateTime = Calendar.getInstance();
    private houseRulesEvent newEvent;
    private EditText eventName;
    private EditText eventDate;
    private EditText eventTime;
    private EditText houseRules;
    private CheckBox makePublic;
    private Button changeEvent, pickAPlaceButton, removeBtn;
    private int PLACE_PICKER_REQUEST = 1;
    private String originalEventName;



    private FirebaseAuth auth;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private DatabaseReference userReference;
    private houseRulesUser ourUser;
    private Map<String,houseRulesEvent> userEvents;
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
        eventName = (EditText) findViewById(R.id.ed_event_name);
        eventDate = (EditText) findViewById(R.id.ed_event_date);
        eventTime = (EditText) findViewById(R.id.ed_event_time);
        pickAPlaceButton = (Button) findViewById(R.id.ed_place_picker_create);
        houseRules = ((EditText) findViewById(R.id.ed_event_rules));
        makePublic = (CheckBox) findViewById(R.id.ed_event_privacy);

        changeEvent = (Button) findViewById(R.id.save_changes);

        removeBtn = (Button) findViewById(R.id.remove_event);




        originalEventName = getIntent().getExtras().getString("eventShared");

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ourUser = dataSnapshot.getValue(houseRulesUser.class);
                userEvents = ourUser.getHostEventMap();
                newEvent = userEvents.get((String)originalEventName);
                String namerr = newEvent.getName();
                System.out.println(namerr);
                eventName.setText(namerr);

                eventDate.setText(newEvent.getDate());


                eventTime.setText(newEvent.getTime());

                houseRules.setText(newEvent.getHouseRules());

                if (newEvent.getPrivacy()){
                    makePublic.setChecked(true);
                }

                pickAPlaceButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            startActivityForResult(builder.build(EditEventActivity.this), PLACE_PICKER_REQUEST);
                        } catch (GooglePlayServicesRepairableException e) {
                            e.printStackTrace();
                        } catch (GooglePlayServicesNotAvailableException e) {
                            e.printStackTrace();
                        }
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

                changeEvent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newEvent.setName(originalEventName);
                        removeEvent(newEvent);
                        makeEvent();
                        ourUser.addHostEvent(newEvent);
                        userReference.setValue(ourUser);
                        if (newEvent.getPrivacy()){
                            myRef = userDatabase.getReference("publicEvents/"+auth.getCurrentUser().getUid()+newEvent.hashCode()+"/");
                            myRef.setValue(newEvent);
                        }
                        startActivity(new Intent(EditEventActivity.this,MyEventsActivity.class));
                    }
                });

                removeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        newEvent.setName(originalEventName);
                        removeEvent(newEvent);
                        userReference.setValue(ourUser);
                        startActivity(new Intent(EditEventActivity.this,MyEventsActivity.class));
                    }
                });




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to load user", "loadPost:onCancelled", databaseError.toException());
            }
        };
        userReference.addValueEventListener(userListener);

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


    private void removeEvent(final houseRulesEvent removed) {
        if (removed.getPrivacy()==true) {
            String name = auth.getCurrentUser().getUid() + removed.hashCode();
            DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference("publicEvents");
            eventsRef.child(name).removeValue();
        }
        ourUser.removeHostEvent(removed);
    }
}
