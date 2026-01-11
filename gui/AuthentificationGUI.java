package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

// CHANGE: extend BaseWindow instead of JFrame
public class AuthentificationGUI extends BaseWindow {
	 	private JButton buttonSubmit;
	    private JLabel labelPrompt, labelEmail, labelPassword;
	    private JTextField fieldEmail, fieldPassword;

	    
	    
	    
    public AuthentificationGUI() {
    	
        super("Authentification - Serveur QCM");
        
        // change the titre of the header bdik fonction likayn f mother class
        changerHeaderTitre("Connextion");
        
        
        
        
        // creating elements of the page
        labelPrompt = new JLabel("Veuillez entrer vos coordonnées");
        labelPrompt.setFont(new Font("SansSerif", Font.BOLD, 12));
        labelPrompt.setForeground(Color.BLACK);
        labelPrompt.setHorizontalAlignment(JLabel.CENTER);
        labelPrompt.setVerticalAlignment(JLabel.BOTTOM);
        
        labelEmail = new JLabel("Email:");
        labelEmail.setForeground(Color.BLACK);
        labelEmail.setHorizontalAlignment(JLabel.CENTER);
        labelEmail.setVerticalAlignment(JLabel.CENTER);
        
        fieldEmail = new JTextField();

        labelPassword = new JLabel("Mot de Passe:");
        labelPassword.setForeground(Color.BLACK);
        labelPassword.setHorizontalAlignment(JLabel.CENTER);
        labelPassword.setVerticalAlignment(JLabel.CENTER);
        
        fieldPassword = new JTextField();
        
        buttonSubmit = new JButton("Connexion");
        
        
        // main bpdy creation
        centerPanel = new JPanel(new GridLayout(5, 5, 10, 50)); // 9asmna hnaya cetner panel 3la 3x3 grid
        
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
        centerPanel.add(labelPrompt);
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
        
        centerPanel.add(new JLabel(""));
        centerPanel.add(labelEmail);
        centerPanel.add(fieldEmail);
        centerPanel.add(new JLabel(""));	// hadi zidnaha ghir bach na9zo 3la wa7d l5awiya
        centerPanel.add(new JLabel(""));
        
        centerPanel.add(new JLabel(""));
        centerPanel.add(labelPassword);
        centerPanel.add(fieldPassword);
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
        
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
        centerPanel.add(buttonSubmit);
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
        
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
        centerPanel.add(new JLabel(""));
        
        this.add(centerPanel, BorderLayout.CENTER);

        
        
        
        
        

        this.setVisible(true);
    }
 
    public static void main(String[] args) {
        new AuthentificationGUI();
    }
}