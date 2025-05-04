package com.example.skillnexus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> users;

    public UserListAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_row, parent, false);

        TextView txtUserName = view.findViewById(R.id.txtUserName);
        TextView txtUserSkill = view.findViewById(R.id.txtUserSkill);

        User user = users.get(position);
        txtUserName.setText(user.getName());
        txtUserSkill.setText(user.getSkill());

        return view;
    }
}
