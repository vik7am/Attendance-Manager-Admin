package com.example.vikrant.attendancemanageradmin.admin;

/**
 * Created by vikrant on 15-03-2018.
 */

public class TimeTable {
    public String id;
    public int day_of_week;
    public int lecture_no;
    public String subject_id;
    public String teacher_id;

    public TimeTable() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public TimeTable(int day_of_week,int lecture_no,String subject_id,String teacher_id) {
        this.day_of_week = day_of_week;
        this.lecture_no = lecture_no;
        this.subject_id = subject_id;
        this.teacher_id = teacher_id;

    }
}
