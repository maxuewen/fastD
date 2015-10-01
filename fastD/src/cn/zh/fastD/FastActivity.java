package cn.zh.fastD;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zh.Service.fastService;
import cn.zh.Utils.ActionBarUtils;
import cn.zh.Utils.Constants;
import cn.zh.Utils.HttpUtils;
import cn.zh.Utils.NetUtils;
import cn.zh.Utils.NoTouchViewPager;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.adapter.fast_m1_adp;
import cn.zh.adapter.fast_m2_Adpter;
import cn.zh.adapter.viewPagerAdapter;
import cn.zh.domain.main;
import cn.zh.domain.user;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class FastActivity extends Activity implements OnItemClickListener,OnClickListener,OnCheckedChangeListener
		,AMapLocationListener,Runnable{

	private NoTouchViewPager vp_main;
	private RadioButton cu_rbut;
	private RadioButton rbut_1;
	private RadioButton rbut_2;
	private RadioButton rbut_3;

	private ImageView iv_noData_m1;
	private ImageView tv_tip;
	private ImageView iv_noData_m3;
	
	private ViewPagerScroller vps;
	private int cu_page = 0;			//从0开始的；
	
	List<main> list_m1_adp;
	List<main> list_m2_adp;
	List<main> list_m3_adp;
	
	private ListView lv_m1;
	private ListView lv_m2;
	private ListView lv_m3;
	
	fast_m1_adp m1_lv_adp; 
	fast_m2_Adpter 	   m2_lv_adp; 
	fast_m1_adp m3_lv_adp; 
//	
	private View actionBar;
	private ImageButton but_back;
	private TextView tv_title;
	private Button but_next;
	
	private TextView m4_name_ship;
	private TextView m4_formNum;
	private TextView m4_shipPhone;
	private TextView m4_phone_receipt;
	private TextView m4_name_receipt;
	private TextView m4_receiptAd;
	private TextView m4_shipAd;
	private TextView m4_time;
	
	
	
	private LocationManagerProxy aMapLocManager = null;				//关于定位的
	private Handler handler = new Handler();
	private AMapLocation aMapLocation;// 用于判断定位超时
	
	private SharedPreferences sh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.m_layout_content);
		
		this.sh = getSharedPreferences("fastD", MODE_APPEND);
		
		//=========================================检查账号密码
//				SharedPreferences sh = getSharedPreferences("fastD", MODE_APPEND);
//				if(sh.getString("fastLogin", "false").equals("true")){
					if(NetUtils.isNetworkAvailable(this)){
						checkUser_password();
					}else{
						new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
						.setTitleText("还没有联网!")
						.show();
					}
//				}
		//================================================检查账号密码
		
		//=====================================================设置anctionbar
		actionBar = ActionBarUtils.setAtionBar(this,
				R.layout.fast_register_actionbar);
		but_back = (ImageButton) actionBar.findViewById(R.id.fr_back);
		but_back.setOnClickListener(this);
		tv_title = (TextView) actionBar.findViewById(R.id.fr_register_titleBar);
		but_next = (Button) actionBar.findViewById(R.id.fr_next);
		but_next.setOnClickListener(this);
		setActionBar("我的", "");
		setBackground_back(2);
		//=====================================================设置anctionbar
		init();
	}
		
	@Override
	protected void onStart() {
		F5();
//		if(sh.getString("fastService", "false").equals("false")){
			startService(new Intent(FastActivity.this,fastService.class));
//		}
		super.onStart();
	}
	
	
	@Override
	protected void onDestroy() {
//		
//		Editor edit = sh.edit();
//		Gson g = new Gson();
//		
//		System.out.println(Constants.fast_list_m1.size());
//		
//		edit.putString("fast_m1",g.toJson(Constants.fast_list_m1));
//		edit.putString("fast_m2",g.toJson(Constants.fast_list_m2));
//		edit.putString("fast_m3",g.toJson(Constants.fast_list_m3));
//		edit.commit();
		
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	
	

	private void init() {
	
		LayoutInflater inflater = LayoutInflater.from(this);
		vp_main = (NoTouchViewPager)findViewById(R.id.vp_main);
		List<View> list = new ArrayList<View>();
		
		// ==========================================================定位
		aMapLocManager = LocationManagerProxy.getInstance(this);
		aMapLocManager.requestLocationData(LocationProviderProxy.AMapNetwork, 60 * 1000, 10, this);
		handler.postDelayed(this, 12000);// 设置超过12秒还没有定位到就停止定位
		
		// ==========================================================主界面第一张
		View v ;
		v = inflater.inflate(R.layout.user_m_mypage, null);
		lv_m1 = (ListView) v.findViewById(R.id.lv_myRecorde);
		iv_noData_m1 = (ImageView) v.findViewById(R.id.iv_user_m3_centent);
		list_m1_adp = new ArrayList<main>();
		m1_lv_adp = new fast_m1_adp(this, list_m1_adp);
		lv_m1.setAdapter(m1_lv_adp);
		lv_m1.setOnItemClickListener(this);
		list.add(v);
		// ==========================================================主界面第二张
		v = inflater.inflate(R.layout.user_m_ship, null);
		lv_m2 = (ListView) v.findViewById(R.id.lv_ReceiptAdInfo);
		tv_tip = (ImageView) v.findViewById(R.id.iv_user_m2_centent);
		list_m2_adp = new ArrayList<main>();
		m2_lv_adp = new fast_m2_Adpter(this, list_m2_adp);
		lv_m2.setAdapter(m2_lv_adp);
		lv_m2.setOnItemClickListener(this);
		list.add(v);
		// ==========================================================主界面第三张
		v = inflater.inflate(R.layout.user_m3_receipt, null);
		iv_noData_m3 = (ImageView) v.findViewById(R.id.iv_user_m3_centent);
		lv_m3 = (ListView) v.findViewById(R.id.lv_user_m3_receipt);
		list_m3_adp = new ArrayList<main>();
		m3_lv_adp = new fast_m1_adp(this,list_m3_adp);
		lv_m3.setAdapter(m3_lv_adp);
		lv_m3.setOnItemClickListener(this);
		list.add(v);
		
		// ==========================================================界面第四张  订单的详细信息显示页面
		v =inflater.inflate(R.layout.fast_m4_form_more, null);
		m4_name_ship = (TextView)v.findViewById(R.id.m4_name_ship_);
		m4_formNum = (TextView)v.findViewById(R.id.m4_formNum_);
		m4_shipPhone = (TextView)v.findViewById(R.id.m4_shipPhone_);
		m4_phone_receipt = (TextView)v.findViewById(R.id.m4_receipt_phone_);
		m4_name_receipt = (TextView)v.findViewById(R.id.m4_name_receipt_);
		m4_receiptAd = (TextView)v.findViewById(R.id.m4_receiptAd_);
		m4_shipAd = (TextView)v.findViewById(R.id.m4_shipAd_);
		m4_time = (TextView)v.findViewById(R.id.m4_time_);
		list.add(v);
		
		
		rbut_1 = ((RadioButton) findViewById(R.id.radio_button1));
		rbut_1.setOnCheckedChangeListener(this);
		rbut_2 = ((RadioButton) findViewById(R.id.radio_button2));
		rbut_2.setOnCheckedChangeListener(this);
		rbut_3 = ((RadioButton) findViewById(R.id.radio_button3));
		rbut_3.setOnCheckedChangeListener(this);
		
		viewPagerAdapter vpad = new viewPagerAdapter(this, list);
		vp_main.setAdapter(vpad);
		
		vps = new ViewPagerScroller(this);
		vps.setScrollDuration(0);
		vps.initViewPagerScroll(vp_main);
		
		setBottomRbut(1); // 设置button1为选中状态
		cu_rbut = rbut_1;
		
		F5();
		
		///从本地加载数据
		
		String str = null;
		if(sh.contains("fast_m1")){
			str = sh.getString("fast_m1", null);
			if(!TextUtils.isEmpty(str)){
				List<main> m = new Gson().fromJson(str, new TypeToken<ArrayList<main>>(){}.getType());
				Constants.fast_list_m1 = new ArrayList<main>();
				if(m.size() != 0){
					Constants.fast_list_m1.addAll(m);
					list_m1_adp.addAll(m);
					m1_lv_adp.notifyDataSetChanged();
				}
			}
		}else {
			return;
		}
		
		str = sh.getString("fast_m2", null);
		if(!TextUtils.isEmpty(str)){
			List<main> m =new Gson().fromJson(str, new TypeToken<ArrayList<main>>(){}.getType());
			Constants.fast_list_m2 = new ArrayList<main>();
			if(m.size() != 0){
				Constants.fast_list_m2.addAll(m);
				list_m2_adp.addAll(m);
				m2_lv_adp.notifyDataSetChanged();
			}
		}
		
		str = sh.getString("fast_m3", null);
		if(!TextUtils.isEmpty(str)){
			List<main> m = new Gson().fromJson(str, new TypeToken<ArrayList<main>>(){}.getType());
		 	System.out.println("2");
			Constants.fast_list_m3 = new ArrayList<main>();
			if(m.size() != 0){
				Constants.fast_list_m3.addAll(m);
				list_m3_adp.addAll(m);
				m3_lv_adp.notifyDataSetChanged();
			}
		}
			
	}
	
	


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		main m1 = (main) Constants.fast_list_m1.get(position);
		
		// 设置actionbar

		if (parent.getAdapter() == m1_lv_adp 
				&& m1.getState().equals(Constants.formState_unfinish)) {///未接收，显示地图
			
			Intent in = new Intent(FastActivity.this,MapandInfoActivity.class);
			in.putExtra("method", new Gson().toJson(Constants.fast_list_m1.get(position)));
			startActivity(in);
			
		}else if(parent.getAdapter() == m1_lv_adp 
				&& m1.getState().equals(Constants.formState_doing)){		//已接收，，订单查询
			Intent in = new Intent(FastActivity.this,WebActivity_simple.class);
			in.putExtra("url", m1.getFormNum());
			in.putExtra("method", "FastActivity");
			startActivity(in);
		}
		
		else if (parent.getAdapter() == m2_lv_adp) { // 区域内订单===============================
			Intent in = new Intent(FastActivity.this,MapandInfo_2_Activity.class);
			in.putExtra("method", new Gson().toJson(Constants.fast_list_m2.get(position)));
			startActivity(in);

		} else if (parent.getAdapter() == m3_lv_adp) {		//订单详情//第三张页面
			
			main m = Constants.fast_list_m3.get(position);
			
			m4_formNum.setText(m.getFormNum());
			m4_name_receipt.setText(m.getRecieptName());
			m4_name_ship.setText(m.getUser_name());
			m4_phone_receipt.setText(m.getReceiptPhone());
			m4_receiptAd.setText(m.getRiceiptAd());
			m4_shipAd.setText(m.getShipAd());
			m4_shipPhone.setText(m.getUser_phone());
			m4_time.setText(m.getTime());
			
			vps.setScrollDuration(600);
			vp_main.setCurrentItem(3);
			vps.setScrollDuration(0);
			
			setActionBar("订单详情", "");
			setBackground_back(1);
			
			cu_page = 3;
		
		} else {

		}
		
	}
	
	
	private void setActionBar(String title, String next) {
		but_next.setText(next);
		tv_title.setText(title);

		if (TextUtils.isEmpty(next)) {
			but_next.setClickable(false);
		} else {
			but_next.setClickable(true);
		}
	}
	
	private void setBackground_back(int a) {
		if (a == 1) {
			but_back.setBackground(this.getResources().getDrawable(
					R.drawable.ic_action_back));
			but_back.setClickable(true);
		} 
		else {
			but_back.setBackground(null);
			but_back.setClickable(false);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.fr_back:
		System.out.println("case R.id.fr_back:");	
			setBackBut();
			break;
			
		case R.id.fr_next:
			if("退出".equals(but_next.getText())){
				Intent in = new Intent(FastActivity.this,LoginActivity.class);
				in.putExtra("method", "FastActivity");
				
				Editor edit = sh.edit();
				edit.putString("fastLogin", "false");
				edit.commit();
				
				startActivity(in);
//				FastActivity.this.finish();
			}
		}
		
		
		
		
		
	}
	private void setBackBut(){
		int a = vp_main.getCurrentItem();
		if(a == 3){
			vps.setScrollDuration(600);
			vp_main.setCurrentItem(0);
			vps.setScrollDuration(0);
			
			setActionBar("我的", "");
			setBackground_back(2);
			
			cu_page = 0;
		}else if(a == 4){
			
		}
	}
	

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			setBackground_back(2);
			switch (buttonView.getId()) {
			case R.id.radio_button1:
				if (Constants.fast_list_m1 != null
						&& Constants.fast_list_m1.size() != 0
						&& list_compan(Constants.fast_list_m1,list_m1_adp) != 0) {
					list_m1_adp.clear();
					list_m1_adp.addAll(Constants.fast_list_m1);
					m1_lv_adp.notifyDataSetChanged();
				}
				setBottomRbut(1);
				vp_main.setCurrentItem(0);

				cu_rbut = rbut_1;
				cu_page = 0;
				break;
			case R.id.radio_button2:
				if (Constants.fast_list_m2 != null
						&& Constants.fast_list_m2.size() != 0
						&& Constants.fast_list_m2.size() != list_m2_adp.size()) {
					list_m2_adp.clear();
					list_m2_adp.addAll(Constants.fast_list_m2);
					m2_lv_adp.notifyDataSetChanged();
				}
				setBottomRbut(2);
				vp_main.setCurrentItem(1);
				cu_rbut = rbut_2;
				cu_page = 1;
				break;
			case R.id.radio_button3:
				if (Constants.fast_list_m3 != null
						&& Constants.fast_list_m3.size() != 0
						&& list_compan(Constants.fast_list_m3,list_m3_adp) != 0) {
					list_m3_adp.clear();
					list_m3_adp.addAll(Constants.fast_list_m3);
					m3_lv_adp.notifyDataSetChanged();
				}
				
				

				setBottomRbut(3);
				vp_main.setCurrentItem(2);

				cu_rbut = rbut_3;
				cu_page = 2;
				break;

			}
		}
	}
	
	private int list_compan(List<main> l1,List<main> l2){
		int a = l1.size(); int b = l2.size();
		if(a > b) return a-b;
		if(a < b) return b-a;
		int c = 0;
		for(int i = 0; i < a; i++){
			if(!l1.get(i).getState().equals(l2.get(i).getState())){
				c++;
			}
		}
		return c;
	}
	//108, 108, 108			36, 155, 255
	private void setBottomRbut(int a) {
		if (cu_rbut != null){			//==============================有错,,,
			cu_rbut.setTextColor(Color.rgb(81, 81, 81));
			if(cu_rbut == rbut_1){
				rbut_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home1, 0, 0);
			}else if(cu_rbut == rbut_2){
				rbut_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ship1, 0, 0);
			}else{
				rbut_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.receipt1, 0, 0);
			}
		}
			

		if (a == 1) {
			rbut_1.setTextColor(FastActivity.this.getResources().getColor(R.color.blue_main));
			rbut_1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.home, 0, 0);
			
			setActionBar("我的", "");
			setBackground_back(2);

			if (Constants.fast_list_m1 == null
					|| Constants.fast_list_m1.size() == 0) {
				iv_noData_m1.setVisibility(0x00000000);
			} else {
				iv_noData_m1.setVisibility(0x00000004);
			}

		} else if (a == 2) {
			rbut_2.setTextColor(FastActivity.this.getResources().getColor(R.color.blue_main));
			rbut_2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ship, 0, 0);
			
			setActionBar("区域内", "");
			setBackground_back(2);

			if (Constants.fast_list_m2 == null
					|| Constants.fast_list_m2.size() == 0) {
				tv_tip.setVisibility(0x00000000);
			} else {
				tv_tip.setVisibility(0x00000004);
			}
		} else if (a == 3) {
			rbut_3.setTextColor(FastActivity.this.getResources().getColor(R.color.blue_main));
			rbut_3.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.receipt, 0, 0);
			
			setActionBar("已完成", "退出");
			setBackground_back(2);

			if (Constants.fast_list_m3 == null
					|| Constants.fast_list_m3.size() == 0) {
				iv_noData_m3.setVisibility(0x00000000);
			} else {
				iv_noData_m3.setVisibility(0x00000004);
			}
		}
	}
	
	
	private void F5(){
		if (Constants.fast_list_m3 == null
				|| Constants.fast_list_m3.size() == 0) {
			iv_noData_m3.setVisibility(0x00000000);
		} else {
			iv_noData_m3.setVisibility(0x00000004);
		}
		if (Constants.fast_list_m2 == null
				|| Constants.fast_list_m2.size() == 0) {
			tv_tip.setVisibility(0x00000000);
		} else {
			tv_tip.setVisibility(0x00000004);
		}
		if (Constants.fast_list_m1 == null
				|| Constants.fast_list_m1.size() == 0) {
			iv_noData_m1.setVisibility(0x00000000);
		} else {
			iv_noData_m1.setVisibility(0x00000004);
		}
		
		if (Constants.fast_list_m1 != null
				&& Constants.fast_list_m1.size() != 0
				&& list_compan(Constants.fast_list_m1,list_m1_adp) != 0) {
			list_m1_adp.clear();
			list_m1_adp.addAll(Constants.fast_list_m1);
			m1_lv_adp.notifyDataSetChanged();
		}
		
		if (Constants.fast_list_m2 != null
				&& Constants.fast_list_m2.size() != 0
				&& Constants.fast_list_m2.size() != list_m2_adp.size()) {
			list_m2_adp.clear();
			list_m2_adp.addAll(Constants.fast_list_m2);
			m2_lv_adp.notifyDataSetChanged();
		}
		
		if (Constants.fast_list_m3 != null
				&& Constants.fast_list_m3.size() != 0
				&& list_compan(Constants.fast_list_m3,list_m3_adp) != 0) {
			list_m3_adp.clear();
			list_m3_adp.addAll(Constants.fast_list_m3);
			m3_lv_adp.notifyDataSetChanged();
		}

		
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
	public void run() {
		if (aMapLocation == null) {
			stopLocation();// 销毁掉定位
		}
	}
	private void stopLocation() {
		if (aMapLocManager != null) {
			aMapLocManager.removeUpdates(this);
			aMapLocManager.destroy();
		}
		aMapLocManager = null;
	}

	@Override
	public void onLocationChanged(AMapLocation location) {
		if (location != null) {
			this.aMapLocation = location;// 判断超时机制
			System.out.println(location);
			Constants.fast_lat = location.getLatitude();
			Constants.fast_lng = location.getLongitude();
		}
		
	}
	
	///判断快递员的密码是否正确
	
	private void checkUser_password(){
		Gson j = new Gson();
		
		RequestParams map = new RequestParams();
		map.put("method",j.toJson( Constants.get_fast));
		map.put("param", j.toJson(sh.getString("fastName", null)));
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(HttpUtils.url+"fastServlet", map, new AsyncHttpResponseHandler(){
			@Override
			public void onSuccess(int statusCode, Header[] arg1, byte[] responseBody) {
				if (statusCode == 200) {
					Gson j = new Gson();
					user u = j.fromJson(new String(responseBody), user.class);
					if(u != null){
						if(!u.getPassword().equals(sh.getString("userPassword", null)) ){
							
							new SweetAlertDialog(FastActivity.this, SweetAlertDialog.SUCCESS_TYPE)
							.setTitleText("您的密码已被修改过，请重新登录")
							.setConfirmClickListener(
									new SweetAlertDialog.OnSweetClickListener() {

										@Override
										public void onClick(
												SweetAlertDialog sweetAlertDialog) {
											Intent in = new Intent(FastActivity.this,LoginActivity.class);
											in.putExtra("method", "FastActivity");
											
											Editor edit = sh.edit();
											edit.putString("fastLogin", "false");
											edit.commit();
											
											startActivity(in);
											FastActivity.this.finish();
										}
									}).show();
						}
					}
				}
			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				
			}
		});
	}
	
	

}
