package main;

import java.awt.*;
import javax.swing.*;
import console.Console;

import java.awt.image.BufferedImage;

public class Driver {
	public static final int WIDTH = 800, HEIGHT = 600;
	public static JFrame frame = new JFrame(Information.FRAME_NAME);
	public static Panel panel = new Panel(); //the panel
	
	public static void main(String[] args) {
		Runtime.init();
		frame.setSize(WIDTH,HEIGHT);
		frame.setLocation(650,0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setContentPane(panel);
		frame.requestFocus();
		frame.setVisible(true);
		
		ImageIcon icon = new ImageIcon("lib/assets/images/tempplayer.png");
		frame.setIconImage(icon.getImage());
		
		Console.sysLog("Program Initiated In: "+Runtime.getElapsedTimeInMs()+"ms");
		Console.appLog("Version: "+Information.VERSION);
	}
}
