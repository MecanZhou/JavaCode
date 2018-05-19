import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CTReadFOM {

	public static void main(String args[])
	{
		String newtest = "newtest";
		CTReadFOM a=new CTReadFOM(newtest);
		a.ReadFom();
	}
	public static String fedName=null;
	public static NodeList FomRoot=null;
	public static NodeList nodeList1=null;
	public static NodeList nodeList2=null;
	public static NodeList nodeList3=null;
	public static NodeList nodeList4=null;
	public static NodeList nodeList5=null;
	public static NodeList SomRoot=null;
	public static NodeList SomList1=null;
	public static NodeList SomList2=null;
	public static NodeList SomList3=null;
	public static NodeList SomList4=null;
	public static String FederateName=null;
//	public static long Delay=0;
	public static ArrayList<Integer> Delay=new ArrayList<Integer>();
	public static long timeDelay=0;

	public static ArrayList<objectClass> objParameters=new ArrayList<objectClass>();
	public CTReadFOM(String fedname){

			fedName=fedname;
//			System.out.println("test main");
			Delay=ReadSom();
			writeFED(ReadFom());
			System.out.println("delay dize: "+Delay.size());
			timeDelay=countTimeDelay(Delay);
//		ReadXML();

	}
	public static long countTimeDelay(ArrayList<Integer>delay)
	{
		System.out.println("--------");
		for(int num=0;num<Delay.size();num++)
		{
			System.out.println("----------"+Delay.get(num)+"----------------");
		}
		
		long time=0;
		int t=0;
		for(int i=0;i<delay.size();i++)
		{
			if(i==0)
			{
				t=OJLD.gcd(delay.get(i), delay.get(i));
			}
			else
			{
				System.out.println("~~~~~~~~~~~~~~~"+t);
				t=OJLD.gcd(delay.get(i), t);
			}
		}
		time=t;
		System.out.println(time);
		return time;
	}
	
//	public static void main(String[] args)
//	{
//		Delay=ReadSom();
//		for(int num=0;num<Delay.size();num++)
//		{
//			System.out.println("----------"+Delay.get(num)+"----------------");
//		}
//		System.out.println(countTimeDelay(Delay));
//	}
	public static ArrayList<Integer> ReadSom(){
		ArrayList<Integer> delay=new ArrayList<Integer>();
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		try {
//			File file=new File(fedName+"Som.xml");
			File file=new File("newtestSom.xml");
			System.out.println("newtestSom.xml");
			DocumentBuilder db=dbf.newDocumentBuilder();
			Document doc=db.parse(file);
			Element root=doc.getDocumentElement();
			NodeList SomRoot= root.getChildNodes();
			for(int a=0;a<SomRoot.getLength();a++)
			{
				Node childNode1=SomRoot.item(a);
				
				if(childNode1.getNodeName().equals("ModelInfo"))
				{
					System.out.println(childNode1.getNodeName());
					SomList1=childNode1.getChildNodes();
					for(int b=0;b<SomList1.getLength();b++)
					{
						Node childNode2=SomList1.item(b);
						
						if(childNode2.getNodeName().equals("ModelExeConfig"))
						{
							System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
							System.out.println(childNode2.getNodeName());
							SomList2=childNode2.getChildNodes();
							for(int c=0;c<SomList2.getLength();c++)
							{
								Node childNode3=SomList2.item(c);
								System.out.println(childNode3.getNodeName());
								if(childNode3.getNodeName().equals("FederateName"))
								{
									
									System.out.println(childNode3.getFirstChild().getNodeValue());
									FederateName=childNode3.getFirstChild().getNodeValue();
									
								}
								if(childNode3.getNodeName().equals("Timer"))
								{
									System.out.println("!!!!!!!!!!!!!!!!!!!!!  "+childNode3.getFirstChild().getNodeValue());
									int timer=Integer.parseInt(childNode3.getFirstChild().getNodeValue().trim());
									if(timer>0)
									{
										delay.add(timer);
									}

								}
							}
							
						}
					}
//					System.out.println("OK");
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return delay;
	}
	public static ArrayList<objectClass> ReadFom(){
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		try {
			File file=new File(fedName+"Fom.xml");
			System.out.println(fedName+"Fom.xml");
			DocumentBuilder db=dbf.newDocumentBuilder();
//			InputStream is=new FileInputStream("FOM.xml");
			Document doc = db.parse(file);
//			System.out.println("OK");
			Element root=doc.getDocumentElement();
			NodeList XMLROOT= root.getChildNodes();
			
			for(int a=0;a<XMLROOT.getLength();a++)
			{
				Node childNode1=XMLROOT.item(a);
//				System.out.println("根节点子节点: "+childNode1.getNodeName());
				if(childNode1.getNodeName().equals("objects")){
//					System.out.println("进入"+childNode1.getNodeName()+"层......");
					nodeList1=childNode1.getChildNodes();
					for(int b=0;b<nodeList1.getLength();b++)
					{
						Node childNode2=nodeList1.item(b);
						System.out.println("objects 节点子节点: "+childNode2.getNodeName());
						if(childNode2.getNodeName().equals("objectClass")){
							System.out.println("进入"+childNode2.getNodeName()+"层......");
							nodeList2=childNode2.getChildNodes();
							for(int c=0;c<nodeList2.getLength();c++)
							{
								Node childNode3=nodeList2.item(c);
								System.out.println("objectClass 节点子节点："+childNode3.getNodeName());
								if(childNode3.getNodeName().equals("objectClass"))
								{
									System.out.println("进入嵌套的objectClass层......");
									nodeList3=childNode3.getChildNodes();
									for(int d=0;d<nodeList3.getLength();d++)
									{
										objectClass obj=new objectClass();
										Node childNode4=nodeList3.item(d);
										System.out.println("嵌套层objectClass节点子节点： "+childNode4.getNodeName());
										if (childNode4.getNodeName().equals("objectClass"))
										{
											System.out.println("进入第三层嵌套");
											nodeList4=childNode4.getChildNodes();
											for(int e=0;e<nodeList4.getLength();e++)
											{
												
												Node childNode5=nodeList4.item(e);
												System.out.println("第三层嵌套objectClass 节点子节点: "+childNode5.getNodeName());
												if(childNode5.getNodeName().equals("Name"))
												{
													System.out.println(childNode5.getNodeName()+"属性值为："+childNode5.getFirstChild().getNodeValue());
													obj.setName(childNode5.getFirstChild().getNodeValue());
												}
												if(childNode5.getNodeName().equals("Attribute"))
												{
													System.out.println(childNode5.getNodeName()+"属性值为："+childNode5.getFirstChild().getNodeValue());
													obj.value.add(childNode5.getFirstChild().getNodeValue());

												}
												
											}
											
											System.out.println("执行add方法："+obj.getName()+"  "+obj.getValue());
											objParameters.add(obj);
											System.out.println(objParameters.size());
										}
									}
								}
							}
						}
					}
					
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return objParameters;
	}

	public static void writeFED(ArrayList<objectClass> objParameters) {
		System.out.println("para size"+objParameters.size());
		for(int i=0;i<objParameters.size();i++)
		{
			System.out.println("= = = =："+objParameters.get(i).getName());
		}
		ArrayList<objectClass> parameter=objParameters;
		String FEDpath="FED.xml";
		String encording="UTF-8";
		String lineTxt=null;
		boolean flag=true;
		int times=0;

//		System.out.println("读取成功......");
		/*
		 * 开始写入fed
		 */
		try {
			RandomAccessFile mm=null;
			File file=new File(FEDpath);
			InputStreamReader read=new InputStreamReader(new FileInputStream(file),encording);
			BufferedReader bufferedReader=new BufferedReader(read);
			mm=new RandomAccessFile(FEDpath, "rw");
			while((lineTxt=bufferedReader.readLine())!=null){
//				System.out.println("写入: "+lineTxt);
				if(lineTxt.contains("</FED>"))
				{
					mm.writeBytes("</FED>"+"\r\n");
//					System.out.println("FED~~~");
					bufferedReader.close();
					read.close();
					mm.close();
					break;
				}
				mm.writeBytes(lineTxt+"\r\n");
				if(lineTxt.equals("</interactionclass>")&&flag)
				{
//					mm.writeBytes(lineTxt+"\r\n");
					mm.writeBytes("<objectclass>\r\n");
					for(int i=0;i<parameter.size();i++)
					{
						String test="\t<"+parameter.get(i).getName()+">\r\n";
						mm.writeBytes(test);
						for(int j=0;j<parameter.get(i).getValue().size();j++)
						{
							test="\t\t<attribute"+(j+1)+">"+parameter.get(i).getValue().get(j)+"</attribute"+(j+1)+">\r\n";
							mm.writeBytes(test);
						}
						test="\t</"+parameter.get(i).getName()+">\r\n";
						mm.writeBytes(test);
					}
					mm.writeBytes("</objectclass>\r\n");
					times++;
//					System.out.println("写了"+times+"次~~~~~");
					flag=false;

				}

				
			}

			
		} catch (Exception e) {
			// TODO: handle exception
		}

		
	}

}

 class objectClass{
	 public String name=null;
	 public ArrayList<String>value=new ArrayList<String>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<String> getValue() {
		return value;
	}
	public void setValue(ArrayList<String> value) {
		this.value = value;
	}

}
