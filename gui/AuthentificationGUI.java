package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AuthentificationGUI extends JFrame {
	
	JPanel header, footer;
	Dimension screenSize;
	
	
	public AuthentificationGUI () {
		
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
		
		int x = screenSize.width;
		int y = screenSize.height;

		this.setTitle("This is the title of the window");
		this.setSize(x/2, y/2);		//	determine the dimensions of the window in function of width and hight
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
        new AuthentificationGUI();
    }
}