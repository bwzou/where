package com.creation.where.activity;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.creation.where.R;

public class MyFootprintActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_footprint);
    }

    public void back(View view) {
        finish();
    }

}
