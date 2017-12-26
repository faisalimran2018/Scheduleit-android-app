package com.example.malik.tablayoutexample;

/**
 * Created by MALIK on 12/5/2017.
 */

public class TimeSlot {
    public String day;
    public String startTime;
    public String endTime;
    public String type;
    public boolean serviceRunning;


    public TimeSlot(String day, String startTime, String endTime,String type){
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
    }


}
