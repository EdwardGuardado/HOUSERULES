package com.edward.cs48.houserules.EventActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;
import com.edward.cs48.houserules.HouseRulesUser.houseRulesUser;
import com.edward.cs48.houserules.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

import java.util.ArrayList;


public class MyEventsActivity extends AppCompatActivity { //AppCompatActivity {
    /*
    private static final String TAG = "MyEventsActivity";

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView eventName;
        public TextView eventHostName;
        public TextView eventAddress;

        public EventViewHolder(View v) {
            super(v);
            eventName = (TextView) itemView.findViewById(R.id.my_event_name);
            eventHostName = (TextView) itemView.findViewById(R.id.my_event_host_name);
            eventAddress = (TextView) itemView.findViewById(R.id.my_event_address);
        }
    }

    private RecyclerView mEventRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseAuth auth;
    private FirebaseDatabase userDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference userReference;
    private houseRulesUser ourUser;
    private FirebaseRecyclerAdapter<houseRulesEvent, EventViewHolder> mFirebaseAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mEventRecyclerView = (RecyclerView) findViewById(R.id.myEventsRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(true);
        auth = FirebaseAuth.getInstance();
        //userReference = userDatabase.getReference("user/userdatabase/"+ auth.getCurrentUser().getUid() + "/");
        userReference = userDatabase.getReference();
        mFirebaseAdapter = new FirebaseRecyclerAdapter<houseRulesEvent, EventViewHolder>(
                houseRulesEvent.class,
                R.layout.activity_my_events_element,
                EventViewHolder.class,
                userReference.child("user/userdatabase/" + auth.getCurrentUser().getUid() + "/hostEventMap")) {
            protected void populateViewHolder(EventViewHolder viewHolder, houseRulesEvent model, int position) {
                viewHolder.eventName.setText(model.getName());
                viewHolder.eventHostName.setText(model.getName());
                viewHolder.eventAddress.setText(model.getAddress());
            }
        };

        mFirebaseAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int eventCount = mFirebaseAdapter.getItemCount();
                int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastVisiblePosition == -1 || (positionStart >= (eventCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    mEventRecyclerView.scrollToPosition(positionStart);
                }
            }
        });
        mEventRecyclerView.setLayoutManager(mLinearLayoutManager);
        mEventRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void onPause() { super.onPause(); }

    private void removeEvent(houseRulesEvent removed) {
        String name = "publicEvents/" + auth.getCurrentUser().getUid() + removed.hashCode() + "/";
    }
<<<<<<< HEAD

    */
}

