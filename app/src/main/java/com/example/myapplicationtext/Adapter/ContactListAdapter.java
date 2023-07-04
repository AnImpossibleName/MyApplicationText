package com.example.myapplicationtext.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactListAdapter extends ArrayAdapter<String> {

    private Context context;
    private List<String> contactsList;

    public ContactListAdapter(Context context, List<String> contactsList) {
        super(context, 0, contactsList);
        this.context = context;
        this.contactsList = contactsList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        String contactName = contactsList.get(position);

        TextView contactNameTextView = view.findViewById(android.R.id.text1);
        contactNameTextView.setText(contactName);

        return view;
    }
}