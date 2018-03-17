package com.example.vikrant.attendancemanageradmin.admin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.vikrant.attendancemanageradmin.R;

public class AdminMainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
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
        //startActivity(new Intent(getApplicationContext(),TimeTableSettingActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
