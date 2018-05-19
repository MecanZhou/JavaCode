/**
 * 
 */
package edu.jhun.test;

import org.junit.Test;

import edu.jhun.utils.DBHelper;

/**
 * @author Administrator
 * @time 2018年1月19日下午1:11:15
 *
 */
public class DBHelperTest {
	
	@Test
	public void testGetConnection() {
		//数据库获取连接对象测试
		DBHelper.getConnection();
	}

}
