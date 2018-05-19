package psm.Models.DataModel;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import javax.swing.JLabel;

import com.thoughtworks.xstream.annotations.XStreamOmitField;


public class PictureOfMember implements Serializable 
{
	
	public String Id; //ͼƬ��ʾ
	public String Name;
//	public Point LogicalLocation; //ͼƬ�߼���ʾλ��
//  public Dimension LogicalSize ; 
	public int ImageWidth;
	public int ImageHeight;
	public boolean logicalIsActive; //ͼƬ�߼�ѡ��
   	public Rectangle[] LogicalRect;
   	public Cursor SelectCursor;//ѡ�к�����״̬
   	public List<Line> LineList;

   
   	public int oldX = -999;
   	public int oldY = -999;
   	
   	public int count = 0;
   	public int type;        //����Ŵ���С�����
   	public List<JLabel> JLabelList=new ArrayList<JLabel>();
   	//public JLabel ImageLable=new JLabel();
	public JLabel imageLabel=new JLabel();;
	
   	//public List<JLabel> JLabelList=new ArrayList<JLabel>();
   	public PictureOfMember()
   	{   		
   		SelectCursor = new Cursor(Cursor.CROSSHAIR_CURSOR );
   		imageLabel.setLocation( new Point());
   		ImageWidth=70;
   		ImageHeight=70;
   		LogicalRect = new Rectangle[8];
   		LineList = new ArrayList<Line>();
   		Name = "" + count;
   		count++;
   	}
}
