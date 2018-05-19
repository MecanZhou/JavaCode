/**
 * 
 */
package edu.jhun.bean;

import java.util.List;

/**
 * @author Administrator
 * @time 2018年1月11日下午4:52:44
 *	方案实体类
 */
public class Scheme {
	
	private String shemeId;
	private String shemeName;
	private List<Member> members;
	
	//构造方法
	public Scheme() {
		super();
	}
	public Scheme(String shemeId, String shemeName, List<Member> members) {
		super();
		this.shemeId = shemeId;
		this.shemeName = shemeName;
		this.members = members;
	}
	
	//getter/setter方法
	public String getShemeId() {
		return shemeId;
	}
	public void setShemeId(String shemeId) {
		this.shemeId = shemeId;
	}
	public String getShemeName() {
		return shemeName;
	}
	public void setShemeName(String shemeName) {
		this.shemeName = shemeName;
	}
	public List<Member> getMembers() {
		return members;
	}
	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	@Override
	public String toString() {
		return "Scheme [shemeId=" + shemeId + ", shemeName=" + shemeName + ", members=" + members + "]";
	}

}
