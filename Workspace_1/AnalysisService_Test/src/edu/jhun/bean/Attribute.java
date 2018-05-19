/**
 * 
 */
package edu.jhun.bean;

/**
 * @author Administrator
 * @time 2018年1月16日下午5:24:48
 *
 */
public class Attribute {
	
	private String shemeId = null;
	private String memberId = null;
	private String attrId = null;
	private String attrName = null;
	private double Outputvalue = 0;
	private int step = 0;
	public Attribute() {
		super();
	}
	
	public Attribute(String shemeId, String memberId, String attrId, String attrName, double outputvalue, int step) {
		super();
		this.shemeId = shemeId;
		this.memberId = memberId;
		this.attrId = attrId;
		this.attrName = attrName;
		Outputvalue = outputvalue;
		this.step = step;
	}


	public String getShemeId() {
		return shemeId;
	}

	public void setShemeId(String shemeId) {
		this.shemeId = shemeId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getAttrId() {
		return attrId;
	}
	public void setAttrId(String attrId) {
		this.attrId = attrId;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public double getOutputvalue() {
		return Outputvalue;
	}
	public void setOutputvalue(double outputvalue) {
		Outputvalue = outputvalue;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}

	@Override
	public String toString() {
		return "Attribute [shemeId=" + shemeId + ", memberId=" + memberId + ", attrId=" + attrId + ", attrName="
				+ attrName + ", Outputvalue=" + Outputvalue + ", step=" + step + "]";
	}
}
