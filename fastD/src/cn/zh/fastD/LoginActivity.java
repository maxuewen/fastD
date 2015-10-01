package cn.zh.fastD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zh.Utils.ActionProcessButton;
import cn.zh.Utils.ActionProcessButton.Mode;
import cn.zh.Utils.Constants;
import cn.zh.Utils.HttpUtils;
import cn.zh.Utils.NetUtils;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.adapter.viewPagerAdapter;
import cn.zh.domain.fast;
import cn.zh.domain.main;
import cn.zh.domain.user;
import cn.zh.domain.user_Ad;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.*;

public class LoginActivity extends Activity implements OnClickListener,
		OnPageChangeListener {

	ViewPager viewPager;
	viewPagerAdapter adap;
	List<View> list;
	ViewPagerScroller vps;

	ActionProcessButton user_login_but;
	ActionProcessButton fast_login_but;
	Button user_findpassword_but;
	Button fast_findpasswrod_but;
	Button user_register_but;
	Button fast_register_but;
	EditText username;
	EditText fastname;
	TextView user_toast;
	EditText user_passwrod;
	EditText fast_password;
	TextView fast_toast;
	
	private int cu_page = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
			
		
			SharedPreferences sh = getSharedPreferences("fastD", MODE_APPEND);
			String s = getIntent().getStringExtra("islogin");
			
			if("login_false".equals(s)){
				Editor edit = sh.edit();
				edit.putString("userLogin", "false");
				edit.commit(); 
			}
			
			String str = getIntent().getStringExtra("method");
			if(sh.getString("userLogin", "false").equals("true") &&!"MainActivity".equals(str)){
				startActivity(new Intent(this,MainActivity.class));
				this.finish();
			}else if( sh.getString("fastLogin", "false").equals("true") && !"FastActivity".equals(str)){
				startActivity(new Intent(this,FastActivity.class));
				this.finish();
			}
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		LayoutInflater flat = LayoutInflater.from(this);
		list = new ArrayList<View>();
		View view1 = flat.inflate(R.xml.user_page, null);
		View view2 = flat.inflate(R.xml.fastd_page, null);
		list.add(view1);
		list.add(view2);

		user_register_but = (Button) view1.findViewById(R.id.user_register_but);
		user_login_but = (ActionProcessButton) view1.findViewById(R.id.user_login_but);
		user_findpassword_but = (Button) view1
				.findViewById(R.id.user_findPassword_but);
		user_login_but.setOnClickListener(this);
		user_register_but.setOnClickListener(this);
		user_findpassword_but.setOnClickListener(this);

		username = (EditText) view1.findViewById(R.id.user_username);
		user_passwrod = (EditText) view1.findViewById(R.id.user_password);
		user_toast = (TextView) view1.findViewById(R.id.toast_user);
//		tv = user_toast;

		fast_password = (EditText) view2.findViewById(R.id.fastd_password);
		fastname = (EditText) view2.findViewById(R.id.fastd_username);
		fast_toast = (TextView) view2.findViewById(R.id.toast_fast);
		
		fast_findpasswrod_but = (Button) view2.findViewById(R.id.fastd_findPassword_but);
		fast_register_but = (Button) view2.findViewById(R.id.fast_register_but);
		fast_login_but = (ActionProcessButton) view2.findViewById(R.id.fast_login_but);
		fast_findpasswrod_but.setOnClickListener(this);
		fast_register_but.setOnClickListener(this);
		fast_login_but.setOnClickListener(this);

		adap = new viewPagerAdapter(this, list);

		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(adap);
		viewPager.setOnPageChangeListener(this);
		
		//初始化viewpagerscroller
		vps = new ViewPagerScroller(this);
		vps.setScrollDuration(700);
		vps.initViewPagerScroll(viewPager);
	}

	//界面上的一切click的处理
	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.fast_login_but:
			fast_login();
			break;

		case R.id.fast_register_but:
			startActivity(new Intent(LoginActivity.this,Fastr_register_Activity.class));
			break;
		case R.id.fastd_findPassword_but:
			Intent in = new Intent(LoginActivity.this,User_register_Activity.class);
			in.putExtra("isUser",viewPager.getCurrentItem());
			in.putExtra("mod",2);
			startActivity(in);
			break;
		case R.id.user_findPassword_but:
			Intent in2 = new Intent(LoginActivity.this,User_register_Activity.class);
			in2.putExtra("isUser",viewPager.getCurrentItem());
			in2.putExtra("mod",2);
			startActivity(in2);
			break;
		case R.id.user_login_but:
			user_login();
			break;
		case R.id.user_register_but:
			Intent in1 = new Intent(LoginActivity.this,User_register_Activity.class);
			in1.putExtra("isUser",viewPager.getCurrentItem());
			in1.putExtra("mod",1);
			startActivity(in1);
			break;
		}

	}


	@Override
	public void onPageScrollStateChanged(int a) {

	}

	@Override
	public void onPageScrolled(int a, float b, int c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int a) {
		cu_page = a;
	}
	
	Handler hand = new Handler();
	
	
	//登陆按钮
	private Boolean login_pro(String userName,String password){
		
		if(!TextUtils.isEmpty(userName)){
			if(userName.length()!=11){
				new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
				.setTitleText("提示")
				.setContentText("手机号长度不正确")
				.show();
				return false;
			}else{
				Pattern compile = Pattern.compile("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$");
				Matcher matcher = compile.matcher(userName);
				if(!matcher.find()){
					
					new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
					.setTitleText("提示")
					.setContentText("手机号格式不正确")
					.show();
					
					return false;
				}
			}
		}else{
			new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
			.setTitleText("账号不能为空")
			.setContentText("")
			.show();
			
			return false;
		}
		if(!TextUtils.isEmpty(password)){
			if(password.length()<6||password.length()>20){
				
				new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
				.setTitleText("提示")
				.setContentText("密码长度不正确")
				.show();
				
				return false;
			}
		}else{
			new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
			.setTitleText("密码不能为空")
			.setContentText("")
			.show();
			
			return false;
		}
		
		return true;
	}
	
	private void user_login() {
		
		
		final String userName = username.getText().toString().trim();
		final String password = user_passwrod.getText().toString().trim();
		//==============================================本地账号
		if(userName.equals("0") && password.equals("0")){
			startActivity(new Intent(LoginActivity.this,MainActivity.class));
			LoginActivity.this.finish();
			return;
		}
		//==============================================本地账号		
		
		if(login_pro(userName,password)){
			RequestParams map = new RequestParams();
			map.put("method", Constants.get_user);
			map.put("param", userName);
			user_login_but.setProgress(10);
			
			AsyncHttpClient client = new AsyncHttpClient();
			client.post(HttpUtils.url+"userServlet", map, new AsyncHttpResponseHandler(){
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					if(!NetUtils.isNetworkAvailable(LoginActivity.this)){
						show("网络错误");
					}else{
						show(new String("连接不到服务器"));
					}
				}
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					user_login_but.setProgress(20);
					user_login_but.setMode(Mode.PROGRESS);
					user_login_but.setProgress(20);
					user_login_but.setClickable(false);
					user_login_but.setProgress(25);
					
				}
				@Override
				public void onSuccess(int statusCode, Header[] arg1, byte[] responseBody) {
					user_login_but.setProgress(40);
					if (statusCode == 200) {
						Gson j = new Gson();
						user u = j.fromJson(new String(responseBody), user.class);
						user_login_but.setProgress(45);
						if(u != null){
							if(!u.getPassword().equals(password) ){
								show("账号或密码错误");
							}else{
								///密码正确，开始登陆钱的准备
								SharedPreferences s = getSharedPreferences("fastD", MODE_APPEND);
								Editor edit = s.edit();
								user_login_but.setProgress(50);
								edit.putString("userId", u.getUser_id());
								edit.putString("userName", u.getName());
								edit.putString("userPhone", u.getPhone());
								edit.putString("userPassword", u.getPassword());
								edit.putString("userLogin", "true");
								edit.commit();  
								
								user_login_but.setProgress(60);
								
								Gson g = new Gson();
								String str = null;
								Map<String , String> map = new HashMap<String , String>();
								map.put("method", g.toJson(Constants.getFormByUserId_all));
								map.put("param", g.toJson(u.getUser_id()));
								user_login_but.setProgress(65);
								try {
									str = HttpUtils.postRequest(HttpUtils.url+"formServlet", map);
									user_login_but.setProgress(80);
								} catch (InterruptedException e) {
								} catch (ExecutionException e) {
								}
								if(Constants.list_form_m3 == null) Constants.list_form_m3 = new ArrayList<main>();
								if(Constants.list_form_m2 == null) Constants.list_form_m2 = new ArrayList<user_Ad>();
								if(!TextUtils.isEmpty(str)){
									Constants.list_form_m1 =g.fromJson(str, new TypeToken<List<main>>(){}.getType());
								}
								startActivity(new Intent(LoginActivity.this,MainActivity.class));
								user_login_but.setProgress(99);
								LoginActivity.this.finish();
							}
							
						}else{
							show("账号不存在");
						}
						
					}else{
						show("登录错误");
					}
				}
			});
		}
	}
	private void fast_login() {
		final String userName = fastname.getText().toString().trim();
		final String password = fast_password.getText().toString().trim();
		
		//==============================================本地账号
				if(userName.equals("0") && password.equals("0")){
					startActivity(new Intent(LoginActivity.this,FastActivity.class));
					LoginActivity.this.finish();
					return;
				}
		//==============================================本地账号		
		
		if(login_pro(userName,password)){
			//======================================================================================fast登录

			RequestParams map = new RequestParams();
			map.put("method", Constants.get_fast);
			map.put("param", userName);
			fast_login_but.setProgress(10);
			AsyncHttpClient client = new AsyncHttpClient();
			client.post(HttpUtils.url+"fastServlet", map, new AsyncHttpResponseHandler(){
				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					if(!NetUtils.isNetworkAvailable(LoginActivity.this)){
						fast_show("网络错误");
					}else{
						fast_show("连接不到服务器");
					}
				}
				@Override
				public void onStart() {
					// TODO Auto-generated method stub
					fast_login_but.setProgress(20);
					super.onStart();
					fast_login_but.setMode(Mode.PROGRESS);
					fast_login_but.setProgress(20);
					fast_login_but.setClickable(false);
					fast_login_but.setProgress(25);
					
				}
				@Override
				public void onSuccess(int statusCode, Header[] arg1, byte[] responseBody) {
					fast_login_but.setProgress(40);
					if (statusCode == 200) {
						Gson j = new Gson();
						fast u = j.fromJson(new String(responseBody), fast.class);
						fast_login_but.setProgress(45);
						if(u != null){
							if(!u.getPassword().equals(password) ){
								fast_show("账号或密码错误");
							}else{
								///密码正确，开始登陆钱的准备
								SharedPreferences s = getSharedPreferences("fastD", MODE_APPEND);
								Editor edit = s.edit();
								fast_login_but.setProgress(50);
								edit.putString("fastId", u.getFast_id());
								edit.putString("fastName", u.getName());
								edit.putString("fastPhone", u.getPhone());
								edit.putString("fastPassword", u.getPassword());
								edit.putString("fastLogin", "true");
								edit.commit();  
								
								fast_login_but.setProgress(60);
								
								Gson g = new Gson();
								String str = null;
								
								Map<String , String> map = new HashMap<String , String>();
								map.put("method", g.toJson(Constants.getFormByFastId));
								map.put("fastId", g.toJson(u.getFast_id()));
								map.put("state", g.toJson(Constants.formState_doing_unfinish));
								fast_login_but.setProgress(65);
								try {
									str = HttpUtils.postRequest(HttpUtils.url+"formServlet", map);
									fast_login_but.setProgress(80);
								} catch (InterruptedException e) {
								} catch (ExecutionException e) {
								}
								if(Constants.fast_list_m2 == null) Constants.fast_list_m2 = new ArrayList<main>();
								if(Constants.fast_list_m3 == null) Constants.fast_list_m3 = new ArrayList<main>();
								if(!TextUtils.isEmpty(str)){
									Constants.fast_list_m1 =g.fromJson(str, new TypeToken<List<main>>(){}.getType());
								}
								
								startActivity(new Intent(LoginActivity.this,FastActivity.class));
								fast_login_but.setProgress(99);
								LoginActivity.this.finish();
							}
							
						}else{
							fast_show("账号不存在");
						}
						
					}else{
						fast_show("登录错误");
					}
				}
			});
			//==================================================================================fast登陆
		}
		
	}
	
	private void show(String str){
		new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
		.setTitleText(str)
		.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
								
								@Override
								public void onClick(SweetAlertDialog sweetAlertDialog) {
									// TODO Auto-generated method stub
									user_login_but.setClickable(true);
									user_login_but.setProgress(0);
									sweetAlertDialog.cancel();
								}
							})
		.show();
	}
	
	private void fast_show(String str){
		new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE)
		.setTitleText(str)
		.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
								
								@Override
								public void onClick(SweetAlertDialog sweetAlertDialog) {
									// TODO Auto-generated method stub
									fast_login_but.setClickable(true);
									fast_login_but.setProgress(0);
									sweetAlertDialog.cancel();
								}
							})
		.show();
	}
	
	
	
	

}
