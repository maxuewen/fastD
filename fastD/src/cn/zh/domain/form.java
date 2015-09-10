package cn.zh.domain;

public class form {
	private String user_id;
	private String formNum;
	private String riceiptAd;
	private String receiptPhone;
	private String recieptName;
	private String shipPhone;
	private double poi_x;
	private double poi_y;
	private String time;
	private String state;
	private String fast_id;
	private String mark;
	
	
	
	
	
	@Override
	public String toString() {
		return "form [user_id=" + user_id + ", formNum=" + formNum
				+ ", riceiptAd=" + riceiptAd + ", receiptPhone=" + receiptPhone
				+ ", recieptName=" + recieptName + ", shipPhone=" + shipPhone
				+ ", poi_x=" + poi_x + ", poi_y=" + poi_y + ", time=" + time
				+ ", state=" + state + ", fast_id=" + fast_id + ", mark="
				+ mark + "]";
	}
	public form(String user_id, String formNum, String riceiptAd,
			String receiptPhone, String recieptName, String shipPhone,
			double poi_x, double poi_y, String time, String state,
			String fast_id, String mark) {
		super();
		this.user_id = user_id;
		this.formNum = formNum;
		this.riceiptAd = riceiptAd;
		this.receiptPhone = receiptPhone;
		this.recieptName = recieptName;
		this.shipPhone = shipPhone;
		this.poi_x = poi_x;
		this.poi_y = poi_y;
		this.time = time;
		this.state = state;
		this.fast_id = fast_id;
		this.mark = mark;
	}
	public String getRecieptName() {
		return recieptName;
	}
	public void setRecieptName(String recieptName) {
		this.recieptName = recieptName;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getFormNum() {
		return formNum;
	}
	public void setFormNum(String formNum) {
		this.formNum = formNum;
	}
	public String getRiceiptAd() {
		return riceiptAd;
	}
	public void setRiceiptAd(String riceiptAd) {
		this.riceiptAd = riceiptAd;
	}
	public String getReceiptPhone() {
		return receiptPhone;
	}
	public void setReceiptPhone(String receiptPhone) {
		this.receiptPhone = receiptPhone;
	}
	public String getShipPhone() {
		return shipPhone;
	}
	public void setShipPhone(String shipPhone) {
		this.shipPhone = shipPhone;
	}
	public double getPoi_x() {
		return poi_x;
	}
	public void setPoi_x(double poi_x) {
		this.poi_x = poi_x;
	}
	public double getPoi_y() {
		return poi_y;
	}
	public void setPoi_y(double poi_y) {
		this.poi_y = poi_y;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFast_id() {
		return fast_id;
	}
	public void setFast_id(String fast_id) {
		this.fast_id = fast_id;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
}
