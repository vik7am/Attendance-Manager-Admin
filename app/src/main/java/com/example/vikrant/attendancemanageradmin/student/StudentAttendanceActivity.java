package com.example.vikrant.attendancemanageradmin.student;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.vikrant.attendancemanageradmin.admin.Student;
import com.example.vikrant.attendancemanageradmin.admin.Subject;
import com.example.vikrant.attendancemanageradmin.admin.Teacher;
import com.example.vikrant.attendancemanageradmin.admin.TimeTable;
import com.example.vikrant.attendancemanageradmin.teacher.Attendance;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class StudentAttendanceActivity extends AppCompatActivity{

    ListView listView;
    DatabaseReference db;
    MyAdapter adapter;
    String STUDENT_ID;
    int DAY_OF_WEEK;
    EditText editText;
    TextView textView;
    LinearLayout linearLayout;
    TimeTable timeTable;
    Subject subject;
    Attendance attendance;
    Teacher teacher;
    ArrayList<TimeTable> timeTableList;
    ArrayList<TimeTable> currentTimeTableList;
    ArrayList<Subject> subjectList;
    ArrayList<Student> studentList;
    ArrayList<Attendance> attendanceList;
    Calendar calendar;
    Intent intent;
    HashMap<String,Boolean> presentMap;
    HashMap<String,String> subjectMap;
    HashMap<String,String> teacherMap;
    ArrayList<Integer> presentList,absentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        intent=getIntent();
        calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,intent.getIntExtra("dd",0));
        calendar.set(Calendar.MONTH,intent.getIntExtra("mm",0));
        calendar.set(Calendar.YEAR,intent.getIntExtra("yy",0));
        DAY_OF_WEEK=calendar.get(Calendar.DAY_OF_WEEK);
        changeTitle();
        init();
    }

    public void init()
    {
        STUDENT_ID=getSharedPreferences("data",Context.MODE_PRIVATE).getString("id","");
        db= FirebaseDatabase.getInstance().getReference();
        listView=findViewById(R.id.listView1);
        editText=findViewById(R.id.editText1);
        linearLayout=findViewById(R.id.linearLayout1);
        adapter=new MyAdapter(getApplicationContext());
        listView.setAdapter(adapter);
        timeTableList=new ArrayList<>();
        currentTimeTableList=new ArrayList<>();
        attendanceList=new ArrayList<>();
        subjectList=new ArrayList<>();
        studentList=new ArrayList<>();
        presentMap=new HashMap<>();
        subjectMap=new HashMap<>();
        teacherMap=new HashMap<>();
        presentList=new ArrayList<>();
        absentList=new ArrayList<>();
        initDatabase();
    }
    public void initDatabase()
    {
        db.child("timetable").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                timeTableList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    timeTable=rowData.getValue(TimeTable.class);
                    timeTable.id=rowData.getKey();
                    timeTableList.add(timeTable);
                }
                currentData();
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
                    if(attendance.student_id.equals(STUDENT_ID))
                        if(attendance.date.DAY_OF_MONTH ==calendar.get(Calendar.DAY_OF_MONTH))
                            if(attendance.date.MONTH==calendar.get(Calendar.MONTH))
                            {
                                attendance.id=rowData.getKey();
                                attendanceList.add(attendance);
                                if(attendance.present)
                                    presentList.add(attendance.lecture_no);
                                else
                                    absentList.add((attendance.lecture_no));
                            }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });

        db.child("subject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subjectList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    subject=rowData.getValue(Subject.class);
                    subject.id=rowData.getKey();
                    subjectList.add(subject);
                    subjectMap.put(subject.id,subject.name);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
        db.child("teacher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    teacher=rowData.getValue(Teacher.class);
                    teacher.id=rowData.getKey();
                    teacherMap.put(rowData.getKey(),teacher.name);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

    }
    public void changeTitle() {
        Locale usersLocale = Locale.getDefault();
        DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
        String weekdays[] = dfs.getWeekdays();
        setTitle(weekdays[DAY_OF_WEEK]);
    }

    public void currentData()
    {
        currentTimeTableList.clear();
        for(TimeTable tt:timeTableList)
        {
            if(tt.day_of_week==(DAY_OF_WEEK-1))
                currentTimeTableList.add(tt);
        }
    }

    public void cancelUpload(View view)
    {
        linearLayout.setVisibility(View.GONE);
    }


    class MyAdapter extends BaseAdapter {
        Context context;
        MyAdapter(Context context)
        {
            this.context=context;
        }

        @Override
        public int getCount() {

            if(currentTimeTableList==null)
                return 0;
            return currentTimeTableList.size();
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
                view= LayoutInflater.from(StudentAttendanceActivity.this).inflate(R.layout.row1,viewGroup,false);
            }
            ((TextView)view.findViewById(R.id.s_no)).setText(""+(i+1));
            ((TextView)view.findViewById(R.id.subject_name)).setText(subjectMap.get(currentTimeTableList.get(i).subject_id));
            ((TextView)view.findViewById(R.id.teacher_name)).setText(teacherMap.get(currentTimeTableList.get(i).teacher_id));

            if(presentList.contains(i))
                ((TextView)view.findViewById(R.id.subject_name)).setTextColor(Color.GREEN);
            else if(absentList.contains(i))
                ((TextView)view.findViewById(R.id.subject_name)).setTextColor(Color.RED);
            else
                ((TextView)view.findViewById(R.id.subject_name)).setTextColor(Color.BLACK);
            return view;
        }
    }
}
