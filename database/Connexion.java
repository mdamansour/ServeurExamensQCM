package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
	
	private static String URL = "jdbc:mysql://localhost:3306/examens_qcm_bd";
	private static String USER = "root";
	private static String PASSWORD = ""; 
	private static Connection maConnexion = null;
	
	public static Connection getConnexion() throws SQLException {
		
		if (maConnexion == null || maConnexion.isClosed()) { //execute only mni katconnecta awl merra OR mni kit9ta3 connexion
			try {
				Class.forName("com.mysql.jdbc.Driver"); //hada kichargi driver fel memoire
			} catch (ClassNotFoundException e) {
				
				System.err.println("ERREUR : Driver MySQL introuvable.");
				e.printStackTrace();
			}
			
			try {
				maConnexion = DriverManager.getConnection(URL, USER, PASSWORD); // hada liki3mlk lconnexion (kisift les coordonnées)
				System.out.println("Ana tconnectit l database, koulchi mezyan!");
			} catch (SQLException e) {
				
				System.err.println("ERREUR : Impossible de se connecter à la base de données.");
				e.printStackTrace();
			}
		}
		return maConnexion;
	}
}
