package cn.zh.adapter;

import java.util.ArrayList;
import java.util.List;

import cn.zh.Utils.Constants;
import cn.zh.domain.main;
import cn.zh.domain.user_Ad;
import cn.zh.fastD.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class fast_m2_Adpter extends BaseAdapter {

	
	private List<main> list;
	LayoutInflater inflater;

	public fast_m2_Adpter(Context context,List<main> list) {
		super();
		
		this.list = list;
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
			convertView = inflater.inflate(R.layout.lv_receiptad, null);
		}
		TextView tv = (TextView)convertView.findViewById(R.id.tv_receiptAd_name);
		TextView tv2 = (TextView)convertView.findViewById(R.id.tv_receiptAd_ad);
		if (list != null && list.size()>0) {
			tv.setText(list.get(position).getUser_name());
			tv2.setText(list.get(position).getShipAd().toString());
		}
		
		return convertView;
	}

	
	
}
