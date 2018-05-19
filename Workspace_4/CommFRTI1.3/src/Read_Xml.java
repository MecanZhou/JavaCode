

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;



import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;




import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;



public class Read_Xml {

	static String FederationName=null;
	static String FederateName=null;

	String ModelID=null;
	String MemberDBID=null;
	String ModelName=null;//ģ����
	String TempId=null;
	String temp[];
	String IsPublic=null;		//�Ƿ��ǹ���
	String IsTimeRegulating=null;//�Ƿ�ʱ������
	String IsTimeConstrained=null;//�Ƿ�ʱ�����

	static int StepTime=0;   	//��Աһ������������

	double Lookaheads=0.0;		//ʱ��ǰհ��
	String modelDll[]=new String[10];	//ģ��dll�ļ� 
	//String ObjectID=null;//_ObjectId  ����ID
	////����////
	JNative jnative =null ;
	/*String LabelName=null;
	String TypeName=null;
	String ValueString=null;*/
	//////////
	Connection  m_pConnection ;
	ResultSet m_pRecordset;
	java.sql.Statement statement ;
	String run_times;//��ȡ���ݿ������run_time
	ArrayList<Input> S_Objects=new ArrayList<Input>();//��Ŷ�������������ID ����
	ArrayList<String> Target=new ArrayList<String>();
	
	ArrayList<String> Inputsource=new ArrayList<String>();
	

	//ArrayList<String> S_ObjectAttribute=new ArrayList<String>();//��Ŷ��InputSources �������������;
	ArrayList<Output> p_Objects=new ArrayList<Output>();//�������������
	ArrayList<Parameter> InitParameters= new ArrayList<Parameter>();	//��ʼ��������Ϣ
	ArrayList<String> Location=new ArrayList<String>();
	//ArrayList<Display> display= new ArrayList<Display>();//չʾ�������Ϣ
	boolean Locationflag=false;//�жϳ�Ա�Ƿ�Ϊ�������
	String Values=null;
	boolean Modelflag=true;
	static  Map map=new Map();
	DLLNamebean Dllname=new DLLNamebean();
	String IsCombineMember="";
	boolean IsCombineMemberflag=false;
	Combineparameter combineparameter=new Combineparameter();


	public void loginDB(String DBmessage,String DllPath)
	{
		//System.out.println(formulaName);
		File file=new File(DBmessage);//DBmessageΪ�ļ��Ķ�ȡ·����"E:\\test.txt"
		FileReader reader;
		try {
			reader=new FileReader(file);
			int fileLen=(int)file.length();
			char[] chars=new char[fileLen];
			reader.read(chars);
			String txt=String.valueOf(chars);
			temp=txt.split("\n");	
		} catch (Exception e) {
			// TODO: handle exception
		}

		String driver="com.mysql.jdbc.Driver";
		String url=temp[0];
		String user=temp[1];
		String password=temp[2];
		try {
			Class.forName(driver);
			m_pConnection=DriverManager.getConnection(url,user,password);
			if(!m_pConnection.isClosed())
			{
				////System.out.println("���ݿ����ӳɹ�!......");

			}
			/*
			 * jnative��������MDLL.dll  
			 * jnative��������Server.dll
			 * ִ��sql���
			 */

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void LinkDll(String MemId,String XmlPath,String DllPath,String DllName) throws NativeException, IllegalAccessException
	{				
		//ϵͳ����dll�ļ�������д��1.loadLibrary����:��dll�ļ�������c:\windows\system32Ŀ¼��,����ʱֻ��дdll����2.load����:дdll�ļ�������·��
		//System.loadLibrary(DllPath);//InterfaceFun��dll�ļ�

		System.out.println(DllPath.trim());
		System.load(DllPath);
		//  System.loadLibrary(DllPath);
		//����˵��InterfaceFun dll��,AddZhiYe������
		int length = DllName.length();
		DllName=DllName.substring(0,length-4);
		// System.out.println(DllName);
		jnative = new JNative(DllName, "run");//"C:/HLA/tankandplane/Model3/a2.dll"
		// jnative = new JNative("Server", "run");
		//���ô˺����ķ���ֵ
		jnative.setRetVal(Type.STRING);
	}



	public void ReadXml(String XmlPath,String memid)
	{
		String ID= memid;

		File file=new File(XmlPath);
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();


		NodeList XMLROOT=null;//���ڵ�
		NodeList GlobalChild=null;//GlobalInfo�ӽڵ�
		NodeList ModleChild=null;//ModelInfo�ӽڵ�
		NodeList modelc3=null;//nodeChild3���ӽڵ�
		NodeList InitChild=null;//InitStates���ӽڵ�
		NodeList Mapchild=null;//InsideMember���ӽڵ�
		NodeList MemberList=null;//MemberList���ӽڵ�
		NodeList InputMember=null;//InputMember���ӽڵ�
		NodeList InputMemberName=null;//InputMember�е�MemberList���ӽڵ�
		NodeList node5child=null;//ChildNode5���ӽڵ�
		NodeList node6child=null;//ChildNode6���ӽڵ�
		NodeList node7child=null;//InsideMember�е�OutputSources���ӽڵ�
		NodeList node8child=null;//InputMember�е�OutputSources���ӽڵ�
		NodeList OutputMember=null;
		NodeList OutputMemberName=null;
		NodeList node9child=null;
		NodeList node10child=null;
		NodeList node11child=null;
		NodeList node12child=null;
		NodeList node13child=null;
		NodeList node14child=null;
		NodeList node15child=null;
		NodeList node16child=null;
		NodeList node17child=null;
		NodeList node18child=null;
		NodeList node19child=null;
		NodeList node20child=null;
		NodeList node21child=null;
		NodeList node22child=null;
		NodeList node23child=null;

		NodeList InputChild=null;//InputSource���ӽڵ�
		NodeList InputChild2=null;//InputSource���ӽڵ�InputSource���ӽڵ㣬Ҳ����Ƕ�׵ĵڶ���InputSource���ӽڵ�
		NodeList OutputChild=null;//OutputSource���ӽڵ�
		NodeList OutputChild2=null;//OutputSource���ӽڵ�OutputSource���ӽڵ㣬Ҳ����Ƕ�׵ĵڶ���OutputSource���ӽڵ�
		NodeList DisplayChild=null;//Display���ӽڵ�
		NodeList DisplayChild2=null;
		NodeList Displaychild3=null;
		Input input=new Input();
		//��ȡXML����
		try {
			DocumentBuilder builder=factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			Element rootElement = doc.getDocumentElement();//��ȡ���ڵ�
			XMLROOT = rootElement.getChildNodes();//��ȡ�ӽڵ�
			DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
			@SuppressWarnings("unused")
			DocumentBuilder db=dbf.newDocumentBuilder();

			for(int i=0;i<XMLROOT.getLength();i+=1)
			{
				Node childNode1=XMLROOT.item(i);//���ڵ��ӽڵ�
				//	System.out.println("����GlobalInfo"+childNode1.getNodeName());
				if(childNode1.getNodeName().equals("GlobalInfo"))//��һ���ж��Ƿ���GlobalInfo
				{
					GlobalChild=childNode1.getChildNodes();
					//	System.out.println("����GlobalInfo");
					for(int j=1;j<GlobalChild.getLength();j++)
					{
						Node childNode2=GlobalChild.item(j);//�ڶ���GlobalChild����ȡGlobalChild�ӽڵ�
						//	System.out.println("����GlobalInfo");
						if(childNode2.getNodeName().equals("FederationName"))
						{
							FederationName=childNode2.getFirstChild().getNodeValue();
							System.out.println("test FederationName......."+FederationName);
							/*
							 * ��ȡ�ڵ��ֵӦ����  node.getFirstChild().getNodeValue()������
							 * node.getNodeValue(),���߻�ȡ���ǽڵ�ĵ�ǰ����
							 */
							//							formulaName=GlobalChild.item(j).getFirstChild().getNodeValue();
							//							//System.out.println("test FederationName......."+formulaName);
						}

						if(childNode2.getNodeName().equals("ScenarioID"))
						{
							System.out.println("test ScenarioID......."+childNode2.getFirstChild().getNodeValue());
						}
						if(childNode2.getNodeName().equals("FOM"))
						{
							System.out.println("test FOM......."+childNode2.getFirstChild().getNodeValue());
						}

					}
				}
				if(childNode1.getNodeName().equals("ModelInfo"))//��һ���ж��Ƿ���ModelInfo
				{
					System.out.println("��������������������Ա��Ϣ����������������������");
					ModleChild=childNode1.getChildNodes();//�ڶ���ModelChild��ȡModelChild
					int ci=0;
					for(int k=0;k<ModleChild.getLength();k++)
					{
						Node childNode3=ModleChild.item(k);//��ȡModelChild���ӽڵ�
						if(childNode3.getNodeName().equals("ModelExeConfig")&&Modelflag)
						{
							modelc3=childNode3.getChildNodes();
							System.out.println("test ModelExeConfig......"+childNode3.getNodeName());
							System.out.println("һ����"+modelc3.getLength()+"����Ա");
							for(int m=0;m<modelc3.getLength();m++)
							{
								Node childNode4=modelc3.item(m);							
								if(childNode4.getNodeName().equals("ModelID"))
								{
									TempId=childNode4.getFirstChild().getNodeValue();
									String str=TempId;
									System.out.println("test ModelID......"+str);				
									if(!str.equals(ID))
										/*
										 * ����ӽڵ��е������Ա��ID��ƥ��Ŀ��ID��break������������һ����Ա
										 * ƥ������������if�ж�,
										 * 
										 */
									{
										ci++;
										System.out.println("��"+ci+"��");
										System.out.println("��"+k+"��IDƥ��ʧ��");
										System.out.println("ִ��"+Modelflag);
										break;
									}	
									//System.out.println("��"+k+"��IDƥ��ɹ�����ȡ���ݣ�");

									ModelID=TempId;
									Modelflag=false;
								}
								if(childNode4.getNodeName().equals("Is_CombineMember")){
									IsCombineMember=childNode4.getFirstChild().getNodeValue();
									System.out.println("IsCombineMember"+IsCombineMember);
									IsCombineMemberflag=true;
								}

								if(childNode4.getNodeName().equals("FederateName"))
								{
									FederateName=childNode4.getFirstChild().getNodeValue();
									System.out.println("\ntest FederateName......"+FederateName);
								}
								if(childNode4.getNodeName().equals("MemberDBID"))
								{
									MemberDBID=childNode4.getFirstChild().getNodeValue();
									System.out.println("test ModelDBID......"+MemberDBID);
								}
								if(childNode4.getNodeName().equals("ModelName"))
								{
									ModelName=childNode4.getFirstChild().getNodeValue();
									System.out.println("test ModelName......"+ModelName);
								}
								if(childNode4.getNodeName().equals("IsPublic"))
								{
									IsPublic=childNode4.getFirstChild().getNodeValue();
									System.out.println("test IsPublic......"+IsPublic);
								}

								if(childNode4.getNodeName().equals("IsTimeRegulating"))
								{
									IsTimeRegulating=childNode4.getFirstChild().getNodeValue();
									System.out.println("test IsTimeRegulating......"+IsTimeRegulating);
								}
								if(childNode4.getNodeName().equals("IsTimeConstrained"))
								{
									IsTimeConstrained=childNode4.getFirstChild().getNodeValue();
									System.out.println("test IsTimeConstrained......"+IsTimeConstrained);

								}
								/////////////Timer�Ķ�ȡ
								//								if(childNode4.getNodeName().equals("Timer"))
								//								{
								//									StepTime=Integer.parseInt(childNode4.getFirstChild().getNodeValue().trim());
								//									System.out.println("test Timer......"+StepTime);
								//					
								//								}
								//////////////���ڴ���ת����double���͵����⵼����һ�λ�ʹ������ȡxml�ĳ����˳�
								//								if(childNode4.getNodeName().equals("Lookahead"))
								//								{
								//									System.out.println("21");
								//									Lookaheads=Double.valueOf(childNode4.getFirstChild().getNodeValue());//��һ�����ᵼ��������ȡ�����˳�
								//									System.out.println("12");
								//									System.out.println("test Lookaheads......"+Lookaheads);
								//								}

								if(childNode4.getNodeName().equals("InitParameters"))
								{

									InitChild=childNode4.getChildNodes();//������ InitStates���ӽڵ�
									System.out.println("����  InitStates");
									for(int n=0,j=0;n<InitChild.getLength();n++)
									{

										Node childNode5=InitChild.item(n);//���Ĳ�InitChild���ӽڵ�
										if(childNode5.getNodeName().equals("Parameter"))
										{
											Parameter paramter=new Parameter();
											node5child=childNode5.getChildNodes();
											System.out.println("<"+n+">**Parameter***");

											for(int a=0;a<node5child.getLength();a++)
											{
												Node childNode6=node5child.item(a);
												//System.out.println("..................."+node5child.getLength());
												System.out.println("Parameter���ӽڵ�......"+childNode6.getNodeName());
												if(childNode6.getNodeName().equals("Label"))
												{
													System.out.println("sb"+childNode6.getFirstChild());
													//LabelName=childNode6.getFirstChild().getNodeValue();
													if(!(childNode6.getFirstChild()==null))
													{
														paramter.Label=childNode6.getFirstChild().getNodeValue();
														System.out.println(" ....Label......"+paramter.getLabel());
													}

												}
												if(childNode6.getNodeName().equals("Value"))
												{
													//paramter.Value=childNode6.getFirstChild().getNodeValue();
													System.out.println("����....Value......");
													node14child=childNode6.getChildNodes();
													for(int u=0;u<node14child.getLength();u++)
													{

														Node childNode7=node14child.item(u);
														if(childNode7.getNodeName().equals("Type"))
														{
															paramter.Type=childNode7.getFirstChild().getNodeValue();
															System.out
															.println("Type:    "+childNode7.getFirstChild().getNodeValue());
														}
														if(childNode7.getNodeName().equals("Value"))
														{
															paramter.Value=childNode7.getFirstChild().getNodeValue();
															System.out
															.println("��ʼ������Ϊ��"+paramter.Value);
														}
													}

												}

												InitParameters.add(paramter);

											}

										}
									}
									//								System.out.println("InitParameters.size() : "+InitParameters.size());
									//								for(int zz=0;zz<InitParameters.size();zz++)
									//								{
									//									System.out.println("zz : "+zz);
									//									System.out.println("test Value......"+InitParameters.get(zz).getValue());
									//								}

									// ���ն����Ķ�����Ķ��������
								}

								if(childNode4.getNodeName().equals("InputSources"))
								{
									System.out.println("���� InputResources�ڵ�");
									InputChild=childNode4.getChildNodes();
									for(int d=0;d<InputChild.getLength();d++)
									{
										//										Input input=new Input();
										//										S_ObjectAttributes.add(new Input());
										Node childNode9=InputChild.item(d);
										//System.out.println("test child of InputChild......"+childNode9.getNodeName());
										if(childNode9.getNodeName().equals("InputSource"))
										{

											System.out.println(">> InputResource <<");
											String target=childNode9.getFirstChild().getNodeValue();							
											System.out.println("input target"+target);
											if(!(childNode9.getFirstChild().getNodeValue()==null))//��ϳ�Ա��InputSource�ڵ�����û����ֵ�ģ���ͨ��Ա����
											{

												input.ObjectID.add(target);
												input.Attributes.add("isOutput");
												S_Objects.add(input);
												System.out.println("size of S_ObjectAttributes "+S_Objects.size());
											}

											Element e1=(Element) childNode9;//��ȡXML�е�<InputSource ID="1">4</InputSource>�ṹ
											e1.getElementsByTagName("InputSource");
											String Position=e1.getAttribute("ID");	
											Position = target+"/"+Position;							
											Location.add(Position);
											System.out.println(Location.size());
											if(Location.size()>1)
											{
												Locationflag = true;//���������������ڣ���ʾ�Ƕ����룬��Locationflag����Ϊtrue
											}

										}
									}
								}

								/*
								 * ����������Ķ��������
								 */
								//System.out.println("����OutputSources�ڵ�");
								//								if(childNode4.getNodeName().equals("InputSources"))//���InputSources��û���򱱾���XML��ʽ��£��
								//								{
								//									System.out.println("����InputResources�ڵ�");
								//									node15child=childNode4.getChildNodes();
								//									String id=null;
								//									String target=null;
								//									for(int mn=0;mn<node15child.getLength();mn++)
								//									{
								//										Node name1=node15child.item(mn);
								//										System.out.println("node15child.getLength()"+node15child.getLength());
								//										System.out.println("name1.getNodeName()"+name1.getNodeName());
								//										if(name1.getNodeName().equals("InputSource"))
								//										{
								//											System.out.println("����InputResource�ڵ�");
								//											node16child=name1.getChildNodes();
								//											//System.out.println("node16child.getLength()"+node16child.getLength());
								//											for(int km=0;km<node16child.getLength();km++)
								//											{
								//												Node name2=node16child.item(km);
								//												if(name2.getNodeName().equals("ID"))
								//												{
								//													id=name2.getFirstChild().getNodeValue();
								//													System.out.println("ID++++"+id);
								//													input.ObjectID.add(id);
								//													input.Attributes.add("isOutput");
								//													S_Objects.add(input);
								//													System.out
								//													.println("S_Objects.size"+S_Objects.size());
								//													for (int f = 0; f< S_Objects.size(); f++)
								//													{
								//														System.out.println(S_Objects.get(f).ObjectID.toString());
								//													}
								//												}
								//												if(name2.getNodeName().equals("Target"))
								//												{
								//													target=name2.getFirstChild().getNodeValue();
								//													target=id+"-"+target;
								//													map.TargetInput.add(target);
								//													System.out
								//													.println("Target"+target);
								//													target=null;
								//												}
								//											}
								//										}
								//									}
								//								}



								if(childNode4.getNodeName().equals("OutputSources"))
								{

									System.out.println("����OutputResources�ڵ�");
									Output output=new Output();

									OutputChild=childNode4.getChildNodes();
									for(int f=0;f<OutputChild.getLength();f++)
									{

										Node childNode11=OutputChild.item(f);
										//System.out.println("test child of InputChild......"+childNode11.getNodeName());
										if(childNode11.getNodeName().equals("OutputSource"))
										{										
											OutputChild2=childNode11.getChildNodes();
											for(int g=0;g<OutputChild2.getLength();g++)
											{

												Node childNode12=OutputChild2.item(g);
												if(childNode12.getNodeName().equals("ID"))
												{
													output.outId=childNode12.getFirstChild().getNodeValue();
													System.out
													.println("output.outId"+output.outId);
													output.Attributes.add("IsOutput");
												}
												if(childNode12.getNodeName().equals("Attribute"))
												{
													output.Attributes.add(childNode12.getFirstChild().getNodeValue());
													System.out.println(".....Output--Attrabute..."+output.getAttributes().get(0));
													//num++;
												}
												if(childNode12.getNodeName().equals("Target"))
												{
													String target=output.outId+"-"+childNode12.getFirstChild().getNodeValue();
													map.TargetOutput.add(target);
													System.out.println("output target"+target);
												}
											}
											p_Objects.add(output);
											System.out.println("p_Objects"+output.getAttributes());
											System.out.println("p_Objects"+output.outId);

										}

									}
								}
								if(childNode4.getNodeName().equals("Display"))
								{
									System.out.println("����Display�ڵ�");
									DisplayChild=childNode4.getChildNodes();
									for(int f=0;f<DisplayChild.getLength();f++)
									{
										Node node = DisplayChild.item(f);
										if(node.getNodeName().equals("InputPara"))
										{
											System.out.println("����InputPara");
											DisplayChild2=node.getChildNodes();
											for(int mx=0;mx<DisplayChild2.getLength();mx++)
											{
												Node node1=DisplayChild2.item(mx);
												if(node1.getNodeName().equals("Value"))
												{
													System.out
															.println("����Value��");
													Displaychild3=node1.getChildNodes();
													for(int mk=0;mk<Displaychild3.getLength();mk++)
													{
														Node node3=Displaychild3.item(mk);
														if(node3.getNodeName().equals("Value"))
														{
															Inputsource.add(node3.getFirstChild().getNodeValue());
															System.out
																	.println("node3.getFirstChild().getNodeValue():"+node3.getFirstChild().getNodeValue());
															
															//����Ҫ������������ƴ���������ʼ����ֵ
														}
													}
												}
											}
										}
									}
								}
								if(childNode4.getNodeName().equals("InsideMembers"))
								{
									Mapchild=childNode4.getChildNodes();
									for(int i1=0;i1<Mapchild.getLength();i1++)
									{
										Node childNode14=Mapchild.item(i1);
										if(childNode14.getNodeName().equals("ChildMember"))
										{
											String MermberName = null;
											String MemberDLLName=null;
											String MemberId=null;
											MemberList=childNode14.getChildNodes();
											for(int i2=0;i2<MemberList.getLength();i2++)
											{
												Node childNode15=MemberList.item(i2);
												if(childNode15.getNodeName().equals("MemberName"))
												{
													MermberName=childNode15.getFirstChild().getNodeValue();
													System.out
													.println("MermberName"+MermberName);
												}
												if(childNode15.getNodeName().equals("ModelID"))
												{
													MemberId=childNode15.getFirstChild().getNodeValue();
													combineparameter.InsideMemberID.add(MemberId);
												}
												if(childNode15.getNodeName().equals("ModelDLL"))
												{
													MemberDLLName=childNode15.getFirstChild().getNodeValue();												
													MemberDLLName=MemberId+"-"+MemberDLLName;
													System.out.println("MemberDLLName"+MemberDLLName);
													Dllname.MemberDLLName.add(MemberDLLName);
												}
												if(childNode15.getNodeName().equals("InputResources"))
												{
													node7child=childNode15.getChildNodes();
													for(int i3=0;i3<node7child.getLength();i3++){
														Node childNode17=node7child.item(i3);
														if(childNode17.getNodeName().equals("InputResource"))
														{
															//															Element e2=(Element) childNode17;
															//																e2.getElementsByTagName("OutputSource");
															//																e2.get
															//Object obj=null;
															String name=null;
															System.out.println("MermberName"+MermberName);

															//System.out.println("add ����ִ��");
															node9child=childNode17.getChildNodes();
															{
																for(int n=0;n<node9child.getLength();n++)
																{
																	Node childNode18=node9child.item(n);
																	if(childNode18.getNodeName().equals("Id"))
																	{
																		String id=childNode18.getFirstChild().getNodeValue();
																		System.out
																		.println("id"+id);
																		name=id+"-"+MemberId;
																		System.out.println("name"+name);
																		map.MermberRelation.add(name);
																	}

																}
															}
														}
													}
												}
												if(childNode15.getNodeName().equals("InitParameters"))//��ͨ��Ա�Ĳ�����ʼ��
												{
													System.out
													.println("����InitParameters");
													node10child=childNode15.getChildNodes();
													for(int n=0;n<node10child.getLength();n++)
													{
														Node childNode19=node10child.item(n);
														if(childNode19.getNodeName().equals("Parameter"))
														{
															System.out
															.println("����Parameter");
															node17child=childNode19.getChildNodes();
															for(int d=0;d<node17child.getLength();d++)
															{
																Node childNode20=node17child.item(d);
																if(childNode20.getNodeName().equals("Value"))
																{
																	node22child=childNode20.getChildNodes();
																	for(int nm=0;nm<node22child.getLength();nm++)
																	{
																		Node childNode22=node22child.item(nm);
																		if(childNode22.getNodeName().equals("Value"))
																		{
																			String value=childNode22.getFirstChild().getNodeValue();
																			value=MemberId+"-"+value;
																			System.out.println("value"+value);
																			combineparameter.CombineValue.add(value);
																		}
																	}

																}
															}
														}
													}
												}

											}
										}
									}
								}
								if(childNode4.getNodeName().equals("InputMembers"))//����InputMembers�е�InputResourcesû�д�������
								{
									InputMember=childNode4.getChildNodes();
									for(int i1=0;i1<InputMember.getLength();i1++)
									{
										Node childNode15=InputMember.item(i1);
										if(childNode15.getNodeName().equals("ChildMember"))
										{
											String InputMemberName1=null;
											String DllName=null;
											String MemberId=null;
											String name=null;
											InputMemberName=childNode15.getChildNodes();
											System.out.println("����ChildMember");
											for(int i2=0;i2<InputMemberName.getLength();i2++)
											{
												Node childNode16=InputMemberName.item(i2);
												if(childNode16.getNodeName().equals("ModelID"))
												{
													MemberId=childNode16.getFirstChild().getNodeValue();
													combineparameter.InputMemID.add(MemberId);
													System.out
													.println("MemberId"+MemberId);
												}
												if(childNode16.getNodeName().equals("MemberName"))
												{
													InputMemberName1=childNode16.getFirstChild().getNodeValue();
												}
												if(childNode16.getNodeName().equals("ModelDLL"))
												{
													DllName=childNode16.getFirstChild().getNodeValue();			
													DllName=MemberId+"-"+DllName;
													System.out.println("DllName"+DllName);
													Dllname.MemberDLLName.add(DllName);
												}
												if(childNode16.getNodeName().equals("InputSources"))
												{
													node8child=childNode16.getChildNodes();
													for(int i3=0;i3<node8child.getLength();i3++)
													{
														Node childNode18=node8child.item(i3);
														if(childNode18.getNodeName().equals("InputSource"))
														{
															String IDname=childNode18.getFirstChild().getNodeValue();
															String relation=IDname+"-"+MemberId;
															System.out
															.println("relation"+relation);
															map.MermberRelation.add(relation);
														}
													}
												}
												if(childNode16.getNodeName().equals("InitParameters"))
												{
													System.out
													.println("����һ���Ա��InitParameters");
													node8child=childNode16.getChildNodes();
													for(int i3=0;i3<node8child.getLength();i3++)
													{
														Node childNode18=node8child.item(i3);
														if(childNode18.getNodeName().equals("Parameter"))
														{
															node10child=childNode18.getChildNodes();
															for(int im=0;im<node10child.getLength();im++)
															{
																Node childNode20=node10child.item(im);
																if(childNode20.getNodeName().equals("Value"))
																{
																	String value=childNode20.getFirstChild().getNodeValue();
																	//	value =MemberId+"-"+value;
																	System.out
																	.println("�����һ��value");
																	node20child=childNode20.getChildNodes();
																	for(int ik=0;ik<node20child.getLength();ik++)
																	{
																		Node childNode21=node20child.item(ik);
																		if(childNode21.getNodeName().equals("Value"))
																		{
																			System.out
																			.println("����ڶ���value");
																			value=childNode21.getFirstChild().getNodeValue();
																			value =MemberId+"-"+value;
																			System.out
																			.println("value"+value);
																			combineparameter.CombineValue.add(value);
																		}
																	}
																}
															}
														}
													}
												}
												//												if(childNode16.getNodeName().equals("OutputResources"))
												//												{
												//													node8child=childNode16.getChildNodes();
												//													for(int i3=0;i3<node8child.getLength();i3++){
												//														Node childNode18=node8child.item(i3);
												//														if(childNode18.getNodeName().equals("OutputResource"))
												//																{
												//																	node11child=childNode18.getChildNodes();
												//																	for(int n=0;n<node11child.getLength();n++)
												//																	{
												//																		Node childNode19=node11child.item(n);
												//																		if(childNode19.getNodeName().equals("Id"))
												//																		{
												//																			String id=childNode19.getFirstChild().getNodeValue();
												//																			 name=MemberId+"-"+id;
												//																			 System.out.println("name"+name);
												//																				map.MermberRelation.add(name);	
												//																		}
												//																	}	
												//																	//map.MermberRelation.add("11111111111");		
												//																	for(int i11 = 0;i11<map.MermberRelation.size();i11++)
												//																	{
												//																		System.out
												//																				.println("map.MermberRelation"+map.MermberRelation.get(i11));
												//																	}
												//																}
												//													}
												//												}
											}
										}
									}
								}

								if(childNode4.getNodeName().equals("OutputMembers"))
								{
									OutputMember=childNode4.getChildNodes();
									for(int i1=0;i1<OutputMember.getLength();i1++)
									{
										Node childNode19=OutputMember.item(i1);
										//System.out.println("����OutputMember��");
										if(childNode19.getNodeName().equals("ChildMember"))
										{
											String MemberName1=null;
											String MemberId=null;
											OutputMemberName=childNode19.getChildNodes();
											for(int j=0;j<OutputMemberName.getLength();j++)
											{
												//System.out.println("����MemberList��");
												Node childNode20=OutputMemberName.item(j);

												if(childNode20.getNodeName().equals("MemberName"))
												{
													MemberName1=childNode20.getFirstChild().getNodeValue();
													System.out
													.println("MemberName"+MemberName1);
												}
												if(childNode20.getNodeName().equals("ModelID"))
												{
													MemberId=childNode20.getFirstChild().getNodeValue();
													combineparameter.OutputMemID.add(MemberId);
													//								System.out
													//								.println("MemberId"+MemberId);
												}
												if(childNode20.getNodeName().equals("ModelDLL"))
												{
													String MemberDLLname=childNode20.getFirstChild().getNodeValue();
													MemberDLLname=MemberId+"-"+MemberDLLname;
													//	MemberDLLname=MemberName1+"-"+MemberDLLname;
													//								System.out.println("MemberDLLname"+MemberDLLname);
													Dllname.MemberDLLName.add(MemberDLLname);
												}
												if(childNode20.getNodeName().equals("InputSources"))
												{
													node12child=childNode20.getChildNodes();
													for(int n=0;n<node12child.getLength();n++)
													{
														Node childNode21=node12child.item(n);
														if(childNode21.getNodeName().equals("InputSource"))
														{
															String targetID=childNode21.getFirstChild().getNodeValue();
															targetID=targetID+"-"+MemberId;
															//									System.out
															//									.println("targetID"+targetID);
															map.MermberRelation.add(targetID);
														}
													}
												}
												if(childNode20.getNodeName().equals("InitParameters"))
												{
													node18child=childNode20.getChildNodes();
													for(int n=0;n<node18child.getLength();n++)
													{
														Node childNode21=node18child.item(n);
														if(childNode21.getNodeName().equals("Parameter"))
														{
															System.out.println("����Parameter");
															node19child=childNode21.getChildNodes();
															for(int mi=0;mi<node19child.getLength();mi++)
															{
																Node childNode22=node19child.item(mi);
																if(childNode22.getNodeName().equals("Value"))
																{
																	node21child=childNode22.getChildNodes();
																	//								System.out
																	//										.println("�����һ��value");
																	for(int mk=0;mk<node21child.getLength();mk++)
																	{
																		Node childNode23=node21child.item(mk);
																		if(childNode23.getNodeName().equals("Value"))
																		{
																			//									System.out
																			//									.println("����ڶ���value");
																			String value=childNode23.getFirstChild().getNodeValue();	
																			value=MemberId+"-"+value;
																			//									System.out
																			//									.println("value"+value);
																			combineparameter.CombineValue.add(value);
																		}
																	}

																}
															}
														}
													}
												}
											}
										}
									}
								}																														
							}		
						}
					}
				}									
			} 

		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}

	/*
	 * ��Ϊ����XML������
	 */
	public static void main(String[] args) {
		Read_Xml rx=new Read_Xml();
		rx.ReadXml("E:\\ftp\\�ɻ���Som.xml", "2");
		System.out.println("TargetInput"+rx.map.TargetInput.toString());
		System.out.println("TargetOutput"+rx.map.TargetOutput.toString());
		System.out.println("combineparameter"+rx.combineparameter.CombineValue.toString());
		System.out.println("combineRelation"+rx.map.MermberRelation.toString());
		System.out.println("Dllname"+rx.Dllname.MemberDLLName.toString());
		System.out.println("parameter"+rx.InitParameters.size());
		System.out.println("InsideMemberID"+rx.combineparameter.InsideMemberID.toString());
		System.out.println("InPutMemberID"+rx.combineparameter.InputMemID.toString());
		System.out.println("OutPutMemberID"+rx.combineparameter.OutputMemID.toString());
		//	System.out.println(rx.Dllname.MemberDLLName);
		//		for (int i = 0; i < rx.S_Objects.size(); i++)
		//		{
		//			System.out.println(rx.S_Objects.get(i).ObjectID);
		//		}

	}
}
