package com.example.android.waitlist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.example.android.waitlist.data.WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME;
import static com.example.android.waitlist.data.WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE;
import static com.example.android.waitlist.data.WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP;
import static com.example.android.waitlist.data.WaitlistContract.WaitlistEntry.TABLE_NAME;

public class WaitlistDbHelper extends SQLiteOpenHelper {

    static final String DATABASE_NAME = "waitlist.db";

    static final int DATABASE_VERSION = 1;

    public WaitlistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_WAITLIST_TABLE = "create table " + TABLE_NAME + "("
                + _ID + " integer primary key autoincrement, "
                + COLUMN_GUEST_NAME + " text not null, "
                + COLUMN_PARTY_SIZE + " integer not null, "
                + COLUMN_TIMESTAMP + " timestamp default CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(SQL_CREATE_WAITLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}