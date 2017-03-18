package com.creation.where.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.creation.where.R;
import com.creation.where.po.User;
import com.creation.where.util.Constants;
import com.creation.where.util.HttpUtils;
import com.google.gson.Gson;

import static com.creation.where.util.DebugUtils.ShowErrorInf;

public class LoginActivity extends Activity {
    private EditText et_usertel;
    private EditText et_password;
    private Button btn_login;
    private Button btn_qtlogin;

    private Handler handler;
    public  ProgressDialog pd;

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
                pd = new ProgressDialog(LoginActivity.this);
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage(getString(R.string.is_landing));
                pd.show();
                // 开始连接本地服务器
                loginInSever(et_usertel.getText().toString(), et_password.getText().toString());
            }
        });

        btn_qtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });

        this.handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    if (Constants.user == null){
                        ShowErrorInf("用户名或者密码错误！");
                    }
                    else {
                        setUser(Constants.user);
                        Intent intent = new Intent(LoginActivity.this,  MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    pd.dismiss();
                } else if (msg.what == -1){
                    pd.dismiss();
                    ShowErrorInf( "服务器请求异常！");
                }
                else if (msg.what == 0){
                    pd.dismiss();
                    ShowErrorInf( "网络异常！");
                }
            }
        };
        
	}

	private void loginInSever(String phone_number, String password) {
//        List<Param> params=new ArrayList<>();
//        params.add(new Param("option","login"));
//        params.add(new Param("username",phone_number));
//        params.add(new Param("password",password));

        final String phone=phone_number;
        final String pwd=password;
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                // 发送用户名和密码到服务器进行校验，并获得服务器返回值
                StringBuffer params = new StringBuffer();
                params.append("option=").append("login");
                params.append("&phone_number=").append(phone);
                params.append("&password=").append(pwd);
                String encode = "utf-8";

                String res= HttpUtils.getJsonContent(Constants.USR_LOGIN,encode,params);
                Looper.prepare();
                if (res.equals("")||res==null) {
                    handler.sendEmptyMessage(-1);
                }else{
                    Gson gson = new Gson();
                    Constants.user = gson.fromJson(res, User.class);
                    handler.sendEmptyMessage(1);
                }
                Looper.loop();
            }
        }).start();
    }

    /**
     * 保存整个User
     */
    public void setUser(User user){
        //定义一个可以存放全局变量的东西
        SharedPreferences settings = this.getSharedPreferences("UserInfo", MODE_PRIVATE);

        Editor editor = settings.edit();
        editor.putString("usernId", user.getPhone_number());
        editor.commit();   // 提交更改
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
