package com.edward.cs48.houserules.HouseRulesEvent;

/**
 * Created by Edward on 2/18/17.
 */

public class houseRulesEvent {
    private String name;
    private String address;
    private String date;
    private String time;
    private String houseRules;
    private boolean privacy;


    public houseRulesEvent() {
        this.name = "";
        this.address = "";
        this.date = "";
        this.time = "";
        this.houseRules = "";
        this.privacy = false;
    }

    public houseRulesEvent(String name, String address, String date, String time, String houseRules, boolean privacy) {
        this.name = name;
        this.address = address;
        this.date = date;
        this.time = time;
        this.houseRules = houseRules;
        this.privacy = privacy;
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
        this.privacy = newPrivacy;
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
        return this.privacy;
    }
}


