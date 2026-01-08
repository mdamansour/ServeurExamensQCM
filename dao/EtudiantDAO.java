package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modele.Etudiant;

public class EtudiantDAO {

	// 1. Inscription
	public void sauvegarderEtudiant(Etudiant e) {
		Connection cnx = Connexion.getConnexion();
		try {
			String sql = "INSERT INTO etudiant (nom_complet, filiere, niveau) VALUES (?, ?, ?)";
			PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, e.getNomComplet());
			ps.setString(2, e.getFiliere());
			ps.setString(3, e.getNiveau());
			ps.executeUpdate();

			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				e.setId(rs.getInt(1));
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	// 2. Login
	public Etudiant trouverParNom(String nom) {
		Connection cnx = Connexion.getConnexion();
		Etudiant e = null;
		try {
			String sql = "SELECT * FROM etudiant WHERE nom_complet = ?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, nom);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				e = new Etudiant(rs.getString("nom_complet"), rs.getString("filiere"), rs.getString("niveau"));
				e.setId(rs.getInt("id"));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return e;
	}
}