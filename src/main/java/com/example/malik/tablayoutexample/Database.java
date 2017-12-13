package com.example.malik.tablayoutexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by MALIK on 12/5/2017.
 */

public class Database extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scheduleApp.db";
    private static final String TABLE_NAME = "timeInWeek";
    private static final String COLUMN_DAY = "dayOfWeek";
    private static final String COLUMN_STARTTIME = "startTime";
    private static final String COLUMN_ENDTIME = "endTime";
    private static final String COLUMN_TYPE = "type";
    public static final String COLUMN_ID = "id";


    String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_DAY + " TEXT, " + COLUMN_STARTTIME + " TEXT, " + COLUMN_TYPE + " TEXT, " + COLUMN_ENDTIME + " TEXT);";

    private SQLiteDatabase db;


    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(query);
        Log.i("Faisal",query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean alreadyExist(String day,String startTime, String endTime,String type){
//        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + " ("+ COLUMN_DAY + "=? ) "
                + " AND ("+ COLUMN_STARTTIME + "=? ) "
                + " AND ("+ COLUMN_TYPE + "=? ) "
                + " AND ( "+ COLUMN_ENDTIME + "=?);";
        String[] arrwhere = {day,startTime,type,endTime};
        db = getReadableDatabase();
        Cursor c = db.rawQuery(query,arrwhere);
        int queryResult = c.getCount();
        if(queryResult >0){
            return true;
        }
        else {
            return false;
        }



    }


    public void delete(String day,String startTime, String endTime,String type){
        String deleteQuery = " ("+ COLUMN_DAY + "=? ) "
                + " AND ("+ COLUMN_STARTTIME + "=? ) "
                + " AND ("+ COLUMN_TYPE + "=? ) "
                + " AND ( "+ COLUMN_ENDTIME + "=?);";
        String[] arrwhere = {day,startTime,type,endTime};
        db = getWritableDatabase();
        db.delete(TABLE_NAME,deleteQuery,arrwhere);

    }


    public ArrayList<TimeSlot> getDayData(String day){

        //String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_DAY +"=?);";
        String query  = "SELECT * FROM " + TABLE_NAME + " where dayOfWeek = '" +day + "'";
        String[] arrwhere = {day};
        db = getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        ArrayList<TimeSlot> values = new ArrayList<TimeSlot>();
        c.moveToFirst();
        int curserStatus=c.getCount();
        int i=0;
        while (i<curserStatus) {

            {
                values.add(new TimeSlot(
                        c.getString(c.getColumnIndex(COLUMN_DAY)),
                        c.getString(c.getColumnIndex(COLUMN_STARTTIME)),
                        c.getString(c.getColumnIndex(COLUMN_ENDTIME)),
                        c.getString(c.getColumnIndex(COLUMN_TYPE)))
                );
                c.moveToNext();
                i++;
            }
            db.close();
        }
        return values;
    }



    public boolean addTimeSlot(String day,String startTime, String endTime,String type){
        if(!alreadyExist(day,startTime,endTime,type)){
            ContentValues values = new ContentValues();
            db = getWritableDatabase();
            values.put(COLUMN_DAY, day);
            values.put(COLUMN_STARTTIME, startTime);
            values.put(COLUMN_ENDTIME, endTime);
            values.put(COLUMN_TYPE, type);
            db.insert(TABLE_NAME,null,values);
            return true;
        }
        else
            return false;

    }

}
