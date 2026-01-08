package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import dao.ExamenDAO;
import dao.ResultatDAO;
import modele.Etudiant;
import modele.Examen;

public class DashboardEtudiant extends JFrame {

    private Etudiant etudiant;
    private DefaultTableModel modelExamens;
    private DefaultTableModel modelHistorique;
    private JTable tableExamens;
    private JTable tableHistorique;

    public DashboardEtudiant(Etudiant etu) {
        this.etudiant = etu;

        setTitle("Espace Étudiant - " + etudiant.getNomComplet());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("🎓 Passer un Examen", createPanelExamens());
        tabs.addTab("📄 Mes Notes", createPanelHistorique());
        add(tabs);
        
        // Charger les données dès l'ouverture
        rafraichirDonnees();
    }

    private JPanel createPanelExamens() {
        JPanel panel = new JPanel(new BorderLayout());
        
        JLabel lblInfo = new JLabel("Examens disponibles pour " + etudiant.getFiliere() + " (" + etudiant.getNiveau() + ")", JLabel.CENTER);
        lblInfo.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        panel.add(lblInfo, BorderLayout.NORTH);

        // Tableau des examens dispos
        String[] cols = {"ID", "Titre", "Points Max"};
        modelExamens = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Non éditable
        };
        tableExamens = new JTable(modelExamens);
        panel.add(new JScrollPane(tableExamens), BorderLayout.CENTER);

        // Bouton Go
        JButton btnGo = new JButton("▶️ PASSER L'EXAMEN SÉLECTIONNÉ");
        btnGo.setFont(new Font("Arial", Font.BOLD, 14));
        btnGo.setBackground(new Color(46, 204, 113));
        btnGo.setForeground(Color.WHITE);
        
        btnGo.addActionListener(e -> actionPasserExamen());
        
        panel.add(btnGo, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createPanelHistorique() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Tableau Historique
        String[] cols = {"Détails"}; // On utilise la méthode getHistoriqueEtudiant qui renvoie des Strings
        modelHistorique = new DefaultTableModel(cols, 0);
        tableHistorique = new JTable(modelHistorique);
        
        panel.add(new JScrollPane(tableHistorique), BorderLayout.CENTER);
        
        JButton btnRefresh = new JButton("🔄 Rafraîchir");
        btnRefresh.addActionListener(e -> rafraichirDonnees());
        panel.add(btnRefresh, BorderLayout.SOUTH);
        
        return panel;
    }

    private void rafraichirDonnees() {
        // 1. Charger Examens Dispos
        ExamenDAO examDao = new ExamenDAO();
        ArrayList<Examen> dispos = examDao.getExamensDisponibles(etudiant.getNiveau(), etudiant.getFiliere());
        
        modelExamens.setRowCount(0);
        for(Examen e : dispos) {
            // Astuce : On ne charge pas les questions ici pour aller vite, on affiche juste titre
            modelExamens.addRow(new Object[]{e.getId(), e.getTitre(), "?"});
        }

        // 2. Charger Historique
        ResultatDAO resDao = new ResultatDAO();
        ArrayList<String> hist = resDao.getHistoriqueEtudiant(etudiant.getId());
        modelHistorique.setRowCount(0);
        for(String s : hist) {
            modelHistorique.addRow(new Object[]{s});
        }
    }

    private void actionPasserExamen() {
        int row = tableExamens.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez un examen dans la liste !");
            return;
        }

        int idExam = (int) modelExamens.getValueAt(row, 0);

        // 1. Vérification Anti-Triche
        ResultatDAO resDao = new ResultatDAO();
        if (resDao.aDejaPasse(etudiant.getId(), idExam)) {
            JOptionPane.showMessageDialog(this, "⛔ Vous avez déjà passé cet examen !", "Stop", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Chargement complet de l'examen (avec questions)
        ExamenDAO examDao = new ExamenDAO();
        Examen examenComplet = examDao.getExamenParId(idExam);

        if (examenComplet == null || examenComplet.getQuestions().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erreur : Cet examen est vide ou introuvable.");
            return;
        }

        // 3. Lancement de la fenêtre de passage
        FenetrePassageExamen fenetreExam = new FenetrePassageExamen(examenComplet, etudiant);
        fenetreExam.setVisible(true);
        
        // Optionnel : On peut fermer le dashboard ou le laisser ouvert
        // this.setVisible(false); 
    }
}