package simpleFrame;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class SimpleFrameTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				SimpleFrame frame=new SimpleFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
			}
		});
	}
	
//	class SimpleFrame extends JFrame{
//		private static final int DEFAULT_WIDTH=300;
//		private static final int DEFAULT_HEIGHT=200;
//		
//		public SimpleFrame(){
//			setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
//		}
//	}
}
