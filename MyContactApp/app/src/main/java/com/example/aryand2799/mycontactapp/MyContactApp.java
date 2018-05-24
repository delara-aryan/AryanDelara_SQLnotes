package com.example.aryand2799.mycontactapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MyContactApp extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact_app);

        editName = findViewById(R.id.editText_name);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated myDb");

    }

    public void addData(View view) {
        Log.d("MyContactApp", "MainActivity: Add contact button pressed");

        boolean isInserted = myDb.insertData(editName.getText().toString());
        if(isInserted == true) {
            Toast.makeText(MyContactApp.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MyContactApp.this, "FAILED - contact not inserted", Toast.LENGTH_LONG).show();
        }
    }
}
