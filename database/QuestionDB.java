package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modele.Question;

public class QuestionDB {
	
	// methode bach tsjel l'question bles choix dyalo kaaamlin wst database
	public void sauvegarderQuestion(Question q, int idExamen) throws SQLException {
		Connection connexion = Connexion.getConnexion();
		
		String request = "INSERT INTO question (id_examen, enonce, media) VALUES (?, ?, ?)";
		// sift SQL request l DataBase
		PreparedStatement pr = connexion.prepareStatement(request, Statement.RETURN_GENERATED_KEYS); //3mli dik handshake + l ID li generetihli rej3oli f getGeneratedKeys();
		pr.setInt(1, idExamen);
		pr.setString(2, q.getEnonce());
		pr.setString(3, q.getMedia());
		pr.executeUpdate();
		// SQL request tsift
		
		// retreive l'ID litgenera w diro f rs
		ResultSet rs = pr.getGeneratedKeys();
		if (rs.next()) {
			int newId = rs.getInt(1);
			q.setId(newId);
			
			//db hanaya ghadi n inseriw les choix li linked lhad qst f tableau dyal choix.
			String requestChoix = "INSERT INTO choix (id_question, texte_choix, est_correct) VALUES(?, ?, ?)";
			PreparedStatement prChoix = connexion.prepareStatement(requestChoix);
			
			ArrayList<String> choix = q.getChoix();
			ArrayList<Integer> bonnesReponses = q.getBonnesReponses();
			for (int i = 0; i < choix.size(); i++) {
				prChoix.setInt(1, newId); //hada id dyal qst li yalah generina
				prChoix.setString(2, choix.get(i)); // hada enonce dyal qst
				if (bonnesReponses.contains(i)) {
					prChoix.setInt(3, 1);	//hada qst correct
				} else
					prChoix.setInt(3, 0); //hada qst faux
				prChoix.executeUpdate();
			}
			
			
		}
		
		
	}
	
	
}
