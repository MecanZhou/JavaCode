package com.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class LinearRegressionTest {

	public static void main(String[] args) {
		LinearRegressionTest linearRegressionTest= new LinearRegressionTest();
		try {
			//linearRegressionTest.linearRegression();
			linearRegressionTest.testLinearRegression();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testLinearRegression() throws Exception {
		
		//��������Դ�ļ���.arrf��
		//Instances dataset = getInsByFile("houses.arff");
		//��ȡ����Դ�⣨DB��
		Instances dataset = GetData.getInsByDB("tb_house");
		//��λҪ������������λ��
		dataset.setClassIndex(5);
		LinearRegression linearRegression = new LinearRegression();
		try {
			//Ϊ���������ݽ����ع�ģ��
			linearRegression.buildClassifier(dataset);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("365");
		}
		//���غ����ع�ϵ��
		double[] coef = linearRegression.coefficients();
//		double myHouseValue = (coef[0] * 3198) +
//				(coef[1] * 9669) +
//				(coef[2] * 5) +
//				(coef[3] * 3) +
//				(coef[4] * 1) +
//				coef[6];
		String myHouseValue = "myHouseValue = ";
//		for (int i = 0; i < coef.length; i++) {
//			myHouseValue+=i +"*"+ coef[i]+"+";
//		}
		
		myHouseValue+=linearRegression.toString();
		System.out.print(String.valueOf(myHouseValue));
	}
	
	//���ݿ�ȡ����������
	public void linearRegression() throws Exception {

		ArrayList<Attribute> attrs = new ArrayList<>();
		Attribute houseSize = new Attribute("houseSize",0);
		Attribute lotSize = new Attribute("lotSize",1);
		Attribute bedrooms = new Attribute("bedrooms",2);
		Attribute granite = new Attribute("granite",3);
		Attribute bathroom = new Attribute("bathroom",4);
		Attribute sellingPrice = new Attribute("sellingPrice",5);
		attrs.add(houseSize);
		attrs.add(lotSize);
		attrs.add(bedrooms);
		attrs.add(granite);
		attrs.add(bathroom);
		attrs.add(sellingPrice);
		Instances dataset = new Instances("houses", attrs, attrs.size());

		//��λҪ������������λ��
		//dataset.setClass(sellingPrice);
		dataset.setClassIndex(5);

		try(Connection connection = DBHelper.getConnection()) {
			DBHelper cDao = new DBHelper();
			ResultSet rs = cDao.query(connection, "select * from tb_house");
			while (rs.next()) {
				Instance instance = new DenseInstance(attrs.size());
				instance.setValue(houseSize, rs.getDouble("houseSize"));
				instance.setValue(lotSize, rs.getDouble("lotSize"));
				instance.setValue(bedrooms, rs.getDouble("bedrooms"));
				instance.setValue(granite, rs.getDouble("granite"));
				instance.setValue(bathroom, rs.getDouble("bathroom"));
				instance.setValue(sellingPrice, rs.getDouble("sellingPrice"));
				dataset.add(instance);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		LinearRegression linearRegression = new LinearRegression();
		try {
			//Ϊ���������ݽ����ع�ģ��
			linearRegression.buildClassifier(dataset);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//���غ����ع�ϵ��
		double[] coef = linearRegression.coefficients();
		double myHouseValue = (coef[0] * 3198) +
				(coef[1] * 9669) +
				(coef[2] * 5) +
				(coef[3] * 3) +
				(coef[4] * 1) +
				coef[6];

		System.out.print(String.valueOf(myHouseValue));
	}

}
