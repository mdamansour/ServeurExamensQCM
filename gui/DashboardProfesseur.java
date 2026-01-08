package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

import dao.ExamenDAO;
import dao.ResultatDAO;
import modele.Examen;
import modele.Professeur;
import modele.Question;
import modele.Resultat;

public class DashboardProfesseur extends JFrame {

    private Professeur profConnecte;
    
    // Composants Création
    private JTextField txtTitre, txtFiliere, txtNiveau;
    private JTextField txtBaremeJuste, txtBaremeFaux, txtBaremeVide;
    private JLabel lblCompteurQuestions;
    private ArrayList<Question> questionsEnAttente;

    // Composants Résultats
    private JTextField txtIdRecherche;
    private JTable tableResultats;
    private DefaultTableModel modelTable;

    public DashboardProfesseur(Professeur prof) {
        this.profConnecte = prof;
        this.questionsEnAttente = new ArrayList<>();

        setTitle("Espace Professeur - " + prof.getNomComplet());
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Créer un Examen", createPanelCreation());
        tabs.addTab("Consulter les Notes", createPanelResultats());
        add(tabs);
    }

    private JPanel createPanelCreation() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // 1. Infos Examen
        JPanel formPanel = new JPanel(new GridLayout(4, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Paramètres de l'examen"));
        
        txtTitre = new JTextField(); txtFiliere = new JTextField(); txtNiveau = new JTextField();
        txtBaremeJuste = new JTextField("1"); txtBaremeFaux = new JTextField("0"); txtBaremeVide = new JTextField("0");

        formPanel.add(new JLabel("Titre :")); formPanel.add(txtTitre);
        formPanel.add(new JLabel("Filière :")); formPanel.add(txtFiliere);
        formPanel.add(new JLabel("Niveau :")); formPanel.add(txtNiveau);
        formPanel.add(new JLabel("")); formPanel.add(new JLabel("")); 
        
        formPanel.add(new JLabel("Points Juste (+) :")); formPanel.add(txtBaremeJuste);
        formPanel.add(new JLabel("Points Faux (-) :")); formPanel.add(txtBaremeFaux);
        formPanel.add(new JLabel("Points Vide (0) :")); formPanel.add(txtBaremeVide);

        panel.add(formPanel, BorderLayout.NORTH);

        // 2. Gestion Questions
        JPanel qPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        JButton btnAjouterQ = new JButton("Ajouter une Question");
        btnAjouterQ.setFont(new Font("Arial", Font.BOLD, 13));
        
        lblCompteurQuestions = new JLabel("Questions prêtes : 0");
        lblCompteurQuestions.setFont(new Font("Arial", Font.BOLD, 14));
        lblCompteurQuestions.setForeground(new Color(0, 102, 204));

        btnAjouterQ.addActionListener(e -> ouvrirDialogAjoutQuestionDynamique());

        qPanel.add(btnAjouterQ);
        qPanel.add(lblCompteurQuestions);
        panel.add(qPanel, BorderLayout.CENTER);

        // 3. Save
        JButton btnSave = new JButton("ENREGISTRER L'EXAMEN");
        btnSave.setFont(new Font("Arial", Font.BOLD, 16));
        btnSave.setBackground(new Color(46, 204, 113));
        btnSave.setForeground(Color.WHITE);
        btnSave.addActionListener(e -> finaliserExamen());
        
        panel.add(btnSave, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createPanelResultats() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel searchPanel = new JPanel();
        txtIdRecherche = new JTextField(10);
        JButton btnChercher = new JButton("Chercher");
        searchPanel.add(new JLabel("ID Examen :")); searchPanel.add(txtIdRecherche); searchPanel.add(btnChercher);
        
        panel.add(searchPanel, BorderLayout.NORTH);

        String[] cols = {"Nom Étudiant", "Note / 20"};
        modelTable = new DefaultTableModel(cols, 0);
        tableResultats = new JTable(modelTable);
        panel.add(new JScrollPane(tableResultats), BorderLayout.CENTER);

        btnChercher.addActionListener(e -> chargerResultats());
        return panel;
    }

    // --- LE CŒUR DU SYSTÈME DYNAMIQUE ---
    
    private class LigneChoix extends JPanel {
        JCheckBox chkCorrect;
        JTextField txtChoix;

        public LigneChoix(int numero) {
            setLayout(new BorderLayout(5, 0));
            chkCorrect = new JCheckBox("Correct?");
            txtChoix = new JTextField();
            
            add(new JLabel("Choix " + numero + " : "), BorderLayout.WEST);
            add(txtChoix, BorderLayout.CENTER);
            add(chkCorrect, BorderLayout.EAST);
        }
    }

    private void ouvrirDialogAjoutQuestionDynamique() {
        JDialog d = new JDialog(this, "Nouvelle Question", true);
        d.setSize(500, 500);
        d.setLocationRelativeTo(this);
        d.setLayout(new BorderLayout());

        // 1. Enoncé
        JPanel panelNord = new JPanel(new BorderLayout());
        panelNord.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelNord.add(new JLabel("Énoncé de la question :"), BorderLayout.NORTH);
        JTextArea txtEnonce = new JTextArea(3, 40);
        panelNord.add(new JScrollPane(txtEnonce), BorderLayout.CENTER);
        d.add(panelNord, BorderLayout.NORTH);

        // 2. Container des choix
        JPanel panelChoixContainer = new JPanel();
        panelChoixContainer.setLayout(new BoxLayout(panelChoixContainer, BoxLayout.Y_AXIS)); 
        JScrollPane scrollChoix = new JScrollPane(panelChoixContainer);
        scrollChoix.setBorder(BorderFactory.createTitledBorder("Réponses possibles"));
        d.add(scrollChoix, BorderLayout.CENTER);

        ArrayList<LigneChoix> listeLignes = new ArrayList<>();

        Runnable ajouterLigne = () -> {
            LigneChoix ligne = new LigneChoix(listeLignes.size() + 1);
            listeLignes.add(ligne);
            panelChoixContainer.add(ligne);
            panelChoixContainer.add(Box.createVerticalStrut(5)); 
            
            panelChoixContainer.revalidate(); 
            panelChoixContainer.repaint();
        };

        // --- MODIFICATION ICI : 3 choix par défaut ---
        for(int i = 0; i < 3; i++) {
            ajouterLigne.run();
        }

        // 3. Boutons du bas
        JPanel panelSud = new JPanel(new FlowLayout());
        JButton btnAddChoice = new JButton("Ajouter un choix");
        JButton btnValider = new JButton("Terminer");

        btnAddChoice.addActionListener(e -> ajouterLigne.run());

        btnValider.addActionListener(e -> {
            if(txtEnonce.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(d, "L'énoncé est vide !");
                return;
            }

            Question q = new Question(txtEnonce.getText());
            boolean auMoinsUneJuste = false;

            for (int i = 0; i < listeLignes.size(); i++) {
                LigneChoix ligne = listeLignes.get(i);
                String texte = ligne.txtChoix.getText().trim();
                
                if (!texte.isEmpty()) {
                    q.ajouterChoix(texte);
                    if (ligne.chkCorrect.isSelected()) {
                        q.ajouterBonneReponse(i);
                        auMoinsUneJuste = true;
                    }
                }
            }

            if (!auMoinsUneJuste) {
                JOptionPane.showMessageDialog(d, "Il faut cocher au moins une réponse correcte !");
                return;
            }

            questionsEnAttente.add(q);
            lblCompteurQuestions.setText("Questions prêtes : " + questionsEnAttente.size());
            d.dispose();
        });

        panelSud.add(btnAddChoice);
        panelSud.add(btnValider);
        d.add(panelSud, BorderLayout.SOUTH);

        d.setVisible(true);
    }

    private void finaliserExamen() {
        if(questionsEnAttente.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ajoutez au moins une question !");
            return;
        }
        try {
            Examen exam = new Examen(txtTitre.getText(), txtFiliere.getText(), txtNiveau.getText(), profConnecte);
            exam.setBareme(Double.parseDouble(txtBaremeJuste.getText()), Double.parseDouble(txtBaremeFaux.getText()), Double.parseDouble(txtBaremeVide.getText()));
            
            for(Question q : questionsEnAttente) exam.ajouterQuestion(q);
            
            ExamenDAO dao = new ExamenDAO();
            dao.sauvegarderExamen(exam);
            
            JOptionPane.showMessageDialog(this, "Examen enregistré avec succès (ID: " + exam.getId() + ")");
            questionsEnAttente.clear();
            lblCompteurQuestions.setText("Questions prêtes : 0");
            txtTitre.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Vérifiez les champs numériques (Barème).");
        }
    }

    private void chargerResultats() {
        try {
            int id = Integer.parseInt(txtIdRecherche.getText());
            ResultatDAO dao = new ResultatDAO();
            ArrayList<Resultat> resultats = dao.getResultatsParExamen(id);
            modelTable.setRowCount(0);
            for (Resultat r : resultats) modelTable.addRow(new Object[]{r.getNomEtudiant(), r.getNote()});
            if(resultats.isEmpty()) JOptionPane.showMessageDialog(this, "Aucun résultat.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "ID invalide.");
        }
    }
}