package com.creation.where.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.creation.where.R;
import com.creation.where.po.User;
import com.creation.where.util.Constants;
import com.creation.where.util.HttpUtils;

import static com.creation.where.util.DebugUtils.ShowErrorInf;

public class RegisterActivity extends Activity {
	private EditText et_usernick;
    private EditText et_usertel;
    private EditText et_password;
    private Button btn_register;
    private TextView tv_xieyi;
    private ImageView iv_hide;
    private ImageView iv_show;
    private ImageView iv_photo;
    
    private String imageName = "false";
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private Handler handler;
    public  ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		et_usernick = (EditText) findViewById(R.id.et_usernick);
        et_usertel = (EditText) findViewById(R.id.et_usertel);
        et_password = (EditText) findViewById(R.id.et_password);
        
        // 监听多个输入框
        et_usernick.addTextChangedListener(new TextChange());
        et_usertel.addTextChangedListener(new TextChange());
        et_password.addTextChangedListener(new TextChange());
        btn_register = (Button) findViewById(R.id.btn_register);
        tv_xieyi = (TextView) findViewById(R.id.tv_xieyi);
        iv_hide = (ImageView) findViewById(R.id.iv_hide);

        iv_show = (ImageView) findViewById(R.id.iv_show);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);
        
        String xieyi = "<font color=" + "\"" + "#AAAAAA" + "\">" + "点击上面的"
                + "\"" + "注册" + "\"" + "按钮,即表示你同意" + "</font>" + "<u>"
                + "<font color=" + "\"" + "#576B95" + "\">" + "《微在软件许可及服务协议》"
                + "</font>" + "</u>";

        tv_xieyi.setText(Html.fromHtml(xieyi));
        iv_hide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_hide.setVisibility(View.GONE);
                iv_show.setVisibility(View.VISIBLE);
                et_password
                        .setTransformationMethod(HideReturnsTransformationMethod
                                .getInstance());
                // 切换后将EditText光标置于末尾
                CharSequence charSequence = et_password.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });
        
        iv_show.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_show.setVisibility(View.GONE);
                iv_hide.setVisibility(View.VISIBLE);
                et_password
                        .setTransformationMethod(PasswordTransformationMethod
                                .getInstance());
                // 切换后将EditText光标置于末尾
                CharSequence charSequence = et_password.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
            }
        });
        
        iv_photo.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	//showCamera()
            	Toast.makeText(RegisterActivity.this,
                        "调用照相机", Toast.LENGTH_SHORT).show();
            }
        });

        btn_register.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernick = et_usernick.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String usertel = et_usertel.getText().toString().trim();
                pd = new ProgressDialog(RegisterActivity.this);
                pd.setCanceledOnTouchOutside(false);
                pd.setMessage(getString(R.string.is_registering));
                pd.show();
                register(usernick, password, usertel);

            }
        });

        this.handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ShowErrorInf( "注册成功！");
                    setUser(Constants.user);
                    Intent intent = new Intent(RegisterActivity.this,  MainActivity.class);
                    startActivity(intent);
                    finish();
                    pd.dismiss();
                } else if (msg.what == -1){
                    pd.dismiss();
                    ShowErrorInf( "服务器请求异常！");
                }
                else if (msg.what == -2){
                    pd.dismiss();
                    ShowErrorInf( "该号码已经被注册！");
                }
                else if (msg.what == 0){
                    pd.dismiss();
                    ShowErrorInf( "网络异常！");
                }
            }
        };
	}

    /**
     * 用户注册
     * @param usernick
     * @param password
     * @param usertel
     */
    private void register(String usernick, String password, String usertel) {
//        List<Param> params=new ArrayList<>();
//        params.add(new Param("option","login"));
//        params.add(new Param("username",phone_number));
//        params.add(new Param("password",password));

        Constants.user=new User();
        Constants.user.setNickname(usernick);
        Constants.user.setPhone_number(usertel);
        Constants.user.setPassword(password);
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                // 发送用户名和密码到服务器进行校验，并获得服务器返回值
                StringBuffer params = new StringBuffer();
                params.append("option=").append("register");
                params.append("&nickname=").append(Constants.user.getNickname());
                params.append("&phone_number=").append(Constants.user.getPhone_number());
                params.append("&password=").append(Constants.user.getPassword());
                String encode = "utf-8";

                String res= HttpUtils.getJsonContent(Constants.USR_LOGIN,encode,params);
                Looper.prepare();
                if (res.equals("")||res==null) {
                    handler.sendEmptyMessage(-1);
                }else{
                    int tmp=Integer.parseInt(res);
                    if(tmp==1)
                        handler.sendEmptyMessage(1);
                    else if(tmp==-1)
                        handler.sendEmptyMessage(-1);
                    else if(tmp==-2)
                        handler.sendEmptyMessage(-2);
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
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("userPhoneNumber", user.getPhone_number());
        editor.commit();
    }

    /**
     * EditText监听器
     */
    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,int count) {

            boolean Sign1 = et_usernick.getText().length() > 0;
            boolean Sign2 = et_usertel.getText().length() > 0;
            boolean Sign3 = et_password.getText().length() > 0;
            if (Sign1 & Sign2 & Sign3) {
                btn_register.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                btn_register.setEnabled(false);
            }
        }

    }
    
    public void back(View view) {
        finish();
    }

}
