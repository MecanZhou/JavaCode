/**
 * 
 */
package edu.jhun.test;

import java.sql.Connection;

import org.junit.Test;

import edu.jhun.service.AnalysisService;
import edu.jhun.utils.DBHelper;

/**
 * @author Administrator
 * @time 2018年1月22日下午3:46:40
 *
 */
public class AnalysisServiceTest {
	
	private Connection connection = DBHelper.getConnection();
	private AnalysisService analysisService = new AnalysisService();
	
	@Test
	public void testGetLR() {
		analysisService.getLinearRegression(connection, "datacollect0-3-1-3", "1-30000");
	}
//	@Test
//	public void testGetSLR() {
//		analysisService.getSimpleLinearRegression(connection, "datacollect0-3-1-1-3", "1-5000");
//	}
//	@Test
//	public void testGetAR() {
//		analysisService.getApriori("tb_contact_lenses");
//	}
//	@Test
//	public void testGetStatistics(){
//		analysisService.getStatistics("datacollect0-1-3-30-50", connection);
//	}
//	@Test
//	public void testGetDirectData(){
//		analysisService.getDirectData("datacollect0-1-3-30-50", connection);
//	}
	
}
