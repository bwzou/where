package com.creation.where.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.creation.where.R;

public class WelcomeActivity extends Activity {
	private Button wBtnRegister;
	private Button wBtnLogin;
	private ImageButton wIbtnAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		wBtnRegister = (Button) findViewById(R.id.welcome_btn_register);
		wBtnLogin = (Button) findViewById(R.id.welcome_btn_login);
		wIbtnAbout = (ImageButton) findViewById(R.id.welcome_ibtn_about);
		
		this.wBtnLogin.setOnClickListener(new BtnLoginClick());
		this.wBtnRegister.setOnClickListener(new BtnLoginClick());
		this.wIbtnAbout.setOnClickListener(new BtnLoginClick());
	}
	
	private class BtnLoginClick implements View.OnClickListener{
		@Override
		public void onClick(View arg0) {
			if (arg0.getId() == R.id.welcome_btn_login) {
				Intent intent = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				finish();
			}
			if (arg0.getId() == R.id.welcome_btn_register) {
				Intent intent = new Intent(WelcomeActivity.this,
						RegisterActivity.class);
				startActivity(intent);
				finish();
			}
			if (arg0.getId() == R.id.welcome_ibtn_about) {
				Intent intent = new Intent(WelcomeActivity.this,
						AboutActivity.class);
				startActivity(intent);
				finish();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome, menu);
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
