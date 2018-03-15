package com.example.vikrant.attendancemanageradmin.admin;

/**
 * Created by vikrant on 15-03-2018.
 */

public class Subject {
    public String name;
    public String Teacher;

    public Subject() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Subject(String name) {
        this.name = name;
    }
}
