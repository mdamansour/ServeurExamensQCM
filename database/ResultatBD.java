package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Resultat;

public class ResultatBD {
	
	// 1. SAVE
	public void sauvegarderResultat(int idEtudiant, int idExamen, double note) {
		try {
			Connection conn = Connexion.getConnexion();
			
			String req = "INSERT INTO resultat (id_etudiant, id_examen, note_sur_20, date_passage) VALUES (?, ?, ?, NOW())";
			
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, idEtudiant);
			ps.setInt(2, idExamen);
			ps.setDouble(3, note);
			
			ps.executeUpdate();
			System.out.println("Note enregistrée avec succès !");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 2. RETRIEVE (For Professor)
	public ArrayList<Resultat> recupererParExamen(int idExamen) {
		ArrayList<Resultat> list = new ArrayList<>();
		try {
			Connection conn = Connexion.getConnexion();
			
			String req = "SELECT * FROM resultat WHERE id_examen = ?";
			
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, idExamen);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Resultat r = new Resultat(
					rs.getInt("id"),
					rs.getInt("id_examen"),
					rs.getInt("id_etudiant"),
					rs.getDouble("note_sur_20"),
					rs.getString("date_passage")
				);
				list.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<Resultat> recupererParEtudiant(int idEtudiant) {
		ArrayList<Resultat> list = new ArrayList<>();
		try {
			Connection conn = Connexion.getConnexion();
			
			String req = "SELECT * FROM resultat WHERE id_etudiant = ?";
			
			PreparedStatement ps = conn.prepareStatement(req);
			ps.setInt(1, idEtudiant);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Resultat r = new Resultat(
					rs.getInt("id"),
					rs.getInt("id_examen"),
					rs.getInt("id_etudiant"),
					rs.getDouble("note_sur_20"),
					rs.getString("date_passage")
				);
				list.add(r);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}