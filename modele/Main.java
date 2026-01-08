package modele;

import dao.Connexion;
import dao.QuestionDAO;
import modele.Examen;
import modele.Professeur;
import modele.Question;

public class Main {

	public static void main(String[] args) {
		
		// 1. Création du Professeur
		Professeur prof = new Professeur("Dr. Amansour", "Informatique");
		
		// 2. Création de l'Examen (Cible : M1 / Info)
		Examen exam = new Examen("Programmation Java", "Info", "M1", prof);
		
		// 3. Configuration du Barème (Examen difficile !)
		// +2 points si juste, -1 point si faux, 0 si vide
		exam.setBareme(2.0, -1.0, 0.0);
		
		// 4. Création d'une Question
		Question q1 = new Question("Quelle est la capitale de l'Allemagne ?");
		q1.ajouterChoix("Paris");   // index 0
		q1.ajouterChoix("Berlin");  // index 1
		q1.ajouterChoix("Madrid");  // index 2
		
		q1.ajouterBonneReponse(1); // Berlin est la bonne réponse
		
		// 5. Ajout de la question à l'examen
		exam.ajouterQuestion(q1);
		
		// 6. Affichage du résultat
		System.out.println("--- DÉTAILS DE L'EXAMEN ---");
		System.out.println(exam.toString());
		
		System.out.println("\n--- LISTE DES QUESTIONS ---");
		for(Question q : exam.getQuestions()) {
			System.out.println(q.toString());
		}
		
		System.out.println("\nNote totale possible: " + exam.getNoteTotale() + " points." + q1.getBonnesReponses());
		
		//------------------------------------------------------------------------//
		
		
		
		System.out.println("--- DÉBUT DU TEST BASE DE DONNÉES ---");

		// 1. Création d'une question en Java (Mémoire RAM)
		Question q = new Question("Quel langage utilise-t-on pour Android ?");
		
		// 2. Ajout des choix
		q.ajouterChoix("Python"); // index 0
		q.ajouterChoix("Java");   // index 1
		q.ajouterChoix("C++");    // index 2
		q.ajouterChoix("Kotlin"); // index 3
		
		// 3. Définition des bonnes réponses (Java et Kotlin sont valides)
		q.ajouterBonneReponse(1);
		q.ajouterBonneReponse(3);
		
		// 4. Appel du DAO pour envoyer tout ça dans MySQL
		QuestionDAO dao = new QuestionDAO();
		
		System.out.println("Envoi de la question à la base de données...");
		dao.sauvegarderQuestion(q);
		
		System.out.println("--- FIN DU TEST ---");
	}
		
		
	}
