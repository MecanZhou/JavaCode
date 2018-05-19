/**
 * 
 */
package test.weka;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import test.db.DBHelper;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * @author Administrator
 * @time 2018年1月18日下午1:00:26
 * 线性回归算法不用数据库连接池连接速度测试
 */
public class LinearRegressionTest {
	
	public static void main(String[] args) {
		LinearRegressionTest lrt= new LinearRegressionTest();
		try {
			//lrt.testLinearRegression();
			System.out.println(lrt.getLinearRegressionResult("1,1-2,1000-1010"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//线性回归分析服务(str:[方案ID],[欲分析的属性ID],[欲分析步长])
	public String getLinearRegressionResult(String str) {
		int maxAttrs = 0;
		String[] s1= new String[str.length()];
		s1=str.split(",");
//		for(int i=0;i<s1.length;i++){
//			System.out.println(s1[i]);
//		}
		String[] s2= new String[s1[1].length()];
		s2=s1[1].split("-");
//		for(int i=0;i<s2.length;i++){
//			System.out.println(s2[i]);
//		}
		String[] s3= new String[s1[1].length()];
		s3=s1[2].split("-");
		try(Connection connection = DBHelper.getConnection()) {
			DBHelper cDao = new DBHelper();
			ResultSet rs = cDao.query(connection, "SELECT MAX(MemberId) FROM datacollect0 WHERE FederationId = ?", s1[0]);
			if (rs.first()) {
				maxAttrs = rs.getInt(1);
				rs.close();
			}else {
				return null;
			}
			ArrayList<Attribute> attrs = new ArrayList<>();
			//System.out.println(maxAttrs);
			for (int i = 0; i < maxAttrs; i++) {
				String attributeName = s1[0]+"-"+String.valueOf(i+1);
				attrs.add(i,new Attribute(attributeName, i));
			}
			Instances dataset = new Instances(s1[0], attrs, attrs.size());
			dataset.setClassIndex(Integer.valueOf(s2[1])-1);
			//System.out.println(rs.first());
			for (int i = Integer.valueOf(s3[0]); i <= Integer.valueOf(s3[1]); i++) {
				Instance instance = new DenseInstance(attrs.size());
				for (int j = 0; j < maxAttrs; j++) {
					rs  = cDao.query(connection, "select Outputvalue from datacollect0 where MemberId = ? and step = ?", j+1,i);
					if (rs.first()) {
						instance.setValue(attrs.get(j), rs.getDouble("Outputvalue"));
						rs.close();
					}
				}
				dataset.add(instance);
			}
			LinearRegression linearRegression = new LinearRegression();
			try {
				//为给出的数据建立回归模型
				linearRegression.buildClassifier(dataset);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//返回函数回归系数
			double[] coef = linearRegression.coefficients();
			String result = s1[1] + "=" ;
			for (int i = 0; i < coef.length; i++) {
				if (i==Integer.valueOf(s2[1])-1) {
					
				}else if (i==coef.length-1) {
					result += coef[i] ;
				}else {
					result += coef[i] + "* (" +s1[0]+"-"+ (i+1) +")"+ "+";
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
