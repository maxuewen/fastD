package cn.zh.fastD;

import java.util.ArrayList;
import java.util.List;

import cn.zh.Utils.ActionBarUtils;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.adapter.viewPagerAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

public class User_register_Activity extends Activity implements OnPageChangeListener{
	
	ViewPager vp;
	ViewPagerScroller vps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register_main);
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		
		vp = (ViewPager) findViewById(R.id.user_register);
		LayoutInflater inflater = LayoutInflater.from(this);
		List<View> list = new ArrayList<View>();
		list.add(inflater.inflate(R.layout.user_register_viewpage_1, null));
		list.add(inflater.inflate(R.layout.user_register_viewpage_2, null));
		
		vp.setAdapter(new viewPagerAdapter(this, list));
		vp.setOnPageChangeListener(this);
		
		vps = new ViewPagerScroller(this);
		vps.setScrollDuration(700);
		vps.initViewPagerScroll(vp);
		
		ActionBarUtils.setAtionBar(this, R.layout.user_register_actionbar);
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


}
