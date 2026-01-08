package main;

import java.util.Scanner;

import dao.ExamenDAO;
import dao.ProfesseurDAO;
import dao.QuestionDAO;
import modele.Examen;
import modele.Professeur;
import modele.Question;

public class MenuProfesseur {

	public static void main(String[] args) {
		Scanner clavier = new Scanner(System.in);
		ProfesseurDAO profDao = new ProfesseurDAO();
		Professeur profConnecte = null;
		
		System.out.println("==========================================");
		System.out.println("   ESPACE PROFESSEUR - CRÉATION D'EXAMEN  ");
		System.out.println("==========================================");
		
		// --- PARTIE 1 : IDENTIFICATION ---
		while (profConnecte == null) {
			System.out.println("1. Je suis un nouveau professeur (Inscription)");
			System.out.println("2. Je suis déjà inscrit (Login)");
			System.out.print("👉 Votre choix : ");
			int choix = clavier.nextInt();
			clavier.nextLine(); // Vider le buffer
			
			if (choix == 1) {
				System.out.print("Entrez votre Nom Complet : ");
				String nom = clavier.nextLine();
				System.out.print("Entrez votre Spécialité : ");
				String spec = clavier.nextLine();
				profConnecte = new Professeur(nom, spec);
				profDao.sauvegarderProfesseur(profConnecte); 
				System.out.println("✅ Compte créé ! Bienvenue Prof. " + nom);
				
			} else if (choix == 2) {
				System.out.print("Entrez votre Nom Exact : ");
				String nom = clavier.nextLine();
				profConnecte = profDao.trouverParNom(nom);
				
				if (profConnecte != null) {
					System.out.println("👋 Bon retour, Prof. " + profConnecte.getNomComplet());
				} else {
					System.out.println("❌ Professeur introuvable. Réessayez.");
				}
			}
		}
		
		// --- PARTIE 2 : CONFIGURATION DE L'EXAMEN ---
		System.out.println("\n--- NOUVEL EXAMEN ---");
		System.out.print("Titre de l'examen : ");
		String titre = clavier.nextLine();
		System.out.print("Filière cible : ");
		String filiere = clavier.nextLine();
		System.out.print("Niveau cible : ");
		String niveau = clavier.nextLine();
		
		Examen examen = new Examen(titre, filiere, niveau, profConnecte);
		
		System.out.println("\n--- CONFIGURATION DU BARÈME ---");
		System.out.print("Points si JUSTE (ex: 2) : ");
		double pJuste = clavier.nextDouble();
		System.out.print("Points si FAUX (ex: -1) : ");
		double pFaux = clavier.nextDouble();
		System.out.print("Points si VIDE (ex: 0) : ");
		double pVide = clavier.nextDouble();
		clavier.nextLine(); 
		
		examen.setBareme(pJuste, pFaux, pVide);
		
		// --- PARTIE 3 : AJOUT DES QUESTIONS (LOGIQUE AMÉLIORÉE) ---
		boolean continuer = true;
		QuestionDAO questionDao = new QuestionDAO();
		
		while (continuer) {
			System.out.println("\n--- AJOUT D'UNE QUESTION ---");
			System.out.print("Énoncé de la question : ");
			String enonce = clavier.nextLine();
			
			Question q = new Question(enonce);
			
			// 3.1 Ajout des choix
			System.out.print("Combien de choix possibles ? ");
			int nbChoix = clavier.nextInt();
			clavier.nextLine(); 
			
			for (int i = 0; i < nbChoix; i++) {
				System.out.print("   > Choix n°" + (i+1) + " : ");
				String texteChoix = clavier.nextLine();
				q.ajouterChoix(texteChoix);
			}
			
			// 3.2 Définition des bonnes réponses (BOUCLE DE SÉCURITÉ)
			System.out.println("Quelles sont les bonnes réponses ?");
			boolean ajoutReponses = true;
			
			while (ajoutReponses) {
				System.out.print("👉 Entrez le numéro d'une réponse correcte (ou 0 pour finir) : ");
				int numRep = clavier.nextInt();
				
				if (numRep == 0) {
					// On vérifie qu'il y a au moins une réponse correcte avant de sortir
					if (q.getBonnesReponses().isEmpty()) {
						System.out.println("⚠️ Erreur : Il faut au moins une réponse correcte !");
					} else {
						ajoutReponses = false; // On sort de la boucle
					}
				} 
				else if (numRep < 1 || numRep > nbChoix) {
					// SÉCURITÉ : On empêche de choisir une réponse qui n'existe pas
					System.out.println("❌ Erreur : Le choix " + numRep + " n'existe pas. (Max: " + nbChoix + ")");
				} 
				else {
					// Tout est bon, on ajoute (Attention : numRep 1 devient index 0)
					q.ajouterBonneReponse(numRep - 1);
					System.out.println("✅ Réponse " + numRep + " marquée comme correcte.");
				}
			}
			clavier.nextLine(); // Vider buffer après les int
			
			// SAUVEGARDE
			questionDao.sauvegarderQuestion(q);
			examen.ajouterQuestion(q);
			
			System.out.print("\nVoulez-vous ajouter une autre question ? (1=Oui, 0=Non) : ");
			int rep = clavier.nextInt();
			clavier.nextLine();
			if (rep == 0) continuer = false;
		}
		
		// --- PARTIE 4 : SAUVEGARDE FINALE ---
		System.out.println("\nEnregistrement de l'examen complet...");
		ExamenDAO examenDao = new ExamenDAO();
		examenDao.sauvegarderExamen(examen);
		
		System.out.println("✅ EXAMEN CRÉÉ AVEC SUCCÈS ! (ID : " + examen.getId() + ")");
		clavier.close();
	}
}