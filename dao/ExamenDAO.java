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
}