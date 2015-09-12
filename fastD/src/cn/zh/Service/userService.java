package cn.zh.Service;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import cn.zh.Utils.Constants;
import cn.zh.Utils.HttpUtils;
import cn.zh.Utils.ToastUtil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class userService extends Service implements Runnable{

	private Thread mThread;
	private static String mRestMsg;
	int x = 5;

	@Override
	public void run() {
		while (true) {
			try {
				if (mRestMsg != "" && mRestMsg != null) {
					// 向服务器发送心跳包
					sendHeartbeatPackage(mRestMsg);
				}
				Thread.sleep(1000 * x);
			} catch (Exception e) {
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
			
			System.out.println("1");
			
			Map<String , String> map = new HashMap<String , String>();
			map.put("method", Constants.get_user);
			map.put("param", "15822858570");
			String str = HttpUtils.postRequest(HttpUtils.url+"userServlet", map);
			
			System.out.println(str);
			
			map.put("method", Constants.getFormByUserId_all);
			map.put("param", "asdf");
			String a = HttpUtils.postRequest(HttpUtils.url+"formServlet", map);
			
			System.out.println(a);
			
		} catch (Exception e) {
			Toast.makeText(this, "服务器服务器已关闭", 1);
			return;
		}

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		System.out.println("kaishifuwu");
		
		mThread = new Thread(this);
		mThread.start();
		mRestMsg = HttpUtils.url + "formServlet";

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
