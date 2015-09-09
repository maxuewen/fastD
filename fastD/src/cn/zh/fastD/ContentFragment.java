package cn.zh.fastD;

import java.util.ArrayList;
import java.util.List;

import cn.zh.Utils.Constants;
import cn.zh.Utils.NoTouchViewPager;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.adapter.formListViewAdp_m1;
import cn.zh.adapter.viewPagerAdapter;
import cn.zh.domain.main;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class ContentFragment extends Fragment implements OnItemClickListener,
		OnCheckedChangeListener {
	
	List list_m1;
	
	private ViewPagerScroller vps;

	private View view;
	private NoTouchViewPager vp_main;
	private RadioButton cu_rbut;
	private RadioButton rbut_1;
	private RadioButton rbut_2;
	private RadioButton rbut_3;
	private ListView lv_m1;
	private formListViewAdp_m1 formLVadp;
	private TextView m4_state;
	private TextView m4_company;
	private TextView m4_form;
	private TextView m4_receiptAd;
	private TextView m4_mark;
	private TextView m4_time;
	private TextView m4_fastName;
	private TextView m4_fastPhone;
	

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

		LayoutInflater inflater = getActivity().getLayoutInflater();
		vp_main = (NoTouchViewPager)view.findViewById(R.id.vp_main);
		List<View> list = new ArrayList<View>();
		View v ;
		
		//=============================主界面第一张
		v = inflater.inflate(R.layout.user_m_mypage, null);
		lv_m1 = (ListView) v.findViewById(R.id.lv_myRecorde);
		formLVadp = new formListViewAdp_m1(getActivity());
		lv_m1.setAdapter(formLVadp);
		lv_m1.setOnItemClickListener(this);
		
		
		list.add(v);
		
		//=============================主界面第二张
		v = inflater.inflate(R.layout.user_m_ship, null);
//		
		list.add(v);
		
		//=============================主界面第三张
		v = inflater.inflate(R.layout.user_m3_receipt, null);
//		
		
		
		list.add(v);
		//=============================主界面第四张,显示未完成的订单
		v = inflater.inflate(R.layout.user_m4_finishform, null);
			m4_state = (TextView)v.findViewById(R.id.tv_state);
			m4_company = (TextView)v.findViewById(R.id.tv_company);
			m4_form = (TextView)v.findViewById(R.id.tv_form);
			m4_receiptAd = (TextView)v.findViewById(R.id.tv_receiptAd);
			m4_time = (TextView)v.findViewById(R.id.tv_time);
			m4_mark = (TextView)v.findViewById(R.id.tv_mark);
			m4_fastPhone = (TextView)v.findViewById(R.id.tv_fastPhone);
			m4_fastName = (TextView)v.findViewById(R.id.tv_fastName);
			
			
		list.add(v);
		//=============================主界面第五张，显示已完成的订单
		
		v = inflater.inflate(R.layout.user_m5_unfinish_form, null);
		
		
		list.add(v);
		
		
		//设置主界面的viewpage
		viewPagerAdapter vpad = new viewPagerAdapter(getActivity(), list);
		vp_main.setAdapter(vpad);
		vps = new ViewPagerScroller(getActivity());
		vps.setScrollDuration(0);
		vps.initViewPagerScroll(vp_main);
		
		
		//设置底部的三个button
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
				vp_main.setCurrentItem(0);
				
				cu_rbut = rbut_1;
				break;
			case R.id.radio_button2:
				setBottomRbut(2);
				vp_main.setCurrentItem(1);
				
				cu_rbut = rbut_2;
				break;
			case R.id.radio_button3:
				setBottomRbut(3);
				vp_main.setCurrentItem(2);

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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		main m = (main) Constants.list_form_m1.get(position);
//		&& !m.getState().equals(Constants.formState_unfinish)
		if(parent.getAdapter() == formLVadp && !m.getState().equals(Constants.formState_unfinish)){
			vps.setScrollDuration(600);
			vp_main.setCurrentItem(3);
			vps.setScrollDuration(0);
			m4_state.setText(m.getState());
			m4_company.setText(m.getCompany());
			m4_form.setText(m.getFormNum());
			m4_receiptAd.setText(m.getRiceiptAd());
			m4_time.setText(m.getTime());
			m4_mark.setText(m.getMark());
			m4_fastName.setText(m.getFast_name());
			m4_fastPhone.setText(m.getFast_phone());
		}else{
			
			vps.setScrollDuration(600);
			vp_main.setCurrentItem(4);
			vps.setScrollDuration(0);
			
		}
		
	}
	
	
	

}
