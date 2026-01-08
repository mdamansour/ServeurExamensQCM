package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modele.Question;

public class QuestionDAO {

	// --- MODIFICATION ICI : On ajoute 'int idExamen' car la BDD l'exige maintenant ---
	public void sauvegarderQuestion(Question q, int idExamen) {
		Connection cnx = Connexion.getConnexion();
		
		try {
			// 1. On insère la QUESTION avec son lien vers l'EXAMEN (id_examen)
			// La nouvelle structure de la table est : id, id_examen, enonce, media
			String sqlQuestion = "INSERT INTO question (id_examen, enonce, media) VALUES (?, ?, ?)";
			
			PreparedStatement ps = cnx.prepareStatement(sqlQuestion, Statement.RETURN_GENERATED_KEYS);
			
			// Remplissage des paramètres
			ps.setInt(1, idExamen);       // Le lien avec l'examen parent (Obligatoire)
			ps.setString(2, q.getEnonce());
			ps.setString(3, q.getMedia());
			
			ps.executeUpdate();
			
			// 2. On récupère l'ID généré pour cette question
			ResultSet rs = ps.getGeneratedKeys();
			int idQuestion = 0;
			if (rs.next()) {
				idQuestion = rs.getInt(1);
				q.setId(idQuestion); // Mise à jour de l'objet Java
			}
			
			// 3. On insère les CHOIX (Inchangé)
			String sqlChoix = "INSERT INTO choix (id_question, texte_choix, est_correct) VALUES (?, ?, ?)";
			PreparedStatement psChoix = cnx.prepareStatement(sqlChoix);
			
			for (int i = 0; i < q.getChoix().size(); i++) {
				String texte = q.getChoix().get(i);
				boolean estCorrect = q.getBonnesReponses().contains(i);
				
				psChoix.setInt(1, idQuestion);
				psChoix.setString(2, texte);
				psChoix.setBoolean(3, estCorrect);
				
				psChoix.executeUpdate();
			}
			
			System.out.println("✅ Question enregistrée (ID: " + idQuestion + ") pour l'examen " + idExamen);
			
		} catch (SQLException e) {
			System.err.println("❌ Erreur lors de la sauvegarde de la question.");
			e.printStackTrace();
		}
	}
	
	
	// Méthode pour RÉCUPÉRER une question (Inchangée, elle marche toujours)
	public Question getQuestionParId(int id) {
		Connection cnx = Connexion.getConnexion();
		Question q = null;
		
		try {
			String sql = "SELECT * FROM question WHERE id = ?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				String enonce = rs.getString("enonce");
				String media = rs.getString("media");
				q = new Question(id, enonce, media);
				
				// Récupération des choix
				String sqlChoix = "SELECT * FROM choix WHERE id_question = ?";
				PreparedStatement psChoix = cnx.prepareStatement(sqlChoix);
				psChoix.setInt(1, id);
				ResultSet rsChoix = psChoix.executeQuery();
				
				int index = 0;
				while (rsChoix.next()) {
					String texte = rsChoix.getString("texte_choix");
					boolean estCorrect = rsChoix.getBoolean("est_correct");
					
					q.ajouterChoix(texte);
					if (estCorrect) {
						q.ajouterBonneReponse(index);
					}
					index++;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return q;
	}
}