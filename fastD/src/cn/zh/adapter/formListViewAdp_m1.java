package cn.zh.adapter;

import java.util.List;


import cn.zh.Utils.Constants;
import cn.zh.domain.main;
import cn.zh.fastD.R;
import android.content.Context;
import android.text.TextUtils;
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
	public void clean(){
		this.list.clear();
	}

	public formListViewAdp_m1(Context context,List<main> list) {
		super();
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(Constants.list_form_m1 != null){
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
			convertView = inflater.inflate(R.layout.listview_user_m1, null);
		}
		
		TextView tv = (TextView)convertView.findViewById(R.id.tv_userlv_m1_company);
		TextView tv1 = (TextView)convertView.findViewById(R.id.tv_userlv_m1_formId);
		TextView tv2 = (TextView)convertView.findViewById(R.id.tv_userlv_m1_state);
		if (list != null && list.size()>0) {
			if(list.get(position).getCompany() != null && !TextUtils.isEmpty(list.get(position).getCompany().toString())){
				tv.setText(list.get(position).getCompany().toString());
			}else{
				tv.setText("等待接收");
			}
			if(list.get(position).getFormNum() != null &&!TextUtils.isEmpty(list.get(position).getFormNum())){
				tv1.setText(list.get(position).getFormNum().toString());
			}else{
				tv1.setText(list.get(position).getTime());
			}
			tv2.setText(list.get(position).getState().toString());
		}
		return convertView;
	}

}
