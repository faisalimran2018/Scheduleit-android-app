package com.example.malik.tablayoutexample;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by MALIK on 12/5/2017.
 */

public class CustomListView extends BaseAdapter {

    private ArrayList<TimeSlot> timeArray;
    private Activity activity;


    public CustomListView(ArrayList<TimeSlot> timeArray, Activity context){
        this.timeArray = timeArray;
        activity = context;
    }
    @Override
    public int getCount() {
        return timeArray.size();
    }

    @Override
    public Object getItem(int i) {
        return timeArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        //return null;
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_list, null);

        TextView textType = view.findViewById(R.id.typeDes);
        final TextView textTime = view.findViewById(R.id.timeDes);
        ImageButton deleteButton =view.findViewById(R.id.delete);

        //textNumber.setText(i);
        textType.setText(timeArray.get(i).type);
        textTime.setText(timeArray.get(i).startTime+ "----" +timeArray.get(i).endTime);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Dialog dialog = new Dialog(activity);
//                dialog.setTitle("Delete Alert");
//                TextView textView = new TextView(activity);
//                textView.setText("Are you sure, you want to delete this !");
//                dialog.setContentView(textView);
//                dialog.show();

                AlertDialog alertDialog = new AlertDialog.Builder(
                        activity).create();

                // Setting Dialog Title
                //alertDialog.setTitle("Delete Alert !");

                // Setting Dialog Message
                alertDialog.setMessage("Do you want to delete this slot?");

                // Setting Icon to Dialog
                //alertDialog.setIcon(R.drawable.delete);

                // Setting OK Button
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String day = timeArray.get(i).day;
                        String startTime = timeArray.get(i).startTime;
                        String endTime = timeArray.get(i).endTime;
                        String type = timeArray.get(i).type;
                        //(day+":"+startTime+":"+endTime+":"+type);
                        timeArray.remove(i);
                        notifyDataSetChanged();
                        Thread thread = new Thread(new deleteTimeSlot(day,startTime,endTime,type));
                        thread.start();
                        Toast.makeText(activity,"Successfully Deleted",Toast.LENGTH_LONG).show();
                    }
                });

                alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(activity,"Didn't Deleted",Toast.LENGTH_LONG).show();
                    }
                });


                // Showing Alert Message
                alertDialog.show();










            }
        });
        return view;

    }


    public class deleteTimeSlot implements Runnable{
        private Database database;
        private String day,startTime,endTime,type;

        public deleteTimeSlot(String day,String startTime,String endTime,String type){
            this.day = day;
            this.startTime=startTime;
            this.endTime =endTime;
            this.type = type;
        }

        @Override
        public void run() {
            database = new Database(activity.getApplicationContext(), null, null, 1);
            database.delete(day,startTime,endTime,type);
        }
    }


}
