package com.edward.cs48.houserules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button btnCreateEvent, btnMyEvents, btnMyInvites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnCreateEvent = (Button) findViewById(R.id.button_create_event);
        btnMyEvents = (Button) findViewById(R.id.button_my_events);
        btnMyInvites = (Button) findViewById(R.id.button_my_invites);

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.edward.cs48.houserules.MainActivity.this, CreateEventActivity.class);
                startActivity(intent);
            }
        });

        btnMyEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.edward.cs48.houserules.MainActivity.this, MyEventsActivity.class);
                startActivity(intent);
            }
        });

        btnMyInvites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.edward.cs48.houserules.MainActivity.this, MyInvitesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
