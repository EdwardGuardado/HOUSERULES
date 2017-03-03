package com.edward.cs48.houserules.EventActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.Iterator;

import static com.edward.cs48.houserules.MainActivity.user;

public class MyEventsActivity extends ListActivity{ //AppCompatActivity {

    private static final String TAG = "MyEventsActivity";
    private ArrayList<String> events = new ArrayList<String>();
    private ArrayList<houseRulesEvent> eventsHostedUser = user.getHostEventList();
    private ArrayList<houseRulesEvent> eventsAttendUser = user.getAttendEventList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setUp();
        String[] newEvents = events.toArray(new String[events.size()]);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,newEvents);
        setListAdapter(adapter);
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

    private void setUp(){
        for ( houseRulesEvent event : eventsHostedUser){
            events.add(event.getName());
        }
        for (houseRulesEvent event : eventsAttendUser){
            events.add(event.getName());
        }
    }
}
