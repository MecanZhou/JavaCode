package psm.Models.DataModel;

import java.io.Serializable;

public class Attribute implements Serializable 
{
	public boolean isInput;
	public boolean isOutput;
	public boolean isInit;
	public boolean isModel;
	
	
	public Attribute(){
		isInput=false;
		isOutput=false;
		isInit=false;
		isModel=false;
	}
}
