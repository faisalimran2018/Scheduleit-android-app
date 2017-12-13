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

public class Thursday extends Fragment {


    public Database database;
    public ArrayList<TimeSlot> thursdayArray;

    public class getThursdayItems implements Runnable {

        @Override
        public void run() {
            database = new Database(getActivity(),null,null,1);
            thursdayArray = database.getDayData("Thursday");

        }
    }



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_thursday, container, false);

        Thread thread = new Thread(new getThursdayItems());
        ListView listView = (ListView) view.findViewById(R.id.listViewThursday);
        database = new Database(getActivity(),null,null,1);
        thursdayArray = database.getDayData("Thursday");
//        mondayArray = new ArrayList<TimeSlot>();
//        mondayArray.add(new TimeSlot("Monday","1:10:AM","2:10:AM","Class"));

        CustomListView customThursday = new CustomListView(thursdayArray,getActivity());

        listView.setAdapter(customThursday);


        return view;
    }

}
