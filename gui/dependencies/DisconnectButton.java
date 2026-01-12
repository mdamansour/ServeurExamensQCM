package gui.dependencies; // Adjust package as needed

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import gui.AuthentificationGUI;

public class DisconnectButton extends JButton {

    // We accept the 'parentFrame' so we know which window to close
    public DisconnectButton(JFrame parentFrame) {
        super("Déconnexion");

        // 1. Apply the standard styling (Matching your 'creerBouton' style)
        this.setBackground(Color.GRAY);
        this.setForeground(Color.WHITE);
        this.setFocusPainted(false);
        this.setFont(new Font("SansSerif", Font.BOLD, 12));

        // 2. Add the action listener
        this.addActionListener(e -> {
            // Close the specific window passed to this button
            if (parentFrame != null) {
                parentFrame.dispose();
            }
            // Open the login screen
            new AuthentificationGUI();
        });
    }
}