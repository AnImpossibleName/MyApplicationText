package com.example.myapplicationtext.ui.fragment;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtext.R;
import com.example.myapplicationtext.module.bean.User;

import java.util.ArrayList;
import java.util.List;

public class NewFriendsActivity extends AppCompatActivity {

    private ListView contactlv;
    private ListView newFriendsListView;
    private ArrayAdapter<String> newFriendsAdapter;
    private List<String> newFriendsList;
    private SQLiteDatabase database;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friends);

        SharedPreferences loginname = getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        username = loginname.getString("username", "");

        newFriendsListView = findViewById(R.id.new_friends_list_view);
        newFriendsList = new ArrayList<>();

        // 打开或创建数据库
        database = openOrCreateDatabase("chat_messages.db", MODE_PRIVATE, null);
        database = openOrCreateDatabase("user_accounts.db", MODE_PRIVATE, null);// 打开用户数据库


        // 查询所有用户
        Cursor cursor = database.rawQuery("SELECT name FROM tab_account", null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("name"));
            newFriendsList.add(username);
        }
        cursor.close();

        newFriendsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, newFriendsList);
        newFriendsListView.setAdapter(newFriendsAdapter);

        contactlv = findViewById(R.id.new_friends_list_view);

        setListener();
    }

    public void addFriend(String friendName) {

        // 将好友名称写入登录时创建的表
//        database.execSQL("INSERT INTO " + getUsername() + " (friend_name) VALUES ('" + friendName + "')");

        ContentValues values = new ContentValues();
        String friend_name = "friend_name";
        values.put(friend_name, friendName);
        database.insert(username, null, values);


        Toast.makeText(this, "Added " + friendName + " as friend", Toast.LENGTH_SHORT).show();
    }

    private String getUsername() {
        // 获取登录时创建的表名称
        // 这里假设登录时输入的用户名为张三
        return "张三";
    }

    private void setListener() {
        contactlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                
                String addname = newFriendsList.get(position);
                Toast.makeText(view.getContext(), "click " + addname + " as friend, position is " + position, Toast.LENGTH_SHORT).show();
                addFriend(addname);

            }
        });

    }
}