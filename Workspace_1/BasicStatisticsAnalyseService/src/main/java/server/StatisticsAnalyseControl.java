package server;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.jdbc.Connection;

import edu.jhun.bean.PreParedData;
import edu.jhun.services.AnalysisService;
import edu.jhun.utils.DBHelper;

/**
 *基本统计数据计算服务控制器
 * @author Administrator
 *
 */
@RestController
public class StatisticsAnalyseControl {
	 private AnalysisService analyzeService=new AnalysisService();
	 private Connection con=DBHelper.getConnection();
	 
	@Autowired
    private DiscoveryClient client;
	/**
	 * 获取基本统计数据的服务
	 * @param reqStr（方案名-仿真次数-属性名-起始步长-终止步长）
	 * @return（最大值-**-最小值-**-平均值-**-方差-**-）
	 */
	  //getBasicStatisticsAnalysis?reqStr=datacollect0-1-3-30-50
      //最大值-25.0-最小值-15.0-平均值-20.238095238095237-方差-0.6629309000588888-
	    @RequestMapping(value = "/baseSta" ,method = RequestMethod.GET)
	    public String getBasicStatisticsAnalysis(@RequestParam String reqStr) {   
			String str=analyzeService.getStatistics(reqStr,con);
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
		 String str=analyzeService.getDirectData(reqStr, con);
		 return str;
	    }
	
		

		
}
