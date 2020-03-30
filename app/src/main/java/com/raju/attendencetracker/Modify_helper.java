package com.raju.attendencetracker;


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
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Modify_helper extends AppCompatActivity {

    int flag=0;
    Spinner spinner;
    Button date;
    Button modify_btn;
    String currdate="nothing";
    TextView hidesub;
    TextView hide_marked;
    Button hide_edit;
    Button hide_present;
    Button hide_absent;
    Button hide_cancel;
    int choose=0;
    List<String> list;
    SqllitDatabase mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_helper);
        spinner=findViewById(R.id.modify_spinner);
        date=findViewById(R.id.modify_date);
        mydb=new SqllitDatabase(this);
        modify_btn=findViewById(R.id.modify_btn);
        hidesub=findViewById(R.id.hidesubject);
        hide_marked=findViewById(R.id.hide_mark);
        hide_edit=findViewById(R.id.edit_hide);
        hide_present=findViewById(R.id.present_hide);
        hide_absent=findViewById(R.id.absent_hide);
        hide_cancel=findViewById(R.id.cancel_hide);
        date.setOnClickListener(new View.OnClickListener() {
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
                hidesub.setVisibility(TextView.INVISIBLE);
                hide_marked.setVisibility(TextView.INVISIBLE);
                hide_edit.setVisibility(View.INVISIBLE);
                hide_present.setVisibility(View.INVISIBLE);
                hide_absent.setVisibility(View.INVISIBLE);
                hide_cancel.setVisibility(View.INVISIBLE);
                choose=position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        check();
    }
    public void handlerdatebtn(){
        Calendar calendar=Calendar.getInstance();
        final int YEAR=calendar.get(Calendar.YEAR);
        final int MONTH=calendar.get(Calendar.MONTH);
        final int DATE=calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth){
                hidesub.setVisibility(TextView.INVISIBLE);
                hide_marked.setVisibility(TextView.INVISIBLE);
                hide_edit.setVisibility(View.INVISIBLE);
                hide_present.setVisibility(View.INVISIBLE);
                hide_absent.setVisibility(View.INVISIBLE);
                hide_cancel.setVisibility(View.INVISIBLE);
                Calendar calendar=Calendar.getInstance();
               // int MONTH=calendar.get(Calendar.MONTH)+1;
                if(year!=2020) {
                    month=month+1;
                    String str=dayOfMonth+ "/" +month+ "/" +year;
                    date.setText(str);
                    currdate = "Not Valid Date";
                }
                else{
                    month = month + 1;
                    String str = dayOfMonth + "/" + month + "/" + year;
                    if((dayOfMonth> DATE && month>MONTH+1) || (month>MONTH+1))
                        currdate = "Not Valid Date";
                    else {
                        currdate = str;
                        String s=month + " " + MONTH ;
                       // Toast.makeText(Modify_helper.this,s, Toast.LENGTH_SHORT).show();
                    }
                    date.setText(str);
                }
            }
        },YEAR,MONTH,DATE);
        datePickerDialog.show();
    }
    public void check(){
        modify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currdate.equals("nothing")){
                    StyleableToast.makeText(Modify_helper.this,"Please select date",R.style.exampleToast).show();
                }
                else if(currdate.equals("Not Valid Date")){
                    StyleableToast.makeText(Modify_helper.this,"Not Valid Date",R.style.exampleToast).show();
                }
                else if(choose==0){
                    StyleableToast.makeText(Modify_helper.this,"Select subject",R.style.exampleToast).show();
                }
                else{
                    String sub=list.get(choose);
                    String date=currdate;
                    String type="Nothing";
                    boolean chk=mydb.checkattendance(sub,date);
                    if(chk){
                        flag=1;
                        hidesub.setText(sub);
                        hidesub.setTextColor(getResources().getColor(R.color.colorgreen));
                        hidesub.setVisibility(TextView.VISIBLE);
                        hide_marked.setText("Attendance Status: " + "No marked");
                        hide_marked.setTextColor(getResources().getColor(R.color.colorgreen));
                        hide_marked.setVisibility(TextView.VISIBLE);
                        hide_edit.setVisibility(View.VISIBLE);
                        hide_edit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                hide_present.setVisibility(View.VISIBLE);
                                hide_absent.setVisibility(View.VISIBLE);
                                hide_cancel.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                    else{
                        int present=0;
                        int absent=0;
                        int cancel=0;
                        Cursor cursor=mydb.getAttendamce(date,sub);
                        while(cursor.moveToNext()){
                            if(!cursor.isNull(2) && cursor.getString(2).toString().equals("1"))
                                present=1;
                            else if(!cursor.isNull(3) && cursor.getString(3).toString().equals("1"))
                                absent=1;
                            else if(!cursor.isNull(4) && cursor.getString(4).toString().equals("1"))
                                cancel=1;
                        }
                        if(present==1)
                            type="present";
                        if(absent==1)
                            type="absent";
                        if(cancel==1)
                            type="cancel";
                         hidesub.setText(sub);
                         hidesub.setTextColor(getResources().getColor(R.color.colorgreen));
                         hidesub.setVisibility(TextView.VISIBLE);
                         hide_marked.setText("Attendance Status: " + type);
                         hide_marked.setTextColor(getResources().getColor(R.color.colorgreen));
                         hide_marked.setVisibility(TextView.VISIBLE);
                         hide_edit.setVisibility(View.VISIBLE);
                         hide_edit.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 hide_present.setVisibility(View.VISIBLE);
                                 hide_absent.setVisibility(View.VISIBLE);
                                 hide_cancel.setVisibility(View.VISIBLE);
                             }
                         });
                    }
                }
            }
        });
          hide_present.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(flag==1){
                      boolean ch=mydb.mark_attendance(list.get(choose),currdate,"present");
                      if(ch) {
                          StyleableToast.makeText(Modify_helper.this, "Attendance Modified Successfully...", R.style.exampleToast).show();
                          Intent nextactivity = new Intent(Modify_helper.this, student_dashboard.class);
                          startActivity(nextactivity);
                          finish();
                        }
                        else {
                          StyleableToast.makeText(Modify_helper.this,"Attendance Not Modified",R.style.exampleToast).show();
                      }
                      flag=0;
                  }
                  else{
                     Attendance obj=new Attendance();
                     if(obj.modify_attendance(list.get(choose),currdate,"present",mydb)) {
                         StyleableToast.makeText(Modify_helper.this, "Attendance Modified Successfully...", R.style.exampleToast).show();
                         Intent nextactivity = new Intent(Modify_helper.this, student_dashboard.class);
                         startActivity(nextactivity);
                         finish();
                     }
                     else
                         StyleableToast.makeText(Modify_helper.this,"Attendance not modified",R.style.exampleToast).show();
                  }
              }
          });
          hide_absent.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (flag == 1) {
                      boolean ch=mydb.mark_attendance(list.get(choose),currdate,"absent");
                      if(ch) {
                          StyleableToast.makeText(Modify_helper.this, "Attendance Modified Successfully...", R.style.exampleToast).show();
                          Intent nextactivity = new Intent(Modify_helper.this, student_dashboard.class);
                          startActivity(nextactivity);
                          finish();
                      }
                      else {
                          StyleableToast.makeText(Modify_helper.this,"Attendance Not Modified",R.style.exampleToast).show();
                      }
                      flag=0;
                   }
                  else{
                      Attendance obj = new Attendance();
                      if (obj.modify_attendance(list.get(choose), currdate, "absent", mydb)) {
                          StyleableToast.makeText(Modify_helper.this, "Attendance Modified Successfully...", R.style.exampleToast).show();
                          Intent nextactivity = new Intent(Modify_helper.this, student_dashboard.class);
                          startActivity(nextactivity);
                          finish();
                      }
                      else
                          StyleableToast.makeText(Modify_helper.this, "Attendance not modified", R.style.exampleToast).show();
                  }
              }
          });
          hide_cancel.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (flag == 1) {
                      boolean ch = mydb.mark_attendance(list.get(choose), currdate, "cancel");
                      if (ch) {
                          StyleableToast.makeText(Modify_helper.this, "Attendance Modified Successfully...", R.style.exampleToast).show();
                          Intent nextactivity = new Intent(Modify_helper.this, student_dashboard.class);
                          startActivity(nextactivity);
                          finish();
                      }
                      else {
                          StyleableToast.makeText(Modify_helper.this, "Attendance Not Modified", R.style.exampleToast).show();
                      }
                      flag = 0;
                  }
                  else {
                       Attendance obj = new Attendance();
                       if (obj.modify_attendance(list.get(choose), currdate, "cencel", mydb)) {
                          StyleableToast.makeText(Modify_helper.this, "Attendance Modified Successfully...", R.style.exampleToast).show();
                          Intent nextactivity = new Intent(Modify_helper.this, student_dashboard.class);
                          startActivity(nextactivity);
                           finish();
                       }
                      else
                          StyleableToast.makeText(Modify_helper.this, "Modified not Successfully...", R.style.exampleToast).show();
                  }
              }
          });
    }
}
