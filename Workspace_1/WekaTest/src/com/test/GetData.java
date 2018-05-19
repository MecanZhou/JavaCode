/**
 * 
 */
package com.test;

import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.converters.DatabaseLoader;

/**
 * @author Administrator
 * @time 2018年1月12日下午3:43:26
 *
 */
public class GetData {
	
	//文件夹路径
	private static final String WEKA_PATH = "WekaData/";
	
	//从文件里获得数据集
	public static Instances getInsByFile(String filePath){

		Instances dataset= null;
		try {
			dataset = ConverterUtils.DataSource.read(WEKA_PATH+filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataset;
	}
	//从数据库获得数据集
	public static Instances getInsByDB(String tb){

		final String sqlUrl="jdbc:mysql://localhost:3306/weka_data";
		final String sqlUser="admin";
		final String sqlPwd="root";
		Instances dataset= null;
		try {
			DatabaseLoader loader = new DatabaseLoader();
			loader.setUrl(sqlUrl);
			loader.setUser(sqlUser);
			loader.setPassword(sqlPwd);
			loader.setQuery("select * from "+tb);
			dataset = loader.getDataSet();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataset;
	}
	
	//从数据库手动添加属性
	public static Instances getInsBySet(String str) {
		Instances dataset = null;
		
		return dataset;
	}

}
