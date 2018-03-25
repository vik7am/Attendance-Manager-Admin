package com.example.vikrant.attendancemanageradmin.admin;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.vikrant.attendancemanageradmin.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TimeTableSettingActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_setting);
        init();
    }

    DatabaseReference db;
    String userId,t,s;
    int no_of_lecture;
    EditText editText;
    TimeTable timeTable;

    public void init()
    {
        db= FirebaseDatabase.getInstance().getReference();
        editText=findViewById(R.id.editText);
        editor=getSharedPreferences("data", Context.MODE_PRIVATE).edit();
    }

    public void uploadData(View view)
    {
        no_of_lecture=Integer.parseInt(editText.getText().toString());
        //t = db.push().getKey();
        db.child("teacher").child("0").setValue(new Teacher("Free"));
        //s = db.push().getKey();
        db.child("subject").child("0").setValue(new Subject("Free","0"));
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<no_of_lecture;j++)
            {
                userId = db.push().getKey();
                timeTable = new TimeTable(i,j,"0","0");
                db.child("timetable").child(userId).setValue(timeTable);
            }
        }
        /*editor.putString("FREE_SUBJECT",s);
        editor.putString("FREE_TEACHER",t);
        editor.commit();*/
        finish();
    }
}
