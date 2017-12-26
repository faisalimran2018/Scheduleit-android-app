package com.example.malik.tablayoutexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;

/**
 * Created by MALIK on 12/26/2017.
 */

public class OnSlientReceiver extends BroadcastReceiver {
    public OnSlientReceiver() {
        super();
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        String mode = intent.getStringExtra("Mode");
       AudioManager audioManager =  (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
       if(mode.equals("Silent")){
           audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
           MainActivity.callOrMeeting = "Class";
           MainActivity.silentOrNot = true;
       }
       else if(mode.equals("Vibrate")){
           audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
           MainActivity.callOrMeeting = "Meeting";
           MainActivity.silentOrNot=true;
       }
       else{
           audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
       }
    }
}
