package bean;

public class RunningData {
	private String Member;
	private double time;
	private String value;
	private int runid;
	public String getMember() {
		return Member;
	}
	public int getRunid() {
		return runid;
	}
	public double getTime() {
		return time;
	}
	public String getValue() {
		return value;
	}
	public void setMember(String member) {
		Member = member;
	}
	public void setRunid(int runid) {
		this.runid = runid;
	}
	public void setTime(double time) {
		this.time = time;
	}
	public void setValue(String value) {
		this.value = value;
	}

}
