package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modele.Professeur;

public class ProfesseurBD {
	public Professeur trouverParID(int id) throws SQLException {
		Professeur professeur = null;
		ResultSet resultat = null;
		Statement st = null;
		String request = "SELECT * FROM professeur WHERE id = " + id;

		try {
			st = Connexion.getConnexion().createStatement();
			resultat = st.executeQuery(request);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (resultat.next()) {
			professeur = new Professeur( 
	                resultat.getInt(1), 
	                resultat.getString("nom_complet"), 
	                resultat.getString("specialite"),
	                resultat.getString("email"),
	                resultat.getString("password")
	            );
			}
		
		return professeur;
	}
	
	public static void main(String[] args) {
		ProfesseurBD P1 = new ProfesseurBD();
		try {
			System.out.println(P1.trouverParID(1));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
