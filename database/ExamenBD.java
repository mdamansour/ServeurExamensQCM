package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modele.Examen;
import modele.Question;

public class ExamenBD {
	
	
	
	public void creerExamen(Examen examen) throws SQLException{
		//creer l'connexion lighan5edmo bih
		Connection connexion = Connexion.getConnexion();
		//haada howa code SQL
		String requestExam = "INSERT INTO examen (titre, filiere, niveau, id_prof, point_si_juste, point_si_faux, point_si_vide) VALUES (?, ?, ?, ?, ?, ?, ?)";
		// wjd lhandshake + red lbal rah kanstnak tsayftli id li saybti mn ba3d
		PreparedStatement prExam = connexion.prepareStatement(requestExam, Statement.RETURN_GENERATED_KEYS);
		// 3amar l5awyat ????????
		prExam.setString(1, examen.getTitre());
		prExam.setString(2, examen.getFiliere());
		prExam.setString(3, examen.getNiveau());
		prExam.setInt(4, examen.getProfesseur().getId());
		prExam.setDouble(5, examen.getPointSiJuste());
		prExam.setDouble(6, examen.getPointSiFaux());
		prExam.setDouble(7, examen.getPointSiVide());
		
		// sift request mkmol db ser 3la lah
		prExam.executeUpdate();
		
		
		
		/*---------------------------------save the questions---------------------------------*/
		ResultSet resultatExam = prExam.getGeneratedKeys();
		QuestionBD question = new QuestionBD();
		
		if (resultatExam.next()) {
			for(int i = 0; i<examen.getQuestions().size();i++) {
				question.sauvegarderQuestion(examen.getQuestions().get(i), resultatExam.getInt(1));
			}
		}
		
	}
	

	public void supprimerExamen(int idExamen) throws SQLException {
	    Connection connexion = Connexion.getConnexion();
	    
	    /* --- STEP 1: Delete Results (Scores) --- */
	    // If we don't do this, we can't delete the exam because it has scores linked to it
	    String reqResultat = "DELETE FROM resultat WHERE id_examen = ?";
	    PreparedStatement prRes = connexion.prepareStatement(reqResultat);
	    prRes.setInt(1, idExamen);
	    prRes.executeUpdate();
	    
	    /* --- STEP 2: Delete Questions (and their Choices) --- */
	    // Instead of writing complex SQL here, we reuse the tool we already built!
	    QuestionBD qDB = new QuestionBD();
	    
	    // Get the list of questions for this exam
	    ArrayList<Question> questions = qDB.recupererParExamen(idExamen);
	    
	    // Loop through them and delete them one by one
	    // (This automatically deletes the choices too, because supprimerQuestion does that)
	    for (Question q : questions) {
	        qDB.supprimerQuestion(q.getId()); 
	    }
	    
	    /* --- STEP 3: Delete the Exam Header --- */
	    // Now that the exam is empty (no scores, no questions), we can delete it.
	    String reqExam = "DELETE FROM examen WHERE id = ?";
	    PreparedStatement prExam = connexion.prepareStatement(reqExam);
	    prExam.setInt(1, idExamen);
	    prExam.executeUpdate();
	    
	    System.out.println("Examen " + idExamen + " and all its content deleted.");
	}
	
}
