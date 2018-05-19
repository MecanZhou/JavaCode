/**
 * 
 */
package edu.jhun.test;

import org.junit.Test;

import edu.jhun.dao.AttributeDAO;

/**
 * @author Administrator
 * @time 2018年1月16日下午5:50:16
 *
 */
public class AttributeDAOTest {

	@Test
	public void testFindAttrOutput() {
		String str = "1-1-1-1000-1010";
		AttributeDAO aDao = new AttributeDAO();
		aDao.findAttrOutput(str);
		str = "1-2-1-1000-1010";
		aDao.findAttrOutput(str);
	}
}
