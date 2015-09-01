package cn.zh.domain;

public class user {
	private String user_id;
	private String phone;
	private String name;
	private String receiptAd;
	private String password;
	public String getUser_id() {
		return user_id;
	}
	public user(String user_id, String phone, String password) {
		super();
		this.user_id = user_id;
		this.phone = phone;
		this.password = password;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReceiptAd() {
		return receiptAd;
	}
	public void setReceiptAd(String receiptAd) {
		this.receiptAd = receiptAd;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public user(String user_id, String phone, String name, String receiptAd,
			String password) {
		super();
		this.user_id = user_id;
		this.phone = phone;
		this.name = name;
		this.receiptAd = receiptAd;
		this.password = password;
	}
	
	
}
