package com.raju.attendencetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class student_dashboard extends AppCompatActivity {
    ImageButton addSubject;
    ImageButton markattendance;
    ImageButton viewattendance;
    ImageButton viewattendance1;
    ImageButton manage_leave;
    ImageButton modify_attendance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);
        addSubject=(ImageButton)findViewById(R.id.addsubject);
        markattendance=findViewById(R.id.mark_attendance);
        viewattendance=findViewById(R.id.view_atndnce);
        viewattendance1=findViewById(R.id.view_atndnce1);
        manage_leave=findViewById(R.id.manage_leave);
        modify_attendance=findViewById(R.id.modify);
        AddSubject();
        Markattendance();
        View_Attendance_percent();
        View_Attendance();
        ManageLeave();
        Modify();
    }
   public void AddSubject(){
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextactivity = new Intent(student_dashboard.this,Subject.class);
                startActivity(nextactivity);
            }
        });
   }
   public void Markattendance(){
        markattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextactivity = new Intent(student_dashboard.this,Attendance.class);
                startActivity(nextactivity);
            }
        });
   }
   public void View_Attendance_percent(){
        viewattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextactivity = new Intent(student_dashboard.this,view_att_helper.class);
                startActivity(nextactivity);
            }
        });
   }
   public void View_Attendance(){
        viewattendance1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextactivity = new Intent(student_dashboard.this,view_att_helper1.class);
                startActivity(nextactivity);
            }
        });
   }
   public void ManageLeave(){
        manage_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextactivity = new Intent(student_dashboard.this,Manage_Leave.class);
                startActivity(nextactivity);
            }
        });
   }
   public void Modify(){
        modify_attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextactivity = new Intent(student_dashboard.this,Modify_helper.class);
                startActivity(nextactivity);
            }
        });
   }
}
