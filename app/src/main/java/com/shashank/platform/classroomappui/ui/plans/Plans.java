package com.shashank.platform.classroomappui.ui.plans;


import android.os.Bundle;


import android.support.v7.app.AppCompatActivity;

import com.shashank.platform.classroomappui.R;

public class Plans extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plans);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Plan");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
