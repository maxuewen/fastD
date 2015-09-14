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
import cn.zh.domain.user_Ad;
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
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.Toast;

public class userService extends Service implements Runnable{

	private Thread mThread;
	int x = 5;
	Gson g = new Gson();

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
				System.out.println("线程出错"+e.getMessage());
				 continue;
			}
		}
	}

	private void sendHeartbeatPackage() {
		try {
			
			Map<String , String> map = new HashMap<String , String>();
			SharedPreferences sh = getSharedPreferences("fastD", MODE_APPEND);
			String userId = sh.getString("userId", null);
			String userphone = sh.getString("userPhone", null);
			
			//添加到第一张页面
			map.put("method", g.toJson(Constants.getFormByUserId_all));
			map.put("param", g.toJson(userId));
			List<main> list_1 = g.fromJson(HttpUtils.postRequest(HttpUtils.url+"formServlet", map),new TypeToken<List<main>>(){}.getType());
			if(list_compan( Constants.list_form_m1,list_1)){
				Constants.list_form_m1 = list_1;
				showNotification();
				
				System.out.println(Constants.list_form_m1+"里面的");
			}
			System.out.println(Constants.list_form_m1+"未免的的");
			
			map.clear();
			map.put("method", g.toJson(Constants.get_user_ad));
			map.put("param", g.toJson(userId));
			
			List<user_Ad> list_2 = g.fromJson(HttpUtils.postRequest(HttpUtils.url+"userServlet", map),new TypeToken<List<user_Ad>>(){}.getType());
			if(Constants.list_form_m2 != null && list_2 != null && Constants.list_form_m2.size() != list_2.size()){
				Constants.list_form_m2 = list_2;
				showNotification();
			}else if(Constants.list_form_m2 == null && list_2 != null){
				Constants.list_form_m2 = list_2;
				showNotification();
			}
			
			map.clear();
			map.put("method", g.toJson(Constants.getMyReceiptForm));
			map.put("param", g.toJson(userphone));
			List<main> list_3 = g.fromJson(HttpUtils.postRequest(HttpUtils.url+"formServlet", map),new TypeToken<List<main>>(){}.getType());
			if(list_compan(Constants.list_form_m3 , list_3 )){
				Constants.list_form_m3 = list_3;
				showNotification();
			}
			
			map.clear();
			map.put("method", g.toJson(Constants.alert_User_ad));
			map.put("userId", g.toJson(userId));
			map.put("Lat",g.toJson(String.valueOf(Constants.user_lat)));
			map.put("Lng",g.toJson(String.valueOf(Constants.user_lng)));
			System.out.println("7");
			String s= g.fromJson(HttpUtils.postRequest(HttpUtils.url+"userServlet", map)
					, String.class);
			
			System.out.println("8");
		} catch (Exception e) {
			Toast.makeText(this, "服务器服务器已关闭", 1).show();
			return;
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		
		mThread = new Thread(this);
		mThread.start();
		SharedPreferences sh = getSharedPreferences("fastD", MODE_PRIVATE);
		Editor edit = sh.edit();
		
		edit.putString("service", "true");

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		
		SharedPreferences sh = getSharedPreferences("fastD", MODE_PRIVATE);
		Editor edit = sh.edit();
		
		edit.putString("service", "false");
		
		
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private Boolean list_compan(List<main> l1,List<main> l2){			//true代表不想等
		if(l1 == null && l2 == null) return false;
		if(l1 == null && l2 != null) return true;
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
//		Builder builder = new NotificationCompat.Builder(this);
//		builder.setSmallIcon(R.drawable.ic_launcher);
//		builder.setContentTitle("fastD");
//		builder.setContentText("你有新的物流消息");
		
//		Notification notification = builder.build();
		NotificationManager Manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.ic_launcher, "新的物理消息", 
				System.currentTimeMillis());
		Intent	intent = new Intent(this,MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 100, intent, 0);
		
		notification.setLatestEventInfo(this, "fastD", "您有新的快递消息", contentIntent);
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		
		
		Manager.notify(100, notification);
		
		
	}
	
	

}
