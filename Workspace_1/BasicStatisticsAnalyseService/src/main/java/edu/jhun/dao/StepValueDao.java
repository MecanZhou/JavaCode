package edu.jhun.dao;

/**
 * 根据解析的请求字符串，
 * 完成数据库的查询，
 * 结果集存入PreParedData
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.mysql.jdbc.Connection;

import edu.jhun.bean.PreParedData;
import edu.jhun.bean.RequestedString;
import edu.jhun.utils.DBHelper;

public class StepValueDao extends BaseDAO{

	private String caseName;//表名
	//数据库名称：simulationdatacollection
	//表名：datacollect0
	public StepValueDao() {
	
		this.caseName=null;
	}
	
	
	//根据条件取数据库的数据：Outputvalue,step
	
	public synchronized ResultSet findReqData(RequestedString reqStr,Connection con)
	{
		caseName=reqStr.getCaseName();
		ResultSet rs=null;
		rs=query(con, 
				"select Outputvalue,step from "+caseName+" where FederationId=? and MemberId=? and step between ? and ?",
				reqStr.getFederationId(),reqStr.getMemberId(),reqStr.getStartStep(),reqStr.getEndStep());

		return rs;
	}
 }
