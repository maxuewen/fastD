package cn.zh.Utils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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

	static{
		Properties prop = new Properties();
		try {
		prop.load(HttpUtils.class.getClassLoader().getResourceAsStream("ip.properties"));
			url=prop.get("ip").toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static HttpClient httpCilent = new DefaultHttpClient();
	public static String url;
	
	
	
	/**
	 * ���� ����
	 */
	public static String getRequest(final String url)throws Exception{
		FutureTask<String> task = new FutureTask<String>(
				new Callable<String>() {
					public String call() throws ClientProtocolException, IOException  {
						//����httpget����
						HttpResponse httpResponse = httpCilent.execute(new HttpGet(url));
						if(httpResponse.getStatusLine().getStatusCode()==200){
							//��ȡ����������Ӧ�ַ�
							return EntityUtils.toString(httpResponse.getEntity());
						}
						return "����������";
					}
				}
				);
		new Thread(task).start();
		return task.get();
	}
	
	/**
	 *  ��������post 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static String postRequest(final String url,final Map<String, String> map) throws InterruptedException, ExecutionException{
		System.out.println("asdf==============111111111111111111=");
		FutureTask<String> ft = new FutureTask<String>(
				new Callable<String>() {
					public String call() throws Exception {System.out.println("asdf============2222222222222222222222==");
						HttpPost httpPost = new HttpPost(url);
						List<NameValuePair> list = new ArrayList<NameValuePair>();
						if(map!=null){
							for(String str:map.keySet()){
								list.add(new BasicNameValuePair(str, map.get(str)));
							}
						}
						System.out.println("asdf=========3333333333333333333333======");
						httpPost.setEntity(new UrlEncodedFormEntity(list,"utf-8"));
						System.out.println("34343434343434343");
						//System.out.println(httpPost);
						HttpResponse httpResponse = httpCilent.execute(httpPost);System.out.println("asdf====444444444444444444==========");
						if(httpResponse.getStatusLine().getStatusCode()==200){
							return EntityUtils.toString(httpResponse.getEntity());
						}
						return "����������";
					};
				}
				);
		System.out.println("asdf======55555555555555555555555=========");
		new Thread(ft).start();
		return ft.get();
	}
	
	
}
