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
import java.util.Date;
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
    MyDate date;
    SharedPreferences sp;
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
        sp=getSharedPreferences("data",Context.MODE_PRIVATE);
        //date=new Date(sp.getInt("yy", 0), sp.getInt("mm", 0), sp.getInt("dd", 0));
        date=new MyDate(0,0,0);
        date.DAY_OF_MONTH=sp.getInt("dd", 0);
        date.MONTH=sp.getInt("mm", 0);
        date.YEAR=sp.getInt("yy", 0);
        init();
        //System.out.println(""+Calendar.getInstance().getTime().getDate());
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
                //Date d=Calendar.getInstance().getTime();
                attendanceList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    attendance=rowData.getValue(Attendance.class);
                    if(attendance.lecture_no==LECTURE_NO)
                        if(attendance.date.DAY_OF_MONTH==date.DAY_OF_MONTH)
                            if(attendance.date.MONTH==date.MONTH)
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
        // = Calendar.getInstance().getTime();

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
                view= LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,viewGroup,false);
            }
            textView=view.findViewById(android.R.id.text1);
            textView.setText(studentList.get(i).name);
            if(hashMap.containsKey(studentList.get(i).id))
                if(hashMap.get(studentList.get(i).id))
                    textView.setTextColor(Color.GREEN);
                else
                    textView.setTextColor(Color.RED);
            else
                textView.setTextColor(Color.BLACK);
            return view;
        }
    }
}
