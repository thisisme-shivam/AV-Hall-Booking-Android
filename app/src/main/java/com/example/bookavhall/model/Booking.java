package com.example.bookavhall.model;

public class Booking {
    String bookingTime, userUid, bookingUid, avHallUid, bookingDate;
    public Booking(){

    }

    public String getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(String bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getBookingUid() {
        return bookingUid;
    }

    public void setBookingUid(String bookingUid) {
        this.bookingUid = bookingUid;
    }

    public String getAvHallUid() {
        return avHallUid;
    }

    public void setAvHallUid(String avHallUid) {
        this.avHallUid = avHallUid;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }
}
