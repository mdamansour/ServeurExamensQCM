package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.ExamenBD;
import gui.dependencies.BaseWindow;
import gui.dependencies.DisconnectButton;
import modele.Examen;
import modele.Professeur;

public class ProfesseurGUI extends BaseWindow {

    private Professeur professeurConnecte;
    private JTable tableExamens;
    private DefaultTableModel tableModel;
    private JButton btnAjouter, btnResultats, btnSupprimer, btnDeconnexion;

    public ProfesseurGUI(Professeur professeur) {
        super("Espace Professeur");
        this.professeurConnecte = professeur;

        changerHeaderTitre("Tableau de Bord - Dr. " + professeur.getNomComplet());

        // Configuration du panneau central en BorderLayout
	        // NORTH: Titre
	        // CENTER: Tableau
	        // SOUTH: Boutons d'action
        centerPanel.setLayout(new BorderLayout());

        // 1. Initialisation des composants
        initTableau();
        initBoutons(); 

        // 2. Chargement des données
        chargerExamens();

        this.setVisible(true);
    }

    private void initTableau() {
        // --- LE TITRE (HAUT) ---
        JLabel labelTitre = new JLabel("Vos Examens Créés :");
        labelTitre.setFont(new Font("Arial", Font.BOLD, 16));
        labelTitre.setForeground(primaryColor);
        labelTitre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelTitre.setVerticalAlignment(JLabel.CENTER);
        labelTitre.setHorizontalAlignment(JLabel.CENTER);

        // --- LE TABLEAU (CENTRE) ---
        String[] colonnes = {"ID", "Titre de l'examen", "Filière", "Niveau"};
        
        tableModel = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableExamens = new JTable(tableModel);
        tableExamens.setRowHeight(30);
        tableExamens.getTableHeader().setBackground(primaryColor);
        tableExamens.getTableHeader().setForeground(Color.WHITE);
        tableExamens.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(tableExamens);

        // Ajout au panneau central (Nord et Centre)
        centerPanel.add(labelTitre, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void initBoutons() {
        JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelBoutons.setBackground(secondaryColor);

        // Standard buttons
        btnAjouter = creerBouton("Créer un Examen", new Color(46, 204, 113));
        btnResultats = creerBouton("Voir les Résultats", new Color(52, 152, 219)); 
        btnSupprimer = creerBouton("Supprimer", new Color(231, 76, 60)); 


        
        
        // We pass 'this' because ProfesseurGUI is the window we want to close
        DisconnectButton btnDeconnexion = new DisconnectButton(this);
        southWestPanel.add(btnDeconnexion); // Added to the footer area

        
        
        
        
        // --- Actions for local buttons ---
        btnAjouter.addActionListener(e -> JOptionPane.showMessageDialog(this, "Bientôt..."));
        
        btnResultats.addActionListener(e -> {
            // ... (your existing code) ...
        });

        btnSupprimer.addActionListener(e -> supprimerExamenSelectionne());
        
        // NOTE: We do NOT need to add an ActionListener for btnDeconnexion here.
        // The class handles it internally.

        // Add to panels
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnResultats);
        panelBoutons.add(btnSupprimer);
        

        centerPanel.add(panelBoutons, BorderLayout.SOUTH);
    }
    
    
    
    
    
    
    
    
    
    
    // Logique de suppression extraite pour la lisibilité
    private void supprimerExamenSelectionne() {
        int selectedRow = tableExamens.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un examen à supprimer.");
            return;
        }
        
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment supprimer cet examen ?", "ATTENTION", JOptionPane.YES_NO_OPTION);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            int idExamen = (int) tableModel.getValueAt(selectedRow, 0);
            try {
                ExamenBD examenBD = new ExamenBD();
                examenBD.supprimerExamen(idExamen);
                chargerExamens(); // Rafraîchir
                JOptionPane.showMessageDialog(this, "Examen supprimé.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private JButton creerBouton(String texte, Color bg) {
        JButton btn = new JButton(texte);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        return btn;
    }

    private void chargerExamens() {
        tableModel.setRowCount(0); // Clear
        ExamenBD examenBD = new ExamenBD();
        try {
            ArrayList<Examen> liste = examenBD.recupererParProfesseur(professeurConnecte.getId());
            for (Examen ex : liste) {
                Object[] ligne = {ex.getId(), ex.getTitre(), ex.getFiliere(), ex.getNiveau()};
                tableModel.addRow(ligne);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Test
        Professeur p = new Professeur(1, "Test Prof", "Info", "email", "pass");
        new ProfesseurGUI(p);
    }
}