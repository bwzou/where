package com.creation.where.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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


public class LocationMainActivity extends Activity {

    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    private static final int accuracyCircleFillColor = 0xAAFFFF88;
    private static final int accuracyCircleStrokeColor = 0xAA00FF00;

    MapView mMapView;
    BaiduMap mBaiduMap;

    // UI相关
    Button requestLocButton;
    Button btnEdit;
    boolean isFirstLoc = true; // 是否首次定位

    //发表言论
    private View pop;       //要显示的pop
    private TextView title;     //pop中的文本信息
    public BDLocation poplocate = new BDLocation();

    //内容编辑
    private Button mBtnSend;
    private EditText mEditTextContent;
    public View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_main);
        requestLocButton = (Button) findViewById(R.id.button1);
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

        btnEdit=(Button)findViewById(R.id.btn_edit);
        view=(View)findViewById(R.id.rl_bottom);
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

                //清空EditText内容
                mEditTextContent.setText("");
            }
        };
        mBtnSend.setOnClickListener(btnSendClickListener);

        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
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
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
    }

    //初始化评论的pop
    private void initPop(BDLocation location) {
        pop = View.inflate(getApplicationContext(), R.layout.custom_pop, null);
        Log.i("位置为维度"+location.getLatitude(),"经度"+location.getLongitude());

        //必须使用百度的params
        ViewGroup.LayoutParams params = new MapViewLayoutParams.Builder().layoutMode(MapViewLayoutParams.ELayoutMode.mapMode) //按照经纬度设置
                .position(new LatLng(location.getLatitude(), location.getLongitude())) //这个坐标无所谓的，但是不能传null
                .width(MapViewLayoutParams.WRAP_CONTENT)  //宽度
                .height(MapViewLayoutParams.WRAP_CONTENT)  //高度
                //.yOffset(-5)  //相距  正值往下  负值往上
                .build();
        mMapView.addView(pop,params);
        //默认设置显示
        pop.setVisibility(View.VISIBLE);
        //初始化这个title
        title = (TextView) pop.findViewById(R.id.et_msg);
        title.setText(mEditTextContent.getText());  //不能使用toString方法，因为存在SpannableString
    }

    // 绘制mark覆盖物
    private void drawMark(BDLocation location) {
        Log.i("位置为维度"+location.getLatitude(),"经度"+location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.test_money_logo); // 描述图片
        markerOptions.position(new LatLng(location.getLatitude(), location.getLongitude())) // 设置位置
                .icon(bitmap) // 加载图片
                .draggable(true) // 支持拖拽
                .title("世界之窗旁边的草房"); // 显示文本
        //把绘制的圆添加到百度地图上去
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

            //获取位置信息
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
