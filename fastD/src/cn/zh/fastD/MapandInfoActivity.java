package cn.zh.fastD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.Header;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cn.zh.Utils.ActionBarUtils;
import cn.zh.Utils.Constants;
import cn.zh.Utils.HttpUtils;
import cn.zh.Utils.NetUtils;
import cn.zh.Utils.ActionProcessButton.Mode;
import cn.zh.domain.main;
import cn.zh.domain.user;
import cn.zh.domain.user_Ad;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MapandInfoActivity extends Activity {
	
	private main m = null;
	private int page = 0 ;
	private MapView mapView;
	private AMap aMap;
	private UiSettings mUiSettings;
	
	LatLng userLatLng;
	LatLng fastLatLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mapand_info);
		
		m = new Gson().fromJson(getIntent().getStringExtra("method"), main.class);
		
		
		//===============================================设置actionbar
		View actionBar = ActionBarUtils.setAtionBar(this,R.layout.user_register_actionbar);
		((ImageButton) actionBar.findViewById(R.id.back)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(MapandInfoActivity.this,MainActivity.class);
				MapandInfoActivity.this.finish();
				startActivity(in);
			}
		});
		
		
			((TextView) actionBar.findViewById(R.id.user_register_titleBar)).setText("订单查看");
			
		
		//=====================================地图设置
		mapView = (MapView)findViewById(R.id.user_m5_map);
		if (aMap == null) {
			aMap = mapView.getMap();
			mUiSettings = aMap.getUiSettings();
		}
		mUiSettings.setZoomControlsEnabled(false);
		aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
		mapView.onCreate(savedInstanceState);
		
		//===============================================设置actionbar
		init();
	}

	private void init() {
		
		((TextView)findViewById(R.id.tv_name_)).setText(m.getUser_name());
		((TextView)findViewById(R.id.tv_fastPhone)).setText(m.getUser_phone());
		((TextView)findViewById(R.id.tv_fastAd)).setText(m.getShipAd());
		((TextView)findViewById(R.id.tv_time__)).setText(m.getTime());
		((TextView)findViewById(R.id.tv_mark)).setText(m.getMark());
		
		fastLatLng = new LatLng(Constants.fast_lat, Constants.fast_lng);
		userLatLng = new LatLng(m.getCu_x(), m.getCu_y());
		
		
		moveCamera(userLatLng);
		drawMarkers(userLatLng,"用户当前的位置");
		drawMarkers(fastLatLng,"您当前的位置");
	}
	
	
	public void drawMarkers(LatLng l,String str) {
		Marker marker = aMap.addMarker(new MarkerOptions()
				.position(l)
				.title(str)
				.draggable(true));
		marker.showInfoWindow();// 设置默认显示一个infowinfow
	}
	
	private void moveCamera(LatLng l){
		aMap.moveCamera(new CameraUpdateFactory().newLatLngZoom(l, 14));
	}
	
	

	/**
	 * 方法必须重写
	 */

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mapView.onResume();
	}
	/**
	 * 方法必须重写
	 */
	@Override
	public void onPause() {
		super.onPause();
		mapView.onPause();
		//stopLocation(); 停止定位
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapView.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		mapView.onDestroy();
	}
}
