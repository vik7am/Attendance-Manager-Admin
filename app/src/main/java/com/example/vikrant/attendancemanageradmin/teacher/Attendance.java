package com.example.vikrant.attendancemanageradmin.teacher;

import java.util.Date;

/**
 * Created by vikrant on 24-03-2018.
 */

public class Attendance {
    public String id;
    public int lecture_no;
    public Date date;
    public boolean present;
    public String student_id,subject_id;

    public Attendance() {
    }

    public Attendance(String student_id,String subject_id,int lecture_no,Date date,boolean present) {
        this.student_id = student_id;
        this.subject_id=subject_id;
        this.lecture_no=lecture_no;
        this.date=date;
        this.present=present;
    }
}
