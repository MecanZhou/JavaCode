import hla.rti.ConcurrentAccessAttempted;
import hla.rti.FederateNotExecutionMember;
import hla.rti.InteractionClassNotDefined;
import hla.rti.InteractionClassNotPublished;
import hla.rti.InteractionParameterNotDefined;
import hla.rti.InvalidFederationTime;
import hla.rti.RTIinternalError;
import hla.rti.RestoreInProgress;
import hla.rti.SaveInProgress;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class FrameResponse implements InteractionImpl{
	public static String initResult="";//���initPara��û�б����ĵ�����
	public static String result="";//���initPara�б�����֮�������
	CTLinkRTI rti = new CTLinkRTI();
	JFrame frame=new JFrame("�޸ĳ�Ա����");//���� �޸ĳ�Ա���� ����
	DefaultTableModel tmd;//�����ģ��
	//�����Ա����tableData��numColumns
	public static Object[][] tableData;
	String [] numColumns={"SCHEME_MODEL_ID","PARA_ID","PARA_TYPE","PARA_DEFA_VALUE","PARA_LABEL","PRAR_PHYSICSUNIT"};


	
	@Override
	public void createTable(String SCHEME_ID) throws ClassNotFoundException, SQLException {
		//���ô�������
		frame.setBounds(0, 0, 800, 400);//���ô����С
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);//���ô�����˳�
		frame.setLocationRelativeTo(null);	//���ô���λ����Ļ���м�
         //����getMemberID�������������ݷ���������÷���ID���������ķ���ֵ����arrayList
		ArrayList<String> arrayList=getMemberID(SCHEME_ID);
	    sortTableData(arrayList);//���մ�С�����˳������arrayList�е�����
	    //��ô�������������� tableDa ��numColums
        final Object [][] tableData=getTableData();
        final String [] numColumns=getNumColumns();
		JScrollPane scrollPane = new JScrollPane();  //����һ��������壬������table������ʹ�ã�
    	JPanel pane=new JPanel();//����һ����壬�����÷��ͽ�����ȡ����button
    	//��������button
		final JButton button1=new JButton("���ͽ���");
		final JButton button2=new JButton("ȡ��");
		
		//��button1��Ӽ����¼�  ���ͽ�����
		button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					//���tableû�б����Ļ��߸��ĺ��������ԭ������ͬ�����޷����ͽ�����
					if(!(result.equals(initResult))&&(!(result==""))){				
					rti.modifyPara(result);//���ͽ�����
					initResult=result;//���ͽ�����֮�󣬽�result����initResult
//					frame.dispose();//��ȡ���رմ���
					}
				} catch (InteractionClassNotDefined e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InteractionClassNotPublished e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InteractionParameterNotDefined e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvalidFederationTime e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FederateNotExecutionMember e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SaveInProgress e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RestoreInProgress e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (RTIinternalError e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ConcurrentAccessAttempted e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		//��button2���������¼������ȡ�����ر��޸ĳ�Ա��������
		button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();//��ȡ���رմ���
			}
		});
		//��button1 button2��ӵ����pane��
		pane.add(button1,BorderLayout.WEST);
		pane.add(button2,BorderLayout.EAST);
		//�����scrollpane pane��ӵ�������
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.getContentPane().add(pane,BorderLayout.SOUTH);
		//�������
		 tmd=new DefaultTableModel(tableData,numColumns){
	     //����ֻ�б������п��Ա��޸�       
		public boolean isCellEditable(int row, int column)
	      {
	                return column==3;
	      }       
		 };
	     JTable table = new JTable(tmd);
	     //��ȡ����һ�к͵����е����� �����������ӳ��ַ���
	     for (int i = 0; i < tableData.length; i++) 
			{
				initResult=initResult+"#"+tmd.getValueAt(i, 0).toString()+"-"+tmd.getValueAt(i, 3).toString();
			}
	     initResult=initResult.substring(1, initResult.length());//ȥ���ַ�����ͷ�ַ�#
			     System.out.println("��ʼ��initResult��         "+initResult);
		scrollPane.setViewportView(table);//����һ���ӿڣ�����б�Ҫ������������ͼ
		//�������ӱ��ı����
		tmd.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				//ͬ��    ��ȡ����һ�к͵����е����� �����������ӳ��ַ���
				result="";
				for (int i = 0; i < tableData.length; i++) {
					result=result+"#"+tmd.getValueAt(i, 0).toString()+"-"+tmd.getValueAt(i, 3).toString();
					}
				     result=result.substring(1, result.length());
				     
				System.out.println("table�ı��¼���       "+result);
			}
		});
		
		frame.show();
	}

	//getTableData����
	@Override
	public Object[][] getTableData() {
		// TODO Auto-generated method stub
		return tableData;
	}
    //getNumColumns����
	@Override
	public String[] getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}

	//����һ�����ݿ����Ӷ�����������ݿⷽ��
	@Override
	public Connection getConnection(String username) throws ClassNotFoundException, SQLException {
        String serverIP="172.16.73.110";
        //String username="MODELDB";
        String password="root";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@"+serverIP+":1521:xe", username, password);
		System.out.println("���ݿ�Oracle���ӳɹ�");
		return con;
	}

	//������飬�������SCHEME_MODEL_ID��MUID�Լ�����֮��Ķ�Ӧ��ϵ
	@Override
	public ArrayList<String> getMemberID(String SCHEME_ID) throws ClassNotFoundException, SQLException {
		ArrayList<String> MID=new ArrayList<String>();
		Connection con=getConnection("SCENARIODB");
		Statement stat=con.createStatement();
		String sql="select * from SCHEME_MODEL_INFO WHERE SCHEME_ID="+SCHEME_ID;
		ResultSet rs=stat.executeQuery(sql);
		while(rs.next()){
		MID.add(rs.getString("SCHEME_MODEL_ID")+"#"+rs.getShort("MUID"));}
	    con.close();
		return MID;
	}

	//��ð������tableDate����memberID����
	@Override
	public void sortTableData(ArrayList<String> MUID) throws ClassNotFoundException, SQLException {
		ArrayList<String> MemID=new ArrayList<String>();
		ArrayList<String> MuID=new ArrayList<String>();
		for (int i = 0; i < MUID.size(); i++) {
			MemID.add(MUID.get(i).split("#")[0]);
			MuID.add(MUID.get(i).split("#")[1]);
		}
		Connection con=getConnection("MODELDB");
	    Statement stat=con.createStatement();
	    String sqlStr = "select * from MODEL_PARA_INFO WHERE";
	    String sqlStr2="(MUID=";
	    String sqlStr1="";
	    int num=0;
	    	/*���ݿ��ѯ��䣬*/
	    System.out.println("MUID: "+MUID);
	    for (int i = 0; i < MuID.size(); i++)
	    {
	    	if(i==0)
	    	{
	    		 sqlStr1=MuID.get(i);
	    	}
	    	else sqlStr1=sqlStr1+" or MUID="+MuID.get(i);
		}
	    sqlStr=sqlStr+sqlStr2+sqlStr1+") and PARA_TYPE='init_para'";
//	    System.out.println(sqlStr);
	    ResultSet rsBegin=stat.executeQuery(sqlStr);
	   //��tableData���鸳ֵ
	    int i=0;
	    while(rsBegin.next()){i++;}// ��������i
	    num=i;
	    tableData = new Object[num][1];
	    ResultSet rs=stat.executeQuery(sqlStr);
	    String MemberID = null;
	    int number=0;
	    while(rs.next())
	    {
	    	String MUID_PARA=rs.getString("MUID");
	    	System.out.println(MUID_PARA);
	    	for (int i1 = 0; i1 < MuID.size(); i1++) {
	    		if (MUID_PARA.equals(MuID.get(i1))) {
	    			MemberID=MemID.get(i1);
				}
			}
	    	String PARA_ID=rs.getString("PARA_ID");
	    	String PARA_TYPE=rs.getString("PARA_TYPE");
	    	String PARA_DEFA_VALUE=rs.getString("PARA_DEFA_VALUE");
	    	if(PARA_DEFA_VALUE==null){PARA_DEFA_VALUE="NULL";}
	    	String PARA_LABEL=rs.getString("PARA_LABEL");
	    	String PRAR_PHYSICSUNIT=rs.getString("PRAR_PHYSICSUNIT");
	    	tableData[number]=new Object[]{MemberID,PARA_ID,PARA_TYPE,PARA_DEFA_VALUE,PARA_LABEL,PRAR_PHYSICSUNIT};    	
			number++;
	    }
	    //tableData�����е����ݰ��ճ�ԱID��С��������
	    for (int j = 0; j < tableData.length; j++) {
		for (int k=j+1;k<tableData.length;k++){
			if(Integer.parseInt((String)(tableData[j][0]))>Integer.parseInt((String)(tableData[k][0])))
			{
				Object[] p;
				p=tableData[j];
				tableData[j]=tableData[k];
				tableData[k]=p;
			}
		}
	    }
	    for (int j = 0; j < tableData.length; j++) {
			System.out.println("88888      "+tableData[0][j]);
			System.out.println("88888      "+tableData[1][j]);
		}
	    con.close();
}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		FrameResponse response=new FrameResponse();
		response.createTable("23");
	}
	}