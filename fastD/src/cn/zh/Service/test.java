package cn.zh.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class test extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("onCreate");
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("onDestroy");
	}

}
