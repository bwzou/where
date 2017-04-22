package com.creation.where.activity;

import android.app.Activity;
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
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.creation.where.R;
import com.creation.where.po.Footprint;
import com.creation.where.po.Param;
import com.creation.where.util.Constants;
import com.creation.where.util.HttpUtils;
import com.creation.where.util.LocationUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.creation.where.util.Constants.user;
import static com.creation.where.util.DebugUtils.ShowErrorInf;

public class FootPrintActivity extends Activity {

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    MapView mMapView;
    BaiduMap mBaiduMap;

    Button requestLocButton;
    Button btnEdit;
    boolean isFirstLoc = true;
    boolean isFirstAdjust=true;
    private Handler handler;
    public Gson gson = new Gson();
    public ArrayList<Footprint> aroundFootprint=null;

    //发表足记
    private View pop;
    private TextView title;
    public BDLocation poplocate = new BDLocation();    //使用者的地理位置
    private Button mBtnSend;
    private EditText mEditTextContent;
    public View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_print);
        requestLocButton = (Button) findViewById(R.id.footprint_btn);
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

        btnEdit=(Button)findViewById(R.id.footprint_edit);
        view=(View)findViewById(R.id.footprint_rl_bottom);
        View.OnClickListener btnEditClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                String str=btnEdit.getText().toString();
                if(str.equals("隐藏")){
                    btnEdit.setText("编辑");
                    btnEdit.setBackgroundColor(Color.parseColor("#90000000"));
                    view.setVisibility(View.GONE);
                }else{
                    btnEdit.setText("隐藏");
                    btnEdit.setBackgroundColor(Color.parseColor("#70000000"));
                    view.setVisibility(View.VISIBLE);
                }
            }
        };
        btnEdit.setOnClickListener(btnEditClickListener);

        mBtnSend=(Button)findViewById(R.id.btn_sendmessage);
        mEditTextContent=(EditText)findViewById(R.id.et_sendmessage);
        View.OnClickListener btnSendClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                //获取内容并且显示
                drawMark(poplocate);
                initPop(poplocate);
                setFootprint(poplocate);
                mEditTextContent.setText("");
            }
        };
        mBtnSend.setOnClickListener(btnSendClickListener);

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmap_footprint_view);
        mBaiduMap = mMapView.getMap();
        mCurrentMarker = BitmapDescriptorFactory.fromResource(R.drawable.test_money_logo);
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration( mCurrentMode, true, mCurrentMarker,
                                            accuracyCircleFillColor, accuracyCircleStrokeColor));
        mMapView.removeViewAt(1);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();

        this.handler=new Handler(){
            @Override
            public void handleMessage(android.os.Message msg) {
                if (msg.what == 1) {
                    if(isFirstAdjust){
                        isFirstAdjust=false;
                        LatLng ll = new LatLng(poplocate.getLatitude(), poplocate.getLongitude());
                        MapStatus.Builder builder = new MapStatus.Builder();
                        double dist,tmp,tmp2;
                        if(poplocate.getLatitude()>0) {
                            tmp = LocationUtils.getDistance(poplocate.getLatitude(), poplocate.getLongitude(), poplocate.getLatitude() - 0.05, poplocate.getLongitude() - 0.05);
                            tmp2 = LocationUtils.getDistance(poplocate.getLatitude(), poplocate.getLongitude(), poplocate.getLatitude() - 0.05, poplocate.getLongitude() + 0.05);
                        }else {
                            tmp = LocationUtils.getDistance(poplocate.getLatitude(), poplocate.getLongitude(), poplocate.getLatitude() + 0.05, poplocate.getLongitude() - 0.05);
                            tmp2 = LocationUtils.getDistance(poplocate.getLatitude(), poplocate.getLongitude(), poplocate.getLatitude() + 0.05, poplocate.getLongitude() + 0.05);
                        }
                        dist=tmp+tmp2;
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
                    if(aroundFootprint!=null){
                        for(int i=0;i<aroundFootprint.size();i++){
                            BDLocation locate = new BDLocation();
                            locate.setLatitude(aroundFootprint.get(i).getLatitude());
                            locate.setLongitude(aroundFootprint.get(i).getLongitude());
                            initPop(locate,aroundFootprint.get(i).getComment());      //开始显示在地图上面
                            drawMark(locate);
                        }
                    }
                }else if(msg.what==2){
                    ShowErrorInf( "添加成功！");
                }

            }
        };

        Timer timer=new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run(){
                getAroundFootPrint(poplocate);
                this.cancel();    //只执行一次
            }
        },2000);

    }

    /**
     * 留下一个印记
     * @param location
     * @return
     */
    public void setFootprint(BDLocation location){
        //构造Footprint类
        Footprint footprint=new Footprint();
        footprint.setUser_id(user.getId());
        footprint.setComment(mEditTextContent.getText().toString());
        footprint.setLatitude(location.getLatitude());
        footprint.setLongitude(location.getLongitude());
        Log.i("setFootprint位置为维度"+location.getLatitude(),"经度"+location.getLongitude());
        final List<Param> params=new ArrayList<>();

        params.add(new Param("option","insert"));
        params.add(new Param("footprint",footprint));
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                String jsonString = gson.toJson(params);
                StringBuffer content = new StringBuffer();
                content.append("content=").append(jsonString);
                String encode = "utf-8";
                String res= HttpUtils.getJsonContent(Constants.USR_FOOTPRINT,encode,content);

                Looper.prepare();
                if (res.equals("")||res==null) {
                    handler.sendEmptyMessage(-1);
                }else{
                    handler.sendEmptyMessage(2);
                }
                Looper.loop();
            }
        }).start();
    }

    /**
     * 获取附近的足记
     * @param location
     * @return
     */
    public void getAroundFootPrint(BDLocation location){
        if(location==null)
            return;
        Log.i("getAroundFootPrint位置为维度"+location.getLatitude(),"经度"+location.getLongitude());
        final List<Param> params=new ArrayList<>();
        params.add(new Param("option","getaround"));
        params.add(new Param("latitude",location.getLatitude()));
        params.add(new Param("longitude",location.getLongitude()));
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                String jsonString = gson.toJson(params);
                StringBuffer content = new StringBuffer();
                content.append("content=").append(jsonString);
                String encode = "utf-8";
                String res= HttpUtils.getJsonContent(Constants.USR_FOOTPRINT,encode,content);

                Looper.prepare();
                if (res.equals("")||res==null) {
                    handler.sendEmptyMessage(-1);
                }else {
                    aroundFootprint=gson.fromJson(res,new TypeToken<ArrayList<Footprint>>(){}.getType());
                    handler.sendEmptyMessage(1);
                }
                Looper.loop();
            }
        }).start();
    }

    /**
     * 初始化评论的pop
     * @param location
     */
    private void initPop(BDLocation location) {
        pop = View.inflate(getApplicationContext(), R.layout.custom_pop, null);
        Log.i("initPop位置为维度"+location.getLatitude(),"经度"+location.getLongitude());

        //必须使用百度的params
        ViewGroup.LayoutParams params = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                //.yOffset(-5)  //相距  正值往下  负值往上
                .build();
        mMapView.addView(pop,params);

        pop.setVisibility(View.VISIBLE);
        title = (TextView) pop.findViewById(R.id.et_msg);
        title.setText(mEditTextContent.getText());  //不能使用toString方法，因为存在SpannableString
    }

    /**
     * 初始化评论的pop
     * @param location
     * @param text
     */
    private void initPop(BDLocation location,String text) {
        pop = View.inflate(getApplicationContext(), R.layout.custom_pop, null);
        Log.i("initPop位置为维度"+location.getLatitude(),"经度"+location.getLongitude());

        //必须使用百度的params
        ViewGroup.LayoutParams params = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.mapMode)
                .position(new LatLng(location.getLatitude(), location.getLongitude()))
                .width(MapViewLayoutParams.WRAP_CONTENT)
                .height(MapViewLayoutParams.WRAP_CONTENT)
                //.yOffset(-5)
                .build();
        mMapView.addView(pop,params);

        pop.setVisibility(View.VISIBLE);
        title = (TextView) pop.findViewById(R.id.et_msg);
        title.setText(text);  //不能使用toString方法，因为存在SpannableString
    }

    /**
     * 绘制mark覆盖物
     * @param location
     */
    private void drawMark(BDLocation location) {
        Log.i("drawMark位置为维度"+location.getLatitude(),"经度"+location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.test_money_logo);
        markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude()))
                .icon(bitmap)
                .draggable(true)
                .title("世界之窗旁边的草房");

        mBaiduMap.addOverlay(markerOptions);
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

            poplocate.setLatitude(location.getLatitude());
            poplocate.setLongitude(location.getLongitude());
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

