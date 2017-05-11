/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package com.creation.where.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.creation.where.R;
import com.creation.where.manager.SystemBarTintManager;

/**基础android.support.v4.app.FragmentActivity，通过继承可获取或使用 里面创建的 组件 和 方法
 * *onFling内控制左右滑动手势操作范围，可自定义
 * @author Lemon
 * @see #setContentView
 * @see #onDestroy
 * @use extends BaseActivity, 具体参考 .DemoActivity 和 .DemoFragmentActivity
 */
public abstract class BaseActivity extends AppCompatActivity {
	private static final String TAG = "BaseActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 默认标题TextView，layout.xml中用@id/tvBaseTitle绑定。子Activity内调用autoSetTitle方法 会优先使用INTENT_TITLE
	 * @warn 如果子Activity的layout中没有android:id="@id/tvBaseTitle"的TextView，使用前必须在子Activity中赋值
	 */
	@Nullable
	protected TextView tvBaseTitle;

	@TargetApi(Build.VERSION_CODES.KITKAT)
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);

		// 状态栏沉浸，4.4+生效 <<<<<<<<<<<<<<<<<
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.newTopbarbg); //状态背景色，可传drawable资源
		// 状态栏沉浸，4.4+生效 >>>>>>>>>>>>>>>>>
	}

}