package com.example.malik.tablayoutexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by MALIK on 11/13/2017.
 */

public class Wednesday extends Fragment {
    public Database database;
    public ArrayList<TimeSlot> wednesdayArray;

    public class getWednesdayItems implements Runnable {

        @Override
        public void run() {
            database = new Database(getActivity(),null,null,1);
            wednesdayArray = database.getDayData("Wednesday");

        }
    }



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_wednesday, container, false);

        Thread thread = new Thread(new getWednesdayItems());
        ListView listView = (ListView) view.findViewById(R.id.listViewWednesday);
        database = new Database(getActivity(),null,null,1);
        wednesdayArray = database.getDayData("Wednesday");
//        mondayArray = new ArrayList<TimeSlot>();
//        mondayArray.add(new TimeSlot("Monday","1:10:AM","2:10:AM","Class"));

        CustomListView customWednesday = new CustomListView(wednesdayArray,getActivity());

        listView.setAdapter(customWednesday);


        return view;
    }
}
