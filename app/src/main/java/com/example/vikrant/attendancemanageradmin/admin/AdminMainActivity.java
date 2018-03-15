package com.example.vikrant.attendancemanageradmin.admin;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.vikrant.attendancemanageradmin.R;

public class AdminMainActivity extends AppCompatActivity {

    //EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        //editText=new EditText(getApplicationContext());
        //editText.setTextColor(Color.BLACK);
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
