package com.creation.where.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.creation.where.R;
import com.creation.where.po.Param;
import com.creation.where.po.Together;
import com.creation.where.util.Constants;
import com.creation.where.util.HttpUtils;
import com.google.gson.Gson;
import com.hh.timeselector.timeutil.datedialog.DateListener;
import com.hh.timeselector.timeutil.datedialog.TimeConfig;
import com.hh.timeselector.timeutil.datedialog.TimeSelectorDialog;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.enums.LemonBubbleLayoutStyle;
import net.lemonsoft.lemonbubble.enums.LemonBubbleLocationStyle;
import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.parseDouble;

public class TogetherActivity extends Activity {
    private EditText et_selectTime;
    private EditText et_destination;
    private EditText et_event;
    private EditText et_people_number;
    private EditText et_transportation;
    private EditText et_cost_money;
    private Button btn_publish;
    private Button btn_selectTime;
    private TextView tv_xieyi;
    private Handler handler;
    public Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together);

        et_selectTime=(EditText) findViewById(R.id.et_selectTime);
        et_destination=(EditText) findViewById(R.id.et_destination);
        et_event=(EditText) findViewById(R.id.et_event);
        et_people_number=(EditText) findViewById(R.id.et_people_number);
        et_transportation=(EditText) findViewById(R.id.et_transportation);
        et_cost_money=(EditText) findViewById(R.id.et_cost_money);
        btn_publish=(Button) findViewById(R.id.btn_publish);
        btn_selectTime=(Button) findViewById(R.id.btn_selectTime);

        // 监听多个输入框
        et_selectTime.addTextChangedListener(new TextChange());
        et_destination.addTextChangedListener(new TextChange());
        et_event.addTextChangedListener(new TextChange());
        et_people_number.addTextChangedListener(new TextChange());
        et_transportation.addTextChangedListener(new TextChange());
        et_cost_money.addTextChangedListener(new TextChange());

        btn_publish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LemonHello.getInformationHello("您确定要发布吗？", "发布行程后您将找到小伙伴一起同行。")
                        .addAction(new LemonHelloAction("取消", new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                            }
                        }))
                        .addAction(new LemonHelloAction("我要发布", Color.GREEN, new LemonHelloActionDelegate() {
                            @Override
                            public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                                helloView.hide();
                                // 提示框使用了LemonBubble，请您参考：https://github.com/1em0nsOft/LemonBubble4Android
                                LemonBubble.getRoundProgressBubbleInfo()
                                        .setLocationStyle(LemonBubbleLocationStyle.BOTTOM)
                                        .setLayoutStyle(LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT)
                                        .setBubbleSize(200, 50)
                                        .setProportionOfDeviation(0.1f)
                                        .setTitle("正在请求服务器...")
                                        .show(TogetherActivity.this);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        setTogether();
                                    }
                                }, 1500);
                            }
                        }))
                        .show(TogetherActivity.this);
            }
        });

        tv_xieyi = (TextView) findViewById(R.id.tv_togetherxieyi);
        String xieyi = "<font color=" + "\"" + "#AAAAAA" + "\">" + "点击上面的"
                + "\"" + "发布" + "\"" + "按钮,即表示你同意" + "</font>" + "<u>"
                + "<font color=" + "\"" + "#576B95" + "\">" + "《一起拼途用户协议》"
                + "</font>" + "</u>";
        tv_xieyi.setText(Html.fromHtml(xieyi));

        this.handler=new Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 1) {
                    LemonBubble.showRight(TogetherActivity.this, "发布成功，您可以在我的行程中查看！", 2000);
                }else if(msg.what == -1){
                    LemonBubble.showRight(TogetherActivity.this, "发布失败，网络错误！", 2000);
                }
            }
        };
    }

    /**
     * 发布行程
     */
    private void setTogether(){
        //构造Together类
        Together together=new Together();
        together.setUser_id(Constants.user.getId());
        together.setWhat_time(et_selectTime.getText().toString());
        together.setDestination(et_destination.getText().toString());
        together.setEvent(et_event.getText().toString());
        together.setCost(parseDouble(et_cost_money.getText().toString()));
        together.setTransportation(et_transportation.getText().toString());

        final List<Param> params=new ArrayList<>();
        params.add(new Param("option","insert"));
        params.add(new Param("together",together));
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jsonString = gson.toJson(params);
                StringBuffer content = new StringBuffer();
                content.append("content=").append(jsonString);
                String encode = "utf-8";
                String res= HttpUtils.getJsonContent(Constants.USR_TOGETHER,encode,content);

                Looper.prepare();
                if (res.equals("")||res==null) {
                    handler.sendEmptyMessage(-1);
                }else{
                    handler.sendEmptyMessage(1);
                }
                Looper.loop();
            }
        }).start();
    }


    /**
     * 设计时间选择控件形式
     * @param type
     */
    private void showDatePickDialog(int type) {
        TimeSelectorDialog dialog = new TimeSelectorDialog(this);
        //设置标题
        dialog.setTimeTitle("选择时间:");
        //显示类型
        dialog.setIsShowtype(TimeConfig.YEAR_MONTH_DAY_HOUR_MINUTE);
        //默认时间
        dialog.setCurrentDate("2017-01-11　14:50");
        //隐藏清除按钮
        dialog.setEmptyIsShow(false);
        dialog.setDateListener(new DateListener() {
            @Override
            public void onReturnDate(String time,int year, int month, int day, int hour, int minute, int isShowType) {
                et_selectTime.setText(time);
            }
            @Override
            public void onReturnDate(String empty) {
                et_selectTime.setText(empty);
            }
        });
        dialog.show();
    }

    /**
     * 选择时间
     * @param view
     */
    public void selectTime(View view){
        showDatePickDialog(TimeConfig.YEAR_MONTH_DAY_HOUR_MINUTE);
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
            boolean Sign1 = et_selectTime.getText().length() > 0;
            boolean Sign2 = et_destination.getText().length() > 0;
            boolean Sign3 = et_event.getText().length() > 0;
            boolean Sign4 = et_people_number.getText().length() > 0;
            boolean Sign5 = et_transportation.getText().length() > 0;
            boolean Sign6 = et_cost_money.getText().length() > 0;
            if (Sign1 & Sign2 & Sign3 & Sign4 & Sign5 & Sign6) {
                btn_publish.setEnabled(true);
            }
            // 在layout文件中，对Button的text属性应预先设置默认值，否则刚打开程序的时候Button是无显示的
            else {
                btn_publish.setEnabled(false);
            }
        }
    }

    public void back(View view) {
        finish();
    }
}
