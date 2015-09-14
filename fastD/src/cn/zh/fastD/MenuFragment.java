package cn.zh.fastD;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MenuFragment extends Fragment implements OnClickListener {

	View view;
	SharedPreferences sh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		sh = getActivity().getSharedPreferences("fastD", 0x8000);
		
		super.onCreate(savedInstanceState);

		this.view = getActivity().getLayoutInflater().inflate(
				R.layout.m_layout_menu, null);

		((RelativeLayout) view.findViewById(R.id.menu_1))
				.setOnClickListener(this);
		((RelativeLayout) view.findViewById(R.id.menu_2))
		.setOnClickListener(this);
		((RelativeLayout) view.findViewById(R.id.menu_3))
		.setOnClickListener(this);
		((RelativeLayout) view.findViewById(R.id.menu_4))
		.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.menu_1:
			startActivity(new Intent(getActivity(),WebActivity.class));
			break;

		case R.id.menu_2:
			startActivity(new Intent(getActivity(),AddReceiptAd.class));
			break;

		case R.id.menu_3:
			Intent in = new Intent(getActivity(),User_register_Activity.class);
			in.putExtra("key", "1");
			
			startActivity(in);
			break;

		case R.id.menu_4:
			Intent a = new Intent(getActivity(),LoginActivity.class);
			a.putExtra("islogin", "login_false");
			startActivity(a);
			break;

		}

	}

}
