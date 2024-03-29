package com.example.attendancemanager.admin;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.attendancemanager.R;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class SubjectActivity extends AppCompatActivity implements DialogInterface.OnClickListener{

    ListView listView;
    DatabaseReference db;
    MyAdapter adapter;
    String name,userId,teacher_id;
    EditText editText;
    TextView textView,textView2;
    LinearLayout linearLayout;
    Subject subject;
    Teacher teacher;
    ArrayList<Subject> subjectList;
    ArrayList<Teacher> teacherList;
    HashMap<String,String> hashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        init();
    }



    public void init()
    {
        db= FirebaseDatabase.getInstance().getReference();
        listView=findViewById(R.id.listView1);
        editText=findViewById(R.id.editText1);
        textView2=findViewById(R.id.textView1);
        linearLayout=findViewById(R.id.linearLayout1);
        adapter=new MyAdapter(getApplicationContext());
        listView.setAdapter(adapter);
        subjectList=new ArrayList<>();
        teacherList=new ArrayList<>();
        hashMap=new HashMap<>();
        initDatabase();
    }
    public void initDatabase()
    {
        db.child("subject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                subjectList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    subject=rowData.getValue(Subject.class);
                    subjectList.add(subject);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });

        db.child("teacher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacherList.clear();
                for (DataSnapshot rowData : dataSnapshot.getChildren()) {
                    teacher=rowData.getValue(Teacher.class);
                    teacherList.add(teacher);
                    teacher.id=rowData.getKey();
                    hashMap.put(rowData.getKey(),teacher.name);
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

    public void selectTeacher(View view)
    {
        int length=teacherList.size();
        int i=0;
        String list[]=new String[length];
        for(Teacher t:teacherList)
            list[i++]=t.name;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(list,this);
        builder.show();
    }
    public void uploadData(View view)
    {
        linearLayout.setVisibility(View.GONE);
        name=editText.getText().toString().toLowerCase();
        userId = db.push().getKey();
        subject = new Subject(name,teacher_id);
        db.child("subject").child(userId).setValue(subject);
        editText.setText("");
        textView2.setText("Select Teacher");
    }
    public void cancelUpload(View view)
    {
        linearLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        textView2.setText(teacherList.get(i).name);
        teacher_id=teacherList.get(i).id;
    }

    class MyAdapter extends BaseAdapter {
        Context context;
        MyAdapter(Context context)
        {
            this.context=context;
        }

        @Override
        public int getCount() {
            if(subjectList==null)
                return 0;
            return subjectList.size();
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
                view= LayoutInflater.from(SubjectActivity.this).inflate(R.layout.row1,viewGroup,false);
            }
            ((TextView)view.findViewById(R.id.s_no)).setText(""+(i+1));
            ((TextView)view.findViewById(R.id.subject_name)).setText(subjectList.get(i).name);
            ((TextView)view.findViewById(R.id.teacher_name)).setText(hashMap.get(subjectList.get(i).teacher_id));
            //textView.setTextColor(Color.BLACK);
            return view;
        }
    }
}