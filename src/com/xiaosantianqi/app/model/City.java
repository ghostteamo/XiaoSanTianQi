package com.xiaosantianqi.app.model;

public class City {
	private int id;
	private int provinceId;
	private String cityName;
	private String cityCode;

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityCode() {
		return cityCode;
	}
}
