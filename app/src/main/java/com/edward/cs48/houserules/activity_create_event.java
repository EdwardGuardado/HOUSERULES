package com.edward.cs48.houserules;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.util.Calendar;

public class activity_create_event extends AppCompatActivity {

    Calendar dateTime = Calendar.getInstance();
    private EditText eventName;
    private EditText eventAddress;
    private EditText eventDate;
    private EditText eventTime;
    private EditText eventRules;
    private CheckBox makePublic;
    private Button createEventBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        eventName = (EditText) findViewById(R.id.event_name);
        eventAddress = (EditText) findViewById(R.id.event_address);
        eventDate = (EditText) findViewById(R.id.event_date);
        eventTime = (EditText) findViewById(R.id.event_time);
        eventRules = (EditText) findViewById(R.id.event_rules);
        makePublic = (CheckBox) findViewById(R.id.event_privacy);
        createEventBtn = (Button) findViewById(R.id.submit_btn);




    }
}
