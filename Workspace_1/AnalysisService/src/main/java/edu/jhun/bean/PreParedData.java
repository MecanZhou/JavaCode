package edu.jhun.bean;

/*
 * 数据库取出的数据，（步长，值）
 */
public class PreParedData {

	private double ouputValue;
	private int step;
	
	
	public PreParedData()
	{
		
	}
	
	public PreParedData(double ouputValue, int step) {
		super();
		this.ouputValue = ouputValue;
		this.step = step;
	}

	public double getOuputValue() {
		return ouputValue;
	}
	public void setOuputValue(double ouputValue) {
		this.ouputValue = ouputValue;
	}
	public int getStep() {
		return step;
	}
	public void setStep(int step) {
		this.step = step;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "值："+ouputValue+"  步长"+step;
	}
}
