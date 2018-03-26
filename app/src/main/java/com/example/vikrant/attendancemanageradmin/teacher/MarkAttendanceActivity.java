package com.example.vikrant.attendancemanageradmin.teacher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.vikrant.attendancemanageradmin.R;
import com.example.vikrant.attendancemanageradmin.admin.Student;
import com.example.vikrant.attendancemanageradmin.admin.Subject;
import com.example.vikrant.attendancemanageradmin.admin.TimeTable;
import com.example.vikrant.attendancemanageradmin.student.MyDate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MarkAttendanceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,DialogInterface.OnClickListener{


    ListView listView;
    DatabaseReference db;
    MyAdapter adapter;
    String userId;
    int listViewId,DAY_OF_WEEK,LECTURE_NO;
    EditText editText;
    TextView textView;
    LinearLayout linearLayout;
    Attendance attendance;
    Student student;
    ArrayList<TimeTable> timeTableList;
    ArrayList<TimeTable> currentTimeTableList;
    ArrayList<Subject> subjectList;
    ArrayList<Student> studentList;
    ArrayList<Attendance> attendanceList;
    Intent intent;
    String SUBJECT_ID,TEACHER_ID;
    boolean present;
    Calendar calendar;
    MyDate date;
    HashMap<String,Boolean> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);
        intent=getIntent();
        DAY_OF_WEEK=intent.getIntExtra("DAY_OF_WEEK",0);
        LECTURE_NO=intent.getIntExtra("LECTURE_NO",0);
        SUBJECT_ID=intent.getStringExtra("SUBJECT_ID");
        TEACHER_ID=intent.getStringExtra("TEACHER_ID");
        calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,intent.getIntExtra("dd",0));
        calendar.set(Calendar.MONTH,intent.getIntExtra("mm",0));
        calendar.set(Calendar.YEAR,intent.getIntExtra("yy",0));
        init();
    }

    public void init()
    {
        db= FirebaseDatabase.getInstance().getReference();
        listView=findViewById(R.id.listView1);
        editText=findViewById(R.id.editText1);
        linearLayout=findViewById(R.id.linearLayout1);
        adapter=new MyAdapter(getApplicationContext());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        timeTableList=new ArrayList<>();
        currentTimeTableList=new ArrayList<>();
        attendanceList=new ArrayList<>();
        subjectList=new ArrayList<>();
        studentList=new ArrayList<>();
        hashMap=new HashMap<>();
        initDatabase();
    }
    public void initDatabase()
    {
        db.child("student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                timeTableList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    student=rowData.getValue(Student.class);
                    student.id=rowData.getKey();
                    studentList.add(student);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
        db.child("attendance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                attendanceList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    attendance=rowData.getValue(Attendance.class);
                    if(attendance.lecture_no==LECTURE_NO)
                        if(attendance.date.DAY_OF_MONTH==calendar.get(Calendar.DAY_OF_MONTH))
                            if(attendance.date.MONTH==calendar.get(Calendar.MONTH))
                            {
                                attendance.id=rowData.getKey();
                                attendanceList.add(attendance);
                                hashMap.put(attendance.student_id,attendance.present);
                            }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });

    }
    public void fun1(View view)
    {

    }

    public void cancelUpload(View view)
    {
        linearLayout.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        listViewId=i;
        String list[]={"Present","Absent"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(list,  this);
        builder.show();

    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        if(i==0)present=true;
        else present=false;
        date=new MyDate(calendar.get(Calendar.DAY_OF_MONTH),calendar.get(Calendar.MONTH),calendar.get(Calendar.YEAR));
        if(hashMap.containsKey(studentList.get(listViewId).id)==false)
            userId = db.push().getKey();
        else
            userId = attendanceList.get(listViewId).id;
        attendance = new Attendance(studentList.get(listViewId).id,SUBJECT_ID,LECTURE_NO,date,present);
        db.child("attendance").child(userId).setValue(attendance);
    }

    class MyAdapter extends BaseAdapter {
        Context context;
        MyAdapter(Context context)
        {
            this.context=context;
        }

        @Override
        public int getCount() {
            if(studentList==null)
                return 0;
            return studentList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null)
            {
                view=LayoutInflater.from(MarkAttendanceActivity.this).inflate(R.layout.row0,viewGroup,false);
            }
            ((TextView)view.findViewById(R.id.s_no)).setText(""+(i+1));
            ((TextView)view.findViewById(R.id.name)).setText(studentList.get(i).name);

            /*textView=view.findViewById(android.R.id.text1);
            textView.setText(studentList.get(i).name);*/
            if(hashMap.containsKey(studentList.get(i).id))
                if(hashMap.get(studentList.get(i).id))
                    ((TextView)view.findViewById(R.id.name)).setTextColor(Color.GREEN);
                else
                    ((TextView)view.findViewById(R.id.name)).setTextColor(Color.RED);
            else
                ((TextView)view.findViewById(R.id.name)).setTextColor(Color.BLACK);
            return view;
        }
    }
}
