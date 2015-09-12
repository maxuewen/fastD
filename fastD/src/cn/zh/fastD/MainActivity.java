package cn.zh.fastD;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.json.JSONStringer;

import android.content.Intent;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;

import cn.zh.Service.HeartbeatService;
import cn.zh.Service.test;
import cn.zh.Service.userService;
import cn.zh.Utils.Constants;
import cn.zh.Utils.HttpUtils;
import cn.zh.domain.user;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements AMapLocationListener,Runnable{

	private Fragment mContent;
	
	private LocationManagerProxy aMapLocManager = null;
	private Handler handler = new Handler();
	private AMapLocation aMapLocation;// 用于判断定位超时

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m_layout_main);
		
		
		
		
		Intent service = new Intent(this , userService.class);
		startService(service);
		System.out.println("kaishi ;l ");
		
//		/json.fromJson(s, new TypeToken<List<user_Ad>>(){}.getType())
//		Gson f = new Gson();
//		user u = new user("asd", "phine", "maxuewe", "xw", 12, 12);
//		Map<String , String> map = new HashMap<String, String>();
//		map.put("method", Constants.add_user);
//		map.put("param", f.toJson(u));
//		
//		
//		
//		
//		
//		try {
//			HttpUtils.postRequest(HttpUtils.url+"userServlet", map);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
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
