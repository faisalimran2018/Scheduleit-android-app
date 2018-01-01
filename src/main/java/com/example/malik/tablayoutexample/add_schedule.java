package com.example.malik.tablayoutexample;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;

public class add_schedule extends AppCompatActivity implements

        AdapterView.OnItemSelectedListener {
    public static final String TAG = "TESTLOG";



    Spinner spinner1,spinner2;
    ArrayAdapter<CharSequence> arrayAdapter;
    TimePicker startTimePic,endTimePic;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent setIntent = new Intent(this,MainActivity.class);
        startActivity(setIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);




        spinner1 = (Spinner) findViewById(R.id.type);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.type,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter);


        spinner2 = (Spinner) findViewById(R.id.Day);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.days_of_week,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter);


        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(add_schedule.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startTimePic = (TimePicker) findViewById(R.id.TPStart);
                int hourStart = startTimePic.getCurrentHour();
                int minutesStart = startTimePic.getCurrentMinute();
                String AMPMStart = "AM";
                if(hourStart>11)
                {
                    hourStart = hourStart-12;
                    AMPMStart = "PM";
                }
                if(hourStart>12){
                    hourStart = hourStart-12;
                }
                if(hourStart==0){
                    hourStart=12;
                }


                //Log.i(TAG, "onCreate: add_Schedule "+hourStart +":" +minutesStart+":"+AMPMStart);
                endTimePic = (TimePicker) findViewById(R.id.TPSEnd);
                int hourEnd = endTimePic.getCurrentHour();
                int minutesEnd = endTimePic.getCurrentMinute();
                String AMPMEnd = "AM";
                if(hourEnd>11)
                {
                    hourEnd = hourEnd-12;
                    AMPMEnd = "PM";
                }
                if(hourEnd>12){
                    hourEnd = hourEnd-12;
                }
                if(hourEnd==0){
                    hourEnd=12;
                }

                Spinner type = (Spinner)findViewById(R.id.type);
                Spinner day = (Spinner) findViewById(R.id.Day);
                String typeText = type.getSelectedItem().toString();
                String dayText = day.getSelectedItem().toString();


                String startTime = Integer.toString(hourStart) + ":"
                        + Integer.toString(minutesStart) + ":"
                        +AMPMStart;

                Log.i(TAG, "onClick: Start"+startTime);

                String endTime = Integer.toString(hourEnd) + ":"
                        + Integer.toString(minutesEnd) + ":"
                        +AMPMEnd;
                Log.i(TAG, "onClick: End"+endTime);


                Thread thread = new Thread(new addTimeSlot(dayText,startTime,endTime,typeText));
                thread.start();
//                customListView.notifyDataSetChanged();
//                notifyDataSetChanged();
                Intent intent = new Intent(add_schedule.this,MainActivity.class);
                startActivity(intent);
                finish();
                Toast.makeText(getApplicationContext(),"Successfully Added",Toast.LENGTH_LONG).show();


            }
        });



    }


    public class addTimeSlot implements Runnable{
        public Database database;

        public String day;
        public String startTime;
        public String endTime;
        public String type;




        public addTimeSlot(String day, String startTime, String endTime, String type){
            this.day = day;
            this.startTime = startTime;
            this.endTime = endTime;
            this.type = type;

        }

        @Override
        public void run() {
               database = new Database(getApplicationContext(),null,null,1);
               database.addTimeSlot(day,startTime,endTime,type);
        }
    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        String item = adapterView.getItemAtPosition(i).toString();
//        Toast.makeText(this, item, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
