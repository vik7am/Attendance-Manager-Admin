package com.example.vikrant.attendancemanageradmin.admin;

/**
 * Created by vikrant on 15-03-2018.
 */

public class Subject {
    public String id;
    public String name;
    public String teacher_id;

    public Subject() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Subject(String name,String teacher_id) {
        this.name = name;
        this.teacher_id=teacher_id;
    }
}
