package com.example.vikrant.attendancemanageradmin.teacher;

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
import com.example.vikrant.attendancemanageradmin.admin.Subject;
import com.example.vikrant.attendancemanageradmin.admin.Teacher;
import com.example.vikrant.attendancemanageradmin.admin.TimeTable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class TeacherAttendanceActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,DialogInterface.OnClickListener{

    ListView listView;
    DatabaseReference db;
    MyAdapter adapter;
    String userId,currentTeacherId,freeSubject,freeTeacher;
    int listViewId,DAY_OF_WEEK;
    EditText editText;
    TextView textView;
    LinearLayout linearLayout;
    TimeTable timeTable;
    Subject subject;
    Teacher teacher;
    ArrayList<TimeTable> timeTableList;
    ArrayList<TimeTable> currentTimeTableList;
    ArrayList<Subject> subjectList;
    HashMap<String,String> hashMap;
    Calendar calendar;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance);
        calendar=Calendar.getInstance();
        DAY_OF_WEEK=calendar.get(Calendar.DAY_OF_WEEK);
        changeTitle();
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
        subjectList=new ArrayList<>();
        hashMap=new HashMap<>();
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

        db.child("subject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subjectList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    subject=rowData.getValue(Subject.class);
                    subject.id=rowData.getKey();
                    subjectList.add(subject);
                    hashMap.put(subject.id,subject.name);
                    if(subject.name.equals("Free"))
                        freeSubject=subject.id;
                }
                currentData();
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
        db.child("teacher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //subjectList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    teacher=rowData.getValue(Teacher.class);
                    teacher.id=rowData.getKey();
                    //subjectList.add(subject);
                    //System.out.println(teacher.name);
                    if(teacher.name.equals("smriti"))
                        currentTeacherId=teacher.id;
                    if(teacher.name.equals("Free"))
                        freeTeacher=teacher.id;
                }
                currentData();
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    public void currentData()
    {
        currentTimeTableList.clear();
        for(TimeTable tt:timeTableList)
        {
            if(tt.day_of_week==(DAY_OF_WEEK-1))
                if(tt.teacher_id.equals(currentTeacherId))
                    currentTimeTableList.add(tt);
                else
                    currentTimeTableList.add(new TimeTable(tt.day_of_week,tt.lecture_no,freeSubject,freeTeacher));
        }
        //System.out.println("&&&&&&"+currentTeacherId+"******"+currentTimeTableList.size());
    }

    public void changeTitle() {
        Locale usersLocale = Locale.getDefault();
        DateFormatSymbols dfs = new DateFormatSymbols(usersLocale);
        String weekdays[] = dfs.getWeekdays();
        setTitle(weekdays[DAY_OF_WEEK]);
    }

    public void cancelUpload(View view)
    {
        linearLayout.setVisibility(View.GONE);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        intent=new Intent(this,MarkAttendanceActivity.class);
        intent.putExtra("DAY_OF_WEEK",DAY_OF_WEEK);
        intent.putExtra("LECTURE_NO",i);
        intent.putExtra("SUBJECT_ID",currentTimeTableList.get(i).subject_id);
        intent.putExtra("TEACHER_ID",currentTimeTableList.get(i).teacher_id);
        startActivity(intent);

        /*
        listViewId=i;
        int length=subjectList.size();
        int ii=0;
        String list[]=new String[length];
        for(Subject t:subjectList)
            list[ii++]=t.name;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(list,  this);
        builder.show();
        */
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
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
            textView.setText(hashMap.get(currentTimeTableList.get(i).subject_id));
            textView.setTextColor(Color.BLACK);
            return view;
        }
    }
}
