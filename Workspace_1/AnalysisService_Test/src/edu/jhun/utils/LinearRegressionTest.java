package edu.jhun.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.jhun.dao.CommonDAO;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;


public class LinearRegressionTest {

	public static void main(String[] args) {
		LinearRegressionTest lrt= new LinearRegressionTest();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//设置日期格式
			System.out.println(df.format(new Date())+"  Request the Service -START: "+Thread.currentThread().getStackTrace()[1].getMethodName()+"***");
			
			System.out.println(lrt.getLinearRegressionResult("3","3-3","1-30000"));
			
			System.out.println(df.format(new Date())+"  Request the Service -END: "+Thread.currentThread().getStackTrace()[1].getMethodName()+"***");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//线性回归分析服务(str:[方案ID],[欲分析的属性ID]scheme-member-attr,[欲分析步长]start-end)
	public String getLinearRegressionResult(String schemeId,String attrId,String steplength) {
		int maxAttrs = 0;
		//以-为分界符找到类属性的位置，s1[2]
		String[] s1= new String[attrId.length()];
		s1=attrId.split("-");
//		for(int i=0;i<s1.length;i++){
//			System.out.println(s1[i]);
//		}
		//以-为分界符找到分析步长，s2[0]起s2[1]止
		String[] s2= new String[steplength.length()];
		s2=steplength.split("-");
		//查询数据进行线性回归算法计算
		try(Connection connection = C3P0Utils.getConnection()) {
			CommonDAO cDao = new CommonDAO();
			//查询方案中属性个数
			ResultSet rs = cDao.query(connection, "SELECT MAX(MemberId) FROM datacollect0 WHERE FederationId = ?", schemeId);
			if (rs.first()) {
				maxAttrs = rs.getInt(1);
				rs.close();
			}else {
				return null;
			}
			//建立属性list，储存属性名
			ArrayList<Attribute> weka_attrs = new ArrayList<>();
			//System.out.println(maxAttrs);
			for (int i = 0; i < maxAttrs; i++) {
				String attributeName = schemeId+"-"+String.valueOf(i+1);
				weka_attrs.add(i,new Attribute(attributeName, i));
			}
			//建立实例数据集
			Instances dataset = new Instances(schemeId, weka_attrs, weka_attrs.size());
			//定位要分析的属性位置
			dataset.setClassIndex(Integer.valueOf(s1[1])-1);
			//System.out.println(rs.first());
			//为各个属性添加属性值实例
			List<edu.jhun.bean.Attribute> attrs = new ArrayList<>();
			rs = cDao.query(connection, "select * from datacollect0 where FederationId = ? and step between ? and ?",schemeId, s2[0],s2[1]);
			while (rs.next()) {
				edu.jhun.bean.Attribute attr = new edu.jhun.bean.Attribute();
				attr.setShemeId(s1[0]);
				attr.setMemberId(s1[1]);
				//attr.setAttrId(s2[2]);
				attr.setStep(rs.getInt("step"));
				attr.setOutputvalue(rs.getDouble("Outputvalue"));
				attrs.add(attr);
			}
			for (int i = Integer.valueOf(s2[0]); i < Integer.valueOf(s2[1]); i++) {
				//创建单个实例（即一组分析属性值）
				Instance instance = new DenseInstance(weka_attrs.size());
				for (int j = 0; j < maxAttrs; j++) {
					//System.out.println(j+3*(i-Integer.valueOf(s2[0])));
					instance.setValue(weka_attrs.get(j), attrs.get(j+3*(i-Integer.valueOf(s2[0]))).getOutputvalue());
				}
				//将单个实例添加到实例数据集中
				dataset.add(instance);
			}

			/*从数据库一个个获取属性值查询过慢，不可取
			for (int i = Integer.valueOf(s3[0]); i <= Integer.valueOf(s3[1]); i++) {
				//创建单个实例（即一组分析属性值）
				Instance instance = new DenseInstance(attrs.size());
				for (int j = 0; j < maxAttrs; j++) {
					rs  = cDao.query(connection, "select Outputvalue from datacollect0 where MemberId = ? and step = ?", j+1,i);
					if (rs.first()) {
						instance.setValue(attrs.get(j), rs.getDouble("Outputvalue"));
						rs.close();
					}
				}
				//将单个实例添加到实例数据集中
				dataset.add(instance);
			}*/
			//进行线性回归运算
			LinearRegression linearRegression = new LinearRegression();
			try {
				//为给出的数据建立回归模型
				linearRegression.buildClassifier(dataset);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//返回函数回归系数
//			double[] coef = linearRegression.coefficients();
//			String result = "(" + attrId +")" + "=" ;
//			for (int i = 0; i < coef.length; i++) {
//				if (i==Integer.valueOf(s1[1])-1) {
//					//表达式不显示被分析的属性系数
//				}else if (i==coef.length-1) {
//					result += coef[i] ;
//				}else {
//					result += coef[i] + "* (" + schemeId +"-"+ (i+1) +")"+ "+";
//				}
//			}
			String result = linearRegression.toString();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
