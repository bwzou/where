package com.creation.where.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.creation.where.R;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void back(View view) {
        finish();
    }
}
