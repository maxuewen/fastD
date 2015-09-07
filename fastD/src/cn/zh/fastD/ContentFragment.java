package cn.zh.fastD;

import java.util.ArrayList;
import java.util.List;

import cn.zh.Utils.NoTouchViewPager;
import cn.zh.adapter.viewPagerAdapter;
import android.annotation.SuppressLint;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class ContentFragment extends Fragment implements
		OnCheckedChangeListener {

	private View view;
	private NoTouchViewPager vp_main;
	private RadioButton cu_rbut;
	private RadioButton rbut_1;
	private RadioButton rbut_2;
	private RadioButton rbut_3;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.view = getActivity().getLayoutInflater().inflate(
				R.layout.m_layout_content, null);

		init();

	}

	private void init() {

		vp_main = (NoTouchViewPager)view.findViewById(R.id.vp_main);
		List<View> list = new ArrayList<View>();
//		list.add(object);
		viewPagerAdapter vpad = new viewPagerAdapter(getActivity(), list);
		
		rbut_1 = ((RadioButton)view.findViewById(R.id.radio_button1));
				rbut_1.setOnCheckedChangeListener(this);
		rbut_2 = ((RadioButton)view.findViewById(R.id.radio_button2));
				rbut_2.setOnCheckedChangeListener(this);
		rbut_3 = ((RadioButton)view.findViewById(R.id.radio_button3));
				rbut_3.setOnCheckedChangeListener(this);
				
				
		
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.radio_button1:
				setBottomRbut(1);
				
				
				cu_rbut = rbut_1;
				break;
			case R.id.radio_button2:
				setBottomRbut(2);
				
				
				cu_rbut = rbut_2;
				break;
			case R.id.radio_button3:
				setBottomRbut(3);
				

				cu_rbut = rbut_3;
				break;
			}
		}

	}
	
	private void setBottomRbut(int a){
		if(cu_rbut != null)cu_rbut.setTextColor(Color.rgb(108,108,108));
		
		if(a == 1){
			rbut_1.setTextColor(Color.rgb(36,155,255));
		}else if(a == 2){
			rbut_2.setTextColor(Color.rgb(36,155,255));
		}else if(a == 3){
			rbut_3.setTextColor(Color.rgb(36,155,255));
		}
	}
	
	
	

}
