package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import modele.Question;

public class QuestionBD {
	
	// methode bach tsjel l'question bles choix dyalo kaaamlin wst database
	public void sauvegarderQuestion(Question q, int idExamen) throws SQLException {
		Connection connexion = Connexion.getConnexion();
		
		String request = "INSERT INTO question (id_examen, enonce, media) VALUES (?, ?, ?)";
		// sift SQL request l DataBase
		PreparedStatement pr = connexion.prepareStatement(request, Statement.RETURN_GENERATED_KEYS); //3mli dik handshake + l ID li generetihli rej3oli f getGeneratedKeys();
		pr.setInt(1, idExamen);
		pr.setString(2, q.getEnonce());
		pr.setString(3, q.getMedia());
		pr.executeUpdate();
		// SQL request tsift
		
		// retreive l'ID litgenera w diro f rs
		ResultSet rs = pr.getGeneratedKeys();
		if (rs.next()) {
			int newId = rs.getInt(1);
			q.setId(newId);		// hna safi setup dyal id fel question dyalna tdart w objet question wla 3ando id dyalo li tgenera
			
			
			/*--------------------------Choixs-----------------------------****/
			
			//db hanaya ghadi n inseriw les choix li linked lhad qst f tableau dyal choix.
			String requestChoix = "INSERT INTO choix (id_question, texte_choix, est_correct) VALUES(?, ?, ?)";
			PreparedStatement prChoix = connexion.prepareStatement(requestChoix);
			
			ArrayList<String> choix = q.getChoix();
			ArrayList<Integer> bonnesReponses = q.getBonnesReponses();
			for (int i = 0; i < choix.size(); i++) {
				prChoix.setInt(1, newId); //hada id dyal qst li yalah generina
				prChoix.setString(2, choix.get(i)); // hada enonce dyal qst
				if (bonnesReponses.contains(i)) {
					prChoix.setInt(3, 1);	//hada qst correct
				} else
					prChoix.setInt(3, 0); //hada qst faux
				prChoix.executeUpdate();
			}
		}
		
	    System.out.println("Question added successfully.");

	}
	
	
	
	// methode likatsiftlk les questions kamlin likaynin f exam sous forme de ArrayList
	public ArrayList<Question> recupererParExamen(int idExamen) throws SQLException{
		Connection connexion = Connexion.getConnexion();
		ArrayList<Question> questions = new ArrayList<>();
		
		String request = "SELECT * FROM question WHERE id_examen = ?"; //hadi request incomplet lighadi ytsift mn ba3d
		PreparedStatement prQuestion = connexion.prepareStatement(request); // wjd handshake
		prQuestion.setInt(1, idExamen); // 3amar dik ? b la valeur dyal idExamen
		ResultSet resultatQuestion = prQuestion.executeQuery(); //sir 3la lah execute l'request
		
		while (resultatQuestion.next()) {
			Question q = new Question(
					resultatQuestion.getInt(1),  // 3ml f l'objet question jdid id lijbdti mn DB
					resultatQuestion.getString(3),	// .... enonce
					resultatQuestion.getString(4)	// .... Media link
					);

			q.setChoix(chargerChoix(q, connexion));
			questions.add(q);
		}
		
		return questions;
		
	}
	
	
	
	
	/*--------------------------Choixs-----------------------------*/
	public ArrayList<String> chargerChoix(Question question, Connection connexion) throws SQLException{
		
		//hadi hiya la partie lighadi tchargilna les choix f arrayList lighadi n3tiwha l chaque qst
		ArrayList<String> choix = new ArrayList<>();	// hnaya ghadi n3amro les choix dyal koma qst
		
		String requestChoix = "SELECT * FROM choix WHERE id_question = ?";
		PreparedStatement prChoix = connexion.prepareStatement(requestChoix);	//dirlna l handshake m3a la table choix
		prChoix.setInt(1, question.getId());	//dirli f dik ? la valeur dyal id dyal question li 7na fih daba
		ResultSet resultatChoix = prChoix.executeQuery();	
		
		int index = 0;
		
		while(resultatChoix.next()) {
			choix.add(resultatChoix.getString(3));
			
			if(resultatChoix.getInt(4)==1) {


				
				
				//hadi kat7sblek imta wsalti l la bonne reponse
				question.ajouterBonneReponse(index);	
			}
			
			// Increment index for the next row
			index++;
		}
		
		return choix;
	}
	
	
	
	
	// Methode bach tms7 question (khass tms7 les choix dyawlho 3ad tms7o howa)
	public void supprimerQuestion(int idQuestion) throws SQLException {
	    Connection connexion = Connexion.getConnexion();
	    
	
	    // Hada darouri bach may3tikch error dyal Foreign Key
	    String requestChoix = "DELETE FROM choix WHERE id_question = ?";
	    PreparedStatement prChoix = connexion.prepareStatement(requestChoix);
	    prChoix.setInt(1, idQuestion);
	    prChoix.executeUpdate();
	    
	    // 2. Step 2: Delete the question itself
	    // Db 3ad n9dro nms7o question
	    String requestQuestion = "DELETE FROM question WHERE id = ?";
	    PreparedStatement prQuestion = connexion.prepareStatement(requestQuestion);
	    prQuestion.setInt(1, idQuestion);
	    prQuestion.executeUpdate();
	    
	    System.out.println("Question deleted successfully.");
	}
	
	
	
	
}