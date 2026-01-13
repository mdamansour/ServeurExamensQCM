package gui.dependencies;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import modele.Question;

public class QuestionDialog extends JDialog {
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
        
        btnMedia.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                choisirFichierMedia();
            }
        });
        
        mediaPanel.add(btnMedia);
        mediaPanel.add(lblMediaSelected);

        JPanel combinedTop = new JPanel(new GridLayout(2, 1, 0, 5));
        combinedTop.add(enoncePanel);
        combinedTop.add(mediaPanel);
        
        topPanel.add(combinedTop, BorderLayout.CENTER);
        this.add(topPanel, BorderLayout.NORTH);

        // 2. CENTER PANEL
        JPanel centerWrapper = new JPanel(new BorderLayout());

        choicesPanel = new JPanel();
        choicesPanel.setLayout(new BoxLayout(choicesPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(choicesPanel);
        scroll.setBorder(BorderFactory.createTitledBorder("Choix de réponse"));
        
        // ajouter 2 choix par default
        ajouterLigneChoix();
        ajouterLigneChoix();
        
        // Button to add more choices
        JButton btnAddChoice = new JButton("+ Ajouter un choix");


        
        
        btnAddChoice.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                ajouterLigneChoix();
            }
        });
        
        
        
        
        
        JPanel btnAddPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnAddPanel.add(btnAddChoice);

        centerWrapper.add(scroll, BorderLayout.CENTER);
        centerWrapper.add(btnAddPanel, BorderLayout.SOUTH);

        this.add(centerWrapper, BorderLayout.CENTER);

        // 3. BOTTOM PANEL: Validate Button
        JButton btnOk = new JButton("Valider la Question");
        btnOk.setBackground(new Color(46, 204, 113));
        btnOk.setForeground(Color.WHITE);


        
        
        btnOk.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                valider();
            }
        });
        
        
        
        
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
            this.lblMediaSelected.setForeground(new Color(39, 174, 96)); //vert
        }
    }

    
    
    
    // ajoute ligne
    
    
    private void ajouterLigneChoix() {
        JPanel line = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtChoice = new JTextField(30);
        JCheckBox chkCorrect = new JCheckBox("Correct?");
        JButton btnDelete = new JButton("X");
        
        // botton pour supprimer 
        
        
        btnDelete.setBackground(new Color(231, 76, 60)); // Red
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setMargin(new Insets(2, 6, 2, 6)); // Make it small
        btnDelete.setFocusPainted(false);

        // add to list
        choiceFields.add(txtChoice);
        correctBoxes.add(chkCorrect);
        
        // Delete Action
        btnDelete.addActionListener(e -> {
            choicesPanel.remove(line);
            choiceFields.remove(txtChoice);
            correctBoxes.remove(chkCorrect);
            
            // Refresh GUI
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
            if (!text.isEmpty()) {
                createdQuestion.ajouterChoix(text);
                if (correctBoxes.get(i).isSelected()) {
                    createdQuestion.ajouterBonneReponse(createdQuestion.getChoix().size() - 1); 
                    hasCorrectAnswer = true;
                }
            }
        }

        
        
        
        
        
        // those are errors handling ida ma
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
