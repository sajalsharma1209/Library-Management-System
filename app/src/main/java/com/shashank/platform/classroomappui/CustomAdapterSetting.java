package com.shashank.platform.classroomappui;

import static android.os.Build.VERSION_CODES.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapterSetting extends ArrayAdapter<String> {
    Activity context;
    String name[];
    String num[];
    TextView medium_text, small_text;

    public CustomAdapterSetting(Activity context, String[] name, String[] num) {
        super(context, R, name);
        this.context = context;
        this.name = name;
        this.num = num;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View v = inflater.inflate(R, null, true);
        medium_text = (TextView) v.findViewById(R);
        small_text = (TextView) v.findViewById(R);
        medium_text.setText(name[position]);
        small_text.setText(num[position]);
        return v;
    }
}
