/**
 * 
 */
package org.springframework.boot.AnalysisService;

import java.sql.Connection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.jhun.service.AnalysisService;
import edu.jhun.utils.DBHelper;

/**
 * @author Administrator
 * @time 2018年1月18日下午4:07:37
 *
 */
@RestController
public class ComputeController {

	private final Logger logger = Logger.getLogger(getClass());
	private Connection connection = DBHelper.getConnection();
	private AnalysisService analysisService= new AnalysisService();
	@Autowired
	private DiscoveryClient client;

	//微服务加法测试方法
	@RequestMapping(value = "/add" ,method = RequestMethod.GET)
	public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
		ServiceInstance instance = client.getLocalServiceInstance();
		Integer r = a + b;
		logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
		return r;
	}
	/**
	 * 获取基本统计数据的服务
	 * @param reqStr（方案名-仿真次数-属性名-起始步长-终止步长）
	 * @return（最大值-**-最小值-**-平均值-**-方差-**-）
	 */
	//getBasicStatisticsAnalysis?reqStr=datacollect0-1-3-30-50
	//最大值-25.0-最小值-15.0-平均值-20.238095238095237-方差-0.6629309000588888-
	@RequestMapping(value = "/getBasicStatisticsAnalysis" ,method = RequestMethod.GET)
	public String getBasicStatisticsAnalysis(@RequestParam String reqStr) {   
		String str=analysisService.getStatistics(reqStr,connection);
		return str;
	}
	/**
	 * 获取仿真数据信息（步长-值）
	 * @param reqStr（方案名-仿真次数-属性名-起始步长-终止步长）
	 * @return （步长-值-步长-值-）
	 */
	//获取某一属性的（值，步长）
	//返回string=“值-步长-值-步长-”
	@RequestMapping(value = "/getAtrributeRunData" ,method = RequestMethod.GET)
	public String getAtrributeRunData(@RequestParam String reqStr) { 
		String str=analysisService.getDirectData(reqStr, connection);
		return str;
	}

	/**
	 * //调用简单线性回归算法服务
	 * @param attrIndex 欲分析属性id ex:[datacollect0-1-1-2-3]
	 * @param steplength 欲分析步长范围 ex:[1-5000]
	 * @return double[2] 0,属性2影响系数  1,属性3影响系数 2,常数
	 */
	@RequestMapping(value = "/getSimpleLRResult", method = RequestMethod.GET)
	public String getSimpleLRResult(@RequestParam String attrIndex, @RequestParam String steplength) {
		String res = analysisService.getSimpleLinearRegression(connection, attrIndex, steplength);
		return res;
	}

	/**
	 * //调用多重线性回归算法服务
	 * @param attrId 欲分析属性id ex:[datacollect0-1-1-3]
	 * @param steplength 欲分析步长范围 ex:[1-5000]
	 * @return 线性回归关系表达式
	 */
	@RequestMapping(value = "/getLinearRegressionResult",method = RequestMethod.GET)
	public synchronized String getLinearRegressionResult(@RequestParam String attrId,@RequestParam String steplength) {
		String res=analysisService.getLinearRegression(connection, attrId, steplength);
		return res;
	}

	/**
	 * //调用Apriori算法服务
	 * @param tb 数据库表名
	 * @return apriori算法结果
	 */
	@RequestMapping(value = "/getAprioriResult" , method = RequestMethod.GET)
	public String getAprioriResult(@RequestParam String tb) {
		String res = analysisService.getApriori(tb);
		return res;
	}
}