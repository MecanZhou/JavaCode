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
         g.drawImage(img, 0, 0, this);// 先把之前保存在内存的Image画上  	
        // g.setColor(Color.BLACK);
         logicalStructureManage.DoPaint(g);
   
	}
	

	public void update(Graphics g) {     
	         if (offScreenImage == null) {     
             // 截取窗体所在位置的图片     
             offScreenImage = this.createImage(WIDTH, HEIGHT);     
        }     
        // 获得截取图片的画布     
       Graphics gImage = offScreenImage.getGraphics();     
        // 获取画布的底色并且使用这种颜色填充画布（默认的颜色为黑色）     
            Color c = Color.BLACK;     
            gImage.setColor(c);     
            gImage.fillRect(0, 0, WIDTH, HEIGHT); // 有清除上一步图像的功能，相当于gImage.clearRect(0, 0, WIDTH, HEIGHT)     
         // 将截下的图片上的画布传给重绘函数，重绘函数只需要在截图的画布上绘制即可，不必在从底层绘制     
            paint(gImage);     
        //将接下来的图片加载到窗体画布上去，才能考到每次画的效果     
        g.drawImage(offScreenImage, 0, 0, null);     
	    }    

}
