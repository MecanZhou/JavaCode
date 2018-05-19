package psm.Models.BusinessModel;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFileChooser;

import psm.Models.DataModel.CombineMember;
import psm.Models.DataModel.Formula;


public interface InterfaceFileManage {
	
	/**
	 * ����FOM.xml�ļ�
	 */
	public void CreateFomXml();
	
	/**
	 * ����Config.xml�ļ�
	 */
	public void CreateSomXml();
	
	public void CreateCombineMembersXml(CombineMember combineMember);
	
	public CombineMember GetCombineMemberXML(String XMLPath);
	
	/**
	 * ����Ԥ����Ϣ
	 * @param ��Ҫ����ķ���
	 * @param ָ���ĶԻ����������
	 * @param �ļ�����·��
	 * @throws IOException 
	 */

	public void DoSaveAction(Formula formula_,String strpath) throws IOException;

	public Formula DoOpenAction(String absolutePath) throws FileNotFoundException, IOException, ClassNotFoundException;
	
	public void UploadScheme(String FileName);

	
	
}
