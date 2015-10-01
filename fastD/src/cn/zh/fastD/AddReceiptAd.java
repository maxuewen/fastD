package cn.zh.fastD;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

import org.apache.http.Header;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zh.Utils.ActionBarUtils;
import cn.zh.Utils.Constants;
import cn.zh.Utils.HttpUtils;
import cn.zh.Utils.NetUtils;
import cn.zh.Utils.NoTouchViewPager;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.adapter.viewPagerAdapter;
import cn.zh.domain.user_Ad;

import com.amap.api.mapcore2d.ed;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mrwujay.cascade.activity.BaseActivity;

public class AddReceiptAd extends BaseActivity implements OnClickListener, OnWheelChangedListener {
	
	private WheelView mViewProvince;
	private WheelView mViewCity;
	private WheelView mViewDistrict;
	private Button mBtnConfirm;
	
	private NoTouchViewPager vp_main;
	private EditText et_name;
	private EditText et_phone;
	private EditText et_AdData;
	private Button but_register;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_ad);
		
		
		vp_main = (NoTouchViewPager)findViewById(R.id.Add_Ad_main);
		
		View view = ActionBarUtils.setAtionBar(this, R.layout.user_register_actionbar);
		((TextView)view.findViewById(R.id.user_register_titleBar)).setText("添加地址");
		view.findViewById(R.id.back).setOnClickListener(this);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		
		List<View> list = new ArrayList<View>();
		
		View v1 = inflater.inflate(R.layout.activity_reciept_ad, null);
		list.add(v1);
		setUpViews(v1);
		setUpListener();
		setUpData();
		
		View v2 = inflater.inflate(R.layout.add_ad_info, null);
		list.add(v2);
		et_name = (EditText)v2.findViewById(R.id.r_name);
		et_phone = (EditText)v2.findViewById(R.id.r_phone);
		et_AdData = (EditText)v2.findViewById(R.id.r_AdData);
		but_register = ((Button)v2.findViewById(R.id.but_Adregister));
		but_register.setOnClickListener(this);
		
		
		vp_main.setAdapter(new viewPagerAdapter(this, list));
		ViewPagerScroller vps = new ViewPagerScroller(this);
		vps.setScrollDuration(600);
		vps.initViewPagerScroll(vp_main);
		
	}
	
	private void setUpViews(View v) {
		mViewProvince = (WheelView) v.findViewById(R.id.id_province);
		mViewCity = (WheelView) v.findViewById(R.id.id_city);
		mViewDistrict = (WheelView) v.findViewById(R.id.id_district);
		mBtnConfirm = (Button) v.findViewById(R.id.btn_confirm);
	}
	
	private void setUpListener() {
    	// 添加change事件
    	mViewProvince.addChangingListener(this);
    	// 添加change事件
    	mViewCity.addChangingListener(this);
    	// 添加change事件
    	mViewDistrict.addChangingListener(this);
    	// 添加onclick事件
    	mBtnConfirm.setOnClickListener(this);
    }
	
	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(AddReceiptAd.this, mProvinceDatas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(this, areas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(this, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	@Override
	public void onClick(View v) {
		
			showSelectedResult();
			
			switch(v.getId()){
			case R.id.btn_confirm:
				vp_main.setCurrentItem(1);
				break;
				
				
			case R.id.back:
				if(vp_main.getCurrentItem() == 0){
					startActivity(new Intent(AddReceiptAd.this,MainActivity.class));
					AddReceiptAd.this.finish();
				}else{
					vp_main.setCurrentItem(0);
				}
				break;
				
				
			case R.id.but_Adregister:
				String name = et_name.getText().toString();
				String phone = et_phone.getText().toString().trim();
				String AdDate = et_AdData.getText().toString();
				
				if(TextUtils.isEmpty(name)){
					new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
					.setTitleText("姓名不能为空")
					.show();
					return;
				}
				if(TextUtils.isEmpty(phone)){
					new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
					.setTitleText("手机号不能为空")
					.show();
					return;
				}
				if(TextUtils.isEmpty(AdDate)){
					new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
					.setTitleText("地址信息不能为空")
					.show();
					return;
				}
				
//				========================================提交地址，===========================
				SharedPreferences sh = getSharedPreferences("fastD", MODE_PRIVATE);
				user_Ad u = new user_Ad(sh.getString("userId", null), showSelectedResult(), AdDate, name, phone);
				
				
				RequestParams map = new RequestParams();
				map.put("method", Constants.add_userAd);
				map.put("param", new Gson().toJson(u));
				
				AsyncHttpClient client = new AsyncHttpClient();
				client.post(HttpUtils.url+"userServlet", map, new AsyncHttpResponseHandler(){
					@Override
					public void onFailure(int arg0, Header[] arg1, byte[] arg2,
							Throwable arg3) {
						if(!NetUtils.isNetworkAvailable(AddReceiptAd.this)){
							show("网络错误");
						}else{
							show(new String(arg2));
//							show("服务器错误");
						}
					}
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
						but_register.setClickable(false);
					}
					@Override
					public void onSuccess(int statusCode, Header[] arg1, byte[] responseBody) {
						if (statusCode == 200) {
							Gson j = new Gson();
							if(j.fromJson(new String(responseBody), String.class).equals(Constants.ok)){
								
								new SweetAlertDialog(AddReceiptAd.this,SweetAlertDialog.ERROR_TYPE)
								.setTitleText("注册成功")
								.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
														
														@Override
														public void onClick(SweetAlertDialog sweetAlertDialog) {
															// TODO Auto-generated method stub
														startActivity(new Intent(AddReceiptAd.this, MainActivity.class));
														}
													})
								.show();
							}
							}else{
								show("注册失败");
							}
					}
				});
				
				
//				========================================提交地址，===========================
				
			}
		
	}
	
	
	private void show(String str){
		new SweetAlertDialog(AddReceiptAd.this,SweetAlertDialog.ERROR_TYPE)
		.setTitleText(str)
		.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
								
								@Override
								public void onClick(SweetAlertDialog sweetAlertDialog) {
									// TODO Auto-generated method stub
									but_register.setClickable(true);
									sweetAlertDialog.cancel();
								}
							})
		.show();
	}

	private String showSelectedResult() {
//		Toast.makeText(AddReceiptAd.this, "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","
//				+mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
		return mCurrentProviceName+mCurrentCityName+mCurrentDistrictName;
	}
	
}
