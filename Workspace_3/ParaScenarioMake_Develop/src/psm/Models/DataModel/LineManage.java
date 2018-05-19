package psm.Models.DataModel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.util.ArrayList;



import psm.Models.BusinessModel.DragAndDropDropTargetListener;
import psm.Models.BusinessModel.PictureOfMemberManage;

public class LineManage {
	ArrayList<Line> spaceLineList = new ArrayList<Line>();
    public ArrayList<Line> logicalLineList = new ArrayList<Line>();    
    
    public void drawAL(int sx, int sy, int ex, int ey, Graphics2D g) {
		double H = 10; // 箭头高度
		double L = 4; // 底边的一半
		int x3 = 0;
		int y3 = 0;
		int x4 = 0;
		int y4 = 0;
		double awrad = Math.atan(L / H); // 箭头角度
		double arraow_len = Math.sqrt(L * L + H * H); // 箭头的长度
		double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
		double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
		double x_3 = ex - arrXY_1[0]; // (x3,y3)是第一端点
		double y_3 = ey - arrXY_1[1];
		double x_4 = ex - arrXY_2[0]; // (x4,y4)是第二端点
		double y_4 = ey - arrXY_2[1];

		Double X3 = new Double(x_3);
		x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();
		// 画线平滑
		//g.setColor(color);
		
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g.setStroke(new BasicStroke(1.0f)); // 设置粗细
		Line2D lin = new Line2D.Float(sx, sy, ex, ey);
		g.draw(lin);
		
		
		
		GeneralPath triangle = new GeneralPath();
		triangle.moveTo(ex, ey);
		triangle.lineTo(x3, y3);
		triangle.lineTo(x4, y4);
		triangle.closePath();
		// 实心箭头
		((Graphics2D) g).fill(triangle);
//		System.out.println("Color:           "+color);
//		g.setColor(color);
		DragAndDropDropTargetListener._jp_panel.repaint();
		

	}
    
	// 计算
	public static double[] rotateVec(int px, int py, double ang,
			boolean isChLen, double newLen) {

		double mathstr[] = new double[2];
		// 矢量旋转函数，参数含义分别是x分量、y分量、旋转角、是否改变长度、新长度
		double vx = px * Math.cos(ang) - py * Math.sin(ang);
		double vy = px * Math.sin(ang) + py * Math.cos(ang);
		if (isChLen) {
			double d = Math.sqrt(vx * vx + vy * vy);
			vx = vx / d * newLen;
			vy = vy / d * newLen;
			mathstr[0] = vx;
			mathstr[1] = vy;
		}
		return mathstr;
	}
	
   	public void GetLinkNum(PictureOfMemberManage pomManage, Line line) {
		
		  for(PictureOfMember pom : pomManage.pOMList)
          {
              for (int i = 4; i < 8; i++)
              {
                  if (pom.LogicalRect[i].contains( line.StartPoint.getX()+2,line.StartPoint.getY()+2))
                  {
                      line.Linknum1 = i;
                  }
                  if (pom.LogicalRect[i].contains( line.EndPoint.getX()+2,line.EndPoint.getY()+2))
                  {
                      line.Linknum2 = i;
                  }
              }
          }
		// TODO Auto-generated method stub
		
	}

	public void LineMovePath(PictureOfMemberManage pomManage, Line line) {
		// TODO Auto-generated method stub
		
	}
	
	public void LineDrawArc(Graphics gra,Line line)
     {
     
     }
	
	public void Draw(Graphics g, ArrayList<Line> lineList)
      {
          for (int i=0;i<lineList.size();i++)
          {
              if (lineList.get(i).Point2 == null)                              //非折线
              {                 
            	  //     System.out.println("test:             第几条线："+i+lineList.get(i).lineColor+"sx"+lineList.get(i).StartPoint.x+"ex"+ lineList.get(i).EndPoint.x);           	 
            		  drawAL(lineList.get(i).StartPoint.x,lineList.get(i).StartPoint.y, 
                    		  lineList.get(i).EndPoint.x,lineList.get(i).EndPoint.y,(Graphics2D) g);  
            	
              }

              if (lineList.get(i).IsActived == true)
              {           	
                  ShowSelected(g, lineList.get(i));
              }
          }
     }
	 
	public void ShowSelected(Graphics g, Line line)
      {
		Dimension s = new Dimension(5, 5);
		  if (line.Point2 ==null)
          {
        	  int y1,y2;
        	  y1=(int) (line.StartPoint.getX() + (line.EndPoint.getX() - line.StartPoint.getX()) / 2);
        	  y2=(int) (line.StartPoint.getY() + (line.EndPoint.getY() - line.StartPoint.getY()) / 2);
        	 
              g.fillRect(y1,y2,s.height,s.width);
          }
          else
          {        	  
        	  int a,b,c,d,e,f,i,h;
        	  a=(int) (line.Point2.getX() - 2);
        	  b=(int)(line.Point2.getY() - 2);
        	  c=(int) (line.Point3.getX() - 2);
        	  d=(int) (line.Point3.getY() - 2);
        	  e=(int) (line.Point4.getX() - 2);
        	  f=(int) (line.Point4.getY() - 2);
        	  i=(int) (line.Point5.getX() - 2);
        	  h=(int) (line.Point5.getY() - 2);

              g.fillRect(a,b,5,5);
              g.fillRect(c,d,5,5);
              g.fillRect(e,f,5,5);
              g.fillRect(i,h,5,5);
          }

      }

	  public void lineMove(Line line, PictureOfMember pom)
      {
		  if (line.Linknum1 != -1 || line.Linknum2 != -1)
          {
              if (line.Linknum1 != -1)
              {
                  line.StartPoint = pom.LogicalRect[line.Linknum1].getLocation();
                  if (line.Point2 !=null)
                  {
                	  Point p0= new Point((int)line.StartPoint.getX() + 5, (int)line.StartPoint.getY());
                      line.Point2 = p0;
                      if (line.StartPoint.getX() < line.EndPoint.getX())
                      {
                    	  Point p1 = new Point((int)line.Point4.getX(), (int)line.StartPoint.getY());
                          line.Point3 = p1;
                      }
                      if (line.StartPoint.getX() > line.EndPoint.getX())
                      {
                    	  Point p2 = new Point((int)line.Point2.getX(),(int)line.Point4.getY());
                          line.Point3 = p2;
                      }
                  }
              }
              if (line.Linknum2 != -1)
              {
                  line.EndPoint = pom.LogicalRect[line.Linknum2].getLocation();
                  if (line.Point2 !=null)
                  {
                	  Point p3 = new Point((int)line.EndPoint.getX() - 5, (int)line.EndPoint.getY());
                      line.Point5 = p3;
                      if (line.StartPoint.getX() < line.EndPoint.getX())
                      {
                    	 
                          line.Point4 = new Point((int)line.Point3.getX(), (int)line.EndPoint.getY());
                      }
                      if (line.StartPoint.getX() > line.EndPoint.getX())
                      {
                          line.Point4 = new Point((int)line.Point5.getX(), (int)line.Point3.getY());
                      }
                  }
              }
          }

       
      }

	  
	  public double distince(double x1,double y1, double x2, double y2)
      {
			
		   double distance = 0;
	       distance=Math.hypot((x1 - x2),(y1 - y2));       
          return distance;
      }


	  public void MoveLine(Line line, MouseEvent e)
      {
		  if(line.MoveType==1)
          {
              line.Point2=new Point((int)line.Point2.getX(),(int)e.getY());
              line.Point3=new Point((int)line.Point2.getX(),(int)e.getY());
              line.Point4=new Point((int)line.Point4.getX(),(int)e.getY());
              line.Point5=new Point((int)line.Point4.getX(),(int)e.getY());
          }
          if(line.MoveType==4)
          {
              line.Point2=new Point((int)line.Point2.getX(),(int)e.getY());
              line.Point3=new Point((int)line.Point3.getX(),(int)e.getY());
          }
          if(line.MoveType==6)
          {
              line.Point3=new Point((int)line.Point3.getX(),(int)e.getY());
              line.Point4=new Point((int)line.Point4.getX(),(int)e.getY());
          }
          if (line.MoveType==8)
          {
              line.Point4 = new Point((int)line.Point4.getX(), (int)e.getY());
              line.Point5 = new Point((int)line.Point5.getX(), (int)e.getY());
           }
          if (line.MoveType == 10)
          {
              line.Point3 = new Point((int)line.Point3.getX(), (int)e.getY());
              line.Point5 = new Point((int)line.Point4.getX(),(int) e.getY());
              line.Point4 = new Point((int)line.Point4.getX(), (int)e.getY());
          }
  
      }


	  public void MoveLine2(Line line, MouseEvent e)
      {
		  if(line.MoveType==2)
          {
              line.Point2=new Point(e.getX(),(int)line.Point2.getY());
              line.Point3=new Point(e.getX(),(int)line.Point2.getY());
              line.Point4=new Point(e.getX(),(int)line.Point5.getY());
              line.Point5=new Point(e.getX(),(int)line.Point5.getY());
          }
              if(line.MoveType==3)
              {
                  line.Point2=new Point(e.getX(),(int)line.Point2.getY());
                  line.Point3=new Point(e.getX(),(int)line.Point3.getY());
              }
                  if(line.MoveType==5)
                  {
                      line.Point3=new Point(e.getX(),(int)line.Point3.getY());
                      line.Point4=new Point(e.getX(),(int)line.Point4.getY());
                  }
                  if (line.MoveType==7)
                  {
                      line.Point4 = new Point(e.getX(),(int) line.Point4.getY());
                      line.Point5 = new Point(e.getX(), (int)line.Point5.getY());
                  }
                  if (line.MoveType == 9)
                  {
                      line.Point3 = new Point((int)e.getX(), (int)line.Point3.getY());
                      line.Point5 = new Point((int)e.getX(), (int)line.Point4.getY());
                      line.Point4= new Point((int)e.getX(), (int)line.Point4.getY());
                  }
      }

	  /**
	   * 在鼠标按下时，判断是否选中了线
	   * @param e 鼠标事件数据提供类
	   * @param lineList 线集合
	   * @param g 绘制选中的状态
	   * @return 选中的线
	   */
	  public Line SelectLine(MouseEvent e, ArrayList<Line> lineList,Graphics g)
      { 		
		Point p = new Point(e.getX(), e.getY());
		double a = p.getX();
		double b = p.getY();
		
		if (e.isControlDown()==false)
        {
            for (Line line : lineList)
            {
            	line.IsActived = false;
            }
        }

		for (Line line : lineList) {
			double a1 = line.StartPoint.getX();
			double b1 = line.StartPoint.getY();
			double a2 = line.EndPoint.getX();
			double b2 = line.EndPoint.getY();

			double ave1 = 0.5 * (a2 - a1);
			double ave2 = 0.5 * (b2 - b1);

			if (line.Point3 == null) {
				if ((distince(a, b, a1, b1) + distince(a, b, a2, b2) < (distince(
						a1, b1, a2, b2) + 0.05))) {
					line.IsActived = true;
					if (line.IsActived) {
						g.fillRect((int) (a1 + ave1), (int) (b1 + ave2), 5, 5);
						return line;
					}
				} else {
					line.IsActived = false;
				}
			}
			else {
				double a11 = line.Point3.getX();
				double b11 = line.Point3.getY();
				double a22 = line.Point4.getX();
				double b22 = line.Point4.getY();

				if ((distince(a11, b11, a, b) + distince(a, b, a22, b22) < (distince(
						a11, b11, a22, b22) + 0.05))) {
					line.IsActived = true;
					if (line.IsActived) {
						g.fillRect(line.Point3.x - 2, line.Point3.y - 2, 5, 5);
						g.fillRect(line.Point4.x - 2, line.Point4.y - 2, 5, 5);
						return line;
					}

				}

				else {
					line.IsActived = false;
				}

			}
		}

		return null;

      }


	  public void SelectLinePath(Line line, PictureOfMemberManage pomManage)                        //line 不为空
      {
		  
      }

	  public Dimension GetPomSize(PictureOfMemberManage pomManage, Point point) 
      {
          for(PictureOfMember pom:pomManage.pOMList)
          {
              for (int i = 4; i < 8; i++)
              {
                  if (point == pom.LogicalRect[i].getLocation())
                      return pom.imageLabel.getSize();
              }
          }
          return new Dimension(0, 0);
      }
	  
}
