package psm.Models.BusinessModel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import com.mysql.jdbc.Util;
import psm.Controls.ManageContainer;
import psm.Models.DataModel.CombineMember;
import psm.Models.DataModel.Line;
import psm.Models.DataModel.LineManage;
import psm.Models.DataModel.Member;
import psm.Models.DataModel.MemberInteraction;
import psm.Models.DataModel.MemberObject;
import psm.Models.DataModel.Model;
import psm.Models.DataModel.PictureOfMember;
import psm.Models.DataModel.ModelClass.Model_para_info;
import psm.Views.FrmDeployModel;
import psm.Views.FrmParaChoose;

public class LogicalStructureManage {
	private PictureOfMember selectPom = null;
	public static PictureOfMember temPOM = null;// 用于传值给FrmDeployManage，确定选中的图片
	public static Line temLine = null;// 用于传值给FrmParaChoose，确定选中的直线
	FrmDeployModel frmDeployMdoel;
	static LineManage lineManage = new LineManage(); // 操作直线的对象
	private static Line selectLine;
	private boolean isDrawLine;
	public static PictureOfMemberManage pomManage = new PictureOfMemberManage();
	private boolean isPress = false;
	public static boolean isDrawSquare;//是否可以画线
	private Rectangle square;
	static Point point1 = new Point(0, 0);
	static Point point2 = new Point(0, 0);
	
	private boolean isSaveLine;
	private MemberManage memberManage = new MemberManage();// 成员管理对象
	private RelationManage relationManage = new RelationManage();// 关系管理对象
	PropertyManage propretyChange = new PropertyManage();
	private boolean isDragOverLine = false;
	private Point p11;
	private Point p22;
	public static boolean flag_tempLine;
	
	private int xL;
	private int yL;
	
	Color color;

	@SuppressWarnings("unused")
	private FormulaManage formulaManage;

	public LogicalStructureManage() {
		formulaManage = (FormulaManage) ManageContainer
				.GetObject("FormulaManage");		
	}
	
	public static ImageIcon getImageIcon(String path, int width, int height) {
//		  if (width == 0 || height == 0) {
//		   return new ImageIcon(this.getClass().getResource(path));
//		  }
		  ImageIcon icon = new ImageIcon(path);
		  icon.setImage(icon.getImage().getScaledInstance(width, height,
		    Image.SCALE_DEFAULT));
		  return icon;
		 }
	

	/**
	 * 对树节点执行拖拽，释放等到所需要的成员
	 * @param nodeName 树节点的名称
	 * @param g
	 * @param point 鼠标点
	 * @param panel 逻辑界面
	 * @return 对应的成员
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public Object DoDragDorp(String nodeName, Graphics g, Point point,
			JPanel panel) throws ClassNotFoundException, IOException {

		for (Model model : ModelManage.ModelList) {
			if ((model.Model_name + model.Muid).equals(nodeName)) {
				String pomName = null;		
				Member member = memberManage.CreateMember(ModelManage.Clone(model));
				member.pictureOfMember.imageLabel.setSize(new Dimension(70, 70));
				member.pictureOfMember.Name = member.Name;
				String[] temp= member.pictureOfMember.Name.split("-");
				String unName=temp[1];
				
				ImageIcon iconI=getImageIcon("src\\psm\\Image\\1.png",member.pictureOfMember.imageLabel.getSize().width,
						member.pictureOfMember.imageLabel.getSize().height);				
				member.pictureOfMember.imageLabel.setIcon(iconI);
								
				member.pictureOfMember.imageLabel.setBounds(point.x, point.y,70, 70);				
				member.pictureOfMember.Id = member.Id;

				if (pomManage.pOMList.size() > 0) {
					for (int i = 0; i < pomManage.pOMList.size(); i++) {
						// 对成员名进行拆分
						String[] pomTemp = pomManage.pOMList.get(i).Name
								.split("-");
						pomName = pomTemp[1];
					}
					// 如果已存在一个FedrationTimer
					if (pomName.equals(unName)
							&& pomName.equals("FedrationTimer")) {
						JOptionPane.showMessageDialog(new JPanel(),"一个预案只能有一个定时器", "提示",
								JOptionPane.YES_NO_CANCEL_OPTION);
					} else {
						pomManage.pOMList.add(member.pictureOfMember);
						panel.add(member.pictureOfMember.imageLabel);
						panel.repaint();
					}
				} else {
					pomManage.pOMList.add(member.pictureOfMember);
					panel.add(member.pictureOfMember.imageLabel);
					panel.repaint();
				}
			}

		}
		
		for(int i=0;i<CombineMemberManage.getCombineMemberList().size();i++){
			if(CombineMemberManage.getCombineMemberList().get(i).Name.equals(nodeName)){
				 CombineMember combineMemberTemp = CombineMemberManage.Clone(CombineMemberManage.getCombineMemberList().get(i));
				 memberManage.CreateMember(combineMemberTemp);
                 //在释放点画图
				 combineMemberTemp.pictureOfMember.imageLabel.setSize(new Dimension(70, 70));
				 combineMemberTemp.pictureOfMember.imageLabel = new JLabel();
				 combineMemberTemp.pictureOfMember.imageLabel.setBounds(point.x, point.y,
							70, 70);
                 //设置成员
                 combineMemberTemp.pictureOfMember.imageLabel.setIcon(new ImageIcon(
 						"src\\psm\\Image\\Control.png"));
                 combineMemberTemp.pictureOfMember.Name = combineMemberTemp.Name;
                 pomManage.pOMList.add(combineMemberTemp.pictureOfMember);
                                  
                panel.add(combineMemberTemp.pictureOfMember.imageLabel);
 				panel.repaint();
			}
		}

		return 1;
	}

	/**
	 * 鼠标移动的时候要自动变化鼠标样式
	 * @param g 
	 * @param e 鼠标点
	 * @param panel 面板
	 * @return boolean值
	 */
	public boolean MouseMove(Graphics g, MouseEvent e, JPanel panel) {

		if (isPress) {
			SetCursor(e, panel);
		}
		return false;
	}

	/**
	 * 获取选中的图片
	 * @param g
	 * @param e
	 * @param panel
	 * @return
	 */
	public PictureOfMember SelectPom(Graphics g, MouseEvent e, JPanel panel) {

		selectPom = pomManage.SelectPom(e, panel);

		temPOM = selectPom;
		String nodeName = null;
		if (selectPom != null) {
			nodeName = selectPom.Name;
			propretyChange.tableMemberChange(e, nodeName);// 当点击成员名的时候，属性栏发生变换

			selectPom.logicalIsActive = true;
			selectPom.oldX = e.getX();
			selectPom.oldY = e.getY();
			
			 new Point(selectPom.LogicalRect[4].getLocation().x-selectPom.imageLabel.getWidth()/2,selectPom.LogicalRect[4].getLocation().y);
	           e.getPoint();
	           xL = e.getX()-selectPom.LogicalRect[4].getLocation().x+selectPom.imageLabel.getWidth()/2;
	           yL = e.getY()-selectPom.LogicalRect[4].getLocation().y;

			ReDrawPom(g);
			panel.repaint();
			Point p22 = new Point((int) e.getX(), (int) e.getY()); // 获取鼠标按下时的坐标
			pomManage.SetCursorOfLogical(panel, selectPom, p22, point1);
			isPress = true;
			// 选中模型的时候判断是线的起点还是终点
			for (Line line : lineManage.logicalLineList) {
				//line.lineColor=Color.BLACK;
				for (int i = 4; i < 8; i++) {
					if (selectPom.LogicalRect[i].contains(line.StartPoint.getX()+2,line.StartPoint.getY()+2)) {
						line.Linknum1 = i;
						line.PointAName = selectPom.Name;
						//line.lineColor=Color.BLUE;
					}
					else if (selectPom.LogicalRect[i].contains(line.EndPoint.getX()+2,line.EndPoint.getY()+2)) {
						line.Linknum2 = i;
						line.PointBName = selectPom.Name;
						//line.lineColor=Color.BLUE;
					}
					
				}
			}
		}
//		else{
//			for(Line line : lineManage.logicalLineList){
//				line.lineColor=Color.black;
//			}
//		}
		return selectPom;
	}

	/**
	 * 判断线是否被选中，改变其属性
	 * @param e
	 * @param g
	 * @return
	 */
	private Line SelectLine(MouseEvent e, Graphics g) {
		selectLine = lineManage.SelectLine(e, lineManage.logicalLineList, g);
		temLine=selectLine;
		if (selectLine != null) {
			selectLine.IsActived=true;
		}
		return selectLine;

	}

	public double distince(double x1, double y1, double x2, double y2) {

		double distance = 0;
		distance = Math.hypot((x1 - x2), (y1 - y2));
		return distance;
	}

	/**
	 * 该方法是检验输入成员和输出成员的type是否匹配，只有匹配的才能进行连线
	 */
	private void CheckLineShow() {

		int count=0;
		List<Member> tempMemberS = new ArrayList<Member>();                     //用来临时存储输入成员
		List<Member> tempMemberE = new ArrayList<Member>();                     //用来临时存储输出成员

		for (int i = 0; i < lineManage.logicalLineList.size(); i++){	//遍历界面上的线，再通过线的两端成员判断出输入成员和输出成员		
			for (int m = 4; m < 8; m++) {
				for (PictureOfMember pom : pomManage.pOMList){
					if (pom.LogicalRect[m].contains(
							lineManage.logicalLineList.get(i).StartPoint.getX()+2,lineManage.logicalLineList.get(i).StartPoint.getY()+2)){
						for(Member member :memberManage.getMemberList()){
							if(member.Name.equals(pom.Name)){
								tempMemberS.add(member);
								
							}
						}
					}
					if(pom.LogicalRect[m].contains(
							lineManage.logicalLineList.get(i).EndPoint.getX()+2,lineManage.logicalLineList.get(i).EndPoint.getY()+2)){
						for(Member member :memberManage.getMemberList()){
							if(member.Name.equals(pom.Name)){
								tempMemberE.add(member);								
							}
						}
					}
				
				}
			}
		}
		
		try{
		if(tempMemberS.size()>0&&tempMemberE.size()>0){
		//遍历输入成员的type属性是否包含input_para,输出成员的属性是否包含out_putpara
			
			for (int j = 0; j < tempMemberS.size(); j++) {
				for (int k = 0; k < tempMemberS.get(j).Model.Model_para_infoList.size(); k++) {
					for (int m = 0; m < tempMemberE.get(j).Model.Model_para_infoList.size(); m++) {
						if (String.valueOf(tempMemberS.get(j).Model.Model_para_infoList.get(k).Para_type).contains("output_para")
								&& String.valueOf(tempMemberE.get(j).Model.Model_para_infoList.get(m).Para_type).contains("input_para")) {
							        count++;									
						}
					}
				}
			
			if (count == 0) {
				for (int a = 4; a < 8; a++) {
					for (int b = 4; b < 8; b++) {
						for (int i = 0; i < lineManage.logicalLineList.size(); i++) {
							if (tempMemberS.get(j).pictureOfMember.LogicalRect[a].getLocation().equals(lineManage.logicalLineList.get(i).StartPoint)
									&& tempMemberE.get(j).pictureOfMember.LogicalRect[b].getLocation().equals(lineManage.logicalLineList.get(i).EndPoint)) {
								lineManage.logicalLineList.remove(lineManage.logicalLineList.get(i));
							}
						}

					}
				}									
			}
			else{
				count=0;
			}
		}	
		
			
			}
		}finally{			
	
		}
	}

	/**
	 * 判断是否画线
	 * @param panel
	 * @return
	 */
	private Boolean IsDrawLine(JPanel panel) {
		if (panel.getCursor() == Cursor
				.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)) {
			isDrawLine = true;
			return true;
		}
		return false;
	}

	/**
	 * 鼠标每点击一次，获取鼠标坐标点
	 * @param e
	 * @return 鼠标坐标点
	 */
	public Point getPoint(MouseEvent e) {
		Point p = new Point((int) e.getX(), (int) e.getY());
		return p;
	}

	/**
	 * 鼠标按下实现方法
	 * @param g
	 * @param e
	 * @param panel
	 */
	public void DoMousePress(Graphics g, MouseEvent e, JPanel panel) {
		SelectPom(g, e, panel);
		SelectLine(e, g);
		boolean b = IsDrawLine(panel);
		if(b==false){
			IsDrawSquare(e,panel);
		}

	}
	/**
	 * 鼠标移动要实现的方法
	 * @param g
	 * @param e
	 * @param panel
	 * @return
	 */
	public boolean DoMouseMove(Graphics g, MouseEvent e, JPanel panel) {
			boolean b = MovePom(e, g, panel);
			if (b == true) {
				return true;
			}
			if (panel.getCursor() == (Cursor
					.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR))) {
				drawline(e, g, panel);
				LineLink(g, e, panel);
			}
			if (panel.getCursor() == (Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR))) {
			DrawSquare(e, panel);  //框选
			}
		return false;
	}

	/**
	 * 鼠标抬起要实现的方法
	 * @param g
	 * @param panel
	 * @param e
	 */
	public void DoMouseUp(Graphics g, JPanel panel, MouseEvent e) {
			
		panel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		flag_tempLine=false;
		isDrawSquare = false;
		CheckLineShow();
		
		if (point2.x != 0 && point2.y != 0 && isSaveLine) {
			Line line = new Line(point1);
			line.EndPoint = point2;
			lineManage.logicalLineList.add(line);
			isDragOverLine = true;
		}
		
		// 鼠标抬起后重新赋值
		isDrawLine = false;
		
		square=new Rectangle(point1.x,point1.y,point2.x - point1.x,point2.y- point1.y);//框选出一个区域，来进行查找
		point1 = new Point(0, 0);
		point2 = new Point(0, 0);
		isSaveLine = false;
		SelectCombineMember();                                        //框选
		
		drawAllList(g);
		panel.repaint();
	}
	
	/**
	 * 成员之间画连接线
	 * @param e 鼠标点
	 * @param g 绘制线
	 * @param panel 当前面板
	 */
	public void drawline(MouseEvent e, Graphics g, JPanel panel) {
		if (isDrawLine == true) {
			point2 = new Point(e.getX(), e.getY());
			
			flag_tempLine = true;
			drawAL(point1.x, point1.y, point2.x, point2.y, g,color);
			isSaveLine = false;
			
			panel.repaint();
		}
		panel.repaint();
	}

	/**
	 * 设置鼠标样式
	 * @param e 鼠标点
	 * @param panel 当前面板
	 */
	private void SetCursor(MouseEvent e, JPanel panel) {
		if (selectPom != null) {
			Point pointMovePoint = new Point(e.getX(), e.getY());
			pomManage.SetCursorOfLogical(panel, selectPom, pointMovePoint,
					point1);

			point1 = pomManage.SetCursorOfLogical(panel, selectPom,
					pointMovePoint, point1);
		} else {
			@SuppressWarnings("unused")
			Point pointMovePoint = new Point(e.getX(), e.getY());

		}
	}

	/**
	 * 图片移动的时候执行的方法
	 * @param e
	 * @param g
	 * @param panel
	 * @return
	 */
	private boolean MovePom(MouseEvent e, Graphics g, JPanel panel) {
		if (selectPom != null) {
		
			flag_tempLine = false;
			Point p = pomManage.ControlCursor(panel, selectPom, e); // 鼠标拖拽过程如果出了边界，则设置一个坐标为0，不能为负数
			Dimension dimension = new Dimension((int) panel.getWidth(),(int) panel.getHeight());
			pomManage.Move(e, p, selectPom, dimension, panel,xL,yL);
			if (isDragOverLine) {
				for (Line line : lineManage.logicalLineList) { // 拖动之前要点击好两张图片
																// 线的终点 除去5
					if (line.PointBName == selectPom.Name && line.Linknum2 == 7) {
						line.EndPoint = selectPom.LogicalRect[7].getLocation();
						DrawTempVLine(g); // 画折线和直线 //默认情况
						panel.repaint();						
					}
					if (line.PointBName == selectPom.Name && line.Linknum2 == 4) {						
						line.EndPoint = selectPom.LogicalRect[4].getLocation();
						DrawTempVLine(g);
						panel.repaint();						
					}
					if (line.PointBName == selectPom.Name && line.Linknum2 == 6) {						
						line.EndPoint = selectPom.LogicalRect[6].getLocation();
						DrawTempVLine(g);
						panel.repaint();						
					}

					// 线的起点 ；除去7
					if (line.PointAName == selectPom.Name && line.Linknum1 == 5) {
						line.StartPoint = selectPom.LogicalRect[5]
								.getLocation();

						DrawTempVLine(g);
						panel.repaint();						
					}
					if (line.PointAName == selectPom.Name && line.Linknum1 == 6) {
						line.StartPoint = selectPom.LogicalRect[6]
								.getLocation();

						DrawTempVLine(g);
						panel.repaint();						
					}
					if (line.PointAName == selectPom.Name && line.Linknum1 == 4) {
						line.StartPoint = selectPom.LogicalRect[4]
								.getLocation();
						DrawTempVLine(g);
						panel.repaint(); 						
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * 画临时折线
	 * @param g
	 * @param line
	 */
	public void DrawVLine(Graphics g, Line line) { 
		g.drawLine(line.StartPoint.x, line.StartPoint.y, line.Point2.x,line.Point2.y);
		g.drawLine(line.Point2.x, line.Point2.y, line.Point3.x, line.Point3.y);
		g.drawLine(line.Point3.x, line.Point3.y, line.Point4.x, line.Point4.y);
		g.drawLine(line.Point4.x, line.Point4.y, line.Point5.x, line.Point5.y);
		drawAL(line.Point5.x, line.Point5.y, line.EndPoint.x, line.EndPoint.y,
				g,line.lineColor);

	}
 
	/**
	 * 线的连接
	 * @param g
	 * @param e
	 * @param panel
	 */
	private void LineLink(Graphics g, MouseEvent e, JPanel panel) {
		Point p111 = new Point(e.getX(), e.getY());
		Point p = pomManage.IsLineLinkOfLogical(p111, point1, pomManage.pOMList);

		if (p != p111
				&& panel.getCursor() == Cursor
						.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR)) {
			point2 = p;
			drawAllList(g);
			isSaveLine = true;
		}
	}

	/**
	 * 画带箭头的直线方法
	 * @param sx 开始点x坐标
	 * @param sy 开始点y坐标
	 * @param ex 结束点x坐标
	 * @param ey 结束点y坐标
	 * @param g
	 */
	public void drawAL(int sx, int sy, int ex, int ey, Graphics g,Color c) {

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
		// 画线画平滑的线
		Line2D lin = new Line2D.Float(sx, sy, ex, ey);
		((Graphics2D) g).draw(lin);
	
		GeneralPath triangle = new GeneralPath();
		triangle.moveTo(ex, ey);
		triangle.lineTo(x3, y3);
		triangle.lineTo(x4, y4);
		triangle.closePath();
		// 实心箭头
		((Graphics2D) g).fill(triangle);
		
		g.setColor(c);

	}

	/**
	 * 计算
	 * @param px x分量
	 * @param py y分量
	 * @param ang旋转角
	 * @param isChLen是否改变长度
	 * @param newLen新的长度
	 * @return
	 */
	public double[] rotateVec(int px, int py, double ang, boolean isChLen,
			double newLen) {
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

	public void drawAllList(Graphics g) {
		Line line = new Line();
		for (int i = 0; i < lineManage.logicalLineList.size(); i++) {
			line = (Line) lineManage.logicalLineList.get(i);
			// drawAL(line.StartPoint.x, line.StartPoint.y, line.EndPoint.x,
			// line.EndPoint.y, g);
		}
	}

	/**
	 * 多选，选择框选中的成员
	 */
	private void SelectCombineMember() {
		for (PictureOfMember pom : pomManage.pOMList) {				
			if (square.contains(new Rectangle(pom.imageLabel.getLocation(),
					pom.imageLabel.getSize()))) {
				pom.logicalIsActive = true;
				pom.imageLabel.setFocusable(false);
			}
		}
		for (Line line : lineManage.logicalLineList) {
			if (square.contains(line.StartPoint)
					&& square.contains(line.EndPoint))
				line.IsActived = true;
		}

	}

	/**
	 * 重绘图片上的端口
	 * @param g
	 */
	private void ReDrawPom(Graphics g) {
		for (PictureOfMember pom : pomManage.pOMList) {
			pomManage.Draw(g, pom);
		}
	}


	/**
	 * 重绘直线
	 * @param g
	 */
	private void ReDrawLine(Graphics g) {
		lineManage.Draw(g, lineManage.logicalLineList);
	}

	/**
	 * 设置成员所对应的图片
	 * @param member
	 * @throws IOException
	 */
	static void SetImage(Member member) throws IOException {

		URL url = ((URLClassLoader) Util.class.getClassLoader())
				.findResource("psm/Image/1.png");
		ImageIcon icon = new ImageIcon(url);

		member.pictureOfMember.imageLabel = new JLabel(icon); // 写之前一定要实例化

		member.pictureOfMember.imageLabel.setIcon(new ImageIcon(ImageIO
				.read(new File("C:\\model.jpg"))));

	}

	// [start] OtherFunction
	/**
	 * 右键菜单的选项
	 * @param e
	 * @param g
	 * @param panel
	 */
	public void DoShowRightMenu(MouseEvent e,  final Graphics g,
			final JPanel panel) {
		JPopupMenu pop = new JPopupMenu();
		if (selectLine != null) {
			if (e.isPopupTrigger()) {
				if (selectLine.Point2 != null) {
					JMenuItem DelObjitem = new JMenuItem("取消对象");
					DelObjitem.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							DelObjClass(e, g, panel);
						}
					});
					pop.add(DelObjitem);
					JMenuItem DelInteritem = new JMenuItem("取消交互");
					DelInteritem.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							DelInterClass();
						}
					});
					pop.add(DelInteritem);
					JMenuItem DelLine = new JMenuItem("删除");
					DelLine.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							Delete(MemberManage.MemberList, pomManage.pOMList,
									lineManage.logicalLineList, panel);						
							panel.repaint();
						}
					});
					pop.add(DelLine);
				}
				else {
					JMenuItem OrderObjitem = new JMenuItem("订购对象");
					OrderObjitem.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
//							FrmParaChoose windows=new FrmParaChoose();
//						    windows.frame.setVisible(true);					    
							OrderObjClass(e, g, panel);
						}
					});
					pop.add(OrderObjitem);
					JMenuItem OrderInteritem = new JMenuItem("订购交互");
					OrderInteritem.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							OrderInterClass();
						}
					});
					pop.add(OrderInteritem);
					JMenuItem DelLine = new JMenuItem("删除");
					DelLine.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							Delete(MemberManage.MemberList, pomManage.pOMList,
									lineManage.logicalLineList, panel);						
							panel.repaint();
						}
					});
					pop.add(DelLine);
					
//					JMenuItem showColor = new JMenuItem("修改颜色");
//					showColor.addMouseListener(new MouseAdapter() {
//						public void mouseReleased(MouseEvent e) {
//							color=JColorChooser.showDialog(null, "选择线的颜色！", Color.BLUE);
//							selectLine.lineColor=color;
//							}
//					});
//					pop.add(showColor);
				}
				pop.show(e.getComponent(), e.getX(), e.getY());
			}
		} 
		else if (selectPom != null) {
			if (e.isPopupTrigger()) {
				JMenuItem memberitem = new JMenuItem("设置成员");
				memberitem.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						frmDeployMdoel = new FrmDeployModel();
						frmDeployMdoel.frame.setVisible(true);
					}

				});
				pop.add(memberitem);
				JMenuItem Deleteitem = new JMenuItem("删除");
				Deleteitem.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						Delete(MemberManage.MemberList, pomManage.pOMList,
								lineManage.logicalLineList, panel);						
						panel.repaint();
					}
				});
				pop.add(Deleteitem);
				pop.show(e.getComponent(), e.getX(), e.getY());
			}
		}

	}

	/**
	 * 执行右击菜单中的删除方法
	 * @param memberList 成员列表
	 * @param pOMList 成员图片的列表
	 * @param lineList 线的列表
	 * @param panel
	 */
	private void Delete(List<Member> memberList, ArrayList<PictureOfMember> pOMList,
			ArrayList<Line> lineList, JPanel panel) {
		
		int count=0;//因为线定义的是静态变量，所以每次删线的时候，会更新线的数量，导致循环遍历出错，所以在这里要手动添加一个虚的大小
		
		//删除绘制的线
		for(int i=0;i<lineManage.logicalLineList.size()+count;i++){
			if(lineManage.logicalLineList.get(i-count).IsActived==true){
				//如果该线是还没有订购
                if (lineManage.logicalLineList.get(i-count).Point2 == null){
                    lineManage.logicalLineList.remove(i-count);
                }
                else//如果该线订购了则删除对应的关系
                {
                    for (int j = 0; j < relationManage.MemberObjecList.size();j++ ){
                        if (relationManage.MemberObjecList.get(j).logicalLine == lineManage.logicalLineList.get(i)){
                            lineManage.logicalLineList.remove(i-count);
                            relationManage.MemberObjecList.remove(j);
                            count++;
                        }
                    }
                }
			}
		}
		
		//删除成员的时候，如果成员有线的关联，则将线也一起删除掉
		for(int k=0;k<pOMList.size();k++){
			PictureOfMember pOM=pomManage.pOMList.get(k);
			if(pOM.logicalIsActive){
				List<Line> tempLineList=new ArrayList<Line>();
				for(int m=4;m<8;m++){
					for(Line line:lineList){
						//坐标点+2是解决getlocation获取的点是int,getx()是double四舍五入产生的偏差
						if(pOM.LogicalRect[m].contains(line.StartPoint.getX()+2,line.StartPoint.getY()+2)){
							tempLineList.add(line);
						}
						if(pOM.LogicalRect[m].contains(line.EndPoint.getX()+2,line.EndPoint.getY()+2)){
							tempLineList.add(line);
						}						
					}					
				}
				for(int i=0;i<tempLineList.size();i++){
					//如果该线还没有被订购
					if(tempLineList.get(i).Point2==null){
						for(int m=0;m<lineList.size();m++){
							if(lineList.get(m).EndPoint.equals(tempLineList.get(i).EndPoint)){
								lineList.remove(m);
							}
						}
						
					}
					//如果订购了删除对应的关系
					else {
						for (int m = 0; m < lineList.size(); m++) {
							if (lineList.get(m).EndPoint.equals(tempLineList.get(i).EndPoint)) {
								for (int j = 0; j < relationManage.MemberObjecList
										.size(); j++) {
									if (relationManage.MemberObjecList.get(j).logicalLine == tempLineList.get(i)) {
										lineList.remove(m);
										relationManage.MemberObjecList.remove(j);
									}
								}
							}
						}
					}
				}
			}
		}
		
		// 删除成员本身(此处要警惕for循环里的陷阱如果设置计数器最大值为变量，在循环体内改变了这个最大值会造成意外结果！)
		for (int j = pOMList.size() - 1; j > -1; j--) {
			for (int i = memberList.size() - 1; i > -1; i--) {
				if (memberList.get(i).Name.equals(pOMList.get(j).Name)
						&& pOMList.get(j).logicalIsActive == true) {
					pOMList.remove(j);
					memberList.remove(i);
					panel.remove(i);
					selectPom = null;
					break;
				}
			}
		}
	}

	/**
	 * 执行右击菜单中的订购对象
	 * @param e
	 * @param g
	 * @param panel
	 * @return
	 */
	private int OrderObjClass(MouseEvent e, Graphics g, JPanel panel) {
		if (selectLine != null && selectLine.LineType == null) {
			selectLine.IsVLine = true;
			selectLine.LineType = "Object";
			panel.repaint();
			MemberObject memberObject = new MemberObject();
			Member producerMember = new Member();
			Member consumerMember = new Member();
			
			for (Member member : MemberManage.MemberList) {				
				Rectangle[] rects = new Rectangle[4];
				rects[0] = member.pictureOfMember.LogicalRect[4];
				rects[1] = member.pictureOfMember.LogicalRect[5];
				rects[2] = member.pictureOfMember.LogicalRect[6];
				rects[3] = member.pictureOfMember.LogicalRect[7];
				for (int i = 0; i < 4; i++) {
					if (rects[i].contains(selectLine.StartPoint.getX()+2,selectLine.StartPoint.getY()+2)) {
						producerMember = member;
					}
					if (rects[i].contains(selectLine.EndPoint.getX()+2,selectLine.EndPoint.getY()+2)) {
						consumerMember = member;
					}
				}
			}
			
			//公布对象类的成员的参数位置信息,针对单输出
			for(Model_para_info producerMember_para_infoList:producerMember.Model.Model_para_infoList){
				if(producerMember_para_infoList.Para_type.toString().equals("output_para")){
			        for(Model_para_info consumerMember_para_infoList:consumerMember.Model.Model_para_infoList){
				        if(consumerMember_para_infoList.Para_type.toString().equals("input_para")&&consumerMember_para_infoList.Para_desc!=null){
					      String[] valueTable=consumerMember_para_infoList.Para_desc.split("/");
                            for(int i=0;i<valueTable.length;i++){
                    	       String[] value=valueTable[i].split("-");
                    	         if(value[0].equals(producerMember_para_infoList.Para_desc)&&!value[0].equals("")&&!producerMember_para_infoList.equals("")){   
                    	    	      relationManage.LocationList.add(producerMember.Id+"/"+value[1]);
                    	          }
                            }
				        }
			        }
			    }
			}
				
			memberObject.producer = producerMember;
			memberObject.consumer = consumerMember;
			
			memberObject.producerName = producerMember.Name;
			
			if (producerMember.Id != null) {
				memberObject.producerId = Integer.valueOf(producerMember.Id);
				memberObject.consumer.getGetFromId().add(producerMember.Id+"-"+producerMember.UniqueId);
			}

			selectLine.PointAName = producerMember.Name;
			memberObject.consumerName = consumerMember.Name;
			if (consumerMember.Id != null) {
				memberObject.consumerId = Integer.valueOf(consumerMember.Id);
				memberObject.producer.getSendToId().add(consumerMember.Id+"-"+consumerMember.UniqueId);
			}
			
	
			selectLine.PointBName = consumerMember.Name;
			memberObject.logicalLine = selectLine;
			relationManage.MemberObjecList.add(memberObject);
			return 1;
		}
		return 0;
	}

	// 执行右击菜单中的取消交互
	private int DelInterClass() {
		if (selectLine != null && selectLine.LineType.endsWith("Interaction")) {
			for (int i = relationManage.MemberInteractionList.size() - 1; i > -1; i--) {
				if (relationManage.MemberInteractionList.get(i).logicalLine == selectLine) {
					relationManage.MemberInteractionList.remove(i);
					selectLine.Point2 = selectLine.Point3 = selectLine.Point4 = selectLine.Point5 = null;// 这个位置要删除4个点
					selectLine.LineType = null;
				}
			}
			return 0;
		}
		return 1;
	}

	// 执行右击菜单中的订购交互
	private int OrderInterClass() {
		if (selectLine != null && selectLine.LineType.equals(null)) {
			MemberInteraction memberInteraction = new MemberInteraction();
			Member producerMember = new Member();
			Member consumerMember = new Member();
			for (Member member : MemberManage.MemberList) {
				Rectangle[] rects = new Rectangle[4];
				rects[0] = member.pictureOfMember.LogicalRect[4];
				rects[1] = member.pictureOfMember.LogicalRect[5];
				rects[2] = member.pictureOfMember.LogicalRect[6];
				rects[3] = member.pictureOfMember.LogicalRect[7];
				for (int i = 0; i < 4; i++) {
					if (rects[i].contains(selectLine.StartPoint.getX()+2,selectLine.StartPoint.getY()+2)) {
						producerMember = member;
					}
					if (rects[i].contains(selectLine.EndPoint.getX()+2,selectLine.EndPoint.getY()+2)) {
						consumerMember = member;
					}
				}
			}
			memberInteraction.producer = producerMember;
			memberInteraction.producerName = producerMember.Name;
			memberInteraction.producerId = Integer.valueOf(producerMember.Id);
			selectLine.PointAName = producerMember.Name;
			memberInteraction.consumer = consumerMember;
			memberInteraction.consumerName = consumerMember.Name;
			memberInteraction.consumerId = Integer.valueOf(consumerMember.Id);
			selectLine.PointBName = consumerMember.Name;
			memberInteraction.logicalLine = selectLine;
			relationManage.MemberInteractionList.add(memberInteraction);
			
			return 1;
		}
		return 0;
	}

	// 执行右击菜单中的取消对象
	private int DelObjClass(MouseEvent e, Graphics g, JPanel panel) {
		if (selectLine != null && selectLine.LineType.equals("Object")) {
			selectLine.IsVLine = false;
			panel.repaint();
			for (int i = relationManage.MemberObjecList.size() - 1; i > -1; i--) {
				if (relationManage.MemberObjecList.get(i).logicalLine
						.equals(selectLine)) {
					relationManage.MemberObjecList.remove(i);
					selectLine.Point2 = selectLine.Point3 = selectLine.Point4 = selectLine.Point5 = null;// 这个位置要删除4个点
					selectLine.LineType = null;
				}
			}
			return 0;
		}
		return 1;
	}

	// [end]

	// [start]界面按钮的操作

	public int DoAddInter_Click() {
		return OrderInterClass();
	}


	public int DoDelInter_Click() {
		return DelInterClass();
	}

	public void DoDel_Click() {	
		JPanel jTempPanel=DragAndDropDropTargetListener._jp_panel;
		int count=0;//因为线定义的是静态变量，所以每次删线的时候，会更新线的数量，导致循环遍历出错，所以在这里要手动添加一个虚的大小
		for(int i=0;i<lineManage.logicalLineList.size()+count;i++){
			if(lineManage.logicalLineList.get(i-count).IsActived==true){
				
				//如果该线是还没有订购
                if (lineManage.logicalLineList.get(i-count).Point2 == null)
                {
                    lineManage.logicalLineList.remove(i-count);
                }
                else//如果该线订购了则删除对应的关系
                {
                    for (int j =0;j<relationManage.MemberObjecList.size();j++)
                    {
                        if (lineManage.logicalLineList.get(i-count).equals(relationManage.MemberObjecList.get(j).logicalLine))
                        {
                        	lineManage.logicalLineList.remove(i-count);
                            relationManage.MemberObjecList.remove(j);
                            count++;
                        }
                    }
                }
			}
		}
		
		
		for (int j = pomManage.pOMList.size() - 1; j > -1; j--) {
			for (int i = MemberManage.MemberList.size() - 1; i > -1; i--) {
				if (MemberManage.MemberList.get(i).Name.equals(pomManage.pOMList.get(j).Name)
						&& pomManage.pOMList.get(j).logicalIsActive == true) {
					pomManage.pOMList.remove(j);
					MemberManage.MemberList.remove(i);
					jTempPanel.remove(i);
					selectPom = null;
					break;
				}
			}
		}
		
	}

	@SuppressWarnings("static-access")
	public void DoKeyDown(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_DELETE){
		System.out.println(e.getKeyCode());
		Delete(memberManage.MemberList, pomManage.pOMList, lineManage.logicalLineList,DragAndDropDropTargetListener._jp_panel);
		}
	}
	
	// [end]


	public void DoPaint(Graphics g) {
		ReDrawPom(g);
		ReDrawLine(g);
		if(isDrawSquare){
		DrawTempSquare(g);
		}
		if (flag_tempLine) {
			drawtempline(g);
		}
		DrawTempVLine(g);
		
	}

	/**
	 * 判断画直线还是折线，二者只能选择其一 线的终点是7
	 * @param g
	 */
	public void DrawTempVLine(Graphics g)
	{
		for (Line line : lineManage.logicalLineList) {
			if (line.IsVLine) {
				p22 = line.EndPoint;
				p11 = line.StartPoint;
				if (p22.x >= p11.x) {
					line.Point2 = new Point(p11.x + (p22.x - p11.x) / 2, p11.y);
					line.Point3 = line.Point2;
					line.Point5 = new Point(line.Point2.x, p22.y);
					line.Point4 = line.Point5;
	
				}
				if (p22.y - p11.y > 70 && p11.x > p22.x && p11.x - p22.x <= 70) {
					line.Point2 = new Point(p11.x + 30, p11.y);
					line.Point3 = new Point(line.Point2.x, p11.y + 65
							+ (p22.y - 65 - p11.y - 65) / 2);
					line.Point5 = new Point(p22.x - 30, p22.y);
					line.Point4 = new Point(line.Point5.x, line.Point3.y);
					
				}
				if (p11.y <= p22.y && p11.x - p22.x > 70) {
					line.Point2 = new Point(p11.x + 30, p11.y);
					line.Point3 = new Point(line.Point2.x, p11.y - 70);
					line.Point5 = new Point(p22.x - 30, p22.y);
					line.Point4 = new Point(line.Point5.x, line.Point3.y);
				

				}
				if (p11.y > p22.y && p11.y - p22.y <= 70
						&& p11.x - p22.x >= 260) {
					line.Point2 = new Point(p11.x + 30, p11.y);
					line.Point5 = new Point(p22.x - 30, p22.y);
					line.Point4 = new Point(line.Point5.x, p22.y - 70);
					line.Point3 = new Point(line.Point2.x, line.Point4.y);
				

				}
				if (p11.y - p22.y > 70 && p11.x > p22.x) {
					line.Point2 = new Point(p11.x + 30, p11.y);
					line.Point5 = new Point(p22.x - 30, p22.y);
					line.Point3 = new Point(line.Point2.x, p11.y - 65
							- (p11.y - p22.y - 70) / 2);
					line.Point4 = new Point(line.Point5.x, line.Point3.y);
		

				}
				DrawVLine(g, line);

			
			} else {
				drawAL(line.StartPoint.x, line.StartPoint.y, line.EndPoint.x,
						line.EndPoint.y, g,line.lineColor);
			}
		}
	}
	
	public void drawtempline(Graphics g) {
		if (point2.x != 0) {
			drawAL(point1.x, point1.y, point2.x, point2.y, g,color);
		}
	}

	public void DrawVLine(Graphics g) {
		for (Line line : lineManage.logicalLineList) {
			drawAL(line.StartPoint.x, line.StartPoint.y, line.EndPoint.x,
					line.EndPoint.y, g,line.lineColor);
		}
	}
	/**
	 * 首先判断是否是画矩形
	 * @param e
	 * @param panel
	 */
	private void IsDrawSquare(MouseEvent e, JPanel panel){
		if(panel.getCursor()==Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR)&&selectLine==null){
			isDrawSquare=true;
			point1=e.getPoint();
		}
		
	}
	/**
	 *鼠标移动的时候，不断刷新，记录第二个点的位置
	 * @param e
	 * @param panel
	 */
	private void DrawSquare(MouseEvent e, JPanel panel) 
	{
		if (isDrawSquare == true) {				
			point2 = e.getPoint();
			panel.repaint();
		}
	}
	
	/**
	 * 画矩形
	 * @param e
	 */
	private void DrawTempSquare(Graphics g) {
	
		if (isDrawSquare == true) {	
			g.drawRect(point1.x, point1.y,
					(int) (point2.x - point1.x),
					(int) (point2.y - point1.y));

		}

	}

}
