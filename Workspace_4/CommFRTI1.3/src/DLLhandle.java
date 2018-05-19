
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;

import org.xvolks.jnative.exceptions.NativeException;



public class DLLhandle {
	public static boolean flag=true;
	public static boolean flag1=true;
	public static ArrayList<String> RetentionRelationship=new ArrayList<String>();//存储保留关系的数组
	public static ArrayList<String> pane=new ArrayList<String>();

	public static int LayersCalculating=1;                         //计算层数的参数
	public static ArrayList<String> begin=new ArrayList<String>();
	public static ArrayList<String> end=new ArrayList<String>();
	public static ArrayList<String> out=new ArrayList<String>();
	public static ArrayList<String> storage=new ArrayList<String>();
	public static ArrayList<String> parameter=new ArrayList<String>();
	public static ArrayList<Integer> LagersStorage=new ArrayList<Integer>();
	public static ArrayList<String> value=new ArrayList<String>();
	public static ArrayList<String> st=new ArrayList<String>();
	public static ArrayList<String> parameterend=new ArrayList<String>();
	public static ArrayList<String> valueend=new ArrayList<String>();//记录最后的值

	public static ArrayList<String> ts=new ArrayList<String>();
	public static ArrayList<String> collect=new ArrayList<String>();
	public static Read_Xml rx=new Read_Xml();
	public static void main(String[] args) throws IOException, IllegalAccessException, NativeException, InterruptedException {

	}


	public static String ExecuteCombineMemberDll(Read_Xml read_xml,ArrayList<String> parameter,ArrayList<String> huan) throws IOException, IllegalAccessException, NativeException, InterruptedException
	{

		if(flag1)
		{


			for(int i=0;i<read_xml.map.MermberRelation.size();i++)  
			{
				String[] strarray=read_xml.map.MermberRelation.get(i).split("-");
				begin.add(strarray[0]);
				end.add(strarray[1]);
				storage.add(strarray[0]);
				storage.add(strarray[1]);

			}

			flag1=false;
		}

		LagersStorage.clear();
		for(int i=0;i<parameter.size();i++)
		{
			String[] strarray=parameter.get(i).split("-"); 
			parameterend.add(strarray[0]);
		}

		String str1="";
		String str="";
		String str3="";

		if((begin.size()==0)&&(end.size()==0))
		{		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		

			out=method(out);



			for(int i=0;i<out.size();i++)
			{
				String[] strarray=out.get(i).split("!");
				value.add(strarray[0]);

			}
			System.out.println("WWWWWWWWWWWWW:"+df.format(new Date()));// new Date()为获取当前系统时间
			System.out.println("value.size():"+value.size()+"storage.size():"+storage.size());// new Date()为获取当前系统时间
			for(int i=0;i<value.size();i++)
			{
				for(int c=0;c<storage.size();c++)
				{
					if(!equals(value,storage.get(c)))
					{
						st.add(storage.get(c));
					
					}
				}
			}

			st=method(st);
			System.out.println("EEEEEEEEEEEEEEEEEEEE:"+df.format(new Date()));// new Date()为获取当前系统时间
			for(int i=0;i<out.size()-1;i++)
			{
				String[] strarray=out.get(i).split("!"); 

				str1=numberparameter(read_xml.map.MermberRelation,strarray[0],valueend);
				if(strarray[1].equals(out.get(i+1).split("!")[1]))
				{   

					if(equals(parameterend,strarray[0]))
					{  

						for(int k=0;k<parameterend.size();k++)
						{	
							str1=dll(find(read_xml.Dllname.MemberDLLName,strarray[0]),find(parameter,strarray[0]), read_xml,huan,strarray[0])+"*"+dll(find(read_xml.Dllname.MemberDLLName,out.get(i+1).split("!")[0]),find(parameter,out.get(i+1).split("!")[0]), read_xml, huan,out.get(i+1).split("!")[0]);


						}
						valueend.add(dll(find(read_xml.Dllname.MemberDLLName,strarray[0]),str1, read_xml, huan,strarray[0])+"-"+strarray[0]);
						str1=numberparameter(read_xml.map.MermberRelation,out.get(i+1).split("!")[0],valueend);
						valueend.add(dll(find(read_xml.Dllname.MemberDLLName,out.get(i+1).split("!")[0]),find(parameter,out.get(i+1).split("!")[0]), read_xml, huan,out.get(i+1).split("!")[0])+"-"+out.get(i+1).split("!")[0]);

						i=i+2;
					}

					else
					{   




						valueend.add(dll(find(read_xml.Dllname.MemberDLLName,strarray[0]),str1, read_xml, huan,strarray[0])+"-"+strarray[0]);
						str1=numberparameter(read_xml.map.MermberRelation,out.get(i+1).split("!")[0],valueend);
						valueend.add(dll(find(read_xml.Dllname.MemberDLLName,out.get(i+1).split("!")[0]),str1,read_xml, huan,out.get(i+1).split("!")[0])+"-"+out.get(i+1).split("!")[0]);

						i=i+2;
					}
				}
				else 
				{
					if(equals(read_xml.combineparameter.InputMemID,strarray[0]))
					{ 

						if(str1=="")
						{

							if(equals(parameterend,out.get(out.size()-1).split("!")[0])){
								str1=dll(find(read_xml.Dllname.MemberDLLName,strarray[0]),find(parameter,strarray[0]), read_xml, huan,strarray[0]);
								valueend.add(str1+"-"+strarray[0]);
							}
							else
							{
							//	System.out.println(str1+"ssssssssssss");
								str1=dll(find(read_xml.Dllname.MemberDLLName,strarray[0]),find(read_xml.combineparameter.CombineValue,strarray[0]), read_xml, huan,strarray[0]);
								valueend.add(str1+"-"+strarray[0]);
							}
					
						}
						else
						{
							if(equals(parameterend,out.get(out.size()-1).split("!")[0])){
								str1=dll(find(read_xml.Dllname.MemberDLLName,strarray[0]),str1+"/"+find(parameter,strarray[0]), read_xml, huan,strarray[0]);
								valueend.add(str1+"-"+strarray[0]);
							}
							else
							{
							
								str1=dll(find(read_xml.Dllname.MemberDLLName,strarray[0]),str1+"/"+find(read_xml.combineparameter.CombineValue,strarray[0]), read_xml, huan,strarray[0]);
								valueend.add(str1+"-"+strarray[0]);
							}
					
				
						}
					}
					else
					{  
						if(str1=="")
						{
							for(int q=0;q<read_xml.map.TargetInput.size();q++)
							{
								if(!read_xml.map.TargetInput.get(q).split("-")[0].equals(out.get(0).split("!")[0]))
								{

									for(int k=0;k<read_xml.combineparameter.CombineValue.size();k++)
									{
										if(read_xml.combineparameter.CombineValue.get(k).split("-")[0].equals(out.get(0).split("!")[0]))
										{
											str1=read_xml.combineparameter.CombineValue.get(k).split("-")[1];
											q=read_xml.map.TargetInput.size();


										}

									}

								}
							}
							
							str1=dll(find(read_xml.Dllname.MemberDLLName,strarray[0]),str1, read_xml, huan,strarray[0]);
							valueend.add(str1+"-"+strarray[0]);
						}
						else
						{

							for(int q=0;q<read_xml.map.TargetInput.size();q++)
							{
								if(!read_xml.map.TargetInput.get(q).split("-")[0].equals(out.get(0).split("!")[0]))
								{

									for(int k=0;k<read_xml.combineparameter.CombineValue.size();k++)
									{
										if(read_xml.combineparameter.CombineValue.get(k).split("-")[0].equals(out.get(0).split("!")[0]))
										{
											str1=str1+"/"+read_xml.combineparameter.CombineValue.get(k).split("-")[1];
											q=read_xml.map.TargetInput.size();


										}

									}

								}
							}
						
							str1=dll(find(read_xml.Dllname.MemberDLLName,strarray[0]),str1, read_xml, huan,strarray[0]);
							valueend.add(str1+"-"+strarray[0]);
						}
					}
				}

			}

			
			str3=str1;
		//	System.out.println(str1+"rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
			if(equals(read_xml.combineparameter.InputMemID,out.get(out.size()-1).split("!")[0]))
			{ 
				if(equals(parameterend,out.get(out.size()-1).split("!")[0])){
					str1=dll(find(read_xml.Dllname.MemberDLLName,out.get(out.size()-1).split("!")[0]),find(parameter,out.get(out.size()-1).split("!")[0])+"/"+str1,read_xml, huan,out.get(out.size()-1).split("!")[0]);
					valueend.add(str1+"-"+out.get(out.size()-1).split("!")[0]);
				}
				else
				{
		//			System.out.println(find(read_xml.combineparameter.CombineValue,out.get(out.size()-1).split("!")[0])+"ssssssssssss");
					str1=dll(find(read_xml.Dllname.MemberDLLName,out.get(out.size()-1).split("!")[0]),find(read_xml.combineparameter.CombineValue,out.get(out.size()-1).split("!")[0])+"/"+str1,read_xml, huan,out.get(out.size()-1).split("!")[0]);
					valueend.add(str1+"-"+out.get(out.size()-1).split("!")[0]);
				}
			}
			else
			{    
				str1=dll(find(read_xml.Dllname.MemberDLLName,out.get(out.size()-1).split("!")[0]),str1,read_xml, huan,out.get(out.size()-1).split("!")[0]);
				valueend.add(str1+"-"+out.get(out.size()-1).split("!")[0]);
			}
			if(st.size()!=0)
			{
				for(int i=0;i<st.size();i++)
				{	

					valueend.add(dll(find(read_xml.Dllname.MemberDLLName,st.get(i)),str3, read_xml, huan,st.get(i))+"-"+st.get(i));

				}

			}
			str1="";
			valueend=method(valueend);
			System.out.println("valueend"+valueend);
			rx.map.TargetOutput=method(rx.map.TargetOutput);
			for(int k=0;k<rx.map.TargetOutput.size();k++)
			{
				for(int i=0;i<valueend.size();i++)
				{
					if(rx.map.TargetOutput.get(k).split("-")[0].equals(valueend.get(i).split("-")[1]))
					{
						//	temp=rx.map.TargetOutput.get(k).split("-")[1]+"-"+valueend.get(i).split("-")[0];	
						//str1=temp+","+str1;
						if(str1=="")
						{
							str1=rx.map.TargetOutput.get(k).split("-")[1]+"-"+valueend.get(i).split("-")[0];	
						}
						else
						{
							str1=str1+","+rx.map.TargetOutput.get(k).split("-")[1]+"-"+valueend.get(i).split("-")[0];
						}
					}
				}
			}

			valueend.clear();
		}

		else
		{
			if(pane.size()==0){

				for(int i=0;i<end.size();i++)
				{
					for(int k=0;k<begin.size();k++)
					{
						if(!equals(end,begin.get(k)))
						{
							pane.add(begin.get(k));
						}
					}
				}

			} 

			if((pane.size()==0)&&flag)
			{
				pane.add(rx.map.TargetInput.get(0).split("-")[0]);
				flag=false;
			}
			pane=method(pane);
			for(int i=0;i<begin.size();i++)
			{
				for(int a=0;a<pane.size();a++)
				{
					if(pane.get(a).equals(begin.get(i)))
					{

						out.add(begin.get(i)+"!"+LayersCalculating);
						LagersStorage.add(i);

					}
					else
					{
						out.add(pane.get(a)+"!"+LayersCalculating);
					}
				}
			}
			pane.clear();
			for(int i=0;i<LagersStorage.size();i++)
			{	

				begin.remove((int)LagersStorage.get(i)-i);
				pane.add(end.get((int)LagersStorage.get(i)-i));


				if(begin.size()==0)
				{ 
					LayersCalculating=LayersCalculating+1;
					str=end.get((int)LagersStorage.get(i)-i)+"!"+LayersCalculating;
					out.add(str); 
				}
				end.remove((int)LagersStorage.get(i)-i);

			}


			for(int i=0;i<end.size();i++)
			{
				for(int s=0;s<pane.size();s++)
				{if(i<end.size())
				{
					if(end.get(i).equals(pane.get(s)))
					{  

						RetentionRelationship.add(begin.get(i)+"-"+end.get(i));

						end.remove(i);
						begin.remove(i);
					}
				}
				}

			}

			//Thread.sleep(10000);
			LayersCalculating=LayersCalculating+1;

			return ExecuteCombineMemberDll(read_xml, parameter,huan);

		}
		//System.out.println("RetentionRelationship"+RetentionRelationship);
		ts.clear();
		value.clear();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println("AAAAAAA:"+df.format(new Date()));// new Date()为获取当前系统时间
		return "C#M@"+str1;

	}




	public static String find(ArrayList<String> array,String str)
	{
		String string="";

		for(int i=0;i<array.size();i++)
		{
			String[] strarray=array.get(i).split("-"); 
			if(strarray[0].equals(str))
			{
				string=strarray[1];
			}
		}
		return string;
	}
	public static boolean equals(ArrayList<String> array,String str) {
		boolean re=false;

		for(int i=0;i<array.size();i++)
		{
			if(array.get(i).equals(str))
			{
				re=true;
			}
		}


		return re;
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

	public static String dll(String str,String str1,Read_Xml read_xml,ArrayList<String> huan,String temp) throws IllegalAccessException, NativeException, IOException

	{
		String fileName="test.txt";
		File file = new File(fileName);
		if(huan.size()==0)
		{
			for(int i=0;i<RetentionRelationship.size();i++)
			{
				if(RetentionRelationship.get(i).split("-")[1].equals(temp))
				{	

					for(int k=0;k<read_xml.combineparameter.CombineValue.size();k++)
					{
						if(read_xml.combineparameter.CombineValue.get(k).split("-")[0].equals(RetentionRelationship.get(i).split("-")[0]))
						{   
							if(str1=="")
							{
								for(int s=0;s<read_xml.combineparameter.CombineValue.size();s++)
								{
									if(read_xml.combineparameter.CombineValue.get(s).split("-")[0].equals(temp))
									{  
										str1=read_xml.combineparameter.CombineValue.get(s).split("-")[1];
									}
								}

							}			
							str1=str1+"/"+read_xml.combineparameter.CombineValue.get(i).split("-")[1];

						}
					}
				}
			}
		}
		else
		{
			for(int i=0;i<RetentionRelationship.size();i++)
			{ 
				if(RetentionRelationship.get(i).split("-")[1].equals(temp))
				{	

					for(int k=0;k<huan.size();k++)
					{    
						if(huan.get(k).split("-")[0].equals(RetentionRelationship.get(i).split("-")[0]))
						{  
							if(str1=="")

							{
								for(int s=0;s<read_xml.combineparameter.CombineValue.size();s++)
								{
									if(read_xml.combineparameter.CombineValue.get(s).split("-")[0].equals(temp))
									{
										str1=read_xml.combineparameter.CombineValue.get(s).split("-")[1];
									}
								}

							}
						
							str1=str1+"/"+huan.get(k).split("-")[1];


						}
					}
				}
			}
		}
		
	
		JNative jnative=new JNative (str,"run");
		jnative.setRetVal(Type.STRING);
		jnative.setParameter(0,str1);//设置调用阶段的参数
		jnative.invoke();//对函数进行调用
		for(int i=0;i<RetentionRelationship.size();i++)
		{
			if(RetentionRelationship.get(i).split("-")[0].equals(temp))
			{	

				for(int s=0;s<ts.size();s++)
				{
					if(!ts.get(s).equals(temp))
					{	
						if(!file.exists())
						{
							file.createNewFile();
						}
						FileOutputStream fos = new FileOutputStream(file, true);  
						OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");  
						osw.write(temp+"-"+jnative.getRetVal()+"\r\n");  

						osw.close();  
						fos.close();  

					}
				}
				if(ts.size()==0)
				{
					if(!file.exists())
					{
						file.createNewFile();
					}
					FileOutputStream fos = new FileOutputStream(file, true);  
					OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");  		
					osw.write(RetentionRelationship.get(i).split("-")[0]+"-"+jnative.getRetVal()+"\r\n");  
					osw.close();  
					fos.close();  

				}
				ts.add(temp);
			}
		}

		
		
		return jnative.getRetVal();

	}


	public static String get(ArrayList<String> array,String str)
	{
		String string="";

		for(int i=0;i<array.size();i++)
		{
			String[] strarray=array.get(i).split("-"); 
			if(strarray[1].equals(str))
			{
				string=strarray[0];
			}
		}
		return string;
	}
	public static String numberparameter(ArrayList<String> arrayList,String str,ArrayList<String> List)
	{
		String temp="";
		if(arrayList.size()==0)
		{
			return "";
		}
		else
		{
			for(int i=0;i<arrayList.size();i++)
			{
				if(str.equals(arrayList.get(i).split("-")[1]))
				{
					for(int a=0;a<List.size();a++)
					{
						if(arrayList.get(i).split("-")[0].equals(List.get(a).split("-")[1]))
						{
							if(temp=="")
							{
								temp=List.get(a).split("-")[0];
							}
							else
							{
								temp=temp+"*"+List.get(a).split("-")[0];
							}
						}
					}
				}
			}

		}
		str=temp;

		return str;

	}
}

//}