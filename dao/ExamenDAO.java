package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modele.Examen;
import modele.Question;

public class ExamenDAO {

	public void sauvegarderExamen(Examen exam) {
		Connection cnx = Connexion.getConnexion();
		try {
			// 1. Sauvegarder l'EXAMEN lui-même
			String sql = "INSERT INTO examen (titre, filiere, niveau, id_prof, point_si_juste, point_si_faux, point_si_vide) VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, exam.getTitre());
			ps.setString(2, exam.getFiliere());
			ps.setString(3, exam.getNiveau());
			// Attention : Le prof doit avoir été sauvegardé AVANT, sinon son ID sera 0 ou null
			ps.setInt(4, exam.getProfesseur().getId()); 
			
			// Le Barème
			ps.setDouble(5, exam.getPointSiJuste());
			ps.setDouble(6, exam.getPointSiFaux());
			ps.setDouble(7, exam.getPointSiVide());
			
			ps.executeUpdate();
			
			// Récupérer l'ID de l'examen
			ResultSet rs = ps.getGeneratedKeys();
			int idExamen = 0;
			if (rs.next()) {
				idExamen = rs.getInt(1);
				exam.setId(idExamen);
			}
			System.out.println("✅ Examen créé avec succès (ID: " + idExamen + ")");

			// 2. Sauvegarder les LIENS avec les QUESTIONS (Table examen_question)
			// On suppose que les questions existent déjà dans la BDD (elles ont un ID)
			String sqlLien = "INSERT INTO examen_question (id_examen, id_question) VALUES (?, ?)";
			PreparedStatement psLien = cnx.prepareStatement(sqlLien);
			
			for (Question q : exam.getQuestions()) {
				if (q.getId() > 0) { // On vérifie que la question existe bien en base
					psLien.setInt(1, idExamen);
					psLien.setInt(2, q.getId());
					psLien.executeUpdate();
					System.out.println("   -> Question " + q.getId() + " ajoutée à l'examen.");
				} else {
					System.err.println("   ⚠️ Attention : La question '" + q.getEnonce() + "' n'a pas d'ID. Elle n'a pas été sauvegardée avant ?");
				}
			}
			
		} catch (SQLException e) {
			System.err.println("❌ Erreur lors de la sauvegarde de l'examen");
			e.printStackTrace();
		}
	}
	
	// Méthode pour CHARGER un examen complet (avec ses questions)
	public Examen getExamenParId(int id) {
		Connection cnx = Connexion.getConnexion();
		Examen exam = null;
		
		try {
			// 1. Récupérer les infos de l'examen
			String sql = "SELECT * FROM examen WHERE id = ?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				String titre = rs.getString("titre");
				String filiere = rs.getString("filiere");
				String niveau = rs.getString("niveau");
				
				double pJuste = rs.getDouble("point_si_juste");
				double pFaux = rs.getDouble("point_si_faux");
				double pVide = rs.getDouble("point_si_vide");
				
				// ASTUCE : Pour l'instant, on met un prof "null" ou vide pour simplifier
				// (Sinon il faudrait aussi appeler ProfesseurDAO)
				modele.Professeur profInconnu = new modele.Professeur("Prof (chargé)", "Inconnu");
				
				// On crée l'objet Examen
				exam = new Examen(id, titre, filiere, niveau, profInconnu, pJuste, pFaux, pVide);
				
				// 2. Récupérer les ID des questions liées
				String sqlLien = "SELECT id_question FROM examen_question WHERE id_examen = ?";
				PreparedStatement psLien = cnx.prepareStatement(sqlLien);
				psLien.setInt(1, id);
				ResultSet rsLien = psLien.executeQuery();
				
				// Pour chaque ID trouvé, on charge la question complète via QuestionDAO
				QuestionDAO qDao = new QuestionDAO();
				while (rsLien.next()) {
					int idQuestion = rsLien.getInt("id_question");
					Question q = qDao.getQuestionParId(idQuestion);
					if (q != null) {
						exam.ajouterQuestion(q);
					}
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return exam;
	}
	
	// NOUVEAU : Trouver les examens selon le niveau et la filière de l'étudiant
		public java.util.ArrayList<Examen> getExamensDisponibles(String niveau, String filiere) {
			Connection cnx = Connexion.getConnexion();
			java.util.ArrayList<Examen> liste = new java.util.ArrayList<>();
			
			try {
				// On filtre par niveau ET filière
				String sql = "SELECT * FROM examen WHERE niveau = ? AND filiere = ?";
				PreparedStatement ps = cnx.prepareStatement(sql);
				ps.setString(1, niveau);
				ps.setString(2, filiere);
				
				ResultSet rs = ps.executeQuery();
				
				// Pour l'affichage de la liste, on n'a pas besoin de charger toutes les questions
				// On charge juste les infos de base (Titre, ID...)
				while (rs.next()) {
					Examen e = new Examen(
						rs.getInt("id"), 
						rs.getString("titre"), 
						rs.getString("filiere"), 
						rs.getString("niveau"), 
						null, 0,0,0 // On s'en fiche du reste pour l'instant
					);
					liste.add(e);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return liste;
		}
	
}