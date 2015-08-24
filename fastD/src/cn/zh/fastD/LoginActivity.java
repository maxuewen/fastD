package cn.zh.fastD;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import cn.zh.Utils.ActionProcessButton;
import cn.zh.Utils.ActionProcessButton.Mode;
import cn.zh.adapter.viewPagerAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

public class LoginActivity extends Activity {
	
	ViewPager viewPager;
	viewPagerAdapter adap;
	List<View> list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
//		ActionProcessButton but = (ActionProcessButton) findViewById(R.id.login_but);
//		but.setProgress(50);
//		but.setMode(Mode.PROGRESS);
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		LayoutInflater flat = LayoutInflater.from(this);
		list = new ArrayList<View>();
		list.add(flat.inflate(R.layout.user_page, null));
		list.add(flat.inflate(R.layout.fastd_page, null));
		
		adap = new viewPagerAdapter(this, list);
		
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(adap);
	}

}
