package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modele.Professeur;

public class ProfesseurDAO {

	public void sauvegarderProfesseur(Professeur p) {
		Connection cnx = Connexion.getConnexion();
		try {
			// On insère le nom et la spécialité
			String sql = "INSERT INTO professeur (nom_complet, specialite) VALUES (?, ?)";
			PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, p.getNomComplet());
			ps.setString(2, p.getSpecialite());
			ps.executeUpdate();

			// On récupère l'ID généré pour le mettre dans l'objet Java
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				p.setId(rs.getInt(1));
			}
			System.out.println("✅ Professeur enregistré (ID: " + p.getId() + ")");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Retrouver un prof par son nom 
	public Professeur trouverParNom(String nomRecherche) {
		Connection cnx = Connexion.getConnexion();
		Professeur prof = null;
		
		try {
			String sql = "SELECT * FROM professeur WHERE nom_complet = ?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, nomRecherche);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				// On a trouvé le prof !
				prof = new Professeur(rs.getString("nom_complet"), rs.getString("specialite"));
				prof.setId(rs.getInt("id")); // Très important de récupérer son ID
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return prof; // Retourne l'objet Prof ou null si pas trouvé
	}
	
}