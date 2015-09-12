package cn.zh.domain;

import com.amap.api.maps2d.model.LatLng;

public class poiPoint {
	
	private LatLng latlng;
	private String title;
	private String adName;
	
	public poiPoint(){}
	
	public poiPoint(LatLng latlng, String title, String adName) {
		super();
		this.latlng = latlng;
		this.title = title;
		this.adName = adName;
	}
	
	public double getLat(){
		return this.latlng.latitude;
	}
	
	public double getLng(){
		return this.latlng.longitude;
	}
	
	public LatLng getLatlng() {
		return latlng;
	}


	public void setLatlng(LatLng latlng) {
		this.latlng = latlng;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAdName() {
		return adName;
	}


	public void setAdName(String adName) {
		this.adName = adName;
	}


	@Override
	public String toString() {
		return title;
	}
	
	
}
