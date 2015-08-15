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
		String theCity = prefs.getString("cityName", "") + "  "
				+ prefs.getString("date1", "");

		String pm10 = prefs.getString("pm10", "");
		String pm25 = prefs.getString("pm25", "");
		String qlty = prefs.getString("qlty", "");
		String qltyState = "空气质量类别     " + qlty;
		String pm10State = "PM10   " + pm10 + "ug/m³/h";
		String pm25State = "PM25    " + pm25 + "ug/m³/h";

		String one = prefs.getString("date1", "") + "  "
				+ prefs.getString("temp11", "") + "℃" + "~"
				+ prefs.getString("temp12", "") + "℃" + "  "
				+ prefs.getString("des1", "");

		String two = prefs.getString("date2", "") + "  "
				+ prefs.getString("temp21", "") + "℃" + "~"
				+ prefs.getString("temp22", "") + "℃" + "  "
				+ prefs.getString("des2", "");

		String three = prefs.getString("date3", "") + "  "
				+ prefs.getString("temp31", "") + "℃" + "~"
				+ prefs.getString("temp32", "") + "℃" + "  "
				+ prefs.getString("des3", "");

		String four = prefs.getString("date4", "") + "  "
				+ prefs.getString("temp41", "") + "℃" + "~"
				+ prefs.getString("temp42", "") + "℃" + "  "
				+ prefs.getString("des4", "");

		String five = prefs.getString("date5", "") + "  "
				+ prefs.getString("temp51", "") + "℃" + "~"
				+ prefs.getString("temp52", "") + "℃" + "  "
				+ prefs.getString("des5", "");

		String six = prefs.getString("date6", "") + "  "
				+ prefs.getString("temp61", "") + "℃" + "~"
				+ prefs.getString("temp62", "") + "℃" + "  "
				+ prefs.getString("des6", "");

		String seven = prefs.getString("date7", "") + "  "
				+ prefs.getString("temp71", "") + "℃" + "~"
				+ prefs.getString("temp72", "") + "℃" + "  "
				+ prefs.getString("des7", "");

		String[] data = { theCity, qltyState, pm10State, pm25State, one, two,
				three, four, five, six, seven };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				BasicActivity.this, android.R.layout.simple_list_item_1, data);
		listView.setAdapter(adapter);
	}
}
