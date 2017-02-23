package com.creation.where.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.creation.where.R;

public class MyFriendsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_friends);

    }



    public void back(View view) {
        finish();
    }
}
