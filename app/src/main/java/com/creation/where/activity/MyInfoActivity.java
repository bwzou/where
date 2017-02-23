package com.creation.where.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.creation.where.R;

public class MyInfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
    }

    public void back(View view) {
        finish();
    }
}
