package com.raju.attendencetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.List;


public class Subject extends AppCompatActivity {
    SqllitDatabase mydb;
    String sem;
    Spinner spinner;
    List<String> list;
    Button add_btn;
    Button rem_btn;
    int choose=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        mydb=new SqllitDatabase(this);
        spinner=findViewById(R.id.spinner_sub);
        add_btn=findViewById(R.id.add_subject_btn);
        rem_btn=findViewById(R.id.removesubject);
        Cursor cursor= mydb.getAllData();
               while(cursor.moveToNext()){
                   sem=cursor.getString(2);
               }
      if(sem.equals("One") || sem.equals("one") || sem.equals("ONE") || sem.equals("1")){
           AddSubject("one");
      }
      else if(sem.equals("Two") || sem.equals("two") || sem.equals("TWO") || sem.equals("2")){
          AddSubject("two");
      }
      else if(sem.equals("Three") || sem.equals("three") || sem.equals("THREE") || sem.equals("3")){
          AddSubject("three");
      }
      else if(sem.equals("Four") || sem.equals("four") || sem.equals("FOUR") || sem.equals("4")){
          AddSubject("four");
      }
      else if(sem.equals("Five") || sem.equals("five") || sem.equals("FIVE") || sem.equals("5")){
          AddSubject("five");
      }
      else if(sem.equals("Six") || sem.equals("six") || sem.equals("SIX") || sem.equals("6")){
          AddSubject("six");
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
        addsubject();
        removesubject();
    }
    public void AddSubject(String sub){
        if(sub.equals("one")){
            list=new ArrayList<String>();
            list.add("Select Subject");
            list.add("Logic Design (CS2001D)");
            list.add("Statical Method (MA6020D)");
            list.add("Proffessional Communitaional (MS1001D)");
            list.add("Introduction To Programming (CS2003D)");
            list.add("Discrete Mathematics (MA6223D)");
            list.add("programming laboratory (CS2029D)");
        }
        else if(sub.equals("two")){
            list=new ArrayList<String>();
            list.add("Select Subject");
            list.add("COMPUTER ORGANIZATIO (CS2004D)");
            list.add("DATA STRUCTURES AND ALGORITHMS (CS2005D)");
            list.add("Proffessional Communitaional (MS1001D)");
            list.add("GRAPH THEORY AND COMBINATORS (MA6224D)");
            list.add("FOUNDATION S OF PROGRAMMIN (CS4067D)");
            list.add("DATA STRUCTURES LABORATORY (CS2094D)");
            list.add("COMPUTER ORGANIZATION (CS2004)");
            list.add("WEB PROGRAMMING (CS4042D)");
        }
        else if(sub.equals("three")){
            list=new ArrayList<String>();
            list.add("Select Subject");
            list.add("DATABASE MANAGEMENT SYSTEMS (CS3002D)");
            list.add("OPERATING SYSTEMS (CS3003D)");
            list.add("OBJECT ORIENTED SYSTEMS (CS3007D)");
            list.add("OBJECT ORIENTED SYSTEMS LABORATORY (CS4097D)");
            list.add("NUMBER THEORY AND COMPUTING (CS4021D)");
            list.add("CLOUD COMPUTING (CS4037D)");
            list.add("DESIGN AND ANALYSIS OF ALGORITHM (CS4050D)");
            list.add("BIOINFORMATICS (CS4040D)");
            list.add("ARTIFICIAL INTELLIGENCE (CS4023D)");
            list.add("OPERATING SYSTEMS LABORATORY (CS3092D)");
        }
        else if(sub.equals("four")){
            list=new ArrayList<String>();
            list.add("Select Subject");
            list.add("COMPUTER NETWORKS (CS3006D)");
            list.add("DATA MINING (CS4038D)");
            list.add("MACHINE LEARNING (CS4044D)");
            list.add("SOFTWARE ENGINEERING (CS3004D)");
            list.add("SOFTWARE ENGINEERING LABORATORY (CS4096D)");
            list.add("ADVANCED DATABASE MANAGEMENT SYSTEMS (CS4036D)");
            list.add("COMPUTATIONAL INTELLIGENCE (CS4023)");
            list.add("INFORMATION THEORY (CS4024)");
            list.add("COMBINATIONAL ALGORITHMS (CS4026)");
            list.add("TOPICS IN ALGORITHMS (CS4027)");
            list.add("QUANTUM COMPUTATIONS (CS4028)");
            list.add("TOPICS IN THEORY OF COMPUTATION (CS4029)");
            list.add("COMPUTATIONAL COMPLEXITY (CS4030)");
            list.add("COMPUTATIONAL ALGEBRA (CS4031)");
            list.add("COMPUTER ARCHITECTURE (CS4032)");
            list.add("DISTRIBUTED COMPUTING (CS4033)");
        }
        else if(sub.equals("five")){
            list=new ArrayList<String>();
            list.add("Select Subject");
            list.add("Principles of Management (ME4104)");
            list.add("Principles of Programming Languages ( CS4022)");
            list.add("Computational Intelligence (CS4023)");
            list.add("Information Theory (CS4024)");
            list.add("Combinatorial Algorithms (CS4026)");
            list.add("Topics in Algorithms (CS4027)");
            list.add("Quantum Computation (CS4028)");
            list.add("Topics in Theory of Computation (CS4029)");
            list.add("Computational Complexity (CS4030)");
            list.add("Computational Algebra (CS4031)");
            list.add("Computer Architecture (CS4032)");
            list.add("Distributed Computing (CS4033)");
            list.add("Middleware Technologies (CS4034)");
            list.add("Computer Security (CS4035)");
            list.add("Advanced Database Management Systems (CS4036)");
        }
        else if(sub.equals("six")){
            list=new ArrayList<String>();
            list.add("Select Subject");
            list.add("Project (CS3099)");
        }
    }
    public void addsubject(){
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choose==0){
                    StyleableToast.makeText(Subject.this,"Select Anyone subject",R.style.exampleToast).show();
                }
                else{
                    String str=list.get(choose);
                    if(mydb.checksubject(str)){
                         boolean succ=mydb.subject_inserted(str);
                         if(succ){
                             String st=str +"Added Successfully...";
                             StyleableToast.makeText(Subject.this,st,R.style.exampleToast).show();
                         }
                         else{
                               String st=str +"Not Added";
                               StyleableToast.makeText(Subject.this,st,R.style.exampleToast).show();
                         }
                    }
                    else{
                        String st=str +"Alreay Added...";
                        StyleableToast.makeText(Subject.this,st,R.style.exampleToast).show();
                    }
                    Intent nextactivity = new Intent(Subject.this, student_dashboard.class);
                    startActivity(nextactivity);
                    finish();
                }
        }
        });
    }
    public void removesubject(){
          rem_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if(choose==0){
                      StyleableToast.makeText(Subject.this,"Select Anyone subject",R.style.exampleToast).show();
                  }
                  else{
                        String str=list.get(choose);
                        if(mydb.delchecksubject(str)){
                            if(mydb.delete_subject(str)){
                                if(mydb.deletesubject(str)){
                                String st=str + "Successfully Deleted...";
                                StyleableToast.makeText(Subject.this,st,R.style.exampleToast).show();
                                }
                                else{
                                    StyleableToast.makeText(Subject.this,"Attendance record Not deleted",R.style.exampleToast).show();
                                }
                            }
                            else{
                                String st=str + "Nots Deleted...";
                                StyleableToast.makeText(Subject.this,st,R.style.exampleToast).show();
                            }
                        }
                        else{
                            StyleableToast.makeText(Subject.this,"You Haven't Enrolled this Subject..",R.style.exampleToast).show();
                        }
                      Intent nextactivity = new Intent(Subject.this, student_dashboard.class);
                      startActivity(nextactivity);
                      finish();
                  }


              }
          });
    }
}
