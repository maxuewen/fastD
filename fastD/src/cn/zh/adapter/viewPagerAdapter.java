package cn.zh.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class viewPagerAdapter extends PagerAdapter {
	
	Context context;
	List<View> list;
	
	
	
	public viewPagerAdapter(Context con,List<View> list){
		this.context=con;
		this.list=list;
	}
	
	
	
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager)container).removeView(list.get(position));
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		((ViewPager)container).addView(list.get(position));
		return list.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return (arg0==arg1);
	}

}
