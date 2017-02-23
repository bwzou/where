package com.creation.where.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.creation.where.R;
import com.creation.where.fragment.AccountFragment;
import com.creation.where.fragment.ConversationFragment;
import com.creation.where.fragment.FindFragment;
import com.creation.where.fragment.StatusFragment;
import com.creation.where.util.ChatEmojiUtils;

public class MainActivity extends Activity {
	
	protected static final String TAG = "MainActivity";
    // textview for unread message count
    private TextView unreadLabel;
    // textview for unread event message
    private TextView unreadAddressLable;
    
    private Button[] mTabs;
    private StatusFragment statusFragment;
    private FindFragment findFragment;
    private AccountFragment accountFragment;
    private ConversationFragment conversationFragment;
    private Fragment[] fragments;
    private int index;

    private int currentTabIndex;
    // user logged into another device
    public boolean isConflict = false;
    // user account was removed
    private boolean isCurrentAccountRemoved = false;
    
    private static final String TAG_COVERSATION = "TAG_COVERSATION";
    private static final String TAG_CONTACTS = "TAG_CONTACTS";
    private static final String TAG_FIND = "TAG_FIND";
    private static final String TAG_PROFILE = "TAG_PROFILE";
    
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

		unreadLabel = (TextView) findViewById(R.id.unread_msg_number);
        unreadAddressLable = (TextView) findViewById(R.id.unread_address_number);
        mTabs = new Button[4];
        mTabs[0] = (Button) findViewById(R.id.btn_status_list);
        mTabs[1] = (Button) findViewById(R.id.btn_chat_list);
        mTabs[2] = (Button) findViewById(R.id.btn_find_list);
        mTabs[3] = (Button) findViewById(R.id.btn_account_list);
        // select first tab
        mTabs[0].setSelected(true);

        final ImageView ivAdd= (ImageView) findViewById(R.id.iv_add);
		
		statusFragment=new StatusFragment();
		findFragment = new FindFragment();
		accountFragment = new AccountFragment();
		conversationFragment = new ConversationFragment();
	    getFragmentManager().beginTransaction().add(R.id.fragment_container,statusFragment).add(R.id.fragment_container,conversationFragment).hide(conversationFragment).show(statusFragment).commit();
	    fragments = new Fragment[]{statusFragment, conversationFragment, findFragment, accountFragment};

        //获取表情包
        new Thread(new Runnable() {
            @Override
            public void run() {
                ChatEmojiUtils.getInstace().getFileText(getApplication());
            }
        }).start();
	}

	
	/**
     * on tab clicked
     * @param view
     */
    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_status_list:
                index = 0;
                break;
            case R.id.btn_chat_list:
                index = 1;
                break;
            case R.id.btn_find_list:
                index = 2;
                break;
            case R.id.btn_account_list:
                index = 3;
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getFragmentManager().beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // set current tab selected
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }
	
    
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
