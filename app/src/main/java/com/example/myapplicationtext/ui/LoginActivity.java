package com.example.myapplicationtext.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplicationtext.R;
import com.example.myapplicationtext.module.bean.User;
import com.example.myapplicationtext.module.dao.UserAccountTable;
import com.example.myapplicationtext.module.db.UserAccount;
import com.example.myapplicationtext.module.db.UsserAccount;

import java.util.Calendar;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private Button bt_login;
    private  Button bt_register;
    private Button user_A;
    private Button user_B;
    private SQLiteDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        database = openOrCreateDatabase("user_accounts.db", MODE_PRIVATE, null);//打开用户表
        // 创建表（如果不存在）
        database.execSQL(UserAccount.CREATE_TABLE);

        initView();

        initListener();

        initListener();

    }


    //初始化控件
    private void initView() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        bt_login = findViewById(R.id.loginbtn);
        bt_register = findViewById(R.id.btn_register);
        user_A = findViewById(R.id.loginUser_A);
        user_B = findViewById(R.id.loginUser_B);

    }

    //初始化监听
    private void initListener(){
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        //快捷输入，方便测试
        user_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("张三");
                password.setText("123");
            }
        });

        user_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username.setText("李四");
                password.setText("123");

            }
        });
    }


    private void register() {
        final String loginName = username.getText().toString();
        final String loginPwd = password.getText().toString();

        //校验
        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPwd)){
            Toast.makeText(LoginActivity.this,"输入的用户名和密码不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }

        //注册
        if (register(loginName, loginPwd)) {
            Toast.makeText(LoginActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "用户已存在", Toast.LENGTH_SHORT).show();
        }
    }

    private void login(){
        final String loginName = username.getText().toString();
        final String loginPwd = password.getText().toString();

        //校验
        if (TextUtils.isEmpty(loginName) || TextUtils.isEmpty(loginPwd)){
            Toast.makeText(LoginActivity.this,"输入的用户名和密码不能为空！",Toast.LENGTH_SHORT).show();
            return;
        }

        //登录
        if (isValidUser(loginName, loginPwd)) {
            // 用户存在，进行登录操作
            login();
        } else {
            // 用户不存在，显示错误信息
            Toast.makeText(LoginActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean register(String username, String password) {
        // 检查用户是否已存在
        Cursor cursor = database.query(UserAccount.TABLE_NAME,
                null,
                UserAccount.COL_NAME + " = ?",
                new String[]{username},
                null,
                null,
                null);

        if (cursor.moveToNext()) {
            // 用户已存在
            cursor.close();
            return false;
        }
        cursor.close();

        // 生成用户id
        int id = generateUserId();

        // 插入用户信息到数据库表中
        ContentValues values = new ContentValues();
        values.put(UserAccount.COL_ID, id);
        values.put(UserAccount.COL_NAME, username);
        values.put(UserAccount.COL_PWD, password);
        database.insert(UserAccount.TABLE_NAME, null, values);

        return true;
    }

    private int generateUserId() {
        // 查询当前数据库中用户的最大id值
        Cursor cursor = database.rawQuery("SELECT MAX(" + UserAccount.COL_ID + ") FROM " +
                UserAccount.TABLE_NAME, null);

        int maxId = 0;
        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0);
        }
        cursor.close();

        // 生成下一个用户的id
        return maxId + 1;
    }

    private boolean isValidUser(String username, String password) {
        // 查询表中的数据，验证用户是否存在
        Cursor cursor = database.query(UserAccount.TABLE_NAME,
                null,
                UserAccount.COL_NAME + " = ? AND " + UserAccount.COL_PWD + " = ?",
                new String[]{username, password},
                null,
                null,
                null);

        boolean isValid = cursor.moveToNext();

        cursor.close();

        return isValid;
    }

}