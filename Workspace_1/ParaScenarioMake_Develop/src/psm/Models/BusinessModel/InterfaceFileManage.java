package psm.Models.BusinessModel;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;

import psm.Models.DataModel.CombineMember;
import psm.Models.DataModel.Formula;


public interface InterfaceFileManage {
	
	/**
	 * 创建FOM.xml文件
	 */
	public void CreateFomXml();
	
	/**
	 * 创建Config.xml文件
	 */
	public void CreateSomXml();
	
	public void CreateCombineMembersXml(CombineMember combineMember);
	
	public CombineMember GetCombineMemberXML(String XMLPath);
	
	/**
	 * 保存预案信息
	 * @param 需要保存的方案
	 * @param 指定的对话框操作方法
	 * @param 文件保存路径
	 * @throws IOException 
	 */

	public void DoSaveAction(Formula formula_,String strpath) throws IOException;

	public Formula DoOpenAction(String absolutePath) throws FileNotFoundException, IOException, ClassNotFoundException;
	
	public void UploadScheme(String FileName);

	
	
}
