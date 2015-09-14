package cn.zh.adapter;

import java.util.ArrayList;
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

	
	public void clean(){
		this.list.clear();
	}
	
	public user_m3_ListViewAdp(Context context,List<main> list) {
		super();
		this.list = list;
//		this.list = Constants.list_form_m3;
		
		inflater = LayoutInflater.from(context);
		
		System.out.println(this.list.size()+"m3");
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(this.list != null){
			return list.size();
		}else{
			return 0;
		}
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
		if (list != null && list.size()>0) {
			tv.setText(list.get(position).getUser_name().toString());
			tv1.setText(list.get(position).getUser_phone().toString());
			tv2.setText(list.get(position).getTime().toString());
			
			
		}
		
		return convertView;
	}

}
