package com.example.malik.tablayoutexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class add_schedule extends AppCompatActivity implements

        AdapterView.OnItemSelectedListener {
    public Activity activity;



    Spinner spinner1,spinner2,spinnerStartTime,spinnerStartMin,spinnerAMPM,spinnerEndTime,spinnerEndMin,endAMPM;
    ArrayAdapter<CharSequence> arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        spinner1 = (Spinner) findViewById(R.id.type);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.type,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner1.setAdapter(arrayAdapter);


        spinner2 = (Spinner) findViewById(R.id.Day);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.days_of_week,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(arrayAdapter);

        spinnerStartTime = (Spinner) findViewById(R.id.stime);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.hours,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerStartTime.setAdapter(arrayAdapter);


        spinnerStartMin = (Spinner) findViewById(R.id.sminutes);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.minutes,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerStartMin.setAdapter(arrayAdapter);



        spinnerAMPM = (Spinner) findViewById(R.id.am_pm);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.am_pm,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerAMPM.setAdapter(arrayAdapter);




        spinnerEndTime = (Spinner) findViewById(R.id.end_hour);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.hours,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerEndTime.setAdapter(arrayAdapter);


        spinnerEndMin = (Spinner) findViewById(R.id.end_min);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.minutes,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerEndMin.setAdapter(arrayAdapter);



        endAMPM = (Spinner) findViewById(R.id.end_ampm);
        arrayAdapter = ArrayAdapter.createFromResource(this,R.array.am_pm,android.R.layout.simple_spinner_dropdown_item);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        endAMPM.setAdapter(arrayAdapter);
        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(add_schedule.this,MainActivity.class);
                startActivity(intent);

            }
        });
        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Spinner type = (Spinner)findViewById(R.id.type);
                Spinner day = (Spinner) findViewById(R.id.Day);
                Spinner sHour = (Spinner) findViewById(R.id.stime);
                Spinner sMin = (Spinner) findViewById(R.id.sminutes);
                Spinner sAMPM = (Spinner) findViewById(R.id.am_pm);

                Spinner eHour = (Spinner) findViewById(R.id.end_hour);
                Spinner eMin = (Spinner) findViewById(R.id.end_min);
                Spinner eAMPM = (Spinner) findViewById(R.id.end_ampm);

                String typeText = type.getSelectedItem().toString();
                String dayText = day.getSelectedItem().toString();
                String startTime = sHour.getSelectedItem().toString() + ":"
                        + sMin.getSelectedItem().toString() + ":"
                        +sAMPM.getSelectedItem().toString();

                String endTime = eHour.getSelectedItem().toString() + ":"
                        + eMin.getSelectedItem().toString() + ":"
                        +eAMPM.getSelectedItem().toString();

                Thread thread = new Thread(new addTimeSlot(dayText,startTime,endTime,typeText));
                thread.start();
                //customListView.notifyDataSetChanged();
                //notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"After Addition",Toast.LENGTH_SHORT).show();


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
