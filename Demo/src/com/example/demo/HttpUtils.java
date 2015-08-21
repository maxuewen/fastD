package com.example.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpUtils {

	public static HttpClient httpCilent = new DefaultHttpClient();
	public static final String url = "http://172.16.1.11:80/AndroidServer/login";
	
	
	
	/**
	 * 发送 请求
	 */
	public static String getRequest(final String url)throws Exception{
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					public String call() throws ClientProtocolException, IOException  {
						//创建httpget对象
						HttpResponse httpResponse = httpCilent.execute(new HttpGet(url));
						if(httpResponse.getStatusLine().getStatusCode()==200){
							//获取服务器的相应字符串
							return EntityUtils.toString(httpResponse.getEntity());
						}
						return null;
					}
				}
				);
		new Thread(task).start();
		return task.get();
	}
	
	/**
	 *  发送请求，post 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static String postRequest(final String url,final Map<String, String> map) throws InterruptedException, ExecutionException{

		FutureTask<String> ft = new FutureTask<String>(
				new Callable<String>() {
					public String call() throws Exception {
						HttpPost httpPost = new HttpPost(url);
						List<NameValuePair> list = new ArrayList<NameValuePair>();
						for(String str:map.keySet()){
							list.add(new BasicNameValuePair(str, map.get(str)));
						}
						httpPost.setEntity(new UrlEncodedFormEntity(list,"gbk"));
						HttpResponse httpResponse = httpCilent.execute(httpPost);
						if(httpResponse.getStatusLine().getStatusCode()==200){
							return EntityUtils.toString(httpResponse.getEntity());
						}
						return null;
					};
				}
				);
		new Thread(ft).start();
		return ft.get();
	}
	
	
}
