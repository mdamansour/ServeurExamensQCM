package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import database.ExamenBD;
import database.ResultatBD;
import gui.dependencies.BaseWindow;
import gui.dependencies.DisconnectButton;
import modele.Etudiant;
import modele.Examen;
import modele.Resultat;

public class EtudiantGUI extends BaseWindow {

    private Etudiant etudiant;
    
    // Two tables: One for history, one for available exams
    private DefaultTableModel modelResultats;
    private DefaultTableModel modelDispo;
    private JTable tableResultats;
    private JTable tableDispo;

    public EtudiantGUI(Etudiant etudiant) {
        super("Espace Etudiant");
        this.etudiant = etudiant;
        
        changerHeaderTitre("Bienvenue, " + etudiant.getNomComplet());
        
        // Use a GridLayout to show 2 tables (Top: Available, Bottom: History)
        centerPanel.setLayout(new GridLayout(2, 1, 0, 20)); // 2 rows, 1 col, gap

        // 1. Initialize UI sections
        initialiserTableauDispo();
        initialiserTableauResultats();
        
        // 2. Load Data
        chargerDonnees();
        
        // 3. Footer (Disconnect)
        DisconnectButton btnDisconnect = new DisconnectButton(this);
        southWestPanel.add(btnDisconnect);
        
        this.setVisible(true);
    }

    private void initialiserTableauDispo() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Examens disponibles (À passer)"));
        panel.setBackground(secondaryColor);

        String[] cols = {"ID", "Titre", "Professeur", "Questions"};
        modelDispo = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tableDispo = new JTable(modelDispo);
        styleTable(tableDispo);
        
        // Button "Passer l'examen"
        JButton btnPasser = new JButton("PASSER L'EXAMEN SÉLECTIONNÉ");
        btnPasser.setBackground(new Color(46, 204, 113));
        btnPasser.setForeground(Color.WHITE);
        btnPasser.addActionListener(e -> actionPasserExamen());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(secondaryColor);
        btnPanel.add(btnPasser);

        panel.add(new JScrollPane(tableDispo), BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        centerPanel.add(panel);
    }

    private void initialiserTableauResultats() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Historique de vos notes"));
        panel.setBackground(secondaryColor);

        String[] cols = {"Examen ID", "Note / 20", "Date de passage"};
        modelResultats = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tableResultats = new JTable(modelResultats);
        styleTable(tableResultats);
        
        panel.add(new JScrollPane(tableResultats), BorderLayout.CENTER);
        centerPanel.add(panel);
    }
    
    private void styleTable(JTable table) {
        table.setRowHeight(25);
        table.getTableHeader().setBackground(primaryColor);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("SansSerif", Font.PLAIN, 13));
    }

    public void chargerDonnees() {
        modelDispo.setRowCount(0);
        modelResultats.setRowCount(0);
        
        ResultatBD resBD = new ResultatBD();
        ExamenBD examBD = new ExamenBD();

        try {
            // 1. Get History (Results)
            ArrayList<Resultat> mesResultats = resBD.recupererParEtudiant(etudiant.getId());
            HashSet<Integer> examensPassesIDs = new HashSet<>();

            for (Resultat r : mesResultats) {
                examensPassesIDs.add(r.getIdExamen());
                // FIX: Syntax error from previous file corrected here
                modelResultats.addRow(new Object[]{r.getIdExamen(), r.getNote(), r.getDatePassage()});
            }

            // 2. Get Available Exams (Matching Filiere/Niveau)
            ArrayList<Examen> examsPotentiels = examBD.recupererPourEtudiant(etudiant.getFiliere(), etudiant.getNiveau());

            for (Examen ex : examsPotentiels) {
                // FILTER: Only show exams NOT in history
                if (!examensPassesIDs.contains(ex.getId())) {
                    modelDispo.addRow(new Object[]{
                        ex.getId(), 
                        ex.getTitre(), 
                        ex.getProfesseur().getNomComplet(),
                        // Note: Questions might not be loaded, but we can't easily show count without fetching
                        "Disponible"
                    });
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void actionPasserExamen() {
        int row = tableDispo.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un examen à passer.");
            return;
        }

        int idExamen = (int) modelDispo.getValueAt(row, 0);
        String titre = (String) modelDispo.getValueAt(row, 1);
        

        Examen examToPass = new Examen(null, null, null, null);
        examToPass.setId(idExamen);
        examToPass.setTitre(titre);
        

        
        try {
            ExamenBD ebd = new ExamenBD();
            ArrayList<Examen> all = ebd.recupererPourEtudiant(etudiant.getFiliere(), etudiant.getNiveau());
            for(Examen e : all) {
                if(e.getId() == idExamen) {
                    examToPass = e;
                    break;
                }
            }
            
            new PasserExamenGUI(etudiant, examToPass, this);
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Test
        Etudiant eTest = new Etudiant(1, "Ahmed Etudiant", "Genie Info", "M1", "H123456", "ahmed@test.com", "pass");
        new EtudiantGUI(eTest);
    }
}