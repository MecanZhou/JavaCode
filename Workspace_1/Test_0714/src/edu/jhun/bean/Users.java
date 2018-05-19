package edu.jhun.bean;

public class Users {
	
	private int id;
	private String userName;
	private String userPwd;
	private String gender;
	private String pwdQuestion;
	private String pwdAnswer;
	public Users() {
		super();
		// TODO 自动生成的构造函数存根
	}
	public Users(int id, String userName, String userPwd, String gender,
			String pwdQuestion, String pwdAnswer) {
		super();
		this.id = id;
		this.userName = userName;
		this.userPwd = userPwd;
		this.gender = gender;
		this.pwdQuestion = pwdQuestion;
		this.pwdAnswer = pwdAnswer;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPwdQuestion() {
		return pwdQuestion;
	}
	public void setPwdQuestion(String pwdQuestion) {
		this.pwdQuestion = pwdQuestion;
	}
	public String getPwdAnswer() {
		return pwdAnswer;
	}
	public void setPwdAnswer(String pwdAnswer) {
		this.pwdAnswer = pwdAnswer;
	}
	
}
