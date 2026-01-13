package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.EtudiantBD;
import database.ResultatBD;
import gui.dependencies.BaseWindow;
import modele.Etudiant;
import modele.Resultat;

public class ProfResultatsGUI extends BaseWindow {

    private int idExamen;
    private JTable tableResultats;
    private DefaultTableModel model;

    public ProfResultatsGUI(int idExamen, String titreExamen) {
        super("Résultats de l'examen");
        this.idExamen = idExamen;

        // IMPORTANT: When closing this specific window, do not close the whole app
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        changerHeaderTitre("Notes : " + titreExamen);
        
        // Configuration
        centerPanel.setLayout(new BorderLayout());

        initialiserTableau();
        chargerDonnees();
        initialiserBoutons(); 

        this.setVisible(true);
    }

    private void initialiserTableau() {
        // 1. Titre interne
        JLabel labelTitre = new JLabel("Liste des notes des étudiants");
        labelTitre.setFont(new Font("Arial", Font.BOLD, 14));
        labelTitre.setForeground(primaryColor);
        labelTitre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        labelTitre.setHorizontalAlignment(JLabel.CENTER);

        // 2. Configuration du tableau
        String[] colonnes = {"Matricule", "Nom Complet", "Note / 20", "Date de passage"};
        
        model = new DefaultTableModel(colonnes, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tableResultats = new JTable(model);
        tableResultats.setRowHeight(30);
        tableResultats.getTableHeader().setBackground(primaryColor);
        tableResultats.getTableHeader().setForeground(Color.WHITE);
        tableResultats.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JScrollPane scrollPane = new JScrollPane(tableResultats);

        centerPanel.add(labelTitre, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void chargerDonnees() {
        // Vider le tableau
        model.setRowCount(0);

        ResultatBD resultatBD = new ResultatBD();
        EtudiantBD etudiantBD = new EtudiantBD();

        // 1. Récupérer tous les résultats pour cet examen
        ArrayList<Resultat> listeResultats = resultatBD.recupererParExamen(this.idExamen);

        // 2. Pour chaque résultat, on doit trouver le nom de l'étudiant
        for (Resultat res : listeResultats) {
            try {
                // On utilise l'ID stocké dans le resultat pour trouver les infos de l'étudiant
                Etudiant etudiant = etudiantBD.trouverParID(res.getIdEtudiant());
                
                if (etudiant != null) {
                    Object[] ligne = {
                        etudiant.getMatricule(),
                        etudiant.getNomComplet(),
                        res.getNote(),
                        res.getDatePassage()
                    };
                    model.addRow(ligne);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void initialiserBoutons() {
        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelBas.setBackground(secondaryColor);

        // --- EXPORT BUTTON ---
        JButton btnExport = new JButton("Exporter en CSV");
        btnExport.setBackground(new Color(52, 152, 219)); // Blue
        btnExport.setForeground(Color.WHITE);
        btnExport.setFocusPainted(false);
        btnExport.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnExport.addActionListener(e -> exporterEnCSV());

        // --- CLOSE BUTTON ---
        JButton btnFermer = new JButton("Fermer");
        btnFermer.setBackground(new Color(231, 76, 60)); // Red
        btnFermer.setForeground(Color.WHITE);
        btnFermer.setFocusPainted(false);
        btnFermer.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnFermer.addActionListener(e -> this.dispose()); // Ferme uniquement cette fenêtre

        panelBas.add(btnExport);
        panelBas.add(btnFermer);
        
        centerPanel.add(panelBas, BorderLayout.SOUTH);
    }

    private void exporterEnCSV() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Aucune donnée à exporter.", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer les résultats");
        fileChooser.setSelectedFile(new File("Resultats_Examen_" + idExamen + ".csv"));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            
            // Ensure extension is .csv
            if (!fileToSave.getAbsolutePath().endsWith(".csv")) {
                fileToSave = new File(fileToSave.getAbsolutePath() + ".csv");
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileToSave))) {
                
                // 1. Write Headers (Using ; as separator for Excel French/European compatibility)
                bw.write("Matricule;Nom Complet;Note / 20;Date de passage");
                bw.newLine();

                // 2. Iterate through JTable rows
                for (int i = 0; i < model.getRowCount(); i++) {
                    String matricule = (String) model.getValueAt(i, 0);
                    String nom = (String) model.getValueAt(i, 1);
                    Double note = (Double) model.getValueAt(i, 2);
                    String date = (String) model.getValueAt(i, 3);

                    // We use ; as delimiter. 
                    // Quotes around strings handle cases where data might contain ;
                    // %.2f will use the local decimal separator (likely comma in FR), which works fine with ; delimiter
                    String line = String.format("%s;\"%s\";%.2f;%s", 
                        matricule, nom, note, date);
                    
                    bw.write(line);
                    bw.newLine();
                }

                JOptionPane.showMessageDialog(this, 
                    "Exportation réussie !\nFichier : " + fileToSave.getAbsolutePath());

            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de l'exportation : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}