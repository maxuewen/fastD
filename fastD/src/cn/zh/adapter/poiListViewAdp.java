package cn.zh.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.amap.api.services.poisearch.Cinema;

import cn.zh.domain.poiPoint;
import cn.zh.fastD.R;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class poiListViewAdp extends BaseAdapter {
	
	private Context context;
	private List<poiPoint> list;
	LayoutInflater inflater;
	
	public void setList(List<poiPoint> list) {
		
//		for (int i = 0; i < list.size(); i++) {
//			this.list.add(list.get(i));
//		}
//		for (int i = 0; i < this.list.size(); i++) {
//			for (int j = 0; j < list.size(); j++,i++) {
//				this.list.set(i, list.get(i));
//			}
//			this.list.set(i, null);
//		}
		this.list = list;
	}

	public poiListViewAdp(Context context, List<poiPoint> list) {
		super();
		this.context = context;
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
			convertView = inflater.inflate(R.layout.poilist_itme, null);
		}
		
		TextView tv = (TextView)convertView.findViewById(R.id.poiList_itme);
		if (list != null || list.size()>0) {
			tv.setText(list.get(position).toString());
		}
		
		return convertView;
	}

}
