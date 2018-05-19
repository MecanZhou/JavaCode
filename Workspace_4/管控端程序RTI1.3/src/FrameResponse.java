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
	public static String initResult="";//表格initPara中没有被更改的数据
	public static String result="";//表格initPara中被更改之后的数据
	CTLinkRTI rti = new CTLinkRTI();
	JFrame frame=new JFrame("修改成员参数");//创建 修改成员参数 窗体
	DefaultTableModel tmd;//定义表模型
	//定义成员变量tableData和numColumns
	public static Object[][] tableData;
	String [] numColumns={"SCHEME_MODEL_ID","PARA_ID","PARA_TYPE","PARA_DEFA_VALUE","PARA_LABEL","PRAR_PHYSICSUNIT"};


	
	@Override
	public void createTable(String SCHEME_ID) throws ClassNotFoundException, SQLException {
		//设置窗体属性
		frame.setBounds(0, 0, 800, 400);//设置窗体大小
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);//设置窗体可退出
		frame.setLocationRelativeTo(null);	//设置窗体位于屏幕正中间
         //调用getMemberID（）函数，根据方案名，获得方案ID，并将它的返回值赋给arrayList
		ArrayList<String> arrayList=getMemberID(SCHEME_ID);
	    sortTableData(arrayList);//按照从小到大的顺序整理arrayList中的数据
	    //获得创建表的两条数据 tableDa 和numColums
        final Object [][] tableData=getTableData();
        final String [] numColumns=getNumColumns();
		JScrollPane scrollPane = new JScrollPane();  //创建一个滚动面板，来放置table（配套使用）
    	JPanel pane=new JPanel();//创建一个面板，来放置发送交互和取消的button
    	//创建两个button
		final JButton button1=new JButton("发送交互");
		final JButton button2=new JButton("取消");
		
		//给button1添加监听事件  发送交互类
		button1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					//如果table没有被更改或者更改后的数据与原数据相同，则无法发送交互类
					if(!(result.equals(initResult))&&(!(result==""))){				
					rti.modifyPara(result);//发送交互类
					initResult=result;//发送交互类之后，将result赋给initResult
//					frame.dispose();//按取消关闭窗体
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
		//给button2添加鼠标点击事件，点击取消，关闭修改成员参数窗体
		button2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frame.dispose();//按取消关闭窗体
			}
		});
		//将button1 button2添加到面板pane中
		pane.add(button1,BorderLayout.WEST);
		pane.add(button2,BorderLayout.EAST);
		//将面板scrollpane pane添加到窗体中
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.getContentPane().add(pane,BorderLayout.SOUTH);
		//创建表格
		 tmd=new DefaultTableModel(tableData,numColumns){
	     //设置只有表格第四列可以被修改       
		public boolean isCellEditable(int row, int column)
	      {
	                return column==3;
	      }       
		 };
	     JTable table = new JTable(tmd);
	     //读取表格第一列和第四列的数据 并将他们连接成字符串
	     for (int i = 0; i < tableData.length; i++) 
			{
				initResult=initResult+"#"+tmd.getValueAt(i, 0).toString()+"-"+tmd.getValueAt(i, 3).toString();
			}
	     initResult=initResult.substring(1, initResult.length());//去掉字符串开头字符#
			     System.out.println("初始化initResult：         "+initResult);
		scrollPane.setViewportView(table);//创建一个视口（如果有必要）并设置其视图
		//给表格添加表格改变监听
		tmd.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				//同上    读取表格第一列和第四列的数据 并将他们连接成字符串
				result="";
				for (int i = 0; i < tableData.length; i++) {
					result=result+"#"+tmd.getValueAt(i, 0).toString()+"-"+tmd.getValueAt(i, 3).toString();
					}
				     result=result.substring(1, result.length());
				     
				System.out.println("table改变事件：       "+result);
			}
		});
		
		frame.show();
	}

	//getTableData（）
	@Override
	public Object[][] getTableData() {
		// TODO Auto-generated method stub
		return tableData;
	}
    //getNumColumns（）
	@Override
	public String[] getNumColumns() {
		// TODO Auto-generated method stub
		return numColumns;
	}

	//返回一个数据库连接对象的连接数据库方法
	@Override
	public Connection getConnection(String username) throws ClassNotFoundException, SQLException {
        String serverIP="172.16.73.110";
        //String username="MODELDB";
        String password="root";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection con = DriverManager.getConnection("jdbc:oracle:thin:@"+serverIP+":1521:xe", username, password);
		System.out.println("数据库Oracle连接成功");
		return con;
	}

	//获得数组，数组包含SCHEME_MODEL_ID、MUID以及他们之间的对应关系
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

	//用冒泡排序将tableDate按照memberID排序
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
	    	/*数据库查询语句，*/
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
	   //给tableData数组赋值
	    int i=0;
	    while(rsBegin.next()){i++;}// 表格的行数i
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
	    //tableData数组中的数据按照成员ID从小到大排序
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