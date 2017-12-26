package com.example.malik.tablayoutexample;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.security.auth.login.LoginException;

/**
 * Created by MALIK on 12/20/2017.
 */

public class CallReceiver extends BroadcastReceiver {
    public static final String TAG = "TESTLOG";
    Calendar calendar;
    SimpleDateFormat df;
    String lastCallTime ="";
    String callerNumber="";
    MediaPlayer mediaPlayer;
    Context contextRec;
    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;

    public CallReceiver() {
        super();
        calendar = Calendar.getInstance();
        df = new SimpleDateFormat("HH:mm:a");
        Log.i(TAG, "CallReceiver: " + "Constructre");
    }


    @Override
    public void onReceive(final Context context, Intent intent) {
//        mediaPlayer = MediaPlayer.create(contextRec,R.raw.beep);
        Log.i(TAG, "onReceive: " +"Called");
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephony.listen(new PhoneStateListener(){
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                super.onCallStateChanged(state, incomingNumber);
                // first time call
                //Log.i(TAG, "onCallStateChanged: "+incomingNumber);
                if(!incomingNumber.equals("") && MainActivity.callingNumber.equals("") && MainActivity.callingTime.equals("")){
                    Log.i(TAG, "onCallStateChanged: First Time Called");
                    MainActivity.callingNumber = incomingNumber;
                    df = new SimpleDateFormat("HH:mm:a");
                    MainActivity.callingTime = df.format(calendar.getTime());

                }
                if(!incomingNumber.equals("") && !MainActivity.callingNumber.equals("") && !MainActivity.callingTime.equals("")){
                    if(incomingNumber.equals(MainActivity.callingNumber)){
                        df = new SimpleDateFormat("HH:mm:a");
                        String time = df.format(calendar.getTime());
                        String[] currentTime = time.split(":");
                        String[] savedTime = MainActivity.callingTime.split(":");
                        if(currentTime[0].equals(savedTime[0]) && currentTime[2].equals(savedTime[2])){
                            int minC = Integer.parseInt(currentTime[1]);
                            int minS = Integer.parseInt(savedTime[1]);
                            minS++;
                            if(minC<=minS){
                                Log.i(TAG, "onCallStateChanged: Emergency");

//                                mediaPlayer.start();
                                notification = new NotificationCompat.Builder(context);
                                notification.setAutoCancel(true);
                                notification.setSmallIcon(R.drawable.notification);
                                notification.setColor(Color.red(1));
                                notification.setTicker("Scheduley");
                                notification.setWhen(System.currentTimeMillis());
                                notification.setContentTitle("Urgent Call");
                                notification.setContentText("Caller Number : "+MainActivity.callingNumber);

                                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                notificationManager.notify(uniqueID,notification.build());

                                Intent callerIntent  = new Intent(Intent.ACTION_CALL);
                                PendingIntent pendingIntent = PendingIntent.getActivities(context,0, new Intent[]{callerIntent},0);
                                callerIntent.setData(Uri.parse("tel: "+MainActivity.callingNumber));
                                notification.setContentIntent(pendingIntent);

                            }
                        }
                                
                    }
                }
//                Log.i(TAG, "onCallStateChanged: "+MainActivity.callingNumber);
//                Log.i(TAG, "onCallStateChanged: "+MainActivity.callingTime);




            }
        },PhoneStateListener.LISTEN_CALL_STATE);
    }
}
