package com.edward.cs48.houserules.HouseRulesUser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;

/**
 * Created by Edward on 2/18/17.
 */

public class houseRulesUser implements Serializable {
    private String fullName;
    private String screenName;
    private String email;
    private Boolean isHost;

    // Number of events a user is host of
    private int hostEventNum; // Host specific feature
    // Number of events a user is invited to attend
    private int attendEventNum; // Attendee specific feature

    // List of events of which the user is a host of
    private Map<String,houseRulesEvent> hostEventMap = new HashMap<String, houseRulesEvent>();
    // List of events of which the user is an attendee of
    private Map<String,houseRulesEvent> attendEventMap = new HashMap<String, houseRulesEvent>();
    //List of events of which the user is invited to
    private Map<String,houseRulesEvent> invitedEventMap = new HashMap<String, houseRulesEvent>();

    public houseRulesUser() {
        this.fullName="";
        this.screenName = "";
        this.email = "";
        this.hostEventNum = 0; // Host specific feature
        this.attendEventNum = 0; // Attendee specific feature
        this.setAttendEventMap(new HashMap<String, houseRulesEvent>());
        this.setInvitedEventMap(new HashMap<String, houseRulesEvent>());
        this.setHostEventMap(new HashMap<String, houseRulesEvent>());

    }

    public houseRulesUser(String fullName, String screenName, String email) {
        this.fullName=fullName;
        this.screenName = screenName;
        this.email = email;
        this.hostEventNum = 0; // Host specific feature
        this.attendEventNum = 0; // Attendee specific feature
        this.attendEventMap = new HashMap<String, houseRulesEvent>();
        this.invitedEventMap = new HashMap<String, houseRulesEvent>();
        this.hostEventMap = new HashMap<String, houseRulesEvent>();
    }

    public String getFullName(){return this.fullName;}
    public String getScreenName() { return this.screenName; }
    public String getEmail() { return this.email; }
    public int getHostEventNum() { return this.hostEventNum; } // Host specific feature
    public int getAttendEventNum() { return this.attendEventNum; } // Attendee specific feature

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void addHostEvent(houseRulesEvent newHostEvent) { // Host specific feature
        hostEventMap.put(newHostEvent.getName(), newHostEvent);
        hostEventNum++;
    }

    public void addAttendEvent(houseRulesEvent newAttendEvent) {
        attendEventMap.put(newAttendEvent.getName(),newAttendEvent);
        attendEventNum++;
    }

    public void addInviteEvent(houseRulesEvent newInviteEvent) {
        invitedEventMap.put(newInviteEvent.getName(),newInviteEvent);
    }

    public void removeAttendEvent (houseRulesEvent newAttendEvent){
        attendEventMap.remove(newAttendEvent.getName());
        attendEventNum--;
    }

    public void removeHostEvent (houseRulesEvent newHostEvent){
        hostEventMap.remove(newHostEvent.getName());
        hostEventNum--;
    }
    public void removeInviteEvent (houseRulesEvent newInviteEvent){
        invitedEventMap.remove(newInviteEvent.getName());
    }


    public Map<String, houseRulesEvent> getHostEventMap() {
        return hostEventMap;
    }

    public Map<String, houseRulesEvent> getAttendEventMap() {
        return attendEventMap;
    }

    private boolean attendingEvent(houseRulesEvent event1){
        return this.attendEventMap.containsKey(event1.getName());
    }

    public Map<String, houseRulesEvent> getInvitedEventMap() {
        return invitedEventMap;
    }

    public Boolean getHost() {
        return isHost;
    }

    public void setAttendEventMap(Map<String, houseRulesEvent> attendEventMap) {
        this.attendEventMap = attendEventMap;
    }

    public void setAttendEventNum(int attendEventNum) {
        this.attendEventNum = attendEventNum;
    }

    public void setHost(Boolean host) {
        isHost = host;
    }

    public void setHostEventMap(Map<String, houseRulesEvent> hostEventMap) {
        this.hostEventMap = hostEventMap;
    }

    public void setHostEventNum(int hostEventNum) {
        this.hostEventNum = hostEventNum;
    }

    public void setInvitedEventMap(Map<String, houseRulesEvent> invitedEventMap) {
        this.invitedEventMap = invitedEventMap;
    }



    private void readObject(ObjectInputStream aInputStream
    ) throws ClassNotFoundException, IOException {
        //always perform the default de-serialization first
        aInputStream.defaultReadObject();
    }


    private void writeObject(
            ObjectOutputStream aOutputStream
    ) throws IOException {
        //perform the default serialization for all non-transient, non-static fields
        aOutputStream.defaultWriteObject();
    }


}
