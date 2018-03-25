package com.example.vikrant.attendancemanageradmin.student;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.example.vikrant.attendancemanageradmin.R;

import java.util.Calendar;

public class StudentMainActivity extends AppCompatActivity {

    DatePickerDialog datePickerDialog;
    Intent intent;

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
        Calendar calendar= Calendar.getInstance();
        intent=new Intent(getApplicationContext(),StudentAttendanceActivity.class);
        intent.putExtra("dd",calendar.get(Calendar.DAY_OF_MONTH));
        intent.putExtra("mm",calendar.get(Calendar.MONTH));
        intent.putExtra("yy",calendar.get(Calendar.YEAR));
        startActivity(intent);
    }
    public void editAttendance(View view)
    {
        getDate();
    }
    public void fun1(View view)
    {

    }

    public void getDate()
    {
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        intent=new Intent(getApplicationContext(),StudentAttendanceActivity.class);
                        intent.putExtra("dd",dayOfMonth);
                        intent.putExtra("mm",monthOfYear);
                        intent.putExtra("yy",year);
                        startActivity(intent);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
}
