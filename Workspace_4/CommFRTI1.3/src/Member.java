import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import se.pitch.prti.FederateAmbassador_Impl;
import se.pitch.prti.LogicalTimeDouble;
import se.pitch.prti.LogicalTimeIntervalDouble;
import se.pitch.prti.RTI;
import hla.rti.ArrayIndexOutOfBounds;
import hla.rti.AttributeHandleSet;
import hla.rti.AttributeNotDefined;
import hla.rti.AttributeNotOwned;
import hla.rti.ConcurrentAccessAttempted;
import hla.rti.CouldNotOpenFED;
import hla.rti.EnableTimeConstrainedPending;
import hla.rti.EnableTimeRegulationPending;
import hla.rti.ErrorReadingFED;
import hla.rti.EventRetractionHandle;
import hla.rti.FederateAlreadyExecutionMember;
import hla.rti.FederateLoggingServiceCalls;
import hla.rti.FederateNotExecutionMember;
import hla.rti.FederateOwnsAttributes;
import hla.rti.FederatesCurrentlyJoined;
import hla.rti.FederationExecutionDoesNotExist;
import hla.rti.FederationTimeAlreadyPassed;
import hla.rti.IllegalTimeArithmetic;
import hla.rti.InteractionClassNotDefined;
import hla.rti.InteractionClassNotPublished;
import hla.rti.InteractionParameterNotDefined;
import hla.rti.InvalidFederationTime;
import hla.rti.InvalidLookahead;
import hla.rti.InvalidResignAction;
import hla.rti.LogicalTime;
import hla.rti.LogicalTimeInterval;
import hla.rti.NameNotFound;
import hla.rti.ObjectAlreadyRegistered;
import hla.rti.ObjectClassNotDefined;
import hla.rti.ObjectClassNotPublished;
import hla.rti.ObjectNotKnown;
import hla.rti.OwnershipAcquisitionPending;
import hla.rti.RTIambassador;
import hla.rti.RTIinternalError;
import hla.rti.ReceivedInteraction;
import hla.rti.ReflectedAttributes;
import hla.rti.RestoreInProgress;
import hla.rti.SaveInProgress;
import hla.rti.SuppliedAttributes;
import hla.rti.SuppliedParameters;
import hla.rti.TimeAdvanceAlreadyInProgress;
import hla.rti.TimeConstrainedAlreadyEnabled;
import hla.rti.TimeConstrainedWasNotEnabled;
import hla.rti.TimeRegulationAlreadyEnabled;
import hla.rti.TimeRegulationWasNotEnabled;

import org.xvolks.jnative.exceptions.NativeException;

public class Member extends FederateAmbassador_Impl {
	static String init_para;
	public static ArrayList<String > huan=new ArrayList<String>();
	private RTIambassador rtiambassador;// RTIambassador��һ���ӿڣ�����һ���ӿڶ���
	public int p_ObjectId[] = new int[10];
	public int p_AttributeId[] = new int[20]; // dObjectId[i]  
	public int s_ObjectId[] = new int[10];
	public int s_AttributeId[] = new int[20];
	public String fileName="test.txt";
	public	File file = new File(fileName);
	public int p_ObId[] = new int[10];// ע�����ʵ�����ص�intֵ
	public int d_ObId[] = new int[10]; // ����ص�
	public static int s_AttributeIdNumber = 0; // �ܹ������Ķ��������������Ŀ
	//public static int receiveNumber = 0;
	public int p_attributeIdText[] = new int[10];
	public int _messageId[] = new int[20];// �����ܿض�����������Ա�����Ľ�����
	public int _parameterIdText[] = new int[20];// �����ܿض�����������Ա�����Ľ���������Ծ��
    
	public int _DelayUpdate;//��ʱ�ƽ��Ľ�������
	public int _DelayUpdateMessage;//������ʱ�ƽ�ʱ��Ľ���������ֵ
	public int DelayTime;//��ʱ�ƽ���ʱ��
	public int _ReplyDelayUpdate;//��Ӧ��ʱ�Ľ�������
	public int _ReplyDelayUpdateMessage;//��Ӧ��ʱ�Ľ���������

	static TimerOperation timeroperation;//ʵ����һ����ʱ������
	public int _TimerUpdate;//��ʱ���Ľ�������
	public int _TimerUpdateMessage;//��ʱ���Ľ���������ֵ
	public boolean IsTimerFinish=true;
	public int _ReplyTimerUpdate;//��Ӧ��ʱ���Ľ�������
	public int _ReplyTimerUpdateMessage;//��Ӧ��ʱ���Ľ���������ֵ
	//public boolean IsTimeroperation=true;

	public ArrayList<Integer> InstanID = new ArrayList<Integer>();//��¼�ó�Ա�����ĳ�Ա�������ࣩ�ĸ���
	// ʱ�����ʱ�����ޱ���
	boolean TimeRegulated = false;
	boolean TimeConstrained = false;
	boolean TimeAdvanceOutstanding = false;

	//�߼�ʱ�䣬 �Ѵ�xml�ļ��ж�����Lookaheads��ֵ������
	LogicalTime currentTime;//���ǰʱ��
	LogicalTimeInterval lookhead; //ʱ��ǰհ��

	public boolean IsFirst = true;
	public boolean IsStart = false;// ��ʼ�����Flag
	public boolean IsQuit = false; // ��ͣ�����Flag
	public boolean IsS_Object = false; // IsS_Object�Ƿ���ڶ�����Ķ�����

	public boolean registrationFlag = false;//���Ƴ�Ա�Ƿ�ע�����������ֵ�ɹ���Flag
	public boolean IsPause = true; // ��Ա�Ƿ���ͣ��Flag
	public boolean IsContinue = false;//��Ա�Ƿ������Flag
	//	public boolean IstimeAdvance = false; // bj2
	public static String MemID = null;//��Ա��ID��
	public static String XmlPath = null;//ͨ�ó�Ա��ȡ��XML��·��
	public static String DllPathList = null;//ͨ�ó�Ա���ص�DLL��·��
	public static String DllNameList = null;//��Ҫ����DLL������
	public static String rtiHost = null;//RTI��������IP��ַ

	public static String DLLInput = null;//DLL������
	public static String DllOutput = null;//DLL�����
	//public static String TemporaryStorage = null;
	public static byte[] MemberOutput = null;//��¼��Ա�����byte����
	public static String receive = "";//���ڼ�¼��Ա����DLL�������
	public static int TimerCycle;  //��Ա��StepTime�Ĺ�Լ��
	public static int StepCount=0; 
	public static int CycleCount=0;

	public static boolean IsDllbefor = true;
	public static boolean  firstflag=true;
	public static boolean doubleflag=true;
	static Read_Xml readxml = new Read_Xml();
	public boolean testflag=false;
	public boolean pauseflag=false;//�ж��Ƿ���ͣ��Ա��flag
	public boolean restartflag=false;//�ж��Ƿ������Ա��flag
	//public static int RTIID=0;
	public boolean loopFlag=false;//���Ƴ�Աѭ�����е�Flag
	public boolean flagtimeadvance=true;//���Ƴ�Աѭ�����е�Flag

	//String[] message=new String[2];
	ArrayList<String > message=new ArrayList<String>();
	ArrayList<String > valuerelation=new ArrayList<String>();
	ArrayList<String> tempvalue=new ArrayList<String>();
	DLLhandle dllwork=new DLLhandle();
	ArrayList< String> Attributevalue=new ArrayList<String>();

	public boolean ValueFlag=true;//���������Ƿ������ǵ�һ�δ����Flag
	public boolean StartFlag=true;//���FLAGֵ��Ϊ������ͨ��Ա��һ�����㹻��ʱ��ȥ����ص�������������ֱ�ӽ���reflectValue������������ӳٲ�������tick�������
	String [] ValueRecode;//��¼��Ա���ܵ�������
	String [] CombineMemberValue;//��¼��ϳ�Ա���ݵ�����
	DataCollection datacollection=new DataCollection();
      public int sum=0;
    
	public static void main(String[] args) throws Exception {
		MemID = args[0];
		XmlPath = args[1];
		DllPathList = args[2];
		DllNameList = args[3];
		rtiHost = args[4];

		String [] DllName;
		DllName=DllNameList.split("-");
		for(int i=0;i<DllName.length;i++)
		{
			String dllmessage=DllName[i];
			readxml.LinkDll(MemID, XmlPath, DllPathList, dllmessage);// ����DLL����
		}
		readxml.ReadXml(XmlPath, MemID); // /��ȡXML�ļ�
		System.out.println("���ճ�Ա��ʼֵ");
//		if(readxml.InitParameters.size()!=0)//���InitParameters��ֵ���ȡ��û��ֵ���ö�ȡ
//		{
//			receive =readxml.InitParameters.get(0).getValue();///????????????????????????????���˳����������ܳ��ִ��󣡣�������
//			System.out.println("��ʼֵreceive.trim()Ϊ"+receive.trim());
//		}
		if(readxml.Inputsource.size()!=0)//��Ա�ĳ�ʼֵ�Ǵ�Display�ڵ��ж�ȡ������
		{
			for(int i =0; i<readxml.Inputsource.size();i++)
			{
				//����ȡ����������"/"ƴ������
				if((i+1)==readxml.Inputsource.size())// ��������һ��Ԫ��
				{
					receive=receive+readxml.Inputsource.get(i);
				}
				else
				{
					receive=receive+readxml.Inputsource.get(i)+"/";
				}
			}
		}

		DLLInput = receive.trim();
		System.out.println("DLLInput"+DLLInput);
		Member commf = new Member();
		commf.run();
	}

	@SuppressWarnings("static-access")
	public void run() throws  CouldNotOpenFED,
	ErrorReadingFED, RTIinternalError, ConcurrentAccessAttempted, FederateAlreadyExecutionMember, FederationExecutionDoesNotExist, SaveInProgress, RestoreInProgress, InvalidLookahead, TimeRegulationAlreadyEnabled, EnableTimeRegulationPending, TimeAdvanceAlreadyInProgress, InvalidFederationTime, FederateNotExecutionMember, TimeConstrainedAlreadyEnabled, EnableTimeConstrainedPending, NameNotFound, ObjectClassNotDefined, AttributeNotDefined, OwnershipAcquisitionPending, InteractionClassNotDefined, FederateLoggingServiceCalls, InteractionClassNotPublished, InteractionParameterNotDefined, ObjectClassNotPublished, ObjectAlreadyRegistered, IllegalTimeArithmetic, ObjectNotKnown, AttributeNotOwned, FederationTimeAlreadyPassed, FederatesCurrentlyJoined, TimeConstrainedWasNotEnabled, TimeRegulationWasNotEnabled, FederateOwnsAttributes, InvalidResignAction, InterruptedException, IllegalAccessException, IOException, NativeException, SQLException {
		rtiambassador = RTI.getRTIambassador(rtiHost, 8989); // ��ʼ��RTI��ʹ
		System.out.println("readxml.FederateName:"+readxml.FederateName);
		System.out.println("readxml.FederationName:"+readxml.FederationName);
		System.out.println("this:"+this.toString());
		try{
			rtiambassador.joinFederationExecution(readxml.FederateName,
					readxml.FederationName, this);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		rtiambassador.tick();
		System.out.println("��������ɹ�");

		System.out.println("Member--aaabbbccc: "+readxml.FederateName+MemID);
		System.out.println("readxml.IsTimeRegulating"+readxml.IsTimeRegulating);
		lookhead=new LogicalTimeIntervalDouble(0.1);
		currentTime = new LogicalTimeDouble(0.0);
		String temp1 = "Model" + readxml.ModelID;
		AttributeHandleSet attributes2 = RTI.attributeHandleSetFactory().create();
		System.out.println("����ԱΪ"+temp1);
		for(int i=0;i<readxml.Dllname.MemberDLLName.size();i++)
		{
			String [] MemberDLLName=null;
			MemberDLLName=readxml.Dllname.MemberDLLName.get(i).split("-");
			System.load("C://HLA//"+readxml.FederationName+"//"+temp1+"//"+MemberDLLName[1]);
			System.out.println("����"+readxml.Dllname.MemberDLLName.get(i)+"�ɹ�");
		}
		System.out.println("readxml.p_Objects.size()"+readxml.p_Objects.size());
		p_ObjectId[0] = rtiambassador.getObjectClassHandle(temp1); // ��ȡ�������ID,��ʱ��Ϊд���ĳ��򣡣���������������
		System.out.println("p_ObjectId[0]  !1111"+p_ObjectId[0]);
		p_AttributeId[0] = rtiambassador.getAttributeHandle(
				"isOutput", p_ObjectId[0]);
		attributes2.add(p_AttributeId[0]); // �����Ծ���������Ծ����
		rtiambassador.publishObjectClass(p_ObjectId[0], attributes2);
		System.out.println("����������ɹ�");
		attributes2.empty();
		////////////����XMl����subscribe��ֵ
		String ModelName = null;
		AttributeHandleSet attributes3 = RTI.attributeHandleSetFactory().create();
		int index = 0;
		for (int i = 0; i < readxml.S_Objects.size(); i++)
		{
			System.out.println("����readxmld��ѭ��");
			ModelName = "Model" + readxml.S_Objects.get(i).ObjectID.get(i);
			System.out.println("�����Ķ�����IDΪ"+ModelName);
			s_ObjectId[i] = rtiambassador.getObjectClassHandle(ModelName);
			//	readxml.S_Objects=method(readxml.S_Objects);//��readxml.S_Objects�ظ��Ĳ���ȥ��
			System.out.println("Attributes:"+readxml.S_Objects.get(i).Attributes.size());
			for (int j = 0; j < readxml.S_Objects.get(i).Attributes.size(); j++) {
				//				s_AttributeId[index] = rtiambassador.getAttributeHandle(
				//						readxml.S_Objects.get(i).Attributes.get(j),
				//						s_ObjectId[i]);
				s_AttributeId[index] = rtiambassador.getAttributeHandle("isOutput", s_ObjectId[i]);
				attributes3.add(s_AttributeId[index]);
				index++;
			}
			System.out.println("�����ඩ�����");
			//	attributes3.add("isOutput");
			rtiambassador.subscribeObjectClassAttributes(s_ObjectId[i],attributes3);
			attributes3.empty();
		}
		s_AttributeIdNumber = index;
		// ��ʼ�����ܿض˵Ľ�����
		// ����������Start����ʼ����

		_messageId[0] = rtiambassador.getInteractionClassHandle("Start");
		System.out.println("_messageId[0]��ֵΪ"+_messageId[0]);
		_parameterIdText[0] = rtiambassador.getParameterHandle("SMessage",
				_messageId[0]);

		System.out.println("����������Start��׼����ʼ����");
		rtiambassador.subscribeInteractionClass(_messageId[0]);

		// ����������Pause����ͣ����

		_messageId[1] = rtiambassador.getInteractionClassHandle("Pause");

		_parameterIdText[1] = rtiambassador.getParameterHandle("PMessage",
				_messageId[1]);

		rtiambassador.subscribeInteractionClass(_messageId[1]);

		// ����
		_messageId[2] = rtiambassador.getInteractionClassHandle("Continue");

		_parameterIdText[2] = rtiambassador.getParameterHandle("CMessage",
				_messageId[2]);

		rtiambassador.subscribeInteractionClass(_messageId[2]);

		// ����������Quit���˳�����

		_messageId[3] = rtiambassador.getInteractionClassHandle("Quit");

		_parameterIdText[3] = rtiambassador.getParameterHandle("QMessage",
				_messageId[3]);

		rtiambassador.subscribeInteractionClass(_messageId[3]);
		//�������ݸ���
		_messageId[10]=rtiambassador.getInteractionClassHandle("Modify");
		
		_parameterIdText[10]=rtiambassador.getParameterHandle("MPMessage", _messageId[10]);
		rtiambassador.subscribeInteractionClass(_messageId[10]);
		// ˢ��

		_messageId[4] = rtiambassador.getInteractionClassHandle("Refresh");

		_parameterIdText[4] = rtiambassador.getParameterHandle("RMessage",
				_messageId[4]);

		rtiambassador.subscribeInteractionClass(_messageId[4]);

		// ���������� ReplySart

		_messageId[5] = rtiambassador.getInteractionClassHandle("ReplyStart");

		_parameterIdText[5] = rtiambassador.getParameterHandle("RSMessage",
				_messageId[5]);

		rtiambassador.publishInteractionClass(_messageId[5]);

		// ���������� ReplyStop��������ͣӦ��

		_messageId[6] = rtiambassador.getInteractionClassHandle("ReplyPause");

		_parameterIdText[6] = rtiambassador.getParameterHandle("RPMessage",
				_messageId[6]);

		rtiambassador.publishInteractionClass(_messageId[6]);

		_messageId[7] = rtiambassador
				.getInteractionClassHandle("ReplyContinue");

		_parameterIdText[7] = rtiambassador.getParameterHandle("RCMessage",
				_messageId[7]);

		rtiambassador.publishInteractionClass(_messageId[7]);

		// ����������AdjustModelParameters,���ڷ������ֵ

		_messageId[8] = rtiambassador.getInteractionClassHandle("ReplyRefresh");

		_parameterIdText[8] = rtiambassador.getParameterHandle("RRMessage",
				_messageId[8]);

		rtiambassador.publishInteractionClass(_messageId[8]);

		_messageId[9] = rtiambassador.getInteractionClassHandle("ReplyQuit");

		_parameterIdText[9] = rtiambassador.getParameterHandle("RQMessage",
				_messageId[9]);

		rtiambassador.publishInteractionClass(_messageId[9]);


		//��ʱ���ı�һ���ƽ���ʱ��
		_DelayUpdate = rtiambassador.getInteractionClassHandle("DelayUpdate");

		_DelayUpdateMessage = rtiambassador.getParameterHandle("DMessage",_DelayUpdate);

		rtiambassador.subscribeInteractionClass(_DelayUpdateMessage);


		_ReplyDelayUpdate = rtiambassador.getInteractionClassHandle("ReplyDelayUpdate");

		_ReplyDelayUpdateMessage = rtiambassador.getParameterHandle("RDMessage",_ReplyDelayUpdate);

		rtiambassador.publishInteractionClass(_ReplyDelayUpdateMessage);

		System.out.println("ͨ�ó�Ա��Start״̬  "+IsStart);
		while (loopFlag) {

			if (IsStart)// ����ʼ����
			{
				System.out.println("ͨ�ó�Ա��Start״̬Ϊ"+IsStart+"  ��ʼ���ͽ�����");
				// ���������Ծ����
				SuppliedParameters parameters = RTI.suppliedParametersFactory()
						.create(1);
				parameters.add(_parameterIdText[5], "start".getBytes());
				rtiambassador.sendInteraction(_messageId[5], parameters, null);
				System.out.println("�ɹ����ͽ�����");
				IsStart = false;
				break;
				
			}
			if (IsQuit) {
				break;
			}
		}
		// ////////

		// ��ʼ����////////////////////////

		while (!IsQuit) // û���յ��˳�����
		{
			if(testflag)//�ѳ�Ա�涨Ϊʱ����ƺ�ʱ������
			{
				rtiambassador.enableTimeRegulation(currentTime, lookhead);
				while(!TimeRegulated)
				{
					rtiambassador.tick();//�������������ʱ��ȥ����Ϊʱ�����
				}
				rtiambassador.enableTimeConstrained();
				while(!TimeConstrained)
				{
					rtiambassador.tick();//�������������ʱ��ȥ����Ϊʱ������
				}
				testflag=false;
				System.out.println("�޸ĳ�Աʱ�����ʽ�ɹ�");
			}
			if(pauseflag)//��ͣ��Ա
			{
				rtiambassador.disableTimeConstrained();
				rtiambassador.disableTimeRegulation();
				SuppliedParameters parameters = RTI.suppliedParametersFactory()
						.create(0);
				parameters.add(_parameterIdText[6], "Pause".getBytes());
				rtiambassador.sendInteraction(_messageId[6], parameters, null);
				pauseflag=false;
			}	
			if(restartflag)//������Ա
			{
				rtiambassador.enableTimeRegulation(currentTime, lookhead);
				while(!TimeRegulated)
				{
					rtiambassador.tick();//�������������ʱ��ȥ����Ϊʱ�����
				}
				rtiambassador.enableTimeConstrained();
				while(!TimeConstrained)
				{
					rtiambassador.tick();//�������������ʱ��ȥ����Ϊʱ������
				}
				SuppliedParameters parameters = RTI.suppliedParametersFactory()
						.create(0);
				parameters.add(_parameterIdText[7], "Continue".getBytes());
				rtiambassador.sendInteraction(_messageId[7], parameters, null);
				restartflag=false;
			}
			//System.out.println("IsS_Objectaaaaaaaaaaa:"+IsS_Object);
			if (IsS_Object && !IsPause) // ������ڶ����ඩ���ߣ���ʼע�����ʵ��
			{
				System.out.println("p_ObjectId[0]"+p_ObjectId[0] );
				p_ObId[0] = rtiambassador.registerObjectInstance(
						p_ObjectId[0], readxml.ModelName+readxml.ModelID);
				System.out.println("p_ObId[0] "+p_ObId[0] );
				System.out.println("ע�������ʵ���ɹ�"+readxml.ModelName);
				IsS_Object = false; // IsS_Object=IsS_Object
				registrationFlag = true;
				System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"); 
			}

			if (!TimeAdvanceOutstanding && registrationFlag && !IsPause&&TimeRegulated) 
			{
				System.out.println("******************flagtimeadvance********************!!!!!!!1"+flagtimeadvance);
				if(flagtimeadvance)
				{
					currentTime.increaseBy(lookhead);
					flagtimeadvance=false;
				}

				if(registrationFlag) // ע�����ܸ���&&!IsPause
				{
					System.out.println(currentTime);
					if(firstflag)//��һ�����г�Ա�ó�ʼ���ݵ���DLL
					{
						System.out.println("readxml.IsCombineMember"+readxml.IsCombineMember);
						if(readxml.IsCombineMember.equals("1"))//�������ϳ�Ա��ִ����ϳ�Ա�ĵ���dll����
						{
							System.out.println("��ȡreadxml.IsCombineMember��ֵ�ɹ�");
							ArrayList<String> rec=new ArrayList<String>();
							//							for(int i=0;i<readxml.map.TargetInput.size();i++)
							//							{
							//								Member.receive=readxml.map.TargetInput.get(i).split("-")[1]+"-"+Member.receive;
							//								System.out.println("Member.rec"+Member.receive);
							//							}
							//							rec.add(Member.receive);//���ڵ���ϳ�Ա��һ�����ô����ʼֵ

							String fileName="test.txt";
							File file = new File(fileName);
							huan.clear();
							if(file.exists())
							{
								BufferedReader br = new BufferedReader(new InputStreamReader(
										new FileInputStream("test.txt")));
								for (String line = br.readLine(); line != null; line = br.readLine()) {
									huan.add(line);   
								}
								br.close();
								file.delete();
							}
							huan=method(huan);
							DllOutput=dllwork.ExecuteCombineMemberDll(readxml,rec,huan);
							System.out.println("�����һ�ε�����ϳ�Ա��work����"+DllOutput);
							firstflag=false;
						}
						else//���������ϳ�Ա������ִ�е���dll����
						{
							firstflag=false;
							System.out.println("��ʱ��readxml.Locationflag��ֵΪ:"+readxml.Locationflag);
							if(readxml.Locationflag)//��ʾ�Ƕ������Ա
							{
								ExecuteDll(); 
								System.out.println("ִ��ExecuteDll����");
							}
							else
							{
								ExecuteDll();  
								System.out.println("ִ��ExecuteDll����");
							}
						}
					}
					else//���ǵ�һ�ε���DLL��Ա���ȵ���reflectvalue������Ȼ���ٽ��е���DLL����
					{
						if(InstanID.size()!=0)//�������������������Ϊ0ʱ���ͽ����жϴ���
						{
							reflectvalue(Attributevalue);//����Attributevalue���Ե���DLL�Ĳ������и���ֵ�Ĳ���
							for(int i=0;i<Attributevalue.size();i++)
							{
								System.out.println("Attributevalue.size()��"+Attributevalue.size());
							}
							Attributevalue.clear();//������reflectvalue����֮�����Attributevalue�����е�����
							ValueFlag=true;//ValueFlag����true��ʾ��һ������ص����������ݿ���ֱ�Ӽ�¼
						}
						System.out.println("readxml.IsCombineMember"+readxml.IsCombineMember);
						if(readxml.IsCombineMember.equals("1"))//�������ϳ�Ա��ִ����ϳ�Ա�ĵ���dll����
						{
							String finalvalue="";
							System.out.println("������ϳ�Ա��dllwork����");
							huan.clear();
							if(file.exists())
							{
								BufferedReader br = new BufferedReader(new InputStreamReader(
										new FileInputStream("test.txt")));
								for (String line = br.readLine(); line != null; line = br.readLine()) {
									huan.add(line);   
								}
								br.close();
								file.delete();
							}
							huan=method(huan);

							finalvalue=dllwork.ExecuteCombineMemberDll(readxml, valuerelation,huan);//����DLL�����õó���ֵ��finalvalue��ֵ	

							DllOutput=finalvalue;
							valuerelation.clear();
							System.out.println("ExecuteCombineMemberDll�����ֵΪ"+DllOutput);
						}
						else//���������ϳ�Ա������ִ�е���dll����
						{
							System.out.println("��ʱ��readxml.Locationflag��ֵΪ:"+readxml.Locationflag);
							if(readxml.Locationflag)//��ʾ�Ƕ������Ա
							{
								ExecuteDll(); //����ִ��DLL�ĺ���
								System.out.println("ִ��ExecuteDll����");
							}
							else
							{
								ExecuteDll();   //����ִ��DLL�ĺ���
								System.out.println("ִ��ExecuteDll����");
							}
						}
					}
					// String ת byte[]��������ΪUTF-8
					SuppliedAttributes attrs = null; // �ṩ������
					attrs = RTI.suppliedAttributesFactory().create(1);
					try {
						// /�������
						attrs.empty();
						MemberOutput = (MemID+"/"+DllOutput).getBytes("UTF-8");//ƴ�ӳ�Ա�Լ���ID��Ҫ���͵���Ϣ
						System.out.println("MemID/DllOutput"+MemID+"/"+DllOutput);
						attrs.add(p_AttributeId[0], MemberOutput);

						MemberOutput=null;
						System.out.println("readxml.FederationName"+readxml.FederationName);
						System.out.println("currentTime"+currentTime);
						String fedname=readxml.FederationName.toLowerCase();
						//						if(readxml.IsCombineMember.equals("1"))//��ϳ�Ա�����з�֮���ټ�¼
						//						{
						//							CombineMemberValue=DllOutput.split("-");
						//							System.out.println("temp[1]"+CombineMemberValue[1]);
						//							connection.Collect(MemID, CombineMemberValue[1], fedname, currentTime);
						//						}
						//						else//��ͨ��Աֱ�Ӽ�¼
						//						{
						//							connection.Collect(MemID, DllOutput, fedname, currentTime);//���ݴ洢�������������в��������ݴ������ݿ�
						//						}
						if(readxml.IsCombineMember.equals("1"))//��ϳ�Ա�����з�֮���ټ�¼
						{
							CombineMemberValue=DllOutput.split("-");
							System.out.println("temp[1]"+CombineMemberValue[1]);
							datacollection.Collection(MemID, CombineMemberValue[1], fedname, currentTime);//�����������ݼ�¼���ļ��У��ȳ������ͬһ¼�����ݿ�
						}
						else//��ͨ��Աֱ�Ӽ�¼
						{
							
							datacollection.Collection(MemID, DllOutput, fedname, currentTime);//�����������ݼ�¼���ļ��У��ȳ������ͬһ¼�����ݿ�

						}

						//!!!!��������Ӵ洢���ݵĲ���
						
					
						
						System.out.println("updateAttributeValues$$$$$$$$$$$$$$$$$$-=---------------");
						System.out.println("updateAttributeValues time is : "+currentTime);

						rtiambassador.updateAttributeValues(p_ObId[0],attrs, null,currentTime); // ��������ֵ,�ص�����
						System.out.println("�����������ֵ");
						//	System.out.println("----------------------------------------111111111111111111111");
						if(InstanID.size()!=0)
						{
							receive="";
							System.out.println("���receive�ɹ�");
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				if (!TimeAdvanceOutstanding && TimeRegulated) // ����ʱ���ƽ�&&!IsPause//�Ƿ���ʱ�����
				{
					// ʱ���ƽ����󷢳���ѭ�����ڵȴ�״̬
					// ֱ���յ�RTI�ص�Ӧ��timeAdvanceGrant()������ȷ���Ƿ�����ƽ�
					try {
						Thread.sleep(DelayTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//	rtiambassador.tick();//�����������tick������Ҫ������ʱ���ƽ�֮ǰ���л���������ʱ���ƽ�֮�����?
					System.out.println("����ʱ���ƽ�");
					//		rtiambassador.tick();
					TimeAdvanceOutstanding = true;
					rtiambassador.timeAdvanceRequest(currentTime);//ֻ��������ʱ���ƽ�RTI��������֪��Ҫ�������ݣ������÷������������ֵ�ص�����
					System.out.println("����ʱ���ƽ��ɹ�");
					if(StartFlag)
					{
						Thread.sleep(60);//�����ó���ֹͣ��Ϊ�˵�һ������ʱ�ø�������ֵ�Ļص�������ʱ���ƽ��ɹ��Ļص�����֮ǰִ��
						StartFlag=false;
					}
					//			Thread.sleep(200);//!!!!!!!!!!!!!
					//			rtiambassador.tick();
				}
				if (IsQuit)// �յ�ֹͣ����
				{
					break;
				}
			}
			if (IsQuit)// �յ�ֹͣ����
			{
				break;
			}
		}
		String fedname=readxml.FederationName.toLowerCase();
		file.delete();
		//	reader.DataRecord(fedname);//��ȡ�ı��м�¼�����ݲ��������ݿ�
		rtiambassador.resignFederationExecution(1);//�˳�����ִ��
	}
	//������Ļص�����
	public void receiveInteraction(
			int                 interactionClass, 
			ReceivedInteraction theInteraction,  
			byte[]              userSuppliedTag){
		byte[] message = null;
		byte[] para = null;
		byte[] val = null;
		System.out.println("inter+"+interactionClass);
		System.out.println("����receiveInteraction����");
		System.out.println("�յ�������");
		if(interactionClass ==_DelayUpdate)
		{
			System.out.println("�յ�Delay���������");
			for(int i=0; i < theInteraction.size();i++)
			{
				try {
					if (theInteraction.getParameterHandle(i) ==_DelayUpdateMessage)
					{
						message = theInteraction.getValueReference(i);
						DelayTime =  Integer.parseInt(new String(message));
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArrayIndexOutOfBounds e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		//		if(readxml.StepTime!=0)
		//		{
		//			if(interactionClass == _TimerUpdate)
		//			{
		//				System.out.println("�յ�Timer���������");
		//				if(IsTimerFinish)	//�жϵ�ǰ�����Ƿ��Ѿ����
		//				{
		//					StepCount++;
		//					if(StepCount>=CycleCount)
		//					{
		//						///��ʱ���Ĺ���ʵ��
		//						IsTimerFinish=false;
		//						//timeroperation.run("string"); //��������ɺ�run()������ὫIsFinish��Ϊtrue
		//						TimerDll("string");
		//					}
		//				}
		//				System.out.println("StepCount: "+StepCount);
		//			}
		//		}
		System.out.println("�ж��Ƿ��յ���ʼ�Ľ������ֵ"+_messageId[0]);
		if (interactionClass == _messageId[0]) // �յ���ʼ
		{
			testflag=true;
			loopFlag=true;
			IsPause=false;
			for (int i = 0; i < theInteraction.size(); i++) {
				// ����������Ľ������ǵ�һ�������࣬��_parameterIdText[0]��
				System.out.println("####################"+_parameterIdText[0]);
				try {
					if (theInteraction.getParameterHandle(i) == _parameterIdText[0]) {
						System.out.println("�ɹ��յ����ص�Start����");
						message = theInteraction.getValueReference(i);

						TimerCycle =  Integer.parseInt(new String(message));//��ʱ��һ�δ��������ʱ��
						if(readxml.StepTime!=0&&TimerCycle!=0)
						{
							CycleCount=(readxml.StepTime)/TimerCycle;//�ó�Ա����һ��������ʾ����������ʱ����Ա��ʾ��
						}
						System.out.println("TimerCycle:"+TimerCycle+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
						IsStart = true;
						//IstimeAdvance = true;
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArrayIndexOutOfBounds e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (interactionClass == _messageId[1]) // �յ���ͣ
		{
			System.out.println("���յ�����ͣ����");
			IsPause = true; // ��ͣ=true		
			pauseflag=true;
			loopFlag=true;
			System.out.println("��ͣ�ظ�");
		}
		if (interactionClass == _messageId[10]) // �յ����ݸ���
		{
			for(int i=0; i < theInteraction.size();i++)
			{
				try {
					if (theInteraction.getParameterHandle(i) ==_parameterIdText[10])
					{
						message = theInteraction.getValueReference(i);
						String Arry= new String(message,"UTF8");
						String paraArry[]=Arry.toString().split("#");
						for (int j = 0; j < paraArry.length; j++) {
							String memID=paraArry[j].split("-")[0];
							 if(memID.equals(readxml.ModelID)){
								 receive=paraArry[j].split("-")[1];
								init_para= receive;
								readxml.InitParameters.get(0).Value=init_para;
								 break;
							}
						}
						System.out.println("�յ���Ա���� #################          "+receive);
					}
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ArrayIndexOutOfBounds e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		if (interactionClass == _messageId[2]) // �յ�����
		{
			IsPause = false;
			restartflag=true;
			loopFlag=true;
			System.out.println("shoudao����aaaaaaa");
		}
		if (interactionClass == _messageId[3]) // �յ��˳�
		{
			IsQuit = true; // ֹͣ=true
			loopFlag=true;
		}
		////�ܶ�ʱ�����Ƶĳ�Ա��ʱ�Ľ��ܶ�ʱ������Ϣ���Դ���Ϊ��Ϣ���ݣ�

		if (interactionClass == _messageId[4]) {
			String ModelID = null;
			String receiveValue = null;
			String ModelName = null;
			for (int i = 0; i < theInteraction.size(); i++) {
				try {
					if (theInteraction.getParameterHandle(i) == _parameterIdText[4]) {
						para = theInteraction.getValueReference(i);
						ModelID = new String(para);
						if (ModelID != readxml.ModelID)
							return;
					}
				} catch (ArrayIndexOutOfBounds e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					if (theInteraction.getParameterHandle(i) == _parameterIdText[8]) {
						@SuppressWarnings("unused")
						String[] p;
						val = theInteraction.getValueReference(i);
						receiveValue = new String(val);
						p = receiveValue.split("|");
					}
				} catch (ArrayIndexOutOfBounds e) {
					e.printStackTrace();
				}

			}
			if (ModelID == readxml.ModelID) {
				for (int i = 0; i < readxml.InitParameters.size(); i++) {
					if (readxml.InitParameters.get(i).getLabel() == ModelName)// temp3�Ǵ��name��
						readxml.InitParameters.get(i).setValue(receiveValue);// temp2�Ǵ��Value��
				}
			}
		}

	}

	// ���ֶ���ʵ��
	public void discoverObjectInstance(
			int theObject,
			int theObjectClass,
			String objectName) {
		// TODO Auto-generated method stub
		InstanID.add(theObject);		//��¼�Լ������Ķ�����
		System.out.println("���ֶ�����ʵ��");
		System.out.println("�˳�Ա�����Ķ�������  "+InstanID.toString());
	}

	// ��ʼע�����ʵ��
	public void startRegistrationForObjectClass(int theClass) {
		// TODO Auto-generated method stub
		System.out.println("�ж���ͨ�ó�Ա������ĳ�Ա����");
		IsS_Object = true;
	}

	//�������������ֵ����ʱ��
	public void reflectAttributeValues(
			int theObject,
			ReflectedAttributes theAttributes, 
			LogicalTime theTime,
			byte[] userSuppliedTag,
			EventRetractionHandle retractionHandle) throws ArrayIndexOutOfBounds, InterruptedException 
			{
		for (int i = 0; i < InstanID.size(); i++) {
			System.out.println("iiiiiiiii:"+i);
			System.out.println("InstanID.size"+InstanID.size());
			String a=new String(theAttributes.getValue(i));
			System.out.println("���벻��ʱ���reflectAttributeValues����");
			receive=a;
		}
			}
	// �������������ֵ������ʱ��
	public void reflectAttributeValues(int theObject,
			ReflectedAttributes theAttributes, byte[] userSuppliedTag) {

		System.out.println("����reflectAttributeValues�ص�����");
		String temp="";
		try {
			temp=new String(theAttributes.getValueReference(0));//���ܴ��ݹ����Ĳ���
		} catch (ArrayIndexOutOfBounds e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(ValueFlag)//��һ��ʱAttributevalueû�и�ֵ�����Բ���ֱ�ӱȽϣ�������һ��Flagֵ�ж��ǲ��ǵ�һ��������Ϣ���ڶ����Լ��Ժ������ж�
		{
			Attributevalue.add(temp);//����ȡ�����ݴ���ArrayList���顣
			ValueFlag=false;
		}
		else{
			for(int i=0;i<Attributevalue.size();i++)
			{
				if(temp!=Attributevalue.get(i))//��������в����ڵ�ǰ������������
				{
					Attributevalue.add(temp);//����ȡ�����ݴ���ArrayList���顣
					Attributevalue=method(Attributevalue);
				}
			}	
		}
		System.out.println("Attributevalue[i] : ####"+Attributevalue);
	}

	// ����ʱ����ƵĻص�������RTI��Ӧ����Ա��ʱ���������
	public void timeRegulationEnabled(LogicalTime theFederateTime) {
		// TODO Auto-generated method stub
		System.out.println("����timeRegulationEnabled����");
		TimeRegulated = true;
	}
	// ����ʱ��Լ���Ļص�������RTI��Ӧ����Ա��ʱ��Լ������
	public void timeConstrainedEnabled(LogicalTime theFederateTime) {
		// TODO Auto-generated method stub
		TimeConstrained = true;
	}

	// ʱ���ƽ��Ļص�������RTI��Ӧ����Ա��ʱ���ƽ�����
	public void timeAdvanceGrant(LogicalTime theTime) {
		System.out.println("����timeAdvanceGrant����");
		//TODO Auto-generated method stub
		System.out.println("InstanID.size()                "+InstanID.size());
		TimeAdvanceOutstanding = false;
		flagtimeadvance=true;
		System.out.println("����ʱ���ƽ��Ļص�����---------------------------------------------------------------------");
	}

	public void turnInteractionsOn (
			int theHandle)
	{
		System.out.println("�ж���Member���������");
	}
	public void turnUpdatesOnForObjectInstance (
			int theObject,
			AttributeHandleSet theAttributes)
	{
		System.out.println("���Կ�ʼ��������ֵ");
	}
	public void stopRegistrationForObjectClass(int theClass) {
		// TODO Auto-generated method stub
		System.out.println("�ж���ͨ�ó�Ա�ĳ�Ա�˳�");
	}

	//	public void TimerDll(String str)
	//	{
	//		try {
	//			Thread.sleep(DelayTime);
	//		} catch (InterruptedException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		System.out.println("Timer���ڴ����С�������������");
	//		StepCount=0;
	//		System.out.println("Timer�������������������");
	//		IsTimerFinish=true;
	//	}
	// //Dll����
	public static void ExecuteDll() throws InterruptedException
	{
		try {

			System.out.println("||||||||||||||||||||||||||||||||receive"+receive);
			DLLInput = receive.trim();
			System.out.println("dll"+DLLInput);
			readxml.jnative.setParameter(0, readxml.InitParameters.get(0).Value);//��һ��������Initֵ
			System.out.println("����DLL�ĵ�һ��������"+readxml.InitParameters.get(0).Value);
			readxml.jnative.setParameter(1, DLLInput);//�ڶ���������Inputֵ
			System.out.println("����DLL�ĵڶ���������"+DLLInput);
			readxml.jnative.invoke(); // ����ִ��
			DllOutput = readxml.jnative.getRetVal().trim(); // ��ȡDll������ֵ
			System.out.println("����DLL��ֵΪ��"+DllOutput);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			Thread.sleep(10000);
			System.out.println("e.printStackTrace()"+e.toString());
		} catch (NativeException e) {
			// TODO Auto-generated catch block
			Thread.sleep(10000);
			System.out.println("e.printStackTrace()"+e.toString());
		}

	}

	public void reflectvalue(ArrayList<String> attributevalue)//�����attributevalue�Ǳ���Ա������������Ϣ��һ��String��������
	{
		System.out.println("currentTime:---------------------------------"+currentTime);
		String[] normaltempValue = null;//��¼��ͨ��Ա�յ����ݵ�����
		String[] combinetempValue=null;//��¼��ϳ�Ա�յ����ݵ�����
		String[] ValueHandle=null;//���ڴ�����ϳ�Ա�����������
		System.out.println("����reflectvalue����");
		System.out.println("333333readxml.Locationflag333333333"+readxml.Locationflag);

		if(readxml.Locationflag==true)//LocationflagΪtrue���ж����ж��������//������
		{
			for(int s=0;s<attributevalue.size();s++)//���forѭ���ǽ�attributevalue�������м�ֵ����Ϣ����˳��ļӵ�message���������
			{
				normaltempValue=attributevalue.get(s).split("/");//����ȡ����ֵ����ֲ���
				for(int i=0;i<readxml.Location.size();i++)
				{
					String[] Location=readxml.Location.get(i).split("/");//�����Location��¼���Ǳ���Ա�����Ķ�����������Ϣ�����ƴ�ӵ�
					if(Location[0].equals(normaltempValue[0]))//�ж��Ƿ����Լ��������Ķ������ID��Location�е�ID��ͬ
					{
						int b=Integer.parseInt(Location[1]);//�������Ա��λ����Ϣ��Location�ж�ȡ��Location���ڶ�ȡXMLʱ��ȡ��ֵ
						System.out.println("b ��Ϣ��λ����ϢΪ:"+b);
						if(b>message.size())//�����ж��ٸ�λ����Ϣ���ȿ������Ϣλ��������������ǰ�����"0"��Ȼ������Ϣ���뵽�÷ŵĵط�
						{
							for(int j=message.size();j<b;j++)
							{
								message.add("0");
							}
							message.remove(b-1);
							message.add(b-1,normaltempValue[1]);
							System.out.println(message);
						}
						else
						{
							message.remove(b-1);
							message.add(b-1,normaltempValue[1]);
							System.out.println(message);
						}
					}
				}
			}
			String temp="";
			System.out.println("message.length : "+message.size());
			for(int i=0;i<message.size();i++)// ��Ĭ��������Ϣ�����˵�����£������е�����ȫ��ƴ���������������
			{
				if((i+1)==message.size())
				{
					temp=temp+message.get(i);
				}
				else
				{
					temp=temp+message.get(i)+"/";
				}
				System.out.println("ƴ��֮���temp��ֵΪ   "+temp);
			}
			receive=temp;
			temp="";
		}
		else if(readxml.IsCombineMember.equals("1"))//�������ϳ�Ա
		{
			if(attributevalue.size()>=InstanID.size())//������������ݱȳ�Ա���������ݶ�����������
			{
				tempvalue.clear();
				for(int i=0;i<attributevalue.size();i++)
				{
					tempvalue.add(attributevalue.get(i));
				}
				System.out.println("tempvalue:"+tempvalue);
				for(int i=0;i<attributevalue.size();i++)
				{
					combinetempValue=attributevalue.get(i).split("/");
					//ValueRecode=combinetempValue;
					for(int j=0;j<readxml.map.TargetInput.size();j++)
					{
						String[] Member=readxml.map.TargetInput.get(j).split("-");
						String[] newvalue=null;
						if(combinetempValue[0].equals(Member[0]))//����������������ǵ�ǰ��Ա��Ҫ��
						{
							System.out.println("name1[1]"+combinetempValue[1]);
							newvalue=combinetempValue[1].split("@");
							System.out.println("newvalue[0]"+newvalue[0]);
							if(newvalue[0].equals("C#M"))//�������ϳ�Ա����������Ϣ
							{
								if(MemID.equals(newvalue[1].split("-")[0])){
									String value=Member[1]+"-"+newvalue[1].split("-")[1];//Member[1]��D1�����name[1]��C#M����name[2]�Ǵ�������ֵ
									System.out.println("value"+value);
									valuerelation.add(value);
									System.out.println("����DllOutput��ֵΪ%%%%%%%%%%%:"+valuerelation);
								}
							}
							else//����ϳ�Ա����������Ϣ
							{
								String value=Member[1]+"-"+combinetempValue[1];//����value��ֵΪD1-value
								System.out.println("value"+value);
								valuerelation.add(value);
								System.out.println("����DllOutput��ֵΪ%%%%%%%%%%%:"+valuerelation);
							}
						}

					}	
				}
			}
			else//ȱ��ֵ�����
			{
				System.out.println("ȱ��ֵ������һ����ֵ��Ϊ�������д���");
				System.out.println("tempvalue:"+tempvalue.toString());
				for(int i=0;i<tempvalue.size();i++)
				{
					attributevalue.add(tempvalue.get(i));
				}
				System.out.println("attributevalue:"+attributevalue.toString());
				for(int i=0;i<attributevalue.size();i++)
				{
					combinetempValue=attributevalue.get(i).split("/");
					//ValueRecode=combinetempValue;
					for(int j=0;j<readxml.map.TargetInput.size();j++)
					{
						String[] Member=readxml.map.TargetInput.get(j).split("-");
						String[] newvalue=null;
						if(combinetempValue[0].equals(Member[0]))//����������������ǵ�ǰ��Ա��Ҫ��
						{
							System.out.println("name1[1]"+combinetempValue[1]);
							newvalue=combinetempValue[1].split("@");
							System.out.println("newvalue[0]"+newvalue[0]);
							if(newvalue[0].equals("C#M"))//�������ϳ�Ա����������Ϣ
							{
								if(MemID.equals(newvalue[1].split("-")[0])){
									String value=Member[1]+"-"+newvalue[1].split("-")[1];//Member[1]��D1�����name[1]��C#M����name[2]�Ǵ�������ֵ
									System.out.println("value"+value);
									valuerelation.add(value);
									System.out.println("����DllOutput��ֵΪ%%%%%%%%%%%:"+valuerelation);
								}
							}
							else//����ϳ�Ա����������Ϣ
							{
								String value=Member[1]+"-"+combinetempValue[1];//����value��ֵΪD1-value
								System.out.println("value"+value);
								valuerelation.add(value);
								System.out.println("����DllOutput��ֵΪ%%%%%%%%%%%:"+valuerelation);
							}
						}

					}
					//			else
					//			{
					//				combinetempValue=ValueRecode;//�������������ϢΪ�գ�����һ���õ������ݴ�����һ��
					//				System.out.println("����ȱʧ��ʹ����һ�����ݣ���һ������Ϊ��"+combinetempValue.toString());
					//			}
					//			for(int i=0;i<readxml.map.TargetInput.size();i++)
					//			{
					//				String[] Member=readxml.map.TargetInput.get(i).split("-");
					//				String[] newvalue=null;
					//				if(combinetempValue[0].equals(Member[0]))//����������������ǵ�ǰ��Ա��Ҫ��
					//				{
					//					System.out.println("name1[1]"+combinetempValue[1]);
					//					newvalue=combinetempValue[1].split("@");
					//					System.out.println("newvalue[0]"+newvalue[0]);
					//					if(newvalue[0].equals("C#M"))//�������ϳ�Ա����������Ϣ
					//					{
					//						if(MemID.equals(newvalue[1].split("-")[0])){
					//							String value=Member[1]+"-"+newvalue[1].split("-")[1];//Member[1]��D1�����name[1]��C#M����name[2]�Ǵ�������ֵ
					//							System.out.println("value"+value);
					//							valuerelation.add(value);
					//						}
					//					}
					//					else//����ϳ�Ա����������Ϣ
					//					{
					//						String value=Member[1]+"-"+combinetempValue[1];//����value��ֵΪD1-value
					//						System.out.println("value"+value);
					//						valuerelation.add(value);
					//					}
					//				}
					//			}		

				}
			}}
		else//�������������//������
		{
			if(readxml.IsCombineMember.equals("1"))//�������ϳ�Ա
			{
				String a=null;
				String relation[]=null;
				String targetInput[]=null;
				if(attributevalue.size()!=0)
				{
					relation=attributevalue.get(0).split("/");
					ValueRecode=relation;
				}
				else
				{
					relation=ValueRecode;
					System.out.println("����ȱʧ��ʹ����һ�����ݣ���һ������Ϊ��"+relation.toString());
				}
				for(int i=0;i<readxml.map.TargetInput.size();i++)
				{
					targetInput=readxml.map.TargetInput.get(i).split("-");
					String value=null;
					String [] combo=null;
					System.out.println("relation[1]"+relation[1]);
					combo=relation[1].split("@");
					System.out.println("combo[0]"+combo[0]);
					if(targetInput[0].equals(relation[0]))//����������ĵ�һ��������TargetInput����ĵ�һ��ֵ��ͬ
					{
						if(combo[0].equals("C#M"))//"C#M"��ʾ����ϳ�Ա����������Ϣ
						{
							value=targetInput[1]+"-"+combo[1];
						}
						else//������ϳ�Ա��������Ϣ
						{
							value=targetInput[1]+"-"+relation[1];//value��ֵ����Ҫ���ڵ�����ϳ�Ա��dll��ֵ��value�ĸ�ʽӦΪD1-�������ĳ�Ա��������ֵ
							//System.out.println("��ϳ�Ա����dll��ֵΪ��"+value);
						}
						valuerelation.add(value);
						combo=null;
					}
					else//���else���������Եģ����ƥ��ʧ�����������ֵ
					{
						System.out.println("targetInput[0]"+targetInput[0]);
						System.out.println("relation[0]"+relation[0]);
					}
				}
			}
			else{//�����ǵ������ҷ���ϳ�Ա
				String[] receiveValue = null;
				String str[]=null;
				String member[]=null;	
				String TargetID[]=null;
				if(attributevalue.size()!=0)
				{
					receiveValue=attributevalue.get(0).split("/");
					ValueRecode=receiveValue;
				}
				else
				{
					receiveValue=ValueRecode;
					System.out.println("����ȱʧ��ʹ����һ�����ݣ���һ������Ϊ��"+receiveValue.toString());
				}
				System.out.println("name11[1]"+receiveValue[1]);
				member=receiveValue[1].split("@");
				System.out.println("member"+member);
				if(member[0].equals("C#M"))//�������ϳ�Ա����������Ϣ�����C#M�����ֵȡ���������dll
				{
					str=member[1].split(",");
					for(int i=0;i<str.length;i++)//�ж��ٸ���Ϣ���á�-������
					{
						TargetID=str[i].split("-");
						System.out.println("TargetID:"+TargetID.toString());
						if(TargetID[0].equals(MemID))
						{
							receive=TargetID[1];
							System.out.println("���յ���receiveֵΪ:"+receive);
						}
					}

				}
				else//�����ʾ����ͨ�ĳ�Ա�Ͷ������Ա����������Ϣ
				{
					receive=member[0];
					System.out.println("���յ���receiveֵΪ:"+receive);
				}
			}
			System.out.println("receive-----------------"+receive);
		}
	}
	public static ArrayList method(ArrayList array) {
		ArrayList arr = new ArrayList();
		Iterator it = array.iterator();
		while(it.hasNext()){
			Object obj = it.next();
			if(!arr.contains(obj))

				arr.add(obj);
		}
		return arr;
	}
}
