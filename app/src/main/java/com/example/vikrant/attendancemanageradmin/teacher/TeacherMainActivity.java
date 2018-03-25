package com.example.vikrant.attendancemanageradmin.teacher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.vikrant.attendancemanageradmin.R;

public class TeacherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        setTitle("Teacher");
    }
    public void timetable(View view)
    {
        startActivity(new Intent(getApplicationContext(),TeacherTimeTableActivity.class));
    }
    public void attendance(View view)
    {
        startActivity(new Intent(getApplicationContext(),TeacherAttendanceActivity.class));
    }
}
