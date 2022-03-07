package com.example.attendancemanager.admin;

import androidx.appcompat.app.AppCompatActivity;
import com.example.attendancemanager.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        setTitle("Admin");
    }

    public void students(View view)
    {
        startActivity(new Intent(getApplicationContext(),StudentActivity.class));
    }
    public void teachers(View view)
    {
        startActivity(new Intent(getApplicationContext(),TeacherActivity.class));
    }
    public void subjects(View view)
    {
        startActivity(new Intent(getApplicationContext(),SubjectActivity.class));
    }
    public void timetable(View view)
    {
        startActivity(new Intent(getApplicationContext(),TimeTableActivity.class));
    }
    public void timetableSetting(View view)
    {
        startActivity(new Intent(getApplicationContext(),TimeTableSettingActivity.class));
    }
    public void help(View view)
    {

    }
}