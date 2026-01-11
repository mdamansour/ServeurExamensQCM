package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

// CHANGE: extend BaseWindow instead of JFrame
public class AuthentificationGUI extends BaseWindow {

    public AuthentificationGUI() {
    	
        super("Authentification - Serveur QCM");
        
        
        
        
        
        changerHeaderTitre("Connextion");
        this.setVisible(true);
    }
 
    public static void main(String[] args) {
        new AuthentificationGUI();
    }
}