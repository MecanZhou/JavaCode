package psm.Models.BusinessModel;

import psm.Models.DataModel.Formula;

public class FormulaManage implements InterfaceFormulaManage{

    /*
     * 整个系统只允许一个活动方案
     */
	//[start] Fields
    public static Formula formula=new Formula();
    //[end]
    
    public static void setFormula(Formula formula_)
    {
    	formula=formula_;
    }
    public static void setFormula(String markInfo,String strformula)
    {
    	if(markInfo=="Name")
    	{
    	    formula.Name=strformula;
    	}
    	if(markInfo=="Author")
    	{
    		formula.Author=strformula;
    	}
    	if(markInfo=="Goal")
    	{
    		formula.Goal=strformula;
    	}
    	if(markInfo=="Scale")
    	{
    		formula.Scale=strformula;
    	}
    	if(markInfo=="Outline")
    	{
    		formula.Outline=strformula;
    	}
    }
    public static Formula getFormula()
    {
    	return formula;
    }
}
