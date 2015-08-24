package com.example.androidtest;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class HeartbeatService extends Service implements Runnable {

	private Thread mThread;
	public int count = 0;
	private boolean isTip = true;
	private static String mRestMsg;
	int x = 1;

	@Override
	public void run() {
		while (true) {
			try {
				System.out.println(count);

				if (count > 1) {
					// count = 1;
					if (isTip) {
						// 判断应用是否在运行
						ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
						List<RunningTaskInfo> list = am.getRunningTasks(3);
						for (RunningTaskInfo info : list) {
							if (info.topActivity.getPackageName().equals(
									"com.example.androidtest")) {
								// 通知应用，显示提示“连接不到服务器”
								// Intent intent = new Intent("org.yhn.demo");
								// intent.putExtra("msg", true);
								// sendBroadcast(intent);
								//System.out.println("连接不到服务器");
								break;
							}
						}

						isTip = false;
					}
				}
				if (mRestMsg != "" && mRestMsg != null) {
					// 向服务器发送心跳包
					sendHeartbeatPackage(mRestMsg);

					count += 1;

				}

				Thread.sleep(1000 * x);
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("线程出错");
				 continue;
			}
		}
	}

	private void sendHeartbeatPackage(String msg) {
		HttpGet httpGet = new HttpGet(msg);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// 发送请求
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);

			if (httpResponse == null) {
				System.out.println("服务器没有响应");

				return;
			}

			// 处理返回结果
			final int responseCode = httpResponse.getStatusLine()
					.getStatusCode();
			if (responseCode == HttpStatus.SC_OK) {
				// 只要服务器有回应就OK
				System.out.println("只要服务器有回应就OK   ");
				count = 0;
				isTip = true;
			} else {
				System.out.println("responseCode " + responseCode);
			}

		} catch (Exception e) {
			System.out.println("服务器服务器已关闭001===========" + e.getMessage());
			return;
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		mThread = new Thread(this);
		mThread.start();
		count = 0;
		mRestMsg = HttpUtils.url + "main";

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
