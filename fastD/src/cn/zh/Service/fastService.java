package cn.zh.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.zh.Utils.Constants;
import cn.zh.Utils.HttpUtils;
import cn.zh.Utils.NetUtils;
import cn.zh.domain.main;
import cn.zh.fastD.FastActivity;
import cn.zh.fastD.MainActivity;
import cn.zh.fastD.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.widget.Toast;

public class fastService extends Service implements Runnable{

	private Thread mThread;
	int x = 5;
	Gson g = new Gson();
	SharedPreferences sh;

	@Override
	public void run() {
		while (true) {
			try {
				if(NetUtils.isNetworkAvailable(this)){
					sendHeartbeatPackage();
				}else{
					System.out.println("没网");
				}
				
				Thread.sleep(1000 * x);
			} catch (Exception e) {
				try {
					Thread.sleep(1000 * x);
				} catch (InterruptedException e1) {
					System.out.println("暂停出错");
					continue;
				}
				System.out.println("fast线程出错"+e.getMessage());
				 continue;
			}
		}
	}

	private void sendHeartbeatPackage() {
		try {
			
			Map<String , String> map = new HashMap<String , String>();
			
			String fastId = sh.getString("fastId", null);
			
			map.put("fastId", g.toJson(fastId));
			
			map.put("Lat",g.toJson(String.valueOf(Constants.fast_lat)));
			map.put("Lng",g.toJson(String.valueOf(Constants.fast_lng)));
			
			Map<String,String> m = g.fromJson(HttpUtils.postRequest(HttpUtils.url+"fastServiceSvl", map)
					,new TypeToken<HashMap<String,String>>(){}.getType());
			
			System.out.println(m);
			
			//添加到第一张页面
			List<main> list_1 = g.fromJson(m.get("m1"),new TypeToken<List<main>>(){}.getType());
			
			List<main> m1 = new Gson().fromJson(sh.getString("fast_m1", null), new TypeToken<List<main>>(){}.getType());
			if(list_compan( m1,list_1)){
				Constants.fast_list_m1 = list_1;
				sh.edit().putString("fast_m1",  new Gson().toJson(list_1)).commit();
				showNotification();
			}
			
			//添加到第er张页面=====================================================================
			List<main> list_2 = g.fromJson(m.get("m2"),new TypeToken<List<main>>(){}.getType());
			
			List<main> m2 = new Gson().fromJson(sh.getString("fast_m2", null), new TypeToken<List<main>>(){}.getType());
			if(list_compan(m2,list_2)){
				Constants.fast_list_m2 = list_2;
				sh.edit().putString("fast_m1",  new Gson().toJson(list_2)).commit();
				showNotification();
			}
			//添加到第三张页面=====================================================================
			List<main> list_3 = g.fromJson(m.get("m3"),new TypeToken<List<main>>(){}.getType());

			List<main> m3 = new Gson().fromJson(sh.getString("fast_m3", null), new TypeToken<List<main>>(){}.getType());
			if(list_compan(m3,list_3)){
				Constants.fast_list_m3 = list_3;
				sh.edit().putString("fast_m1",  new Gson().toJson(list_3)).commit();
				showNotification();
			}
			//=====================================================================
			
			System.out.println("8"+"fast");
			
		} catch (Exception e) {
			Toast.makeText(this, "服务器服务器已关闭", 1).show();
			System.out.println("服务器服务器已关闭");
			return;
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		mThread = new Thread(this);
		mThread.start();
		
		sh = getSharedPreferences("fastD", MODE_APPEND);
		sh.edit().putString("fastService", "true").commit();

		return super.onStartCommand(intent, flags, startId);
	}

	
	
	
	@Override
	public void onDestroy() {
		
		
		sh.edit().putString("fastService", "false").commit();
		System.out.println(sh.getString("fastService", "false"));
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Boolean list_compan(List<main> l1,List<main> l2){			//true代表不想等
		if(l1 == null && l2 == null) return false;
		if(l1 == null && l2 != null && l2.size() != 0) return true;
		if(l1 != null && l2 == null) return false;
			
		int a = l1.size(); int b = l2.size();
		if(a > b) return true;
		if(a < b) return true;
		int c = 0;
		for(int i = 0; i < a; i++){
			if(!l1.get(i).getState().equals(l2.get(i).getState())){
				c++;
			}
		}
		if(c==0)return false;
		return true;
	}
	
	private void showNotification(){
		NotificationManager Manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher, "新的物流消息", 
				System.currentTimeMillis());
		Intent	intent = new Intent(this,FastActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 100, intent, 0);
		
		notification.setLatestEventInfo(this, "fastD", "ffff您有新的快递消息", contentIntent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		Manager.notify(100, notification);
	}

}
