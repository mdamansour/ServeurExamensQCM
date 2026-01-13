package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import database.ExamenBD;
import gui.dependencies.BaseWindow;
import gui.dependencies.QuestionDialog;
import modele.Examen;
import modele.Professeur;
import modele.Question;

public class CreerExamGUI extends BaseWindow {

    private Professeur professeur;
    private ProfesseurGUI parentGUI; 
    
    // Form fields
    private JTextField txtTitre, txtFiliere, txtNiveau;
    private JSpinner spinJuste, spinFaux, spinVide;
    
    // Data management
    private DefaultListModel<String> modelListeQuestions;
    private ArrayList<Question> listeQuestions; 

    
    
    
    
    
    
    
    
    
    
    
    public CreerExamGUI(Professeur prof, ProfesseurGUI parent) {
    	
        super("Création d'un nouvel examen");
        this.professeur = prof;
        this.parentGUI = parent;
        this.listeQuestions = new ArrayList<>();

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        changerHeaderTitre("Nouvel Examen");

        centerPanel.setLayout(new BorderLayout(10, 10));
        
       
        
        // 1. Init Form (North)
        initialiserFormulaire();
        
        // 2. Init Questions List (Center)
        initialiserZoneQuestions();
        
        // 3. Init Buttons (South)
        initialiserBoutons();

        this.setVisible(true);
        
        
        
        
        
    }
    // 1. Init Form (North)
    
    private void initialiserFormulaire() {
        JPanel formPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        formPanel.setBackground(secondaryColor);
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Générales"));	// 9ad border + titr

        // Row 1
        JPanel basicInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        basicInfo.setBackground(secondaryColor);
        
        txtTitre = new JTextField(15);
        txtFiliere = new JTextField(8);
        txtNiveau = new JTextField(5);
        
        basicInfo.add(new JLabel("Titre:")); basicInfo.add(txtTitre);
        basicInfo.add(new JLabel("Filière:")); basicInfo.add(txtFiliere);
        basicInfo.add(new JLabel("Niveau:")); basicInfo.add(txtNiveau);

        // Row 2
        JPanel baremeInfo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        baremeInfo.setBackground(secondaryColor);
        
        spinJuste = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, 0.5));	//(default, min, max, le pas)
        spinFaux = new JSpinner(new SpinnerNumberModel(0.0, -5.0, 0.0, 0.5)); 
        spinVide = new JSpinner(new SpinnerNumberModel(0.0, -5.0, 0.0, 0.5));

        baremeInfo.add(new JLabel("Barème -> Correct (+):")); baremeInfo.add(spinJuste);
        baremeInfo.add(new JLabel("Incorrect (-):")); baremeInfo.add(spinFaux);
        baremeInfo.add(new JLabel("Vide (0):")); baremeInfo.add(spinVide);

        formPanel.add(basicInfo);
        formPanel.add(baremeInfo);
        centerPanel.add(formPanel, BorderLayout.NORTH);

    }
        
        
    
    
    private void initialiserZoneQuestions() {
        modelListeQuestions = new DefaultListModel<>();
        JList<String> jListQuestions = new JList<>(modelListeQuestions);
        jListQuestions.setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(jListQuestions);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Questions ajoutées"));
        
        centerPanel.add(scrollPane, BorderLayout.CENTER);
    }

    
    
    private void initialiserBoutons() {
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(secondaryColor);

        JButton btnAddQ = new JButton("+ Ajouter une Question");
        btnAddQ.setBackground(new Color(52, 152, 219)); // nombre representant Blue
        btnAddQ.setForeground(Color.WHITE);
        
        JButton btnSave = new JButton("SAUVEGARDER L'EXAMEN");
        btnSave.setBackground(new Color(46, 204, 113)); // Green
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("SansSerif", Font.BOLD, 12));

        
        
        
        
        // hadom action listeners 
        btnAddQ.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ouvrirDialogQuestion();
            }
        });

        btnSave.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                sauvegarderExamen();
            }
        });

        btnPanel.add(btnAddQ);
        btnPanel.add(btnSave);
        
        centerPanel.add(btnPanel, BorderLayout.SOUTH);
    }

    
    
    
    
    
    private void ouvrirDialogQuestion() {
        QuestionDialog dialog = new QuestionDialog(this);
        Question q = dialog.showDialog();
        
        if (q != null) {
            listeQuestions.add(q);
            String mediaIndicator = (q.getMedia() != null && !q.getMedia().isEmpty()) ? " [MEDIA]" : "";
            modelListeQuestions.addElement("Q" + listeQuestions.size() + ": " + q.getEnonce() + mediaIndicator + " (" + q.getChoix().size() + " choix)");
        }
    }

    private void sauvegarderExamen() {
        if (txtTitre.getText().trim().isEmpty() || listeQuestions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un titre et au moins une question.");
            return;
        }

        try {
            Examen newExam = new Examen(
                txtTitre.getText(),
                txtFiliere.getText(),
                txtNiveau.getText(),
                this.professeur
            );
            
            newExam.setBareme(
                (double) spinJuste.getValue(), 
                (double) spinFaux.getValue(), 
                (double) spinVide.getValue()
            );
            
            newExam.setQuestions(listeQuestions);

            ExamenBD examenBD = new ExamenBD();
            examenBD.creerExamen(newExam);
            
            JOptionPane.showMessageDialog(this, "Examen créé avec succès !");
            
            if (parentGUI != null) {
                parentGUI.chargerExamens();
            }
            this.dispose();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde: " + e.getMessage());
        }
    }

    
    
    
    
    // Dialogue pour créer une question pop up lighat3tina acces bach nda5lo

}