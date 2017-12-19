package com.example.malik.tablayoutexample;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by MALIK on 11/13/2017.
 */

public class Monday extends Fragment {
        public Database database;
        public ArrayList<TimeSlot> mondayArray;
        public CustomListView customMonday;



        // Inner Class
        public class getMondayItems implements Runnable {

            @Override
            public void run() {
                database = new Database(getActivity(),null,null,1);
                mondayArray = database.getDayData("Monday");

            }
        }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_monday, container, false);

        Thread thread = new Thread(new getMondayItems());
        ListView listView = (ListView) view.findViewById(R.id.listViewMonday);
        database = new Database(getActivity(),null,null,1);
        mondayArray = database.getDayData("Monday");
//        mondayArray = new ArrayList<TimeSlot>();
//        mondayArray.add(new TimeSlot("Monday","1:10:AM","2:10:AM","Class"));

         customMonday = new CustomListView(mondayArray,getActivity());

       listView.setAdapter(customMonday);


        return view;
    }
}



