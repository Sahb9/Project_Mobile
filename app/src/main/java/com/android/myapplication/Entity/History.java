package com.android.myapplication.Entity;

public class History {
    private String uid;
    private String dataTime;
    private String subject;

    public History() {
    }

    public History(String uid, String dataTime, String subject) {
        this.uid = uid;
        this.dataTime = dataTime;
        this.subject = subject;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
