package com.example.bookavhall.model;

public class Report {
    private String avHall;
    private String bookingId;
    private String date;
    private String time;
    private String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Report() {

    }

    public String getAvHall() {
        return avHall;
    }

    public void setAvHall(String avHall) {
        this.avHall = avHall;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
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
}
