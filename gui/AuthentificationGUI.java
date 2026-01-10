package gui;

import javax.swing.*;
import java.awt.*;

public class AuthentificationGUI extends JFrame {

    // Composants globaux pour y accéder plus tard dans les Listeners
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public AuthentificationGUI() {
        // 1. Configuration de la fenêtre principale
        setTitle("Application de Gestion des QCM - Connexion");
        setSize(500, 400); // Taille initiale
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer à l'écran
        setLayout(new BorderLayout()); // Layout principal: Nord, Sud, Centre

        // 2. Construction des parties
        initHeader();
        initCenter();
        initFooter();
        
        // 3. Rendre visible
        setVisible(true);
    }

    /**
     * Partie HAUTE : Le titre et le logo (simulé par couleur)
     */
    private void initHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(41, 128, 185)); // Bleu professionnel
        headerPanel.setPreferredSize(new Dimension(500, 80));
        headerPanel.setLayout(new GridBagLayout()); // Pour centrer le texte

        JLabel titleLabel = new JLabel("Gestion des Examens QCM");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        headerPanel.add(titleLabel);
        
        // Ajout au Nord du BorderLayout principal
        this.add(headerPanel, BorderLayout.NORTH);
    }

    /**
     * Partie CENTRALE : Le formulaire de connexion
     */
    private void initCenter() {
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(236, 240, 241)); // Gris très clair
        centerPanel.setLayout(new GridBagLayout()); // Le meilleur layout pour les formulaires
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Marges entre les éléments
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // -- Label Email --
        JLabel emailLabel = new JLabel("Email Universitaire :");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; 
        gbc.gridy = 0;
        centerPanel.add(emailLabel, gbc);

        // -- Champ Email --
        emailField = new JTextField(20);
        gbc.gridx = 1; 
        gbc.gridy = 0;
        centerPanel.add(emailField, gbc);

        // -- Label Mot de passe --
        JLabel passLabel = new JLabel("Mot de passe :");
        passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; 
        gbc.gridy = 1;
        centerPanel.add(passLabel, gbc);

        // -- Champ Mot de passe --
        passwordField = new JPasswordField(20);
        gbc.gridx = 1; 
        gbc.gridy = 1;
        centerPanel.add(passwordField, gbc);

        // -- Bouton Connexion --
        loginButton = new JButton("Se Connecter");
        loginButton.setBackground(new Color(39, 174, 96)); // Vert "Succès"
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        
        gbc.gridx = 0; 
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Le bouton prend toute la largeur
        gbc.fill = GridBagConstraints.NONE; // Ne pas étirer le bouton
        gbc.anchor = GridBagConstraints.CENTER; // Centrer le bouton
        centerPanel.add(loginButton, gbc);

        // Ajout au Centre du BorderLayout principal
        this.add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * Partie BASSE : Copyright et version
     */
    private void initFooter() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY)); // Ligne grise au dessus
        
        JLabel copyLabel = new JLabel("© 2026 Université Sidi Mohamed Ben Abdellah - Fes");
        copyLabel.setFont(new Font("SansSerif", Font.ITALIC, 10));
        copyLabel.setForeground(Color.GRAY);
        
        footerPanel.add(copyLabel);

        // Ajout au Sud du BorderLayout principal
        this.add(footerPanel, BorderLayout.SOUTH);
    }

    // Main pour tester visuellement le layout
    public static void main(String[] args) {
        // Utiliser le "Look and Feel" du système pour que ça soit plus joli (style Windows)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            new AuthentificationGUI();
        });
    }
}