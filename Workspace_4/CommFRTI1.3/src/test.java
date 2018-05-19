
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;

import se.pitch.prti.LogicalTimeDouble;
import se.pitch.prti.LogicalTimeIntervalDouble;
import hla.rti.IllegalTimeArithmetic;
import hla.rti.InvalidFederationTime;
import hla.rti.InvalidLookahead;
import hla.rti.LogicalTime;
import hla.rti.LogicalTimeInterval;


public class test {
	public static ArrayList<String> huan=new ArrayList<String>();
	public static ArrayList<String> relation=new ArrayList<String>();
	public static ArrayList<String> parameter=new ArrayList<String>();
	public static Read_Xml rx=new Read_Xml();
	/**
	 * @param args
	 * @throws InvalidFederationTime 
	 * @throws IllegalTimeArithmetic 
	 * @throws InvalidLookahead 
	 * @throws SQLException 
	 * @throws  
	 * @throws NativeException 
	 * @throws IOException 
	 * @throws IllegalAccessException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InvalidFederationTime, IllegalTimeArithmetic, InvalidLookahead, SQLException, IllegalAccessException, IOException, NativeException, InterruptedException  {
		// TODO Auto-generated method stub
		rx.ReadXml("C:\\Users\\daiy\\Desktop\\skySom.xml", "1");
		String temp1 = "Model" + rx.ModelID;
		for(int i=0;i<rx.Dllname.MemberDLLName.size();i++)
		{
			String [] MemberDLLName=null;
			MemberDLLName=rx.Dllname.MemberDLLName.get(i).split("-");
			System.load("C://HLA//"+rx.FederationName+"//"+temp1+"//"+MemberDLLName[1]);
			System.out.println("加载"+rx.Dllname.MemberDLLName.get(i)+"成功");
		}
//		System.load("C://Users//daiy//Desktop//dll//CT3.dll");
//	JNative jnative=new JNative ("CT3","run");
//jnative.setRetVal(Type.STRING);
//	jnative.setParameter(0,"10");//设置调用阶段的参数
//	jnative.invoke();//对函数进行调用
//	System.out.println(jnative.getRetVal());
	//	parameter.add("2-30");
	//	parameter.add("3-30");
		  String fileName="test.txt";
			File file = new File(fileName);//file.createNewFile();
			//Thread.sleep(100000);
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
			DLLhandle d=new DLLhandle();
		String a=d.ExecuteCombineMemberDll(rx,parameter,huan);
		
		System.out.println(a+"ccc");	
			
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