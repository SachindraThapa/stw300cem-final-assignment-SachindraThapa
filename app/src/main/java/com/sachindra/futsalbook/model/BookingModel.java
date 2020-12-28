package com.sachindra.futsalbook.model;

public class BookingModel {
    String date, time, username,id;

    public BookingModel(String date, String time, String username, String id) {
        this.date = date;
        this.time = time;
        this.username = username;
        this.id = id;
    }

    public BookingModel(String date, String time, String username) {
        this.date = date;
        this.time = time;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
