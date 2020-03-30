package com.raju.attendencetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class view_att_helper1 extends AppCompatActivity {

    Spinner spinner;
    Button next;
    TextView attend_class;
    TextView miss_class;
    TextView cancel_class;
    TextView lecture_conduct;
    TextView take_leave;
    SqllitDatabase mydb;
    List<String> list;
    int choose=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_att_helper1);
        spinner=findViewById(R.id.view_select1);
        next=findViewById(R.id.view_btn1);
        lecture_conduct=findViewById(R.id.lecture_conduct);
        attend_class=findViewById(R.id.lecture_attend);
        take_leave=findViewById(R.id.taken_leave);
        miss_class=findViewById(R.id.lecture_missed);
        cancel_class=findViewById(R.id.lecture_canceled);
        mydb=new SqllitDatabase(this);
        list=new ArrayList<String>();
        list.add("Select Subject");
        Cursor cursor=mydb.subgetAllData();
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
        helper();
    }
    public void helper(){
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choose==0 && list.size()==1)
                    StyleableToast.makeText(view_att_helper1.this,"You must enroll at least one subject",R.style.exampleToast).show();
                else if(choose==0)
                    StyleableToast.makeText(view_att_helper1.this,"Select Subject",R.style.exampleToast).show();
                else{
                    Attendance obj=new Attendance();
                    List<Integer> arr =obj.View_Attendance(list.get(choose),mydb);
                    Cursor cursor=mydb.LeaveAllData();
                    int sum=0;
                    while(cursor.moveToNext()){
                        sum=sum + Integer.parseInt(cursor.getString(1));
                    }
                    int attend=arr.get(0);
                    int absent=arr.get(1);
                    int cancel=arr.get(2);
                    int total=arr.get(3)+sum;
                    int leave=sum;
                    String str1="Lecture Cunducted : " + total;
                    String str2="Lecture Attendend : " + attend;
                    String str3="Taken Leave : " + leave;
                    String str4="Missed Lectured : " + absent;
                    String str5="Canceled Lecture : " + cancel;
                    lecture_conduct.setText(str1);
                    attend_class.setText(str2);
                    take_leave.setText(str3);
                    miss_class.setText(str4);
                    cancel_class.setText(str5);
                    lecture_conduct.setTextColor(getResources().getColor(R.color.colorgreen));
                    attend_class.setTextColor(getResources().getColor(R.color.colorgreen));
                    take_leave.setTextColor(getResources().getColor(R.color.colorgreen));
                    miss_class.setTextColor(getResources().getColor(R.color.colorgreen));
                    cancel_class.setTextColor(getResources().getColor(R.color.colorgreen));
                }
            }
        });
    }
}
