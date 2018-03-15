package com.example.vikrant.attendancemanageradmin.admin;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vikrant.attendancemanageradmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TimeTableSettingActivity extends AppCompatActivity {

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
    }


    public void uploadData(View view)
    {
        no_of_lecture=Integer.parseInt(editText.getText().toString());
        t = db.push().getKey();
        db.child("teacher").child(t).setValue(new Teacher("Free"));
        s = db.push().getKey();
        db.child("subject").child(s).setValue(new Subject("Free",t,"Free"));
        for(int i=0;i<7;i++)
        {
            for(int j=0;j<no_of_lecture;j++)
            {
                userId = db.push().getKey();
                timeTable = new TimeTable(i,j,s,t,"Free","Free");
                db.child("timetable").child(userId).setValue(timeTable);
            }
        }
        finish();

    }


}
