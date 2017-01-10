package com.example.vincent.afinal.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.vincent.afinal.Objects.User;

import java.util.List;

/**
 * Created by Vincent on 23.11.2016.
 */

public class ListUsersAdapter extends ArrayAdapter {

    public ListUsersAdapter(Context context, int resource){
        super(context,resource);
    }

    //@Override
    public void add(User object) {
        super.add(object);
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
