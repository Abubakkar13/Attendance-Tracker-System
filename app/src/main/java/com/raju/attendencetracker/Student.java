package com.raju.attendencetracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

public class Student extends AppCompatActivity {
    SqllitDatabase mydb;
    EditText student_name;
    EditText student_roll;
    EditText student_sem;
    Button next_btn;
    Button getall_btn;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        mydb=new SqllitDatabase(this);
        student_name=(EditText)findViewById(R.id.name);
        student_roll=(EditText)findViewById(R.id.roll);
        student_sem=(EditText)findViewById(R.id.semester);
        next_btn=(Button)findViewById(R.id.next);
         Adddata();

    }
    public void Adddata(){
        next_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      if(valid()){
                        boolean check = mydb.insert(student_roll.getText().toString(),student_name.getText().toString(),student_sem.getText().toString());
                        if(check==true) {
                            StyleableToast.makeText(Student.this,"Student Added Successfully...",R.style.exampleToast).show();
                            Intent nextactivity = new Intent(Student.this, student_dashboard.class);
                            startActivity(nextactivity);
                            finish();
                        }
                        else
                            StyleableToast.makeText(Student.this,"Student not added",R.style.exampleToast).show();
                      }
                      else
                          StyleableToast.makeText(Student.this,"Please fill all the field",R.style.exampleToast).show();
                    }
                }
        );
    }
    public boolean valid(){
        if(student_name.getText().toString().isEmpty())
            return false;
        else if(student_sem.getText().toString().isEmpty())
            return false;
        else if(student_roll.getText().toString().isEmpty())
            return false;
        return true;
    }

}
