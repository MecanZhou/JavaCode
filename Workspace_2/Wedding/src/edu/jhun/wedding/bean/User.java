package edu.jhun.wedding.bean;

public class User {
	
	private int user_id;
	private String user_nickname;
	private String user_tel;
	private String user_password;
	
	public User() {
		super();
	}
	public User(int user_id, String user_nickname, String user_tel,
			String user_password) {
		super();
		this.user_id = user_id;
		this.user_nickname = user_nickname;
		this.user_tel = user_tel;
		this.user_password = user_password;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUser_nickname() {
		return user_nickname;
	}
	public void setUser_nickname(String user_nickname) {
		this.user_nickname = user_nickname;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_nickname=" + user_nickname
				+ ", user_tel=" + user_tel + ", user_password=" + user_password
				+ "]";
	}

}
