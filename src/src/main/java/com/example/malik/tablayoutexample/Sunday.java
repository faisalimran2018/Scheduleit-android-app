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

public class Sunday extends Fragment {
    public Database database;
    public ArrayList<TimeSlot> sundayArray;

    public class getSundayItems implements Runnable {

        @Override
        public void run() {
            database = new Database(getActivity(),null,null,1);
            sundayArray = database.getDayData("Sunday");

        }
    }



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_sunday, container, false);

        Thread thread = new Thread(new getSundayItems());
        ListView listView = (ListView) view.findViewById(R.id.listViewSunday);
        database = new Database(getActivity(),null,null,1);
        sundayArray = database.getDayData("Sunday");
//        mondayArray = new ArrayList<TimeSlot>();
//        mondayArray.add(new TimeSlot("Monday","1:10:AM","2:10:AM","Class"));

        CustomListView customSunday = new CustomListView(sundayArray,getActivity());

        listView.setAdapter(customSunday);


        return view;
    }

}
