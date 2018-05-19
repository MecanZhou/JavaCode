package psm.Models.DataModel;
import java.awt.*;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import javax.swing.JLabel;

import com.thoughtworks.xstream.annotations.XStreamOmitField;


public class PictureOfMember implements Serializable 
{
	
	public String Id; //图片标示
	public String Name;
//	public Point LogicalLocation; //图片逻辑显示位置
//  public Dimension LogicalSize ; 
	public int ImageWidth;
	public int ImageHeight;
	public boolean logicalIsActive; //图片逻辑选中
   	public Rectangle[] LogicalRect;
   	public Cursor SelectCursor;//选中后鼠标的状态
   	public List<Line> LineList;

   
   	public int oldX = -999;
   	public int oldY = -999;
   	
   	public int count = 0;
   	public int type;        //区别放大缩小的情况
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
