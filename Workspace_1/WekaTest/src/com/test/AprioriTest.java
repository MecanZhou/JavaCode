/**
 * 
 */
package com.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import weka.associations.Apriori;
import weka.core.Instances;

/**
 * @author Administrator
 * @time 2018��1��10������3:03:03
 *	weka Apriori�㷨����
 */
public class AprioriTest {

	private static final boolean car = false;
	private static final int classIndex = -1;
	private static final double delta = 0.05;
	private static final boolean doNotCheckCapabilities = false;
	private static final double lowerBoundMinSupport = 0.5;
	private static final double minMetric = 0.9;
	private static final int numRules = 10;
	private static final boolean outputItemSets= true;
	private static final boolean removeAllMissingCols=false;
	private static final double significanceLevel = -1.0;
	private static final boolean treatZeroAsMissing = false;
	private static final double upperBoundMinSupport = 1.0;
	private static final boolean verbose = false;
	
	public String testApriori(){
		//�������ݼ�ʵ������
		Instances dataset = null;
		//��ȡweka��ʽ�ļ�����ȡ����Դ
		//dataset = GetData.getInsByFile("contact-lenses.arff");
		//��ȡweka���ݿ��ļ���������nominal
		dataset = GetData.getInsByDB("tb_contact_lenses");
		
		//��������������
		//dataset.setClassIndex(dataset.numAttributes() - 1);
		
		//��ȡApriori����ʵ�� �������㷨���� ����������
		String aprioriResult = null;
		Apriori apriori = new Apriori();
		//�����㷨���в���
		//��������false��ȫ�ֹ���
		apriori.setCar(car);
		//����������
		apriori.setClassIndex(classIndex);
		//�����ݼ���λ
		apriori.setDelta(delta);
		//���������
		apriori.setDoNotCheckCapabilities(doNotCheckCapabilities);
		//��С֧�ֶ��½�
		apriori.setLowerBoundMinSupport(lowerBoundMinSupport);
		//������Сֵ
		apriori.setMinMetric(minMetric);
		//����������
		apriori.setNumRules(numRules);
		//�Ƿ�����
		apriori.setOutputItemSets(outputItemSets);
		//�Ƴ�ȫ��Ϊȱʡ����
		apriori.setRemoveAllMissingCols(removeAllMissingCols);
		//��Ҫ�̶�
		apriori.setSignificanceLevel(significanceLevel);
		//�Ƿ���0Ϊ��ʧֵ
		apriori.setTreatZeroAsMissing(treatZeroAsMissing);
		//��С֧�ֶ��Ͻ�
		apriori.setUpperBoundMinSupport(upperBoundMinSupport);
		//�Ƿ�������ģʽ����
		apriori.setVerbose(verbose);
		
		//��ʼ���������㷨����
		try{
			apriori.buildAssociations(dataset);
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		aprioriResult = apriori.toString();
		return aprioriResult;
	}
	
	public static void main(String[] args) throws IOException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//�������ڸ�ʽ
		System.out.println(df.format(new Date())+"  Request the Service -START: "+Thread.currentThread().getStackTrace()[1].getMethodName()+"***");
		
		AprioriTest aprioriTest = new AprioriTest();
		String string= aprioriTest.testApriori();
		System.out.println(string);
		System.out.println(df.format(new Date())+"  Request the Service -END: "+Thread.currentThread().getStackTrace()[1].getMethodName()+"***");
		//������Ϊ.txt�ļ���ʽ
		File file = new File("test.txt");  
		
        PrintStream ps;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ps = new PrintStream(new FileOutputStream(file));
			ps.println("=====˵��=====");// ���ļ���д���ַ���  
			ps.append("<conf>:���Ŷȣ��ٷֱȣ�\n<lift>:������  ��min=1,��ʾ�¼�����,Խ�����Խ���ܣ�\n"
					+ "<lev>:�ܸ��� ��min=0,��ʾ�¼�����,Խ�����Խ���ܣ�\n<conv>:ȷ�Ŷ�,Խ�����Խ����\n");
			ps.append("============");
			ps.append(string);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
            StringBuilder stringBuilder = new StringBuilder();
            while (bufferedReader.ready()) {
                String line = bufferedReader.readLine();
                stringBuilder.append(line).append("$");
                
            }
            System.out.println(stringBuilder.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  
	}
}
