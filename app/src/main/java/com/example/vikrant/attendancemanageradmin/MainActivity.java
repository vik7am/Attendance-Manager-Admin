package com.example.vikrant.attendancemanageradmin;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    EditText editText;
    String name,userId;
    DatabaseReference databaseReference;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=new EditText(getApplicationContext());
        editText.setTextColor(Color.BLACK);
        //init();
    }

    public void getUserName()
    {
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Name");
        builder.setView(editText);
        builder.setPositiveButton("Ok", this);
        builder.create();
        builder.show();
    }

    public void students(View view)
    {
        startActivity(new Intent(getApplicationContext(),StudentActivity.class));
    }
    public void teachers(View view)
    {

    }

    public void init()
    {
        databaseReference=FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child("1").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Toast.makeText(getApplicationContext(),"Welcome:"+user.username,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }

    private void writeNewUser(String userId, String name) {
        User user = new User(name);
        databaseReference.child("users").child(userId).setValue(user);
        Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            //getUserName();
            //Toast.makeText(getApplicationContext(),"Welcome",Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        name=editText.getText().toString().toLowerCase();
        userId = databaseReference.push().getKey();
        writeNewUser(userId,name);
        //((ViewGroup)editText.getParent()).removeView(editText);
    }
}
