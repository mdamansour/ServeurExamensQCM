package database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import modele.Etudiant;

public class EtudiantBD {
	
	public Etudiant trouverParID(int id) throws SQLException {
		Etudiant etudiant = null;
		ResultSet resultat = null;
		Statement st = null;
		String request = "SELECT * FROM etudiant WHERE id = " + id;

		try {
			st = Connexion.getConnexion().createStatement();
			resultat = st.executeQuery(request);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (resultat.next()) {
			etudiant = new Etudiant(
	                resultat.getInt(1), 
	                resultat.getString(2), 
	                resultat.getString(3),
	                resultat.getString(4),
	                resultat.getString(5),
	                resultat.getString(6)
	            );
			}
		
		return etudiant;
		
	}
	
	public static void main(String[] args) {
		EtudiantBD E1 = new EtudiantBD();
		try {

			
			System.out.println(			E1.trouverParID(1));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
