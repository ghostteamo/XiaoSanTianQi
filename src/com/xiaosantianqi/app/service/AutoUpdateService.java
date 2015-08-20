package com.xiaosantianqi.app.service;

import com.xiaosantianqi.app.receiver.AutoUpdateReceiver;
import com.xiaosantianqi.app.util.HttpCallbackListener;
import com.xiaosantianqi.app.util.HttpUtil;
import com.xiaosantianqi.app.util.Utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

public class AutoUpdateService extends Service {

	public IBinder onBind(Intent intent) {

		return null;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new Runnable() {
			public void run() {
				Log.d("服务", "自动更新");
				updateWeather();
			}

		}).run();

		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour = 1 * 10 * 1000; // 这是1小时的毫秒数
		long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this, AutoUpdateReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}

	protected void updateWeather() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String weatherCode = prefs.getString("id", "");
		String address = "http://api.heweather.com/x3/weather?cityid="
				+ weatherCode + "&key=96193962513d4cf1b153921213446f9f";
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			public void onFinish(String response) {
				Utility.handleWeatherResponse(AutoUpdateService.this, response);
			}

			public void onError(Exception e) {
				e.printStackTrace();
			}

		});
	}
}
