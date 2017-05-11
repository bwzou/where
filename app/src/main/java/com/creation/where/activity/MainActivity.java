package com.creation.where.activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.creation.where.R;
import com.creation.where.base.BaseActivity;
import com.creation.where.fragment.AccountFragment;
import com.creation.where.fragment.CircleFragment;
import com.creation.where.fragment.StatusFragment;
import com.creation.where.fragment.TogetherFragment;
import com.creation.where.util.ChatEmojiUtils;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity extends BaseActivity {
	protected static final String TAG = "MainActivity";
    private StatusFragment statusFragment;
    private TogetherFragment togetherFragment;
    private AccountFragment accountFragment;
    private CircleFragment circleFragment;
    private Fragment[] fragments;

    private int currentTabIndex;
    // user logged into another device
    public boolean isConflict = false;
    // user account was removed
    private boolean isCurrentAccountRemoved = false;

    /**
     * check if current user account was remove
     */
    public boolean getCurrentAccountRemoved() {
        return isCurrentAccountRemoved;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        initUI();

		statusFragment=new StatusFragment();
        togetherFragment = new TogetherFragment();
		accountFragment = new AccountFragment();
        circleFragment = new CircleFragment();
	    getFragmentManager().beginTransaction().add(R.id.fragment_container,statusFragment).add(R.id.fragment_container,circleFragment).hide(circleFragment).show(statusFragment).commit();
	    fragments = new Fragment[]{statusFragment, circleFragment, togetherFragment, accountFragment};

        //获取表情包
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChatEmojiUtils.getInstace().getFileText(getApplication());
            }
        }).start();
	}

    private void initUI() {
        final String[] colors = getResources().getStringArray(R.array.default_preview);
        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.main_bottom);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_first),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("哪儿")
                        .badgeTitle("state")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_third),
                        Color.parseColor(colors[2]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("圈子")
                        .badgeTitle("new")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fourth),
                        Color.parseColor(colors[3]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("拼途")
                        .badgeTitle("with")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.ic_fifth),
                        Color.parseColor(colors[4]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("我")
                        .badgeTitle("icon")
                        .build()
        );

        navigationTabBar.setModels(models);
        //IMPORTANT: ENABLE SCROLL BEHAVIOUR IN COORDINATOR LAYOUT
        navigationTabBar.setBehaviorEnabled(true);

        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(final NavigationTabBar.Model model, final int index) {
            }

            @Override
            public void onEndTabSelected(final NavigationTabBar.Model model, final int index) {
                if (currentTabIndex != index) {
                    FragmentTransaction trx = getFragmentManager().beginTransaction();
                    trx.hide(fragments[currentTabIndex]);
                    if (!fragments[index].isAdded()) {
                        trx.add(R.id.fragment_container, fragments[index]);
                    }
                    trx.show(fragments[index]).commit();
                }
                currentTabIndex = index;
                model.hideBadge();
            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }
	
//	/**
//     * on tab clicked
//     * @param view
//     */
////    public void onTabClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_status_list:
//                index = 0;
//                break;
//            case R.id.btn_chat_list:
//                index = 1;
//                break;
//            case R.id.btn_find_list:
//                index = 2;
//                break;
//            case R.id.btn_account_list:
//                index = 3;
//                break;
//        }
//        if (currentTabIndex != index) {
//            FragmentTransaction trx = getFragmentManager().beginTransaction();
//            trx.hide(fragments[currentTabIndex]);
//            if (!fragments[index].isAdded()) {
//                trx.add(R.id.fragment_container, fragments[index]);
//            }
//            trx.show(fragments[index]).commit();
//        }
//        mTabs[currentTabIndex].setSelected(false);
//        // set current tab selected
//        mTabs[index].setSelected(true);
//        currentTabIndex = index;
//    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
