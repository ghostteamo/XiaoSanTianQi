package com.xiaosantianqi.app.activity;

import java.util.Timer;
import java.util.TimerTask;

import com.xiaosantianqi.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartActivity extends Activity {

	private Timer timer;
	private static int TIME = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startpage);
		TimerTask task = new TimerTask() {
			public void run() {
				if (TIME == 0) {
					Intent intent = new Intent(StartActivity.this,
							ChooseAreaActivity.class);
					startActivity(intent);
					TIME = 1;
				}
				timer.cancel();
				TIME = 0;
				finish();
			}
		};

		timer = new Timer(true);
		timer.schedule(task, 2500, 1000);
	}
}
