package com.creation.where.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.creation.where.R;

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
	        getView().findViewById(R.id.re_createworld).setOnClickListener(this);
	        getView().findViewById(R.id.re_travel).setOnClickListener(this);
	        getView().findViewById(R.id.re_shopping).setOnClickListener(this);
	        getView().findViewById(R.id.re_games).setOnClickListener(this);
	        getView().findViewById(R.id.re_unkown).setOnClickListener(this);
	    }

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}

}
