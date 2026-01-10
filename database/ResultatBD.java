package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Resultat;

public class ResultatBD {
	
	// 1. SAVE: Used when the student clicks "Terminer"
	public void sauvegarderResultat(int idEtudiant, int idExamen, double note) {
		try {
			Connection conn = Connexion.getConnexion();
			
			// We save the ID of the student, the Exam, and the Score. 
			// 'NOW()' is a MySQL function that automatically records the current date/time.
			String sql = "INSERT INTO resultat (id_etudiant, id_examen, note_sur_20, date_passage) VALUES (?, ?, ?, NOW())";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idEtudiant);
			ps.setInt(2, idExamen);
			ps.setDouble(3, note);
			
			ps.executeUpdate();
			System.out.println("Note enregistrée avec succès !");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 2. RETRIEVE (For Professor): See all scores for a specific Exam
	public ArrayList<Resultat> recupererParExamen(int idExamen) {
		ArrayList<Resultat> list = new ArrayList<>();
		try {
			Connection conn = Connexion.getConnexion();
			
			// CRITICAL JOIN: We join 'resultat' with 'etudiant' to get the name 'nom_complet'.
			// Without this, you would only see IDs (e.g., "Student 5 got 14/20") which is useless for the Prof.
			String sql = "SELECT r.*, e.nom_complet " +
			             "FROM resultat r " +
			             "JOIN etudiant e ON r.id_etudiant = e.id " +
			             "WHERE r.id_examen = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idExamen);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Resultat r = new Resultat(
					rs.getInt("id"),
					rs.getInt("id_examen"),
					rs.getInt("id_etudiant"),
					rs.getString("nom_complet"), // Fetched from the JOIN
					rs.getDouble("note_sur_20")
				);
				list.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	// 3. RETRIEVE (For Student): See my own history
	public ArrayList<Resultat> recupererParEtudiant(int idEtudiant) {
		ArrayList<Resultat> list = new ArrayList<>();
		try {
			Connection conn = Connexion.getConnexion();
			// No need for JOIN here necessarily, since the student knows their own name.
			String sql = "SELECT * FROM resultat WHERE id_etudiant = ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idEtudiant);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Resultat r = new Resultat(
					rs.getInt("id"),
					rs.getInt("id_examen"),
					rs.getInt("id_etudiant"),
					"Moi", // Placeholder name since it's the student looking at their own list
					rs.getDouble("note_sur_20")
				);
				list.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}