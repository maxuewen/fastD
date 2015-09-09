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

public class formListViewAdp_m1 extends BaseAdapter {
	
	private List<main> list;
	LayoutInflater inflater;
	
	public void setList(List<main> list) {

		this.list = list;
	}

	public formListViewAdp_m1(Context context) {
		super();
		main m = new main();
		m.setCompany("中通");
		m.setFormNum("1234567895");
		m.setState("未完成");
		Constants.list_form_m1.add(m);
		
		main m1 = new main();
		m1.setCompany("中阿通");
		m1.setFormNum("1234567895");
		m1.setState("历史");
		Constants.list_form_m1.add(m1);
		
		main m2 = new main();
		m2.setCompany("中通");
		m2.setFormNum("1234567895");
		m2.setState("未接收");//
		Constants.list_form_m1.add(m2);
		
		this.list = Constants.list_form_m1;
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
		if (list != null || list.size()>0) {
			tv.setText(list.get(position).getCompany().toString());
			tv1.setText(list.get(position).getFormNum().toString());
			tv2.setText(list.get(position).getState().toString());
		}
		
		return convertView;
	}

}
