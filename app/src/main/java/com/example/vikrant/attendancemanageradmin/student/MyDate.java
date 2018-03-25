package com.example.vikrant.attendancemanageradmin.student;

/**
 * Created by vikrant on 25-03-2018.
 */

public class MyDate {
    public String id;
    public int DAY_OF_MONTH;
    public int MONTH;
    public int YEAR;

    public MyDate() {
    }

    public MyDate(int DAY_OF_MONTH,int MONTH,int YEAR) {
        this.DAY_OF_MONTH = DAY_OF_MONTH;
        this.MONTH=MONTH;
        this.YEAR=YEAR;
    }
}
