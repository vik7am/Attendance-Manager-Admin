package com.example.attendancemanager.teacher;

import com.example.attendancemanager.student.*;

import java.util.Date;

public class Attendance {
    public String id;
    public int lecture_no;
    public MyDate date;
    public boolean present;
    public String student_id,subject_id;

    public Attendance() {
    }

    public Attendance(String student_id,String subject_id,int lecture_no,MyDate date,boolean present) {
        this.student_id = student_id;
        this.subject_id=subject_id;
        this.lecture_no=lecture_no;
        this.date=date;
        this.present=present;
    }
}
