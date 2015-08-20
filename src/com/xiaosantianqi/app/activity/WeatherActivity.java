package com.xiaosantianqi.app.activity;

import com.xiaosantianqi.app.R;
import com.xiaosantianqi.app.service.AutoUpdateService;
import com.xiaosantianqi.app.util.HttpCallbackListener;
import com.xiaosantianqi.app.util.HttpUtil;
import com.xiaosantianqi.app.util.Utility;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WeatherActivity extends Activity implements OnClickListener {

	private LinearLayout weatherInfoLayout;
	private TextView cityNameText;
	private TextView publishText;
	private Button moreInformation;
	private Button switchCity;
	private Button refreshWeather;
	private TextView wind;
	private TextView temp;
	private TextView temp11;
	private TextView temp12;
	private TextView des1;

	private TextView temp21;
	private TextView temp22;
	private TextView des2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.weather_layout1);
		// ��ʼ�����ؼ�
		weatherInfoLayout = (LinearLayout) findViewById(R.id.linearLayout);
		wind = (TextView) findViewById(R.id.wind);
		temp = (TextView) findViewById(R.id.temp);
		temp11 = (TextView) findViewById(R.id.temp11);
		temp12 = (TextView) findViewById(R.id.temp12);
		des1 = (TextView) findViewById(R.id.des1);
		temp21 = (TextView) findViewById(R.id.temp21);
		temp22 = (TextView) findViewById(R.id.temp22);
		des2 = (TextView) findViewById(R.id.des2);
		cityNameText = (TextView) findViewById(R.id.city_name);
		publishText = (TextView) findViewById(R.id.publish_text);
		switchCity = (Button) findViewById(R.id.switch_city);
		moreInformation = (Button) findViewById(R.id.moreinformation);
		refreshWeather = (Button) findViewById(R.id.refresh_weather);
		String countryCode = getIntent().getStringExtra("country_code");
		if (!TextUtils.isEmpty(countryCode)) {
			// ���ؼ�����ʱ��ȥ��ѯ����
			publishText.setText("ͬ����...");
			weatherInfoLayout.setVisibility(View.INVISIBLE);
			moreInformation.setVisibility(View.INVISIBLE);
			cityNameText.setVisibility(View.INVISIBLE);
			queryWeatherCode(countryCode);
		} else {
			// û���ؼ�����ʱ��ֱ����ʾ��������
			showWeather();
		}
		switchCity.setOnClickListener(this);
		refreshWeather.setOnClickListener(this);
		moreInformation.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.switch_city:
			Intent intent = new Intent(this, ChooseAreaActivity.class);
			intent.putExtra("from_weather_activity", true);
			startActivity(intent);
			finish();
			break;
		case R.id.refresh_weather:
			publishText.setText("ͬ����...");
			SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(this);
			String weatherCode = prefs.getString("id", "");
			if (!TextUtils.isEmpty(weatherCode)) {
				String address = "http://api.heweather.com/x3/weather?cityid="
						+ weatherCode + "&key=96193962513d4cf1b153921213446f9f";
				queryFromServer(address, "weatherCode");
			}
			break;
		case R.id.moreinformation:
			Intent intentInformation = new Intent(WeatherActivity.this,
					BasicActivity.class);
			startActivity(intentInformation);
		default:
			break;
		}
	}

	/**
	 * ��ѯ�ؼ���������Ӧ���������š�
	 */
	private void queryWeatherCode(String countryCode) {
		String address = "http://www.weather.com.cn/data/list3/city"
				+ countryCode + ".xml";
		queryFromServer(address, "countryCode");
		Log.d("countryCode", countryCode);
	}

	/**
	 * ��ѯ������������Ӧ��������
	 */
	private void queryWeatherInfo(String weatherCode) {
		String address = "http://api.heweather.com/x3/weather?cityid=CN"
				+ weatherCode + "&key=96193962513d4cf1b153921213446f9f";
		queryFromServer(address, "weatherCode");
		Log.d("weatherCode", weatherCode);
	}

	/**
	 * ���ݴ���ĵ�ַ������ȥ���������ѯ�������Ż���������Ϣ��
	 */
	private void queryFromServer(final String address, final String type) {
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

			public void onFinish(final String response) {
				if ("countryCode".equals(type)) {
					if (!TextUtils.isEmpty(response)) {
						// �ӷ��������ص������н�������������
						String[] array = response.split("\\|");
						if (array != null && array.length == 2) {
							String weatherCode = array[1];
							queryWeatherInfo(weatherCode);
						}
					}
				} else if ("weatherCode".equals(type)) {

					// ������������ص�������Ϣ
					Utility.handleWeatherResponse(WeatherActivity.this,
							response);
					runOnUiThread(new Runnable() {
						public void run() {
							showWeather();
						}
					});
				}
			}

			public void onError(Exception e) {
				runOnUiThread(new Runnable() {
					public void run() {
						publishText.setText("ͬ��ʧ��");
					}
				});
			}
		});
	}

	/**
	 * ��SharedPreferences�ļ��ж�ȡ�洢��������Ϣ������ʾ�������ϡ�
	 */
	private void showWeather() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		cityNameText.setText(prefs.getString("cityName", ""));
		cityNameText.setVisibility(View.VISIBLE);
		publishText.setText("����ʱ��:\n" + prefs.getString("updateTime", ""));
		wind.setText(prefs.getString("wind", ""));
		temp.setText(prefs.getString("tmp", "") + "��");
		temp11.setText(prefs.getString("temp11", "") + "��");
		temp12.setText(prefs.getString("temp12", "") + "��");
		des1.setText(prefs.getString("des1", ""));
		temp21.setText(prefs.getString("temp21", "") + "��");
		temp22.setText(prefs.getString("temp22", "") + "��");
		des2.setText(prefs.getString("des2", ""));
		weatherInfoLayout.setVisibility(View.VISIBLE);
		moreInformation.setVisibility(View.VISIBLE);
		Intent intent = new Intent(this, AutoUpdateService.class);
		startService(intent);
	}

}
