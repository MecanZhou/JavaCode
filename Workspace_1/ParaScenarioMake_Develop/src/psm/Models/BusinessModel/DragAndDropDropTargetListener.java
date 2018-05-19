package psm.Models.BusinessModel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.PaintEvent;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.GrayFilter;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import com.mysql.jdbc.Util;
import com.sun.corba.se.impl.orbutil.graph.Node;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.NEWARRAY;




import psm.Models.DataModel.Line;
import psm.Models.DataModel.Member;
import psm.Models.DataModel.Model;
import psm.Models.DataModel.PictureOfMember;
import psm.Views.FrmMain;
import sun.awt.RepaintArea;
import sun.misc.Cleaner;
import sun.rmi.runtime.NewThreadAction;
import sun.security.pkcs11.P11TlsKeyMaterialGenerator;



public class DragAndDropDropTargetListener implements DropTargetListener {



	Point StartPomA, EndPomA;


	Point Start;
	Point End;
	
	Graphics tempGra=null;

	FrmMain frmMain;

	
	ArrayList<Point> a1 = new ArrayList<Point>();
	ArrayList<Point> a2 = new ArrayList<Point>();
	ArrayList<Line2D> Lines = new ArrayList<Line2D>();


	PropertyManage propretyChange = new PropertyManage();
	ArrayList<JLabel> JLabelList = new ArrayList<JLabel>();
	ArrayList<Member> MemberList = new ArrayList<Member>();
	
	public static JPanel _jp_panel=null;

	 @SuppressWarnings("unused")
	private static MemberManage memberManage=new MemberManage();//成员管理对象	 
	
	private LogicalStructureManage tabPageLogicalStructureManage=new LogicalStructureManage();		

	@Override
	public void dragEnter(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DropTargetEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drop(final DropTargetDropEvent e)  //界面的主要操作事件
	{
        Transferable t = e.getTransferable();

         String Nodename = "";

         try 
         {
             if( t.isDataFlavorSupported( DataFlavor.stringFlavor ) )
             {
                Nodename = t.getTransferData( DataFlavor.stringFlavor  ).toString();                         
             }
         
         } 
         catch( IOException ioe ) 
         {
            ioe.printStackTrace();
         } 
         catch( UnsupportedFlavorException ufe )
         {
            ufe.printStackTrace();
         }
      
        DropTarget dt = (DropTarget)e.getSource();
        final JPanel panelLogicalStructure=(JPanel)dt.getComponent();
        _jp_panel=panelLogicalStructure;
     
        try
        {        	        
        	final Graphics g=panelLogicalStructure.getGraphics();
        	tempGra=g;
        	Point point = new Point(e.getLocation().x, e.getLocation().y);
        	tabPageLogicalStructureManage.DoDragDorp(Nodename, g, point, FrmMain.panelLogicalStructure);
        }
        
       catch(Exception e1){
//       	JOptionPane.showMessageDialog(null, "在新建成员时出现错误！请查找原因", "警告", JOptionPane.ERROR_MESSAGE);
    	   	e1.printStackTrace();
        }

        //界面上的其余操作
        panelLogicalStructure.addMouseListener(new MouseListener()//面板的鼠标点击监听
        {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			//鼠标按下
			@Override
			public void mousePressed(MouseEvent e) 
			{	
				tabPageLogicalStructureManage.DoMousePress(tempGra,e, panelLogicalStructure);					
	
			}
				
			//鼠标抬起
			@Override		
			public void mouseReleased(MouseEvent e) {
				Graphics gtemp = panelLogicalStructure.getGraphics();
				tabPageLogicalStructureManage.DoShowRightMenu(e,gtemp,panelLogicalStructure);//执行弹出右键快捷键
				tabPageLogicalStructureManage.DoMouseUp(tempGra,panelLogicalStructure,e);
				// TODO Auto-generated method stub
				
			}
        });
	

	
	//界面上的移动动作操作

	 panelLogicalStructure.addMouseMotionListener(new MouseMotionListener(){

	

		@Override
		public void mouseDragged(MouseEvent e) {
		
			tabPageLogicalStructureManage.DoMouseMove(tempGra,e,panelLogicalStructure);
           
            
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			
		 tabPageLogicalStructureManage.MouseMove(tempGra,e,panelLogicalStructure);
			// TODO Auto-generated method stub
			
		}
	 
	 });
	}
	@Override

	public void dropActionChanged(DropTargetDragEvent arg0) {
		// TODO Auto-generated method stub

	}
	
	




}
