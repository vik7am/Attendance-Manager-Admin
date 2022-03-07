package com.example.attendancemanager.admin;

import androidx.appcompat.app.AppCompatActivity;
import com.example.attendancemanager.R;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TimeTableSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table_setting);
        init();
    }

    DatabaseReference db;
    String userId;
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
        db.child("teacher").child("0").setValue(new Teacher("Free"));
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
        finish();
    }
}