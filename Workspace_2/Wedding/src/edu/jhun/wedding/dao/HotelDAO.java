package edu.jhun.wedding.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.jhun.wedding.bean.Hotel;

/**
 * �Ƶ������ݷ�����
 * @author Administrator
 *
 */
public class HotelDAO extends BaseDAO{

	/**
	 * ��ӾƵ�
	 * @param hotel
	 * @return
	 */
	public boolean addHotel(Hotel hotel){
		return update(
				"insert into tb_hotel(hotel_name,hotel_tel,hotel_star,hotel_img) values(?,?,?,?)",
				hotel.getHotel_name(),hotel.getHotel_tel(),hotel.getHotel_star(),hotel.getHotel_img());
	}
	
	/**
	 * ��idɾ���Ƶ�
	 * @param hotel_id
	 * @return
	 */
	public boolean deleteHotel(int hotel_id){
		return update(
				"delete from tb_hotel where hotel_id = ?",
				hotel_id);
	}
	
	/**
	 * ��id���¾Ƶ�
	 * @param hotel
	 * @return
	 */
	public boolean updateHotel(Hotel hotel){
		return update(
				"update tb_hotel set hotel_name=?,hotel_tel=?,hotel_star=?,hotel_img=? where hotel_id=?",
				hotel.getHotel_name(),hotel.getHotel_tel(),
				hotel.getHotel_star(),hotel.getHotel_img(),hotel.getHotel_id());
	}
	
	/**
	 * ��id��ѯһ���Ƶ�
	 * @param hotel_id
	 * @return
	 */
	public Hotel findHotelById(int hotel_id){
		Hotel hotel = null;
		//��ȡ����������ݣ�����hotel����
		try (Connection conn = C3P0Utils.getConnection();){
			ResultSet rs = query(conn,"select * from tb_hotel where hotel_id=?",hotel_id);
			if(rs.first()){
				hotel = new Hotel();
				hotel.setHotel_id(rs.getInt("hotel_id"));
				hotel.setHotel_name(rs.getString("hotel_name"));
				hotel.setHotel_img(rs.getString("hotel_img"));
				hotel.setHotel_tel(rs.getString("hotel_tel"));
				hotel.setHotel_star(rs.getString("hotel_star"));
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return hotel;
	}
	
	/**
	 * ��ѯ���еľƵ�
	 * @return
	 */
	public List<Hotel> findAllHotels(){
		//�����Ƶ꼯��
		List<Hotel> hotels = new ArrayList<>();
		//ѭ����ȡ�������ÿһ�У���������ӵ�������
		try (Connection conn = C3P0Utils.getConnection()){
			//��ý����
			ResultSet rs = query(conn,"select * from tb_hotel");
			while(rs.next()){
				Hotel hotel = new Hotel();
				hotel.setHotel_id(rs.getInt("hotel_id"));
				hotel.setHotel_name(rs.getString("hotel_name"));
				hotel.setHotel_img(rs.getString("hotel_img"));
				hotel.setHotel_tel(rs.getString("hotel_tel"));
				hotel.setHotel_star(rs.getString("hotel_star"));
				hotels.add(hotel);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotels;
	}
}
