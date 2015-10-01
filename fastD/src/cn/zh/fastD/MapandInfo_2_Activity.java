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
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zh.Utils.ActionBarUtils;
import cn.zh.Utils.Constants;
import cn.zh.Utils.HttpUtils;
import cn.zh.Utils.NetUtils;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.Utils.ActionProcessButton.Mode;
import cn.zh.Utils.NoTouchViewPager;
import cn.zh.adapter.viewPagerAdapter;
import cn.zh.domain.main;
import cn.zh.domain.user;
import cn.zh.domain.user_Ad;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MapandInfo_2_Activity extends Activity implements OnClickListener{
	
	private main m = null;
	private MapView mapView;
	private AMap aMap;
	private UiSettings mUiSettings;
	
	LatLng userLatLng;
	LatLng fastLatLng;
	private Button but_commit;
	private NoTouchViewPager vp_main;
	private EditText et_form;
	private Button but_commit_2;
	viewPagerAdapter vpad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register_main);
		
		m = new Gson().fromJson(getIntent().getStringExtra("method"), main.class);
		vp_main = (NoTouchViewPager)findViewById(R.id.user_register);
		
		//===============================================设置actionbar
		View actionBar = ActionBarUtils.setAtionBar(this,R.layout.user_register_actionbar);
		((ImageButton) actionBar.findViewById(R.id.back)).setOnClickListener(this);
		((TextView) actionBar.findViewById(R.id.user_register_titleBar)).setText("区域内订单");
		
		//===============================================设置actionbar
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View v;
		List<View> list = new ArrayList<View>();
		
		v = inflater.inflate(R.layout.activity_mapand_info_2, null);
		list.add(v);
		but_commit = (Button)v.findViewById(R.id.but_commit);
		but_commit.setOnClickListener(this);
		//=====================================地图设置
		mapView = (MapView)v.findViewById(R.id.user_m5_map);
		if (aMap == null) {
			aMap = mapView.getMap();
			mUiSettings = aMap.getUiSettings();
		}
		mUiSettings.setZoomControlsEnabled(false);
		aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
		mapView.onCreate(savedInstanceState);
		
		//===============================================设置actionbar
		((TextView)v.findViewById(R.id.tv_name_)).setText(m.getUser_name());
		((TextView)v.findViewById(R.id.tv_fastPhone)).setText(m.getUser_phone());
		((TextView)v.findViewById(R.id.tv_fastAd)).setText(m.getShipAd());
		((TextView)v.findViewById(R.id.tv_time__)).setText(m.getTime());
		((TextView)v.findViewById(R.id.tv_mark)).setText(m.getMark());
		
		fastLatLng = new LatLng(Constants.fast_lat, Constants.fast_lng);
		userLatLng = new LatLng(m.getCu_x(), m.getCu_y());
		
		moveCamera(userLatLng);
		drawMarkers(userLatLng,"用户当前的位置");
		drawMarkers(fastLatLng,"您当前的位置");
		
		v = inflater.inflate(R.layout.form_input, null);
		et_form = (EditText)v.findViewById(R.id.formNum);
		but_commit_2 = (Button) v.findViewById(R.id.but_commit_2);
		but_commit_2.setOnClickListener(this);
		list.add(v);
		
		vpad = new viewPagerAdapter(this, list);
		vp_main.setAdapter(vpad);
		ViewPagerScroller vps = new ViewPagerScroller(this);
		vps.setScrollDuration(600);
		vps.initViewPagerScroll(vp_main);
		
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

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.fr_back:
			if(vp_main.getCurrentItem() == 0){
				Intent in = new Intent(MapandInfo_2_Activity.this,FastActivity.class);
				MapandInfo_2_Activity.this.finish();
				startActivity(in);
			}else{
				vp_main.setCurrentItem(0);
			}
			break;
		case R.id.but_commit:
			vp_main.setCurrentItem(1);
			break;
			
		case R.id.but_commit_2:
			String str = et_form.getText().toString().trim();
			if(TextUtils.isEmpty(str)){
				
				
				
				
				
				
			}else{
				show("订单号不能为空");
			}
			
		
		
		
		
		}
		
		
		
		SharedPreferences sh = getSharedPreferences("fastD", MODE_PRIVATE);
		
		Gson g = new Gson();
		RequestParams map = new RequestParams();
		map.put("method", Constants.alertForm);
		Map<String, String> m = new HashMap<String, String>();
		m.put("method", Constants.get_user);
		user user = null;
		try {
			user = g.fromJson(HttpUtils.postRequest(HttpUtils.url+"userServlet", m), user.class);
		} catch (JsonSyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		map.put("method", Constants.alertForm);
		map.put("fastId", sh.getString("fastId", null));
		map.put("formNum", et_form.getText().toString().trim());
		map.put("userId", user.getUser_id());
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(HttpUtils.url+"formServlet", map, new AsyncHttpResponseHandler(){
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				if(!NetUtils.isNetworkAvailable(MapandInfo_2_Activity.this)){
					show("网络错误");
				}else{
					show(new String(arg2));
				}
			}
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				but_commit_2.setClickable(false);
			}
			@Override
			public void onSuccess(int statusCode, Header[] arg1, byte[] responseBody) {
				if (statusCode == 200) {
					Gson j = new Gson();
					String str = j.fromJson(new String(responseBody), String.class);
					if(str.equals(Constants.ok)){
						show("提交成功");
					}else{
						show("提交失败");
					}
					
				}else{
					show("提交失败");
				}
			}
		});
		
	}
	
	
	private void show(String str){
		new SweetAlertDialog(MapandInfo_2_Activity.this,SweetAlertDialog.ERROR_TYPE)
		.setTitleText(str)
		.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
								
								@Override
								public void onClick(SweetAlertDialog sweetAlertDialog) {
									// TODO Auto-generated method stub
									but_commit_2.setClickable(true);
									sweetAlertDialog.cancel();
								}
							})
		.show();
	}
	
	


}
