package com.habitdark.myapplication.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

public class DateService {
    public static int findDayofWeek(int DAY, Month MONTH, int YEAR)
    {
        LocalDate localDate = LocalDate.of(YEAR, MONTH, DAY);
        DayOfWeek dayOfWeek = DayOfWeek.from(localDate);
        int val = dayOfWeek.getValue();
        return val;
    }
}
