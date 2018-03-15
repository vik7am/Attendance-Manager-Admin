package com.example.vikrant.attendancemanageradmin.admin;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.vikrant.attendancemanageradmin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SubjectActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        init();
    }

    ListView listView;
    DatabaseReference databaseReference,databaseReference2;
    MyAdapter adapter;
    String name,userId;
    EditText editText;
    TextView textView;
    LinearLayout linearLayout;
    Subject subject;
    Teacher teacher;
    ArrayList<String> nameList;
    ArrayList<String> teacherNameList;

    public void init()
    {
        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference2= FirebaseDatabase.getInstance().getReference();
        listView=findViewById(R.id.listView1);
        editText=findViewById(R.id.editText1);
        linearLayout=findViewById(R.id.linearLayout1);
        adapter=new MyAdapter(getApplicationContext());
        listView.setAdapter(adapter);
        nameList=new ArrayList<String>();
        teacherNameList=new ArrayList<String>();
        initGlide();
    }
    public void initGlide()
    {
        databaseReference.child("subject").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    subject=dataSnapshot1.getValue(Subject.class);
                    nameList.add(subject.name);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

        databaseReference.child("teacher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacherNameList.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    teacher=dataSnapshot1.getValue(Teacher.class);
                    teacherNameList.add(teacher.name);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    public void addData(View view)
    {
        linearLayout.setVisibility(View.VISIBLE);

    }

    public void selectTeacher(View view)
    {

        int length=teacherNameList.size();
        int i=0;
        String list[]=new String[length];
        for(String text:teacherNameList)
            list[i++]=text;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setItems(list,this);
        builder.show();
    }
    public void uploadData(View view)
    {
        linearLayout.setVisibility(View.GONE);
        name=editText.getText().toString().toLowerCase();
        userId = databaseReference.push().getKey();
        subject = new Subject(name);
        databaseReference.child("subject").child(userId).setValue(subject);
    }
    public void cancelUpload(View view)
    {
        linearLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        Toast.makeText(this,""+i,Toast.LENGTH_SHORT).show();
    }

    class MyAdapter extends BaseAdapter {
        Context context;
        MyAdapter(Context context)
        {
            this.context=context;
        }

        @Override
        public int getCount() {
            if(nameList==null)
                return 0;
            return nameList.size();
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
            textView=(TextView)view.findViewById(android.R.id.text1);
            textView.setText(nameList.get(i));
            textView.setTextColor(Color.BLACK);
            return view;
        }
    }
}
