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
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//�������ڸ�ʽ
			System.out.println(df.format(new Date())+"  Request the Service -START: "+Thread.currentThread().getStackTrace()[1].getMethodName()+"***");
			
			System.out.println(lrt.getLinearRegressionResult("3","3-3","1-30000"));
			
			System.out.println(df.format(new Date())+"  Request the Service -END: "+Thread.currentThread().getStackTrace()[1].getMethodName()+"***");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//���Իع��������(str:[����ID],[������������ID]scheme-member-attr,[����������]start-end)
	public String getLinearRegressionResult(String schemeId,String attrId,String steplength) {
		int maxAttrs = 0;
		//��-Ϊ�ֽ���ҵ������Ե�λ�ã�s1[2]
		String[] s1= new String[attrId.length()];
		s1=attrId.split("-");
//		for(int i=0;i<s1.length;i++){
//			System.out.println(s1[i]);
//		}
		//��-Ϊ�ֽ���ҵ�����������s2[0]��s2[1]ֹ
		String[] s2= new String[steplength.length()];
		s2=steplength.split("-");
		//��ѯ���ݽ������Իع��㷨����
		try(Connection connection = C3P0Utils.getConnection()) {
			CommonDAO cDao = new CommonDAO();
			//��ѯ���������Ը���
			ResultSet rs = cDao.query(connection, "SELECT MAX(MemberId) FROM datacollect0 WHERE FederationId = ?", schemeId);
			if (rs.first()) {
				maxAttrs = rs.getInt(1);
				rs.close();
			}else {
				return null;
			}
			//��������list������������
			ArrayList<Attribute> weka_attrs = new ArrayList<>();
			//System.out.println(maxAttrs);
			for (int i = 0; i < maxAttrs; i++) {
				String attributeName = schemeId+"-"+String.valueOf(i+1);
				weka_attrs.add(i,new Attribute(attributeName, i));
			}
			//����ʵ�����ݼ�
			Instances dataset = new Instances(schemeId, weka_attrs, weka_attrs.size());
			//��λҪ����������λ��
			dataset.setClassIndex(Integer.valueOf(s1[1])-1);
			//System.out.println(rs.first());
			//Ϊ���������������ֵʵ��
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
				//��������ʵ������һ���������ֵ��
				Instance instance = new DenseInstance(weka_attrs.size());
				for (int j = 0; j < maxAttrs; j++) {
					//System.out.println(j+3*(i-Integer.valueOf(s2[0])));
					instance.setValue(weka_attrs.get(j), attrs.get(j+3*(i-Integer.valueOf(s2[0]))).getOutputvalue());
				}
				//������ʵ����ӵ�ʵ�����ݼ���
				dataset.add(instance);
			}

			/*�����ݿ�һ������ȡ����ֵ��ѯ����������ȡ
			for (int i = Integer.valueOf(s3[0]); i <= Integer.valueOf(s3[1]); i++) {
				//��������ʵ������һ���������ֵ��
				Instance instance = new DenseInstance(attrs.size());
				for (int j = 0; j < maxAttrs; j++) {
					rs  = cDao.query(connection, "select Outputvalue from datacollect0 where MemberId = ? and step = ?", j+1,i);
					if (rs.first()) {
						instance.setValue(attrs.get(j), rs.getDouble("Outputvalue"));
						rs.close();
					}
				}
				//������ʵ����ӵ�ʵ�����ݼ���
				dataset.add(instance);
			}*/
			//�������Իع�����
			LinearRegression linearRegression = new LinearRegression();
			try {
				//Ϊ���������ݽ����ع�ģ��
				linearRegression.buildClassifier(dataset);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//���غ����ع�ϵ��
//			double[] coef = linearRegression.coefficients();
//			String result = "(" + attrId +")" + "=" ;
//			for (int i = 0; i < coef.length; i++) {
//				if (i==Integer.valueOf(s1[1])-1) {
//					//���ʽ����ʾ������������ϵ��
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
