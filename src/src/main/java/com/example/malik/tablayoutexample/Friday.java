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

public class Friday extends Fragment {

    public Database database;
    public ArrayList<TimeSlot> fridayArray;

    public class getFridayItems implements Runnable {

        @Override
        public void run() {
            database = new Database(getActivity(),null,null,1);
            fridayArray = database.getDayData("Friday");

        }
    }



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_friday, container, false);

        Thread thread = new Thread(new getFridayItems());
        ListView listView = (ListView) view.findViewById(R.id.listViewFriday);
        database = new Database(getActivity(),null,null,1);
        fridayArray = database.getDayData("Friday");
//        mondayArray = new ArrayList<TimeSlot>();
//        mondayArray.add(new TimeSlot("Monday","1:10:AM","2:10:AM","Class"));

        CustomListView customFriday = new CustomListView(fridayArray,getActivity());

        listView.setAdapter(customFriday);


        return view;
    }
}
