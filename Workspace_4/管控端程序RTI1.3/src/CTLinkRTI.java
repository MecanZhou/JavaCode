import hla.rti.AttributeHandleSet;
import hla.rti.ConcurrentAccessAttempted;
import hla.rti.EnableTimeConstrainedPending;
import hla.rti.EnableTimeRegulationPending;
import hla.rti.EventRetractionHandle;
import hla.rti.FederateNotExecutionMember;
import hla.rti.FederationTimeAlreadyPassed;
import hla.rti.IllegalTimeArithmetic;
import hla.rti.InteractionClassNotDefined;
import hla.rti.InteractionClassNotPublished;
import hla.rti.InteractionParameterNotDefined;
import hla.rti.InvalidFederationTime;
import hla.rti.LogicalTime;
import hla.rti.LogicalTimeInterval;
import hla.rti.RTIambassador;
import hla.rti.RTIinternalError;
import hla.rti.ReceivedInteraction;
import hla.rti.ReflectedAttributes;
import hla.rti.RestoreInProgress;
import hla.rti.SaveInProgress;
import hla.rti.SuppliedParameters;
import hla.rti.TimeAdvanceAlreadyInProgress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import bean.RunningData;


import se.pitch.prti.FederateAmbassador_Impl;
import se.pitch.prti.LogicalTimeDouble;
import se.pitch.prti.LogicalTimeIntervalDouble;
import se.pitch.prti.RTI;


public class CTLinkRTI extends FederateAmbassador_Impl{
	private static RTIambassador _rtiAmbassador;
	private static int _Start;//开始按钮的交互类句柄
	//	private static int _StartSender;//Start的发送者参数句柄
	private static int _StartMessage;//Start的发送消息的参数句柄
	private static int _ReplyStart;
	private static int _RSMessage;
	private static int _Pause;//暂停按钮的交互类句柄
	private static int ModifyPara;//修改仿真参数
	//	private static int _PauseSender;//pause的发送者的参数句柄
	private static int _PauseMessage;//pause的发送消息的参数句柄
	private static int MPMessage;
	private static int _ReplyPause;
	private static int _RPMessage;
	private static int _Continue;//继续按钮的交互类句柄
	//	private static int _ContinueSender;//Continue的发送者的参数句柄
	private static int _ContinueMessage;//Continue的发送消息的参数句柄
	private static int _ReplyContinue;
	private static int _RCMessage;
	private static int _Quit;//停止按钮的发送交互类句柄
	//	private static int _StopSender;//Stop的发送者参数句柄
	private static int _QuitMessage;//Stop的发送消息的参数句柄
	private static int _ReplyQuit;
	private static int _RQMessage;
	private static int _Refresh;//刷新按钮的交互类句柄
	//	private static int _RefreshSender;//刷新的发送者的参数句柄
	private static int _RefreshMessage;//刷新的发送消息的参数句柄
	private static int _ReplyRefresh;
	private static int _RRMessage;
	private static int _TimerUpdate;//定时器的交互类句柄
	private static int _TimerUpdateMessage;//定时器的消息参数句柄
	private static int _ReplyTimerUpdate;//回调定时器的交互类句柄
	private static int _ReplyTimerUpdateMessage;//回调定时器的消息参数句柄

	private static int _DelayUpdate;//延迟更新的交互类句柄
	private static int _DelayUpdateMessage;//延迟更新的消息参数句柄
	private static int _ReplyDelayUpdate;//回调延迟更新的交互类句柄
	private static int _ReplyDelayUpdateMessage;//回调延迟更新的消息参数句柄

	private static ArrayList<Integer> _objclass=new ArrayList<Integer>();
	private static ArrayList<Integer>_obj_para=new ArrayList<Integer>();
	private static ArrayList<Integer> _objpara=new ArrayList<Integer>();
	private static ArrayList<Integer>ObjectID=new ArrayList<Integer>();
	private static ArrayList<String>ObjectName=new ArrayList<String>();
	private HashMap _participants = new HashMap();//成员的hashmap
	public static String name =null;
	public ArrayList<RunningData>runningdata=new ArrayList<RunningData>();
	int datacount=0;
	public static int TimePercentage;
	public	static int RTIID=0;
	public static int FedID=0;
	public static String FedName=null;
	public static int RunID=0;
	public static int FedNum=0;
	public static int i=0;
	public static Connection con=CTReadDB.getConnection();
	Statement st=null;
	public static boolean rtiflag=false;
	public long time1=0;
	public long time2=0;
	public boolean timeflag=true;
	public PreparedStatement pst;
	public static String chartname=null;
	public static long TimerUpdate=0;
	public static long AutoDelay=0;
	public static Timer timer;
	public static SuppliedParameters parameters1=RTI.suppliedParametersFactory().create(1);
	public boolean timeadvance=true;
	public boolean timeadvance1=false;
	public boolean timeadvance2=false;
	public boolean timeadvance3=false;
	public boolean continueflag=true;
	LogicalTime currentTime;
	LogicalTimeInterval lookhead;
	public static void Startrun(String FederateName,int fed)
	{
		Connection conn=CTReadDB.getConnection();
		FedName=FederateName;
		FedNum=fed;
		AutoDelay=CTReadFOM.timeDelay;
		TimerUpdate=CTReadFOM.timeDelay;
		System.out.println("TimerUpdate"+TimerUpdate+"AAAAAAAAAAAAAAAAAAa");
		TimePercentage=ControlTerminal.TimePercentage;
		System.out.println("TimeDelay------"+TimerUpdate);
		System.out.println(FedName+" "+FedNum);
		String sql="select * from scheme_desc_info where SCHEME_NAME = 'newtest'";
		try {
			Statement st=(Statement) con.createStatement();
			ResultSet rs=st.executeQuery(sql);

			while(rs.next())
			{
				FedID=rs.getInt(1);
			}
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//			df.format(new)
			String time=df.format(new Date());
			//			System.out.println(df.format(new Date()));
			sql="insert into scheme_running_log values("+FedID+",'"+FedName+"','"+time+"',null)";
			System.out.println(sql);
			st.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
			rs=st.getGeneratedKeys();
			if(rs.next())
			{
				RunID=rs.getInt(1);
				System.out.println("runid: "+RunID);
			}
			//			System.out.println("key: "+st.getGeneratedKeys().toString());
			System.out.println("OK");
		} catch (SQLException e) {
			// TODO: handle exception
		}
		new CTLinkRTI().run();
	}

	@SuppressWarnings("unchecked")
	public  void run()
	{
		try {

			try {
				pst.clearBatch();
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println("管控端仿真启动......");
			//String rtiHost="172.16.73.60";//联邦的IP地址
			File file1=new File("ServerIP.txt");
			BufferedReader reader=new BufferedReader(new FileReader(file1));
			String rtiHost=reader.readLine();
			System.out.println(rtiHost);
			_rtiAmbassador=RTI.getRTIambassador(rtiHost, 8989);

			name=FedName;

			System.out.println("联邦名称 :......"+name);
			///////////////
			_rtiAmbassador.createFederationExecution(FedName, new File("AutoControlFED.fed").toURI().toURL());//这里的方法要修改为rti1.3的方法
			///////////////
			System.out.println("创建联邦成功");
			CTLinkRTI.CreateFedTable(FedName);
			
			//_rtiAmbassador.resignFederationExecution(_rtiAmbassador.createFederationExecution("CommandTest", new File("FED.fed").toURI().toURL()));
			System.out.println("执行");
			_rtiAmbassador.joinFederationExecution("CommandCenter",name, this);
			System.out.println("加入联邦");
			System.out.println("size: "+CTReadFOM.objParameters.size());
			ArrayList<objectClass> parameter=CTReadFOM.objParameters;
			for(int i=0;i<parameter.size();i++)
			{
				System.out.println("对象类："+parameter.get(i).getName());
			}
			for(int i=0;i<parameter.size();i++)//订购对象类
			{
				int _obj=_rtiAmbassador.getObjectClassHandle(parameter.get(i).getName());
				System.out.println("对象类： "+parameter.get(i).getName());
				AttributeHandleSet attribute=RTI.attributeHandleSetFactory().create();
				for(int j=0;j<parameter.get(i).getValue().size();j++)
				{				
					System.out.println(parameter.get(i).getValue().get(j));
					int _objparameter=_rtiAmbassador.getAttributeHandle(parameter.get(i).getValue().get(j), _obj);
					_objpara.add(_objparameter);
					System.out.println("_objparameter"+_objparameter);
					_obj_para.add(_obj);
					attribute.add(_objparameter);				
					System.out.println("订购对象类："+attribute);
				}
				System.out.println("订购对象类句柄"+attribute);
				_rtiAmbassador.subscribeObjectClassAttributes(_obj, attribute);
				System.out.println("订购对象类句柄完成"+attribute);
				attribute.empty();
				System.out.println("清空对象类句柄");
				_objclass.add(_obj);
				System.out.println("加入Obj句柄"+_obj);
				//			_rtiAmbassador.subscribeObjectClassAttributes(_obj);
			}
			System.out.println("检测对象类");
			_Start=_rtiAmbassador.getInteractionClassHandle("Start");
			_StartMessage=_rtiAmbassador.getParameterHandle("SMessage", _Start);
			System.out.println("开始公布交互类Start");
			
			_ReplyStart=_rtiAmbassador.getInteractionClassHandle("ReplyStart");
			_RSMessage=_rtiAmbassador.getParameterHandle("RSMessage",_ReplyStart);
			//******
			_Pause=_rtiAmbassador.getInteractionClassHandle("Pause");
			System.out.println("99999999999999999999999999999999999999999999999999999");
			ModifyPara=_rtiAmbassador.getInteractionClassHandle("Modify");
System.out.println("获取交互类句柄：     "+ModifyPara);
			_PauseMessage=_rtiAmbassador.getParameterHandle("PMessage", _Pause);
			MPMessage=_rtiAmbassador.getParameterHandle("MPMessage", ModifyPara);
System.out.println("获取交互类参数句柄：  "+MPMessage);
			_ReplyPause=_rtiAmbassador.getInteractionClassHandle("ReplyPause");
			_RPMessage=_rtiAmbassador.getParameterHandle("RPMessage", _ReplyPause);

			_Continue=_rtiAmbassador.getInteractionClassHandle("Continue");
			_ContinueMessage=_rtiAmbassador.getParameterHandle("CMessage", _Continue);

			_ReplyContinue=_rtiAmbassador.getInteractionClassHandle("ReplyContinue");
			_RCMessage=_rtiAmbassador.getParameterHandle("RCMessage", _ReplyContinue);

			_Quit=_rtiAmbassador.getInteractionClassHandle("Quit");
			_QuitMessage=_rtiAmbassador.getParameterHandle("QMessage", _Quit);

			_ReplyQuit=_rtiAmbassador.getInteractionClassHandle("ReplyQuit");
			_RQMessage=_rtiAmbassador.getParameterHandle("RQMessage", _ReplyQuit);


			_Refresh=_rtiAmbassador.getInteractionClassHandle("Refresh");
			_RefreshMessage=_rtiAmbassador.getParameterHandle("RMessage", _Refresh);

			_ReplyRefresh=_rtiAmbassador.getInteractionClassHandle("ReplyRefresh");
			_RRMessage=_rtiAmbassador.getParameterHandle("RRMessage", _ReplyRefresh);

			_TimerUpdate=_rtiAmbassador.getInteractionClassHandle("TimerUpdate");
			_TimerUpdateMessage=_rtiAmbassador.getParameterHandle("TMessage", _TimerUpdate);


			_ReplyTimerUpdate=_rtiAmbassador.getInteractionClassHandle("ReplyTimerUpdate");
			_ReplyTimerUpdateMessage=_rtiAmbassador.getParameterHandle("RTMessage", _ReplyTimerUpdate);

			_DelayUpdate=_rtiAmbassador.getInteractionClassHandle("DelayUpdate");
			_DelayUpdateMessage=_rtiAmbassador.getParameterHandle("DMessage", _DelayUpdate);

			_ReplyDelayUpdate=_rtiAmbassador.getInteractionClassHandle("ReplyDelayUpdate");
			_ReplyDelayUpdateMessage=_rtiAmbassador.getParameterHandle("RDMessage", _ReplyDelayUpdate);

			_rtiAmbassador.publishInteractionClass(_Start);
			System.out.println("公布Start交互类成功");
			_rtiAmbassador.publishInteractionClass(_Pause);
			System.out.println("公布交互类             --------------------------------------------");
			_rtiAmbassador.publishInteractionClass(ModifyPara);
			System.out.println("公布交互类aaaaaaaaaaaa成功   ---------------------------------------------");
			_rtiAmbassador.publishInteractionClass(_Continue);
			_rtiAmbassador.publishInteractionClass(_Quit);
			_rtiAmbassador.publishInteractionClass(_Refresh);
			_rtiAmbassador.publishInteractionClass(_TimerUpdate);
			_rtiAmbassador.publishInteractionClass(_DelayUpdate);
			_rtiAmbassador.subscribeInteractionClass(_ReplyStart);
			_rtiAmbassador.subscribeInteractionClass(_ReplyPause);
			_rtiAmbassador.subscribeInteractionClass(_ReplyContinue);
			_rtiAmbassador.subscribeInteractionClass(_ReplyQuit);
			_rtiAmbassador.subscribeInteractionClass(_ReplyRefresh);
			_rtiAmbassador.subscribeInteractionClass(_ReplyTimerUpdate);
			_rtiAmbassador.subscribeInteractionClass(_ReplyDelayUpdate);
			
			
			 lookhead=new LogicalTimeIntervalDouble(0.1);
	         currentTime = new LogicalTimeDouble(0.0);
	         //_rtiAmbassador.enableTimeRegulation(currentTime,lookhead);
	         _rtiAmbassador.disableTimeConstrained();
	         _rtiAmbassador.disableTimeConstrained();
	         
//			while(true)
//			{
//				System.out.println("while .......................");
//				if(timeadvance==true&&timeadvance1==true&&timeadvance2==true)
//				{
//					currentTime.increaseBy(lookhead);
//						timeadvance=false;
//						  _rtiAmbassador.timeAdvanceRequest(currentTime);
//						  Thread.sleep(1000);
//				}
//				Thread.sleep(1000);
//			}
	
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	private static void CreateFedTable(String fedName) throws SQLException {
		
		Connection conn=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("获取数据库驱动失败，请配置数据库驱动路径");
		}
		try {
			conn=DriverManager.getConnection("jdbc:mysql://"+"172.16.73.110"+":3306/"+"data_analysis","root","root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("连接模型分析库失败，请调整模型中的连接IP");
		}
		ResultSet rs1=conn.getMetaData().getTables(null, null,fedName, null );//判断表是否存在，存在则不创建，不存在就创建
		Statement st=conn.createStatement();
		String sql="create table "+fedName+
				"(FederateName varchar(20), " +
				"ModelID varchar(20)," +
				"RID int(20),"+
				"Value varchar(20)," +
				"RunningTime varchar(50))";
		if(!rs1.next())//如果数据库中没有当前联邦名的表单，则创建
		{
			st.execute(sql);
			System.out.println("创建表成功");
		}		
		conn.close();
		
	}

	public void resignFederation() {
		try {
			SuppliedParameters parameters=RTI.suppliedParametersFactory().create(1);
			String message="QMessage";
			byte[] value=message.getBytes();
			parameters.add(_QuitMessage, value);
			//				parameters.add(_ContinueSender, encodeInt(0));
			_rtiAmbassador.sendInteraction(_Quit, parameters, null);
			Thread.sleep(5000);
			System.out.println("正在执行销毁联邦");
			//timer.cancel();
			_rtiAmbassador.resignFederationExecution(1);
			System.out.println(name);
			_rtiAmbassador.destroyFederationExecution(name);
			System.out.println("销毁联邦执行成功");
			try {

				for( int i=0;i< runningdata.size();i++)
				{

					pst.setString(1, runningdata.get(i).getMember());
					pst.setDouble(2, runningdata.get(i).getTime());
					pst.setString(3, runningdata.get(i).getValue());
					pst.setInt(4, runningdata.get(i).getRunid());
					pst.addBatch();
				}
				pst.executeBatch();

				runningdata.clear();

			} catch (SQLException exception) {
				// TODO: handle exception

			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public void start() throws InteractionClassNotDefined, InteractionClassNotPublished, InteractionParameterNotDefined, FederateNotExecutionMember, SaveInProgress, RestoreInProgress, RTIinternalError, ConcurrentAccessAttempted {
		
		SuppliedParameters parameters=RTI.suppliedParametersFactory().create(1);
		//SuppliedParameters parameters= RTI.suppliedParametersFactory().create(1);
		long timerupdate=TimerUpdate;
		System.out.println("TimerUpdate:"+TimerUpdate+"BBBBBBBBBBBBBBBBBBBBB");
		String message =String.valueOf(timerupdate);
		//String message="StartAA";
		byte[] value=message.getBytes();
		parameters.add(_StartMessage, value);
		//			parameters.add(_PauseSender, encodeInt(0));
			System.out.println("start");
		_rtiAmbassador.sendInteraction(_Start,parameters,null);
		System.out.println("成功向通用成员发送Start交互"+parameters);
		System.out.println("成功发送交互类，Start管控端");
		
		
	}
//	private void timerUpdate() {
//		timer=new Timer();
//		Date time=new Date();
//		//long timerupdate=TimerUpdate;/////////////////////////////////////////////////////////////////
//		long timerupdate=1000;
//		//			long period=5000;
//		String message =String.valueOf(timerupdate);
//		byte[] value=message.getBytes();
//		try {
//			parameters1.add(_TimerUpdateMessage, value);
//			System.out.println("成功向parameter1赋值"+value);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		TimerTask task=new TimerTask() {
//
//			public void run() {
//				try {
//					System.out.println("发送timer交互类");
//					_rtiAmbassador.sendInteraction(_TimerUpdate, parameters1, null);
//					System.out.println("发送timer交互类成功");
//					System.out.println("continueflag111111111111111"+continueflag);
//					if(continueflag){
//					System.out.println("currentTime"+currentTime);
//					System.out.println("lookhead"+lookhead);
//					currentTime.increaseBy(lookhead);
//					System.out.println("请求时间推进");
//					_rtiAmbassador.timeAdvanceRequest(currentTime);
//					}
//					
//				} catch (InteractionClassNotDefined e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InteractionClassNotPublished e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InteractionParameterNotDefined e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (FederateNotExecutionMember e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (SaveInProgress e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (RestoreInProgress e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (RTIinternalError e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ConcurrentAccessAttempted e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (InvalidFederationTime e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (FederationTimeAlreadyPassed e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (TimeAdvanceAlreadyInProgress e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (EnableTimeRegulationPending e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (EnableTimeConstrainedPending e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IllegalTimeArithmetic e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println("定时器正常工作......");
//			}
//		};
//		if(timerupdate>0)
//		{
//			timer.schedule(task,time,timerupdate);	
//		}
//
//	}

	public void DelayUpdate(int delay, int timescale) {
		String message=String.valueOf(delay*timescale);
		hla.rti.SuppliedParameters parameters=RTI.suppliedParametersFactory().create(1);
		byte[] value=message.getBytes();
		try {
			parameters.add(_DelayUpdateMessage, value);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		System.out.println("delay update");
		try {
			_rtiAmbassador.sendInteraction(_DelayUpdate, parameters, null);
		} catch (InteractionClassNotDefined e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InteractionClassNotPublished e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InteractionParameterNotDefined e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FederateNotExecutionMember e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SaveInProgress e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestoreInProgress e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RTIinternalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConcurrentAccessAttempted e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("delay update OK");
	}
public void modifyPara(String result) throws InteractionClassNotDefined, InteractionClassNotPublished, InteractionParameterNotDefined, InvalidFederationTime, FederateNotExecutionMember, SaveInProgress, RestoreInProgress, RTIinternalError, ConcurrentAccessAttempted {
	// TODO Auto-generated method stub
	SuppliedParameters parameters=RTI.suppliedParametersFactory().create(1);
	String message =result;
	continueflag=false;//控制管控端不让其推进
	byte[] value=message.getBytes();
	parameters.add(MPMessage, value);
	System.err.println(result+"     发送交互类  发送交互累 发送交互泪 .........");
	_rtiAmbassador.sendInteraction(ModifyPara, parameters, null,currentTime);
	System.out.println("发送修改成员参数交互类  -------------成功");
}
	public void pause() throws InvalidFederationTime {
		SuppliedParameters parameters=RTI.suppliedParametersFactory().create(1);
		String message ="PMessage";
		continueflag=false;//控制管控端不让其推进
		System.out.println("continueflag"+continueflag);
		byte[] value=message.getBytes();
		parameters.add(_PauseMessage, value);
		//			parameters.add(_PauseSender, encodeInt(0));
		if(runningdata.size()>=50)
		{
			for( int i=0;i< runningdata.size();i++)
			{
				try {
					pst.setString(1, runningdata.get(i).getMember());
					pst.setDouble(2, runningdata.get(i).getTime());
					pst.setString(3, runningdata.get(i).getValue());
					pst.setInt(4, runningdata.get(i).getRunid());
					pst.addBatch();
					pst.executeBatch();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			runningdata.clear();
		}
		System.out.println("pause");
		try {
			_rtiAmbassador.sendInteraction(_Pause, parameters, null,currentTime);
		} catch (InteractionClassNotDefined e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InteractionClassNotPublished e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InteractionParameterNotDefined e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FederateNotExecutionMember e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SaveInProgress e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestoreInProgress e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RTIinternalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConcurrentAccessAttempted e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void restart() throws UnsupportedEncodingException, InvalidFederationTime {
		SuppliedParameters parameters=RTI.suppliedParametersFactory().create(1);
		String message ="CMessage";
		continueflag=false;
		System.out.println("continueflag"+continueflag);
		System.out.println("restart");
		byte[] value=message.getBytes();
		parameters.add(_ContinueMessage, value);
		//			parameters.add(_ContinueSender, encodeInt(0));

		try {
			_rtiAmbassador.sendInteraction(_Continue, parameters, null,currentTime);
			System.out.println("成功发送reStart交互类");
		} catch (InteractionClassNotDefined e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InteractionClassNotPublished e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InteractionParameterNotDefined e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FederateNotExecutionMember e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SaveInProgress e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestoreInProgress e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RTIinternalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConcurrentAccessAttempted e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void refresh() throws UnsupportedEncodingException {
		SuppliedParameters parameters=RTI.suppliedParametersFactory().create(2);
		String message ="RMessage";
		System.out.println("refresh");
		byte[] value=message.getBytes();
		parameters.add(_RefreshMessage, value);
		//			parameters.add(_RefreshSender, encodeInt(0));

		try {
			_rtiAmbassador.sendInteraction(_Refresh, parameters, null);
		} catch (InteractionClassNotDefined e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InteractionClassNotPublished e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InteractionParameterNotDefined e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FederateNotExecutionMember e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SaveInProgress e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RestoreInProgress e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RTIinternalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ConcurrentAccessAttempted e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private static byte[] encodeInt(int i) {
		// TODO Auto-generated method stub
		return new byte[] {
				(byte)((i >>> 24) & 0xFF),
				(byte)((i >>> 16) & 0xFF),
				(byte)((i >>>  8) & 0xFF),
				(byte)((i >>>  0) & 0xFF)
		};
	}
	int decodeInt(byte[] buffer)
	{
		return
				((buffer[0] & 0xff) << 24) +
				((buffer[1] & 0xff) << 16) +
				((buffer[2] & 0xff) <<  8) +
				((buffer[3] & 0xff) <<  0);
	}

	public void reflectAttributeValues(
			int theObject,
			ReflectedAttributes theAttributes, 
			LogicalTime theTime,
			byte[] userSuppliedTag,
			EventRetractionHandle retractionHandle) throws InvalidFederationTime, FederationTimeAlreadyPassed, TimeAdvanceAlreadyInProgress, EnableTimeRegulationPending, EnableTimeConstrainedPending, FederateNotExecutionMember, SaveInProgress, RestoreInProgress, RTIinternalError, ConcurrentAccessAttempted  
			{
		//		 con=CTReadDB.getConnection();
		

		if(timeflag)
		{
			time1=System.currentTimeMillis();
			timeflag=false;
		}
		System.out.println("shoudaole");
		int value=0;
		String objectname=null;
		for(int k=0;k<ObjectID.size();k++)
		{
			if(theObject==ObjectID.get(k))
			{
				objectname=ObjectName.get(k);
				for(int j=0;j<theAttributes.size();++j)
				{
					time2=System.currentTimeMillis();
					try {
						pst=(PreparedStatement ) con .prepareStatement("insert into model_runningdata values( ?,?,?,?)");
						String str=new String(theAttributes.getValueReference(j));
						RunningData x=new RunningData();
						x.setMember(objectname);
					//	x.setTime(theTime);
						x.setValue(str);
						x.setRunid(RunID);
						System.out.println("Member: "+x.getMember()
								+" times: "+x.getTime()+" value: "+x.getValue()+" RunID "+RunID);

						runningdata.add(x);
						System.out.println("size: "+runningdata.size());
						//						 System.out.println("ObjectName: "+objectname+" the time: "+theTime.D+" value: "+str);
						//						 if(runningdata.size()>=20)
						//						 {
						//							 System.out.println("begin flush......");
						//							 
						//							 for(int i=0;i<runningdata.size();i++)
						//							 {
						//								 System.out.println("Member: "+runningdata.get(i).getMember()
						//										 +" time: "+runningdata.get(i).getTime()+" value: "+runningdata.get(i).getValue());
						//								 
						//							 }
						//							 System.out.println("end flush......");
						//
						//						 }

						if(runningdata.size()>20)
						{
							for( int i=0;i< runningdata.size();i++)
							{
								pst.setString(1, runningdata.get(i).getMember());
								pst.setDouble(2, runningdata.get(i).getTime());
								pst.setString(3, runningdata.get(i).getValue());
								pst.setInt(4, runningdata.get(i).getRunid());
								pst.addBatch();
							}
							pst.executeBatch();
							runningdata.clear();
							time1=System.currentTimeMillis();
						}else if ((time2-time1)>=10000) {
							for( int i=0;i< runningdata.size();i++)
							{
								pst.setString(1, runningdata.get(i).getMember());
								pst.setDouble(2, runningdata.get(i).getTime());
								pst.setString(3, runningdata.get(i).getValue());
								pst.setInt(4, runningdata.get(i).getRunid());
								pst.addBatch();
							}
							pst.executeBatch();
							runningdata.clear();
							time1=System.currentTimeMillis();
						}
						//									
						//
						////							String a="hla|a|b|c";
						String[] b=str.split("\\|");
						//							int i=0;
						for(int i=0;i<b.length;i++)
						{
							System.out.println(b[i]);
						}
//						if(objectname.equals(chartname))
//						{
//							if(b.length>=3)
//							{
//								if(objectname.equals("Commend"))
//								{
//									HlaDataView.series.get(0).add(theTime, 0);
//									HlaDataView.series.get(1).add(theTime, 0);
//									HlaDataView.series.get(2).add(theTime, 0);
//								}
//								else if(objectname.contains("Tank"))
//								{
//
//									//									if(b[3]==null)
//									//									{
//									//										b[3]="0";
//									//									}
//									HlaDataView.series.get(0).add(theTime, Double.parseDouble(b[1]));
//									HlaDataView.series.get(1).add(theTime, Double.parseDouble(b[2]));
//									HlaDataView.series.get(2).add(theTime, 0);
//								}else if(objectname.contains("Plane"))
//								{
//									HlaDataView.series.get(0).add(theTime, Double.parseDouble(b[1]));
//									HlaDataView.series.get(1).add(theTime, Double.parseDouble(b[2]));
//									HlaDataView.series.get(2).add(theTime, Double.parseDouble(b[3]));		
//								}
//
//							}else
//							{
//								HlaDataView.series.get(0).add(theTime, 0);
//								HlaDataView.series.get(1).add(theTime, 0);
//								HlaDataView.series.get(2).add(theTime, 0);
//							}
//
//						}
//
//												if(new String(theAttributes.getValueReference(j)).equals("A 0")||new String(theAttributes.getValueReference(j)).equals("B 0"))
//												{
//													value=2;
//												} 
//												else{
//													value=5;
//												}
//												System.out.println("准备更新");
//												if(objectname.equals("A"))
//												{
//													System.out.println("Model1 要更新");
//													 HlaDataView.series.get(0).add(theTime.D,value);
//												}
//												if(objectname.equals("B"))
//												{
//													System.out.println("Model2 要更新");
//													 HlaDataView.series.get(1).add(theTime.D,value);
//												}
//												if(objectname.equals("chengyuan"))
//												{
//													System.out.println("Model3 要更新");
//													 HlaDataView.series.get(2).add(theTime.D,value);
//												}
//												for(int i=0;i<HlaDataView.series.size();i++)
//												 {
//													 if(objectname==HlaDataView.series.get(j).getDescription())
//													 {
//														 System.out.println("更新JfreeChart");
//														 HlaDataView.series.get(j).add(theTime.D,value);
//						//								 HlaDataView.series.get(j).a
//													 }
//												 }
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						//						e.printStackTrace();
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}



		/*for(int j=0;j<theAttributes.size();j++)
	 {

	 for(int i=0;i<_objpara.size();i++)
	 {
		 if(theAttributes.getAttributeHandle(j)==_objpara.get(i))
		 {
			 String objectname=null;
			 for(int k=0;k<ObjectID.size();k++)
			 {
				 if(theObject==ObjectID.get(k))
					 objectname=ObjectName.get(k);
			 }
			 try {
				 System.out.println("ObjectName: "+objectname+" the time: "+theTime.D+" value: "+String.valueOf(theAttributes.getValueReference(j)));
			} catch (Exception e) {
				// TODO: handle exception
			}

		 }
	 }
	 }*/
			}
	public void receiveInteraction (
			int                 interactionClass, 
			ReceivedInteraction theInteraction,  
			byte[]              userSuppliedTag)      
	{	


		if (interactionClass ==_ReplyQuit) {
			System.out.println("收到reply Quit交互");
			i++;
			System.out.println(i+"   "+FedNum);
			//		        	 stopUpdate=false;
			//		            Interactionflag=true;
			//		        	 if(i==FedNum)
			//		        	 {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
	public void turnInteractionsOn (
			int theHandle)
	{
		System.out.println("有订购管控端RTI1.3的交互类存在");
	}
	public void turnUpdatesOnForObjectInstance (
			int theObject,
			AttributeHandleSet theAttributes)
	{
		System.out.println("可以开始更新属性值");
	}
	public void stopRegistrationForObjectClass(int theClass) {
		// TODO Auto-generated method stub
		System.out.println("有订购退出");
	}	

	public void discoverObjectInstance(int instanceId, int classId, String instanceName)
	{
		System.out.println("发现对象类实例");
		System.out.println("kaisi");
		System.out.println("收到对象类:"+instanceId+" "+instanceName);
		System.out.println("instanceIdAAAAAAAAAAAAAAAAAA"+instanceId);
		System.out.println("classIdBBBBBBBBBBBBBBBBBBBBBBB"+classId);
		ObjectID.add(instanceId);
		ObjectName.add(instanceName);
		if(instanceId==104)
		{
			timeadvance1=true;////////////////////////////////////////////////////
//			timerUpdate();
		}
		if(instanceId==105)
		{
			timeadvance2=true;//////////////////////////////////////////////////////
			
		}
        
			
		
	}
	public void timeAdvanceGrant(LogicalTime theTime)
	{
		timeadvance = true;
	}

	public String getFedName() {
		// TODO Auto-generated method stub
		return FedName;
	}

}