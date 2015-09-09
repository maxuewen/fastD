package cn.zh.domain;

public class fast {
	private String fast_id;
	private String phone;
	private String password;
	private String name;
	private String company;
	private double xx;
	private double yy;
	private double cu_x;
	private double cu_y;
	
	public fast(String fast_id, String phone, String password, String name,
			String company, double xx, double yy, double cu_x, double cu_y) {
		super();
		this.fast_id = fast_id;
		this.phone = phone;
		this.password = password;
		this.name = name;
		this.company = company;
		this.xx = xx;
		this.yy = yy;
		this.cu_x = cu_x;
		this.cu_y = cu_y;
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
	public String getFast_id() {
		return fast_id;
	}
	public void setFast_id(String fast_id) {
		this.fast_id = fast_id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public double getXx() {
		return xx;
	}
	public void setXx(double xx) {
		this.xx = xx;
	}
	public double getYy() {
		return yy;
	}
	public void setYy(double yy) {
		this.yy = yy;
	}
	public fast(String fast_id, String phone, String password, String name,
			String company, double xx, double yy) {
		super();
		this.fast_id = fast_id;
		this.phone = phone;
		this.password = password;
		this.name = name;
		this.company = company;
		this.xx = xx;
		this.yy = yy;
	}
	
	
}
