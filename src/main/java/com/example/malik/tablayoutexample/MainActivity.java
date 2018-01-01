package com.example.malik.tablayoutexample;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    public static boolean serviceWorking = false;
    public static boolean silentOrNot = false;
    public static String callOrMeeting="";
    public static String callingNumber="";
    public static String callingTime="";
     public Fragment fragment;
     public CustomListView customListViewMonday;

     private SectionsPagerAdapter mSectionsPagerAdapter;
     private ViewPager mViewPager;


     public class ServiceStarter implements Runnable {
         @Override
         public void run() {
             Intent intentServiceStarter =  new Intent(getBaseContext(),SilentSchedule.class);
             startService(intentServiceStarter);
         }
     }


    // getting current fragment
    public Fragment getVisibleFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Caller", callingNumber);
        outState.putString("Time", callingTime);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        callingNumber = savedInstanceState.getString("Caller");
        callingTime = savedInstanceState.getString("Time");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
//                && !notificationManager.isNotificationPolicyAccessGranted()) {
//
//            Intent intent = new Intent(
//                    android.provider.Settings
//                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
//
//            startActivity(intent);
//        }


//        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(mToolbar);
//        mToolbar.setLogo(R.drawable.round);


        // Starting Broadcast Receiver
        SMSCALLReceiver smscallReceiver = new SMSCALLReceiver();
        CallReceiver callReceiver = new CallReceiver();

        Calendar calendar = Calendar.getInstance();
        Date systemTime = calendar.getTime();
        String timeString =  systemTime.toString();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:a");
        String formattedTime = df.format(calendar.getTime());
        String[] hour = formattedTime.split(":");

        // Starting Service
        Intent intentServiceStarter =  new Intent(getBaseContext(),SilentSchedule.class);
        startService(intentServiceStarter);

//        Toast.makeText(this,"Main Activity",Toast.LENGTH_LONG).show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setImageResource(R.drawable.addButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                  fragment = getVisibleFragment();
//                  String frag = String.valueOf(fragment);
//                Toast.makeText(getApplicationContext(),frag,Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this,add_schedule.class);
                startActivity(intent);
                finish();



            }
        });

    }

//   Menu which include settings
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
         //get the optio which is selected from menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    Monday monday = new Monday();
                    return monday;
                case 1:
                    Tuesday tuesday = new Tuesday();
                    return tuesday;
                case 2:
                    Wednesday wednesday = new Wednesday();
                    return wednesday;
                case 3:
                    Thursday thursday = new Thursday();
                    return thursday;
                case 4:
                    Friday friday = new Friday();
                    return friday;
                case 5:
                    Saturday saturday = new Saturday();
                    return saturday;
                case 6:
                    Sunday sunday = new Sunday();
                    return sunday;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 7  total pages.
            return 7;
        }
    }
}
