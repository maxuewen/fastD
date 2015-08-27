package cn.zh.fastD;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zh.Utils.ActionBarUtils;
import cn.zh.Utils.NoTouchViewPager;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.adapter.viewPagerAdapter;

import android.R.integer;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class User_register_Activity extends Activity implements OnPageChangeListener,OnClickListener{
	
	int mod;		//		2代表密码找回，1代表注册
	int isUser;		//		1代表快递员，0代表用户
	
	NoTouchViewPager vp;
	ViewPagerScroller vps;
	
	TextView tv;		//错误提示
	private EditText vp1_phone;
	private EditText varify;
	private EditText password1;
	private EditText password2;
	
	private Button next_but;
	private Button varify_but;
	private Button rigister_finish;
	private View actionBarView;
	private ImageButton back_but;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register_main);
		
		mod = getIntent().getIntExtra("mod",1);
		isUser = getIntent().getIntExtra("isUser",0);
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		
		vp = (NoTouchViewPager) findViewById(R.id.user_register);
		LayoutInflater inflater = LayoutInflater.from(this);
		List<View> list = new ArrayList<View>();
		View v1 = inflater.inflate(R.layout.user_register_viewpage_1, null);
		View v2 = inflater.inflate(R.layout.user_register_viewpage_2, null);
		
		list.add(v1);
		list.add(v2);
		
		vp.setAdapter(new viewPagerAdapter(this, list));
		vp.setOnPageChangeListener(this);
		
		vps = new ViewPagerScroller(this);
		vps.setScrollDuration(700);
		vps.initViewPagerScroll(vp);
		
		actionBarView = ActionBarUtils.setAtionBar(this, R.layout.user_register_actionbar);
		
		//获取界面上的元素
		vp1_phone = (EditText)v1.findViewById(R.id.urv1_phone);
		varify = (EditText)v1.findViewById(R.id.urv1_varify);
		tv = (TextView)v1.findViewById(R.id.urv1_toast);
		next_but = (Button)v1.findViewById(R.id.urv1_next_but);
		varify_but= (Button)v1.findViewById(R.id.urv1_getvarify_but);
		back_but = (ImageButton)actionBarView.findViewById(R.id.back);
//用于密码找会页面		
		if(mod==2 && isUser==0){
			TextView title = (TextView) actionBarView.findViewById(R.id.user_register_titleBar);
			title.setText("用户密码找回");
		}else if(isUser == 1 && mod == 2){
			TextView title = (TextView) actionBarView.findViewById(R.id.user_register_titleBar);
			title.setText("快递员密码找回");
		}else{
			
		}
		
		rigister_finish = (Button) v2.findViewById(R.id.urv2_but);
		password1 = (EditText)v2.findViewById(R.id.urv2_pw1);
		password2 = (EditText)v2.findViewById(R.id.urv2_pw2);
		
		next_but.setOnClickListener(this);
		varify_but.setOnClickListener(this);
		back_but.setOnClickListener(this);
		rigister_finish.setOnClickListener(this);
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	//显示登陆错误提示
//		private void showToast(TextView tv , String str){
//			tv.setText(str);
//			new Handler().postDelayed(new Runnable() {
//				
//				@Override
//				public void run() {
//					if(User_register_Activity.this.tv!=null){
//						User_register_Activity.this.tv.setText("");
//					}
//					
//				}
//			}, 2000);
//		}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.urv1_getvarify_but:
			
			break;
		case  R.id.urv1_next_but:
			nextPagerPro();
			break;
		case  R.id.back:
			startActivity(new Intent(User_register_Activity.this,LoginActivity.class));
			finish();
			break;
		case R.id.urv2_but:
			register();
			break;
		}
		
	}
	
	private void register() {
		String pw1 = password1.getText().toString().trim();
		String pw2 = password2.getText().toString().trim();
		
		if(TextUtils.isEmpty(pw1)){
			new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
			.setTitleText("")
			.setContentText("密码不能为空")
			.show();
			return;
		}else{
			if(pw1.length()>=6){
				//pw1没问题，判断1和2是否相等
				if(pw1.equals(pw2)){
//==============================================================================================
					//数据检验成功，从这里开始注册
					String str;
					
					if(mod==2 && isUser==0){
						str = "密码找回成功";
					}else{
						if(isUser == 0){
							str = "注册成功";
						}else{
							str = "密码找回成功";
						}
					}
					
					new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
					.setTitleText(str)
					.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
						
						@Override
						public void onClick(SweetAlertDialog sweetAlertDialog) {
							// TODO Auto-generated method stub
							startActivity(new Intent(User_register_Activity.this,LoginActivity.class));
							finish();
						}
					})
					.show();
					
					
					
					
				}
				else{
					new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
					.setTitleText("提示")
					.setContentText("两次密码不一致")
					.show();
					return;
				}
				
			}else{
				new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
				.setTitleText("提示")
				.setContentText("密码的最小长度为6")
				.show();
				return;
			}
			
		}
		
	}

	private void nextPagerPro(){
		String str_varify = varify.getText().toString().trim();
		String str_phone = vp1_phone.getText().toString().trim();
		
		if(!TextUtils.isEmpty(str_phone)){
			//手机号不为空，判断格式是否正确
			Pattern compile = Pattern.compile("^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$");
			Matcher matcher = compile.matcher(str_phone);
			if(!matcher.find()){
				new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
				.setTitleText("提示")
				.setContentText("手机号格式不正确")
				.show();
				return;
			}else{
				//手机号格式正确，判断验证码是否为空，是否位数字
				if(!TextUtils.isEmpty(str_varify)){
					if(str_varify.matches("[0-9]+")){
						
//======================================================================================================
						//判断验证码是否正确
						
						
						
//========================================================================================================	
					//验证通过，设置viewage到第二界面
						vp.setCurrentItem(1);
						
					}else
					{
						//验证吗的格式不正确
						new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
						.setTitleText("提示")
						.setContentText("验证码格式不正确")
						.show();
						return;
					}
				}else{
					//判断验证码是否为空
					new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
					.setTitleText("提示")
					.setContentText("验证码不能为空")
					.show();
					return;
				}
				
				
			}
			
			
		}else{
			//手机号为空
			new SweetAlertDialog(this,SweetAlertDialog.ERROR_TYPE)
			.setTitleText("手机号不能为空")
			.setContentText("")
			.show();
			return;
		}
	}
	
	



}
