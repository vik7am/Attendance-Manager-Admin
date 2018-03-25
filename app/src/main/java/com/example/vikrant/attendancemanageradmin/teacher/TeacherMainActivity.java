package com.example.vikrant.attendancemanageradmin.teacher;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.vikrant.attendancemanageradmin.MainActivity;
import com.example.vikrant.attendancemanageradmin.R;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.widget.Toast;


public class TeacherMainActivity extends AppCompatActivity {

    public DatePickerDialog datePickerDialog;

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
        Calendar cal= Calendar.getInstance();
        SharedPreferences.Editor editor= getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        editor.putInt("dd",cal.get(Calendar.DAY_OF_MONTH));
        editor.putInt("mm",cal.get(Calendar.MONTH));
        editor.putInt("yy",cal.get(Calendar.YEAR));
        editor.commit();
        startActivity(new Intent(getApplicationContext(),TeacherAttendanceActivity.class));
    }
    public void editAttendance(View view)
    {
        getDate();
        //startActivity(new Intent(getApplicationContext(),TeacherAttendanceActivity.class));
    }
    public void fun1(View view)
    {
        //startActivity(new Intent(getApplicationContext(),TeacherAttendanceActivity.class));
    }
    public void getDate()
    {
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        //Toast.makeText(getApplicationContext(),""+mMonth,Toast.LENGTH_SHORT).show();
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        SharedPreferences.Editor editor= getSharedPreferences("data", Context.MODE_PRIVATE).edit();
                        editor.putInt("dd",dayOfMonth);
                        editor.putInt("mm",monthOfYear);
                        editor.putInt("yy",year);
                        editor.commit();
                        startActivity(new Intent(getApplicationContext(),TeacherAttendanceActivity.class));
                        //Toast.makeText(getApplicationContext(),""+dayOfMonth+":"+monthOfYear+":"+year,Toast.LENGTH_SHORT).show();
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


}
