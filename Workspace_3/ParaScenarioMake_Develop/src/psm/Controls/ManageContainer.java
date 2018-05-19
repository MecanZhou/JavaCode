package psm.Controls;

import java.util.Hashtable;

import psm.Models.BusinessModel.CombineMemberManage;
import psm.Models.BusinessModel.DbManage;
import psm.Models.BusinessModel.FileManage;
import psm.Models.BusinessModel.FormulaManage;
import psm.Models.BusinessModel.MemberManage;
import psm.Models.BusinessModel.ModelManage;
import psm.Models.BusinessModel.PictureOfMemberManage;
import psm.Models.BusinessModel.RelationManage;
import psm.Models.DataModel.Formula;
import psm.Models.DataModel.LineManage;
import psm.Models.DataModel.MemberObject;

public class ManageContainer {
	public static Hashtable<String, Object> hashTable = new Hashtable<String, Object>();
	/// <summary>
    /// ��ʼ��hashTable
    /// </summary>
	public ManageContainer()
    {
		CombineMemberManage  combineManage= new CombineMemberManage();
        DbManage dbManage = new DbManage();
        FileManage fileManage = new FileManage();
        FormulaManage formulaManage = new FormulaManage();
        LineManage lineManage = new LineManage();
        MemberManage memberManage = new MemberManage();
        ModelManage modelManage = new ModelManage();
        PictureOfMemberManage pOMManage = new PictureOfMemberManage();
        RelationManage relationManage = new RelationManage();

        hashTable.put("CombineMemberManage", combineManage);
        hashTable.put("DbManage", dbManage);
        hashTable.put("FileManage", fileManage);
        hashTable.put("FormulaManage", formulaManage);
        hashTable.put("LineManage", lineManage);
        hashTable.put("MemberManage", memberManage);
        hashTable.put("ModelManage", modelManage);
        hashTable.put("PictureOfMemberManage", pOMManage);
        hashTable.put("RelationManage", relationManage);
    }
	

    /// <summary>
    /// ������������ѯ��Ӧ�Ķ���
    /// </summary>
    /// <param name="type">������</param>
    /// <returns>��ѯ�õ��Ķ���</returns>
    public static Object GetObject(String type)
    {
		Object obj = new Object();

		if (hashTable.get(type) != null) {
			obj = hashTable.get(type);
		} else
			obj = null;
		return obj;
    }
    
    /// <summary>
    /// ��������
    /// </summary>
    public static void InitContainer()
    {
        DbManage dbManage = new DbManage();
        FileManage fileManage = new FileManage();
        FormulaManage formulaManage = new FormulaManage();
        LineManage lineManage = new LineManage();
        MemberManage memberManage = new MemberManage();
        ModelManage modelManage = new ModelManage();
        PictureOfMemberManage pOMManage = new PictureOfMemberManage();
        MemberObject memberObject = new MemberObject();
        
        
        hashTable.clear(); 
        hashTable.put("DbManage", dbManage);
        hashTable.put("FileManage", fileManage);
        hashTable.put("FormulaManage", formulaManage);
        hashTable.put("LineManage", lineManage);
        hashTable.put("MemberManage", memberManage);
        hashTable.put("ModelManage", modelManage);
        hashTable.put("PictureOfMemberManage", pOMManage);
        hashTable.put("MemberObject", memberObject); 
    }

}


