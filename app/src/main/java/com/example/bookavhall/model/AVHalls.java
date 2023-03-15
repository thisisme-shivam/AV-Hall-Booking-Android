package com.example.bookavhall.model;

public class AVHalls {
    private String name;
    private String location;
    private int capacity;
    private String dept;

    public String getAvHallUid() {
        return avHallUid;
    }

    public void setAvHallUid(String avHallUid) {
        this.avHallUid = avHallUid;
    }

    private String avHallUid;

    public AVHalls() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
