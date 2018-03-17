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

public class StudentActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference db;
    MyAdapter adapter;
    String name,userId;
    EditText editText;
    TextView textView;
    LinearLayout linearLayout;
    Student student;
    ArrayList<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
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
        studentList=new ArrayList<>();
        initDatabase();
    }
    public void initDatabase()
    {
        db.child("student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    student=rowData.getValue(Student.class);
                    studentList.add(student);
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    public void addData(View view)
    {
        linearLayout.setVisibility(View.VISIBLE);
    }
    public void uploadData(View view)
    {
        linearLayout.setVisibility(View.GONE);
        name=editText.getText().toString().toLowerCase();
        userId = db.push().getKey();
        student = new Student(name);
        db.child("student").child(userId).setValue(student);
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
                view=LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1,viewGroup,false);
            }
            textView=view.findViewById(android.R.id.text1);
            textView.setText(studentList.get(i).name);
            textView.setTextColor(Color.BLACK);
            return view;
        }
    }
}