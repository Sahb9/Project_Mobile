package com.habitdark.myapplication.Entity;

public class History {
    private String  dataTime, subject;
    private static History instance = null;
    private int current;


    public History() {
    }

    public static History getInstance() {
        if(instance == null) {
            instance = new History();
        }

        return instance;
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
    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

}
