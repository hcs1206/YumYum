package com.redbeans.yumyum.dto;

public class Store {
	private String id;
	private String name;
	private String url;
	private String address;
	private int reviewCount;
	private int lat;
	private int lng;
	
	
	public int getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	public int getLng() {
		return lng;
	}
	public void setLng(int lng) {
		this.lng = lng;
	}
	public Store(String id, String name, String url, String address, int reviewCount, int lat, int lng) {
		super();
		this.id = id;
		this.name = name;
		this.url = url;
		this.address = address;
		this.reviewCount = reviewCount;
		this.lat = lat;
		this.lng = lng;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	@Override
	public String toString() {
		return "Store [id=" + id + ", name=" + name + ", url=" + url + ", address=" + address + ", reviewCount="
				+ reviewCount + "]";
	}
}
