package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dao.EtudiantDAO;
import dao.ProfesseurDAO;
import modele.Etudiant;
import modele.Professeur;

public class FenetreConnexion extends JFrame {

    private JTextField txtNom;
    private JComboBox<String> comboRole; // Choix : Étudiant ou Professeur
    private JComboBox<String> comboAction; // Choix : Connexion ou Inscription

    // Champs supplémentaires pour l'inscription
    private JTextField txtFiliere;
    private JTextField txtNiveau; // Ou Specialité pour le prof
    private JPanel panelDetails; // Le panneau qui change selon l'action

    public FenetreConnexion() {
        // 1. Configuration de la fenêtre
        setTitle("Gestion des Examens QCM - Connexion");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrer à l'écran
        setLayout(new BorderLayout());

        // 2. Titre
        JLabel lblTitre = new JLabel("Bienvenue", JLabel.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitre.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(lblTitre, BorderLayout.NORTH);

        // 3. Formulaire central
        JPanel panelForm = new JPanel(new GridLayout(6, 1, 10, 10)); // Grille verticale
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

        // Rôle (Qui êtes-vous ?)
        comboRole = new JComboBox<>(new String[]{"Étudiant", "Professeur"});
        panelForm.add(new JLabel("Je suis :"));
        panelForm.add(comboRole);

        // Action (Login ou Inscription ?)
        comboAction = new JComboBox<>(new String[]{"Se connecter", "S'inscrire"});
        panelForm.add(new JLabel("Action :"));
        panelForm.add(comboAction);

        // Nom
        txtNom = new JTextField();
        panelForm.add(new JLabel("Nom Complet :"));
        panelForm.add(txtNom);

        add(panelForm, BorderLayout.CENTER);

        // 4. Panneau du bas (Bouton + détails dynamiques)
        JPanel panelBas = new JPanel(new BorderLayout());
        
        // Sous-panneau pour les détails (caché par défaut pour la connexion)
        panelDetails = new JPanel(new GridLayout(4, 1));
        panelDetails.setBorder(BorderFactory.createEmptyBorder(0, 40, 10, 40));
        
        txtFiliere = new JTextField();
        txtNiveau = new JTextField(); // Servira de Spécialité pour le prof
        
        panelDetails.add(new JLabel("Filière (Etudiant) / Spécialité (Prof) :"));
        panelDetails.add(txtFiliere);
        panelDetails.add(new JLabel("Niveau (Ex: M1) :"));
        panelDetails.add(txtNiveau);
        
        panelDetails.setVisible(false); // Caché au départ
        panelBas.add(panelDetails, BorderLayout.NORTH);

        JButton btnValider = new JButton("Valider");
        btnValider.setFont(new Font("Arial", Font.BOLD, 14));
        // Marge autour du bouton
        JPanel btnPanel = new JPanel(); 
        btnPanel.add(btnValider);
        panelBas.add(btnPanel, BorderLayout.SOUTH);

        add(panelBas, BorderLayout.SOUTH);

        // --- INTERACTION (EVENTS) ---

        // Quand on change "Connexion" <-> "Inscription"
        comboAction.addActionListener(e -> {
            String action = (String) comboAction.getSelectedItem();
            if (action.equals("S'inscrire")) {
                panelDetails.setVisible(true); // Montrer les champs extra
                setSize(450, 500); // Agrandir la fenêtre
            } else {
                panelDetails.setVisible(false); // Cacher
                setSize(450, 350); // Rétrécir
            }
        });
        
        // Quand on change "Prof" <-> "Etudiant" (pour adapter les labels)
        comboRole.addActionListener(e -> {
             updateLabels();
        });

        // Click sur le bouton
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                traiterAction();
            }
        });
    }
    
    // Change les textes selon le rôle choisi
    private void updateLabels() {
        String role = (String) comboRole.getSelectedItem();
        if(role.equals("Professeur")) {
            // Le label 0 du panelDetails est "Filière..."
            ((JLabel)panelDetails.getComponent(0)).setText("Spécialité :");
            // On cache le niveau pour le prof car il n'en a pas besoin
            panelDetails.getComponent(2).setVisible(false); 
            txtNiveau.setVisible(false);
        } else {
            ((JLabel)panelDetails.getComponent(0)).setText("Filière :");
            panelDetails.getComponent(2).setVisible(true);
            txtNiveau.setVisible(true);
        }
    }

    // Le Cœur du système : Appel aux DAO
    private void traiterAction() {
        String role = (String) comboRole.getSelectedItem();
        String action = (String) comboAction.getSelectedItem();
        String nom = txtNom.getText().trim();

        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire !", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (role.equals("Étudiant")) {
            EtudiantDAO dao = new EtudiantDAO();
            
            if (action.equals("Se connecter")) {
                Etudiant etu = dao.trouverParNom(nom);
                if (etu != null) {
                    JOptionPane.showMessageDialog(this, "Bienvenue " + etu.getNomComplet());
                    // TODO : Ouvrir le Dashboard Etudiant
                    // new DashboardEtudiant(etu).setVisible(true);
                    // this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Étudiant inconnu.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Inscription
                String filiere = txtFiliere.getText();
                String niveau = txtNiveau.getText();
                Etudiant nouveau = new Etudiant(nom, filiere, niveau);
                dao.sauvegarderEtudiant(nouveau);
                JOptionPane.showMessageDialog(this, "Compte créé ! Connectez-vous maintenant.");
                comboAction.setSelectedItem("Se connecter"); // Basculer auto vers login
            }
        } 
        else if (role.equals("Professeur")) {
            ProfesseurDAO dao = new ProfesseurDAO();
            
            if (action.equals("Se connecter")) {
                Professeur prof = dao.trouverParNom(nom);
                if (prof != null) {
                    JOptionPane.showMessageDialog(this, "Bienvenue Prof. " + prof.getNomComplet());
                 // Dans FenetreConnexion.java :

                    if (prof != null) {
                        JOptionPane.showMessageDialog(this, "Bienvenue Prof. " + prof.getNomComplet());
                        
                        // OUVERTURE DU DASHBOARD
                        new DashboardProfesseur(prof).setVisible(true);
                        this.dispose(); // Ferme la fenêtre de connexion
                    }
                    // new DashboardProfesseur(prof).setVisible(true);
                    // this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Professeur inconnu.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Inscription Prof (utilise txtFiliere comme Spécialité)
                String specialite = txtFiliere.getText(); 
                Professeur nouveau = new Professeur(nom, specialite);
                dao.sauvegarderProfesseur(nouveau);
                JOptionPane.showMessageDialog(this, "Compte Prof créé !");
                comboAction.setSelectedItem("Se connecter");
            }
        }
    }

    // Main pour tester juste cette fenêtre
    public static void main(String[] args) {
        // Style Windows ou Mac (plus joli)
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
        
        SwingUtilities.invokeLater(() -> {
            new FenetreConnexion().setVisible(true);
        });
    }
}