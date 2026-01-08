package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modele.Question;

public class QuestionDAO {

	// Méthode pour SAUVEGARDER une question dans la BDD
	public void sauvegarderQuestion(Question q) {
		Connection cnx = Connexion.getConnexion(); // dirli connexion m3a BD laah yjazik bi5eir
		
		try {
			// 1. D'abord, on insère la QUESTION (Enoncé + Média)
			String sqlQuestion = "INSERT INTO question (enonce, media) VALUES (?, ?)"; 
			
			// On demande à récupérer l'ID généré (le numéro automatique)
			PreparedStatement ps = cnx.prepareStatement(sqlQuestion, Statement.RETURN_GENERATED_KEYS); //wjd rassek rah ghadi nsiftlik wa7d row jdida dyal data fiha 2 dyal inputs (?, ?) moraha stocki f ps, la valeur dyal ID li generalk SQL
			ps.setString(1, q.getEnonce()); // dirli fel Enonce dyal row jdida dyal dik ID li yalah derti (ps) la valeur li ana 3ndi f getEnonce
			ps.setString(2, q.getMedia()); // Peut être null, pas grave
			ps.executeUpdate(); //9di gharad, bsmlah
			
			// 2. On récupère l'ID que la base a donné à cette question
			ResultSet rs = ps.getGeneratedKeys();
			int idQuestion = 0;
			if (rs.next()) {
				idQuestion = rs.getInt(1);
				q.setId(idQuestion); // On met à jour l'objet Java avec son vrai ID
			}
			
			// 3. Ensuite, on insère les CHOIX dans la table 'choix'
			String sqlChoix = "INSERT INTO choix (id_question, texte_choix, est_correct) VALUES (?, ?, ?)";
			PreparedStatement psChoix = cnx.prepareStatement(sqlChoix);
			
			// On parcourt la liste des choix de l'objet Java
			for (int i = 0; i < q.getChoix().size(); i++) {
				String texte = q.getChoix().get(i);
				
				// On vérifie si cet index (i) est dans la liste des bonnes réponses
				boolean estCorrect = q.getBonnesReponses().contains(i);
				
				psChoix.setInt(1, idQuestion); // Lien avec le parent
				psChoix.setString(2, texte);
				psChoix.setBoolean(3, estCorrect); // 1 ou 0
				
				psChoix.executeUpdate();
			}
			
			System.out.println("✅ Question enregistrée avec succès ! (ID: " + idQuestion + ")");
			
		} catch (SQLException e) {
			System.err.println("❌ Erreur lors de la sauvegarde de la question.");
			e.printStackTrace();
		}
	}
}