package com.android.myapplication.Entity;

public class History {
    private String title, content, dataTime, subject;
    private static History instance = null;

    private History() {
    }

    public static History getInstance() {
        if(instance == null) {
            instance = new History();
        }

        return instance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
