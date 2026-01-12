package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    private Professeur profDyali;		//prof concerned
    private JTable tableExamens;	// tableau visible
    private DefaultTableModel newTableau;	// data dyal tableau
    private JButton btnAjouter, btnResultats, btnSupprimer;		// boutonat

    public ProfesseurGUI(Professeur professeur) {
        super("Espace Professeur");
        this.profDyali = professeur;

        changerHeaderTitre("Tableau de Bord - Dr. " + professeur.getNomComplet());

        // Configuration du panneau central en BorderLayout
        centerPanel.setLayout(new BorderLayout());

        // 1. Initialisation des composants
        initTableau();
        initBoutons(); 

        // 2. Chargement des données
        chargerExamens();

        this.setVisible(true);
    }

    private void initTableau() {
    	
    	
    	
    	// setup dyal titre
        JLabel labelTitre = new JLabel("Vos Examens Créés :");
        labelTitre.setFont(new Font("Arial", Font.BOLD, 16));	//type dyal l5att (font)
        labelTitre.setForeground(primaryColor);		// lown titre b couleur principale
        labelTitre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));	// hadi kat3mlk margins b7al cheghol dyal padding f wordpress
        labelTitre.setVerticalAlignment(JLabel.CENTER);		// Postition
        labelTitre.setHorizontalAlignment(JLabel.CENTER);	// Postition
        
        

        // Hadi array lighanstockiw fiha strings dyal les colonnes dyalna
        String[] colonnes = {"ID", "Titre de l'examen", "Filière", "Niveau"};
        
        
        
        // creer objet dyal tableau invisible ze3ma gher data (3adad les colonnes, 3adad rows kibda 5awi '0')
        newTableau = new DefaultTableModel(colonnes, 0) {
        	
        	
        	// hnaya 7aydna l'acces dyal edit, daba wla tableau read only (bsbab dik return false)
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // tracage dyal tableau (hadi hiya likatrsmlek fel window)
        tableExamens = new JTable(newTableau);
        
        // hadi ze3ma ch7al kbira cell (fle3lo)
        tableExamens.setRowHeight(30);
        
        // header dyal tableau
        tableExamens.getTableHeader().setBackground(primaryColor);
        tableExamens.getTableHeader().setForeground(Color.WHITE);
        
        // hada font type dyal l core dyal tableau
        tableExamens.setFont(new Font("SansSerif", Font.PLAIN, 14));	//.paint ze3ma la forme standard dyal l5att, bla zwa9
        
        // hadi hiya likatzidlk scroll bar,(up, down) + kat5ali l header yban
        JScrollPane scrollPane = new JScrollPane(tableExamens);

        // Ajout au panneau central (Nord et Centre)
        centerPanel.add(labelTitre, BorderLayout.NORTH);	// zid titre f nord dyal central
        centerPanel.add(scrollPane, BorderLayout.CENTER);	// zid tableau f centre dyal centre
    }

    
    
    
    
    
    
    
    
    
    
    
    private void initBoutons() {

    	// creer wa7d panel jdida 5asa bles boutons
    	JPanel panelBoutons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        panelBoutons.setBackground(secondaryColor);

        // creer les objets dyal boutons
        btnAjouter = creerBouton("Créer un Examen", new Color(46, 204, 113)); // 5dar
        btnResultats = creerBouton("Voir les Résultats", new Color(52, 152, 219)); // zra9
        btnSupprimer = creerBouton("Supprimer", new Color(231, 76, 60)); // 7mar 

        // Ajout des boutons au panel interne
        panelBoutons.add(btnAjouter);
        panelBoutons.add(btnResultats);
        panelBoutons.add(btnSupprimer);
        

        
        
        
        


        btnAjouter.addActionListener(new ActionListener() {
        	
            @Override
            public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

                JOptionPane.showMessageDialog(null, "Bientôt: Ouverture de la fenêtre Création");
                
            }
        });
        
        
        
        
        btnResultats.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un examen.");

			}
		});

        btnSupprimer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

	            supprimerExamenSelectionne();
			}
		});




        
        // 3ml dik panel des boutons jdida f south dyal central
        centerPanel.add(panelBoutons, BorderLayout.SOUTH);

        //	activer bouton disconnect
        DisconnectButton btnDisconnect = new DisconnectButton(this);
        
        // dir lbouton fblasto b7al da2iman (south west panel)
        southWestPanel.add(btnDisconnect);
    }
    
    private void supprimerExamenSelectionne() {
        int selectedRow = tableExamens.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un examen à supprimer.");
            return;
        }
        
        int confirmation = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment supprimer cet examen ?", "ATTENTION", JOptionPane.YES_NO_OPTION);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            int idExamen = (int) newTableau.getValueAt(selectedRow, 0);
            try {
                ExamenBD examenBD = new ExamenBD();
                examenBD.supprimerExamen(idExamen);
                chargerExamens(); // Rafraîchir le tableau
                JOptionPane.showMessageDialog(this, "Examen supprimé.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    // Helper pour le style uniforme des boutons
    private JButton creerBouton(String texte, Color bg) {
        JButton btn = new JButton(texte);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        return btn;
    }

    private void chargerExamens() {
        newTableau.setRowCount(0); // Clear
        ExamenBD examenBD = new ExamenBD();
        try {
            ArrayList<Examen> liste = examenBD.recupererParProfesseur(profDyali.getId());
            for (Examen ex : liste) {
                Object[] ligne = {ex.getId(), ex.getTitre(), ex.getFiliere(), ex.getNiveau()};
                newTableau.addRow(ligne);
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