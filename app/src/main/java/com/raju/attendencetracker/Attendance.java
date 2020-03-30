package com.raju.attendencetracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.time.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Attendance extends AppCompatActivity {
    Button date_btn;
    Button present_btn;
    Button cancel_btn;
    Button absent_btn;
    SqllitDatabase mydb;
    List<String> list;
    Spinner spinner;
    String currdate="nothing";
    int choose=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        date_btn=findViewById(R.id.choosedata);
        mydb=new SqllitDatabase(this);
        spinner=findViewById(R.id.mark_spinner);
        present_btn=findViewById(R.id.present_btn);
        cancel_btn=findViewById(R.id.cancel_btn);
        absent_btn=findViewById(R.id.absent_btn);
        date_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerdatebtn();
            }
        });
        Cursor cursor=mydb.subgetAllData();
        list=new ArrayList<String>();
        list.add("Select Subject");
        while(cursor.moveToNext()){
            list.add(cursor.getString(1));
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choose=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mark_attendance();
    }
    public void handlerdatebtn(){
        Calendar calendar=Calendar.getInstance();
        final int YEAR=calendar.get(Calendar.YEAR);
        final int MONTH=calendar.get(Calendar.MONTH);
        final int DATE=calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                Calendar calendar=Calendar.getInstance();
                int mon=MONTH+1;
                String st = DATE + "/" +mon+ "/" +YEAR;
                //StyleableToast.makeText(Attendance.this,st,R.style.exampleToast).show();
                int MONTH=calendar.get(Calendar.MONTH)+1;
                 if(year!=2020 || !(MONTH<=month+1 && month+1<=MONTH+6)) {
                     month=month+1;
                     String str=dayOfMonth+ "/" +month+ "/" +year;
                     date_btn.setText(str);
                     currdate = "Not Valid Date";
                 }
                 else{
                     month=month+1;
                     String str=dayOfMonth+ "/" +month+ "/" +year;
                     if(!str.equals(st)){
                         currdate = "nothing";
                         date_btn.setText("Choose date");
                         StyleableToast.makeText(Attendance.this,"Choose only today date",R.style.exampleToast).show();
                     }
                     else{
                          currdate=str;
                          date_btn.setText(str);
                     }

                 }
            }
        },YEAR,MONTH,DATE);
        datePickerDialog.show();
    }
    public void mark_attendance(){
          present_btn.setOnClickListener(new View.OnClickListener() {
              @RequiresApi(api = Build.VERSION_CODES.O)
              @Override
              public void onClick(View v) {
                  if(currdate.equals("nothing"))
                      StyleableToast.makeText(Attendance.this,"Please Choose Date For Mark Attendance...",R.style.exampleToast).show();
                  else if(currdate.equals("Not Valid Date"))
                      StyleableToast.makeText(Attendance.this,"Date Not Valid...",R.style.exampleToast).show();
                  else if(choose==0 && list.size()==1)
                      StyleableToast.makeText(Attendance.this,"You must enroll at least one subject",R.style.exampleToast).show();
                  else if(choose==0)
                      StyleableToast.makeText(Attendance.this,"Please Choose Subject for Mark Attendance...",R.style.exampleToast).show();
                  else{
                           String mark_subject=list.get(choose);
                           String mark_date=currdate;
                           if(mydb.checkattendance(mark_subject,mark_date)){
                               boolean ch=mydb.mark_attendance(mark_subject,mark_date,"present");
                               if(ch) {
                                   StyleableToast.makeText(Attendance.this, "Attendance Added Successfully...", R.style.exampleToast).show();
                                   Intent nextactivity = new Intent(Attendance.this, student_dashboard.class);
                                   startActivity(nextactivity);
                                   finish();
                               }
                               else {
                                   StyleableToast.makeText(Attendance.this,"Attendance Not Added",R.style.exampleToast).show();
                               }
                           }
                           else
                               StyleableToast.makeText(Attendance.this,"Attendance Already Marked...",R.style.exampleToast).show();
                  }

              }
          });

          cancel_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(currdate.equals("nothing"))
                      StyleableToast.makeText(Attendance.this,"Please Choose Date For Mark Attendance...",R.style.exampleToast).show();
                  else if(choose==0 && list.size()==1)
                      StyleableToast.makeText(Attendance.this,"You must enroll at least one subject",R.style.exampleToast).show();
                  else if(choose==0)
                      StyleableToast.makeText(Attendance.this,"Please Choose Subject for Mark Attendance...",R.style.exampleToast).show();
                  else{
                         String mark_subject=list.get(choose);
                         String mark_date=currdate;
                         if(mydb.checkattendance(mark_subject,mark_date)){
                             boolean ch=mydb.mark_attendance(mark_subject,mark_date,"cencel");
                            if(ch)
                                StyleableToast.makeText(Attendance.this,"Attendance canceled Successfully...",R.style.exampleToast).show();
                            else {
                                StyleableToast.makeText(Attendance.this,"Attendance Not canceled...",R.style.exampleToast).show();
                            }
                          }
                        else
                             StyleableToast.makeText(Attendance.this,"Attendance Already Marked...",R.style.exampleToast).show();
                  }
              }
          });

          absent_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(currdate.equals("nothing"))
                      StyleableToast.makeText(Attendance.this,"Please Choose Date For Mark Attendance...",R.style.exampleToast).show();
                  else if(choose==0 && list.size()==1)
                      StyleableToast.makeText(Attendance.this,"You must enroll at least one subject",R.style.exampleToast).show();
                  else if(choose==0)
                      StyleableToast.makeText(Attendance.this,"Please Choose Subject for Mark Attendance...",R.style.exampleToast).show();
                  else{
                         String mark_subject=list.get(choose);
                         String mark_date=currdate;
                        if(mydb.checkattendance(mark_subject,mark_date)){
                           boolean ch=mydb.mark_attendance(mark_subject,mark_date,"absent");
                           if(ch)
                               StyleableToast.makeText(Attendance.this,"Absent Attendance Marked Successfully...",R.style.exampleToast).show();
                           else {
                               StyleableToast.makeText(Attendance.this,"Absent Attendance Not Marked...",R.style.exampleToast).show();
                          }
                       }
                      else
                            StyleableToast.makeText(Attendance.this,"Attendance Already Marked...",R.style.exampleToast).show();
                  }
              }
          });
    }
    public ArrayList<Integer> current_and_projected(String subject,SqllitDatabase mydb1){

       ArrayList<Integer> arr=mydb1.subgetAllAttendance(subject);
        Cursor cursor=mydb1.LeaveAllData();
        int sum=0;
        while(cursor.moveToNext()){
            sum=sum + Integer.parseInt(cursor.getString(1));
        }
        arr.add(sum);
        int present=arr.get(0);
        int absent=arr.get(1);
        int cancel=arr.get(2);
        int total=arr.get(3);
        int leave =arr.get(4);
        int x=40-cancel;
        if(total-cancel+leave>0)
            x=(40-cancel)/(total-cancel+leave);
        int estimate=((x*present)*100)/(40-cancel);
        int percent=100;
        if(total-cancel+leave>0)
            percent=(present*100)/(total-cancel+leave);
        ArrayList<Integer> ar1=new ArrayList<>();
        ar1.add(percent);
        ar1.add(estimate);
        return ar1;
    }
    public ArrayList<Integer> View_Attendance(String subject, SqllitDatabase mydb1){
        ArrayList<Integer> arr=mydb1.subgetAllAttendance(subject);
        return arr;
    }
    public boolean modify_attendance(String sub,String date,String type,SqllitDatabase mydb1){
        return mydb1.updateData(sub,date,type);
    }

}
