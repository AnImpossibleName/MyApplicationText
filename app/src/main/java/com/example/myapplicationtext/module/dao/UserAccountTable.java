package com.example.myapplicationtext.module.dao;
//用户信息表
public class UserAccountTable {
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
}
