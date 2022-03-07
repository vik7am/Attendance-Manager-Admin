package com.example.attendancemanager;

import androidx.appcompat.app.AppCompatActivity;
import com.example.attendancemanager.admin.*;
import com.example.attendancemanager.teacher.*;
import com.example.attendancemanager.student.*;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    String username,ADMIN="admin";
    DatabaseReference db;
    Teacher teacher;
    Student student;
    HashMap<String,String> teacherList;
    HashMap<String,String> studentList;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=findViewById(R.id.input_email);
        initDatabase();
    }

    public void login(View view)
    {
        username=editText.getText().toString();
        editor=getSharedPreferences("data", Context.MODE_PRIVATE).edit();
        if(username.equals(ADMIN))
        {
            editor.putString("id","admin");
            editor.commit();
            startActivity(new Intent(getApplicationContext(),AdminMainActivity.class));
        }
        else if(studentList.containsKey(username))
        {
            editor.putString("id",studentList.get(username));
            editor.commit();
            startActivity(new Intent(getApplicationContext(),StudentMainActivity.class));
        }
        else if(teacherList.containsKey(username))
        {
            editor.putString("id",teacherList.get(username));
            editor.commit();
            startActivity(new Intent(getApplicationContext(),TeacherMainActivity.class));
        }
        else
        {
            Toast.makeText(this, "Incorrect Username", Toast.LENGTH_SHORT).show();
        }
    }

    public void initDatabase()
    {
        teacherList=new HashMap<>();
        studentList=new HashMap<>();
        db= FirebaseDatabase.getInstance().getReference();

        db.child("teacher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacherList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    teacher=rowData.getValue(Teacher.class);
                    teacher.id=rowData.getKey();
                    teacherList.put(teacher.name,teacher.id);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });

        db.child("student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    student=rowData.getValue(Student.class);
                    student.id=rowData.getKey();
                    studentList.put(student.name,student.id);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }
}