package com.edward.cs48.houserules.EventActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;
import com.edward.cs48.houserules.LoginActivities.AuthenticationActivity;
import com.edward.cs48.houserules.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;

public class AttendEventsActivity extends AppCompatActivity {

    private static final String TAG = "MyEventsActivity";

    public static class EventViewHolder extends RecyclerView.ViewHolder {
        public TextView my_event_name;
        public TextView my_event_host_name;
        public TextView my_event_address;
        View mView;

        public EventViewHolder(View v) {
            super(v);
            my_event_name = (TextView) itemView.findViewById(R.id.my_event_name);
            my_event_host_name = (TextView) itemView.findViewById(R.id.my_event_host_name);
            my_event_address = (TextView) itemView.findViewById(R.id.my_event_address);
            mView = v;
        }
    }

    private String mUserName;
    private RecyclerView mEventRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mFirebaseDatabaseReference;
    private FirebaseRecyclerAdapter<houseRulesEvent, EventViewHolder> mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attend_events);

        mUserName = "ANONYMOUS";

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        if (mFirebaseUser == null) {
            startActivity(new Intent(this, AuthenticationActivity.class));
            finish();
            return;
        } else {
            mUserName = mFirebaseUser.getDisplayName();
        }

        mEventRecyclerView = (RecyclerView) findViewById(R.id.myEventsRecyclerView);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setStackFromEnd(false);

        mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
        //userReference = userDatabase.getReference("user/userdatabase/"+ auth.getCurrentUser().getUid() + "/");
        mFirebaseAdapter = new FirebaseRecyclerAdapter<houseRulesEvent, EventViewHolder>(
                houseRulesEvent.class,
                R.layout.activity_my_events_element,
                EventViewHolder.class,
                mFirebaseDatabaseReference.child("user/userdatabase/" + mFirebaseAuth.getCurrentUser().getUid() + "/attendEventMap")) {
            protected void populateViewHolder(final EventViewHolder viewHolder, houseRulesEvent model, int position) {
                viewHolder.my_event_name.setText(model.getName());
                viewHolder.my_event_host_name.setText(model.getHostName());
                viewHolder.my_event_address.setText(model.getAddress());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(AttendEventsActivity.this, viewHolder.my_event_name.getText().toString(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AttendEventsActivity.this, AttendEventsActivity.class);
                        Bundle my_event_bundle = new Bundle();
                        my_event_bundle.putString("My_Event", viewHolder.my_event_name.getText().toString());
                        intent.putExtras(my_event_bundle);
                        startActivity(intent);
                    }
                });
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

    @Override
    protected void onPause() {
        super.onPause();
    }
}