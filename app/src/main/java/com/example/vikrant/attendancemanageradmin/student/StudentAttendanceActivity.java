package com.example.vikrant.attendancemanageradmin.student;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.vikrant.attendancemanageradmin.admin.Teacher;
import com.example.vikrant.attendancemanageradmin.admin.TimeTable;
import com.example.vikrant.attendancemanageradmin.teacher.Attendance;
import com.example.vikrant.attendancemanageradmin.teacher.MarkAttendanceActivity;
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

public class StudentAttendanceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,DialogInterface.OnClickListener{

    ListView listView;
    DatabaseReference db;
    MyAdapter adapter;
    String userId,currentTeacherId,freeSubject,freeTeacher,STUDENT_ID;
    int listViewId,DAY_OF_WEEK,LECTURE_NO;
    EditText editText;
    TextView textView;
    LinearLayout linearLayout;
    TimeTable timeTable;
    Subject subject;
    Teacher teacher;
    Attendance attendance;
    Student student;
    ArrayList<TimeTable> timeTableList;
    ArrayList<TimeTable> currentTimeTableList;
    ArrayList<Subject> subjectList;
    ArrayList<Student> studentList;
    ArrayList<Attendance> attendanceList;
    //HashMap<String,String> hashMap;
    Calendar calendar;
    Intent intent;
    String SUBJECT_ID,TEACHER_ID;
    boolean present,flag;
    HashMap<String,Boolean> hashMap;
    HashMap<String,String> hashMap2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        intent=getIntent();
        /*DAY_OF_WEEK=intent.getIntExtra("DAY_OF_WEEK",0);
        LECTURE_NO=intent.getIntExtra("LECTURE_NO",0);
        SUBJECT_ID=intent.getStringExtra("SUBJECT_ID");
        TEACHER_ID=intent.getStringExtra("TEACHER_ID");*/
        calendar=Calendar.getInstance();
        DAY_OF_WEEK=calendar.get(Calendar.DAY_OF_WEEK);
        //DAY_OF_WEEK=7;
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
        listView.setOnItemClickListener(this);
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
                //System.out.println("Size"+currentTimeTableList.size());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });

        db.child("attendance").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Date d=Calendar.getInstance().getTime();
                attendanceList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    attendance=rowData.getValue(Attendance.class);
                    if(attendance.student_id.equals(STUDENT_ID))
                        if(attendance.date.getDate()==d.getDate())
                            if(attendance.date.getMonth()==d.getMonth())
                            {
                                attendance.id=rowData.getKey();
                                attendanceList.add(attendance);
                                hashMap.put(attendance.subject_id,attendance.present);
                            }
                }
                //currentData();
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
                //currentData();

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        /*
        listViewId=i;
        int length=subjectList.size();
        int ii=0;
        String list[]={"Present","Absent"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(list, this);
        builder.show();
        */
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

        /*
        if(i==0)present=true;
        else present=false;
        Date date = Calendar.getInstance().getTime();
        if(hashMap.containsKey(studentList.get(listViewId).id)==false)
            userId = db.push().getKey();
        else
            userId = attendanceList.get(listViewId).id;
        attendance = new Attendance(studentList.get(listViewId).id,SUBJECT_ID,LECTURE_NO,date,present);
        db.child("attendance").child(userId).setValue(attendance);
        */
        //System.out.println("Flag"+flag);
        /*
        timeTable=currentTimeTableList.get(listViewId);
        subject=subjectList.get(i);
        userId=timeTable.id;
        timeTable.subject_id=subject.id;
        timeTable.teacher_id=subject.teacher_id;
        db.child("timetable").child(userId).setValue(timeTable);
        */
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
            //System.out.println(currentTimeTableList.get(i).subject_id);
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
