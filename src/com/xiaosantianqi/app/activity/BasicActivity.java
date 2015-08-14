package com.xiaosantianqi.app.activity;

import com.xiaosantianqi.app.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class BasicActivity extends Activity {

	private ListView listView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.basic);
		listView = (ListView) findViewById(R.id.list_view);

		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		String theCity = prefs.getString("cityName", "");
		String co = prefs.getString("co", "");
		String no2 = prefs.getString("no2", "");
		String o3 = prefs.getString("o3", "");
		String pm10 = prefs.getString("pm10", "");
		String pm25 = prefs.getString("pm25", "");
		String qlty = prefs.getString("qlty", "");
		String so2 = prefs.getString("so2", "");
		String qltyState = "空气质量类别     " + qlty;
		String coState = "CO    " + co + "ug/m³/h";
		String no2State = "NO2   " + no2 + "ug/m³/h";
		String o3State = "O3    " + o3 + "ug/m³/h";
		String pm10State = "PM10   " + pm10 + "ug/m³/h";
		String pm25State = "PM25    " + pm25 + "ug/m³/h";
		String so2State = "SO2    " + so2 + "ug/m³/h";
		String[] data = { theCity, qltyState, coState, so2State, no2State,
				o3State, pm10State, pm25State };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				BasicActivity.this, android.R.layout.simple_list_item_1, data);
		listView.setAdapter(adapter);
	}
}
