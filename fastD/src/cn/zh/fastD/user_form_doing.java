


package cn.zh.fastD;


import cn.zh.Utils.ActionBarUtils;
import cn.zh.Utils.Constants;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class user_form_doing extends Activity {
	
	
	
	private AMap aMap;
	private MapView mapView;
	private UiSettings mUiSettings;
	LatLng latLng;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		View actionBar = ActionBarUtils.setAtionBar(this,
				R.layout.user_register_actionbar);
		((ImageButton) actionBar.findViewById(R.id.back)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent(user_form_doing.this,MainActivity.class);
				user_form_doing.this.finish();
				startActivity(in);
			}
		});
		((TextView) actionBar.findViewById(R.id.user_register_titleBar)).setText("订单查询");
		
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_m5_unfinish_form);
		
		mapView = (MapView)findViewById(R.id.user_m5_map);
		if (aMap == null) {
			aMap = mapView.getMap();
			mUiSettings = aMap.getUiSettings();
		}
		mUiSettings.setZoomControlsEnabled(false);
		aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
		mapView.onCreate(savedInstanceState);
		
		Intent in = getIntent();
		((TextView)findViewById(R.id.tv_company1)).setText(in.getStringExtra("company"));
		((TextView)findViewById(R.id.tv_fastName1)).setText(in.getStringExtra("fastName"));
		((TextView)findViewById(R.id.tv_fastPhone1)).setText(in.getStringExtra("fastPhone"));
		((TextView)findViewById(R.id.tv_time1)).setText(in.getStringExtra("time"));
		((TextView)findViewById(R.id.tv_receiptAd1)).setText(in.getStringExtra("receiptAd"));
		((TextView)findViewById(R.id.tv_mark1)).setText(in.getStringExtra("mark"));
		latLng = new LatLng( in.getDoubleExtra("x",Constants.ZHONGHUANXUEYUAN.latitude),
				in.getDoubleExtra("y", Constants.ZHONGHUANXUEYUAN.longitude));
		moveCamera(latLng);
		drawMarkers(latLng);
		
	}
	
	public void drawMarkers(LatLng l) {
		Marker marker = aMap.addMarker(new MarkerOptions()
				.position(l)
				.title("快递员当前位置")
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
