package com.example.vikrant.attendancemanageradmin.student;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.vikrant.attendancemanageradmin.R;
import com.example.vikrant.attendancemanageradmin.teacher.TeacherAttendanceActivity;
import com.example.vikrant.attendancemanageradmin.teacher.TeacherTimeTableActivity;

public class StudentMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_main);
        setTitle("Student");
    }
    public void timetable(View view)
    {
        startActivity(new Intent(getApplicationContext(),StudentTimeTableActivity.class));
    }
    public void attendance(View view)
    {
        startActivity(new Intent(getApplicationContext(),StudentAttendanceActivity.class));
    }
}
