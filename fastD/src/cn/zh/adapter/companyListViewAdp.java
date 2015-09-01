package cn.zh.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.amap.api.services.poisearch.Cinema;

import cn.zh.Utils.Constants;
import cn.zh.domain.poiPoint;
import cn.zh.fastD.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class companyListViewAdp extends BaseAdapter {
	
	private List<String> list;
	LayoutInflater inflater;
	
	public companyListViewAdp(Context context) {
		super();
		list = new ArrayList<String>();
		for (int i = 0; i < Constants.companyList.length; i++) {
			list.add(Constants.companyList[i]);
		}
		
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
			convertView = inflater.inflate(R.layout.companylist_itme, null);
		}
		
		TextView tv = (TextView)convertView.findViewById(R.id.companylist_itme_companyName);
		if (list != null || list.size()>0) {
			tv.setText(list.get(position));
		}
		
		return convertView;
	}

}
