package domain;

public class User {
	private String userId;
	private String username;
	private String password;
	private Boolean state;
	
	public Boolean getState() {
		return state;
	}
	public void setState(Boolean state) {
		this.state = state;
	}
	public User() {
		userId=null;
		username=null;
		password=null;
		state=false;
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	@Override
	public String toString() {
		return "{\"userId\":\"" + userId + "\",\"username\":\"" + username
				+ "\", \"password\":\"" + password + "\", \"state\":\"" + state + "\"}";
	}
	public String getUserId() {
		return userId;
	}
	public User(String userId, String username, String password, Boolean state) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.state = state;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
