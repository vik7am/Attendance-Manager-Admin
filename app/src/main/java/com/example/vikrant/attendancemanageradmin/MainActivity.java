package com.example.vikrant.attendancemanageradmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.vikrant.attendancemanageradmin.admin.AdminMainActivity;
import com.example.vikrant.attendancemanageradmin.teacher.TeacherMainActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void admin(View view)
    {
        startActivity(new Intent(getApplicationContext(),AdminMainActivity.class));
    }

    public void students(View view)
    {
        //startActivity(new Intent(getApplicationContext(),StudentMainActivity.class));
    }
    public void teachers(View view)
    {
        startActivity(new Intent(getApplicationContext(),TeacherMainActivity.class));
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
