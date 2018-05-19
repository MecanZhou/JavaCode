package rectangleMove;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.awt.Color;

import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextField;

import com.sun.prism.Graphics;

import java.awt.TextArea;

public class rectThread extends JFrame implements Runnable {
	
	public rectThread() {
		
		Container container = this.getContentPane();
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);

		JButton btnNewButton = new JButton("NextLine");
		getContentPane().add(btnNewButton, BorderLayout.SOUTH);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
	}
	
	public void run(Graphics g) {
		int x=0,y=0;
		g.drawRect(x, y, 5, 5);
	}
	
	public static void main(String[] args) {
		
	}
}
