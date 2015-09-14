package cn.zh.fastD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.zh.Service.userService;
import cn.zh.Utils.ActionBarUtils;
import cn.zh.Utils.Constants;
import cn.zh.Utils.HttpUtils;
import cn.zh.Utils.NetUtils;
import cn.zh.Utils.NoTouchViewPager;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.Utils.myUUID;
import cn.zh.Utils.ActionProcessButton.Mode;
import cn.zh.adapter.viewPagerAdapter;
import cn.zh.domain.main;
import cn.zh.domain.user;

import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class User_register_Activity extends Activity implements
		OnPageChangeListener, OnClickListener {
	
	//要提交的内容
	String phone = null;
	private Boolean isexit = false;

	int mod; // 2代表密码找回，1代表注册
	int isUser; // 1代表快递员，0代表用户		

	NoTouchViewPager vp;
	ViewPagerScroller vps;

	TextView tv; // 错误提示
	private EditText vp1_phone;
	private EditText varify;
	private EditText password1;
	private EditText password2;
	private EditText userName;

	private Button next_but;
	private Button varify_but;
	private Button rigister_finish;
	private View actionBarView;
	private ImageButton back_but;
	// 补充代码-开始
	String APPKEY = "a58f04b86004";
	String APPSECRET = "3ae0713d0d78998bcad8507d3a459024";

	String vp1_phone2;
	String varify2;

	private void init() {
		// TODO Auto-generated method stub

		vp = (NoTouchViewPager) findViewById(R.id.user_register);
		LayoutInflater inflater = LayoutInflater.from(this);
		List<View> list = new ArrayList<View>();
		View v1 = inflater.inflate(R.layout.user_register_viewpage_1, null);
		View v2 = inflater.inflate(R.layout.user_register_viewpage_2, null);
		
		
		if(isUser == 0 && mod == 1){
			userName = (EditText)v2.findViewById(R.id.urv2_userName);
			userName.setVisibility(View.VISIBLE);
			
		}

		list.add(v1);
		list.add(v2);

		vp.setAdapter(new viewPagerAdapter(this, list));
		vp.setOnPageChangeListener(this);

		vps = new ViewPagerScroller(this);
		vps.setScrollDuration(700);
		vps.initViewPagerScroll(vp);

		actionBarView = ActionBarUtils.setAtionBar(this,
				R.layout.user_register_actionbar);

		// 获取界面上的元素
		vp1_phone = (EditText) v1.findViewById(R.id.urv1_phone);
		varify = (EditText) v1.findViewById(R.id.urv1_varify);
		tv = (TextView) v1.findViewById(R.id.urv1_toast);
		next_but = (Button) v1.findViewById(R.id.urv1_next_but);
		varify_but = (Button) v1.findViewById(R.id.urv1_getvarify_but);
		back_but = (ImageButton) actionBarView.findViewById(R.id.back);
		// 用于密码找会页面
		if (mod == 2 && isUser == 0) {
			TextView title = (TextView) actionBarView
					.findViewById(R.id.user_register_titleBar);
			title.setText("用户密码找回");
		} else if (isUser == 1 && mod == 2) {
			TextView title = (TextView) actionBarView
					.findViewById(R.id.user_register_titleBar);
			title.setText("快递员密码找回");
		} else {

		}

		rigister_finish = (Button) v2.findViewById(R.id.urv2_but);
		password1 = (EditText) v2.findViewById(R.id.urv2_pw1);
		password2 = (EditText) v2.findViewById(R.id.urv2_pw2);

		next_but.setOnClickListener(this);
		varify_but.setOnClickListener(this);
		back_but.setOnClickListener(this);
		rigister_finish.setOnClickListener(this);

	}

	private void initSMS() {
		SMSSDK.initSDK(this, APPKEY, APPSECRET);
		EventHandler eh = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handlerSMS.sendMessage(msg);
			}
		};
		SMSSDK.registerEventHandler(eh);
	}

	Handler handlerSMS = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int event = msg.arg1;
			int result = msg.arg2;
			Object data = msg.obj;
			if (result == SMSSDK.RESULT_COMPLETE) {
				// 回调成功
				if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
					// 获取验证码成功
					new SweetAlertDialog(User_register_Activity.this,
							SweetAlertDialog.SUCCESS_TYPE)
							.setTitleText("正在发送验证码").setContentText("").show();
				} else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
					// 提交验证码成功
					//=========================进入第二张页面============================
					 phone = vp1_phone.getText().toString().trim();
					 if(isexit == true && (isUser == 0 && mod == 1)){
						 show("手机号已经注册过");
						 return;
					 }else if(isexit == false && !(isUser == 0 && mod == 1)){
						 show("手机号还未注册");
						 return;
					 }else{
						 vp.setCurrentItem(1);
					 }
					 
				}
			} else {
				// 回调失败
				// 验证失败包含多种因素（比如验证码错误、网络因素等）
				new SweetAlertDialog(User_register_Activity.this,
						SweetAlertDialog.ERROR_TYPE).setTitleText("获取验证码失败")
						.setContentText("").show();
			}
		}
	};

	// 补充代码-结束

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register_main);

		mod = getIntent().getIntExtra("mod", 1);
		isUser = getIntent().getIntExtra("isUser", 0);

		init();
		// 补充代码-开始
		initSMS();
		// 补充代码-结束
	}

	// 补充代码-开始
	@Override
	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	}

	// 补充代码-结束

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.urv1_getvarify_but:
			// 补充代码-开始
			// 获取验证码
			String p = vp1_phone.getText().toString().trim();
			if (!TextUtils.isEmpty(p)) {
				// 判断手机号是否为空
				// 若手机号不为空
				if (p.length() == 11) {
					// 判断手机号输入是否合法
					// 若手机号输入合法
					vp1_phone2 = vp1_phone.getText().toString().trim();
					// 获取验证码
					// 处理过程交给handlerSMS
					phoneExist(p);
					SMSSDK.getVerificationCode("86", vp1_phone2);
//					varify.requestFocus();
				} else {
					// 若手机号输入不合法
					new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
							.setTitleText("电话号码有误").setContentText("").show();
					vp1_phone.requestFocus();
				}
			} else {
				// 若手机号为空
				new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
						.setTitleText("手机号不能为空").setContentText("").show();
				vp1_phone.requestFocus();
			}
			// 补充代码-结束
			break;
		case R.id.urv1_next_but:
			nextPagerPro();
			break;
		case R.id.back:
			startActivity(new Intent(User_register_Activity.this,
					LoginActivity.class));
			finish();
			break;
		case R.id.urv2_but:
			urv2_but();
			break;
		}

	}

	private void urv2_but() {
		String pw1 = password1.getText().toString().trim();
		String pw2 = password2.getText().toString().trim();

		if (TextUtils.isEmpty(pw1)) {
			new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
					.setTitleText("密码不能为空").setContentText("").show();
			return;
		} else {
			if (pw1.length() >= 6) {
				// pw1没问题，判断1和2是否相等
				if (pw1.equals(pw2)) {
					// ==============================================================================================
					// 数据检验成功，从这里开始注册
					String str;
					
					Gson j = new Gson();
					AsyncHttpClient client = new AsyncHttpClient();
					
					if (mod == 2 && isUser == 0) {
						str = "密码找回成功";		//用户密码找回============================
						
						RequestParams map = new RequestParams();
						map.put("method", j.toJson(Constants.alertPasswrod));
						map.put("phone", j.toJson(this.phone));
						map.put("password", j.toJson(pw1));
						
						client.post(HttpUtils.url+"userServlet", map, new AsyncHttpResponseHandler(){
							@Override
							public void onFailure(int arg0, Header[] arg1, byte[] arg2,
									Throwable arg3) {
								
								if(!NetUtils.isNetworkAvailable(User_register_Activity.this)){
									show("网络错误");return;
								}else{
									show("服务器错误");return;
								}
							}
							@Override
							public void onStart() {
								// TODO Auto-generated method stub
								rigister_finish.setClickable(false);
							}
							@Override
							public void onSuccess(int statusCode, Header[] arg1, byte[] responseBody) {
								if (statusCode == 200) {
									Gson j = new Gson();
									String str= j.fromJson(new String(responseBody), String.class);
									if(!str.equals(Constants.ok)){
										show("找回失败");
										return;
									}else{
										show_ok(str);
									}
								}else{
									show("服务器错误");return;
								}
							}
						});

					} else {
						if (isUser == 0) {
							str = "注册成功";			//===============================用户注册
							String name = userName.getText().toString().trim();
							if(!TextUtils.isEmpty(name)){
								RequestParams map = new RequestParams();
								map.put("method", j.toJson(Constants.add_user));
								user u = new user(myUUID.getUUID(), this.phone, name, pw1
										, Constants.user_lat, Constants.user_lng);
								map.put("param", j.toJson(u));
								
								client.post(HttpUtils.url+"userServlet", map, new AsyncHttpResponseHandler(){
									@Override
									public void onFailure(int arg0, Header[] arg1, byte[] arg2,
											Throwable arg3) {
										
										if(!NetUtils.isNetworkAvailable(User_register_Activity.this)){
											show("网络错误");return;
										}else{
											show("服务器错误");return;
										}
									}
									@Override
									public void onStart() {
										// TODO Auto-generated method stub
										rigister_finish.setClickable(false);
									}
									@Override
									public void onSuccess(int statusCode, Header[] arg1, byte[] responseBody) {
										if (statusCode == 200) {
											Gson j = new Gson();
											String str= j.fromJson(new String(responseBody), String.class);
											if(!str.equals(Constants.ok)){
												show("注册失败");return;
											}else{
												show_ok(str);
											}
										}else{
											show("注册失败");return;
										}
									}
								});
								
							}else{
								show("姓名不能为空");return;
							}
						} else {
							str = "密码找回成功";	//快递员密码找回成功
							RequestParams map = new RequestParams();
							map.put("method", j.toJson(Constants.alertPasswrod));
							map.put("phone", j.toJson(this.phone));
							map.put("password", j.toJson(pw1));
							
							client.post(HttpUtils.url+"fastServlet", map, new AsyncHttpResponseHandler(){
								@Override
								public void onFailure(int arg0, Header[] arg1, byte[] arg2,
										Throwable arg3) {
									
									if(!NetUtils.isNetworkAvailable(User_register_Activity.this)){
										show("网络错误");return;
									}else{
										show("服务器错误");return;
									}
								}
								@Override
								public void onStart() {
									// TODO Auto-generated method stub
									rigister_finish.setClickable(false);
								}
								@Override
								public void onSuccess(int statusCode, Header[] arg1, byte[] responseBody) {
									if (statusCode == 200) {
										Gson j = new Gson();
										String str= j.fromJson(new String(responseBody), String.class);
										if(!str.equals(Constants.ok)){
											show("找回失败");return;
										}else{
											show_ok(str);
										}
									}else{
										show("服务器错误");return;
									}
								}
							});
						}
					}
/////////////操作完成，回到登陆界面
					

				} else {
					new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
							.setTitleText("提示").setContentText("两次密码不一致")
							.show();
					return;
				}

			} else {
				new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
						.setTitleText("提示").setContentText("密码的最小长度为6").show();
				return;
			}
		}

	}
	
	private void show(String str){
		new SweetAlertDialog(User_register_Activity.this,SweetAlertDialog.ERROR_TYPE)
		.setTitleText(str)
		.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
								
								@Override
								public void onClick(SweetAlertDialog sweetAlertDialog) {
									rigister_finish.setClickable(true);
									sweetAlertDialog.cancel();
								}
							})
		.show();
	}
	
	private void show_ok(String str){
		new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
		.setTitleText(str)
		.setConfirmClickListener(
				new SweetAlertDialog.OnSweetClickListener() {

					@Override
					public void onClick(
							SweetAlertDialog sweetAlertDialog) {
						// TODO Auto-generated method stub
						startActivity(new Intent(
								User_register_Activity.this,
								LoginActivity.class));
						User_register_Activity.this.finish();
					}
				}).show();
	}

	private void nextPagerPro() {
		
		String phone = vp1_phone.getText().toString().trim();
		if (!TextUtils.isEmpty(phone)) {
			// 判断手机号是否为空
			// 若手机号不为空
			if (phone.length() == 11) {
				// 判断手机号输入是否合法
				// 若手机号合法
				vp1_phone2 = vp1_phone.getText().toString().trim();
				// 判断验证码输入是否合法
				if (varify.getText().toString().trim().length() == 4) {
					// 若验证码输入合法
					varify2 = varify.getText().toString().trim();
					// 提交验证码
					// 处理过程交给
					SMSSDK.submitVerificationCode("86", vp1_phone2, varify2);
				} else {
					// 若验证码输入不合法
					new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
							.setTitleText("验证码格式错误").setContentText("").show();
				}
			} else {
				// 若手机号输入不合法
				new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
						.setTitleText("手机号格式错误").setContentText("").show();
				vp1_phone.requestFocus();
			}
		} else {
			// 若手机号为空
			new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
					.setTitleText("手机号不能为空").setContentText("").show();
			vp1_phone.requestFocus();
		}
		// 补充代码-结束
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
	
	private void phoneExist(String phone){
		
		Gson j = new Gson();
		RequestParams map = new RequestParams();
		map.put("method", j.toJson(Constants.get_user));
		map.put("param", j.toJson(phone));
		AsyncHttpClient client = new AsyncHttpClient();
		client.post(HttpUtils.url+"userServlet", map, new AsyncHttpResponseHandler(){
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
			}
			@Override
			public void onStart() {
			}
			@Override
			public void onSuccess(int statusCode, Header[] arg1, byte[] responseBody) {
				if (statusCode == 200) {
					Gson j = new Gson();
					user u= j.fromJson(new String(responseBody), user.class);
					if(u != null){
						isexit = true;
					}
				}
			}
		});
	}
}
