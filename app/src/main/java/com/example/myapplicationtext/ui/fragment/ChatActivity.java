package com.example.myapplicationtext.ui.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationtext.ContactsActivity;
import com.example.myapplicationtext.R;
import com.example.myapplicationtext.module.db.MsgAccount;


import java.util.Date;
import java.util.Objects;


public class ChatActivity extends AppCompatActivity {


    private TextView chatContentTextView;
    private EditText messageEditText;
    private Button sendButton;
    private Button btn_shanchu;
    private SQLiteDatabase userdb, chatdb;
    private Toolbar toolbar;
    private SharedPreferences User, Friend;
    private String username, friendname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // 初始化数据库
        chatdb = openOrCreateDatabase("chat_message.db", MODE_PRIVATE, null);

        //获取当前用户名
        User = getSharedPreferences("LoginUser", Context.MODE_PRIVATE);
        username = User.getString("username", "");

        /*获取聊天好友名*/
        Friend = getSharedPreferences("ChatFriend", Context.MODE_PRIVATE);
        friendname = Friend.getString("friendname", "");


        initView();

        initListener();

        // 更新聊天内容显示
        updateChatContent();
    }

    private void initListener() {
        // 设置发送按钮的点击事件
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        // 设置删除按钮的点击事件
        btn_shanchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteFriend();
            }
        });

        // 设置返回按钮的点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChatActivity.this, ChatListActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initView() {
        // 初始化布局
        chatContentTextView = findViewById(R.id.chat_content_textview);
        messageEditText = findViewById(R.id.message_edittext);
        sendButton = findViewById(R.id.send_button);
        toolbar = findViewById(R.id.toolbar);
        btn_shanchu = findViewById(R.id.btn_shanchu);

        // 设置Toolbar
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // 获取当前对话用户的名称，并设置为Toolbar的标题

        String currentUserName = username;
        toolbar.setTitle(currentUserName);
    }

    private void sendMessage() {
        // 获取输入的消息内容
        String messageContent = messageEditText.getText().toString().trim();

        // 获取当前时间
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String sendTime = formatter.format(curDate);


        // 插入新的消息到数据库中
        ContentValues values = new ContentValues();
        values.put(MsgAccount.COLUMN_MESSAGE_CONTENT, messageContent);
        values.put(MsgAccount.COLUMN_SENDER_NAME, username);
        values.put(MsgAccount.COLUMN_RECIPIENT_NAME, friendname);
        values.put(MsgAccount.COLUMN_SEND_TIME, sendTime);
        chatdb.insert(MsgAccount.TABLE_NAME, null, values);

        // 更新聊天内容显示
        updateChatContent();

        // 清空输入框
        messageEditText.setText("");
    }

    private void updateChatContent() {
        // 查询数据库中的所有消息

        Cursor cursor = chatdb.rawQuery("SELECT MsgContent, sName, rName FROM " + MsgAccount.TABLE_NAME, null);

        // 清空聊天内容
        chatContentTextView.setText("");

        // 遍历消息并显示在聊天内容页面
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String messageContent = cursor.getString(cursor.getColumnIndex(MsgAccount.COLUMN_MESSAGE_CONTENT));
            @SuppressLint("Range") String senderName = cursor.getString(cursor.getColumnIndex(MsgAccount.COLUMN_SENDER_NAME));
            @SuppressLint("Range") String recipientName = cursor.getString(cursor.getColumnIndex(MsgAccount.COLUMN_RECIPIENT_NAME));

            // 判断消息是否显示以及应该显示什么名称
            String messageDisplay;
            if (senderName.equals(username) && recipientName.equals(friendname)) {
                // 当前登录用户发送且收件人为朋友的消息
                messageDisplay = "我：" + messageContent;
                chatContentTextView.append(messageDisplay + "\n");
            } else if(senderName.equals(friendname) && recipientName.equals(username)){
                // 当前朋友发送且收件人为当前用户的消息
                messageDisplay = senderName + "：" + messageContent;
                chatContentTextView.append(messageDisplay + "\n");
            } else {
                continue;
            }
        }

        // 关闭游标
        cursor.close();
    }

    private void deleteFriend() {
        //删除好友
        String deleteQuery = "DELETE FROM " + username + " WHERE friend_name = ?";
        String friendName = friendname;
        userdb = openOrCreateDatabase("user_accounts.db", MODE_PRIVATE, null);
//        userdb.execSQL(deleteQuery, new String[]{friendName});
        userdb.delete(username," friend_name = ? ", new String[]{friendName});
        userdb.close();

        // 提示删除成功
        Toast.makeText(ChatActivity.this, "删除好友成功", Toast.LENGTH_SHORT).show();

        //退出界面
        Intent intent = new Intent(this, ContactsActivity.class);
        startActivity(intent);
    }

}