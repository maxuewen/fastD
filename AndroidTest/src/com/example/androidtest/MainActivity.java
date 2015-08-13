package com.example.androidtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;

import domain.User;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.app.Activity;

public class MainActivity extends Activity {

	ListView listview;
	List list;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		
		listview = (ListView) findViewById(R.id.listView);
		showAll();

	}

	private void showAll() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("param", "showAll");
		list = new ArrayList<User>();

		String result = null;

		try {
			result = HttpUtils.postRequest(HttpUtils.url + "main", map);

		} catch (Exception e) {
			Toast.makeText(this, "服务器异常，请重试！(101)", 1);
		}

		if (result != null) {
			try {
				JSONArray js = new JSONArray(result);
				User u = null;
				for (int i = 0; i < js.length(); i++) {
					Log.i("asdf", js.get(i).toString());
					try {
						u=(User)js.get(i);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						System.out.println("转换错误");
					}
					Log.i("asdf", u.toString());
					list.add(u);
					Log.i("asdf", list.get(i).toString());
				}
			} catch (JSONException e) {
				Toast.makeText(this, "服务器异常，请重试！(102)", 1);
			}
		}

		listview.setAdapter(new adapter());

	}

	class adapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View v, ViewGroup arg2) {
			View view;

			if (v == null) {
				LayoutInflater li = MainActivity.this.getLayoutInflater();
				view = li.inflate(R.layout.itme_main, null);
			} else {
				view = v;
			}

			TextView t1 = (TextView) view.findViewById(R.id.username);
			TextView t2 = (TextView) view.findViewById(R.id.password);

			User user = (User) list.get(arg0);

			t1.setText(user.getUsername());
			t2.setText(user.getPassword());

			return view;
		}

	}

}
