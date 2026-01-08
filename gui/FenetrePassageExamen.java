package gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
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
    private JPanel panelMediaContainer; // Zone pour l'image ou le bouton vidéo
    private JPanel panelChoix; 
    private JButton btnSuivant;
    private ArrayList<JCheckBox> checkboxesActuelles;

    public FenetrePassageExamen(Examen examen, Etudiant etudiant) {
        this.examen = examen;
        this.etudiant = etudiant;
        this.checkboxesActuelles = new ArrayList<>();

        setTitle("Examen : " + examen.getTitre());
        setSize(700, 650); // Un peu plus grand pour l'image
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

        // 2. Centre (Enoncé + Média + Choix)
        JPanel panelCentre = new JPanel();
        panelCentre.setLayout(new BoxLayout(panelCentre, BoxLayout.Y_AXIS));
        
        // A. Enoncé
        txtEnonce = new JTextArea();
        txtEnonce.setEditable(false);
        txtEnonce.setLineWrap(true);
        txtEnonce.setWrapStyleWord(true);
        txtEnonce.setFont(new Font("Arial", Font.BOLD, 14));
        txtEnonce.setBackground(new Color(240, 240, 240));
        txtEnonce.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        panelCentre.add(txtEnonce);

        // B. Zone Média (Image ou Bouton)
        panelMediaContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCentre.add(panelMediaContainer);

        // C. Zone Choix (Scrollable)
        panelChoix = new JPanel();
        panelChoix.setLayout(new BoxLayout(panelChoix, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(panelCentre); // On scroll tout le centre
        
        // On ajoute les choix au panelCentre via un autre container pour éviter que le scroll casse tout
        JPanel containerChoix = new JPanel(new BorderLayout());
        containerChoix.add(panelChoix, BorderLayout.NORTH);
        panelCentre.add(containerChoix);

        add(scroll, BorderLayout.CENTER);

        // 3. Bas (Bouton Valider)
        btnSuivant = new JButton("Valider et Suivant ➡️");
        btnSuivant.setFont(new Font("Arial", Font.BOLD, 14));
        btnSuivant.setBackground(new Color(52, 152, 219));
        btnSuivant.setForeground(Color.WHITE);
        
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

            // --- GESTION DU MÉDIA (NOUVEAU) ---
            panelMediaContainer.removeAll(); // On vide l'ancien média
            
            if (q.getMedia() != null && !q.getMedia().isEmpty()) {
                File fichierMedia = new File(q.getMedia());
                
                if (fichierMedia.exists()) {
                    String nom = fichierMedia.getName().toLowerCase();
                    
                    // Si c'est une IMAGE -> On l'affiche
                    if (nom.endsWith(".jpg") || nom.endsWith(".png") || nom.endsWith(".jpeg") || nom.endsWith(".gif")) {
                        try {
                            ImageIcon icon = new ImageIcon(fichierMedia.getPath());
                            // Redimensionnement intelligent (Max 400px de large)
                            Image img = icon.getImage();
                            int newWidth = 400;
                            int newHeight = (int) ((double) icon.getIconHeight() / icon.getIconWidth() * newWidth);
                            Image newImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                            
                            JLabel lblImage = new JLabel(new ImageIcon(newImg));
                            panelMediaContainer.add(lblImage);
                            
                        } catch (Exception e) {
                            panelMediaContainer.add(new JLabel("Erreur affichage image"));
                        }
                    } 
                    // Si c'est VIDÉO ou AUDIO -> Bouton "Ouvrir"
                    else {
                        JButton btnOuvrir = new JButton("▶️ Ouvrir le fichier média (" + nom + ")");
                        btnOuvrir.setBackground(Color.ORANGE);
                        btnOuvrir.addActionListener(e -> {
                            try {
                                Desktop.getDesktop().open(fichierMedia);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Impossible d'ouvrir le fichier : " + ex.getMessage());
                            }
                        });
                        panelMediaContainer.add(btnOuvrir);
                    }
                }
            }
            panelMediaContainer.revalidate();
            panelMediaContainer.repaint();

            // --- GESTION DES CHOIX ---
            panelChoix.removeAll();
            checkboxesActuelles.clear();

            for (String choixTexte : q.getChoix()) {
                JCheckBox chk = new JCheckBox(choixTexte);
                chk.setFont(new Font("Arial", Font.PLAIN, 14));
                panelChoix.add(chk);
                panelChoix.add(Box.createVerticalStrut(5));
                checkboxesActuelles.add(chk);
            }

            // Rafraîchir l'affichage global
            panelChoix.revalidate();
            panelChoix.repaint();
            
        } else {
            finirExamen();
        }
    }

    private void traiterReponse() {
        Question q = examen.getQuestions().get(indexQuestionCourante);
        
        ArrayList<Integer> reponsesCochees = new ArrayList<>();
        boolean auMoinsUneCochee = false;
        
        for (int i = 0; i < checkboxesActuelles.size(); i++) {
            if (checkboxesActuelles.get(i).isSelected()) {
                reponsesCochees.add(i);
                auMoinsUneCochee = true;
            }
        }

        double pointsQuestion = 0;
        if (!auMoinsUneCochee) {
            pointsQuestion = examen.getPointSiVide();
        } else {
            pointsQuestion = q.calculerScore(reponsesCochees, examen.getPointSiJuste(), examen.getPointSiFaux());
        }
        
        noteCumulee += pointsQuestion;
        noteMaxPossible += q.getScoreMaxPossible(examen.getPointSiJuste());

        indexQuestionCourante++;
        afficherQuestion();
    }

    private void finirExamen() {
        double noteSur20 = 0.0;
        if (noteMaxPossible > 0) {
            noteSur20 = (noteCumulee / noteMaxPossible) * 20;
        }
        if (noteSur20 < 0) noteSur20 = 0.0;

        ResultatDAO dao = new ResultatDAO();
        dao.sauvegarderResultat(etudiant.getId(), examen.getId(), noteSur20);

        String msg = String.format("Examen terminé !\n\nVotre Note : %.2f / 20\n\n(Détail : %.1f / %.1f points)", 
                                   noteSur20, noteCumulee, noteMaxPossible);
        
        JOptionPane.showMessageDialog(this, msg, "Résultats", JOptionPane.INFORMATION_MESSAGE);
        this.dispose();
    }
}