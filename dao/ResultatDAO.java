package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import modele.Resultat;

public class ResultatDAO {

	// Sauvegarder (Déjà fait)
	public void sauvegarderResultat(int idEtudiant, int idExamen, double note) {
		Connection cnx = Connexion.getConnexion();
		try {
			String sql = "INSERT INTO resultat (id_etudiant, id_examen, note_sur_20) VALUES (?, ?, ?)";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			ps.setInt(1, idEtudiant);
			ps.setInt(2, idExamen);
			ps.setDouble(3, note);
			
			ps.executeUpdate();
			System.out.println("💾 Résultat sauvegardé en base de données !");
			
		} catch (SQLException e) {
			System.err.println("❌ Erreur sauvegarde résultat");
			e.printStackTrace();
		}
	}
	
	// NOUVELLE MÉTHODE : Lire la liste des notes pour un examen donné
	public ArrayList<Resultat> getResultatsParExamen(int idExamen) {
		Connection cnx = Connexion.getConnexion();
		ArrayList<Resultat> listeResultats = new ArrayList<>();
		
		try {
			// La requête magique avec JOIN pour avoir le nom de l'étudiant directement
			String sql = "SELECT r.id, r.id_etudiant, r.note_sur_20, e.nom_complet "
					   + "FROM resultat r "
					   + "JOIN etudiant e ON r.id_etudiant = e.id "
					   + "WHERE r.id_examen = ?";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, idExamen);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("id");
				int idEtudiant = rs.getInt("id_etudiant");
				double note = rs.getDouble("note_sur_20");
				String nomEtudiant = rs.getString("nom_complet");
				
				// On crée l'objet et on l'ajoute à la liste
				Resultat res = new Resultat(id, idExamen, idEtudiant, nomEtudiant, note);
				listeResultats.add(res);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listeResultats;
	}
	
	
	// Vérifie si un étudiant a déjà une note pour cet examen
	public boolean aDejaPasse(int idEtudiant, int idExamen) {
		Connection cnx = Connexion.getConnexion();
		boolean existe = false;
		
		try {
			String sql = "SELECT COUNT(*) FROM resultat WHERE id_etudiant = ? AND id_examen = ?";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, idEtudiant);
			ps.setInt(2, idExamen);
			
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// Si le compteur est > 0, c'est qu'il l'a déjà passé
				if (rs.getInt(1) > 0) {
					existe = true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return existe;
	}
	
	
	// NOUVEAU : Voir mes propres notes
		public java.util.ArrayList<String> getHistoriqueEtudiant(int idEtudiant) {
			Connection cnx = Connexion.getConnexion();
			java.util.ArrayList<String> historique = new java.util.ArrayList<>();
			
			try {
				// Jointure pour récupérer le TITRE de l'examen avec la NOTE
				String sql = "SELECT e.titre, r.note_sur_20, r.date_passage "
						   + "FROM resultat r "
						   + "JOIN examen e ON r.id_examen = e.id "
						   + "WHERE r.id_etudiant = ?";
				
				PreparedStatement ps = cnx.prepareStatement(sql);
				ps.setInt(1, idEtudiant);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					String ligne = "Examen : " + rs.getString("titre") 
								 + " | Note : " + rs.getDouble("note_sur_20") + "/20"
								 + " (" + rs.getTimestamp("date_passage") + ")";
					historique.add(ligne);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return historique;
		}
	
	
	
	
}