package cn.zh.domain;

public class user_Ad {
	private String user_id;
	private String receiptAd_1;
	private String receiptAd_2;
	private String name;
	private String phone;
	
	public user_Ad(){}

	@Override
	public String toString() {
		return "user_Ad [user_id=" + user_id + ", receiptAd_1=" + receiptAd_1
				+ ", receiptAd_2=" + receiptAd_2 + ", name=" + name
				+ ", phone=" + phone + "]";
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getReceiptAd_1() {
		return receiptAd_1;
	}

	public void setReceiptAd_1(String receiptAd_1) {
		this.receiptAd_1 = receiptAd_1;
	}

	public String getReceiptAd_2() {
		return receiptAd_2;
	}

	public void setReceiptAd_2(String receiptAd_2) {
		this.receiptAd_2 = receiptAd_2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public user_Ad(String user_id, String receiptAd_1, String receiptAd_2,
			String name, String phone) {
		super();
		this.user_id = user_id;
		this.receiptAd_1 = receiptAd_1;
		this.receiptAd_2 = receiptAd_2;
		this.name = name;
		this.phone = phone;
	}

	
	
	
	




}
