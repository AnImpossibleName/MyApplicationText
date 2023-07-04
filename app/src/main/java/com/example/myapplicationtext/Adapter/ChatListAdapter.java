package com.example.myapplicationtext.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplicationtext.module.bean.ChatMessage;

import java.util.List;

public class ChatListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<ChatMessage> chatList;

    public ChatListAdapter(Context context, List<ChatMessage> chatList) {
        super(context, 0);
        this.context = context;
        this.chatList = chatList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        String chatName = String.valueOf(chatList.get(position));

        TextView chatNameTextView = view.findViewById(android.R.id.text1);
        chatNameTextView.setText(chatName);

        return view;
    }
}