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
						// �ж�Ӧ���Ƿ�������
						ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
						List<RunningTaskInfo> list = am.getRunningTasks(3);
						for (RunningTaskInfo info : list) {
							if (info.topActivity.getPackageName().equals(
									"com.example.androidtest")) {
								// ֪ͨӦ�ã���ʾ��ʾ�����Ӳ�����������
								// Intent intent = new Intent("org.yhn.demo");
								// intent.putExtra("msg", true);
								// sendBroadcast(intent);
								//System.out.println("���Ӳ���������");
								break;
							}
						}

						isTip = false;
					}
				}
				if (mRestMsg != "" && mRestMsg != null) {
					// �����������������
					sendHeartbeatPackage(mRestMsg);

					count += 1;

				}

				Thread.sleep(1000 * x);
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("�̳߳���");
				 continue;
			}
		}
	}

	private void sendHeartbeatPackage(String msg) {
		HttpGet httpGet = new HttpGet(msg);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		// ��������
		HttpResponse httpResponse = null;
		try {
			httpResponse = httpClient.execute(httpGet);

			if (httpResponse == null) {
				System.out.println("������û����Ӧ");

				return;
			}

			// �����ؽ��
			final int responseCode = httpResponse.getStatusLine()
					.getStatusCode();
			if (responseCode == HttpStatus.SC_OK) {
				// ֻҪ�������л�Ӧ��OK
				System.out.println("ֻҪ�������л�Ӧ��OK   ");
				count = 0;
				isTip = true;
			} else {
				System.out.println("responseCode " + responseCode);
			}

		} catch (Exception e) {
			System.out.println("�������������ѹر�001===========" + e.getMessage());
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
