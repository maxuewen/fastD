package cn.zh.adapter;

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

public class receiptAdpter extends BaseAdapter {

	
	private List<user_Ad> list;
	LayoutInflater inflater;
	
	
	

	public receiptAdpter(Context context) {
		super();
		user_Ad m = new user_Ad();
		m.setName("站三");
		m.setPhone("15822858570");
		m.setReceiptAd_1("天津 西青 盐和刘庆");
		m.setReceiptAd_2("柳口路杨柳青正，中环信息学院，柳口路99好");
		Constants.list_form_m2.add(m);
		
		user_Ad m1 = new user_Ad();
		m1.setName("站三");
		m1.setPhone("15822858570");
		m1.setReceiptAd_1("天津 西青 盐和刘庆");
		m1.setReceiptAd_2("柳口路杨柳青正，中环信息学院，柳口路99好");
		Constants.list_form_m2.add(m1);
		
		user_Ad m2 = new user_Ad();
		m2.setName("站三");
		m2.setPhone("15822858570");
		m2.setReceiptAd_1("天津 西青 盐和刘庆");
		m2.setReceiptAd_2("柳口路杨柳青正，中环信息学院，柳口路99好");
		Constants.list_form_m2.add(m2);
		
		
		this.list = Constants.list_form_m2;
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
		if (list != null || list.size()>0) {
			tv.setText(list.get(position).getName());
			tv2.setText(list.get(position).getReceiptAd_2().toString());
		}
		
		return convertView;
	}

	
	
}
