package cn.zh.fastD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.http.Header;
import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zh.Service.userService;
import cn.zh.Utils.Constants;
import cn.zh.Utils.HttpUtils;
import cn.zh.Utils.NetUtils;
import cn.zh.Utils.ActionProcessButton.Mode;
import cn.zh.domain.main;
import cn.zh.domain.user;
import cn.zh.domain.user_Ad;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends SlidingFragmentActivity implements AMapLocationListener,Runnable{

	private Fragment mContent;
	
	private LocationManagerProxy aMapLocManager = null;
	private Handler handler = new Handler();
	private AMapLocation aMapLocation;// 用于判断定位超时
	
	SharedPreferences sh ;
	
	@Override
	protected void onResume() {
		
		SharedPreferences sh = getSharedPreferences("fastD", MODE_PRIVATE);
		
		if(sh.getString("service", "false").equals("false")){
			startService(new Intent(MainActivity.this,userService.class));
		}
		
		super.onResume();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m_layout_main);
		//=========================================检查账号密码
		SharedPreferences sh = getSharedPreferences("fastD", MODE_APPEND);
		if(sh.getString("userLogin", "false").equals("true")){
			if(NetUtils.isNetworkAvailable(this)){
				checkUser_password();
			}else{
				new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
				.setTitleText("还没有联网!")
				.show();
			}
		}
		//检查账号密码
		
		aMapLocManager = LocationManagerProxy.getInstance(this);
		aMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork, 60 * 1000, 10, this);
		handler.postDelayed(this, 12000);// 设置超过12秒还没有定位到就停止定位

		// check if the content frame contains the menu frame
		if (findViewById(R.id.menu_frame) == null) {
			setBehindContentView(R.layout.m_menu_frame);
			getSlidingMenu().setSlidingEnabled(true);
			getSlidingMenu()
					.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			// add a dummy view
			View v = new View(this);
			setBehindContentView(v);
			getSlidingMenu().setSlidingEnabled(false);
			getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}

		// set the Above View Fragment
		if (savedInstanceState != null) {
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		}

		if (mContent == null) {
			mContent = new ContentFragment();
		}
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, mContent).commit();

		// set the Behind View Fragment
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.menu_frame, new MenuFragment()).commit();

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeEnabled(false);
		sm.setBehindScrollScale(0.25f);
		sm.setFadeDegree(0.25f);
		
		
		
		sm.setBackgroundImage(R.drawable.img_frame_background);
		sm.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (percentOpen * 0.25 + 0.75);
				canvas.scale(scale, scale, -canvas.getWidth() / 2,
						canvas.getHeight() / 2);
			}
		});

		sm.setAboveCanvasTransformer(new SlidingMenu.CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				float scale = (float) (1 - percentOpen * 0.25);
				canvas.scale(scale, scale, 0, canvas.getHeight() / 2);
			}
		});
		
		sm.toggle();
		
	}

	private void stopLocation() {
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(this);
			aMapLocManager.destroy();
		}
		aMapLocManager = null;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	
	//关于定位的方法

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void checkUser_password(){
		sh = getSharedPreferences("fastD", MODE_APPEND);
		Gson j = new Gson();
		
		RequestParams map = new RequestParams();
		map.put("method",j.toJson( Constants.get_user));
		map.put("param", j.toJson(sh.getString("userName", null)));
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(HttpUtils.url+"userServlet", map, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] arg1, byte[] responseBody) {
				if (statusCode == 200) {
					Gson j = new Gson();
					user u = j.fromJson(new String(responseBody), user.class);
					if(u != null){
						if(!u.getPassword().equals(sh.getString("userPassword", null)) ){
							Intent in = new Intent(MainActivity.this,LoginActivity.class);
							in.putExtra("method", "MainActivity");
							startActivity(in);
						}
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			this.aMapLocation = location;// 判断超时机制
			System.out.println("定位");
			System.out.println(location);
			Constants.user_lat = location.getLatitude();
			Constants.user_lng = location.getLongitude();
		}
		
	}



	@Override
	public void run() {
		if (aMapLocation == null) {
			stopLocation();// 销毁掉定位
		}
	}
	
	
}
