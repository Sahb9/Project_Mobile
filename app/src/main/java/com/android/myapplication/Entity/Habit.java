package com.android.myapplication.Entity;

public class Habit {
    private String idHabit;
    private String name;
    private int target;
    private int current;
    private String startDate;
    private Alarm alarm;

    public Habit() {
    }

    public String getIdHabit() {
        return idHabit;
    }

    public void setIdHabit(String idHabit) {
        this.idHabit = idHabit;
    }

    public Habit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "name='" + name + '\'' +
                ", target=" + target + '\'' +
                ", current=" + current +
                '}';
    }
}
