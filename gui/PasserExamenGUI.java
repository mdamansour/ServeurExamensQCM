package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import database.QuestionBD;
import database.ResultatBD;
import gui.dependencies.BaseWindow;
import modele.Etudiant;
import modele.Examen;
import modele.Question;

public class PasserExamenGUI extends BaseWindow {

    private Etudiant etudiant;
    private Examen examen;
    private EtudiantGUI parentGUI;
    
    // We store the question panels to retrieve answers later
    private ArrayList<QuestionPanel> questionPanels = new ArrayList<>();

    public PasserExamenGUI(Etudiant etudiant, Examen examen, EtudiantGUI parent) {
        super("Examen: " + examen.getTitre());
        this.etudiant = etudiant;
        this.examen = examen;
        this.parentGUI = parent;

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        changerHeaderTitre("Examen en cours : " + examen.getTitre());

        centerPanel.setLayout(new BorderLayout());
        
        loadQuestions();

        initialiserInterface();
        
        this.setVisible(true);
    }
    
    
    
    // had method uses QuestionBD bach t3amar examen object with its questions.
    private void loadQuestions() {
        QuestionBD qbd = new QuestionBD();
        try {
            ArrayList<Question> questions = qbd.recupererParExamen(examen.getId());
            examen.setQuestions(questions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    
    
    
    //hadi hiya likat3mlna graphique
    private void initialiserInterface() {
        JPanel questionsContainer = new JPanel();
        questionsContainer.setLayout(new BoxLayout(questionsContainer, BoxLayout.Y_AXIS));

        if (examen.getQuestions().isEmpty()) {
            questionsContainer.add(new JLabel("Aucune question dans cet examen."));
        } else {
            for (int i = 0; i < examen.getQuestions().size(); i++) {
                Question q = examen.getQuestions().get(i);
                QuestionPanel qPanel = new QuestionPanel(q, i + 1);
                questionPanels.add(qPanel);
                questionsContainer.add(qPanel);
            }
        }

        // Scroll Pane
        JScrollPane scrollPane = new JScrollPane(questionsContainer);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Faster scrolling
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer, fin kayn buttons
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footer.setBackground(secondaryColor);
        
        JButton btnTerminer = new JButton("Terminer l'examen");
        btnTerminer.setBackground(new Color(46, 204, 113)); // Green
        btnTerminer.setForeground(Color.WHITE);
        btnTerminer.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        btnTerminer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soumettreExamen();
            }
        });        
        
        footer.add(btnTerminer);
        centerPanel.add(footer, BorderLayout.SOUTH);
    }

    
    
    
    // action of clicking 3la buttoun terminer
    private void soumettreExamen() {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment terminer l'examen ?", 
            "Confirmation", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            calculerEtSauvegarder();
        }
    }

    private void calculerEtSauvegarder() {
        double scoreTotal = 0.0;
        double maxPossible = 0.0;

        for (QuestionPanel qp : questionPanels) {
            Question q = qp.getQuestion();
            ArrayList<Integer> reponses = qp.getSelectedIndices();
            
            // Calculate score for this question
            scoreTotal += q.calculerScore(reponses, examen.getPointSiJuste(), examen.getPointSiFaux(), examen.getPointSiVide());
            
            // Calculate max potential score (to normalize later)
            maxPossible += q.getScoreMaxPossible(examen.getPointSiJuste());
        }

        // Normalize to /20
        double noteSur20 = 0.0;
        if (maxPossible > 0) {
            noteSur20 = (scoreTotal / maxPossible) * 20.0;
        }
        
        // Ensure no negative grades
        if (noteSur20 < 0) noteSur20 = 0.0;

        // Save to DB
        ResultatBD resBD = new ResultatBD();
        resBD.sauvegarderResultat(etudiant.getId(), examen.getId(), noteSur20);

        // Show result
        JOptionPane.showMessageDialog(this, 
            String.format("Examen terminé !\nVotre note : %.2f / 20", noteSur20));

        // Refresh parent and close
        if (parentGUI != null) {
            parentGUI.chargerDonnees();
        }
        this.dispose();
    }

    class QuestionPanel extends JPanel {
        private Question question;
        private ArrayList<JCheckBox> checkBoxes;

        public QuestionPanel(Question q, int index) {
            this.question = q;
            this.checkBoxes = new ArrayList<>();
            
            this.setLayout(new BorderLayout());
            this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10),
                BorderFactory.createLineBorder(Color.LIGHT_GRAY)
            ));
            this.setBackground(Color.WHITE);

            // 1. Enonce + Media
            JPanel header = new JPanel(new BorderLayout());
            header.setBackground(Color.WHITE);
            
            JLabel lblEnonce = new JLabel("Q" + index + ". " + q.getEnonce());
            lblEnonce.setFont(new Font("SansSerif", Font.BOLD, 14));
            header.add(lblEnonce, BorderLayout.CENTER);

            // Media Button (if exists)
            if (q.getMedia() != null && !q.getMedia().isEmpty()) {
                JButton btnMedia = new JButton("Voir Média associé");
                btnMedia.setBackground(new Color(52, 152, 219));
                btnMedia.setForeground(Color.WHITE);
                btnMedia.addActionListener(e -> ouvrirMedia(q.getMedia()));
                header.add(btnMedia, BorderLayout.EAST);
            }
            
            this.add(header, BorderLayout.NORTH);

            // 2. Choices
            JPanel choicesPanel = new JPanel(new GridLayout(0, 1)); // Vertical list
            choicesPanel.setBackground(Color.WHITE);
            
            for (String choixTxt : q.getChoix()) {
                JCheckBox cb = new JCheckBox(choixTxt);
                cb.setBackground(Color.WHITE);
                checkBoxes.add(cb);
                choicesPanel.add(cb);
            }
            
            this.add(choicesPanel, BorderLayout.CENTER);
        }

        public Question getQuestion() {
            return question;
        }

        public ArrayList<Integer> getSelectedIndices() {
            ArrayList<Integer> indices = new ArrayList<>();
            for (int i = 0; i < checkBoxes.size(); i++) {
                if (checkBoxes.get(i).isSelected()) {
                    indices.add(i);
                }
            }
            return indices;
        }

        private void ouvrirMedia(String path) {
            try {
                File file = new File(path);
                if (file.exists()) {
                    Desktop.getDesktop().open(file);
                } else {
                    JOptionPane.showMessageDialog(this, "Fichier introuvable : " + path);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}