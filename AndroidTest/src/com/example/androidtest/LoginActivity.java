package com.example.androidtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import domain.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {

	Button but_login;
	EditText et_username;
	EditText et_password;
	TextView tv_regest;
	String username;
	String password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);

		// 获取界面上的元素
		but_login = (Button) findViewById(R.id.but_login);
		tv_regest = (TextView) findViewById(R.id.but_regist);
		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);

		but_login.setOnClickListener(this);
		tv_regest.setOnClickListener(this);

		et_password.setText("q");
		et_username.setText("q");
	}

	@Override
	public void onClick(View v) {

		if (v instanceof Button) {
			if (loginPro()) {

				Map map = new HashMap<String, String>();
				map.put("param", "login");
				User user = new User(username, password);

				map.put("user", user.toString());

				try {

					String result = HttpUtils.postRequest(HttpUtils.url
							+ "userServlet", map);

					JSONObject js = new JSONObject(result);
					User u;
					try {
						
						 u = new User(js.getString("userId"), "", "",
								js.getBoolean("state"));


					} catch (Exception e) {
						// TODO Auto-generated catch block
						Toast.makeText(this, "登录错误", 0);
						return;
					}
					// org.json.JSONObject re = new org.json.JSONObject(result);

					if (u.getState() == false) {
						Toast.makeText(this, "账号或密码错误", 1).show();
						et_password.setText("");
						et_username.setText("");
						return;
					} else if (u.getState() == true) {
						Intent i = new Intent(LoginActivity.this,
								MainActivity.class);
						startActivity(i);
						finish();
					} else {
						Toast.makeText(this, result, 1);
						return;
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					Toast.makeText(this, "服务器异常", 0).show();
					return;
				}
			}
		}

	}

	private boolean loginPro() {
		username = et_username.getText().toString();
		password = et_password.getText().toString();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, "账号不能为空", 1).show();
			return false;
		}
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(this, "密码不能为空", 1).show();
			return false;
		}
		return true;
	}

}
