package com.example.localarm.data;

import android.media.Image;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class Task implements Serializable {

    // "Information" placed into ArrayList of String
    private ArrayList<String> information;

    private String title;

    // Location and location in text form
    private LatLng location;
    private String locationText;

    // Date and date in text form
    private Date date;
    private String dateText;

    private String description;

    /**
     * Assigns attributes of task based off of string ArrayList.
     * @param information info
     */
    public Task(ArrayList<String> information) {
        this.information = information;

        if (information.size() == 4) {
            this.title = information.get(0);
            this.locationText = information.get(1);
            this.dateText = information.get(2);
            this.description = information.get(3);
        }
    }

    /**
     * Constructor that takes in string parameters for the attributes of a task.
     * @param title title
     * @param locationText location in text form
     * @param dateText date in text form
     * @param description description
     */
    public Task(String title, String locationText, String dateText, @Nullable String description) {
        information = new ArrayList<>();

        this.title = title;
        this.locationText = locationText;
        this.dateText = dateText;
        this.description = description;

        information.add(this.title);
        information.add(this.locationText);
        information.add(this.dateText);
        information.add(this.description);
    }

    public ArrayList<String> getInformation() {
        return information;
    }

    public void setInformation(ArrayList<String> information) {
        this.information = information;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public String getLocationText() {
        return locationText;
    }

    public void setLocationText(String locationText) {
        this.locationText = locationText;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateText() {
        return dateText;
    }

    public void setDateText(String dateText) {
        this.dateText = dateText;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }
}

