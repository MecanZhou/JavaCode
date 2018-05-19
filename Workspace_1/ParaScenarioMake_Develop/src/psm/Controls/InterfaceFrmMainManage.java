package psm.Controls;

import java.io.IOException;

import javax.swing.JFrame;

import psm.Models.DataModel.Formula;


public interface InterfaceFrmMainManage 
{
	//保存预案信息
    public int DoSaveFormula(JFrame frame) throws IOException;
    
    public int DoOpenFormula(JFrame frame) throws IOException, ClassNotFoundException;
    
    //发布预案
    public int DoPublishFormula();
    //上传方案
    public int DoUploadScheme();

}
