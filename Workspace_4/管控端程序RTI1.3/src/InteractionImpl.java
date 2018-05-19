import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InteractionImpl {
	//创建表
	public void createTable(String SCHEME_ID) throws ClassNotFoundException, SQLException;
	//获得创建表所需要的数据tableData【】【】
	public Object[][] getTableData();
	//获得创建表所需要的数据numColumns
	public String[] getNumColumns();
	//连接数据库
	public Connection getConnection(String username) throws ClassNotFoundException, SQLException;
	//根据方案ID，获得该方案的成员ID
	public ArrayList<String> getMemberID(String SCHEME_ID) throws ClassNotFoundException, SQLException;
	//将成员ID进行排序
	public void sortTableData(ArrayList<String> MUID) throws ClassNotFoundException, SQLException;
}
