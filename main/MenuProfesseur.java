package main;

import java.util.ArrayList;
import java.util.Scanner;

import dao.ExamenDAO;
import dao.ProfesseurDAO;
import dao.ResultatDAO;
import modele.Examen;
import modele.Professeur;
import modele.Question;
import modele.Resultat;

public class MenuProfesseur {

	public static void main(String[] args) {
		Scanner clavier = new Scanner(System.in);
		ProfesseurDAO profDao = new ProfesseurDAO();
		Professeur profConnecte = null;
		
		System.out.println("==========================================");
		System.out.println("        ESPACE PROFESSEUR - GESTION       ");
		System.out.println("==========================================");
		
		// --- PHASE 1 : LOGIN / INSCRIPTION ---
		while (profConnecte == null) {
			System.out.println("1. Nouveau professeur (Inscription)");
			System.out.println("2. Déjà inscrit (Login)");
			System.out.print("👉 Choix : ");
			int choix = 0;
			try { choix = clavier.nextInt(); } catch(Exception e) { choix=0; }
			clavier.nextLine(); // Vider buffer
			
			if (choix == 1) {
				System.out.print("Nom Complet : ");
				String nom = clavier.nextLine();
				System.out.print("Spécialité : ");
				String spec = clavier.nextLine();
				profConnecte = new Professeur(nom, spec);
				profDao.sauvegarderProfesseur(profConnecte); 
				System.out.println("✅ Bienvenue Prof. " + nom);
				
			} else if (choix == 2) {
				System.out.print("Nom Exact : ");
				String nom = clavier.nextLine();
				profConnecte = profDao.trouverParNom(nom);
				
				if (profConnecte != null) {
					System.out.println("👋 Bonjour Prof. " + profConnecte.getNomComplet());
				} else {
					System.out.println("❌ Inconnu.");
				}
			}
		}
		
		// --- PHASE 2 : MENU PRINCIPAL ---
		boolean sessionActive = true;
		while (sessionActive) {
			System.out.println("\n--------------------------------");
			System.out.println("QUE VOULEZ-VOUS FAIRE ?");
			System.out.println("1. 📝 Créer un nouvel Examen");
			System.out.println("2. 📊 Voir les résultats d'un Examen");
			System.out.println("0. 🚪 Quitter");
			System.out.print("👉 Votre choix : ");
			int action = clavier.nextInt();
			clavier.nextLine();
			
			if (action == 0) {
				sessionActive = false;
				System.out.println("Au revoir !");
			}
			
			// === OPTION 1 : CRÉATION D'EXAMEN ===
			else if (action == 1) {
				System.out.println("\n--- NOUVEL EXAMEN ---");
				System.out.print("Titre : ");
				String titre = clavier.nextLine();
				System.out.print("Filière (ex: Info) : ");
				String filiere = clavier.nextLine();
				System.out.print("Niveau (ex: M1) : ");
				String niveau = clavier.nextLine();
				
				Examen examen = new Examen(titre, filiere, niveau, profConnecte);
				
				System.out.print("Points Juste (+): ");
				double pJuste = clavier.nextDouble();
				System.out.print("Points Faux (-): ");
				double pFaux = clavier.nextDouble();
				System.out.print("Points Vide (0): ");
				double pVide = clavier.nextDouble();
				clavier.nextLine();
				examen.setBareme(pJuste, pFaux, pVide);
				
				// Boucle Question
				boolean ajoutQ = true;
				// NOTE: On n'utilise plus QuestionDAO ici directement
				
				while (ajoutQ) {
					System.out.print("\nÉnoncé Question : ");
					String enonce = clavier.nextLine();
					Question q = new Question(enonce);
					
					System.out.print("Nb Choix : ");
					int nb = clavier.nextInt();
					clavier.nextLine();
					for(int i=0; i<nb; i++) {
						System.out.print(" > Choix " + (i+1) + ": ");
						q.ajouterChoix(clavier.nextLine());
					}
					
					System.out.println("Bonnes réponses (ex: 1). Tapez 0 pour finir.");
					while(true) {
						int rep = clavier.nextInt();
						if(rep == 0) break;
						if(rep > 0 && rep <= nb) q.ajouterBonneReponse(rep-1);
					}
					clavier.nextLine();
					
					// IMPORTANT : On ajoute seulement à l'objet, on ne sauvegarde pas encore en BDD
					// C'est ExamenDAO qui fera le travail de groupe à la fin
					examen.ajouterQuestion(q);
					
					System.out.print("Autre question ? (1=Oui, 0=Non) : ");
					if(clavier.nextInt() == 0) ajoutQ = false;
					clavier.nextLine();
				}
				
				// SAUVEGARDE FINALE
				// ExamenDAO va sauvegarder l'examen ET ses questions en cascade
				ExamenDAO eDao = new ExamenDAO();
				eDao.sauvegarderExamen(examen);
			}
			
			// === OPTION 2 : VOIR LES RÉSULTATS ===
			else if (action == 2) {
				System.out.print("\nEntrez l'ID de l'examen à consulter : ");
				int idRecherche = clavier.nextInt();
				
				ResultatDAO rDao = new ResultatDAO();
				ArrayList<Resultat> liste = rDao.getResultatsParExamen(idRecherche);
				
				System.out.println("\n--- RÉSULTATS POUR L'EXAMEN ID " + idRecherche + " ---");
				if (liste.isEmpty()) {
					System.out.println("Aucune note enregistrée pour le moment.");
				} else {
					System.out.println("------------------------------------------------");
					System.out.println(String.format("%-20s | %s", "ÉTUDIANT", "NOTE / 20"));
					System.out.println("------------------------------------------------");
					for (Resultat r : liste) {
						System.out.println(String.format("%-20s | %5.2f", r.getNomEtudiant(), r.getNote()));
					}
					System.out.println("------------------------------------------------");
				}
			}
		}
		clavier.close();
	}
}