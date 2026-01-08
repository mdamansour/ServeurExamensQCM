package main;

import java.util.Scanner;
import dao.ExamenDAO;
import modele.Examen;
import modele.Question;

public class PasserExam {

	public static void main(String[] args) {
		Scanner clavier = new Scanner(System.in);
		ExamenDAO dao = new ExamenDAO();

		System.out.println("==========================================");
		System.out.println("   SIMULATION : PASSAGE D'EXAMEN (QCM)   ");
		System.out.println("==========================================");

		// 1. Choix de l'examen
		System.out.print("--> Entrez l'ID de l'examen à passer : ");
		int idExam = clavier.nextInt();

		// 2. Chargement de l'examen depuis la BDD
		Examen examen = dao.getExamenParId(idExam);

		if (examen == null) {
			System.out.println("❌ Erreur : Aucun examen trouvé avec l'ID " + idExam);
			System.exit(0);
		}

		System.out.println("\n🎓 EXAMEN : " + examen.getTitre());
		System.out.println("📝 Consignes de notation :");
		System.out.println("   - Bonne réponse : +" + examen.getPointSiJuste());
		System.out.println("   - Mauvaise réponse : " + examen.getPointSiFaux()); // Souvent négatif
		System.out.println("   - Pas de réponse (0) : " + examen.getPointSiVide());
		System.out.println("------------------------------------------\n");

		// Variables pour le calcul de la note
		double noteCumulee = 0.0;     // Les points que l'étudiant gagne
		double noteMaxPossible = 0.0; // Le total s'il avait tout juste

		// 3. Boucle sur les questions
		int numeroQ = 1;
		for (Question q : examen.getQuestions()) {
			// On augmente le max possible (pour la conversion /20 à la fin)
			noteMaxPossible += examen.getPointSiJuste();

			System.out.println("Question " + numeroQ + " : " + q.getEnonce());
			
			// Affichage des choix
			for (int i = 0; i < q.getChoix().size(); i++) {
				// On affiche "1. Paris" au lieu de "0. Paris" pour être user-friendly
				System.out.println("   " + (i + 1) + ") " + q.getChoix().get(i));
			}
			System.out.println("   0) Je ne sais pas (Passer)");

			// Lecture de la réponse
			System.out.print("👉 Votre réponse (numéro) : ");
			int reponseUtilisateur = clavier.nextInt();

			// 4. Algorithme de correction
			if (reponseUtilisateur == 0) {
				// Cas : Réponse vide
				System.out.println("⏸️ Vous avez passé cette question.");
				noteCumulee += examen.getPointSiVide();
			} 
			else {
				// L'utilisateur a entré 1, mais en Java l'index est 0. On fait -1.
				int indexChoisi = reponseUtilisateur - 1;

				// Vérification de validité (pour ne pas crash si il tape 99)
				if (indexChoisi >= 0 && indexChoisi < q.getChoix().size()) {
					
					// Est-ce que cet index est dans la liste des bonnes réponses ?
					if (q.getBonnesReponses().contains(indexChoisi)) {
						System.out.println("✅ Correct !");
						noteCumulee += examen.getPointSiJuste();
					} else {
						System.out.println("❌ Faux !");
						noteCumulee += examen.getPointSiFaux();
					}
					
				} else {
					System.out.println("⚠️ Choix invalide. Considéré comme vide.");
					noteCumulee += examen.getPointSiVide();
				}
			}
			System.out.println("------------------------------------------");
			numeroQ++;
		}

		// 5. Calcul Final sur 20
		double noteSur20 = 0.0;
		
		if (noteMaxPossible > 0) {
			// Formule : (NoteObtenue / TotalPossible) * 20
			noteSur20 = (noteCumulee / noteMaxPossible) * 20;
		}

		// 6. Gestion du min 0 (Si la note est négative, on met 0)
		if (noteSur20 < 0) {
			noteSur20 = 0.0;
		}

		System.out.println("\n==========================================");
		System.out.println("           RÉSULTAT FINAL                 ");
		System.out.println("==========================================");
		System.out.println("Points bruts : " + noteCumulee + " / " + noteMaxPossible);
		// String.format pour afficher seulement 2 chiffres après la virgule (ex: 14.50)
		System.out.println("NOTE SUR 20  : " + String.format("%.2f", noteSur20) + " / 20");
		
		if (noteSur20 >= 10) {
			System.out.println("🎉 Bravo, vous avez validé !");
		} else {
			System.out.println("📉 Échec, il faut réviser.");
		}
		
		clavier.close();
	}
}