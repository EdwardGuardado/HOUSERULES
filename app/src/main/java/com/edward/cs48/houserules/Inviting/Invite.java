package com.edward.cs48.houserules.Inviting;

import com.edward.cs48.houserules.HouseRulesEvent.houseRulesEvent;

/**
 * Created by Edward on 3/14/17.
 */

public class Invite {
    private String id;
    private String text;
    private String name;
    private houseRulesEvent event;


    public Invite() {
    }

    public Invite(String text, String name, houseRulesEvent event) {
        this.text = text;
        this.name = name;
        this.event = event;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEvent(houseRulesEvent event) {
        this.event = event;
    }

    public houseRulesEvent getEvent() {
        return event;
    }
}

