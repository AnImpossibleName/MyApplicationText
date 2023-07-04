package com.example.myapplicationtext.module.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplicationtext.module.bean.User;
import com.example.myapplicationtext.module.db.UsserAccount;

//数据库操作类
public class AccountDao {

    public final UsserAccount mHelper;

    public AccountDao(Context context) {
        mHelper = new UsserAccount(context);//全局量，可能出错
    }

    @SuppressLint("Range")
    public User getid(String id){
        //从数据库获取对象
        SQLiteDatabase database = mHelper.getReadableDatabase();//final必须？

        //查询
        String sql = "select * from " + UserAccountTable.TABLE_NAME + " where " + UserAccountTable.COL_ID + "=?";
        final Cursor cursor = database.rawQuery(sql, new String[]{id});
        User user = null;
        if (cursor.moveToNext()){
            user = new User();
            //封装对象
            user.setId(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(UserAccountTable.COL_NAME)));
        }

        //关闭
        cursor.close();

        //返回数据
        return user;
    }
}
