/**
 * 
 */
package edu.jhun.bean;

import java.util.List;

/**
 * @author Administrator
 * @time 2018��1��11������4:52:44
 *	����ʵ����
 */
public class Scheme {
	
	private String shemeId;
	private String shemeName;
	private List<Member> members;
	
	//���췽��
	public Scheme() {
		super();
	}
	public Scheme(String shemeId, String shemeName, List<Member> members) {
		super();
		this.shemeId = shemeId;
		this.shemeName = shemeName;
		this.members = members;
	}
	
	//getter/setter����
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
