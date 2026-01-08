package main;

import java.util.Scanner;
import dao.EtudiantDAO;
import dao.ExamenDAO;
import dao.ResultatDAO;
import modele.Etudiant;
import modele.Examen;
import modele.Question;

public class MenuEtudiant {

	public static void main(String[] args) {
		Scanner clavier = new Scanner(System.in);
		EtudiantDAO etudiantDao = new EtudiantDAO();
		Etudiant etudiantConnecte = null;

		System.out.println("==========================================");
		System.out.println("     ESPACE ÉTUDIANT - PASSAGE D'EXAMEN   ");
		System.out.println("==========================================");

		// --- PARTIE 1 : IDENTIFICATION ---
		while (etudiantConnecte == null) {
			System.out.println("1. Je suis un nouvel étudiant (Inscription)");
			System.out.println("2. Je suis déjà inscrit (Login)");
			System.out.print("👉 Votre choix : ");
			int choix = clavier.nextInt();
			clavier.nextLine(); // Vide buffer

			if (choix == 1) {
				System.out.print("Nom Complet : ");
				String nom = clavier.nextLine();
				System.out.print("Filière (ex: Info) : ");
				String filiere = clavier.nextLine();
				System.out.print("Niveau (ex: M1) : ");
				String niveau = clavier.nextLine();
				
				etudiantConnecte = new Etudiant(nom, filiere, niveau);
				etudiantDao.sauvegarderEtudiant(etudiantConnecte);
				System.out.println("✅ Inscription réussie ! (ID: " + etudiantConnecte.getId() + ")");
				
			} else if (choix == 2) {
				System.out.print("Entrez votre Nom exact : ");
				String nom = clavier.nextLine();
				etudiantConnecte = etudiantDao.trouverParNom(nom);
				
				if (etudiantConnecte != null) {
					System.out.println("👋 Bonjour " + etudiantConnecte.getNomComplet());
				} else {
					System.out.println("❌ Étudiant introuvable.");
				}
			}
		}

		// --- PARTIE 2 : CHOIX EXAMEN ---
		System.out.print("\n👉 Entrez l'ID de l'examen à passer : ");
		int idExam = clavier.nextInt();
		
		ExamenDAO examenDao = new ExamenDAO();
		Examen examen = examenDao.getExamenParId(idExam);

		if (examen == null) {
			System.out.println("❌ Cet examen n'existe pas.");
			System.exit(0);
		}

		// --- PARTIE 3 : PASSAGE ---
		System.out.println("\n🎓 DÉBUT DE L'EXAMEN : " + examen.getTitre());
		System.out.println("⚠️ Barème : Juste(+" + examen.getPointSiJuste() + ") Faux(" + examen.getPointSiFaux() + ") Vide(" + examen.getPointSiVide() + ")");
		System.out.println("------------------------------------------");

		double noteCumulee = 0.0;
		double noteMaxPossible = 0.0;

		int num = 1;
		for (Question q : examen.getQuestions()) {
			noteMaxPossible += examen.getPointSiJuste();
			
			System.out.println("\nQuestion " + num + " : " + q.getEnonce());
			for (int i = 0; i < q.getChoix().size(); i++) {
				System.out.println("   " + (i + 1) + ") " + q.getChoix().get(i));
			}
			System.out.println("   0) Passer la question");
			
			System.out.print("👉 Votre réponse : ");
			int rep = clavier.nextInt();
			
			// LOGIQUE DE CORRECTION
			if (rep == 0) {
				System.out.println("⏸️ Question passée.");
				noteCumulee += examen.getPointSiVide();
			} else {
				int indexChoisi = rep - 1;
				if (indexChoisi >= 0 && indexChoisi < q.getChoix().size()) {
					if (q.getBonnesReponses().contains(indexChoisi)) {
						System.out.println("✅ Correct !");
						noteCumulee += examen.getPointSiJuste();
					} else {
						System.out.println("❌ Faux !");
						noteCumulee += examen.getPointSiFaux();
					}
				} else {
					System.out.println("⚠️ Choix invalide = Réponse vide.");
					noteCumulee += examen.getPointSiVide();
				}
			}
			num++;
		}

		// --- PARTIE 4 : RÉSULTATS & SAUVEGARDE ---
		double noteSur20 = 0.0;
		if (noteMaxPossible > 0) {
			noteSur20 = (noteCumulee / noteMaxPossible) * 20;
		}
		if (noteSur20 < 0) noteSur20 = 0.0; // Pas de note négative sur le bulletin

		System.out.println("\n==========================================");
		System.out.println("           RÉSULTAT FINAL                 ");
		System.out.println("==========================================");
		System.out.println("Note calculée : " + String.format("%.2f", noteSur20) + " / 20");

		// SAUVEGARDE EN BDD
		ResultatDAO resultatDao = new ResultatDAO();
		resultatDao.sauvegarderResultat(etudiantConnecte.getId(), examen.getId(), noteSur20);
		
		System.out.println("\n✅ Votre note a été envoyée au professeur.");
		clavier.close();
	}
}