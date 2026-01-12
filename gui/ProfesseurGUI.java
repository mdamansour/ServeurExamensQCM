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

        // initialisation boutons w tableau
        initialiserTableau();
        initialiserBoutons(); 

        // 2. Chargement des données
        chargerExamens();

        this.setVisible(true);
    }

    private void initialiserTableau() {
    	
    	
    	
    	// setup dyal titre
        JLabel labelTitre = new JLabel("Vos Examens Créés :");
        labelTitre.setFont(new Font("Arial", Font.BOLD, 16));	//type dyal l5att (font)
        labelTitre.setForeground(primaryColor);		// lown titre b couleur principale
        labelTitre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));	// hadi kat3mlk margins b7al cheghol dyal padding f wordpress
        labelTitre.setVerticalAlignment(JLabel.CENTER);		// Postition
        labelTitre.setHorizontalAlignment(JLabel.CENTER);	// Postition
        
        

        // Hadi array lighanstockiw fiha strings dyal les colonnes dyalna
        String[] colonnes = {"ID", "Titre de l'examenamen", "Filière", "Niveau"};
        
        
        
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

    
    
    
    
    
    
    
    
    
    
    
    private void initialiserBoutons() {

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
    	
    	
    	
        int selectedExam = tableExamens.getSelectedRow();
        
        
        if (selectedExam == -1) { // par default -1 kat3ni 7ta chi row ma mselectionner
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un examen à supprimer.");
            return;
        }
        
        
        
        int confirmation = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment supprimer cet examen ?", "ATTENTION", JOptionPane.YES_NO_OPTION);	// wach m2akd?
        
        if (confirmation == JOptionPane.YES_OPTION) {		// aaah wellahila m2aaaaked
        	
            int idExamen = (int) newTableau.getValueAt(selectedExam, 0);	//jbed id dyal examenam mn tableau dyal data (li invisible machi drsim) wdiro f idExamen
            try {
                ExamenBD examenamenBD = new ExamenBD();
                examenamenBD.supprimerExamen(idExamen);		// passer id dyal dik examenam lighanmes7o l examenamdb.supprimerExamen
                chargerExamens(); // actualiser tableau
                JOptionPane.showMessageDialog(null, "Examen supprimé.");
            } catch (SQLException examen) {
                examen.printStackTrace();
            }
        }
    }
    
    // hadi ghir methode bach ncreeiw nfs boutton bdes paramtres mbdlin
    private JButton creerBouton(String texamente, Color bg) {
        JButton btn = new JButton(texamente);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        return btn;
    }

    private void chargerExamens() {
        newTableau.setRowCount(0); // msa7 rows likaynin f tablea kaaaaamlin
        ExamenBD examenBD = new ExamenBD();
        try {
            ArrayList<Examen> liste = examenBD.recupererParProfesseur(profDyali.getId());	//wjd array lighadi tsejjel fiha les examenams dyal had prof
            for (Examen examen : liste) {
                Object[] ligne = {examen.getId(), examen.getTitre(), examen.getFiliere(), examen.getNiveau()};	// le type objet 7itach un examen est un objet w expected parametre a un type objet
                newTableau.addRow(ligne);	//3amar had row f newTableau
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) {
        // Test
        Professeur p = new Professeur(2, "Test Prof", "Info", "email", "pass");
        new ProfesseurGUI(p);
    }
    
    
    
    
    
    
}