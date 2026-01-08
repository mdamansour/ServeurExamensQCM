package modele;

import dao.Connexion;
import dao.ExamenDAO;
import dao.ProfesseurDAO;
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
		
		
System.out.println("--- SCÉNARIO COMPLET DE CRÉATION ---");
		
		// 1. Créer et Sauver le PROFESSEUR
		Professeur prof1 = new Professeur("Dr. Amansour", "Informatique");
		ProfesseurDAO profDao = new ProfesseurDAO();
		profDao.sauvegarderProfesseur(prof1); // Hop, il est dans la BDD, il a un ID !
		
		// 2. Créer et Sauver une QUESTION
		Question q11 = new Question("Quelle est la capitale du Maroc ?");
		q11.ajouterChoix("Casablanca");
		q11.ajouterChoix("Rabat");
		q11.ajouterChoix("Marrakech");
		q11.ajouterBonneReponse(1); // Rabat
		
		QuestionDAO qDao = new QuestionDAO();
		qDao.sauvegarderQuestion(q11); // Hop, elle est dans la BDD, elle a un ID !
		
		// 3. Créer l'EXAMEN
		Examen exam1 = new Examen("Examen Final Java", "Génie Info", "M1", prof1);
		exam1.setBareme(2.0, -1.0, 0.0); // Barème strict
		
		// 4. Ajouter la question (qui vient de la BDD) à l'examen
		exam1.ajouterQuestion(q11);
		
		// 5. Sauvegarder l'EXAMEN (et le lien avec la question)
		ExamenDAO examDao = new ExamenDAO();
		examDao.sauvegarderExamen(exam1);
		
		System.out.println("--- FIN DU SCÉNARIO ---");
		
		
		
	}
}