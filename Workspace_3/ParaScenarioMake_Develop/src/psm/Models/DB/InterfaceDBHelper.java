package psm.Models.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface InterfaceDBHelper 
{
	//ִ�в�ѯ����
	public ResultSet ExecuteQuery(String sql) throws SQLException;	
	//ִ�в������
	public void ExecuteInsert(String sql) throws SQLException;
	//ִ��ɾ������
	public void ExecyteDelete(String sql) throws SQLException;
}
