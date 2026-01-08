package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResultatDAO {

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
}