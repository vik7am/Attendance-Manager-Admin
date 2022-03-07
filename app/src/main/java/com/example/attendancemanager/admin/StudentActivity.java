package com.example.attendancemanager.admin;

import androidx.appcompat.app.AppCompatActivity;
import com.example.attendancemanager.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
        editText.setText("");
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
                //LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                //view = layoutInflater.inflate(R.layout.row0, viewGroup, false);
                view= LayoutInflater.from(StudentActivity.this).inflate(R.layout.row0,viewGroup,false);
            }
            ((TextView)view.findViewById(R.id.s_no)).setText(""+(i+1));
            ((TextView)view.findViewById(R.id.name)).setText(studentList.get(i).name);
            return view;
        }
    }
}