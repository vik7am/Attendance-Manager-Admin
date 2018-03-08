package com.example.vikrant.attendancemanageradmin;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class StudentActivity extends AppCompatActivity {

    ListView listView;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    String name,userId;
    EditText editText;
    TextView textView;
    LinearLayout linearLayout;
    User user;
    ArrayList<String> userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        init();
    }

    public void init()
    {
        databaseReference= FirebaseDatabase.getInstance().getReference();
        listView=(ListView)findViewById(R.id.listView1);
        editText=(EditText)findViewById(R.id.editText1);
        linearLayout=(LinearLayout)findViewById(R.id.linearLayout1);
        adapter=new MyAdapter(getApplicationContext());
        listView.setAdapter(adapter);
        userName=new ArrayList<String>();
        initGlide();
    }
    public void initGlide()
    {
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userName.clear();
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    user=dataSnapshot1.getValue(User.class);
                    userName.add(user.username);
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
    public void uploadData(View view)
    {
        linearLayout.setVisibility(View.GONE);
        name=editText.getText().toString().toLowerCase();
        userId = databaseReference.push().getKey();
        User user = new User(name);
        databaseReference.child("users").child(userId).setValue(user);
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
            if(userName==null)
                return 0;
            return userName.size();
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
            textView=(TextView)view.findViewById(android.R.id.text1);
            textView.setText(userName.get(i));
            textView.setTextColor(Color.BLACK);
            return view;
        }
    }
}
