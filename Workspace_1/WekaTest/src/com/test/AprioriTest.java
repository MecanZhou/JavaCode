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
 * @time 2018年1月10日下午3:03:03
 *	weka Apriori算法测试
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
		//创建数据集实例对象
		Instances dataset = null;
		//读取weka格式文件，获取数据源
		//dataset = GetData.getInsByFile("contact-lenses.arff");
		//读取weka数据库文件，并配置nominal
		dataset = GetData.getInsByDB("tb_contact_lenses");
		
		//设置类属性索引
		//dataset.setClassIndex(dataset.numAttributes() - 1);
		
		//获取Apriori函数实例 ，进行算法计算 ，并输出结果
		String aprioriResult = null;
		Apriori apriori = new Apriori();
		//设置算法运行参数
		//关联规则：false：全局关联
		apriori.setCar(car);
		//类属性索引
		apriori.setClassIndex(classIndex);
		//迭代递减单位
		apriori.setDelta(delta);
		//不检查容量
		apriori.setDoNotCheckCapabilities(doNotCheckCapabilities);
		//最小支持度下界
		apriori.setLowerBoundMinSupport(lowerBoundMinSupport);
		//度量最小值
		apriori.setMinMetric(minMetric);
		//关联规则数
		apriori.setNumRules(numRules);
		//是否输出项集
		apriori.setOutputItemSets(outputItemSets);
		//移除全部为缺省的列
		apriori.setRemoveAllMissingCols(removeAllMissingCols);
		//重要程度
		apriori.setSignificanceLevel(significanceLevel);
		//是否视0为丢失值
		apriori.setTreatZeroAsMissing(treatZeroAsMissing);
		//最小支持度上届
		apriori.setUpperBoundMinSupport(upperBoundMinSupport);
		//是否以冗余模式运行
		apriori.setVerbose(verbose);
		
		//开始建立关联算法运算
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");//设置日期格式
		System.out.println(df.format(new Date())+"  Request the Service -START: "+Thread.currentThread().getStackTrace()[1].getMethodName()+"***");
		
		AprioriTest aprioriTest = new AprioriTest();
		String string= aprioriTest.testApriori();
		System.out.println(string);
		System.out.println(df.format(new Date())+"  Request the Service -END: "+Thread.currentThread().getStackTrace()[1].getMethodName()+"***");
		//结果输出为.txt文件格式
		File file = new File("test.txt");  
		
        PrintStream ps;
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			ps = new PrintStream(new FileOutputStream(file));
			ps.println("=====说明=====");// 往文件里写入字符串  
			ps.append("<conf>:置信度（百分比）\n<lift>:提升度  （min=1,表示事件独立,越大关联越紧密）\n"
					+ "<lev>:杠杆率 （min=0,表示事件独立,越大关联越紧密）\n<conv>:确信度,越大关联越紧密\n");
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
