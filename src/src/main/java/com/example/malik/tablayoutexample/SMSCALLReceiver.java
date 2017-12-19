package com.example.malik.tablayoutexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by MALIK on 12/14/2017.
 */

public class SMSCALLReceiver extends BroadcastReceiver {
    public static final String SMS_BUNDLE = "pdus";
    SmsManager sendSMS;



    public boolean isUrgent(String sms){
        String[] smsInPieces;
        smsInPieces = sms.split("\\s+");

        for(int i=0;i<smsInPieces.length;i++){

            if(smsInPieces[i].contains("urgent") || smsInPieces[i].contains("emergency") || smsInPieces[i].contains("Urgent") || smsInPieces[i].contains("Emergency")){
                return true;
            }
        }
        return false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "On Received Called", Toast.LENGTH_SHORT).show();
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            //String smsMessageStr = "";
            String address="";
            String smsBody="";
            for (int i = 0; i < sms.length; ++i) {
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                smsBody = smsMessage.getMessageBody().toString();
                address = smsMessage.getOriginatingAddress();

                //smsMessageStr += "SMS From: " + address + "\n";
                //smsMessageStr += smsBody + "\n";
            }
            if(isUrgent(smsBody)){
                sendSMS = SmsManager.getDefault();
                sendSMS.sendTextMessage(address,null,"User Is Busy Will Call You Later", null,null);
                //Toast.makeText(MainActivity.class,"Sending SMS", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(context, smsBody, Toast.LENGTH_SHORT).show();
        }

    }

}
