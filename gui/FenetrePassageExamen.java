package gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import dao.ResultatDAO;
import modele.Etudiant;
import modele.Examen;
import modele.Question;

public class FenetrePassageExamen extends JFrame {

    private Etudiant etudiant;
    private Examen examen;
    
    // État du passage
    private int indexQuestionCourante = 0;
    private double noteCumulee = 0.0;
    private double noteMaxPossible = 0.0;

    // Composants graphiques
    private JLabel lblTitre, lblProgress;
    private JTextArea txtEnonce;
    private JPanel panelChoix; // Contiendra les checkboxes
    private JButton btnSuivant;
    private ArrayList<JCheckBox> checkboxesActuelles; // Pour lire les réponses

    public FenetrePassageExamen(Examen examen, Etudiant etudiant) {
        this.examen = examen;
        this.etudiant = etudiant;
        this.checkboxesActuelles = new ArrayList<>();

        setTitle("Examen : " + examen.getTitre());
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Empêche de fermer accidentellement
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. En-tête
        JPanel panelNord = new JPanel(new GridLayout(2, 1));
        lblTitre = new JLabel("Examen : " + examen.getTitre(), JLabel.CENTER);
        lblTitre.setFont(new Font("Arial", Font.BOLD, 18));
        lblProgress = new JLabel("Question 1 / " + examen.getQuestions().size(), JLabel.CENTER);
        
        panelNord.add(lblTitre);
        panelNord.add(lblProgress);
        add(panelNord, BorderLayout.NORTH);

        // 2. Centre (Question + Choix)
        JPanel panelCentre = new JPanel(new BorderLayout());
        
        txtEnonce = new JTextArea();
        txtEnonce.setEditable(false);
        txtEnonce.setLineWrap(true);
        txtEnonce.setWrapStyleWord(true);
        txtEnonce.setFont(new Font("Arial", Font.BOLD, 14));
        txtEnonce.setBackground(new Color(240, 240, 240));
        txtEnonce.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelCentre.add(txtEnonce, BorderLayout.NORTH);

        // Zone des choix (défilable)
        panelChoix = new JPanel();
        panelChoix.setLayout(new BoxLayout(panelChoix, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(panelChoix);
        panelCentre.add(scroll, BorderLayout.CENTER);

        add(panelCentre, BorderLayout.CENTER);

        // 3. Bas (Bouton)
        btnSuivant = new JButton("Valider et Suivant ➡️");
        btnSuivant.setFont(new Font("Arial", Font.BOLD, 14));
        btnSuivant.setBackground(new Color(52, 152, 219));
        btnSuivant.setForeground(Color.WHITE);
        
        // Marge autour du bouton
        JPanel panelSud = new JPanel();
        panelSud.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelSud.add(btnSuivant);
        add(panelSud, BorderLayout.SOUTH);

        // ACTION
        btnSuivant.addActionListener(e -> traiterReponse());

        // Démarrage
        afficherQuestion();
    }

    private void afficherQuestion() {
        if (indexQuestionCourante < examen.getQuestions().size()) {
            Question q = examen.getQuestions().get(indexQuestionCourante);
            
            // Mise à jour textes
            lblProgress.setText("Question " + (indexQuestionCourante + 1) + " / " + examen.getQuestions().size());
            txtEnonce.setText(q.getEnonce());

            // Nettoyage des anciens choix
            panelChoix.removeAll();
            checkboxesActuelles.clear();

            // Création des Checkboxes
            for (String choixTexte : q.getChoix()) {
                JCheckBox chk = new JCheckBox(choixTexte);
                chk.setFont(new Font("Arial", Font.PLAIN, 14));
                panelChoix.add(chk);
                panelChoix.add(Box.createVerticalStrut(5)); // Espace
                checkboxesActuelles.add(chk);
            }

            // Rafraîchir l'affichage
            panelChoix.revalidate();
            panelChoix.repaint();
            
        } else {
            finirExamen();
        }
    }

    private void traiterReponse() {
        Question q = examen.getQuestions().get(indexQuestionCourante);
        
        // 1. Récupérer les index cochés
        ArrayList<Integer> reponsesCochees = new ArrayList<>();
        boolean auMoinsUneCochee = false;
        
        for (int i = 0; i < checkboxesActuelles.size(); i++) {
            if (checkboxesActuelles.get(i).isSelected()) {
                reponsesCochees.add(i);
                auMoinsUneCochee = true;
            }
        }

        // 2. Calcul des points
        double pointsQuestion = 0;
        
        if (!auMoinsUneCochee) {
            pointsQuestion = examen.getPointSiVide();
        } else {
            // APPEL AU CERVEAU (Modèle)
            pointsQuestion = q.calculerScore(reponsesCochees, examen.getPointSiJuste(), examen.getPointSiFaux());
        }
        
        // Mise à jour des totaux
        noteCumulee += pointsQuestion;
        noteMaxPossible += q.getScoreMaxPossible(examen.getPointSiJuste());

        // 3. Passer à la suivante
        indexQuestionCourante++;
        afficherQuestion();
    }

    private void finirExamen() {
        // Calcul final / 20
        double noteSur20 = 0.0;
        if (noteMaxPossible > 0) {
            noteSur20 = (noteCumulee / noteMaxPossible) * 20;
        }
        if (noteSur20 < 0) noteSur20 = 0.0;

        // Sauvegarde BDD
        ResultatDAO dao = new ResultatDAO();
        dao.sauvegarderResultat(etudiant.getId(), examen.getId(), noteSur20);

        // Affichage Résultat
        String msg = String.format("Examen terminé !\n\nVotre Note : %.2f / 20\n\n(Détail : %.1f / %.1f points)", 
                                   noteSur20, noteCumulee, noteMaxPossible);
        
        JOptionPane.showMessageDialog(this, msg, "Résultats", JOptionPane.INFORMATION_MESSAGE);
        
        // Fermer la fenêtre
        this.dispose();
    }
}