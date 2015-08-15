package com.xiaosantianqi.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.xiaosantianqi.app.model.City;
import com.xiaosantianqi.app.model.CoolWeatherDB;
import com.xiaosantianqi.app.model.Country;
import com.xiaosantianqi.app.model.Province;

public class Utility {

	public synchronized static boolean handleProvincesResponse(
			CoolWeatherDB coolWeatherDB, String response) {
		if (!TextUtils.isEmpty(response)) {
			String[] allProvinces = response.split(",");
			if (allProvinces != null && allProvinces.length > 0) {
				for (String p : allProvinces) {
					String[] array = p.split("\\|");
					Province province = new Province();
					province.setProvinceCode(array[0]);
					province.setProvinceName(array[1]);
					coolWeatherDB.saveProvince(province);
				}
				return true;
			}
		}
		return false;
	}

	public static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB,
			String response, int provinceId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCities = response.split(",");
			if (allCities != null && allCities.length > 0) {
				for (String c : allCities) {
					String[] array = c.split("\\|");
					City city = new City();
					city.setCityCode(array[0]);
					city.setCityName(array[1]);
					city.setProvinceId(provinceId);
					// 将解析出来的数据存储到City表
					coolWeatherDB.saveCity(city);
				}
				return true;
			}
		}
		return false;
	}

	public static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB,
			String response, int cityId) {
		if (!TextUtils.isEmpty(response)) {
			String[] allCounties = response.split(",");
			if (allCounties != null && allCounties.length > 0) {
				for (String c : allCounties) {
					String[] array = c.split("\\|");
					Country country = new Country();
					country.setCountryCode(array[0]);
					country.setCountryName(array[1]);
					country.setCityId(cityId);
					// 将解析出来的数据存储到Country表
					coolWeatherDB.saveCountry(country);
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * 解析服务器返回的JSON数据，并将解析出的数据存储到本地。
	 */
	public static void handleWeatherResponse(Context context, String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			JSONArray array = jsonObject
					.getJSONArray("HeWeather data service 3.0");
			JSONObject jsonObjectData = array.getJSONObject(0);
			JSONObject jsonObjectAqi = jsonObjectData.getJSONObject("aqi");
			JSONObject jsonObjectCity = jsonObjectAqi.getJSONObject("city");
			String pm10 = jsonObjectCity.getString("pm10");
			String pm25 = jsonObjectCity.getString("pm25");
			String qlty = jsonObjectCity.getString("qlty");
			JSONObject jsonObjectBasic = jsonObjectData.getJSONObject("basic");
			String cityName = jsonObjectBasic.getString("city");
			String id = jsonObjectBasic.getString("id");
			JSONObject jsonObjectUpdateTime = jsonObjectBasic
					.getJSONObject("update");
			String updateTime = jsonObjectUpdateTime.getString("loc");
			JSONObject jsonObjectNow = jsonObjectData.getJSONObject("now");
			String tmp = jsonObjectNow.getString("tmp");
			String des1 = jsonObjectNow.getJSONObject("cond").getString("txt");
			JSONObject jsonObjecWind = jsonObjectNow.getJSONObject("wind");
			String wind = jsonObjecWind.getString("dir");
			JSONArray arrayDaily = jsonObjectData
					.getJSONArray("daily_forecast");
			JSONObject jsonObjectToday = arrayDaily.getJSONObject(0);
			String date1 = jsonObjectToday.getString("date");
			String temp11 = jsonObjectToday.getJSONObject("tmp").getString(
					"min");
			String temp12 = jsonObjectToday.getJSONObject("tmp").getString(
					"max");

			JSONObject jsonObjectTomorrow = arrayDaily.getJSONObject(1);
			String date2 = jsonObjectTomorrow.getString("date");
			String temp21 = jsonObjectTomorrow.getJSONObject("tmp").getString(
					"min");
			String temp22 = jsonObjectTomorrow.getJSONObject("tmp").getString(
					"max");
			String des2 = jsonObjectTomorrow.getJSONObject("cond").getString(
					"txt_d");
			JSONObject jsonObject3 = arrayDaily.getJSONObject(2);
			String date3 = jsonObject3.getString("date");
			String temp31 = jsonObject3.getJSONObject("tmp").getString("min");
			String temp32 = jsonObject3.getJSONObject("tmp").getString("max");
			String des3 = jsonObject3.getJSONObject("cond").getString("txt_d");

			JSONObject jsonObject4 = arrayDaily.getJSONObject(3);
			String date4 = jsonObject4.getString("date");
			String temp41 = jsonObject4.getJSONObject("tmp").getString("min");
			String temp42 = jsonObject4.getJSONObject("tmp").getString("max");
			String des4 = jsonObject4.getJSONObject("cond").getString("txt_d");

			JSONObject jsonObject5 = arrayDaily.getJSONObject(4);
			String date5 = jsonObject5.getString("date");
			String temp51 = jsonObject5.getJSONObject("tmp").getString("min");
			String temp52 = jsonObject5.getJSONObject("tmp").getString("max");
			String des5 = jsonObject5.getJSONObject("cond").getString("txt_d");

			JSONObject jsonObject6 = arrayDaily.getJSONObject(5);
			String date6 = jsonObject6.getString("date");
			String temp61 = jsonObject6.getJSONObject("tmp").getString("min");
			String temp62 = jsonObject6.getJSONObject("tmp").getString("max");
			String des6 = jsonObject6.getJSONObject("cond").getString("txt_d");

			JSONObject jsonObject7 = arrayDaily.getJSONObject(6);
			String date7 = jsonObject7.getString("date");
			String temp71 = jsonObject7.getJSONObject("tmp").getString("min");
			String temp72 = jsonObject7.getJSONObject("tmp").getString("max");
			String des7 = jsonObject7.getJSONObject("cond").getString("txt_d");

			SharedPreferences.Editor editor = PreferenceManager
					.getDefaultSharedPreferences(context).edit();
			editor.putBoolean("city_selected", true);
			editor.putString("cityName", cityName);
			editor.putString("id", id);
			editor.putString("updateTime", updateTime);
			editor.putString("wind", wind);
			editor.putString("tmp", tmp);

			editor.putString("date1", date1);
			editor.putString("temp11", temp11);
			editor.putString("temp12", temp12);
			editor.putString("des1", des1);

			editor.putString("date2", date2);
			editor.putString("temp21", temp21);
			editor.putString("temp22", temp22);
			editor.putString("des2", des2);

			editor.putString("pm10", pm10);
			editor.putString("pm25", pm25);
			editor.putString("qlty", qlty);

			editor.putString("date3", date3);
			editor.putString("temp31", temp31);
			editor.putString("temp32", temp32);
			editor.putString("des3", des3);

			editor.putString("date4", date4);
			editor.putString("temp41", temp41);
			editor.putString("temp42", temp42);
			editor.putString("des4", des4);

			editor.putString("date5", date5);
			editor.putString("temp51", temp51);
			editor.putString("temp52", temp52);
			editor.putString("des5", des5);

			editor.putString("date6", date6);
			editor.putString("temp61", temp61);
			editor.putString("temp62", temp62);
			editor.putString("des6", des6);

			editor.putString("date7", date7);
			editor.putString("temp71", temp71);
			editor.putString("temp72", temp72);
			editor.putString("des7", des7);
			editor.commit();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
