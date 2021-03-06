package cn.zh.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.zh.domain.main;
import cn.zh.domain.user_Ad;

import com.amap.api.maps2d.model.LatLng;

public class Constants {

	public static final int ERROR = 1001;// 网络异常
	public static final int ROUTE_START_SEARCH = 2000;
	public static final int ROUTE_END_SEARCH = 2001;
	public static final int ROUTE_BUS_RESULT = 2002;// 路径规划中公交模式
	public static final int ROUTE_DRIVING_RESULT = 2003;// 路径规划中驾车模式
	public static final int ROUTE_WALK_RESULT = 2004;// 路径规划中步行模式
	public static final int ROUTE_NO_RESULT = 2005;// 路径规划没有搜索到结果

	public static final int GEOCODER_RESULT = 3000;// 地理编码或者逆地理编码成功
	public static final int GEOCODER_NO_RESULT = 3001;// 地理编码或者逆地理编码没有数据

	public static final int POISEARCH = 4000;// poi搜索到结果
	public static final int POISEARCH_NO_RESULT = 4001;// poi没有搜索到结果
	public static final int POISEARCH_NEXT = 5000;// poi搜索下一页

	public static final int BUSLINE_LINE_RESULT = 6001;// 公交线路查询
	public static final int BUSLINE_id_RESULT = 6002;// 公交id查询
	public static final int BUSLINE_NO_RESULT = 6003;// 异常情况

	public static final LatLng BEIJING = new LatLng(39.90403, 116.407525);// 北京市经纬度
	public static final LatLng ZHONGGUANCUN = new LatLng(39.983456, 116.3154950);// 北京市中关村经纬度
	public static final LatLng SHANGHAI = new LatLng(31.238068, 121.501654);// 上海市经纬度
	public static final LatLng FANGHENG = new LatLng(39.989614, 116.481763);// 方恒国际中心经纬度
	public static final LatLng CHENGDU = new LatLng(30.679879, 104.064855);// 成都市经纬度
	public static final LatLng XIAN = new LatLng(34.341568, 108.940174);// 西安市经纬度
	public static final LatLng ZHENGZHOU = new LatLng(34.7466, 113.625367);// 郑州市经纬度
	public static final LatLng ZHONGHUANXUEYUAN = new LatLng(39.126148, 117.01889);// 中环学院
	
	//地图示例区域
	public static final LatLng xx = new LatLng(39.946178, 116.357505);// 中环学院
	public static final LatLng xy = new LatLng(39.946178, 116.437667);// 中环学院
	public static final LatLng yy = new LatLng(39.897727, 116.437667);// 中环学院
	public static final LatLng yx = new LatLng(39.897727, 116.357505);// 中环学院
	
	
	public static String[] companyList = {
		
		"邮政快递","宅急送","天天快递"
		,"韵达快递","百世汇通","中通快递","圆通快递","申通快递","顺丰快递","EMS","北京EMS","城际速递","凡客订单",
		"高铁速递","京东快递","民航快递","香港邮政","邮政国际邮件","中邮物流","中铁快运","芝麻开门"		
	};
	
	public static String url = null;
	
	//关于fast的静态字段
	public static String e_fastPhone_exist = "fast_phone_exist";
	public static String f_fastRegiste = "fast_registe_finish";
	public static String get_fast= "get_fast";
	public static String add_fast = "addfast";
	public static String set_fast_position = "set_fast_position";
	public static String alert_fast_ad = "alert_fast_add";
	
	//关于form的
	public static String formState_unfinish = "未接收";
	public static String formState_doing = "已接收";
	public static String formState_finish = "已完成";
	public static String formState_doing_unfinish = "yiwancheng_and_weiwanchegn";
	public static String getFormByUserId = "getFormByUserId";
	public static String getFormByUserId_all = "getFormByUserId_all";
	public static String getFormByFastId_all = "getFormByFastId_all";
	public static String getFormByFastId = "getFormByFastId";
	public static String addform = "addform";
	public static String alertForm = "alertForm";
	public static String getMyReceiptForm = "getMyReceiptForm";
	public static String getFormCanreceipt = "getFormCanreceipt";
	
	//关于user的
	public static String e_userPhone_exist = "user_phone_exist";
	public static String f_userRegiste = "user_registe_finish";
	public static String get_user = "get_user";
	public static String add_user = "add_user";
	public static String set_user_position = "set_user_position";
	public static String alert_User_ad = "alert_user_add";
	public static String alertPasswrod = "alertPasswrod";
	public static String add_userAd= "add_user_ad";
	
	
	public static String ok = "ok";
	public static String f = "file";
	public static String get_user_ad = "getuserad";
	
	
	public static List<main> list_form_m1 ;
	public static List<user_Ad> list_form_m2 ;
	public static List<main> list_form_m3 ;
	
	public static List<main> fast_list_m1 ;
	public static List<main> fast_list_m2 ;
	public static List<main> fast_list_m3 ;
	
	public static double user_lat = 39.90403;
	public static double user_lng = 116.407525;
	
	public static double fast_lat = 39.90403;
	public static double fast_lng = 116.407525;
	
	
	public static Boolean isStartSerivice = false;

}
