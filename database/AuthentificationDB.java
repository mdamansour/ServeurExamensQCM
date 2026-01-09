package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import modele.Etudiant;
import modele.Professeur;

public class AuthentificationDB {

    public Object utilisateur(String email, String pass) {
        Connection connexion = null;
        try {
            connexion = Connexion.getConnexion();

            // 1. Chercher chez les Professeurs
            String requestProf = "SELECT * FROM professeur WHERE email = ? AND password = ?";
            PreparedStatement psProf = connexion.prepareStatement(requestProf);
            psProf.setString(1, email);
            psProf.setString(2, pass);
            ResultSet resultatProf = psProf.executeQuery();

            if (resultatProf.next()) {
                // On crée l'objet avec les données de la DB
                return new Professeur(
                		resultatProf.getInt("id"),
                		resultatProf.getString("nom_complet"),
                		resultatProf.getString("specialite"),
                		resultatProf.getString("email"),
                		resultatProf.getString("password")
                );
            }

            // 2. Chercher chez les Etudiants
            String requestEtud = "SELECT * FROM etudiant WHERE email = ? AND password = ?";
            PreparedStatement psEtud = connexion.prepareStatement(requestEtud);
            psEtud.setString(1, email);
            psEtud.setString(2, pass);
            ResultSet resultatEtud = psEtud.executeQuery();

            if (resultatEtud.next()) {
                // On crée l'objet Etudiant
                return new Etudiant(
                		resultatEtud.getInt("id"),
                		resultatEtud.getString("nom_complet"),
                		resultatEtud.getString("filiere"),
                		resultatEtud.getString("niveau"),
                		resultatEtud.getString("matricule"),
                		resultatEtud.getString("email"),
                		resultatEtud.getString("password")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si aucun utilisateur n'est trouvé
    }
}