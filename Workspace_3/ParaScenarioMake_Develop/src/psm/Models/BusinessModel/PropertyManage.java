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
        		tableModel.addRow(new Object[] {"成员名",member.Name });
        		tableModel.addRow(new Object[] {"步长",member.Step});
        		tableModel.addRow(new Object[] {"是否时间受限",member.IsTimeConstrained});
        		tableModel.addRow(new Object[] {"是否时间调节",member.IsTimeRegulating});
        		tableModel.addRow(new Object[] {"是否已配置",member.IsDeploied});
        		tableModel.addRow(new Object[] {"引用模型",member.Model});
        		tableModel.addRow(new Object[] {"成员图片",member.pictureOfMember});
        	}
        }
        
        FrmMain.propertyTable.setModel(tableModel);
       
    }
	
	/**
	 * 通过点击树节点响应属性栏的信息更新
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
        		tableModel.addRow(new Object[] {"开发工具",model.Development_tools});
        		tableModel.addRow(new Object[] {"开发语言",model.Development_languages});
        		tableModel.addRow(new Object[] {"模型ID",model.Muid});
        		tableModel.addRow(new Object[] {"模型版本",model.Model_version});
        		tableModel.addRow(new Object[] {"模型发布时间",model.Publish_time});
        		tableModel.addRow(new Object[] {"模型类型",model.Cat_id});
        		tableModel.addRow(new Object[] {"模型介绍",model.Model_intro});
        		tableModel.addRow(new Object[] {"模型名",model.Model_name});
        		tableModel.addRow(new Object[] {"模型修改时间",model.Modified_time});
        		tableModel.addRow(new Object[] {"是否进行验证",model.Is_verified});
        		tableModel.addRow(new Object[] {"是否有文档",model.Hava_doc});
        		tableModel.addRow(new Object[] {"是否有源文件",model.Hava_src});
        		tableModel.addRow(new Object[] {"目标权限",model.Target_access});
        		tableModel.addRow(new Object[] {"目标所用Dll",model.Model_target});
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
