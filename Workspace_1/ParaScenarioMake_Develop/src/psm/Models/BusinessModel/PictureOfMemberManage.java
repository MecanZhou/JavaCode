package psm.Models.BusinessModel;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.awt.AWTException;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import javax.swing.*;
import java.awt.*;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import com.mysql.jdbc.Util;
import psm.Models.DataModel.PictureOfMember;

public class PictureOfMemberManage {

	public ArrayList<PictureOfMember> pOMList = new ArrayList<PictureOfMember>();
	Robot r =null;
	

	/**
	 * 设置成员图片属性
	 */
	public void setpOMList(ArrayList<PictureOfMember> POMList_) {
		pOMList = POMList_;
	}
	
	public ArrayList<PictureOfMember> getpOMList(){
		return pOMList;
	}

	/**
	 * 获取端口
	 * @param pom
	 */
	public void GetPort(PictureOfMember pom) {
		Dimension s = new Dimension(6, 6);
		Point point4 = new Point(pom.imageLabel.getX()
				+ pom.imageLabel.getWidth() / 2 - 3, pom.imageLabel.getY() - 3);
		Point point5 = new Point(pom.imageLabel.getX()
				+ pom.imageLabel.getWidth() - 3, pom.imageLabel.getY()
				+ pom.imageLabel.getHeight() / 2 - 3);
		Point point6 = new Point(pom.imageLabel.getX()
				+ pom.imageLabel.getWidth() / 2 - 3, pom.imageLabel.getY()
				+ pom.imageLabel.getHeight() - 3);
		Point point7 = new Point(pom.imageLabel.getX() - 3,
				pom.imageLabel.getY() + pom.imageLabel.getHeight() / 2 - 3);
		pom.LogicalRect[4] = new Rectangle(point4, s);
		pom.LogicalRect[5] = new Rectangle(point5, s);
		pom.LogicalRect[6] = new Rectangle(point6, s);
		pom.LogicalRect[7] = new Rectangle(point7, s);
	}

	/**
	 * 绘制端口
	 * @param g
	 * @param pom
	 */
	public void DrawPort(Graphics g, PictureOfMember pom) {
		Dimension s = new Dimension(6, 6);
		Point point4 = new Point(pom.imageLabel.getX()
				+ pom.imageLabel.getWidth() / 2 - 3, pom.imageLabel.getY() - 3);
		Point point5 = new Point(pom.imageLabel.getX()
				+ pom.imageLabel.getWidth() - 3, pom.imageLabel.getY()
				+ pom.imageLabel.getHeight() / 2 - 3);
		Point point6 = new Point(pom.imageLabel.getX()
				+ pom.imageLabel.getWidth() / 2 - 3, pom.imageLabel.getY()
				+ pom.imageLabel.getHeight() - 3);
		Point point7 = new Point(pom.imageLabel.getX() - 3,
				pom.imageLabel.getY() + pom.imageLabel.getHeight() / 2 - 3);
		pom.LogicalRect[4] = new Rectangle(point4, s);
		pom.LogicalRect[5] = new Rectangle(point5, s);
		pom.LogicalRect[6] = new Rectangle(point6, s);
		pom.LogicalRect[7] = new Rectangle(point7, s);
		g.drawOval(point4.x, point4.y, s.height, s.width);
		g.drawOval(point5.x, point5.y, s.height, s.width);
		g.drawOval(point6.x, point6.y, s.height, s.width);
		g.drawOval(point7.x, point7.y, s.height, s.width);

	}

	/**
	 * 显示选中状态
	 * @param g 画选中点
	 * @param pom 选中的图片
	 */
	public void ShowSelected(Graphics g, PictureOfMember pom) {

		Dimension s = new Dimension(6, 6);
		Point point0 = new Point(pom.imageLabel.getLocation().x - s.width / 2,
				pom.imageLabel.getLocation().y - s.height / 2);
		pom.LogicalRect[0] = new Rectangle(point0, s);
		Point point1 = new Point(pom.imageLabel.getLocation().x
				+ pom.imageLabel.getWidth() - s.width / 2,
				pom.imageLabel.getLocation().y - s.height / 2);
		pom.LogicalRect[1] = new Rectangle(point1, s);
		Point point2 = new Point(
				(pom.imageLabel.getLocation().x + pom.imageLabel.getWidth())
						- s.width / 2,
				(pom.imageLabel.getLocation().y + pom.imageLabel.getHeight())
						- s.height / 2);
		pom.LogicalRect[2] = new Rectangle(point2, s);
		Point point3 = new Point(pom.imageLabel.getLocation().x - s.width / 2,
				(pom.imageLabel.getLocation().y + pom.imageLabel.getHeight())
						- s.height / 2);
		pom.LogicalRect[3] = new Rectangle(point3, s);

		g.fillRect(point0.x, point0.y, s.height, s.width);
		g.fillRect(point1.x, point1.y, s.height, s.width);
		g.fillRect(point2.x, point2.y, s.height, s.width);
		g.fillRect(point3.x, point3.y, s.height, s.width);
		g.drawRect(point0.x+5, point0.y+5,pom.imageLabel.getWidth()-5, pom.imageLabel.getHeight()-5);
	

	}
	
	public void Draw(Graphics g, PictureOfMember pom) {
		pom.LogicalRect = new Rectangle[8];
		ControlSize(pom);
		pom.imageLabel.setSize(new Dimension(pom.imageLabel.getWidth(),pom.imageLabel.getHeight()));
		pom.imageLabel.setIcon(LogicalStructureManage.getImageIcon("src\\psm\\Image\\事件发生器.png", pom.imageLabel.getWidth(), pom.imageLabel.getHeight()));
		if (pom.imageLabel != null) {
		}
		DrawText(g, pom);//输出模型名
		DrawPort(g, pom); // 画端口
		if (pom.logicalIsActive == true) {
			ShowSelected(g, pom);
		}
		DragAndDropDropTargetListener._jp_panel.repaint();

	}

	/**
	 * 控制图片的大小
	 * @param pom 选中的图片
	 */
	public void ControlSize(PictureOfMember pom) {
		if (pom.imageLabel.getWidth() < 40) {
			pom.imageLabel.setSize(40, pom.imageLabel.getHeight());
		}

		if (pom.imageLabel.getHeight() < 40) {
			pom.imageLabel.setSize(pom.imageLabel.getWidth(), 40);
		}

		if (pom.imageLabel.getWidth() > 200) {
			pom.imageLabel.setSize(200, pom.imageLabel.getHeight());
		}

		if (pom.imageLabel.getHeight() > 200)
			pom.imageLabel.setSize(pom.imageLabel.getWidth(), 200);
	}

	public void DrawText(Graphics g, PictureOfMember pom) {

	}
	

	public void Move(MouseEvent e, Point p, PictureOfMember pom,
			Dimension dimension, JPanel panel,int x ,int y ) {
		if (pom.SelectCursor == Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR))
		{		
			if (e.getX() < 0) {}
			else if (e.getY() < 0) {}
			else if (e.getX() > panel.getWidth()) {}
			else if (e.getY() > panel.getHeight()) {}
			else{
			pom.imageLabel.setLocation(e.getX()-x, e.getY()-y);
			panel.repaint();
			}
		}
		 else if (pom.SelectCursor == Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR))
         {
             if (pom.type == 2)
             {
                 pom.imageLabel.setSize(new Dimension(e.getX() - pom.imageLabel.getLocation().x, e.getY() - pom.imageLabel.getLocation().y));
             }
             else
             {
                 pom.imageLabel.setSize(new Dimension(pom.imageLabel.getLocation().x+ pom.imageLabel.getSize().width - e.getX(),
                		 pom.imageLabel.getLocation().y + pom.imageLabel.getSize().height - e.getY()));
                 pom.imageLabel.setLocation(e.getPoint());
             }
         }
         else if (pom.SelectCursor == Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR))
         {
             if (pom.type == 1)
             {
            	 Dimension dim = new Dimension(e.getX() - pom.imageLabel.getLocation().x, pom.imageLabel.getLocation().y + pom.imageLabel.getSize().height - e.getY());
                 pom.imageLabel.setSize(dim);
                 pom.imageLabel.setLocation(new Point(pom.imageLabel.getLocation().x, e.getY()));
             }
             else
             {
            	 Dimension dim = new Dimension(pom.imageLabel.getSize().width + pom.imageLabel.getLocation().x - e.getX(), e.getY() - pom.imageLabel.getLocation().y);

            	 pom.imageLabel.setSize(dim);
                 pom.imageLabel.setLocation(new Point(e.getX(), pom.imageLabel.getLocation().y));
             }
         }

         //记录上次移动的位置
         pom.oldX = p.x;
         pom.oldY = p.y;
		
	}

	/**
	 * 移动成员的时候设置鼠标样式
	 * @param panel
	 * @param pom
	 * @param e
	 * @return
	 */
	public Point ControlCursor(JPanel panel, PictureOfMember pom, MouseEvent e) {

		Point p = new Point(e.getX(), e.getY());
		
		return p;

	}


	/**
	 * 设置鼠标样式
	 * @param panel
	 * @param pom
	 * @param point
	 * @param startPoint
	 * @return
	 */
	public Point SetCursorOfLogical(JPanel panel, PictureOfMember pom,
			Point point, Point startPoint) {

		if (pom.LogicalRect[4].contains(point)
				|| pom.LogicalRect[5].contains(point)
				|| pom.LogicalRect[6].contains(point)) {
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}
		if (pom.LogicalRect[0].contains(point)|| pom.LogicalRect[2].contains(point)) // 左上角和右下角的端点选中了
		{
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
			pom.SelectCursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
			if (pom.LogicalRect[0].contains(point))
				pom.type = 0;
			else
				pom.type = 2;
		} else if (pom.LogicalRect[1].contains(point)|| pom.LogicalRect[3].contains(point)) // 右上角和左下角
		{
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
			pom.SelectCursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
			if (pom.LogicalRect[1].contains(point))
				pom.type = 1;
			else
				pom.type = 3;
		} else if (point.getX() > pom.LogicalRect[4].getLocation().getX() - 10
				&& point.getX() < pom.LogicalRect[4].getLocation().getX()
						+ pom.LogicalRect[4].getWidth() + 10
				&& point.getY() > pom.LogicalRect[4].getLocation().getY() - 10
				&& point.getY() < pom.LogicalRect[4].getLocation().getY()
						+ pom.LogicalRect[4].getHeight() / 2) {
			startPoint = pom.LogicalRect[4].getLocation();
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			pom.SelectCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		} else if (point.getX() > pom.LogicalRect[5].getLocation().getX()
				+ pom.LogicalRect[5].getWidth() / 2
				&& point.getX() < pom.LogicalRect[5].getLocation().getX()
						+ pom.LogicalRect[5].getWidth() + 10
				&& point.getY() > pom.LogicalRect[5].getLocation().getY() - 10
				&& point.getY() < pom.LogicalRect[5].getLocation().getY()
						+ pom.LogicalRect[5].getHeight() + 10) {
			startPoint = pom.LogicalRect[5].getLocation();
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			pom.SelectCursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		} else if (point.getX() > pom.LogicalRect[6].getLocation().getX() - 10
				&& point.getX() < pom.LogicalRect[6].getLocation().getX()
						+ pom.LogicalRect[6].getWidth() + 10
				&& point.getY() > pom.LogicalRect[6].getLocation().getY()
						+ pom.LogicalRect[6].getHeight() / 2
				&& point.getY() < pom.LogicalRect[6].getLocation().getY()
						+ pom.LogicalRect[6].getHeight() + 10) {
			startPoint = pom.LogicalRect[6].getLocation();
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
			pom.SelectCursor = Cursor
					.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		} else if (point.getX() > pom.imageLabel.getLocation().getX() + 10
				&& point.getX() < pom.imageLabel.getLocation().getX()
						+ pom.imageLabel.getWidth() - 10
				&& point.getY() > pom.imageLabel.getLocation().getY() + 10
				&& point.getY() < pom.imageLabel.getLocation().getY()
						+ pom.imageLabel.getHeight() - 10) {
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			pom.SelectCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
		} else {
			panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			pom.SelectCursor = Cursor
					.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
		}
		return startPoint;

	}

	// / 逻辑结构 判断是否能够自动粘附
	public Point IsLineLinkOfLogical(Point p, Point startPoint,
			ArrayList<PictureOfMember> pomList) {

		for (PictureOfMember pom : pomList) {
			int width = 10;
			int height = 10;

			if (p.getX() >= pom.LogicalRect[4].getLocation().getX() - width
					&& p.getX() <= pom.LogicalRect[4].getLocation().getX() + height
					&& p.getY() >= pom.LogicalRect[4].getLocation().getY() - width
					&& p.getY() <= pom.LogicalRect[4].getLocation().getY() + height) {
				if (startPoint != pom.LogicalRect[4].getLocation())
					return pom.LogicalRect[4].getLocation();
			}
			if (p.getX() >= pom.LogicalRect[6].getLocation().getX() - width
					&& p.getX() <= pom.LogicalRect[6].getLocation().getX() + height
					&& p.getY() >= pom.LogicalRect[6].getLocation().getY() - width
					&& p.getY() <= pom.LogicalRect[6].getLocation().getY() + height) {
				if (startPoint != pom.LogicalRect[6].getLocation())
					return pom.LogicalRect[6].getLocation();
			}
			if (p.getX() >= pom.LogicalRect[7].getLocation().getX() - width
					&& p.getX() <= pom.LogicalRect[7].getLocation().getX() + height
					&& p.getY() >= pom.LogicalRect[7].getLocation().getY() - width
					&& p.getY() <= pom.LogicalRect[7].getLocation().getY() + height) {
				if (startPoint != pom.LogicalRect[7].getLocation())
					return pom.LogicalRect[7].getLocation();
			}
		}
		return p;
		
	}

	
	/**
	 * 判断鼠标点击是否选中图形
	 * @param e
	 * @param panel
	 * @return
	 */
	public PictureOfMember SelectPom(MouseEvent e ,JPanel panel) {
		//Collections.reverse(pOMList);
		 //如果没有按下ctrl键则清空选中状态
		if (e.isControlDown()==false)
        {
            for (PictureOfMember pom : pOMList)
            {
                pom.logicalIsActive = false;
            }
        }
		for (PictureOfMember pom : pOMList) {
			if ((e.getX() >= pom.imageLabel.getLocation().x
					&& e.getX() <= pom.imageLabel.getLocation().x
							+ pom.imageLabel.getWidth()
					&& e.getY() >= pom.imageLabel.getLocation().y && e.getY() <= pom.imageLabel
							.getLocation().y + pom.imageLabel.getHeight())
					) {
				return pom;
			} 

		}
		//Collections.reverse(pOMList);
		return null;
	}

	public void SetIcon(JLabel imageLable, String nodeName) {

		URL url = ((URLClassLoader) Util.class.getClassLoader())
				.findResource("psm/Image/1.png");
		Image im = Toolkit.getDefaultToolkit().createImage(url);
		Icon labelicon = new ImageIcon("psm/Image/1.png");
		imageLable.setIcon(labelicon);
		// TODO Auto-generated method stub

	}

}
