package cn.zh.fastD;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zh.Utils.ActionProcessButton;
import cn.zh.Utils.ActionProcessButton.Mode;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.adapter.viewPagerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener,
		OnPageChangeListener {

	ViewPager viewPager;
	viewPagerAdapter adap;
	List<View> list;
	ViewPagerScroller vps;

	Button user_login_but;
	Button fast_login_but;
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
	
//	TextView tv;	//用于错误信息显示的
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		
		
		// ActionProcessButton but = (ActionProcessButton)
		// findViewById(R.id.login_but);
		// but.setProgress(50);
		// but.setMode(Mode.PROGRESS);

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
		user_login_but = (Button) view1.findViewById(R.id.user_login_but);
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
		fast_login_but = (Button) view2.findViewById(R.id.fast_login_but);
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
			viewPager.setCurrentItem(0);
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
		

	}
	
	Handler hand = new Handler();
	
	//显示登陆错误提示
//	private void showToast(String str){
//		tv.setText(str);
//		hand.postDelayed(null, 0);
//		hand.postDelayed(new Runnable() {
//			
//			@Override
//			public void run() {
//				if(LoginActivity.this.tv!=null){
//					LoginActivity.this.tv.setText("");
//				}
//				
//			}
//		}, 2000);
//	}
	
	
	//登陆按钮
	private Boolean login_pro(String userName,String password){
		
		if(!TextUtils.isEmpty(userName)){
			if(userName.length()!=11){
//				showToast( "手机号不正确");
				new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
				.setTitleText("提示")
				.setContentText("手机号长度不正确")
				.show();
				
				
				return false;
			}else{
				Pattern compile = Pattern.compile("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$");
				Matcher matcher = compile.matcher(userName);
				if(!matcher.find()){
//					showToast("手机号不正确");
					
					new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
					.setTitleText("提示")
					.setContentText("手机号格式不正确")
					.show();
					
					return false;
				}
			}
		}else{
//				showToast( "账号不能为空");
			new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
			.setTitleText("账号不能为空")
			.setContentText("")
			.show();
			
			return false;
		}
		if(!TextUtils.isEmpty(password)){
			if(password.length()<6||password.length()>20){
//				showToast("密码不正确");
				
				new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
				.setTitleText("提示")
				.setContentText("密码长度不正确")
				.show();
				
				return false;
			}
		}else{
//				showToast( "密码不能为为空");
			
			new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
			.setTitleText("密码不能为空")
			.setContentText("")
			.show();
			
			return false;
		}
		
		return true;
	}
	
	

	private void user_login() {
		String userName = username.getText().toString().trim();
		String password = user_passwrod.getText().toString().trim();
//		tv=user_toast;
		if(login_pro(userName,password)){
			
		}
		
	}
	private void fast_login() {
		String userName = fastname.getText().toString().trim();
		String password = fast_password.getText().toString().trim();
//		tv = fast_toast;
		if(login_pro(userName,password)){
			
		}
		
	}
	

}
