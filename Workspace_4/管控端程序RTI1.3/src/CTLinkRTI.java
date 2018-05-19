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
	private static int _Start;//��ʼ��ť�Ľ�������
	//	private static int _StartSender;//Start�ķ����߲������
	private static int _StartMessage;//Start�ķ�����Ϣ�Ĳ������
	private static int _ReplyStart;
	private static int _RSMessage;
	private static int _Pause;//��ͣ��ť�Ľ�������
	private static int ModifyPara;//�޸ķ������
	//	private static int _PauseSender;//pause�ķ����ߵĲ������
	private static int _PauseMessage;//pause�ķ�����Ϣ�Ĳ������
	private static int MPMessage;
	private static int _ReplyPause;
	private static int _RPMessage;
	private static int _Continue;//������ť�Ľ�������
	//	private static int _ContinueSender;//Continue�ķ����ߵĲ������
	private static int _ContinueMessage;//Continue�ķ�����Ϣ�Ĳ������
	private static int _ReplyContinue;
	private static int _RCMessage;
	private static int _Quit;//ֹͣ��ť�ķ��ͽ�������
	//	private static int _StopSender;//Stop�ķ����߲������
	private static int _QuitMessage;//Stop�ķ�����Ϣ�Ĳ������
	private static int _ReplyQuit;
	private static int _RQMessage;
	private static int _Refresh;//ˢ�°�ť�Ľ�������
	//	private static int _RefreshSender;//ˢ�µķ����ߵĲ������
	private static int _RefreshMessage;//ˢ�µķ�����Ϣ�Ĳ������
	private static int _ReplyRefresh;
	private static int _RRMessage;
	private static int _TimerUpdate;//��ʱ���Ľ�������
	private static int _TimerUpdateMessage;//��ʱ������Ϣ�������
	private static int _ReplyTimerUpdate;//�ص���ʱ���Ľ�������
	private static int _ReplyTimerUpdateMessage;//�ص���ʱ������Ϣ�������

	private static int _DelayUpdate;//�ӳٸ��µĽ�������
	private static int _DelayUpdateMessage;//�ӳٸ��µ���Ϣ�������
	private static int _ReplyDelayUpdate;//�ص��ӳٸ��µĽ�������
	private static int _ReplyDelayUpdateMessage;//�ص��ӳٸ��µ���Ϣ�������

	private static ArrayList<Integer> _objclass=new ArrayList<Integer>();
	private static ArrayList<Integer>_obj_para=new ArrayList<Integer>();
	private static ArrayList<Integer> _objpara=new ArrayList<Integer>();
	private static ArrayList<Integer>ObjectID=new ArrayList<Integer>();
	private static ArrayList<String>ObjectName=new ArrayList<String>();
	private HashMap _participants = new HashMap();//��Ա��hashmap
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
			System.out.println("�ܿض˷�������......");
			//String rtiHost="172.16.73.60";//�����IP��ַ
			File file1=new File("ServerIP.txt");
			BufferedReader reader=new BufferedReader(new FileReader(file1));
			String rtiHost=reader.readLine();
			System.out.println(rtiHost);
			_rtiAmbassador=RTI.getRTIambassador(rtiHost, 8989);

			name=FedName;

			System.out.println("�������� :......"+name);
			///////////////
			_rtiAmbassador.createFederationExecution(FedName, new File("AutoControlFED.fed").toURI().toURL());//����ķ���Ҫ�޸�Ϊrti1.3�ķ���
			///////////////
			System.out.println("��������ɹ�");
			CTLinkRTI.CreateFedTable(FedName);
			
			//_rtiAmbassador.resignFederationExecution(_rtiAmbassador.createFederationExecution("CommandTest", new File("FED.fed").toURI().toURL()));
			System.out.println("ִ��");
			_rtiAmbassador.joinFederationExecution("CommandCenter",name, this);
			System.out.println("��������");
			System.out.println("size: "+CTReadFOM.objParameters.size());
			ArrayList<objectClass> parameter=CTReadFOM.objParameters;
			for(int i=0;i<parameter.size();i++)
			{
				System.out.println("�����ࣺ"+parameter.get(i).getName());
			}
			for(int i=0;i<parameter.size();i++)//����������
			{
				int _obj=_rtiAmbassador.getObjectClassHandle(parameter.get(i).getName());
				System.out.println("�����ࣺ "+parameter.get(i).getName());
				AttributeHandleSet attribute=RTI.attributeHandleSetFactory().create();
				for(int j=0;j<parameter.get(i).getValue().size();j++)
				{				
					System.out.println(parameter.get(i).getValue().get(j));
					int _objparameter=_rtiAmbassador.getAttributeHandle(parameter.get(i).getValue().get(j), _obj);
					_objpara.add(_objparameter);
					System.out.println("_objparameter"+_objparameter);
					_obj_para.add(_obj);
					attribute.add(_objparameter);				
					System.out.println("���������ࣺ"+attribute);
				}
				System.out.println("������������"+attribute);
				_rtiAmbassador.subscribeObjectClassAttributes(_obj, attribute);
				System.out.println("���������������"+attribute);
				attribute.empty();
				System.out.println("��ն�������");
				_objclass.add(_obj);
				System.out.println("����Obj���"+_obj);
				//			_rtiAmbassador.subscribeObjectClassAttributes(_obj);
			}
			System.out.println("��������");
			_Start=_rtiAmbassador.getInteractionClassHandle("Start");
			_StartMessage=_rtiAmbassador.getParameterHandle("SMessage", _Start);
			System.out.println("��ʼ����������Start");
			
			_ReplyStart=_rtiAmbassador.getInteractionClassHandle("ReplyStart");
			_RSMessage=_rtiAmbassador.getParameterHandle("RSMessage",_ReplyStart);
			//******
			_Pause=_rtiAmbassador.getInteractionClassHandle("Pause");
			System.out.println("99999999999999999999999999999999999999999999999999999");
			ModifyPara=_rtiAmbassador.getInteractionClassHandle("Modify");
System.out.println("��ȡ����������     "+ModifyPara);
			_PauseMessage=_rtiAmbassador.getParameterHandle("PMessage", _Pause);
			MPMessage=_rtiAmbassador.getParameterHandle("MPMessage", ModifyPara);
System.out.println("��ȡ��������������  "+MPMessage);
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
			System.out.println("����Start������ɹ�");
			_rtiAmbassador.publishInteractionClass(_Pause);
			System.out.println("����������             --------------------------------------------");
			_rtiAmbassador.publishInteractionClass(ModifyPara);
			System.out.println("����������aaaaaaaaaaaa�ɹ�   ---------------------------------------------");
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
			System.out.println("��ȡ���ݿ�����ʧ�ܣ����������ݿ�����·��");
		}
		try {
			conn=DriverManager.getConnection("jdbc:mysql://"+"172.16.73.110"+":3306/"+"data_analysis","root","root");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("����ģ�ͷ�����ʧ�ܣ������ģ���е�����IP");
		}
		ResultSet rs1=conn.getMetaData().getTables(null, null,fedName, null );//�жϱ��Ƿ���ڣ������򲻴����������ھʹ���
		Statement st=conn.createStatement();
		String sql="create table "+fedName+
				"(FederateName varchar(20), " +
				"ModelID varchar(20)," +
				"RID int(20),"+
				"Value varchar(20)," +
				"RunningTime varchar(50))";
		if(!rs1.next())//������ݿ���û�е�ǰ�������ı����򴴽�
		{
			st.execute(sql);
			System.out.println("������ɹ�");
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
			System.out.println("����ִ����������");
			//timer.cancel();
			_rtiAmbassador.resignFederationExecution(1);
			System.out.println(name);
			_rtiAmbassador.destroyFederationExecution(name);
			System.out.println("��������ִ�гɹ�");
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
		System.out.println("�ɹ���ͨ�ó�Ա����Start����"+parameters);
		System.out.println("�ɹ����ͽ����࣬Start�ܿض�");
		
		
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
//			System.out.println("�ɹ���parameter1��ֵ"+value);
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		TimerTask task=new TimerTask() {
//
//			public void run() {
//				try {
//					System.out.println("����timer������");
//					_rtiAmbassador.sendInteraction(_TimerUpdate, parameters1, null);
//					System.out.println("����timer������ɹ�");
//					System.out.println("continueflag111111111111111"+continueflag);
//					if(continueflag){
//					System.out.println("currentTime"+currentTime);
//					System.out.println("lookhead"+lookhead);
//					currentTime.increaseBy(lookhead);
//					System.out.println("����ʱ���ƽ�");
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
//				System.out.println("��ʱ����������......");
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
	continueflag=false;//���ƹܿض˲������ƽ�
	byte[] value=message.getBytes();
	parameters.add(MPMessage, value);
	System.err.println(result+"     ���ͽ�����  ���ͽ����� ���ͽ����� .........");
	_rtiAmbassador.sendInteraction(ModifyPara, parameters, null,currentTime);
	System.out.println("�����޸ĳ�Ա����������  -------------�ɹ�");
}
	public void pause() throws InvalidFederationTime {
		SuppliedParameters parameters=RTI.suppliedParametersFactory().create(1);
		String message ="PMessage";
		continueflag=false;//���ƹܿض˲������ƽ�
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
			System.out.println("�ɹ�����reStart������");
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
//												System.out.println("׼������");
//												if(objectname.equals("A"))
//												{
//													System.out.println("Model1 Ҫ����");
//													 HlaDataView.series.get(0).add(theTime.D,value);
//												}
//												if(objectname.equals("B"))
//												{
//													System.out.println("Model2 Ҫ����");
//													 HlaDataView.series.get(1).add(theTime.D,value);
//												}
//												if(objectname.equals("chengyuan"))
//												{
//													System.out.println("Model3 Ҫ����");
//													 HlaDataView.series.get(2).add(theTime.D,value);
//												}
//												for(int i=0;i<HlaDataView.series.size();i++)
//												 {
//													 if(objectname==HlaDataView.series.get(j).getDescription())
//													 {
//														 System.out.println("����JfreeChart");
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
			System.out.println("�յ�reply Quit����");
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
		System.out.println("�ж����ܿض�RTI1.3�Ľ��������");
	}
	public void turnUpdatesOnForObjectInstance (
			int theObject,
			AttributeHandleSet theAttributes)
	{
		System.out.println("���Կ�ʼ��������ֵ");
	}
	public void stopRegistrationForObjectClass(int theClass) {
		// TODO Auto-generated method stub
		System.out.println("�ж����˳�");
	}	

	public void discoverObjectInstance(int instanceId, int classId, String instanceName)
	{
		System.out.println("���ֶ�����ʵ��");
		System.out.println("kaisi");
		System.out.println("�յ�������:"+instanceId+" "+instanceName);
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