package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modele.Examen;
import modele.Professeur;
import modele.Question;

public class ExamenDAO {

	public void sauvegarderExamen(Examen exam) {
		Connection cnx = Connexion.getConnexion();
		try {
			// 1. Sauvegarder l'EXAMEN (Le Parent)
			String sql = "INSERT INTO examen (titre, filiere, niveau, id_prof, point_si_juste, point_si_faux, point_si_vide) VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, exam.getTitre());
			ps.setString(2, exam.getFiliere());
			ps.setString(3, exam.getNiveau());
			ps.setInt(4, exam.getProfesseur().getId()); 
			
			ps.setDouble(5, exam.getPointSiJuste());
			ps.setDouble(6, exam.getPointSiFaux());
			ps.setDouble(7, exam.getPointSiVide());
			
			ps.executeUpdate();
			
			// Récupérer l'ID généré pour l'examen
			ResultSet rs = ps.getGeneratedKeys();
			int idExamen = 0;
			if (rs.next()) {
				idExamen = rs.getInt(1);
				exam.setId(idExamen);
			}
			System.out.println("✅ Examen créé avec succès (ID: " + idExamen + ")");

			// 2. Sauvegarder les QUESTIONS (Les Enfants)
			// C'est ici que ça change ! On appelle QuestionDAO pour créer les questions
			// en leur donnant l'ID de l'examen qu'on vient de créer.
			
			QuestionDAO qDao = new QuestionDAO();
			
			for (Question q : exam.getQuestions()) {
				// On sauvegarde la question et on lui dit "Ton père est l'examen #idExamen"
				qDao.sauvegarderQuestion(q, idExamen);
			}
			
		} catch (SQLException e) {
			System.err.println("❌ Erreur lors de la sauvegarde de l'examen");
			e.printStackTrace();
		}
	}
	
	// Méthode pour CHARGER un examen (mise à jour pour la nouvelle structure)
	public Examen getExamenParId(int id) {
		Connection cnx = Connexion.getConnexion();
		Examen exam = null;
		
		try {
			// 1. Infos Examen
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
				
				// Professeur placeholder
				Professeur prof = new Professeur(rs.getInt("id_prof"), "Prof", "Inconnu"); 
				
				exam = new Examen(id, titre, filiere, niveau, prof, pJuste, pFaux, pVide);
				
				// 2. Récupérer les QUESTIONS liées à cet examen
				// On cherche directement dans la table 'question' avec id_examen
				String sqlQ = "SELECT id FROM question WHERE id_examen = ?";
				PreparedStatement psQ = cnx.prepareStatement(sqlQ);
				psQ.setInt(1, id);
				ResultSet rsQ = psQ.executeQuery();
				
				QuestionDAO qDao = new QuestionDAO();
				while (rsQ.next()) {
					int idQuestion = rsQ.getInt("id");
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

	// Méthode demandée par le cahier des charges (Filtrage)
	public ArrayList<Examen> getExamensDisponibles(String niveau, String filiere) {
		Connection cnx = Connexion.getConnexion();
		ArrayList<Examen> liste = new ArrayList<>();
		
		try {
			String sql = "SELECT * FROM examen WHERE niveau = ? AND filiere = ?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, niveau);
			ps.setString(2, filiere);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				// On charge une version légère de l'examen (sans les questions pour l'instant)
				Examen e = new Examen(
					rs.getInt("id"), 
					rs.getString("titre"), 
					rs.getString("filiere"), 
					rs.getString("niveau"), 
					null, 
					rs.getDouble("point_si_juste"),
					rs.getDouble("point_si_faux"),
					rs.getDouble("point_si_vide")
				);
				liste.add(e);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return liste;
	}
}