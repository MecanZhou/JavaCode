package multicastSocket;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

public class Receiver extends JFrame implements Runnable, ActionListener {
	
	private static final long serialVersionUID = 6315199435755460823L;
	/**
	 * 多播组接收端
	 */
		
	    int port;  
	    InetAddress group = null;  
	    MulticastSocket socket = null;  
	    JButton startButton;  
	    JButton stopButton;  
	    JButton cleanButton;  
	    JTextArea currentMsg;  
	    JTextArea receiveMsg;  
	    Thread thread;  
	    boolean isStop = false;//停止接收广播信息的标志 
	    
	    public Receiver(){
	    	
	    	 setTitle("接收广播信息");  
	         Container container = this.getContentPane();  
	         startButton = new JButton("开始接收");  
	         stopButton = new JButton("停止接收");  
	         cleanButton = new JButton("清空信息");  
	         startButton.addActionListener(this);  
	         stopButton.addActionListener(this);  
	         cleanButton.addActionListener(this);  
	         currentMsg = new JTextArea(3,20);//创建3行20列的多行文本框  
	         currentMsg.setForeground(Color.red);//设置字体颜色为红色  
	         receiveMsg = new JTextArea(8,20);//默认字体颜色为黑色  
	         container.setLayout(new BorderLayout());  
	         JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);//创建一带水平分隔条的面板  
	         JScrollPane currScrollPane = new JScrollPane();  
	         currScrollPane.setViewportView(currentMsg);  
	         JScrollPane recvScrollPane = new JScrollPane();  
	         recvScrollPane.setViewportView(receiveMsg);  
	         currentMsg.setEditable(false);  
	         receiveMsg.setEditable(false);  
	         sp.add(currScrollPane);  
	         sp.add(recvScrollPane);  
	         container.add(sp,BorderLayout.CENTER);  
	         JPanel bottomJPanel = new JPanel();  
	         bottomJPanel.add(startButton);  
	         bottomJPanel.add(stopButton);  
	         bottomJPanel.add(cleanButton);  
	         container.add(bottomJPanel,BorderLayout.SOUTH);  
	         setSize(500,400);  
	         setVisible(true);  
	         thread = new Thread(this);  
	         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
	         
	         port = 8088;  
	         try  
	         {  
	             group = InetAddress.getByName("230.198.112.0");  
	             socket = new MulticastSocket(port);  
	             socket.joinGroup(group);  
	         }catch (Exception e)  
	         	{  
	               
	         	} 
	    }
	public static void main(String[] args) {
		
		new Receiver();
	}
	
	public void actionPerformed(ActionEvent e1) {
		// TODO 自动生成的方法存根
		 if(e1.getSource() == startButton)  
	        {  
	            startButton.setEnabled(false);  
	            stopButton.setEnabled(true);  
	            if(!(thread.isAlive()))  
	            {  
	                thread = new Thread(this);  
	            }  
	            try  
	            {  
	                thread.start();  
	                isStop = false;  
	            }  
	            catch (Exception ee)  
	            {  
	                  
	            }  
	        }  
	        if(e1.getSource() == stopButton)  
	        {  
	            startButton.setEnabled(true);  
	            stopButton.setEnabled(false);  
	            isStop = true; 
	            
	        }  
	        if(e1.getSource() == cleanButton)  
	        {  
	            receiveMsg.setText("");  
	        }  
	    }  
	      
	    public void run()  
	    {  
	        while(true)  
	        {  
	            byte buff[] = new byte[8888];  
	            DatagramPacket packet = null;  
	            packet = new DatagramPacket(buff, buff.length,group,port);  
	            try  
	            {  
	                socket.receive(packet);  
	                String message = new String(packet.getData(),0,packet.getLength());  
	                currentMsg.setText("正在接收的内容：\n"+message);  
	                receiveMsg.append(message+"\n");
	                receiveMsg.setCaretPosition(receiveMsg.getText().length());
	            }  
	            catch (Exception e)  
	            {  
	                  
	            }  
	            if(isStop == true)  
	            {  
	            	currentMsg.setText("");
	                break;  
	            }  
	        }  
	}

}
