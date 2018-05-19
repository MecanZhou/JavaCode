import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import ExceptionPackage.ConcurrentAccessAttempted;
import ExceptionPackage.CouldNotOpenFED;
import ExceptionPackage.ErrorReadingFED;
import ExceptionPackage.FederationExecutionAlreadyExists;
import ExceptionPackage.RTIinternalError;
import RTI.RTI;
import RTI.RTIambassador;
import SerializationClass.EventRetractionHandle;
import SerializationClass.LogicalTime;
import SerializationClass.LogicalTimeDouble;
import SerializationClass.LogicalTimeInterval;
import SerializationClass.LogicalTimeIntervalDouble;
import SerializationClass.ReceivedInteraction;
import SerializationClass.ReflectedAttributes;
import SerializationClass.SuppliedParameters;
import prti.FederateAmbassador_Impl;


public class Chat_1 extends FederateAmbassador_Impl{

	private RTIambassador rtiambassador;// RTIambassador��һ���ӿڣ�����һ���ӿڶ���
	
	public boolean IsS_Object = false; // IsS_Object�Ƿ���ڶ�����Ķ�����
	boolean TimeRegulated = false;
	boolean TimeConstrained = false;
	boolean TimeAdvanceOutstanding = false;

	public ArrayList<Integer> InstanID = new ArrayList<Integer>();
	
	public String receive="" ;
	public String rtiHost="Localhost";
	public String FederationName="ChatCommunicate";//��������
	LogicalTime sendTime;//
	LogicalTimeInterval lookhead; // 
	
	public int _messageId[] = new int[20];// �����ܿض�����������Ա�����Ľ�����
	public int _parameterIdText[] = new int[20];// �����ܿض�����������Ա�����Ľ���������Ծ��
	/*
	 * 
	 */
	public BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args)throws Exception
	{
		Chat_1 chat=new Chat_1();
		chat.run();
	}
	
	public void run() throws FederationExecutionAlreadyExists, CouldNotOpenFED,
					   ErrorReadingFED, RTIinternalError, ConcurrentAccessAttempted, IOException
	{
		rtiambassador = RTI.getRTIambassador(rtiHost, 8989); // ��ʼ��
	rtiambassador.createFederationExecution(FederationName,new File("C:\\Documents and Settings\\Administrator\\����\\Chat_FED.xml"));
		//rtiambassador.createFederationExecution(FederationName,new File("C:\\Documents and Settings\\Administrator\\workspace\\�ܿض˳���\\FED.xml"));//
		
		String name=null;
		System.out.println("�������Ա�����ƣ�");
		name=in.readLine();
		rtiambassador.joinFederationExecution(name,FederationName, this);
		
		System.out.println("Chat--aaaaaa");
//		lookhead = new LogicalTimeIntervalDouble(1.0);
//		sendTime = new LogicalTimeDouble(0.0);
//		rtiambassador.enableTimeRegulation(sendTime, lookhead);
//		rtiambassador.enableTimeConstrained();
		
		_messageId[0] = rtiambassador.getInteractionClassHandle("Inter_1");
		_parameterIdText[0] = rtiambassador.getParameterHandle("Message", _messageId[0]);
		rtiambassador.publishInteractionClass(_messageId[0]);
		rtiambassador.subscribeInteractionClass(_messageId[0]);
		
		_messageId[1] = rtiambassador.getInteractionClassHandle("Inter_2");
		_parameterIdText[1] = rtiambassador.getParameterHandle("Message", _messageId[1]);
		//rtiambassador.publishInteractionClass(_messageId[1]);
		rtiambassador.subscribeInteractionClass(_messageId[1]);
		
		String msg=null;
		
		System.out.println("����*****��");
		while(true)
		{
			//System.out.println("Chat--cccccc");
			try {
				
				msg=in.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(msg.endsWith(""))
			{
				SuppliedParameters parameters = RTI.suppliedParametersFactory().create(0);
			
				try {
					parameters.add(_parameterIdText[0], msg.getBytes());
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				rtiambassador.sendInteraction(_messageId[0], parameters, null);
			}
		}
		
	}
	
	
	public void receiveInteraction(int interactionClass,
			ReceivedInteraction theInteraction, byte[] userSuppliedTag) {
		byte[] message = null;
		byte[] para = null;
		byte[] val = null;
		
		System.out.println("inter+"+interactionClass);
		if(interactionClass ==_messageId[0])
		{
		
			for(int i=0; i < theInteraction.size();i++)
			{
				
					if (theInteraction.getParameterHandle(i) ==_parameterIdText[0])
					{
						try {
							message = theInteraction.getValueReference(i);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String str=new String(message);
						System.out.println("�յ���"+str);
						System.out.println("���룺");
					}
				
			}
		}
	
		if(interactionClass ==_messageId[1])
		{
			for(int i=0; i < theInteraction.size();i++)
			{
				
					if (theInteraction.getParameterHandle(i) ==_parameterIdText[1])
					{
						try {
							message = theInteraction.getValueReference(i);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String str=new String(message);
						System.out.println("�յ���"+str);
						System.out.println("���룺");
					}
				
			}
		}
	}

	// ���ֶ���ʵ��
	public void discoverObjectInstance(int theObject, int theObjectClass,
			String objectName) {
		// TODO Auto-generated method stub
		InstanID.add(theObject);		//��¼�Լ������Ķ�����

	}

	// ��ʼע�����ʵ��
	public void startRegistrationForObjectClass(int theClass) {
		// TODO Auto-generated method stub
		
		IsS_Object = true;
	}

	//�������������ֵ����ʱ��
	public void reflectAttributeValues(int theObject,
			ReflectedAttributes theAttributes, byte[] userSuppliedTag,
			LogicalTime theTime, EventRetractionHandle retractionHandle) {// TODO
		
		System.out.println("reflectAttributeValues");
		for (int i = 0; i < InstanID.size(); i++) {
			System.out.println("iiiiiiiii:"+i);
			if (InstanID.get(i) == theObject) {
				System.out.println("sssssssssssss:"+theAttributes.size());
				for (int j = 0; j < theAttributes.size(); ++j) // s_AttributeIdNumber===theAttributes.size()
				{
					System.out.println("jjjjjjjj:"+j);
					byte[] str;
					
					try {
						str = theAttributes.getValueReference(j);
						// int index=theAttributes.getAttributeHandle(j);
						receive = new String(str);

						// receiveNumber++;

						 System.out.println("shoudao!!!"+receive);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

	}

	// �������������ֵ������ʱ��
	@SuppressWarnings("static-access")
	public void reflectAttributeValues(int theObject,
			ReflectedAttributes theAttributes, byte[] userSuppliedTag) {
		// TODO Auto-generated method stub
	}

	// ����ʱ����ƵĻص�������RTI��Ӧ����Ա��ʱ���������
	public void timeRegulationEnabled(LogicalTime theFederateTime) {
		// TODO Auto-generated method stub
		TimeRegulated = true;
	}

	// ����ʱ��Լ���Ļص�������RTI��Ӧ����Ա��ʱ��Լ������
	public void timeConstrainedEnabled(LogicalTime theFederateTime) {
		// TODO Auto-generated method stub
		TimeConstrained = true;
	}

	// ʱ���ƽ��Ļص�������RTI��Ӧ����Ա��ʱ���ƽ�����
	public void timeAdvanceGrant(LogicalTime theTime) {
		// TODO Auto-generated method stub
		TimeAdvanceOutstanding = false;
	}

}


