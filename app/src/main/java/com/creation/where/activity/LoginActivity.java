package com.creation.where.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.creation.where.R;

public class LoginActivity extends Activity {
	 private EditText et_usertel;
	    private EditText et_password;
	    private Button btn_login;
	    private Button btn_qtlogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		et_usertel = (EditText) findViewById(R.id.et_usertel);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_qtlogin = (Button) findViewById(R.id.btn_qtlogin);
        
        TextChange textChange = new TextChange();
        et_usertel.addTextChangedListener(textChange);
        et_password.addTextChangedListener(textChange);
        
        // if user changed, clear the password
        et_usertel.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                et_password.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loginInSever(et_usertel.getText().toString(), et_password.getText().toString());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        btn_qtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        
	}

	private void loginInSever(String tel, String password) {
        final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage(getString(R.string.is_landing));
        pd.show();
        
        
//        List<Param> params = new ArrayList<Param>();
//        params.add(new Param("usertel", tel));
//        params.add(new Param("password", password));
//        OkHttpManager.getInstance().post(params, FXConstant.URL_LOGIN, new OkHttpManager.HttpCallBack() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                int code = jsonObject.getInteger("code");
//                if (code == 1000) {
//                    JSONObject json = jsonObject.getJSONObject("user");
//                    JSONArray friends=json.getJSONArray("friends");
//                    Map<String, EaseUser> userlist = new HashMap<String, EaseUser>();
//                    if (friends != null) {
//                        for (int i = 0; i < friends.size(); i++) {
//                            JSONObject friend = friends.getJSONObject(i);
//                            EaseUser easeUser = JSONUtil.Json2User(friend);
//                            userlist.put(easeUser.getUsername(), easeUser);
//                        }
//                        // save the contact list to cache
//                        DemoHelper.getInstance().getContactList().clear();
//                        DemoHelper.getInstance().getContactList().putAll(userlist);
//                        // save the contact list to database
//                        UserDao dao = new UserDao(getApplicationContext());
//                        List<EaseUser> users = new ArrayList<EaseUser>(userlist.values());
//                        dao.saveContactList(users);
//                    }
//
//                    loginHuanXin(json, pd);
//                } else if (code == 2001) {
//                    pd.dismiss();
//                    Toast.makeText(LoginActivity.this,
//                            "�˺Ż��������...", Toast.LENGTH_SHORT)
//                            .show();
//                }else {
//                    pd.dismiss();
//                    Toast.makeText(LoginActivity.this,
//                            "��������æ������...", Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//            @Override
//            public void onFailure(String errorMsg) {
//
//            }
//        });
    }

    //EditText监听器
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,int count) {
            boolean Sign2 = et_usertel.getText().length() > 0;
            boolean Sign3 = et_password.getText().length() > 0;
            if (Sign2 & Sign3) {
                btn_login.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                btn_login.setEnabled(false);
            }
        }
    }
    
    public void back(View view) {
        finish();
    }
    
}
