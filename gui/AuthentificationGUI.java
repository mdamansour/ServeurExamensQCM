package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AuthentificationGUI extends JFrame {
	
	JPanel northPanel, southPanel;
	JPanel northEastPanel, northWestPanel, northCenterPanel;
	JPanel southEastPanel, southWestPanel, southCenterPanel;
	
	Dimension screenSize;
	
	
	public AuthentificationGUI () {
		this.setTitle("This is the title of the window");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		
		//elements for screen dimesions
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = screenSize.width;
		int y = screenSize.height;
		this.setSize(x/2, (y*2/3));		//	determine the dimensions of the window in function of width and hight
		this.setLocationRelativeTo(null);	//Set the position of the window relative to nothing (center)
		
		//creating layout containers
		this.setLayout(new BorderLayout());		// intialises the 5 zones (nord, south...)
		northPanel = new JPanel();		//creates a new container (blasa 5awiya bach t3amarha mn ba3d)
		southPanel = new JPanel();		//south panel container
		
		// creating border layouts
		northPanel.setLayout(new BorderLayout());
		southPanel.setLayout(new BorderLayout());
		
		//creating sub-layout containers
		northEastPanel = new JPanel();
		northWestPanel = new JPanel();
		northCenterPanel = new JPanel();
		southEastPanel = new JPanel();
		southWestPanel = new JPanel();
		southCenterPanel = new JPanel();
		
		
		// creating border layouts for sub-containers
		northEastPanel.setLayout(new BorderLayout());
		northWestPanel.setLayout(new BorderLayout());
		northCenterPanel.setLayout(new BorderLayout());
		southEastPanel.setLayout(new BorderLayout());
		southWestPanel.setLayout(new BorderLayout());
		southCenterPanel.setLayout(new BorderLayout());
		
		
		// add sub containers to parent containers
		northPanel.add(northEastPanel, BorderLayout.EAST);
		northPanel.add(northWestPanel, BorderLayout.WEST);
		northPanel.add(northCenterPanel, BorderLayout.CENTER);
		southPanel.add(southEastPanel, BorderLayout.EAST);
		southPanel.add(southWestPanel, BorderLayout.WEST);
		southPanel.add(southCenterPanel, BorderLayout.CENTER);
		
		// add parent containers to the main container
		this.add(northPanel, BorderLayout.NORTH);
		this.add(southPanel, BorderLayout.SOUTH);
		
		
		
		
		
		
		// customizing each zone
			//North and South
		northPanel.setPreferredSize(new Dimension(0, 50));
		southPanel.setPreferredSize(new Dimension(0, 50));
		
			// East West and Center
		northEastPanel.setPreferredSize(new Dimension(200, 0));
		northEastPanel.setBackground(Color.green);
		
		northWestPanel.setPreferredSize(new Dimension(200, 0));
		northWestPanel.setBackground(Color.blue);
		
		northCenterPanel.setPreferredSize(new Dimension(200, 0));
		northCenterPanel.setBackground(Color.yellow);
		
		southEastPanel.setPreferredSize(new Dimension(200, 0));
		southEastPanel.setBackground(Color.red);
		
		southWestPanel.setPreferredSize(new Dimension(200, 0));
		southWestPanel.setBackground(Color.orange);
		
		southCenterPanel.setPreferredSize(new Dimension(200, 0));
		southCenterPanel.setBackground(Color.gray);
		
		
		
		
		// make the window visible
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
        new AuthentificationGUI();
    }
}