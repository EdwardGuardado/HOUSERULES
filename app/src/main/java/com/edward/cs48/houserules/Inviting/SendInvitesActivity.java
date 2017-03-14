package com.edward.cs48.houserules.Inviting;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;
import com.edward.cs48.houserules.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.edward.cs48.houserules.HouseRulesUser.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rob on 3/13/2017.
 */

public class SendInvitesActivity extends ListActivity {
    private EditText searchName;
    private Button searchButton;
    String userEmail;
    final List<houseRulesUser> users = new ArrayList<houseRulesUser>();
    boolean validUser = false; // by default false unless function finds user's email in the Firebase database
    private static final String TAG = "SendInvitesActivity";
    private ArrayList<String> eventNames;

    private FirebaseAuth auth;
    private DatabaseReference userReference;
    private houseRulesUser ourUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference();

        auth = FirebaseAuth.getInstance();
        userReference = database.getReference("user/userdatabase/"+ auth.getCurrentUser().getUid() + "/");

        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                ourUser = dataSnapshot.getValue(houseRulesUser.class);
                setup();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("Failed to load user", "loadPost:onCancelled", databaseError.toException());
            }
        };
        userReference.addValueEventListener(userListener);

        databaseReference.child("user").child("userdatabase").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                // Populates ArrayList users with all the user objects in Firebase Database
                for (DataSnapshot child : children) {
                    houseRulesUser value = child.getValue(houseRulesUser.class);
                    users.add(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("DATABASE ERROR");

            }
        });
        searchName = (EditText) findViewById(R.id.name_field);
        searchButton = (Button) findViewById(R.id.search_btn);
        // When submit button is clicked, function checks to see if any user in the Firebase database
        // has the email being searched for
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = (LayoutInflater.from(SendInvitesActivity.this)).inflate(R.layout.activity_send_invites, null);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SendInvitesActivity.this);
                alertBuilder.setView(view);
                alertBuilder.setCancelable(true)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userEmail = searchName.getText().toString();
                                for (int i = 0; i < users.size(); i++) {
                                    if (users.get(i).getEmail() == userEmail) {
                                        validUser = true; // Means there exists a user in the database with specified email
                                        Toast.makeText(getApplicationContext(), "Invite has been sent!", Toast.LENGTH_LONG).show();
                                    }
                                }
                                Toast.makeText(getApplicationContext(), "User not found...", Toast.LENGTH_LONG).show();
                            }
                        });
                Dialog dialog = alertBuilder.create();
                dialog.show();
            }
        });
    }

    public void setup(){
        eventNames = new ArrayList<String>(ourUser.getAttendEventMap().keySet());
        String[] newEvents = eventNames.toArray(new String[ourUser.getAttendEventMap().size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,newEvents);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    }

}
