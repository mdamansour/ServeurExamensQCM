package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

	// Paramètres de connexion XAMPP par défaut
	private static final String URL = "jdbc:mysql://localhost:3306/examens_qcm_bd";
	private static final String USER = "root";
	private static final String PASSWORD = ""; // Vide sous XAMPP par défaut

	// Objet Connection unique (Singleton pour éviter d'ouvrir 50 connexions)
	private static Connection cnx = null;

	// Méthode pour récupérer la connexion
	public static Connection getConnexion() {
		try {
			if (cnx == null || cnx.isClosed()) {
				// 1. Chargement du Driver MySQL

				Class.forName("com.mysql.jdbc.Driver");
				// 2. Création de la connexion
				cnx = DriverManager.getConnection(URL, USER, PASSWORD);
				System.out.println("--- Connexion à la Base de Données RÉUSSIE ! ---");
			}
		} catch (ClassNotFoundException e) {
			System.err.println("ERREUR : Driver MySQL introuvable.");
			System.err.println("As-tu ajouté le fichier .jar 'mysql-connector' au projet ?");
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("ERREUR : Impossible de se connecter à XAMPP.");
			System.err.println("Vérifie que Apache et MySQL sont bien START dans XAMPP.");
			e.printStackTrace();
		}
		return cnx;
	}
	
}