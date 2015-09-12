package cn.zh.domain;

public class user {
	private String user_id;
	private String phone;
	private String name;
	private String password;
	private double cu_x;
	private double cu_y;
	
	public user(){}
	
	
	@Override
	public String toString() {
		return "user [user_id=" + user_id + ", phone=" + phone + ", name="
				+ name + ", password=" + password + ", cu_x=" + cu_x
				+ ", cu_y=" + cu_y + "]";
	}
	public String getUser_id() {
		return user_id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public user(String user_id, String phone, String name, String password,
			double cu_x, double cu_y) {
		super();
		this.user_id = user_id;
		this.phone = phone;
		this.name = name;
		this.password = password;
		this.cu_x = cu_x;
		this.cu_y = cu_y;
	}
	
	
	
	
	
}