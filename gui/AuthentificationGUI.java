package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AuthentificationGUI extends JFrame {
	
	JPanel header, footer;
	Dimension screenSize;
	
	
	public AuthentificationGUI () {
		this.setTitle("This is the title of the window");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		
		//elements for screen dimesions
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width;
		int y = screenSize.height;
		this.setSize(x/2, y/2);		//	determine the dimensions of the window in function of width and hight
		this.setLocationRelativeTo(null);	//Set the position of the window relative to nothing (center)
		
		this.setLayout(new BorderLayout());
		
		
		// make the window visible
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
        new AuthentificationGUI();
    }
}