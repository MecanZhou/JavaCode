package psm.Models.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface InterfaceDBHelper 
{
	//执行查询操作
	public ResultSet ExecuteQuery(String sql) throws SQLException;	
	//执行插入操作
	public void ExecuteInsert(String sql) throws SQLException;
	//执行删除操作
	public void ExecyteDelete(String sql) throws SQLException;
}
