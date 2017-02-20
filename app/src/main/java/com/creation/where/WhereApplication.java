package com.creation.where;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import com.baidu.mapapi.SDKInitializer;

import org.json.JSONObject;

public class WhereApplication extends Application {
	public static Context applicationContext;
	private static WhereApplication instance;
	// login user name
	public final String PREF_USERNAME = "username";
	public static String currentUserNick = "";
	private JSONObject userJson;
	private String time = "";

	private DisplayMetrics displayMetrics = null;

	public static WhereApplication getApp() {
		if (instance != null && instance instanceof WhereApplication) {
			return (WhereApplication) instance;
		} else {
			instance = new WhereApplication();
			instance.onCreate();
			return (WhereApplication) instance;
		}
	}

	public static boolean sRunningOnIceCreamSandwich;

	static {
		sRunningOnIceCreamSandwich = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		SDKInitializer.initialize(this);
		applicationContext = this;
		instance = this;
	}

	public static WhereApplication getInstance() {
		return instance;
	}

	public String getTime(){
		return time;

	}
	public void setTime(String time){
		this.time=time;
	}

	// 获取应用的data/data/....File目录
	public String getFilesDirPath() {
		return getFilesDir().getAbsolutePath();
	}

	// 获取应用的data/data/....Cache目录
	public String getCacheDirPath() {
		return getCacheDir().getAbsolutePath();
	}
	
}
