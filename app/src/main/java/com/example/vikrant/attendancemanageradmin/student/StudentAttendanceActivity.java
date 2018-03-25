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
import android.widget.Toast;

import com.example.vikrant.attendancemanageradmin.R;
import com.example.vikrant.attendancemanageradmin.admin.Student;
import com.example.vikrant.attendancemanageradmin.admin.Subject;
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
    ArrayList<TimeTable> timeTableList;
    ArrayList<TimeTable> currentTimeTableList;
    ArrayList<Subject> subjectList;
    ArrayList<Student> studentList;
    ArrayList<Attendance> attendanceList;
    Calendar calendar;
    Intent intent;
    HashMap<String,Boolean> hashMap;
    HashMap<String,String> hashMap2;
    SharedPreferences sp;
    MyDate date;
    Date d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        intent=getIntent();
        //calendar=Calendar.getInstance();
        //DAY_OF_WEEK=calendar.get(Calendar.DAY_OF_WEEK);
        sp=getSharedPreferences("data",Context.MODE_PRIVATE);
        /*d=new Date(sp.getInt("yy", 0), sp.getInt("mm", 0), sp.getInt("dd", 0));
        calendar=Calendar.getInstance();
        calendar.setTime(d);
        */

        date=new MyDate(0,0,0);
        date.DAY_OF_MONTH=sp.getInt("dd", 0);
        date.MONTH=sp.getInt("mm", 0);
        date.YEAR=sp.getInt("yy", 0);
        calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,date.DAY_OF_MONTH);
        calendar.set(Calendar.MONTH,date.MONTH);
        calendar.set(Calendar.YEAR,date.YEAR);

        DAY_OF_WEEK=calendar.get(Calendar.DAY_OF_WEEK);
        if(DAY_OF_WEEK==0)DAY_OF_WEEK=7;
        String ss=""+sp.getInt("yy", 0)+":"+sp.getInt("mm", 0)+":"+sp.getInt("dd", 0);
        Toast.makeText(getApplicationContext(),""+DAY_OF_WEEK+"^"+ss, Toast.LENGTH_SHORT).show();
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
        hashMap=new HashMap<>();
        hashMap2=new HashMap<>();
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
                        if(attendance.date.DAY_OF_MONTH ==date.DAY_OF_MONTH)
                            if(attendance.date.MONTH==date.MONTH)
                            {
                                attendance.id=rowData.getKey();
                                attendanceList.add(attendance);
                                hashMap.put(attendance.subject_id,attendance.present);
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
                    hashMap2.put(subject.id,subject.name);
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
                view= LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,viewGroup,false);
            }
            textView=view.findViewById(android.R.id.text1);
            //System.out.println(""+currentTimeTableList.get(i).subject_id);
            //Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
            textView.setText(hashMap2.get(currentTimeTableList.get(i).subject_id));
            if(hashMap.containsKey(currentTimeTableList.get(i).subject_id))
                if(hashMap.get(currentTimeTableList.get(i).subject_id))
                    textView.setTextColor(Color.GREEN);
                else
                    textView.setTextColor(Color.RED);
            else
                textView.setTextColor(Color.BLACK);
            return view;
        }
    }
}
