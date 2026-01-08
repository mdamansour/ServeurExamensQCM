package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import modele.Etudiant;

public class EtudiantDAO {

    public void sauvegarderEtudiant(Etudiant e) {
        String sql = "INSERT INTO etudiant (nom_complet, filiere, niveau) VALUES (?, ?, ?)";
        try (Connection cnx = Connexion.getConnexion();
             PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, e.getNomComplet());
            ps.setString(2, e.getFiliere());
            ps.setString(3, e.getNiveau());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getInt(1));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Etudiant trouverParNom(String nom) {
        String sql = "SELECT * FROM etudiant WHERE nom_complet = ?";
        try (Connection cnx = Connexion.getConnexion();
             PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, nom);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Etudiant e = new Etudiant(rs.getString("nom_complet"),
                                              rs.getString("filiere"),
                                              rs.getString("niveau"));
                    e.setId(rs.getInt("id"));
                    return e;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}