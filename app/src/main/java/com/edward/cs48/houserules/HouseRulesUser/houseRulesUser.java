package com.edward.cs48.houserules.HouseRulesUser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
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
    private ArrayList<houseRulesEvent> hostEventList = new ArrayList<houseRulesEvent>();
    // List of events of which the user is an attendee of
    private ArrayList<houseRulesEvent> attendEventList = new ArrayList<houseRulesEvent>();
    //List of events of which the user is invited to
    private ArrayList<houseRulesEvent> invitedEventList = new ArrayList<houseRulesEvent>();

    public houseRulesUser() {
        this.fullName="";
        this.screenName = "";
        this.email = "";
        this.hostEventNum = 0; // Host specific feature
        this.attendEventNum = 0; // Attendee specific feature
    }

    public houseRulesUser(String fullName, String screenName, String email) {
        this.fullName=fullName;
        this.screenName = screenName;
        this.email = email;
        this.hostEventNum = 0; // Host specific feature
        this.attendEventNum = 0; // Attendee specific feature
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
        hostEventList.add(newHostEvent);
        hostEventNum++;
    }

    public void addAttendEvent(houseRulesEvent newAttendEvent) {
        attendEventList.add(newAttendEvent);
        attendEventNum++;
    }

    public void addInviteEvent(houseRulesEvent newInviteEvent) {
        invitedEventList.add(newInviteEvent);
    }

    public void removeAttendEvent (houseRulesEvent newAttendEvent){
        attendEventList.remove(newAttendEvent);
        attendEventNum--;
    }

    public void removeHostEvent (houseRulesEvent newHostEvent){
        hostEventList.remove(newHostEvent);
        hostEventNum--;
    }
    public void removeInviteEvent (houseRulesEvent newInviteEvent){
        invitedEventList.remove(newInviteEvent);
    }


    public ArrayList<houseRulesEvent> getHostEventList(){
        return this.hostEventList;
    }

    public ArrayList<houseRulesEvent> getAttendEventList(){
        return this.attendEventList;
    }

    private boolean attendingEvent(houseRulesEvent event1){
        return this.attendEventList.contains(event1);
    }

    public ArrayList<houseRulesEvent> getInvitedEventList() {
        return invitedEventList;
    }

    public Boolean getHost() {
        return isHost;
    }

    public void setAttendEventList(ArrayList<houseRulesEvent> attendEventList) {
        this.attendEventList = attendEventList;
    }

    public void setAttendEventNum(int attendEventNum) {
        this.attendEventNum = attendEventNum;
    }

    public void setHost(Boolean host) {
        isHost = host;
    }

    public void setHostEventList(ArrayList<houseRulesEvent> hostEventList) {
        this.hostEventList = hostEventList;
    }

    public void setHostEventNum(int hostEventNum) {
        this.hostEventNum = hostEventNum;
    }

    public void setInvitedEventList(ArrayList<houseRulesEvent> invitedEventList) {
        this.invitedEventList = invitedEventList;
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
