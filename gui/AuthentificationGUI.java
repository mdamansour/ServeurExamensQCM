package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import database.AuthentificationBD;
import modele.Etudiant;
import modele.Professeur;

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
        
     // --- LOGIQUE D'AUTHENTIFICATION (NOUVEAU) ---
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                traiterConnexion();
            }
        });
        
        
        
        
        
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
    
    
    private void traiterConnexion() {
    	String email = fieldEmail.getText();
        String password = fieldPassword.getText();
        
        if(email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Appeler le Backend
        AuthentificationBD authBD = new AuthentificationBD();
        Object utilisateur = authBD.utilisateur(email, password);

        // 3. Vérifier le résultat
        if (utilisateur == null) {
            // Echec
            JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.", "Echec de connexion", JOptionPane.ERROR_MESSAGE);
        } else {
            // Succès
            this.dispose(); // Fermer la fenêtre de login
            
            if (utilisateur instanceof Etudiant) {
                Etudiant etudiant = (Etudiant) utilisateur;
                System.out.println("Succès : Connexion Etudiant -> " + etudiant.getNomComplet());
                JOptionPane.showMessageDialog(this, "Bienvenue Etudiant : " + etudiant.getNomComplet());
                // TODO: new EspaceEtudiantGUI(etudiant);
                
            } else if (utilisateur instanceof Professeur) {
                Professeur prof = (Professeur) utilisateur;
                System.out.println("Succès : Connexion Professeur -> " + prof.getNomComplet());
                JOptionPane.showMessageDialog(this, "Bienvenue Professeur : " + prof.getNomComplet());
                // TODO: new EspaceProfesseurGUI(prof);
            }
        }
    }
    
    
 
    public static void main(String[] args) {
        new AuthentificationGUI();
    }
}