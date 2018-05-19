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
	public static PictureOfMember temPOM = null;// ���ڴ�ֵ��FrmDeployManage��ȷ��ѡ�е�ͼƬ
	public static Line temLine = null;// ���ڴ�ֵ��FrmParaChoose��ȷ��ѡ�е�ֱ��
	FrmDeployModel frmDeployMdoel;
	static LineManage lineManage = new LineManage(); // ����ֱ�ߵĶ���
	private static Line selectLine;
	private boolean isDrawLine;
	public static PictureOfMemberManage pomManage = new PictureOfMemberManage();
	private boolean isPress = false;
	public static boolean isDrawSquare;//�Ƿ���Ի���
	private Rectangle square;
	static Point point1 = new Point(0, 0);
	static Point point2 = new Point(0, 0);
	
	private boolean isSaveLine;
	private MemberManage memberManage = new MemberManage();// ��Ա�������
	private RelationManage relationManage = new RelationManage();// ��ϵ�������
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
	 * �����ڵ�ִ����ק���ͷŵȵ�����Ҫ�ĳ�Ա
	 * @param nodeName ���ڵ������
	 * @param g
	 * @param point ����
	 * @param panel �߼�����
	 * @return ��Ӧ�ĳ�Ա
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
						// �Գ�Ա�����в��
						String[] pomTemp = pomManage.pOMList.get(i).Name
								.split("-");
						pomName = pomTemp[1];
					}
					// ����Ѵ���һ��FedrationTimer
					if (pomName.equals(unName)
							&& pomName.equals("FedrationTimer")) {
						JOptionPane.showMessageDialog(new JPanel(),"һ��Ԥ��ֻ����һ����ʱ��", "��ʾ",
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
                 //���ͷŵ㻭ͼ
				 combineMemberTemp.pictureOfMember.imageLabel.setSize(new Dimension(70, 70));
				 combineMemberTemp.pictureOfMember.imageLabel = new JLabel();
				 combineMemberTemp.pictureOfMember.imageLabel.setBounds(point.x, point.y,
							70, 70);
                 //���ó�Ա
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
	 * ����ƶ���ʱ��Ҫ�Զ��仯�����ʽ
	 * @param g 
	 * @param e ����
	 * @param panel ���
	 * @return booleanֵ
	 */
	public boolean MouseMove(Graphics g, MouseEvent e, JPanel panel) {

		if (isPress) {
			SetCursor(e, panel);
		}
		return false;
	}

	/**
	 * ��ȡѡ�е�ͼƬ
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
			propretyChange.tableMemberChange(e, nodeName);// �������Ա����ʱ�������������任

			selectPom.logicalIsActive = true;
			selectPom.oldX = e.getX();
			selectPom.oldY = e.getY();
			
			 new Point(selectPom.LogicalRect[4].getLocation().x-selectPom.imageLabel.getWidth()/2,selectPom.LogicalRect[4].getLocation().y);
	           e.getPoint();
	           xL = e.getX()-selectPom.LogicalRect[4].getLocation().x+selectPom.imageLabel.getWidth()/2;
	           yL = e.getY()-selectPom.LogicalRect[4].getLocation().y;

			ReDrawPom(g);
			panel.repaint();
			Point p22 = new Point((int) e.getX(), (int) e.getY()); // ��ȡ��갴��ʱ������
			pomManage.SetCursorOfLogical(panel, selectPom, p22, point1);
			isPress = true;
			// ѡ��ģ�͵�ʱ���ж����ߵ���㻹���յ�
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
	 * �ж����Ƿ�ѡ�У��ı�������
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
	 * �÷����Ǽ��������Ա�������Ա��type�Ƿ�ƥ�䣬ֻ��ƥ��Ĳ��ܽ�������
	 */
	private void CheckLineShow() {

		int count=0;
		List<Member> tempMemberS = new ArrayList<Member>();                     //������ʱ�洢�����Ա
		List<Member> tempMemberE = new ArrayList<Member>();                     //������ʱ�洢�����Ա

		for (int i = 0; i < lineManage.logicalLineList.size(); i++){	//���������ϵ��ߣ���ͨ���ߵ����˳�Ա�жϳ������Ա�������Ա		
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
		//���������Ա��type�����Ƿ����input_para,�����Ա�������Ƿ����out_putpara
			
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
	 * �ж��Ƿ���
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
	 * ���ÿ���һ�Σ���ȡ��������
	 * @param e
	 * @return ��������
	 */
	public Point getPoint(MouseEvent e) {
		Point p = new Point((int) e.getX(), (int) e.getY());
		return p;
	}

	/**
	 * ��갴��ʵ�ַ���
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
	 * ����ƶ�Ҫʵ�ֵķ���
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
			DrawSquare(e, panel);  //��ѡ
			}
		return false;
	}

	/**
	 * ���̧��Ҫʵ�ֵķ���
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
		
		// ���̧������¸�ֵ
		isDrawLine = false;
		
		square=new Rectangle(point1.x,point1.y,point2.x - point1.x,point2.y- point1.y);//��ѡ��һ�����������в���
		point1 = new Point(0, 0);
		point2 = new Point(0, 0);
		isSaveLine = false;
		SelectCombineMember();                                        //��ѡ
		
		drawAllList(g);
		panel.repaint();
	}
	
	/**
	 * ��Ա֮�仭������
	 * @param e ����
	 * @param g ������
	 * @param panel ��ǰ���
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
	 * ���������ʽ
	 * @param e ����
	 * @param panel ��ǰ���
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
	 * ͼƬ�ƶ���ʱ��ִ�еķ���
	 * @param e
	 * @param g
	 * @param panel
	 * @return
	 */
	private boolean MovePom(MouseEvent e, Graphics g, JPanel panel) {
		if (selectPom != null) {
		
			flag_tempLine = false;
			Point p = pomManage.ControlCursor(panel, selectPom, e); // �����ק����������˱߽磬������һ������Ϊ0������Ϊ����
			Dimension dimension = new Dimension((int) panel.getWidth(),(int) panel.getHeight());
			pomManage.Move(e, p, selectPom, dimension, panel,xL,yL);
			if (isDragOverLine) {
				for (Line line : lineManage.logicalLineList) { // �϶�֮ǰҪ���������ͼƬ
																// �ߵ��յ� ��ȥ5
					if (line.PointBName == selectPom.Name && line.Linknum2 == 7) {
						line.EndPoint = selectPom.LogicalRect[7].getLocation();
						DrawTempVLine(g); // �����ߺ�ֱ�� //Ĭ�����
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

					// �ߵ���� ����ȥ7
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
	 * ����ʱ����
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
	 * �ߵ�����
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
	 * ������ͷ��ֱ�߷���
	 * @param sx ��ʼ��x����
	 * @param sy ��ʼ��y����
	 * @param ex ������x����
	 * @param ey ������y����
	 * @param g
	 */
	public void drawAL(int sx, int sy, int ex, int ey, Graphics g,Color c) {

		double H = 10; // ��ͷ�߶�
		double L = 4; // �ױߵ�һ��
		int x3 = 0;
		int y3 = 0;
		int x4 = 0;
		int y4 = 0;
		double awrad = Math.atan(L / H); // ��ͷ�Ƕ�
		double arraow_len = Math.sqrt(L * L + H * H); // ��ͷ�ĳ���
		double[] arrXY_1 = rotateVec(ex - sx, ey - sy, awrad, true, arraow_len);
		double[] arrXY_2 = rotateVec(ex - sx, ey - sy, -awrad, true, arraow_len);
		double x_3 = ex - arrXY_1[0]; // (x3,y3)�ǵ�һ�˵�
		double y_3 = ey - arrXY_1[1];
		double x_4 = ex - arrXY_2[0]; // (x4,y4)�ǵڶ��˵�
		double y_4 = ey - arrXY_2[1];

		Double X3 = new Double(x_3);
		x3 = X3.intValue();
		Double Y3 = new Double(y_3);
		y3 = Y3.intValue();
		Double X4 = new Double(x_4);
		x4 = X4.intValue();
		Double Y4 = new Double(y_4);
		y4 = Y4.intValue();
		// ���߻�ƽ������
		Line2D lin = new Line2D.Float(sx, sy, ex, ey);
		((Graphics2D) g).draw(lin);
	
		GeneralPath triangle = new GeneralPath();
		triangle.moveTo(ex, ey);
		triangle.lineTo(x3, y3);
		triangle.lineTo(x4, y4);
		triangle.closePath();
		// ʵ�ļ�ͷ
		((Graphics2D) g).fill(triangle);
		
		g.setColor(c);

	}

	/**
	 * ����
	 * @param px x����
	 * @param py y����
	 * @param ang��ת��
	 * @param isChLen�Ƿ�ı䳤��
	 * @param newLen�µĳ���
	 * @return
	 */
	public double[] rotateVec(int px, int py, double ang, boolean isChLen,
			double newLen) {
		double mathstr[] = new double[2];
		// ʸ����ת��������������ֱ���x������y��������ת�ǡ��Ƿ�ı䳤�ȡ��³���
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
	 * ��ѡ��ѡ���ѡ�еĳ�Ա
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
	 * �ػ�ͼƬ�ϵĶ˿�
	 * @param g
	 */
	private void ReDrawPom(Graphics g) {
		for (PictureOfMember pom : pomManage.pOMList) {
			pomManage.Draw(g, pom);
		}
	}


	/**
	 * �ػ�ֱ��
	 * @param g
	 */
	private void ReDrawLine(Graphics g) {
		lineManage.Draw(g, lineManage.logicalLineList);
	}

	/**
	 * ���ó�Ա����Ӧ��ͼƬ
	 * @param member
	 * @throws IOException
	 */
	static void SetImage(Member member) throws IOException {

		URL url = ((URLClassLoader) Util.class.getClassLoader())
				.findResource("psm/Image/1.png");
		ImageIcon icon = new ImageIcon(url);

		member.pictureOfMember.imageLabel = new JLabel(icon); // д֮ǰһ��Ҫʵ����

		member.pictureOfMember.imageLabel.setIcon(new ImageIcon(ImageIO
				.read(new File("C:\\model.jpg"))));

	}

	// [start] OtherFunction
	/**
	 * �Ҽ��˵���ѡ��
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
					JMenuItem DelObjitem = new JMenuItem("ȡ������");
					DelObjitem.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							DelObjClass(e, g, panel);
						}
					});
					pop.add(DelObjitem);
					JMenuItem DelInteritem = new JMenuItem("ȡ������");
					DelInteritem.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							DelInterClass();
						}
					});
					pop.add(DelInteritem);
					JMenuItem DelLine = new JMenuItem("ɾ��");
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
					JMenuItem OrderObjitem = new JMenuItem("��������");
					OrderObjitem.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
//							FrmParaChoose windows=new FrmParaChoose();
//						    windows.frame.setVisible(true);					    
							OrderObjClass(e, g, panel);
						}
					});
					pop.add(OrderObjitem);
					JMenuItem OrderInteritem = new JMenuItem("��������");
					OrderInteritem.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							OrderInterClass();
						}
					});
					pop.add(OrderInteritem);
					JMenuItem DelLine = new JMenuItem("ɾ��");
					DelLine.addMouseListener(new MouseAdapter() {
						public void mouseReleased(MouseEvent e) {
							Delete(MemberManage.MemberList, pomManage.pOMList,
									lineManage.logicalLineList, panel);						
							panel.repaint();
						}
					});
					pop.add(DelLine);
					
//					JMenuItem showColor = new JMenuItem("�޸���ɫ");
//					showColor.addMouseListener(new MouseAdapter() {
//						public void mouseReleased(MouseEvent e) {
//							color=JColorChooser.showDialog(null, "ѡ���ߵ���ɫ��", Color.BLUE);
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
				JMenuItem memberitem = new JMenuItem("���ó�Ա");
				memberitem.addMouseListener(new MouseAdapter() {
					public void mouseReleased(MouseEvent e) {
						frmDeployMdoel = new FrmDeployModel();
						frmDeployMdoel.frame.setVisible(true);
					}

				});
				pop.add(memberitem);
				JMenuItem Deleteitem = new JMenuItem("ɾ��");
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
	 * ִ���һ��˵��е�ɾ������
	 * @param memberList ��Ա�б�
	 * @param pOMList ��ԱͼƬ���б�
	 * @param lineList �ߵ��б�
	 * @param panel
	 */
	private void Delete(List<Member> memberList, ArrayList<PictureOfMember> pOMList,
			ArrayList<Line> lineList, JPanel panel) {
		
		int count=0;//��Ϊ�߶�����Ǿ�̬����������ÿ��ɾ�ߵ�ʱ�򣬻�����ߵ�����������ѭ��������������������Ҫ�ֶ����һ����Ĵ�С
		
		//ɾ�����Ƶ���
		for(int i=0;i<lineManage.logicalLineList.size()+count;i++){
			if(lineManage.logicalLineList.get(i-count).IsActived==true){
				//��������ǻ�û�ж���
                if (lineManage.logicalLineList.get(i-count).Point2 == null){
                    lineManage.logicalLineList.remove(i-count);
                }
                else//������߶�������ɾ����Ӧ�Ĺ�ϵ
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
		
		//ɾ����Ա��ʱ�������Ա���ߵĹ���������Ҳһ��ɾ����
		for(int k=0;k<pOMList.size();k++){
			PictureOfMember pOM=pomManage.pOMList.get(k);
			if(pOM.logicalIsActive){
				List<Line> tempLineList=new ArrayList<Line>();
				for(int m=4;m<8;m++){
					for(Line line:lineList){
						//�����+2�ǽ��getlocation��ȡ�ĵ���int,getx()��double�������������ƫ��
						if(pOM.LogicalRect[m].contains(line.StartPoint.getX()+2,line.StartPoint.getY()+2)){
							tempLineList.add(line);
						}
						if(pOM.LogicalRect[m].contains(line.EndPoint.getX()+2,line.EndPoint.getY()+2)){
							tempLineList.add(line);
						}						
					}					
				}
				for(int i=0;i<tempLineList.size();i++){
					//������߻�û�б�����
					if(tempLineList.get(i).Point2==null){
						for(int m=0;m<lineList.size();m++){
							if(lineList.get(m).EndPoint.equals(tempLineList.get(i).EndPoint)){
								lineList.remove(m);
							}
						}
						
					}
					//���������ɾ����Ӧ�Ĺ�ϵ
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
		
		// ɾ����Ա����(�˴�Ҫ����forѭ���������������ü��������ֵΪ��������ѭ�����ڸı���������ֵ�������������)
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
	 * ִ���һ��˵��еĶ�������
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
			
			//����������ĳ�Ա�Ĳ���λ����Ϣ,��Ե����
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

	// ִ���һ��˵��е�ȡ������
	private int DelInterClass() {
		if (selectLine != null && selectLine.LineType.endsWith("Interaction")) {
			for (int i = relationManage.MemberInteractionList.size() - 1; i > -1; i--) {
				if (relationManage.MemberInteractionList.get(i).logicalLine == selectLine) {
					relationManage.MemberInteractionList.remove(i);
					selectLine.Point2 = selectLine.Point3 = selectLine.Point4 = selectLine.Point5 = null;// ���λ��Ҫɾ��4����
					selectLine.LineType = null;
				}
			}
			return 0;
		}
		return 1;
	}

	// ִ���һ��˵��еĶ�������
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

	// ִ���һ��˵��е�ȡ������
	private int DelObjClass(MouseEvent e, Graphics g, JPanel panel) {
		if (selectLine != null && selectLine.LineType.equals("Object")) {
			selectLine.IsVLine = false;
			panel.repaint();
			for (int i = relationManage.MemberObjecList.size() - 1; i > -1; i--) {
				if (relationManage.MemberObjecList.get(i).logicalLine
						.equals(selectLine)) {
					relationManage.MemberObjecList.remove(i);
					selectLine.Point2 = selectLine.Point3 = selectLine.Point4 = selectLine.Point5 = null;// ���λ��Ҫɾ��4����
					selectLine.LineType = null;
				}
			}
			return 0;
		}
		return 1;
	}

	// [end]

	// [start]���水ť�Ĳ���

	public int DoAddInter_Click() {
		return OrderInterClass();
	}


	public int DoDelInter_Click() {
		return DelInterClass();
	}

	public void DoDel_Click() {	
		JPanel jTempPanel=DragAndDropDropTargetListener._jp_panel;
		int count=0;//��Ϊ�߶�����Ǿ�̬����������ÿ��ɾ�ߵ�ʱ�򣬻�����ߵ�����������ѭ��������������������Ҫ�ֶ����һ����Ĵ�С
		for(int i=0;i<lineManage.logicalLineList.size()+count;i++){
			if(lineManage.logicalLineList.get(i-count).IsActived==true){
				
				//��������ǻ�û�ж���
                if (lineManage.logicalLineList.get(i-count).Point2 == null)
                {
                    lineManage.logicalLineList.remove(i-count);
                }
                else//������߶�������ɾ����Ӧ�Ĺ�ϵ
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
	 * �жϻ�ֱ�߻������ߣ�����ֻ��ѡ����һ �ߵ��յ���7
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
	 * �����ж��Ƿ��ǻ�����
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
	 *����ƶ���ʱ�򣬲���ˢ�£���¼�ڶ������λ��
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
	 * ������
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
