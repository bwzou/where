package com.creation.where.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.creation.where.R;

public class AccountFragment extends Fragment implements View.OnClickListener{
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fx_fragment_account, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setListener();
    }
    
    private void initView(){
        ImageView ivAvatar= (ImageView) getView().findViewById(R.id.iv_avatar);
        TextView tvNick= (TextView) getView().findViewById(R.id.tv_name);
        TextView tvFxid= (TextView) getView().findViewById(R.id.tv_fxid);

        tvFxid.setText("微尔号:Jobowen");
    }
    
    private void setListener(){

        getView().findViewById(R.id.re_myinfo).setOnClickListener(this);
        
        getView().findViewById(R.id.re_myfriend).setOnClickListener(this);
        getView().findViewById(R.id.re_myfoot).setOnClickListener(this);
        
        getView().findViewById(R.id.re_xiangce).setOnClickListener(this);
        getView().findViewById(R.id.re_shoucang).setOnClickListener(this);
        
        getView().findViewById(R.id.re_download).setOnClickListener(this);
        
        getView().findViewById(R.id.re_setting).setOnClickListener(this);
     }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode== Activity.RESULT_OK){
            initView();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
