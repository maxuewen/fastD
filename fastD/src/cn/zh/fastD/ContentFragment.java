package cn.zh.fastD;

import java.util.ArrayList;
import java.util.List;

import com.amap.api.maps2d.AMap;

import cn.zh.Utils.ActionBarUtils;
import cn.zh.Utils.Constants;
import cn.zh.Utils.NoTouchViewPager;
import cn.zh.Utils.ViewPagerScroller;
import cn.zh.adapter.formListViewAdp_m1;
import cn.zh.adapter.viewPagerAdapter;
import cn.zh.domain.main;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

@SuppressLint("ValidFragment")
public class ContentFragment extends Fragment implements OnItemClickListener,OnClickListener,
		OnCheckedChangeListener {
	
	List list_m1;
	
	private ViewPagerScroller vps;


	private View view;
	private int cu_page = 0;  					//标记当前页面
	
	private NoTouchViewPager vp_main;
	private RadioButton cu_rbut;
	private RadioButton rbut_1;
	private RadioButton rbut_2;
	private RadioButton rbut_3;
	private ListView lv_m1;
	private ImageView iv_noData_m1;
	private ImageView iv_noData_m2;
	
	private formListViewAdp_m1 formLVadp;
	private TextView m4_state;
	private TextView m4_company;
	private TextView m4_form;
	private TextView m4_receiptAd;
	private TextView m4_mark;
	private TextView m4_time;
	private TextView m4_fastName;
	private TextView m4_fastPhone;
	private TextView m4_receiptName;

	private AMap aMap;

	private View actionBar;
	private ImageButton but_back;
	private TextView tv_title;
	private Button but_next;


	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		actionBar = ActionBarUtils.setAtionBar(getActivity(),
				R.layout.fast_register_actionbar);
		but_back = (ImageButton) actionBar.findViewById(R.id.fr_back);
		
		but_back.setOnClickListener(this);
		tv_title = (TextView) actionBar.findViewById(R.id.fr_register_titleBar);
		but_next = (Button) actionBar.findViewById(R.id.fr_next);
		but_next.setOnClickListener(this);
		setActionBar("我的寄件", "查询");
		
		super.onCreate(savedInstanceState);
		this.view = getActivity().getLayoutInflater().inflate(
				R.layout.m_layout_content, null);
		
		init();
		
		
	}
	
	private void setBackground_back(int a){
		if(a == 1){
			but_back.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_action_sort_by_size));
		}else if(a == 2){
			but_back.setBackground(getActivity().getResources().getDrawable(R.drawable.ic_action_back));
		}else{
			but_back.setBackground(null);
			but_back.setClickable(false);
		}
		
	}
	
	private void setActionBar(String title ,String next){
		but_next.setText(next);
		tv_title.setText(title);
		
		if(TextUtils.isEmpty(next)){
			but_next.setClickable(false);
		}else{
			but_next.setClickable(true);
		}
	}

	private void init() {

		LayoutInflater inflater = getActivity().getLayoutInflater();
		vp_main = (NoTouchViewPager)view.findViewById(R.id.vp_main);
		List<View> list = new ArrayList<View>();
		View v ;
		
		//=============================主界面第一张
		v = inflater.inflate(R.layout.user_m_mypage, null);
		lv_m1 = (ListView) v.findViewById(R.id.lv_myRecorde);
		iv_noData_m1 = (ImageView)v.findViewById(R.id.iv_user_m3_centent);
		
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
		iv_noData_m2 = (ImageView)v.findViewById(R.id.iv_user_m3_centent);
		
		
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
			m4_receiptName = (TextView)v.findViewById(R.id.tv_receiptName);
			
			
		list.add(v);
		//=============================主界面第五张，显示已完成的订单
		
		
		
		
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
				
				
				setBottomRbut(1);			//设置button1为选中状态
				cu_rbut = rbut_1;
	}
	

	
	
	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.radio_button1:
				setBottomRbut(1);
				vp_main.setCurrentItem(0);
				
				cu_rbut = rbut_1;
				cu_page = 0;
				break;
			case R.id.radio_button2:
				setBottomRbut(2);
				vp_main.setCurrentItem(1);
				
				cu_rbut = rbut_2;
				cu_page = 1;
				break;
			case R.id.radio_button3:
				setBottomRbut(3);
				vp_main.setCurrentItem(2);

				cu_rbut = rbut_3;
				cu_page = 2;
				break;
				
		
			}
		}

	}
	
	
	private void setBottomRbut(int a){
		if(cu_rbut != null)cu_rbut.setTextColor(Color.rgb(108,108,108));
		
		if(a == 1){
			rbut_1.setTextColor(Color.rgb(36,155,255));
			
			setActionBar("我的寄件", "查询");
			setBackground_back(1);
			
			if(Constants.list_form_m1 == null || Constants.list_form_m1.size() == 0){
				iv_noData_m1.setVisibility(0x00000000);
			}else{
				iv_noData_m1.setVisibility(0x00000004);
			}
			
		}else if(a == 2){
			rbut_2.setTextColor(Color.rgb(36,155,255));
			
			
			setActionBar("寄件", "添加");
			setBackground_back(1);
		}else if(a == 3){
			rbut_3.setTextColor(Color.rgb(36,155,255));
			
			setActionBar( "收件", "");
			setBackground_back(1);
			
			if(Constants.list_form_m3 == null || Constants.list_form_m3.size() == 0){
				iv_noData_m2.setVisibility(0x00000000);
			}else{
				iv_noData_m2.setVisibility(0x00000004);
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		main m = (main) Constants.list_form_m1.get(position);
		//设置actionbar
		setActionBar("订单查询", "");
		setBackground_back(2);
		
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
			m4_receiptName.setText(m.getRecieptName());
			rbut_2.setClickable(false);
			rbut_3.setClickable(false);
			cu_page = 3;
			
		}else{
			
			Intent in = new Intent(getActivity(), user_form_doing.class);
			in.putExtra("company", m.getCompany());
			in.putExtra("fastName", m.getFast_name());
			in.putExtra("fastPhone",m.getFast_phone());
			in.putExtra("time",m.getTime());
			in.putExtra("receiptAd", m.getRiceiptAd());
			in.putExtra("mark", m.getMark());
			if(m.getCu_x()!=0.0 && m.getCu_y()!=0.0){
				in.putExtra("x", m.getCu_x());
				in.putExtra("y", m.getCu_y());
			}
			startActivity(in);
			rbut_2.setClickable(false);
			rbut_3.setClickable(false);
		}
		
	}

	@Override
	public void onClick(View v) {

		switch(v.getId()){
		case R.id.fr_back:
			if(cu_page == 3){
				vps.setScrollDuration(600);
				vp_main.setCurrentItem(0);
				setActionBar("我的寄件", "");
				setBackground_back(1);
				cu_page = 0;
				rbut_2.setClickable(true);
				rbut_3.setClickable(true);
				
				vps.setScrollDuration(0);
			}else{

				((SlidingFragmentActivity) getActivity()).getSlidingMenu().toggle(true);
			}
			break;
			

		case R.id.fr_next:
			
			if(but_next.getText().equals("查询")){
				Intent in = new Intent(getActivity(),WebActivity.class);
				startActivity(in);
			}else if(but_next.getText().equals("添加")){
				startActivity(new Intent(getActivity(),AddReceiptAd.class));
			}
			
			
		}
		
		
	}
	
	
	

}
