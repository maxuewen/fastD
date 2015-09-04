package cn.zh.fastD;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMap.OnMapClickListener;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Polyline;
import com.amap.api.maps2d.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zh.Utils.ActionBarUtils;
import cn.zh.Utils.Constants;
import cn.zh.Utils.NoTouchViewPager;
import cn.zh.Utils.Snippet;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.adapter.companyListViewAdp;
import cn.zh.adapter.poiListViewAdp;
import cn.zh.adapter.viewPagerAdapter;
import cn.zh.domain.poiPoint;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class Fastr_register_Activity extends Activity implements OnMapClickListener,
		OnItemClickListener, OnPageChangeListener, OnClickListener , AMapLocationListener,OnPoiSearchListener, LocationSource{

	private NoTouchViewPager vp;
	Bundle savedInstanceState;
	private AMap aMap;
	private int currentPoint=0;

	private Button but_urv1_getvatify; // urv1
	private Button but_urv1_next;
	private EditText et_phone;
	private EditText et_varify;

	private MapView mapview; // fast_register_poipager
	private NoTouchViewPager vp_poiBelow;
	private TextView tv_poiPagerBelow;
	private ListView lv_poiPagerBelowList;
	private Button but_poiPage_below;

	private EditText et_fastName; // fast_register_info
	private EditText et_company;
	private EditText et_passwrod;
	private EditText et_passwrod2;
	private Button but_finishRegister;

	ListView lv_companyList; // fast_register_companylist

	Button but_back; // actionbar
	Button but_next;
	TextView tv_title;
	View actionBar;
	
	List<LatLng> poiList;			//四个区域端点
	private Polyline polyline;
	private UiSettings mUiSettings;
	
	
	
	//关于定位的字段
	private AMapLocation aMapLocation;// 用于判断定位超时
	private LocationManagerProxy aMapLocManager = null;
	private Handler handler = new Handler();
	
	
	
	//周边搜索
	private Marker locationMarker; // 选择的点
	private PoiSearch poiSearch;
	private PoiSearch.Query query;// Poi查询条件类
	private List<PoiItem> poiItems;// poi数据
	private PoiResult poiResult;
	private poiListViewAdp poiAdp;
	private List<poiPoint> poiAdpList;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fast_register_main);
		this.savedInstanceState = savedInstanceState;
		
		init();
	}

	private void init() {

//		System.out.println(Snippet.uuid());
		
		//获取和初次设置actionbar
		actionBar = ActionBarUtils.setAtionBar(this,
				R.layout.fast_register_actionbar);
		but_back = (Button) actionBar.findViewById(R.id.fr_back);
		tv_title = (TextView) actionBar.findViewById(R.id.fr_register_titleBar);
		but_next = (Button) actionBar.findViewById(R.id.fr_next);
		setActionBar("登录", "注册", "");

		vp = (NoTouchViewPager) findViewById(R.id.fast_register_main);
		LayoutInflater inflater = LayoutInflater.from(this);
		List<View> list = new ArrayList<View>();

		// urv1
		View view = inflater.inflate(R.layout.user_register_viewpage_1, null);
		list.add(view);
		but_urv1_getvatify = (Button) view
				.findViewById(R.id.urv1_getvarify_but);
		but_urv1_next = (Button) view.findViewById(R.id.urv1_next_but);
		et_phone = (EditText) view.findViewById(R.id.urv1_phone);
		et_varify = (EditText) view.findViewById(R.id.urv1_varify);

		// fast_register_poipager
		view = inflater.inflate(R.layout.fast_register_poipager, null);
		list.add(view);
		//==============================================================mapview的初始化
		mapview = (MapView) view.findViewById(R.id.frv2_map);
		mapview.onCreate(savedInstanceState);
		if (aMap == null) {
			aMap = mapview.getMap();
			mUiSettings = aMap.getUiSettings();
		}
		mUiSettings.setZoomControlsEnabled(false);
		aMap.setOnMapClickListener(this);
		//===================================================================
		vp_poiBelow = (NoTouchViewPager) view.findViewById(R.id.fev2_mapbelow);
		List<View> l = new ArrayList<View>();
		view = inflater.inflate(R.layout.fr_poipager_below, null);
		l.add(view);
		tv_poiPagerBelow = (TextView) view
				.findViewById(R.id.fr_poipagerBelow_tv);
		view = inflater.inflate(R.layout.fr_poipagerbelow2, null);
		l.add(view);
		lv_poiPagerBelowList = (ListView) view
				.findViewById(R.id.fr_poipagerBelow_lv);
		poiAdpList = new ArrayList<poiPoint>();
		poiAdp = new poiListViewAdp(this, poiAdpList);
		lv_poiPagerBelowList.setAdapter(poiAdp);
		lv_poiPagerBelowList.setOnItemClickListener(this);
		
		view = inflater.inflate(R.layout.fr_poipager_below3,null);
		but_poiPage_below = (Button)view.findViewById(R.id.but_poiPager_Below);
		l.add(view);
		vp_poiBelow.setAdapter(new viewPagerAdapter(this, l));
		ViewPagerScroller vps = new ViewPagerScroller(this);
		vps.setScrollDuration(700);
		vps.initViewPagerScroll(vp_poiBelow);

		// fast_register_info
		view = inflater.inflate(R.layout.fast_register_info, null);
		list.add(view);
		et_fastName = (EditText) view.findViewById(R.id.frv3_fastName);
		et_company = (EditText) view.findViewById(R.id.frv3_company);
		et_passwrod = (EditText) view.findViewById(R.id.frv3_password);
		et_passwrod2 = (EditText) view.findViewById(R.id.frv3_password2);
		but_finishRegister = (Button) view.findViewById(R.id.frv3_but);
		et_company.setOnClickListener(this);

		// fast_register_companylist
		view = inflater.inflate(R.layout.fast_register_companylist, null);
		list.add(view);
		lv_companyList = (ListView) view.findViewById(R.id.frv4_listView);
		lv_companyList.setOnItemClickListener(this);
		lv_companyList.setAdapter(new companyListViewAdp(this));
		
		//设置vp的界面，添加adapter
		vp.setAdapter(new viewPagerAdapter(this, list));
		ViewPagerScroller vps1 = new ViewPagerScroller(this);
		vps1.setScrollDuration(700);
		vps1.initViewPagerScroll(vp_poiBelow);
		

		but_back.setOnClickListener(this);
		but_finishRegister.setOnClickListener(this);
		but_next.setOnClickListener(this);
		but_urv1_getvatify.setOnClickListener(this);
		but_urv1_next.setOnClickListener(this);
		but_poiPage_below.setOnClickListener(this);

		lv_companyList.setOnItemClickListener(this);
		
		UiSettings s = aMap.getUiSettings();
		s.setMyLocationButtonEnabled(true);
		aMap.setMyLocationEnabled(true);
		aMap.setLocationSource(this);
		
		
		//地图上画默认的区域
		draLineOnMap(Constants.xx,Constants.xy,Constants.yy,Constants.yx,Constants.xx);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fr_back:
			if (vp.getCurrentItem() == 3) {
				vp.setCurrentItem(2);
			} else {
				startActivity(new Intent(Fastr_register_Activity.this,
						LoginActivity.class));
				finish();
			}
			break;
		case R.id.frv3_but:
			proRegister();
			break;
		case R.id.fr_next:
			if(vp.getCurrentItem() == 1){
				currentPoint++;
				if(currentPoint == 3){		//进入信息填写页
					if(canTOInfoPager() == true){
						setActionBar("登录", "注册", "");
						vp.setCurrentItem(2);
					}else{
						currentPoint = 1;
						vp.setCurrentItem(1);
						vp_poiBelow.setCurrentItem(1);
						setActionBar("登录", "第 1 个点","");
						poiList.clear();
						aMap.clear();
					}
				}else{		
					if(currentPoint != 1 && (poiList==null || poiList.size()<currentPoint-1)){
						new SweetAlertDialog(Fastr_register_Activity.this, SweetAlertDialog.ERROR_TYPE)
						.setTitleText("提示")
						.setContentText("请先设置第 "+ --currentPoint +"点")
						.show();
					}else{
						if(currentPoint == 1){
							polyline.remove();
						}
					
//====================================================选第图的点，此处清空list，代表选完一个点	
					vp_poiBelow.setCurrentItem(1);
					setActionBar("登录", "第 "+currentPoint +" 个点", "");
					location();

					}
				}
				
			}
		case R.id.urv1_getvarify_but:
			getVarify();
			break;
		case R.id.urv1_next_but:
			urv1_next_but();
			break;
			
		case R.id.but_poiPager_Below:
//====================================================选第图的点，此处清空list，代表选完一个点	
			vp_poiBelow.setCurrentItem(1);
			this.poiList.remove(currentPoint-1);
			aMap.clear();
			break;
			
			
		case R.id.frv3_company:
		       et_company.setInputType(InputType.TYPE_NULL);
			vp.setCurrentItem(3);
			
		}

	}

	
	private void urv1_next_but() {
		
		vp.setCurrentItem(1);
		
//		// TODO Auto-generated method stub
//		String str_varify = et_varify.getText().toString().trim();
//		String str_phone = et_phone.getText().toString().trim();
//		
//		if(!TextUtils.isEmpty(str_phone)){
//			//手机号不为空，判断格式是否正确
//			Pattern compile = Pattern.compile("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$");
//			Matcher matcher = compile.matcher(str_phone);
//			if(!matcher.find()){
//				new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
//				.setTitleText("提示")
//				.setContentText("手机号格式不正确")
//				.show();
//				return;
//			}else{
//				//手机号格式正确，判断验证码是否为空，是否位数字
//				if(!TextUtils.isEmpty(str_varify)){
//					if(str_varify.matches("[0-9]+")){
//						
////======================================================================================================
//						//判断验证码是否正确
//						
//						
//						
////========================================================================================================	
//					//验证通过，设置viewage到第二界面
//						setActionBar("登录", "注册", "下一步");
//						vp.setCurrentItem(1);
//						
//					}else
//					{
//						//验证吗的格式不正确
//						new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
//						.setTitleText("提示")
//						.setContentText("验证码格式不正确")
//						.show();
//						return;
//					}
//				}else{
//					//判断验证码是否为空
//					new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
//					.setTitleText("提示")
//					.setContentText("验证码不能为空")
//					.show();
//					return;
//				}
//				
//				
//			}
//			
//			
//		}else{
//			//手机号为空
//			new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
//			.setTitleText("手机号不能为空")
//			.setContentText("")
//			.show();
//			return;
//		}
	}

	//获取验证码
	private void getVarify() {
		// TODO Auto-generated method stub
		
	}

	//注册前的数据检查
	private void proRegister() {
		// TODO Auto-generated method stub
		String fastName = et_fastName.getText().toString().trim();
		String company = et_company.getText().toString().trim();
		String pw = et_passwrod.getText().toString().trim();
		String pw2 = et_passwrod2.getText().toString().trim();
		
		if(TextUtils.isEmpty(fastName)){
			new SweetAlertDialog(Fastr_register_Activity.this, SweetAlertDialog.ERROR_TYPE)
			.setTitleText("姓名不能为空").show();
			return;
		}
		if(TextUtils.isEmpty(fastName)){
			new SweetAlertDialog(Fastr_register_Activity.this, SweetAlertDialog.ERROR_TYPE)
			.setTitleText("请选择所在快递公司").show();
			return;
		}
		if(TextUtils.isEmpty(fastName)){
			new SweetAlertDialog(Fastr_register_Activity.this, SweetAlertDialog.ERROR_TYPE)
			.setTitleText("密码不能为空").show();
			return;
		}
		if(TextUtils.isEmpty(fastName)){
			new SweetAlertDialog(Fastr_register_Activity.this, SweetAlertDialog.ERROR_TYPE)
			.setTitleText("确认密码不能为空").show();
			return;
		}else if(!pw2.equals(pw)){
			new SweetAlertDialog(Fastr_register_Activity.this, SweetAlertDialog.ERROR_TYPE)
			.setTitleText("两次密码不一致").show();
			return;
		}
		
		
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
		
		if(parent.getAdapter() == poiAdp){
			
			if(currentPoint == 1){
				this.poiList = new ArrayList<LatLng>();
			}
			this.poiList.add(poiAdpList.get(position).getLatlng());
			vp_poiBelow.setCurrentItem(2);
			if(currentPoint == 2){
				setActionBar("登录", "第 "+currentPoint +" 个点", "下一步");
				draLineOnMap(poiList.get(0),new LatLng(getLat(0), getLng(1)),poiList.get(1),new LatLng(getLat(1), getLng(0)),poiList.get(0));
			}else{
				setActionBar("登录", "第 "+currentPoint +" 个点", "继续");
			}
			
		}else{
			et_company.setText(Constants.companyList[position]);
			vp.setCurrentItem(2);
		}
			
	}
	
	private double getLat(int a){
		return poiList.get(a).latitude;
	}
	private double getLng(int a){
		return poiList.get(a).longitude;
	}
	
	
	/**
	 * page的监听器，
	 */
	@Override
	public void onPageScrollStateChanged(int a) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int a) {
	
	}
	
	//===================================================mapview的初始化，对生命周期的重写

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onResume() {
		super.onResume();
		mapview.onResume();
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onPause() {
		super.onPause();
		mapview.onPause();
		stopLocation();// 停止定位
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mapview.onSaveInstanceState(outState);
	}

	/**
	 * 方法必须重写
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		mapview.onDestroy();
	}
	
	//设置actionbar上面的提示
	private void setActionBar(String back,String title ,String next){
		but_back.setText(back);
		but_next.setText(next);
		tv_title.setText(title);
		
		if(TextUtils.isEmpty(back)){
			but_back.setClickable(false);
		}else{
			but_back.setClickable(true);
		}
		

		if(TextUtils.isEmpty(next)){
			but_next.setClickable(false);
		}else{
			but_next.setClickable(true);
		}
	}
	
	
	//划线
	private void draLineOnMap(LatLng ... list){
		polyline = aMap.addPolyline(new PolylineOptions().add(list).color(Color.BLUE).width(3));
		aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
	}
	
	
	/**
	 * =======================================================yi下是关于定位的方法
	 */
	
	//销毁定位
	private void stopLocation() {
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(this);
			aMapLocManager.destroy();
		}
		aMapLocManager = null;
	}
	
	private void location(){
		if(aMapLocManager == null)aMapLocManager = LocationManagerProxy.getInstance(this);
		aMapLocManager.requestLocationData(
				LocationProviderProxy.AMapNetwork, -1, 10, this);
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				if (aMapLocation == null) {
					cn.zh.Utils.ToastUtil.show(Fastr_register_Activity.this, "ToastUtil.java");
					stopLocation();// 销毁掉定位
				}
			}
		}, 12*1000);

	}

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
			Double lat = location.getLatitude();
			Double lng = location.getLongitude();
			String cityCode = "";
			String desc = "";
			Bundle locBundle = location.getExtras();
			if (locBundle != null) {
				cityCode = locBundle.getString("citycode");
				desc = locBundle.getString("desc");
			}
			moveCamera(new LatLng(lat, lng));
			searchAround(new LatLng(lat,lng));
		}
		
	}
	
	private void moveCamera(LatLng l){
		aMap.moveCamera(new CameraUpdateFactory().newLatLngZoom(l, 12));
	}
	
	
	//周边搜索
	private void searchAround(LatLng latng){

		// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query = new PoiSearch.Query("", "", "");
		query.setPageSize(20);// 设置每页最多返回多少条poiitem
		query.setPageNum(0);// 设置查第一页

			query.setLimitDiscount(false);
			query.setLimitGroupbuy(false);
		
		if (aMapLocation != null) {
			poiSearch = new PoiSearch(this, query);
			poiSearch.setOnPoiSearchListener(this);
			poiSearch.setBound(new SearchBound(
					new LatLonPoint(latng.latitude, latng.longitude), 1000, true));
			
			poiSearch.searchPOIAsyn();// 异步搜索
		}
	
	}

	@Override
	public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		if (rCode == 0) {
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				if (result.getQuery().equals(query)) {// 是否是同一条
					poiResult = result;
					poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
					
					if (poiItems != null && poiItems.size() > 0) {
					}else {
						cn.zh.Utils.ToastUtil.show(Fastr_register_Activity.this,
								"没有结果");
					}
				}
			} else {
				cn.zh.Utils.ToastUtil
						.show(Fastr_register_Activity.this, "没有结果");
			}
		} else if (rCode == 27) {
			cn.zh.Utils.ToastUtil
					.show(Fastr_register_Activity.this, "网络错误");
		}
		poiAdpList.clear();
		for(int i = 0;i<poiItems.size();i++){
			poiAdpList.add(new poiPoint(new LatLng(
					poiItems.get(i).getLatLonPoint().getLatitude(), poiItems.get(i).getLatLonPoint().getLongitude())
					, poiItems.get(i).getTitle(), poiItems.get(i).getAdName()));
		}
		poiAdp.setList(poiAdpList);
		poiAdp.notifyDataSetChanged();
	}

	@Override
	public void onMapClick(LatLng latng) {
		
		if(Fastr_register_Activity.this.currentPoint != 0){
			aMap.clear();// 清理之前的图标
		}
		this.aMapLocation.setLatitude(latng.latitude);
		this.aMapLocation.setLatitude(latng.longitude);
		locationMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 1)
				.position(latng));
		searchAround(latng);
	}
	private Boolean canTOInfoPager(){
		if(AMapUtils.calculateArea(poiList.get(0),poiList.get(1))>1E6){
			new SweetAlertDialog(this)
			.setTitleText("所选择的区域太小，请重新选择")
			.show();
			
			return false;
		}
		return true;
	}

	@Override
	public void activate(OnLocationChangedListener arg0) {
		location();
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		System.out.println("deactivate");
	}
	
	
	
	

}
