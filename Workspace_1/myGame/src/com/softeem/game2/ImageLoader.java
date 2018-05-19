package com.softeem.game2;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * Í¼Æ¬¼ÓÔØÆ÷
 * @author Administrator
 *
 */
public class ImageLoader {

	public static BufferedImage load(String imgName)
	{
		try {
			URL url = ImageLoader.class.getClassLoader().getResource("imgs/"+imgName);
			return ImageIO.read(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public static void main(String[] args) {
//		System.out.println(load("background.png"));
//	}
}
