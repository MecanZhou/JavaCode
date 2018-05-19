package psm.Models.BusinessModel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

public class Newpanel extends JPanel{	

	private LogicalStructureManage logicalStructureManage = new LogicalStructureManage();
	
	Image offScreenImage = null;
	private Image img ;
	
	public Newpanel() {
		setDoubleBuffered(true);
	}
		
	public void paint(Graphics g)
	{		
	     super.paint(g); 
         g.drawImage(img, 0, 0, this);// �Ȱ�֮ǰ�������ڴ��Image����  	
        // g.setColor(Color.BLACK);
         logicalStructureManage.DoPaint(g);
   
	}
	

	public void update(Graphics g) {     
	         if (offScreenImage == null) {     
             // ��ȡ��������λ�õ�ͼƬ     
             offScreenImage = this.createImage(WIDTH, HEIGHT);     
        }     
        // ��ý�ȡͼƬ�Ļ���     
       Graphics gImage = offScreenImage.getGraphics();     
        // ��ȡ�����ĵ�ɫ����ʹ��������ɫ��仭����Ĭ�ϵ���ɫΪ��ɫ��     
            Color c = Color.BLACK;     
            gImage.setColor(c);     
            gImage.fillRect(0, 0, WIDTH, HEIGHT); // �������һ��ͼ��Ĺ��ܣ��൱��gImage.clearRect(0, 0, WIDTH, HEIGHT)     
         // �����µ�ͼƬ�ϵĻ��������ػ溯�����ػ溯��ֻ��Ҫ�ڽ�ͼ�Ļ����ϻ��Ƽ��ɣ������ڴӵײ����     
            paint(gImage);     
        //����������ͼƬ���ص����廭����ȥ�����ܿ���ÿ�λ���Ч��     
        g.drawImage(offScreenImage, 0, 0, null);     
	    }    

}
