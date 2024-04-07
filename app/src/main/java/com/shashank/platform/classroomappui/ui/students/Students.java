package com.shashank.platform.classroomappui.ui.students;

import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;

import com.shashank.platform.classroomappui.R;

public class Students extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Students");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
