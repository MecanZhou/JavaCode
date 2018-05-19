/**
 * 
 */
package edu.jhun.bean;

import java.util.List;

/**
 * @author Administrator
 * @time 2018年1月8日下午2:28:09
 *
 */
public class Member {
	
	private String MemberId;
	private String MemberName;
	private List<Attribute> attributes;
	public Member() {
		super();
	}
	public Member(String memberId, String memberName, List<Attribute> attributes) {
		super();
		MemberId = memberId;
		MemberName = memberName;
		this.attributes = attributes;
	}
	public String getMemberId() {
		return MemberId;
	}
	public void setMemberId(String memberId) {
		MemberId = memberId;
	}
	public String getMemberName() {
		return MemberName;
	}
	public void setMemberName(String memberName) {
		MemberName = memberName;
	}
	public List<Attribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	@Override
	public String toString() {
		return "Member [MemberId=" + MemberId + ", MemberName=" + MemberName + ", attributes=" + attributes + "]";
	}
	
}
