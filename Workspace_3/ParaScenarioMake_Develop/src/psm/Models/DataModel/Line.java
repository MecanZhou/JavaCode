package psm.Models.DataModel;


import java.awt.Color;
import java.awt.Point;

public class Line {
	
   
    public boolean IsActived;//该直线是否处于选中状态
   
    public Point StartPoint ;
    public Point Point2 ;
    public Point Point3 ;
    public Point Point4 ;
    public Point Point5 ;   
    public Point EndPoint;
    public String PointAName;//"发布者名称 
    public String PointBName;//订购者名称 
    public String LineType ;//改为标记为对象或者交互类,订购类型
    public String ClassName ;
    public int Linknum1 ;    //如果起点连接到某个图形，记住 连接的位置
    public int Linknum2 ;   //终点连接到
    public boolean IsMove ;    //能否移动
    public int MoveType = 0;  //区分移动的情况
    public boolean IsVLine ;  //区分是否画折线
    public Color lineColor;
    
    public Attribute lineAttribute;
    
    
    public Line(){
    	lineAttribute=new Attribute();

    }
    public Line(Point point)
    {
    	lineAttribute=new Attribute();
        IsActived = false;
        StartPoint = point;
        PointBName = "";
        ClassName = "";

        Linknum1 = -1;
        Linknum2 = -1;
    }
	

}
