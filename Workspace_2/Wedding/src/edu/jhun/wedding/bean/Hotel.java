package edu.jhun.wedding.bean;
/**
 * ¾Æµê°ü×°Àà
 * @author Administrator
 *
 */
public class Hotel {

	private int hotel_id;
	private String hotel_name;
	private String hotel_tel;
	private String hotel_star;
	private String hotel_img;
	public int getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(int hotel_id) {
		this.hotel_id = hotel_id;
	}
	public String getHotel_name() {
		return hotel_name;
	}
	public void setHotel_name(String hotel_name) {
		this.hotel_name = hotel_name;
	}
	public String getHotel_tel() {
		return hotel_tel;
	}
	public void setHotel_tel(String hotel_tel) {
		this.hotel_tel = hotel_tel;
	}
	public String getHotel_star() {
		return hotel_star;
	}
	public void setHotel_star(String hotel_star) {
		this.hotel_star = hotel_star;
	}
	public String getHotel_img() {
		return hotel_img;
	}
	public void setHotel_img(String hotel_img) {
		this.hotel_img = hotel_img;
	}
	@Override
	public String toString() {
		return "Hotel [hotel_id=" + hotel_id + ", hotel_name=" + hotel_name
				+ ", hotel_tel=" + hotel_tel + ", hotel_star=" + hotel_star
				+ ", hotel_img=" + hotel_img + "]";
	}
	public Hotel(int hotel_id, String hotel_name, String hotel_tel,
			String hotel_star, String hotel_img) {
		super();
		this.hotel_id = hotel_id;
		this.hotel_name = hotel_name;
		this.hotel_tel = hotel_tel;
		this.hotel_star = hotel_star;
		this.hotel_img = hotel_img;
	}
	public Hotel() {
		super();
	}
	
	
}
