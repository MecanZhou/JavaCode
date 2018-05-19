package edu.jhun.wedding.test;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import edu.jhun.wedding.bean.Hotel;
import edu.jhun.wedding.dao.HotelDAO;

/**
 * HotelDAO的测试
 * @author Administrator
 *
 */
public class HotelDAOTest {

	private HotelDAO dao = new HotelDAO();
	
	@Test
	public void testAddHotel(){
		Hotel hotel = new Hotel(1,"香格里拉大酒店2","888888","5","");
		Assert.assertEquals(true, dao.addHotel(hotel));
	}
	
	@Test
	public void testUpdate(){
		Hotel hotel = new Hotel(1,"沙县大酒店","666666","1","");
		Assert.assertEquals(true, dao.updateHotel(hotel));
	}
	
	@Test
	public void testDelete(){
		Assert.assertEquals(true, dao.deleteHotel(1));
	}
	
	@Test
	public void testFindHotelById(){
		Hotel hotel = dao.findHotelById(2);
		System.out.println(hotel);
	}
	
	@Test
	public void testFindAll(){
		List<Hotel> hotels = dao.findAllHotels();
		for(Hotel hotel : hotels){
			System.out.println(hotel);
		}
	}
}
