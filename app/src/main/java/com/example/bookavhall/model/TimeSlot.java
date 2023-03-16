package com.example.bookavhall.model;

public class TimeSlot {
    String startTime,endTime,lunchTime;

    public String getStartTime() {
        return startTime;
    }


    public String getLunchTime() {
        return lunchTime;
    }

    public void setLunchTime(String lunchTime) {
        this.lunchTime = lunchTime;
    }

    public TimeSlot(String startTime, String endTime, String lunchTime){
        this.startTime = startTime;
        this.endTime = endTime;
        this.lunchTime = lunchTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public TimeSlot(){

    }
}
