package com.creation.where.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creation.where.R;
import com.creation.where.activity.NewFriendsActivity;

/**
 * Created by zbw on 2017/5/9.
 */

public class FriendFragment extends Fragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fx_fragment_friends, null);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.re_newfriends).setOnClickListener(this);
        getView().findViewById(R.id.re_addfriends).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.re_newfriends:
                startActivity(new Intent(getActivity(), NewFriendsActivity.class));
                break;
            case R.id.re_addfriends:
//                startActivity(new Intent(getActivity(), TogetherActivity.class));
                break;
        }
    }
}
