package com.creation.where.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creation.where.R;
import com.creation.where.activity.FootPrintActivity;
import com.creation.where.activity.TogetherActivity;
import com.creation.where.activity.TravelActivity;

public class FindFragment extends Fragment implements View.OnClickListener{
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        return inflater.inflate(R.layout.fx_fragment_find, container, false);
	    }

	    @Override
	    public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	        getView().findViewById(R.id.re_newworld).setOnClickListener(this);
	        getView().findViewById(R.id.re_together).setOnClickListener(this);
	        getView().findViewById(R.id.re_travel).setOnClickListener(this);
	        getView().findViewById(R.id.re_shopping).setOnClickListener(this);
	        getView().findViewById(R.id.re_games).setOnClickListener(this);
	        getView().findViewById(R.id.re_unkown).setOnClickListener(this);
	    }

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
				case R.id.re_newworld:
					startActivity(new Intent(getActivity(), FootPrintActivity.class));
					break;
				case R.id.re_together:
					startActivity(new Intent(getActivity(), TogetherActivity.class));
					break;
				case R.id.re_travel:
					startActivity(new Intent(getActivity(), TravelActivity.class));
					break;
				case R.id.re_shopping:
					//joinQQGroup("ycxd0w_eXmTbKIjyDdHb5Dy_-ZhY8E7t");
					break;
				case R.id.re_games:
					//startActivity(new Intent(getActivity(), SocialFriendActivity.class).putExtra("friendID", DemoHelper.getInstance().getCurrentUsernName()));
					break;
				case R.id.re_unkown:
					//joinQQGroup("ycxd0w_eXmTbKIjyDdHb5Dy_-ZhY8E7t");
					break;
			}
		}
}
