package cn.zh.fastD;

import cn.zh.Utils.ActionBarUtils;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class WebActivity_simple extends Activity implements OnClickListener{
	
	
	private WebView webView;
	private View actionBar;
	private ImageButton but_back;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web_activity_simple);
		
		
		actionBar = ActionBarUtils.setAtionBar(this,
				R.layout.fast_register_actionbar);
		but_back = (ImageButton) actionBar.findViewById(R.id.fr_back);
		
		but_back.setOnClickListener(this);
		((TextView) actionBar.findViewById(R.id.fr_register_titleBar)).setText("订单查询");
		
		Intent in = getIntent();
		String url = in.getStringExtra("url");
		
		webView = (WebView)findViewById(R.id.webView_simple);
		webView.getSettings().setJavaScriptEnabled(true); 
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.loadUrl("http://m.kuaidi100.com/index_all.html?postid=" + url); 
		
	}
	
	
	class MyWebViewClient extends WebViewClient{ 
        @Override 
        public boolean shouldOverrideUrlLoading(WebView view,String url_){ 
            view.loadUrl(url_); 
            return true;
        }}


	@Override
	public void onClick(View v) {
		startActivity(new Intent(WebActivity_simple.this,MainActivity.class));
		WebActivity_simple.this.finish();
		
	}


}
