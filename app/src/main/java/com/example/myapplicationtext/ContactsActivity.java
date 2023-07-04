package com.example.myapplicationtext;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
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

import com.example.myapplicationtext.Adapter.ContactListAdapter;
import com.example.myapplicationtext.activity.LoginActivity;
import com.example.myapplicationtext.module.db.MsgAccount;
import com.example.myapplicationtext.module.db.UserAccount;
import com.example.myapplicationtext.ui.fragment.ChatActivity;
import com.example.myapplicationtext.ui.fragment.ChatListActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private ListView contactsListView;
    private ContactListAdapter contactListAdapter;
    private List<String> contactsList;
    private SQLiteDatabase userdb, chatdb;
    private SharedPreferences friendname;

    private String username;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        //获取登陆用户名称
        SharedPreferences loginname = getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        username = loginname.getString("username", "");
        //记录聊天对象名
        friendname = getSharedPreferences("friendname", Context.MODE_PRIVATE);

        contactsListView = findViewById(R.id.contacts_list_view);
        contactsList = new ArrayList<>();

        // 打开或创建数据库

        userdb = openOrCreateDatabase("user_accounts.db", MODE_PRIVATE, null);

        //测试
//        ContentValues values = new ContentValues();
//        String friend_name = "friend_name";
//        values.put(friend_name, "赵六");
//        database.insert(getUsername(), null, values);


        // 查询登录时创建的表中的好友名称
        Cursor cursor = userdb.rawQuery("SELECT friend_name FROM " + username, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String friendName = cursor.getString(cursor.getColumnIndex("friend_name"));
            contactsList.add(friendName);
        }
        cursor.close();

        contactListAdapter = new ContactListAdapter(this, contactsList);
        contactsListView.setAdapter(contactListAdapter);

        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String friendName = contactsList.get(position);

                Toast.makeText(ContactsActivity.this, "Clicked on " + friendName, Toast.LENGTH_SHORT).show();

                try {
                    chatdb = openOrCreateDatabase("chat_message.db", MODE_PRIVATE, null);// 打开消息数据库
                    // 创建表（如果不存在）

                    chatdb.execSQL(MsgAccount.CREATE_TABLE_CHAT_MESSAGES);
                }catch (Exception e){

                }

                //记录当前聊天对象
                SharedPreferences.Editor editor = friendname.edit();
                editor.putString("friendname", friendName);
                editor.apply();

                chatdb.close();
                userdb.close();

                //跳转
                Intent intent = new Intent(view.getContext(), ChatActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}