package com.raju.attendencetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.Calendar;

public class Manage_Leave extends AppCompatActivity {

    SqllitDatabase mydb;
    SeekBar seekBar;
    ProgressBar progressBar;
    TextView progresstext;
    RadioGroup radioGroup;
    Button submit;
    String leave_type="nothing";
    RadioButton radioButton;
    int no_of_day=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage__leave);
        seekBar=findViewById(R.id.seekbar);
       // progressBar=findViewById(R.id.progressbar);
        radioGroup=findViewById(R.id.leave_group);
        submit=findViewById(R.id.submit);
        progresstext=findViewById(R.id.progresstext);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //progressBar.setProgress(progress);
                progresstext.setText("" + progress/12 +"");
                no_of_day=progress/12;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mydb=new SqllitDatabase(this);
        Add_leave();
    }
    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        leave_type=radioButton.getText().toString();
    }
    public void Add_leave(){
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(leave_type.equals("nothing")){
                    StyleableToast.makeText(Manage_Leave.this,"Please select leave type",R.style.exampleToast).show();
                }
               else if(no_of_day==0){
                    StyleableToast.makeText(Manage_Leave.this,"Please select at least one day to leave",R.style.exampleToast).show();
                }
                else {
                    if (check()) {
                        Calendar calendar = Calendar.getInstance();
                        int YEAR = calendar.get(Calendar.YEAR);
                        int MONTH = calendar.get(Calendar.MONTH)+1;
                        int DATE = calendar.get(Calendar.DATE);
                        String str = DATE + "/" + MONTH + "/" + YEAR;
                        if (mydb.checkleave(str)) {
                            Cursor cursor=mydb.allenrolledsubject();
                            if(cursor.getCount()>0) {
                                if (mydb.addleave(str, no_of_day, leave_type)) {
                                    StyleableToast.makeText(Manage_Leave.this, "Leave Added Successfully...", R.style.exampleToast).show();
                                } else {
                                    StyleableToast.makeText(Manage_Leave.this, "Leave not added", R.style.exampleToast).show();
                                }
                            }
                            else{
                                StyleableToast.makeText(Manage_Leave.this, "You don't have any enrolled subject", R.style.exampleToast).show();
                            }
                        } else{
                            StyleableToast.makeText(Manage_Leave.this,"Aleary taken leave on this date",R.style.exampleToast).show();
                        }

                    }
                    else{
                        StyleableToast.makeText(Manage_Leave.this,"You cann't Take More than 8 days leave",R.style.exampleToast).show();
                    }
                }
            }
        });
    }
    public boolean check(){
        Cursor cursor=mydb.LeaveAllData();
        int sum=0;
        while(cursor.moveToNext()){
            sum=sum + Integer.parseInt(cursor.getString(1));
        }
        if(sum>=8)
            return false;
        return true;
    }
}
