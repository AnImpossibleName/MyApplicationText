package com.example.myapplicationtext.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplicationtext.module.dao.UserAccountTable;

public class UserAccount extends SQLiteOpenHelper{
    // 表名
    public static final String TABLE_NAME = "tab_account";

    // 列名
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_PWD = "password";

    // 创建表的SQL语句
    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + "("
            + COL_ID + " text primary key,"
            + COL_NAME + " text,"
            + COL_PWD + " text)";


    private Context mContext;

    public UserAccount(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 其他方法...
}

