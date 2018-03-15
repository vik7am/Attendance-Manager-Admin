package com.example.vikrant.attendancemanageradmin.admin;

public class Student {

    public String id;
    public String name;

    public Student() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Student(String name) {
        this.name = name;
    }

}
