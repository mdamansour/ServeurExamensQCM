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
	
	
	// Méthode pour RÉCUPÉRER une question complète (avec ses choix) depuis la BDD
	public Question getQuestionParId(int id) {
		Connection cnx = Connexion.getConnexion();
		Question q = null;
		
		try {
			// 1. On récupère les infos de base (Enoncé, Média)
			String sql = "SELECT * FROM question WHERE id = ?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				// On crée l'objet Question
				String enonce = rs.getString("enonce");
				String media = rs.getString("media");
				q = new Question(id, enonce, media); // Utilise le constructeur complet
				
				// 2. On récupère les CHOIX associés
				String sqlChoix = "SELECT * FROM choix WHERE id_question = ?";
				PreparedStatement psChoix = cnx.prepareStatement(sqlChoix);
				psChoix.setInt(1, id);
				ResultSet rsChoix = psChoix.executeQuery();
				
				// On boucle sur les lignes trouvées dans la table 'choix'
				int index = 0;
				while (rsChoix.next()) {
					String texte = rsChoix.getString("texte_choix");
					boolean estCorrect = rsChoix.getBoolean("est_correct");
					
					// On ajoute le texte à la liste des choix
					q.ajouterChoix(texte);
					
					// Si c'est correct, on ajoute l'index à la liste des bonnes réponses
					if (estCorrect) {
						q.ajouterBonneReponse(index);
					}
					index++;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return q; // Retourne l'objet rempli (ou null si pas trouvé)
	}
	
	
}