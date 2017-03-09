package com.edward.cs48.houserules.HouseRulesEvent;

import android.widget.EditText;

import com.edward.cs48.houserules.HouseRulesUser.houseRulesUser;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Edward on 2/18/17.
 */



public class houseRulesEvent implements Serializable {
    private String name;
    private String address;
    private String date;
    private String time;
    private String houseRules;
    private boolean Public;
    private String HostName;


    public houseRulesEvent() {
        this.name = "";
        this.address = "";
        this.date = "";
        this.time = "";
        this.Public = false;
    }

    public houseRulesEvent(String name, String address, String date, String time, String houseRules, boolean privacy) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.time = time;
        this.Public = privacy;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setAddress(String newAddress) {
        this.address = newAddress;
    }

    public void setDate(String newDate) {
        this.date = newDate;
    }

    public void setTime(String newTime) {
        this.time = newTime;
    }

    public void setHouseRules(String newHouseRules) {
        this.houseRules = newHouseRules;
    }

    public void setPrivacy(boolean newPrivacy) {
        this.Public = newPrivacy;
    }

    public String getName() {
        return this.name;
    }

    public String getAddress() {
        return this.address;
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public String getHouseRules() {
        return this.houseRules;
    }

    public boolean getPrivacy() {
        return this.Public;
    }

    private void readObject(ObjectInputStream aInputStream
    ) throws ClassNotFoundException, IOException {
        //always perform the default de-serialization first
        aInputStream.defaultReadObject();
    }

    public void setHostName(String hostname){
        this.HostName =hostname;
    }

    public String getHostName(){
        return this.HostName;
    }


    private void writeObject(
            ObjectOutputStream aOutputStream
    ) throws IOException {
        //perform the default serialization for all non-transient, non-static fields
        aOutputStream.defaultWriteObject();
    }

    public Boolean isHost(houseRulesUser user){
        return user.getFullName().equals(HostName);
    }


}


