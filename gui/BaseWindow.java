package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public abstract class BaseWindow extends JFrame {
	
	// declaration of zones of our application
    protected JPanel northPanel, southPanel;
    protected JPanel northEastPanel, northWestPanel, northCenterPanel;
    protected JPanel southEastPanel, southWestPanel, southCenterPanel;
    
    // declared a custom color
    protected final Color primaryColor = new Color(44, 62, 80);    // Midnight Blue
    protected final Color secondaryColor = new Color(236, 240, 241); // Cloud Gray

    public BaseWindow(String title) {
    	
        this.setTitle(title);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // sets the color of the main body of the application to our secondary custom color
        this.getContentPane().setBackground(secondaryColor);
        
        
        // retreive screen dimensions
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width * 2 / 3, (screenSize.height * 2 / 3));
        this.setLocationRelativeTo(null);

        // setting up main layout
        this.setLayout(new BorderLayout());
        
        // hada ki initialise ga3 les panneaux
        northPanel = new JPanel(new BorderLayout());
        southPanel = new JPanel(new BorderLayout());

        northEastPanel = new JPanel(new BorderLayout());
        northWestPanel = new JPanel(new BorderLayout());
        northCenterPanel = new JPanel(new BorderLayout());
        
        southEastPanel = new JPanel(new BorderLayout());
        southWestPanel = new JPanel(new BorderLayout());
        southCenterPanel = new JPanel(new BorderLayout());

        // da5al had pannaux f north panel
        northPanel.add(northEastPanel, BorderLayout.EAST);
        northPanel.add(northWestPanel, BorderLayout.WEST);
        northPanel.add(northCenterPanel, BorderLayout.CENTER);
        
        // da5al hadom f south pannaux
        southPanel.add(southEastPanel, BorderLayout.EAST);
        southPanel.add(southWestPanel, BorderLayout.WEST);
        southPanel.add(southCenterPanel, BorderLayout.CENTER);

        // zid had l objects f blasthom 
        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);

        // customization of north and south panels
        northPanel.setPreferredSize(new Dimension(0, 50));
        southPanel.setPreferredSize(new Dimension(0, 50));

        // dir l couleur primaire f ga3 les zones for now
        JPanel[] barPanels = {
            northEastPanel, northWestPanel, northCenterPanel,
            southEastPanel, southWestPanel, southCenterPanel
        };

        for (JPanel p : barPanels) {
            p.setBackground(primaryColor);
            p.setPreferredSize(new Dimension(200, 0)); 
        }
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        /*----------------------- Text manipulation ---------------- */
        

        
        // text in ffooter
        JLabel footerText = new JLabel("2025/2026 Université Sidi Mohamed Ben Abdellah - Fes");	//text dyal lfooter, t9dar tbdlo bach mabghiti
        footerText.setFont(new Font("SansSerif", Font.ITALIC, 12));
        footerText.setForeground(Color.WHITE);
        footerText.setHorizontalAlignment(JLabel.CENTER);
        southCenterPanel.add(footerText, BorderLayout.CENTER);
        
        
        
        
        
        
        
        
        
        
        
        
        
    }
    
    public void changerHeaderTitre (String text) {
        // Title of the window
        JLabel headerText = new JLabel(text);
        headerText.setFont(new Font("Arial", Font.BOLD, 16));
        headerText.setForeground(Color.WHITE);
        headerText.setHorizontalAlignment(JLabel.CENTER);
        northCenterPanel.add(headerText, BorderLayout.CENTER);
    }
    
    
}