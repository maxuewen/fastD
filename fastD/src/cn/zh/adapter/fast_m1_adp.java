package cn.zh.adapter;

import java.util.List;

import cn.zh.domain.main;
import cn.zh.fastD.R;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class fast_m1_adp extends BaseAdapter {
	
	

	private List<main> list;
	LayoutInflater inflater;
	

	public fast_m1_adp(Context context,List<main> list) {
		super();
		this.list = list;
		
//		main m1 = new main("mxw", "20150915", "进行中");
//		this.list.add(m1);
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
			convertView = inflater.inflate(R.layout.listview_user_m1, null);
		}
		
		TextView tv = (TextView)convertView.findViewById(R.id.tv_userlv_m1_company);
		TextView tv1 = (TextView)convertView.findViewById(R.id.tv_userlv_m1_formId);
		TextView tv2 = (TextView)convertView.findViewById(R.id.tv_userlv_m1_state);
		if (list != null && list.size()>0) {
			tv.setText(list.get(position).getUser_name());
			tv1.setText(list.get(position).getTime());
			tv2.setText(list.get(position).getState());
		}
		return convertView;
	}

}
