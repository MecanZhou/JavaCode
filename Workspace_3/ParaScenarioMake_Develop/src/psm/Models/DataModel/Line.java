package psm.Models.DataModel;


import java.awt.Color;
import java.awt.Point;

public class Line {
	
   
    public boolean IsActived;//��ֱ���Ƿ���ѡ��״̬
   
    public Point StartPoint ;
    public Point Point2 ;
    public Point Point3 ;
    public Point Point4 ;
    public Point Point5 ;   
    public Point EndPoint;
    public String PointAName;//"���������� 
    public String PointBName;//���������� 
    public String LineType ;//��Ϊ���Ϊ������߽�����,��������
    public String ClassName ;
    public int Linknum1 ;    //���������ӵ�ĳ��ͼ�Σ���ס ���ӵ�λ��
    public int Linknum2 ;   //�յ����ӵ�
    public boolean IsMove ;    //�ܷ��ƶ�
    public int MoveType = 0;  //�����ƶ������
    public boolean IsVLine ;  //�����Ƿ�����
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
