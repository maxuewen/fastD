package cn.zh.domain;

public class main {
	private String formNum;
	private String riceiptAd;
	private String time;
	private String state;
	private String mark;
	
	private String fast_phone;
	private String fast_name;
	private String company;
	private double cu_x;
	private double cu_y;
	
	private String user_phone;
	private String user_name;
	
	private String recieptName;		//收货人的姓名
	private String receiptPhone;
	private String shipAd;
	
	
	
	public main(String str1,String str2,String str3){
		this.user_name = str1;
		this.time = str2;
		this.state = str3;
	}
	
	public String getShipAd() {
		return shipAd;
	}

	public void setShipAd(String shipAd) {
		this.shipAd = shipAd;
	}

	public String getReceiptPhone() {
		return receiptPhone;
	}

	public void setReceiptPhone(String receiptPhone) {
		this.receiptPhone = receiptPhone;
	}

	public String getRecieptName() {
		return recieptName;
	}

	public void setRecieptName(String recieptName) {
		this.recieptName = recieptName;
	}

	public main(form form){
		this.formNum = form.getFormNum();
		this.riceiptAd = form.getRiceiptAd();
		this.time = form.getTime();
		this.state = form.getState();
		this.mark = form.getMark();
		this.shipAd = form.getShipAd();
	}
	
	public main(user user , form form){
		this.user_name = user.getName();
		this.user_phone = user.getPhone();
		
		this.formNum = form.getFormNum();
		this.riceiptAd = form.getRiceiptAd();
		this.time = form.getTime();
		this.state = form.getState();
		this.mark = form.getMark();
		this.shipAd = form.getShipAd();
	}
	
	public main(user user , form form , fast fast){
		this.user_name = user.getName();
		this.user_phone = user.getPhone();
		
		this.formNum = form.getFormNum();
		this.riceiptAd = form.getRiceiptAd();
		this.time = form.getTime();
		this.state = form.getState();
		this.mark = form.getMark();
		this.receiptPhone = form.getReceiptPhone();
		this.recieptName = form.getRecieptName();
		
		this.fast_name = fast.getName();
		this.fast_phone = fast.getPhone();
		this.company = fast.getCompany();
		this.cu_x = fast.getCu_x();
		this.cu_y = fast.getCu_y();
		
		
		this.shipAd = form.getShipAd();
	}
	public main(){
		
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
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getFast_phone() {
		return fast_phone;
	}
	public void setFast_phone(String fast_phone) {
		this.fast_phone = fast_phone;
	}
	public String getFast_name() {
		return fast_name;
	}
	public void setFast_name(String fast_name) {
		this.fast_name = fast_name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public double getCu_x() {
		return cu_x;
	}
	public void setCu_x(double cu_x) {
		this.cu_x = cu_x;
	}
	public double getCu_y() {
		return cu_y;
	}
	public void setCu_y(double cu_y) {
		this.cu_y = cu_y;
	}
	public String getUser_phone() {
		return user_phone;
	}
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Override
	public String toString() {
		return "main [formNum=" + formNum + ", riceiptAd=" + riceiptAd
				+ ", time=" + time + ", state=" + state + ", mark=" + mark
				+ ", fast_phone=" + fast_phone + ", fast_name=" + fast_name
				+ ", company=" + company + ", cu_x=" + cu_x + ", cu_y=" + cu_y
				+ ", user_phone=" + user_phone + ", user_name=" + user_name
				+ ", recieptName=" + recieptName + ", receiptPhone="
				+ receiptPhone + ", shipAd=" + shipAd + "]";
	}


	
	
	
	
	
	
	
	
	
}
