package com.example.vikrant.attendancemanageradmin.admin;

public class Subject {
    public String id;
    public String name;
    public String teacher_id;

    public Subject() {
    }

    public Subject(String name,String teacher_id) {
        this.name = name;
        this.teacher_id=teacher_id;
    }
}
