package com.example.bookavhall;

public class NotificationModel {
    String notificationUid;


    public String getNotificationUid() {
        return notificationUid;
    }

    public void setNotificationUid(String notificationUid) {
        this.notificationUid = notificationUid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String message;
    String time;
    String date;
}
