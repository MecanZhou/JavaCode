package edu.jhun.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Connection;

import edu.jhun.bean.PreParedData;
import edu.jhun.bean.RequestedString;
import edu.jhun.bean.Statistic;
import edu.jhun.dao.CaculationDAO;
import edu.jhun.dao.StepValueDao;
import edu.jhun.utils.DBHelper;

public class AnalysisService {
	////计算平均值、方差、中值、最大值、最小值……
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//设置日期格式
	private CaculationDAO ca=new CaculationDAO();

		
	//获取统计数据
	 public synchronized String getStatistics(String reqStr,Connection con)
	 {
		  RequestedString requestString=new RequestedString(reqStr);
	      StepValueDao dbDao=new StepValueDao();
	        String result="";
	    	ArrayList<Statistic> sta=ca.caculateStatistics(ca.getStructData(dbDao.findReqData(requestString,con)));
			for (Statistic statistic : sta) {
				result=result+statistic.toString();
			}
			System.out.println(df.format(new Date())+" Request the Service: "+Thread.currentThread().getStackTrace()[1].getMethodName()+"\nResult:\n");
			System.out.println(result);

			return result;
	 }

	//获取步长，值
		public String/*ArrayList<PreParedData>*/ getDirectData(String reqStr,Connection con){
			 RequestedString requestString=new RequestedString(reqStr);
			 StepValueDao dbDao=new StepValueDao();
			 String str="";
			 ArrayList<PreParedData> pre=ca.getStructData(dbDao.findReqData(requestString,con));
			 for (PreParedData preParedData : pre) {
					str=str+preParedData.getStep()+"-"+preParedData.getOuputValue()+"-";
				}
			 System.out.println(df.format(new Date())+" Request the Service: "+Thread.currentThread().getStackTrace()[1].getMethodName()+"\nResult:\n");
			 System.out.println();
			 return str;
		
		}
		

}
