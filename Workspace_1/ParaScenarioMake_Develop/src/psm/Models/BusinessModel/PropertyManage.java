package psm.Models.BusinessModel;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import psm.Models.DataModel.Member;
import psm.Models.DataModel.Model;
import psm.Views.FrmMain;

public class PropertyManage {
	
	public void tableMemberChange(MouseEvent e,String nodeName) {
		DefaultTableModel tableModel = new DefaultTableModel();
		tableModel.setRowCount(0);
		tableModel.setColumnIdentifiers(new Object[] { "", "" });
        for (Member member: MemberManage.MemberList){
        	if((member.Name).equals(nodeName))
        	//if(String.valueOf(model.hashCode())==nodeName)
        	{
        		tableModel.addRow(new Object[] {"ID",member.Id});
        		tableModel.addRow(new Object[] {"��Ա��",member.Name });
        		tableModel.addRow(new Object[] {"����",member.Step});
        		tableModel.addRow(new Object[] {"�Ƿ�ʱ������",member.IsTimeConstrained});
        		tableModel.addRow(new Object[] {"�Ƿ�ʱ�����",member.IsTimeRegulating});
        		tableModel.addRow(new Object[] {"�Ƿ�������",member.IsDeploied});
        		tableModel.addRow(new Object[] {"����ģ��",member.Model});
        		tableModel.addRow(new Object[] {"��ԱͼƬ",member.pictureOfMember});
        	}
        }
        
        FrmMain.propertyTable.setModel(tableModel);
       
    }
	
	/**
	 * ͨ��������ڵ���Ӧ����������Ϣ����
	 * @param e
	 * @param nodeName
	 */
	public void tableModelChange(TreeSelectionEvent e,String nodeName) {
        
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[] { "", "" });       
        for (Model model: ModelManage.ModelList){
        	if((model.Model_name+model.Muid).equals(nodeName))
        	//if(String.valueOf(model.hashCode())==nodeName)
        	{
        		tableModel.addRow(new Object[] {"��������",model.Development_tools});
        		tableModel.addRow(new Object[] {"��������",model.Development_languages});
        		tableModel.addRow(new Object[] {"ģ��ID",model.Muid});
        		tableModel.addRow(new Object[] {"ģ�Ͱ汾",model.Model_version});
        		tableModel.addRow(new Object[] {"ģ�ͷ���ʱ��",model.Publish_time});
        		tableModel.addRow(new Object[] {"ģ������",model.Cat_id});
        		tableModel.addRow(new Object[] {"ģ�ͽ���",model.Model_intro});
        		tableModel.addRow(new Object[] {"ģ����",model.Model_name});
        		tableModel.addRow(new Object[] {"ģ���޸�ʱ��",model.Modified_time});
        		tableModel.addRow(new Object[] {"�Ƿ������֤",model.Is_verified});
        		tableModel.addRow(new Object[] {"�Ƿ����ĵ�",model.Hava_doc});
        		tableModel.addRow(new Object[] {"�Ƿ���Դ�ļ�",model.Hava_src});
        		tableModel.addRow(new Object[] {"Ŀ��Ȩ��",model.Target_access});
        		tableModel.addRow(new Object[] {"Ŀ������Dll",model.Model_target});
        	}
        }
        
        FrmMain.propertyTable.setModel(tableModel);
        
        
    }
	public boolean isCellEditable(int rowIndex,int columnIndex){
		if(columnIndex==1){
			return false;
		}
		return true;
	}
}
