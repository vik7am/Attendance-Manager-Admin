package com.example.vikrant.attendancemanageradmin.admin;

/**
 * Created by vikrant on 10-03-2018.
 */

public class Teacher {
    public String name;

    public Teacher() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Teacher(String name) {
        this.name = name;
    }
}
