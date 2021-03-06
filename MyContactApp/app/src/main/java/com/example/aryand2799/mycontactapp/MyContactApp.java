package com.example.aryand2799.mycontactapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MyContactApp extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editPhone;
    EditText editAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_contact_app);

        editName = findViewById(R.id.editText_name);
        editPhone = findViewById(R.id.editText_phone);
        editAddress = findViewById(R.id.editText_address);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated myDb");

    }

    public void addData(View view) {
        Log.d("MyContactApp", "MainActivity: Add contact button pressed");

        boolean isInserted = myDb.insertData(editName.getText().toString(), editPhone.getText().toString(), editAddress.getText().toString());
        if(isInserted == true) {
            Toast.makeText(MyContactApp.this, "Success - contact inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MyContactApp.this, "FAILED - contact not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void viewData(View view) {
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity: view Data: received cursor");

        if(res.getCount() == 0) {
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            // Append res column 0,1,2,3 to the buffer - see StringBuffer and Cursor APIs
            // Delimit each of the "appends" with line feed "\n"
            buffer.append("Name: " + res.getString(1) + " \n");
            buffer.append("Phone number: " + res.getString(2) + " \n");
            buffer.append("Address: " + res.getString(3) + " \n");
            buffer.append("\n");
        }

        showMessage("Data", buffer.toString());
    }

    private void showMessage(String title, String message) {
        Log.d("MyContactApp", "MainActivity: showMessage: assembling AlertDialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static final String EXTRA_MESSAGE = "com.example.aryand2799.mycontactapp.MESSAGE";
    public void searchRecord(View view) {
        Log.d("MyContactApp", "MainActivity: launching SearchActivity");
        Intent intent = new Intent(this, SearchActivity.class);
        String name = editName.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, name);
        Cursor res = myDb.getAllData();

        if(res.getCount() == 0) {
            showMessage("Error", "No data found in database");
            return;
        }

        StringBuffer buffer = new StringBuffer();
        boolean contactFound = false;
        while (res.moveToNext()) {
            if (res.getString(1).equals(name)) {
                buffer.append("Name: " + res.getString(1) + " \n");
                buffer.append("Phone number: " + res.getString(2) + " \n");
                buffer.append("Address: " + res.getString(3) + " \n");
                buffer.append("\n");
                contactFound = true;
            }
        }

        if (contactFound == false) {
            showMessage("Error", "No contact with name " + name + " found in database.");
        } else {
            intent.putExtra(EXTRA_MESSAGE, buffer.toString());
            startActivity(intent);
        }
    }
}
