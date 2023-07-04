package com.example.myapplicationtext.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplicationtext.module.bean.ChatMessage;

public class MsgAccount extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "chat_messages";
    public static final int DATABASE_VERSION = 1;
    public static final String COLUMN_MESSAGE_ID = "Msgid";
    public static final String COLUMN_MESSAGE_CONTENT = "MsgContent";
    public static final String COLUMN_SENDER_NAME = "sName";
    public static final String COLUMN_RECIPIENT_NAME = "rName";
    public static final String COLUMN_SEND_TIME = "sTime";

    public static final String CREATE_TABLE_CHAT_MESSAGES = "create table "
            + TABLE_NAME + "("
            + COLUMN_MESSAGE_ID + " integer primary key autoincrement,"
            + COLUMN_MESSAGE_CONTENT + " text,"
            + COLUMN_SENDER_NAME + " text,"
            + COLUMN_RECIPIENT_NAME + " text,"
            + COLUMN_SEND_TIME + " text)";

    public MsgAccount(Context context) {
        super(context, TABLE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
