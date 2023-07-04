package com.example.myapplicationtext.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationtext.Adapter.ChatListAdapter;
import com.example.myapplicationtext.ContactsActivity;
import com.example.myapplicationtext.R;
import com.example.myapplicationtext.module.bean.Chat;
import com.example.myapplicationtext.module.bean.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends AppCompatActivity {

    private ListView chatListView;
    private ChatListAdapter chatListAdapter;
    private List<ChatMessage> chatList;
    private SQLiteDatabase chatdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatListView = findViewById(R.id.chat_list_view);
        chatList = new ArrayList<>();

        // 打开或创建数据库
        chatdb = openOrCreateDatabase("chat_message.db", MODE_PRIVATE, null);// 打开用户数据库
        try {
            // 查询最新一条聊天数据
            Cursor cursor = chatdb.rawQuery("SELECT * FROM chat_messages ORDER BY Msgid DESC LIMIT 1", null);
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String sender = cursor.getString(cursor.getColumnIndex("sender"));
                @SuppressLint("Range") String message = cursor.getString(cursor.getColumnIndex("message"));
                chatList.add(new ChatMessage(sender, message));
            }
            cursor.close();
        }catch (Exception e){

        }
        chatdb.close();


        chatListAdapter = new ChatListAdapter(this, chatList);
        chatListView.setAdapter(chatListAdapter);

        chatListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatMessage chat = chatList.get(position);
                Toast.makeText(ChatListActivity.this, "Clicked on " + chat.getSender(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void openContactsActivity(View view) {
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

    public void openNewFriendsActivity(View view) {
        Intent intent = new Intent(this, NewFriendsActivity.class);
        startActivity(intent);
    }
}