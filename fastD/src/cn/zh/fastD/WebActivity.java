package cn.zh.fastD;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import cn.zh.Utils.ActionBarUtils;
import cn.zh.Utils.NoTouchViewPager;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.adapter.viewPagerAdapter;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WebActivity extends Activity implements OnClickListener{
	
	
	private EditText et_form;
	private Button but_next;
	private NoTouchViewPager vp_main;
	private WebView webView;
	

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.form_find_main);
		
		vp_main = (NoTouchViewPager)findViewById(R.id.webView_main);
		
		View view = ActionBarUtils.setAtionBar(this, R.layout.user_register_actionbar);
		((TextView)view.findViewById(R.id.user_register_titleBar)).setText("物流查询");
		view.findViewById(R.id.back).setOnClickListener(this);
			
		
		LayoutInflater inflater = LayoutInflater.from(this);
		
		List<View> list = new ArrayList<View>();
		
		View v1 = inflater.inflate(R.layout.form_find_m1, null);
		list.add(v1);
		et_form = (EditText)v1.findViewById(R.id.form);
		but_next = (Button)v1.findViewById(R.id.but_formFind_next);
		but_next.setOnClickListener(this);
		
		
		
		View v2 = inflater.inflate(R.layout.activity_web , null);
		list.add(v2);
		webView = (WebView) v2.findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true); 
//		webView.loadUrl(url); 
//		System.out.println(url);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		  
		vp_main.setAdapter(new viewPagerAdapter(this, list));
		ViewPagerScroller vps = new ViewPagerScroller(this);
		vps.setScrollDuration(600);
		vps.initViewPagerScroll(vp_main);
		
		
	}
	
	class MyWebViewClient extends WebViewClient{
        @Override 
        public boolean shouldOverrideUrlLoading(WebView view,String url_){ 
            view.loadUrl(url_); 
            return true;
        }}

	@Override
	public void onClick(View v) {
		
		switch(v.getId()){
		case R.id.but_formFind_next:
			String str = et_form.getText().toString().trim();
			if(TextUtils.isEmpty(str)){
				new SweetAlertDialog(WebActivity.this, SweetAlertDialog.ERROR_TYPE)
				.setTitleText("运单号不能为空")
				.show();
			}else{
				String url = "http://m.kuaidi100.com/index_all.html?postid=" + str;
				webView.loadUrl(url); 
				vp_main.setCurrentItem(1);
			}
			break;
		case R.id.back:
			if(vp_main.getCurrentItem() == 1){
				
				vp_main.setCurrentItem(0);
			
			}else{
			
				startActivity(new Intent(WebActivity.this,MainActivity.class));
				WebActivity.this.finish();
			
			}
		
		}
		
	}


}
