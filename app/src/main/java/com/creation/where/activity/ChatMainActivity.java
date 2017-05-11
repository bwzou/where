package com.creation.where.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapViewLayoutParams;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.creation.where.R;
import com.creation.where.base.BaseActivity;
import com.creation.where.po.MapChatMsg;
import com.creation.where.po.Message;
import com.creation.where.po.Param;
import com.creation.where.util.Constants;
import com.creation.where.util.HttpUtils;
import com.creation.where.util.LocationUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.creation.where.util.DebugUtils.ShowErrorInf;

public class ChatMainActivity extends BaseActivity {
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    MapView mMapView;
    BaiduMap mBaiduMap;

    //聊天
    private View onePop;
    private TextView oneMsg;
    public BDLocation myLocate = new BDLocation();
    public Marker marker=null;
    boolean isMyFirstMsg=true;
    boolean isFirstLoc = true;
    public Gson gson = new Gson();
    private Handler handler;
    private View otherPop;
    private TextView otherMsg;
    public Marker otherMarker=null;
    boolean isOtherFirstMsg=true;
    boolean isOtherFirstLoc=true;
    private Message otherMessage=null;

    //内容编辑
    Button requestLocButton;
    private Button mBtnSend;
    private EditText mEditTextContent;
    public View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        requestLocButton = (Button) findViewById(R.id.chat_btn);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        requestLocButton.setText("普通");
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        requestLocButton.setText("跟随");
                        requestLocButton.setBackgroundColor(Color.parseColor("#90000000"));
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case COMPASS:
                        requestLocButton.setText("普通");
                        requestLocButton.setBackgroundColor(Color.parseColor("#50000000"));
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case FOLLOWING:
                        requestLocButton.setText("罗盘");
                        requestLocButton.setBackgroundColor(Color.parseColor("#70000000"));
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);

        view=(View)findViewById(R.id.chat_rl_bottom);
        mBtnSend=(Button)findViewById(R.id.btn_sendmessage);
        mEditTextContent=(EditText)findViewById(R.id.et_sendmessage);
        View.OnClickListener btnSendClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                //获取内容并且显示
                setMessage();
                drawMark(myLocate);
                if(isMyFirstMsg){
                    initPop(myLocate);
                    isMyFirstMsg=false;
                }else
                    updatePop(myLocate);
                //清空EditText内容
                mEditTextContent.setText("");
            }
        };
        mBtnSend.setOnClickListener(btnSendClickListener);

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmap_chat_view);
        mBaiduMap = mMapView.getMap();
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.test_money_logo);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker,
                accuracyCircleFillColor, accuracyCircleStrokeColor));
        mMapView.removeViewAt(1);//移除百度图标

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        //获取数据
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        MapChatMsg mapChatMsg=(MapChatMsg)bundle.getSerializable("mapChatMsg");

        this.handler=new Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 1) {
                    if(otherMessage.getMsg_id()!=0){
                        if(isOtherFirstLoc){
                            isOtherFirstLoc=false;
                            LatLng ll = new LatLng((myLocate.getLatitude()+otherMessage.getLatitude())/2,
                                    (myLocate.getLongitude()+otherMessage.getLongitude())/2);
                            MapStatus.Builder builder = new MapStatus.Builder();
                            double dist= LocationUtils.getDistance(myLocate.getLatitude(),myLocate.getLongitude(),otherMessage.getLatitude(),otherMessage.getLongitude());
                            double d=dist/8;
                            float level=0.0f;
                            for(int i=0;i<Constants.Distance.length;i++){
                                if(d<Constants.Distance[i]) {
                                    level = Constants.Level[i];
                                    Log.i("ChatMainActivity Level"," "+level);
                                    break;
                                }
                            }
                            builder.target(ll).zoom(level);
                            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                        }
//                  drawOtherMark(otherMessage.getLatitude(),otherMessage.getLongitude());
                        drawOtherMark(22.747752,113.604378);
                        if(isOtherFirstMsg){
                            initOtherPop(otherMessage.getMsg(),otherMessage.getLatitude(),otherMessage.getLongitude());
                            isOtherFirstMsg=false;
                        }else {
                            updateOtherPop(otherMessage.getMsg(), otherMessage.getLatitude(), otherMessage.getLongitude());
                        }
                    }
                } else if (msg.what == 0){
                    ShowErrorInf( "服务器请求异常！");
                }
                else if (msg.what == -1){
                    ShowErrorInf( "网络异常！");
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                getMessage();
            }
        }, 0, 10000); //0秒之后，每隔10秒做一次run()操作
    }

    /**
     *消息存储
     */
    private void setMessage(){
        //构造Message类
        Message message=new Message();
        message.setFrom_user_id(Constants.user.getId());
        message.setTo_user_id(13);
        message.setMsg(mEditTextContent.getText().toString());
        message.setLatitude(myLocate.getLatitude());
        message.setLongitude(myLocate.getLongitude());

        final List<Param> params=new ArrayList<>();
        params.add(new Param("option","insert"));
        params.add(new Param("message",message));
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                String jsonString = gson.toJson(params);
                StringBuffer content = new StringBuffer();
                content.append("content=").append(jsonString);
                String encode = "utf-8";
                String res= HttpUtils.getJsonContent(Constants.USR_MESSAGE,encode,content);

                Looper.prepare();
                if (res.equals("")||res==null) {
                    handler.sendEmptyMessage(-1);
                }
                Looper.loop();
            }
        }).start();
    }

    /**
     * 消息获取
     */
    private Message getMessage() {     //实际生产中都是有服务器推送的
        //查找最近一次的未读消息
        Message message=new Message();
        message.setFrom_user_id(Constants.user.getId());
        message.setTo_user_id(13);

        final List<Param> params=new ArrayList<>();
        params.add(new Param("option","selectOne"));
        params.add(new Param("message",message));
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                String jsonString = gson.toJson(params);
                StringBuffer content = new StringBuffer();
                content.append("content=").append(jsonString);
                String encode = "utf-8";
                String res= HttpUtils.getJsonContent(Constants.USR_MESSAGE,encode,content);

                Looper.prepare();
                if (res.equals("")||res==null) {
                    handler.sendEmptyMessage(-1);
                }else {
                    handler.sendEmptyMessage(1);
                    otherMessage=gson.fromJson(res,Message.class);
                }
                Looper.loop();
            }
        }).start();
        return null;
    }

    /**
     * 初始化个人的pop
     * @param location
     */
    private void initPop(BDLocation location) {
        onePop = View.inflate(getApplicationContext(), R.layout.custom_pop, null);
        //必须使用百度的params
        ViewGroup.LayoutParams params = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.mapMode) //按照经纬度设置
                .position(new LatLng(location.getLatitude(), location.getLongitude())) //这个坐标无所谓的，但是不能传null
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .build();
        mMapView.addView(onePop,params);
        //默认设置显示
        onePop.setVisibility(View.VISIBLE);
        //初始化这个title
        oneMsg = (TextView) onePop.findViewById(R.id.et_msg);
        oneMsg.setText(mEditTextContent.getText());     //不能使用toString方法，因为存在SpannableString
    }

    /**
     * 更新个人消息显示的pop
     * @param location
     */
    private void updatePop(BDLocation location){
        ViewGroup.LayoutParams params = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .build();
        mMapView.updateViewLayout(onePop, params);
        onePop.setVisibility(View.VISIBLE);
        oneMsg = (TextView) onePop.findViewById(R.id.et_msg);
        oneMsg.setText(mEditTextContent.getText());
    }

    /**
     * 绘制个人mark覆盖物
     * @param location
     */
    private void drawMark(BDLocation location) {
        if(marker!=null)
            marker.remove();
        MarkerOptions markerOptions = new MarkerOptions();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.test_money_logo); // 描述图片
        markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude())) // 设置位置
                .icon(bitmap)   // 加载图片
                .draggable(true)   // 支持拖拽
                .title("世界之窗旁边的草房");  // 显示文本
        //把绘制的圆添加到百度地图上去
        marker=(Marker)mBaiduMap.addOverlay(markerOptions);
    }

    /**
     * 初始化对方的pop
     * @param text
     * @param latitude
     * @param longitude
     */
    private void initOtherPop(String text,double latitude,double longitude) {
        otherPop = View.inflate(getApplicationContext(), R.layout.custom_pop_other, null);
        //必须使用百度的params
        ViewGroup.LayoutParams params = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .position(new LatLng(latitude, longitude))
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .yOffset(-50)
                .build();
        mMapView.addView(otherPop,params);

        otherPop.setVisibility(View.VISIBLE);
        otherMsg = (TextView) otherPop.findViewById(R.id.other_msg);
        otherMsg.setText(text);
    }

    /**
     * 更新对方消息显示的pop
     * @param text
     * @param latitude
     * @param longitude
     */
    private void updateOtherPop(String text,double latitude,double longitude){
        ViewGroup.LayoutParams params = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .position(new LatLng(latitude, longitude))
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                .build();
        mMapView.updateViewLayout(otherPop, params);
        otherPop.setVisibility(View.VISIBLE);
        otherMsg = (TextView) otherPop.findViewById(R.id.other_msg);
        otherMsg.setText(text);
    }

    /**
     * 绘制对方mark覆盖物
     * @param latitude
     * @param longitude
     */
    private void drawOtherMark(double latitude,double longitude) {
        if(otherMarker!=null)
            otherMarker.remove();
        MarkerOptions markerOptions = new MarkerOptions();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.test_zhirun_logo);
        markerOptions.position(new LatLng(latitude, longitude))
                .icon(bitmap)
                .draggable(true)
                .title("世界之窗旁边的草房");

        otherMarker=(Marker)mBaiduMap.addOverlay(markerOptions);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

            myLocate.setLatitude(location.getLatitude());
            myLocate.setLongitude(location.getLongitude());
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
