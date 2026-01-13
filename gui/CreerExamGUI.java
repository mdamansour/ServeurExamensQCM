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

    private void initialiserFormulaire() {
        JPanel formPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        formPanel.setBackground(secondaryColor);
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Générales"));

        // Row 1: Basic Info
        JPanel basicInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        basicInfo.setBackground(secondaryColor);
        
        txtTitre = new JTextField(15);
        txtFiliere = new JTextField(8);
        txtNiveau = new JTextField(5);
        
        basicInfo.add(new JLabel("Titre:")); basicInfo.add(txtTitre);
        basicInfo.add(new JLabel("Filière:")); basicInfo.add(txtFiliere);
        basicInfo.add(new JLabel("Niveau:")); basicInfo.add(txtNiveau);

        // Row 2: Barème
        JPanel baremeInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        baremeInfo.setBackground(secondaryColor);
        
        spinJuste = new JSpinner(new SpinnerNumberModel(1.0, 0.1, 10.0, 0.5));
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
        btnAddQ.setBackground(new Color(52, 152, 219)); // Blue
        btnAddQ.setForeground(Color.WHITE);
        
        JButton btnSave = new JButton("SAUVEGARDER L'EXAMEN");
        btnSave.setBackground(new Color(46, 204, 113)); // Green
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("SansSerif", Font.BOLD, 12));

        btnAddQ.addActionListener(e -> ouvrirDialogQuestion());
        btnSave.addActionListener(e -> sauvegarderExamen());

        btnPanel.add(btnAddQ);
        btnPanel.add(btnSave);
        
        centerPanel.add(btnPanel, BorderLayout.SOUTH);
    }

    private void ouvrirDialogQuestion() {
        QuestionDialog dialog = new QuestionDialog(this);
        Question q = dialog.showDialog();
        
        if (q != null) {
            listeQuestions.add(q);
            // Show a small icon or text if media is present
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

    // --- INNER CLASS: Dialog to Create a Question ---
    class QuestionDialog extends JDialog {
        private JTextField txtEnonce;
        private JLabel lblMediaSelected; 
        private String mediaPath = null; 
        
        private JPanel choicesPanel;
        private ArrayList<JTextField> choiceFields;
        private ArrayList<JCheckBox> correctBoxes;
        private Question createdQuestion = null;

        public QuestionDialog(JFrame parent) {
            super(parent, "Nouvelle Question", true);
            this.setSize(600, 500);
            this.setLocationRelativeTo(parent);
            this.setLayout(new BorderLayout());

            choiceFields = new ArrayList<>();
            correctBoxes = new ArrayList<>();

            // 1. TOP PANEL: Enonce + Media
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBorder(BorderFactory.createTitledBorder("Détails de la question"));
            
            // A. Enonce
            JPanel enoncePanel = new JPanel(new BorderLayout());
            enoncePanel.add(new JLabel("Énoncé : "), BorderLayout.WEST);
            txtEnonce = new JTextField();
            enoncePanel.add(txtEnonce, BorderLayout.CENTER);
            
            // B. Media Selection
            JPanel mediaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton btnMedia = new JButton("Joindre Média (Img/Vid/Aud)");
            lblMediaSelected = new JLabel("Aucun fichier sélectionné");
            lblMediaSelected.setFont(new Font("SansSerif", Font.ITALIC, 11));
            
            btnMedia.addActionListener(e -> choisirFichierMedia());
            
            mediaPanel.add(btnMedia);
            mediaPanel.add(lblMediaSelected);

            JPanel combinedTop = new JPanel(new GridLayout(2, 1, 0, 5));
            combinedTop.add(enoncePanel);
            combinedTop.add(mediaPanel);
            
            topPanel.add(combinedTop, BorderLayout.CENTER);
            this.add(topPanel, BorderLayout.NORTH);

            // 2. CENTER PANEL: Choices List + Add Button
            JPanel centerWrapper = new JPanel(new BorderLayout());

            choicesPanel = new JPanel();
            choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));
            JScrollPane scroll = new JScrollPane(choicesPanel);
            scroll.setBorder(BorderFactory.createTitledBorder("Choix de réponse"));
            
            // Add 2 initial choices
            ajouterLigneChoix();
            ajouterLigneChoix();
            
            // Button to add more choices
            JButton btnAddChoice = new JButton("+ Ajouter un choix");
            btnAddChoice.addActionListener(e -> ajouterLigneChoix());
            JPanel btnAddPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            btnAddPanel.add(btnAddChoice);

            centerWrapper.add(scroll, BorderLayout.CENTER);
            centerWrapper.add(btnAddPanel, BorderLayout.SOUTH);

            this.add(centerWrapper, BorderLayout.CENTER);

            // 3. BOTTOM PANEL: Validate Button
            JButton btnOk = new JButton("Valider la Question");
            btnOk.setBackground(new Color(46, 204, 113));
            btnOk.setForeground(Color.WHITE);
            btnOk.addActionListener(e -> valider());
            
            JPanel bottomPanel = new JPanel();
            bottomPanel.add(btnOk);
            this.add(bottomPanel, BorderLayout.SOUTH);
        }

        private void choisirFichierMedia() {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Média (Images, Vidéos, Audio)", "jpg", "png", "gif", "mp4", "avi", "mp3", "wav");
            chooser.setFileFilter(filter);
            
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                this.mediaPath = file.getAbsolutePath();
                this.lblMediaSelected.setText(file.getName());
                this.lblMediaSelected.setForeground(new Color(39, 174, 96));
            }
        }

        private void ajouterLigneChoix() {
            JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JTextField txtChoice = new JTextField(30);
            JCheckBox chkCorrect = new JCheckBox("Correct?");
            JButton btnDelete = new JButton("X");
            
            // Styling the delete button
            btnDelete.setBackground(new Color(231, 76, 60)); // Red
            btnDelete.setForeground(Color.WHITE);
            btnDelete.setMargin(new Insets(2, 6, 2, 6)); // Make it small
            btnDelete.setFocusPainted(false);

            // Add to lists
            choiceFields.add(txtChoice);
            correctBoxes.add(chkCorrect);
            
            // Delete Action
            btnDelete.addActionListener(e -> {
                choicesPanel.remove(line);
                choiceFields.remove(txtChoice);
                correctBoxes.remove(chkCorrect);
                
                // Refresh UI
                choicesPanel.revalidate();
                choicesPanel.repaint();
            });

            line.add(new JLabel("• "));
            line.add(txtChoice);
            line.add(chkCorrect);
            line.add(btnDelete);
            
            choicesPanel.add(line);
            
            // Refresh UI
            choicesPanel.revalidate();
            choicesPanel.repaint();
        }

        private void valider() {
            if (txtEnonce.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "L'énoncé est vide.");
                return;
            }

            createdQuestion = new Question(txtEnonce.getText());
            
            if (mediaPath != null) {
                createdQuestion.setMedia(mediaPath);
            }

            boolean hasCorrectAnswer = false;

            for (int i = 0; i < choiceFields.size(); i++) {
                String text = choiceFields.get(i).getText().trim();
                // Only add non-empty choices
                if (!text.isEmpty()) {
                    createdQuestion.ajouterChoix(text);
                    // Since we iterate through the list sequentially, the index i matches
                    if (correctBoxes.get(i).isSelected()) {
                        createdQuestion.ajouterBonneReponse(createdQuestion.getChoix().size() - 1); 
                        hasCorrectAnswer = true;
                    }
                }
            }

            if (createdQuestion.getChoix().size() < 2) {
                JOptionPane.showMessageDialog(this, "Il faut au moins 2 choix valides.");
                createdQuestion = null;
                return;
            }
            
            if (!hasCorrectAnswer) {
                JOptionPane.showMessageDialog(this, "Il faut au moins une réponse correcte.");
                createdQuestion = null;
                return;
            }

            this.setVisible(false);
        }

        public Question showDialog() {
            this.setVisible(true);
            return createdQuestion;
        }
    }
}