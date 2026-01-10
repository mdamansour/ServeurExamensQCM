package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modele.Examen;

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
		QuestionDB question = new QuestionDB();
		
		if (resultatExam.next()) {
			for(int i = 0; i<examen.getQuestions().size();i++) {
				question.sauvegarderQuestion(examen.getQuestions().get(i), resultatExam.getInt(1));
			}
		}
		
	}
	
	
	
	
	
}
