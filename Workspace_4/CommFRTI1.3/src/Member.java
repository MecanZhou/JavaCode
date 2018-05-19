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
	private RTIambassador rtiambassador;// RTIambassador是一个接口，定义一个接口对象
	public int p_ObjectId[] = new int[10];
	public int p_AttributeId[] = new int[20]; // dObjectId[i]  
	public int s_ObjectId[] = new int[10];
	public int s_AttributeId[] = new int[20];
	public String fileName="test.txt";
	public	File file = new File(fileName);
	public int p_ObId[] = new int[10];// 注册对象实例返回的int值
	public int d_ObId[] = new int[10]; // 对象回调
	public static int s_AttributeIdNumber = 0; // 总共订购的对象类的属性总数目
	//public static int receiveNumber = 0;
	public int p_attributeIdText[] = new int[10];
	public int _messageId[] = new int[20];// 订购管控端用来操作成员操作的交互类
	public int _parameterIdText[] = new int[20];// 订购管控端用来操作成员操作的交互类的属性句柄
    
	public int _DelayUpdate;//延时推进的交互类句柄
	public int _DelayUpdateMessage;//更新延时推进时间的交互类属性值
	public int DelayTime;//延时推进的时间
	public int _ReplyDelayUpdate;//回应延时的交互类句柄
	public int _ReplyDelayUpdateMessage;//回应延时的交互类属性

	static TimerOperation timeroperation;//实例化一个定时器对象
	public int _TimerUpdate;//定时器的交互类句柄
	public int _TimerUpdateMessage;//定时器的交互类属性值
	public boolean IsTimerFinish=true;
	public int _ReplyTimerUpdate;//回应定时器的交互类句柄
	public int _ReplyTimerUpdateMessage;//回应定时器的交互类属性值
	//public boolean IsTimeroperation=true;

	public ArrayList<Integer> InstanID = new ArrayList<Integer>();//记录该成员订购的成员（对象类）的个数
	// 时间控制时间受限变量
	boolean TimeRegulated = false;
	boolean TimeConstrained = false;
	boolean TimeAdvanceOutstanding = false;

	//逻辑时间， 把从xml文件中读出的Lookaheads赋值给它们
	LogicalTime currentTime;//联邦当前时间
	LogicalTimeInterval lookhead; //时间前瞻量

	public boolean IsFirst = true;
	public boolean IsStart = false;// 开始仿真的Flag
	public boolean IsQuit = false; // 暂停仿真的Flag
	public boolean IsS_Object = false; // IsS_Object是否存在对象类的订购者

	public boolean registrationFlag = false;//控制成员是否注册对象类属性值成功的Flag
	public boolean IsPause = true; // 成员是否暂停的Flag
	public boolean IsContinue = false;//成员是否继续的Flag
	//	public boolean IstimeAdvance = false; // bj2
	public static String MemID = null;//成员的ID号
	public static String XmlPath = null;//通用成员读取的XML的路径
	public static String DllPathList = null;//通用成员加载的DLL的路径
	public static String DllNameList = null;//所要加载DLL的名字
	public static String rtiHost = null;//RTI服务器的IP地址

	public static String DLLInput = null;//DLL的输入
	public static String DllOutput = null;//DLL的输出
	//public static String TemporaryStorage = null;
	public static byte[] MemberOutput = null;//记录成员输出的byte数组
	public static String receive = "";//用于记录成员调用DLL所需参数
	public static int TimerCycle;  //成员的StepTime的公约数
	public static int StepCount=0; 
	public static int CycleCount=0;

	public static boolean IsDllbefor = true;
	public static boolean  firstflag=true;
	public static boolean doubleflag=true;
	static Read_Xml readxml = new Read_Xml();
	public boolean testflag=false;
	public boolean pauseflag=false;//判断是否暂停成员的flag
	public boolean restartflag=false;//判断是否继续成员的flag
	//public static int RTIID=0;
	public boolean loopFlag=false;//控制成员循环运行的Flag
	public boolean flagtimeadvance=true;//控制成员循环运行的Flag

	//String[] message=new String[2];
	ArrayList<String > message=new ArrayList<String>();
	ArrayList<String > valuerelation=new ArrayList<String>();
	ArrayList<String> tempvalue=new ArrayList<String>();
	DLLhandle dllwork=new DLLhandle();
	ArrayList< String> Attributevalue=new ArrayList<String>();

	public boolean ValueFlag=true;//用来区分是否数据是第一次处理的Flag
	public boolean StartFlag=true;//这个FLAG值是为了让普通成员第一步有足够的时间去处理回调函数，而不是直接进入reflectValue函数，后面的延迟操作就由tick函数完成
	String [] ValueRecode;//记录成员所受到的数据
	String [] CombineMemberValue;//记录组合成员数据的数组
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
			readxml.LinkDll(MemID, XmlPath, DllPathList, dllmessage);// 连接DLL方法
		}
		readxml.ReadXml(XmlPath, MemID); // /读取XML文件
		System.out.println("接收成员初始值");
//		if(readxml.InitParameters.size()!=0)//如果InitParameters有值则读取，没有值则不用读取
//		{
//			receive =readxml.InitParameters.get(0).getValue();///????????????????????????????改了程序后这里可能出现错误！！！！！
//			System.out.println("初始值receive.trim()为"+receive.trim());
//		}
		if(readxml.Inputsource.size()!=0)//成员的初始值是从Display节点中读取出来的
		{
			for(int i =0; i<readxml.Inputsource.size();i++)
			{
				//将获取到的输入以"/"拼接起来
				if((i+1)==readxml.Inputsource.size())// 如果是最后一个元素
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
		rtiambassador = RTI.getRTIambassador(rtiHost, 8989); // 初始化RTI大使
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
		System.out.println("加入联邦成功");

		System.out.println("Member--aaabbbccc: "+readxml.FederateName+MemID);
		System.out.println("readxml.IsTimeRegulating"+readxml.IsTimeRegulating);
		lookhead=new LogicalTimeIntervalDouble(0.1);
		currentTime = new LogicalTimeDouble(0.0);
		String temp1 = "Model" + readxml.ModelID;
		AttributeHandleSet attributes2 = RTI.attributeHandleSetFactory().create();
		System.out.println("本成员为"+temp1);
		for(int i=0;i<readxml.Dllname.MemberDLLName.size();i++)
		{
			String [] MemberDLLName=null;
			MemberDLLName=readxml.Dllname.MemberDLLName.get(i).split("-");
			System.load("C://HLA//"+readxml.FederationName+"//"+temp1+"//"+MemberDLLName[1]);
			System.out.println("加载"+readxml.Dllname.MemberDLLName.get(i)+"成功");
		}
		System.out.println("readxml.p_Objects.size()"+readxml.p_Objects.size());
		p_ObjectId[0] = rtiambassador.getObjectClassHandle(temp1); // 读取对象类的ID,暂时改为写死的程序！！！！！！！！！
		System.out.println("p_ObjectId[0]  !1111"+p_ObjectId[0]);
		p_AttributeId[0] = rtiambassador.getAttributeHandle(
				"isOutput", p_ObjectId[0]);
		attributes2.add(p_AttributeId[0]); // 把属性句柄加入属性句柄集
		rtiambassador.publishObjectClass(p_ObjectId[0], attributes2);
		System.out.println("公布对象类成功");
		attributes2.empty();
		////////////订购XMl里面subscribe的值
		String ModelName = null;
		AttributeHandleSet attributes3 = RTI.attributeHandleSetFactory().create();
		int index = 0;
		for (int i = 0; i < readxml.S_Objects.size(); i++)
		{
			System.out.println("进入readxmld的循环");
			ModelName = "Model" + readxml.S_Objects.get(i).ObjectID.get(i);
			System.out.println("订购的对象类ID为"+ModelName);
			s_ObjectId[i] = rtiambassador.getObjectClassHandle(ModelName);
			//	readxml.S_Objects=method(readxml.S_Objects);//将readxml.S_Objects重复的部分去掉
			System.out.println("Attributes:"+readxml.S_Objects.get(i).Attributes.size());
			for (int j = 0; j < readxml.S_Objects.get(i).Attributes.size(); j++) {
				//				s_AttributeId[index] = rtiambassador.getAttributeHandle(
				//						readxml.S_Objects.get(i).Attributes.get(j),
				//						s_ObjectId[i]);
				s_AttributeId[index] = rtiambassador.getAttributeHandle("isOutput", s_ObjectId[i]);
				attributes3.add(s_AttributeId[index]);
				index++;
			}
			System.out.println("对象类订购完毕");
			//	attributes3.add("isOutput");
			rtiambassador.subscribeObjectClassAttributes(s_ObjectId[i],attributes3);
			attributes3.empty();
		}
		s_AttributeIdNumber = index;
		// 开始订购管控端的交互类
		// 订购交互类Start，开始仿真

		_messageId[0] = rtiambassador.getInteractionClassHandle("Start");
		System.out.println("_messageId[0]的值为"+_messageId[0]);
		_parameterIdText[0] = rtiambassador.getParameterHandle("SMessage",
				_messageId[0]);

		System.out.println("订购交互类Start，准备开始仿真");
		rtiambassador.subscribeInteractionClass(_messageId[0]);

		// 订购交互类Pause，暂停仿真

		_messageId[1] = rtiambassador.getInteractionClassHandle("Pause");

		_parameterIdText[1] = rtiambassador.getParameterHandle("PMessage",
				_messageId[1]);

		rtiambassador.subscribeInteractionClass(_messageId[1]);

		// 继续
		_messageId[2] = rtiambassador.getInteractionClassHandle("Continue");

		_parameterIdText[2] = rtiambassador.getParameterHandle("CMessage",
				_messageId[2]);

		rtiambassador.subscribeInteractionClass(_messageId[2]);

		// 订购交互类Quit，退出仿真

		_messageId[3] = rtiambassador.getInteractionClassHandle("Quit");

		_parameterIdText[3] = rtiambassador.getParameterHandle("QMessage",
				_messageId[3]);

		rtiambassador.subscribeInteractionClass(_messageId[3]);
		//订购数据更改
		_messageId[10]=rtiambassador.getInteractionClassHandle("Modify");
		
		_parameterIdText[10]=rtiambassador.getParameterHandle("MPMessage", _messageId[10]);
		rtiambassador.subscribeInteractionClass(_messageId[10]);
		// 刷新

		_messageId[4] = rtiambassador.getInteractionClassHandle("Refresh");

		_parameterIdText[4] = rtiambassador.getParameterHandle("RMessage",
				_messageId[4]);

		rtiambassador.subscribeInteractionClass(_messageId[4]);

		// 公布交互类 ReplySart

		_messageId[5] = rtiambassador.getInteractionClassHandle("ReplyStart");

		_parameterIdText[5] = rtiambassador.getParameterHandle("RSMessage",
				_messageId[5]);

		rtiambassador.publishInteractionClass(_messageId[5]);

		// 公布交互类 ReplyStop，仿真暂停应答

		_messageId[6] = rtiambassador.getInteractionClassHandle("ReplyPause");

		_parameterIdText[6] = rtiambassador.getParameterHandle("RPMessage",
				_messageId[6]);

		rtiambassador.publishInteractionClass(_messageId[6]);

		_messageId[7] = rtiambassador
				.getInteractionClassHandle("ReplyContinue");

		_parameterIdText[7] = rtiambassador.getParameterHandle("RCMessage",
				_messageId[7]);

		rtiambassador.publishInteractionClass(_messageId[7]);

		// 公布交互类AdjustModelParameters,调节仿真参数值

		_messageId[8] = rtiambassador.getInteractionClassHandle("ReplyRefresh");

		_parameterIdText[8] = rtiambassador.getParameterHandle("RRMessage",
				_messageId[8]);

		rtiambassador.publishInteractionClass(_messageId[8]);

		_messageId[9] = rtiambassador.getInteractionClassHandle("ReplyQuit");

		_parameterIdText[9] = rtiambassador.getParameterHandle("RQMessage",
				_messageId[9]);

		rtiambassador.publishInteractionClass(_messageId[9]);


		//延时，改变一步推进的时间
		_DelayUpdate = rtiambassador.getInteractionClassHandle("DelayUpdate");

		_DelayUpdateMessage = rtiambassador.getParameterHandle("DMessage",_DelayUpdate);

		rtiambassador.subscribeInteractionClass(_DelayUpdateMessage);


		_ReplyDelayUpdate = rtiambassador.getInteractionClassHandle("ReplyDelayUpdate");

		_ReplyDelayUpdateMessage = rtiambassador.getParameterHandle("RDMessage",_ReplyDelayUpdate);

		rtiambassador.publishInteractionClass(_ReplyDelayUpdateMessage);

		System.out.println("通用成员的Start状态  "+IsStart);
		while (loopFlag) {

			if (IsStart)// 若开始仿真
			{
				System.out.println("通用成员的Start状态为"+IsStart+"  开始发送交互类");
				// 交互类属性句柄集
				SuppliedParameters parameters = RTI.suppliedParametersFactory()
						.create(1);
				parameters.add(_parameterIdText[5], "start".getBytes());
				rtiambassador.sendInteraction(_messageId[5], parameters, null);
				System.out.println("成功发送交互类");
				IsStart = false;
				break;
				
			}
			if (IsQuit) {
				break;
			}
		}
		// ////////

		// 开始仿真////////////////////////

		while (!IsQuit) // 没有收到退出命令
		{
			if(testflag)//把成员规定为时间控制和时间受限
			{
				rtiambassador.enableTimeRegulation(currentTime, lookhead);
				while(!TimeRegulated)
				{
					rtiambassador.tick();//给服务器充足的时间去设置为时间控制
				}
				rtiambassador.enableTimeConstrained();
				while(!TimeConstrained)
				{
					rtiambassador.tick();//给服务器充足的时间去设置为时间受限
				}
				testflag=false;
				System.out.println("修改成员时间管理方式成功");
			}
			if(pauseflag)//暂停成员
			{
				rtiambassador.disableTimeConstrained();
				rtiambassador.disableTimeRegulation();
				SuppliedParameters parameters = RTI.suppliedParametersFactory()
						.create(0);
				parameters.add(_parameterIdText[6], "Pause".getBytes());
				rtiambassador.sendInteraction(_messageId[6], parameters, null);
				pauseflag=false;
			}	
			if(restartflag)//继续成员
			{
				rtiambassador.enableTimeRegulation(currentTime, lookhead);
				while(!TimeRegulated)
				{
					rtiambassador.tick();//给服务器充足的时间去设置为时间控制
				}
				rtiambassador.enableTimeConstrained();
				while(!TimeConstrained)
				{
					rtiambassador.tick();//给服务器充足的时间去设置为时间受限
				}
				SuppliedParameters parameters = RTI.suppliedParametersFactory()
						.create(0);
				parameters.add(_parameterIdText[7], "Continue".getBytes());
				rtiambassador.sendInteraction(_messageId[7], parameters, null);
				restartflag=false;
			}
			//System.out.println("IsS_Objectaaaaaaaaaaa:"+IsS_Object);
			if (IsS_Object && !IsPause) // 如果存在对象类订购者，则开始注册对象实例
			{
				System.out.println("p_ObjectId[0]"+p_ObjectId[0] );
				p_ObId[0] = rtiambassador.registerObjectInstance(
						p_ObjectId[0], readxml.ModelName+readxml.ModelID);
				System.out.println("p_ObId[0] "+p_ObId[0] );
				System.out.println("注册对象类实例成功"+readxml.ModelName);
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

				if(registrationFlag) // 注册后才能更新&&!IsPause
				{
					System.out.println(currentTime);
					if(firstflag)//第一次所有成员用初始数据调用DLL
					{
						System.out.println("readxml.IsCombineMember"+readxml.IsCombineMember);
						if(readxml.IsCombineMember.equals("1"))//如果是组合成员，执行组合成员的调用dll函数
						{
							System.out.println("获取readxml.IsCombineMember的值成功");
							ArrayList<String> rec=new ArrayList<String>();
							//							for(int i=0;i<readxml.map.TargetInput.size();i++)
							//							{
							//								Member.receive=readxml.map.TargetInput.get(i).split("-")[1]+"-"+Member.receive;
							//								System.out.println("Member.rec"+Member.receive);
							//							}
							//							rec.add(Member.receive);//现在的组合成员第一步不用传入初始值

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
							System.out.println("进入第一次调用组合成员的work函数"+DllOutput);
							firstflag=false;
						}
						else//如果不是组合成员，正常执行调用dll函数
						{
							firstflag=false;
							System.out.println("此时的readxml.Locationflag的值为:"+readxml.Locationflag);
							if(readxml.Locationflag)//表示是多输入成员
							{
								ExecuteDll(); 
								System.out.println("执行ExecuteDll函数");
							}
							else
							{
								ExecuteDll();  
								System.out.println("执行ExecuteDll函数");
							}
						}
					}
					else//不是第一次调用DLL成员就先调用reflectvalue函数，然后再进行调用DLL处理
					{
						if(InstanID.size()!=0)//当订购对象类的数量不为0时，就进入判断处理
						{
							reflectvalue(Attributevalue);//处理Attributevalue并对调用DLL的参数进行赋新值的操作
							for(int i=0;i<Attributevalue.size();i++)
							{
								System.out.println("Attributevalue.size()："+Attributevalue.size());
							}
							Attributevalue.clear();//调用了reflectvalue函数之后，清空Attributevalue数组中的数据
							ValueFlag=true;//ValueFlag等于true表示第一个进入回调函数的数据可以直接记录
						}
						System.out.println("readxml.IsCombineMember"+readxml.IsCombineMember);
						if(readxml.IsCombineMember.equals("1"))//如果是组合成员，执行组合成员的调用dll函数
						{
							String finalvalue="";
							System.out.println("进入组合成员的dllwork函数");
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

							finalvalue=dllwork.ExecuteCombineMemberDll(readxml, valuerelation,huan);//调用DLL，并用得出的值给finalvalue赋值	

							DllOutput=finalvalue;
							valuerelation.clear();
							System.out.println("ExecuteCombineMemberDll输出的值为"+DllOutput);
						}
						else//如果不是组合成员，正常执行调用dll函数
						{
							System.out.println("此时的readxml.Locationflag的值为:"+readxml.Locationflag);
							if(readxml.Locationflag)//表示是多输入成员
							{
								ExecuteDll(); //调用执行DLL的函数
								System.out.println("执行ExecuteDll函数");
							}
							else
							{
								ExecuteDll();   //调用执行DLL的函数
								System.out.println("执行ExecuteDll函数");
							}
						}
					}
					// String 转 byte[]，并编码为UTF-8
					SuppliedAttributes attrs = null; // 提供的属性
					attrs = RTI.suppliedAttributesFactory().create(1);
					try {
						// /添加属性
						attrs.empty();
						MemberOutput = (MemID+"/"+DllOutput).getBytes("UTF-8");//拼接成员自己的ID和要发送的消息
						System.out.println("MemID/DllOutput"+MemID+"/"+DllOutput);
						attrs.add(p_AttributeId[0], MemberOutput);

						MemberOutput=null;
						System.out.println("readxml.FederationName"+readxml.FederationName);
						System.out.println("currentTime"+currentTime);
						String fedname=readxml.FederationName.toLowerCase();
						//						if(readxml.IsCombineMember.equals("1"))//组合成员数据切分之后再记录
						//						{
						//							CombineMemberValue=DllOutput.split("-");
						//							System.out.println("temp[1]"+CombineMemberValue[1]);
						//							connection.Collect(MemID, CombineMemberValue[1], fedname, currentTime);
						//						}
						//						else//普通成员直接记录
						//						{
						//							connection.Collect(MemID, DllOutput, fedname, currentTime);//数据存储操作，将运行中产生的数据存入数据库
						//						}
						if(readxml.IsCombineMember.equals("1"))//组合成员数据切分之后再记录
						{
							CombineMemberValue=DllOutput.split("-");
							System.out.println("temp[1]"+CombineMemberValue[1]);
							datacollection.Collection(MemID, CombineMemberValue[1], fedname, currentTime);//将产生的数据记录在文件中，等程序结束同一录入数据库
						}
						else//普通成员直接记录
						{
							
							datacollection.Collection(MemID, DllOutput, fedname, currentTime);//将产生的数据记录在文件中，等程序结束同一录入数据库

						}

						//!!!!这里需添加存储数据的操作
						
					
						
						System.out.println("updateAttributeValues$$$$$$$$$$$$$$$$$$-=---------------");
						System.out.println("updateAttributeValues time is : "+currentTime);

						rtiambassador.updateAttributeValues(p_ObId[0],attrs, null,currentTime); // 更新属性值,回调函数
						System.out.println("请求更新属性值");
						//	System.out.println("----------------------------------------111111111111111111111");
						if(InstanID.size()!=0)
						{
							receive="";
							System.out.println("清空receive成功");
						}
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				if (!TimeAdvanceOutstanding && TimeRegulated) // 请求时间推进&&!IsPause//是否是时间控制
				{
					// 时间推进请求发出后，循环处于等带状态
					// 直到收到RTI回调应答timeAdvanceGrant()方法来确定是否继续推进
					try {
						Thread.sleep(DelayTime);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//	rtiambassador.tick();//这里的问题是tick函数是要在请求时间推进之前进行还是在请求时间推进之后进行?
					System.out.println("请求时间推进");
					//		rtiambassador.tick();
					TimeAdvanceOutstanding = true;
					rtiambassador.timeAdvanceRequest(currentTime);//只有请求了时间推进RTI服务器才知道要接收数据，即调用反射对象类属性值回调函数
					System.out.println("请求时间推进成功");
					if(StartFlag)
					{
						Thread.sleep(60);//这里让程序停止是为了第一步运行时让更新属性值的回调函数在时间推进成功的回调函数之前执行
						StartFlag=false;
					}
					//			Thread.sleep(200);//!!!!!!!!!!!!!
					//			rtiambassador.tick();
				}
				if (IsQuit)// 收到停止命令
				{
					break;
				}
			}
			if (IsQuit)// 收到停止命令
			{
				break;
			}
		}
		String fedname=readxml.FederationName.toLowerCase();
		file.delete();
		//	reader.DataRecord(fedname);//读取文本中记录的数据并存入数据库
		rtiambassador.resignFederationExecution(1);//退出联邦执行
	}
	//交互类的回调函数
	public void receiveInteraction(
			int                 interactionClass, 
			ReceivedInteraction theInteraction,  
			byte[]              userSuppliedTag){
		byte[] message = null;
		byte[] para = null;
		byte[] val = null;
		System.out.println("inter+"+interactionClass);
		System.out.println("进入receiveInteraction函数");
		System.out.println("收到交互类");
		if(interactionClass ==_DelayUpdate)
		{
			System.out.println("收到Delay命令。。。。");
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
		//				System.out.println("收到Timer命令。。。。");
		//				if(IsTimerFinish)	//判断当前任务是否已经完成
		//				{
		//					StepCount++;
		//					if(StepCount>=CycleCount)
		//					{
		//						///定时器的功能实现
		//						IsTimerFinish=false;
		//						//timeroperation.run("string"); //在任务完成后run()方法便会将IsFinish变为true
		//						TimerDll("string");
		//					}
		//				}
		//				System.out.println("StepCount: "+StepCount);
		//			}
		//		}
		System.out.println("判断是否收到开始的交互类的值"+_messageId[0]);
		if (interactionClass == _messageId[0]) // 收到开始
		{
			testflag=true;
			loopFlag=true;
			IsPause=false;
			for (int i = 0; i < theInteraction.size(); i++) {
				// 如果发过来的交互类是第一个交互类，即_parameterIdText[0]，
				System.out.println("####################"+_parameterIdText[0]);
				try {
					if (theInteraction.getParameterHandle(i) == _parameterIdText[0]) {
						System.out.println("成功收到基地的Start命令");
						message = theInteraction.getValueReference(i);

						TimerCycle =  Integer.parseInt(new String(message));//定时器一次触发所需的时间
						if(readxml.StepTime!=0&&TimerCycle!=0)
						{
							CycleCount=(readxml.StepTime)/TimerCycle;//该成员运行一次所需提示次数（被定时器成员提示）
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

		if (interactionClass == _messageId[1]) // 收到暂停
		{
			System.out.println("我收到了暂停命令");
			IsPause = true; // 暂停=true		
			pauseflag=true;
			loopFlag=true;
			System.out.println("暂停回复");
		}
		if (interactionClass == _messageId[10]) // 收到数据更改
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
						System.out.println("收到成员参数 #################          "+receive);
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
		if (interactionClass == _messageId[2]) // 收到继续
		{
			IsPause = false;
			restartflag=true;
			loopFlag=true;
			System.out.println("shoudao继续aaaaaaa");
		}
		if (interactionClass == _messageId[3]) // 收到退出
		{
			IsQuit = true; // 停止=true
			loopFlag=true;
		}
		////受定时器控制的成员定时的接受定时器的消息（以次数为信息数据）

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
					if (readxml.InitParameters.get(i).getLabel() == ModelName)// temp3是存放name的
						readxml.InitParameters.get(i).setValue(receiveValue);// temp2是存放Value的
				}
			}
		}

	}

	// 发现对象实例
	public void discoverObjectInstance(
			int theObject,
			int theObjectClass,
			String objectName) {
		// TODO Auto-generated method stub
		InstanID.add(theObject);		//记录自己订购的对象类
		System.out.println("发现对象类实例");
		System.out.println("此成员订购的对象类有  "+InstanID.toString());
	}

	// 开始注册对象实例
	public void startRegistrationForObjectClass(int theClass) {
		// TODO Auto-generated method stub
		System.out.println("有订购通用成员对象类的成员存在");
		IsS_Object = true;
	}

	//反射对象类属性值，带时间
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
			System.out.println("进入不带时间的reflectAttributeValues函数");
			receive=a;
		}
			}
	// 反射对象类属性值，不带时间
	public void reflectAttributeValues(int theObject,
			ReflectedAttributes theAttributes, byte[] userSuppliedTag) {

		System.out.println("进入reflectAttributeValues回调函数");
		String temp="";
		try {
			temp=new String(theAttributes.getValueReference(0));//接受传递过来的参数
		} catch (ArrayIndexOutOfBounds e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(ValueFlag)//第一次时Attributevalue没有赋值，所以不能直接比较，所以有一个Flag值判断是不是第一次来了消息，第二次以及以后再做判断
		{
			Attributevalue.add(temp);//将读取的数据存入ArrayList数组。
			ValueFlag=false;
		}
		else{
			for(int i=0;i<Attributevalue.size();i++)
			{
				if(temp!=Attributevalue.get(i))//如果数组中不存在当前发过来的数据
				{
					Attributevalue.add(temp);//将读取的数据存入ArrayList数组。
					Attributevalue=method(Attributevalue);
				}
			}	
		}
		System.out.println("Attributevalue[i] : ####"+Attributevalue);
	}

	// 请求时间控制的回调函数（RTI回应本成员的时间控制请求）
	public void timeRegulationEnabled(LogicalTime theFederateTime) {
		// TODO Auto-generated method stub
		System.out.println("进入timeRegulationEnabled函数");
		TimeRegulated = true;
	}
	// 请求时间约束的回调函数（RTI回应本成员的时间约束请求）
	public void timeConstrainedEnabled(LogicalTime theFederateTime) {
		// TODO Auto-generated method stub
		TimeConstrained = true;
	}

	// 时间推进的回调函数（RTI回应本成员的时间推进请求）
	public void timeAdvanceGrant(LogicalTime theTime) {
		System.out.println("进入timeAdvanceGrant函数");
		//TODO Auto-generated method stub
		System.out.println("InstanID.size()                "+InstanID.size());
		TimeAdvanceOutstanding = false;
		flagtimeadvance=true;
		System.out.println("进入时间推进的回调函数---------------------------------------------------------------------");
	}

	public void turnInteractionsOn (
			int theHandle)
	{
		System.out.println("有订购Member交互类存在");
	}
	public void turnUpdatesOnForObjectInstance (
			int theObject,
			AttributeHandleSet theAttributes)
	{
		System.out.println("可以开始更新属性值");
	}
	public void stopRegistrationForObjectClass(int theClass) {
		// TODO Auto-generated method stub
		System.out.println("有订购通用成员的成员退出");
	}

	//	public void TimerDll(String str)
	//	{
	//		try {
	//			Thread.sleep(DelayTime);
	//		} catch (InterruptedException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//		System.out.println("Timer正在处理中。。。。。。。");
	//		StepCount=0;
	//		System.out.println("Timer处理结束！！！！！！");
	//		IsTimerFinish=true;
	//	}
	// //Dll处理
	public static void ExecuteDll() throws InterruptedException
	{
		try {

			System.out.println("||||||||||||||||||||||||||||||||receive"+receive);
			DLLInput = receive.trim();
			System.out.println("dll"+DLLInput);
			readxml.jnative.setParameter(0, readxml.InitParameters.get(0).Value);//第一个参数是Init值
			System.out.println("传入DLL的第一个参数："+readxml.InitParameters.get(0).Value);
			readxml.jnative.setParameter(1, DLLInput);//第二个参数是Input值
			System.out.println("传入DLL的第二个参数："+DLLInput);
			readxml.jnative.invoke(); // 函数执行
			DllOutput = readxml.jnative.getRetVal().trim(); // 获取Dll处理后的值
			System.out.println("传出DLL的值为："+DllOutput);
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

	public void reflectvalue(ArrayList<String> attributevalue)//这里的attributevalue是本成员订购的所有消息的一个String类型数组
	{
		System.out.println("currentTime:---------------------------------"+currentTime);
		String[] normaltempValue = null;//记录普通成员收到数据的数组
		String[] combinetempValue=null;//记录组合成员收到数据的数组
		String[] ValueHandle=null;//用于处理组合成员多输入的数组
		System.out.println("进入reflectvalue函数");
		System.out.println("333333readxml.Locationflag333333333"+readxml.Locationflag);

		if(readxml.Locationflag==true)//Locationflag为true则有订购有多个对象类//多输入
		{
			for(int s=0;s<attributevalue.size();s++)//这个for循环是将attributevalue数组中有价值的信息按照顺序的加到message这个数组中
			{
				normaltempValue=attributevalue.get(s).split("/");//将获取到的值做拆分操作
				for(int i=0;i<readxml.Location.size();i++)
				{
					String[] Location=readxml.Location.get(i).split("/");//这里的Location记录的是本成员订购的多个对象类的消息是如何拼接的
					if(Location[0].equals(normaltempValue[0]))//判断是否是自己所订购的对象类的ID和Location中的ID相同
					{
						int b=Integer.parseInt(Location[1]);//多输入成员的位置信息从Location中读取，Location是在读取XML时获取的值
						System.out.println("b 消息的位置信息为:"+b);
						if(b>message.size())//不管有多少个位置信息，先看这个信息位置是哪里，将数组的前面加上"0"，然后再信息插入到该放的地方
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
			for(int i=0;i<message.size();i++)// 在默认所有信息都到了的情况下，将所有的数据全部拼接起来做输出处理
			{
				if((i+1)==message.size())
				{
					temp=temp+message.get(i);
				}
				else
				{
					temp=temp+message.get(i)+"/";
				}
				System.out.println("拼接之后的temp的值为   "+temp);
			}
			receive=temp;
			temp="";
		}
		else if(readxml.IsCombineMember.equals("1"))//如果是组合成员
		{
			if(attributevalue.size()>=InstanID.size())//如果传来的数据比成员订购的数据多或者正好相等
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
						if(combinetempValue[0].equals(Member[0]))//如果传过来的数据是当前成员需要的
						{
							System.out.println("name1[1]"+combinetempValue[1]);
							newvalue=combinetempValue[1].split("@");
							System.out.println("newvalue[0]"+newvalue[0]);
							if(newvalue[0].equals("C#M"))//如果是组合成员发过来的消息
							{
								if(MemID.equals(newvalue[1].split("-")[0])){
									String value=Member[1]+"-"+newvalue[1].split("-")[1];//Member[1]是D1，如果name[1]是C#M，则name[2]是传过来的值
									System.out.println("value"+value);
									valuerelation.add(value);
									System.out.println("传入DllOutput的值为%%%%%%%%%%%:"+valuerelation);
								}
							}
							else//非组合成员发过来的消息
							{
								String value=Member[1]+"-"+combinetempValue[1];//这里value的值为D1-value
								System.out.println("value"+value);
								valuerelation.add(value);
								System.out.println("传入DllOutput的值为%%%%%%%%%%%:"+valuerelation);
							}
						}

					}	
				}
			}
			else//缺少值的情况
			{
				System.out.println("缺少值，将上一步的值做为参数进行处理");
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
						if(combinetempValue[0].equals(Member[0]))//如果传过来的数据是当前成员需要的
						{
							System.out.println("name1[1]"+combinetempValue[1]);
							newvalue=combinetempValue[1].split("@");
							System.out.println("newvalue[0]"+newvalue[0]);
							if(newvalue[0].equals("C#M"))//如果是组合成员发过来的消息
							{
								if(MemID.equals(newvalue[1].split("-")[0])){
									String value=Member[1]+"-"+newvalue[1].split("-")[1];//Member[1]是D1，如果name[1]是C#M，则name[2]是传过来的值
									System.out.println("value"+value);
									valuerelation.add(value);
									System.out.println("传入DllOutput的值为%%%%%%%%%%%:"+valuerelation);
								}
							}
							else//非组合成员发过来的消息
							{
								String value=Member[1]+"-"+combinetempValue[1];//这里value的值为D1-value
								System.out.println("value"+value);
								valuerelation.add(value);
								System.out.println("传入DllOutput的值为%%%%%%%%%%%:"+valuerelation);
							}
						}

					}
					//			else
					//			{
					//				combinetempValue=ValueRecode;//如果发过来的消息为空，则将上一步得到的数据传给这一步
					//				System.out.println("数据缺失，使用上一步数据，上一步数据为："+combinetempValue.toString());
					//			}
					//			for(int i=0;i<readxml.map.TargetInput.size();i++)
					//			{
					//				String[] Member=readxml.map.TargetInput.get(i).split("-");
					//				String[] newvalue=null;
					//				if(combinetempValue[0].equals(Member[0]))//如果传过来的数据是当前成员需要的
					//				{
					//					System.out.println("name1[1]"+combinetempValue[1]);
					//					newvalue=combinetempValue[1].split("@");
					//					System.out.println("newvalue[0]"+newvalue[0]);
					//					if(newvalue[0].equals("C#M"))//如果是组合成员发过来的消息
					//					{
					//						if(MemID.equals(newvalue[1].split("-")[0])){
					//							String value=Member[1]+"-"+newvalue[1].split("-")[1];//Member[1]是D1，如果name[1]是C#M，则name[2]是传过来的值
					//							System.out.println("value"+value);
					//							valuerelation.add(value);
					//						}
					//					}
					//					else//非组合成员发过来的消息
					//					{
					//						String value=Member[1]+"-"+combinetempValue[1];//这里value的值为D1-value
					//						System.out.println("value"+value);
					//						valuerelation.add(value);
					//					}
					//				}
					//			}		

				}
			}}
		else//单个订购的情况//单输入
		{
			if(readxml.IsCombineMember.equals("1"))//如果是组合成员
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
					System.out.println("数据缺失，使用上一步数据，上一步数据为："+relation.toString());
				}
				for(int i=0;i<readxml.map.TargetInput.size();i++)
				{
					targetInput=readxml.map.TargetInput.get(i).split("-");
					String value=null;
					String [] combo=null;
					System.out.println("relation[1]"+relation[1]);
					combo=relation[1].split("@");
					System.out.println("combo[0]"+combo[0]);
					if(targetInput[0].equals(relation[0]))//如果传过来的第一个参数和TargetInput里面的第一个值相同
					{
						if(combo[0].equals("C#M"))//"C#M"表示是组合成员发过来的信息
						{
							value=targetInput[1]+"-"+combo[1];
						}
						else//不是组合成员发来的信息
						{
							value=targetInput[1]+"-"+relation[1];//value的值就是要用于调用组合成员的dll的值，value的格式应为D1-所订购的成员传过来的值
							//System.out.println("组合成员传入dll的值为："+value);
						}
						valuerelation.add(value);
						combo=null;
					}
					else//这个else是用来测试的，如果匹配失败则输出两个值
					{
						System.out.println("targetInput[0]"+targetInput[0]);
						System.out.println("relation[0]"+relation[0]);
					}
				}
			}
			else{//这里是单输入且非组合成员
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
					System.out.println("数据缺失，使用上一步数据，上一步数据为："+receiveValue.toString());
				}
				System.out.println("name11[1]"+receiveValue[1]);
				member=receiveValue[1].split("@");
				System.out.println("member"+member);
				if(member[0].equals("C#M"))//如果是组合成员发过来的消息，则把C#M后面的值取出传入调用dll
				{
					str=member[1].split(",");
					for(int i=0;i<str.length;i++)//有多少个信息都用“-”剪切
					{
						TargetID=str[i].split("-");
						System.out.println("TargetID:"+TargetID.toString());
						if(TargetID[0].equals(MemID))
						{
							receive=TargetID[1];
							System.out.println("接收到的receive值为:"+receive);
						}
					}

				}
				else//这里表示是普通的成员和多输入成员发过来的消息
				{
					receive=member[0];
					System.out.println("接收到的receive值为:"+receive);
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
