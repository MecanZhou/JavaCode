import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface InteractionImpl {
	//������
	public void createTable(String SCHEME_ID) throws ClassNotFoundException, SQLException;
	//��ô���������Ҫ������tableData��������
	public Object[][] getTableData();
	//��ô���������Ҫ������numColumns
	public String[] getNumColumns();
	//�������ݿ�
	public Connection getConnection(String username) throws ClassNotFoundException, SQLException;
	//���ݷ���ID����ø÷����ĳ�ԱID
	public ArrayList<String> getMemberID(String SCHEME_ID) throws ClassNotFoundException, SQLException;
	//����ԱID��������
	public void sortTableData(ArrayList<String> MUID) throws ClassNotFoundException, SQLException;
}
