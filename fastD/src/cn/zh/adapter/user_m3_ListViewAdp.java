package cn.zh.adapter;

import java.util.List;


import cn.zh.Utils.Constants;
import cn.zh.domain.main;
import cn.zh.fastD.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class user_m3_ListViewAdp extends BaseAdapter {
	
	private List<main> list;
	LayoutInflater inflater;
	
	public void setList(List<main> list) {

		this.list = list;
	}

	public user_m3_ListViewAdp(Context context) {
		super();
		main m = new main();
		m.setUser_name("张三");
		m.setUser_phone("15822858570");
		m.setTime(String.valueOf(System.currentTimeMillis()));
		Constants.list_form_m3.add(m);
		
		main m1 = new main();
		m1.setUser_name("张三");
		m1.setUser_phone("15822858570");
		m1.setTime(String.valueOf(System.currentTimeMillis()));
		Constants.list_form_m3.add(m1);
		
		main m2 = new main();
		m2.setUser_name("张三");
		m2.setUser_phone("15822858570");
		m2.setTime(String.valueOf(System.currentTimeMillis()));
		Constants.list_form_m3.add(m2);
		
		this.list = Constants.list_form_m3;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if(convertView == null){
			convertView = inflater.inflate(R.layout.lv_m3, null);
		}
		
		TextView tv = (TextView)convertView.findViewById(R.id.tv_m3_name);
		TextView tv1 = (TextView)convertView.findViewById(R.id.tv_m3_phone);
		TextView tv2 = (TextView)convertView.findViewById(R.id.tv_m3_time);
		if (list != null || list.size()>0) {
			tv.setText(list.get(position).getUser_name().toString());
			tv1.setText(list.get(position).getUser_phone().toString());
			tv2.setText(list.get(position).getTime().toString());
		}
		
		return convertView;
	}

}
