package main;

import java.util.ArrayList;
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
		System.out.println("     ESPACE ÉTUDIANT - APPLICATION QCM    ");
		System.out.println("==========================================");

		// --- PARTIE 1 : LOGIN / INSCRIPTION ---
		while (etudiantConnecte == null) {
			System.out.println("1. Inscription");
			System.out.println("2. Login");
			System.out.print("👉 Choix : ");
			int choix = 0;
			try { choix = clavier.nextInt(); } catch(Exception e) { choix=0; }
			clavier.nextLine(); 

			if (choix == 1) {
				System.out.print("Nom : "); String nom = clavier.nextLine();
				System.out.print("Filière (ex: Info) : "); String filiere = clavier.nextLine();
				System.out.print("Niveau (ex: M1) : "); String niveau = clavier.nextLine();
				etudiantConnecte = new Etudiant(nom, filiere, niveau);
				etudiantDao.sauvegarderEtudiant(etudiantConnecte);
				System.out.println("✅ Bienvenue " + nom);
			} else if (choix == 2) {
				System.out.print("Nom exact : "); String nom = clavier.nextLine();
				etudiantConnecte = etudiantDao.trouverParNom(nom);
				if (etudiantConnecte != null) System.out.println("👋 Bonjour " + etudiantConnecte.getNomComplet());
				else System.out.println("❌ Inconnu.");
			}
		}

		// --- PARTIE 2 : MENU PRINCIPAL ---
		boolean sessionActive = true;
		while (sessionActive) {
			System.out.println("\n--------------------------------");
			System.out.println("MENU ÉTUDIANT (" + etudiantConnecte.getFiliere() + " " + etudiantConnecte.getNiveau() + ")");
			System.out.println("1. 🎓 Passer un examen (Liste filtrée)");
			System.out.println("2. 📊 Voir mes notes passées");
			System.out.println("0. 🚪 Quitter");
			System.out.print("👉 Votre choix : ");
			int action = clavier.nextInt();
			clavier.nextLine();

			if (action == 0) {
				sessionActive = false;
				System.out.println("Au revoir !");
			}
			
			// === 1. PASSER UN EXAMEN ===
			else if (action == 1) {
				ExamenDAO examDao = new ExamenDAO();
				// LE FILTRE AUTOMATIQUE DEMANDÉ PAR LE CAHIER DES CHARGES
				ArrayList<Examen> disponibles = examDao.getExamensDisponibles(etudiantConnecte.getNiveau(), etudiantConnecte.getFiliere());
				
				if (disponibles.isEmpty()) {
					System.out.println("🚫 Aucun examen disponible pour votre profil (" + etudiantConnecte.getNiveau() + " " + etudiantConnecte.getFiliere() + ").");
				} else {
					System.out.println("\n--- EXAMENS DISPONIBLES ---");
					for (Examen e : disponibles) {
						System.out.println("ID [" + e.getId() + "] : " + e.getTitre());
					}
					
					System.out.print("\n👉 Entrez l'ID de l'examen à passer (ou 0 pour retour) : ");
					int idChoix = clavier.nextInt();
					clavier.nextLine();
					
					if (idChoix > 0) {
						passerExamen(idChoix, etudiantConnecte, clavier);
					}
				}
			}
			
			// === 2. VOIR HISTORIQUE ===
			else if (action == 2) {
				ResultatDAO resDao = new ResultatDAO();
				ArrayList<String> historique = resDao.getHistoriqueEtudiant(etudiantConnecte.getId());
				
				System.out.println("\n--- MES RÉSULTATS ---");
				if (historique.isEmpty()) System.out.println("Aucun résultat.");
				else {
					for (String h : historique) System.out.println(h);
				}
			}
		}
		clavier.close();
	}
	
	// --- MÉTHODE HELPER POUR PASSER L'EXAMEN (Pour garder le main propre) ---
	public static void passerExamen(int idExam, Etudiant etudiant, Scanner clavier) {
		ExamenDAO dao = new ExamenDAO();
		Examen examen = dao.getExamenParId(idExam);
		
		if (examen == null) {
			System.out.println("❌ Examen introuvable.");
			return;
		}
		
		// SÉCURITÉ ANTI-TRICHE
		ResultatDAO resDao = new ResultatDAO();
		if (resDao.aDejaPasse(etudiant.getId(), examen.getId())) {
			System.out.println("⛔ Vous avez déjà passé cet examen !");
			return;
		}
		
		// LOGIQUE EXAMEN
		System.out.println("\n🎓 DÉBUT : " + examen.getTitre());
		System.out.println("⚠️ Barème : Juste(+" + examen.getPointSiJuste() + ") / Faux(" + examen.getPointSiFaux() + ")");
		
		double noteCumulee = 0.0;
		double noteMaxPossible = 0.0;
		
		for (Question q : examen.getQuestions()) {
			double maxQ = q.getScoreMaxPossible(examen.getPointSiJuste());
			noteMaxPossible += maxQ;
			
			System.out.println("\n❓ " + q.getEnonce());
			for(int i=0; i<q.getChoix().size(); i++) System.out.println("   " + (i+1) + ") " + q.getChoix().get(i));
			System.out.println("   0) Passer");
			
			System.out.print("👉 Réponse (ex: 1 3) : ");
			String line = clavier.nextLine();
			
			if (!line.equals("0") && !line.isEmpty()) {
				ArrayList<Integer> rep = new ArrayList<>();
				for(String s : line.split(" ")) {
					try { rep.add(Integer.parseInt(s)-1); } catch(Exception e) {}
				}
				double score = q.calculerScore(rep, examen.getPointSiJuste(), examen.getPointSiFaux());
				noteCumulee += score;
				System.out.println("   -> Score : " + score);
			} else {
				noteCumulee += examen.getPointSiVide();
			}
		}
		
		double noteSur20 = (noteMaxPossible > 0) ? (noteCumulee / noteMaxPossible) * 20 : 0;
		if (noteSur20 < 0) noteSur20 = 0;
		
		System.out.println("\n🏁 FINI ! Note : " + String.format("%.2f", noteSur20) + "/20");
		resDao.sauvegarderResultat(etudiant.getId(), examen.getId(), noteSur20);
	}
}