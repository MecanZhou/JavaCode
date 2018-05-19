package psm.Views;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import psm.Component.CalendarPanel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.Date;

import java.awt.Color;
import java.awt.Dialog.ModalExclusionType;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
//import java.awt.Window.Type;

public class CalendarFrame extends JFrame {
    
    private JPanel contentPane;
    private CalendarPanel calendarPanel;
    Date datetime;
    public FrmDeployModel frmDeployModel;
    public FrmUpModel frmUpDownModel;
    int getFocus=0;
    /**
     * Launch the application.
     */
    
    /**
     * Create the frame.
     */
    public CalendarFrame() {
        setTitle("时间选择");
        setResizable(false);
        setBounds(200, 100, 182, 222);
        addWindowListener(new WindowListener(){
        	 public void windowClosing(WindowEvent e) {
        		 if(getFocus==2||getFocus==3){
               		 frmUpDownModel.setEnabled(true);
               		 }
        		 
        	 }

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
        
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 248, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);// 使用绝对定位布局
        calendarPanel = new CalendarPanel();// 创建日历控件
        calendarPanel.addDateChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                do_calendarPanel_propertyChange(evt);// 调用事件处理方法
                if(getFocus==2||getFocus==3){
           		 frmUpDownModel.setEnabled(true);
           		 dispose();
           		 }
               
            }
        });
        calendarPanel.setBounds(6, 6, 162, 170);

        contentPane.add(calendarPanel);
    }
    public void getfrmDeployModel(FrmDeployModel fd)
    {
    	frmDeployModel=fd;
    	getFocus=1;
    }
    
    public void getfrmUpDownModel(FrmUpModel fud){
    	frmUpDownModel=fud;
    	if(frmUpDownModel.btnPublishData.hasFocus()){
    		getFocus=2;
    	}
    	if(frmUpDownModel.btndtiModified.hasFocus()){
    		getFocus=3;
    	}
    }
   
     public void do_calendarPanel_propertyChange(PropertyChangeEvent evt) {
        // 通过事件跟新标签控件的日期
    	datetime=calendarPanel.getDate();
        String str = String.valueOf(datetime);
        if(getFocus==1){
        frmDeployModel.modelPubTextshow(str);
        }
        else if(getFocus==2){
        frmUpDownModel.dtiPulishShow(str);
        }
        else if(getFocus==3){
        	frmUpDownModel.dtiModifiedShow(str);
        }
        
    }
   
}
