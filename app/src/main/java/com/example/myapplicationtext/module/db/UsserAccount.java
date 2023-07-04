package com.example.myapplicationtext.module.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplicationtext.module.dao.UserAccountTable;

public class UsserAccount extends SQLiteOpenHelper {

    private Context mContext;
    public UsserAccount(@Nullable Context context) {

        super(context, "account.db", null, 1);
    }

    @Override//创建时调用
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(UserAccountTable.CREATE_TABLE);
        Toast.makeText(mContext, "建表成功", Toast.LENGTH_SHORT).show();
    }

    @Override//更新时调用
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
