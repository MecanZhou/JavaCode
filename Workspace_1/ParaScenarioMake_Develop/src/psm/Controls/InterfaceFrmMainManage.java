package psm.Controls;

import java.io.IOException;

import javax.swing.JFrame;

import psm.Models.DataModel.Formula;


public interface InterfaceFrmMainManage 
{
	//����Ԥ����Ϣ
    public int DoSaveFormula(JFrame frame) throws IOException;
    
    public int DoOpenFormula(JFrame frame) throws IOException, ClassNotFoundException;
    
    //����Ԥ��
    public int DoPublishFormula();
    //�ϴ�����
    public int DoUploadScheme();

}
