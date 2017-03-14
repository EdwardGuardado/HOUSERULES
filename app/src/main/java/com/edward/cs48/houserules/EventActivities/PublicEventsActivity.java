package com.edward.cs48.houserules.EventActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;
import com.edward.cs48.houserules.HouseRulesUser.houseRulesUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;


public class PublicEventsActivity extends ListActivity{ //AppCompatActivity {

    private static final String TAG = "PublicEventsActivity";
    private ArrayList<houseRulesEvent> events = new ArrayList<houseRulesEvent>();
    private ArrayList<String> eventNames = new ArrayList<String>();


    private FirebaseAuth auth;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference eventsRef;

    private String hostId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        eventsRef = userDatabase.getReference("publicEvents");
        setup();
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
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
    protected void onResume(){
        super.onResume();
    }

    public void setup(){

        eventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    houseRulesEvent tmpEvent = snapshot.getValue(houseRulesEvent.class);
                    if (!tmpEvent.getHostID().equals(auth.getCurrentUser().getUid())){
                        eventNames.add(tmpEvent.getName());
                        events.add(tmpEvent);
                    }
                }
                String[] newEvents = eventNames.toArray(new String[events.size()]);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(com.edward.cs48.houserules.EventActivities.PublicEventsActivity.this, android.R.layout.simple_list_item_1,newEvents);
                setListAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }


}
