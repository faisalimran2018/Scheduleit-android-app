package com.example.malik.tablayoutexample;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executor;

import static java.lang.Thread.sleep;

/**
 * Created by MALIK on 12/14/2017.
 */

public class SilentSchedule extends IntentService {
    public static final String TAG = "TESTLOG";
    public static  int savedTime =12;
    AudioManager am;
    ArrayList<Integer> startHour;
    ArrayList<Integer> startMin;
    ArrayList<Integer> starttAMPM;

    ArrayList<Integer> endHour;
    ArrayList<Integer> endMin;
    ArrayList<Integer> endtAMPM;


    ArrayList<TimeSlot> dayData;
    ArrayList<TimeSlot> alreadyInService;
    Database database;
    Calendar calendar;
    SimpleDateFormat df;

    public String indexOfRunningSchedue;
    public  boolean serviceStoper =false;

    public SilentSchedule(String name) {
        super(name);
    }
    public SilentSchedule() {
        super("SilentSchedule");
    }

//
//    public class timeslotRunner extends AsyncTask<Void, Void, Void>{
//
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            String getType = dayData.get(Integer.parseInt(indexOfRunningSchedue)).type;
//            String getEndTime = dayData.get(Integer.parseInt(indexOfRunningSchedue)).endTime;
//
//            Intent intent = new Intent(getBaseContext(),silentSlot.class);
//            intent.putExtra("Type",getType);
//            intent.putExtra("EndTime",getEndTime);
//
//            startService(intent);
//
//            return null;
//        }
//    }



//    public SilentSchedule(){}
    public void loadData(String day){
        database = new Database(getApplicationContext(),null,null,1);
        dayData = database.getDayData(day);
    }

    public String inStartTime(String hour,String minutes,String AMPM){
        if (hour.equals("00")){
            Log.i(TAG, "inStartTime: Hour is 00");
            hour = "12";
        }
        int currentHour = Integer.parseInt(hour);
        int currentMin = Integer.parseInt(minutes);
        if(currentHour>12){
            currentHour = currentHour-12;
        }

        Log.i(TAG, "inStartTime: Current Hour"+ hour);
        Log.i(TAG, "inStartTime: "+dayData.size());


        for(int i=0;i<dayData.size();i++){
            String hourDatabase =  dayData.get(i).startTime;
            String endHourDatabase = dayData.get(i).endTime;
            Log.i(TAG, "inStartTime: Database Time "+hourDatabase);
            String[] DatabaseTime = hourDatabase.split(":");
            String[] DatbaseTimeEnd = endHourDatabase.split(":");
            int databaseHour = Integer.parseInt(DatabaseTime[0]);
            int databaseMinute = Integer.parseInt(DatabaseTime[1]);
            int databaseEndMinutes = Integer.parseInt(DatbaseTimeEnd[1]);
            String datbaseAMPM = DatabaseTime[2];


            if(datbaseAMPM.equals(AMPM) && (currentHour==databaseHour) && (currentMin>=databaseMinute && currentMin<=databaseEndMinutes)){
                Log.i(TAG, "inStartTime: Time Matched");
                return Integer.toString(i);
            }

        }
        return "Not found";
    }

    public String currentDay(){
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        switch (currentDay) {
            case Calendar.SUNDAY:
                return "Sunday";
            case Calendar.MONDAY:
                return "Monday";
            case Calendar.TUESDAY:
               return "Tuesday";
            case Calendar.THURSDAY:
                return "Thursday";
            case Calendar.FRIDAY:
                return "Friday";
            case Calendar.SATURDAY:
               return "Saturday";
            case Calendar.WEDNESDAY:
                return "Wednesday";
            default:
                return null;
        }
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if(01==1){
            Log.i(TAG, "onHandleIntent: Ture 1 01");
        }
        Log.i(TAG, "onHandleIntent: Method called");

//        Toast.makeText(getApplicationContext(), "Started Service", Toast.LENGTH_SHORT).show();


        while (true) {
            Log.i(TAG, "onHandleIntent: Within while");

            //Toast.makeText(SilentSchedule.this,"Started Sevice", Toast.LENGTH_SHORT).show();
            //return super.onStartCommand(intent, flags, startId);
            Log.i(TAG, "onStartCommand called");
            calendar = Calendar.getInstance();
            loadData(currentDay());

            //getting time
            df = new SimpleDateFormat("HH:mm:a");
            String currentTime = df.format(calendar.getTime());
            String[] splittedTime = currentTime.split(":");
            Log.i(TAG, "onHandleIntent: "+currentTime);

            String indexStartTime = inStartTime(splittedTime[0], splittedTime[1], splittedTime[2]);
            Log.i(TAG,"Time Matcher "+indexStartTime);
//            am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//            int currentRinger = am.getRingerMode();
            if (!indexStartTime.equals("Not found")) {
                Log.i(TAG, "onHandleIntent:"+"Silent");
                //am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
            else {
                //am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                Log.i(TAG, "onHandleIntent:"+"Normal");
            }

            try {
                sleep(30000);
                Toast.makeText(SilentSchedule.this, "After On Minute", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "After One Minute");
            } catch (Exception e) {
            }
            //stopService(intent);
            if(serviceStoper){
                break;
            }
        }
        //return Service.START_REDELIVER_INTENT;
    }


}
