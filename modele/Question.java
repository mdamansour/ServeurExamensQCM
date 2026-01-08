package modele;

import java.util.ArrayList;

public class Question {
	// Attributs
	private int id;
	private String enonce;
	private ArrayList<String> choix;
	private ArrayList<Integer> bonnesReponses; // Liste des index corrects (0, 1, 3...)
	private String media; // Chemin vers image/video
	
	// Constructeur de création
	public Question(String enonce) {
		this.enonce = enonce;
		this.choix = new ArrayList<>(); 
		this.bonnesReponses = new ArrayList<>();
	}
	
	// Constructeur complet (depuis BDD)
	public Question(int id, String enonce, String media) {
		this.id = id;
		this.enonce = enonce;
		this.media = media;
		this.choix = new ArrayList<>();
		this.bonnesReponses = new ArrayList<>();
	}
	
	// --- MÉTHODES MÉTIER (LE CERVEAU) ---
	
	/**
	 * Calcule la note de cette question en fonction des réponses de l'élève.
	 * @param indicesReponses : Les cases cochées par l'élève (ex: [0, 2])
	 * @param baremeJuste : Points gagnés par bonne case (ex: +2)
	 * @param baremeFaux : Points perdus par mauvaise case (ex: -1)
	 * @return Le score final (peut être négatif)
	 */
	public double calculerScore(ArrayList<Integer> indicesReponses, double baremeJuste, double baremeFaux) {
		double score = 0.0;
		
		if (indicesReponses == null || indicesReponses.isEmpty()) {
			return 0.0; // Pas de réponse = 0 (sera géré comme "Vide" dans le Main si besoin)
		}

		for (int indexChoisi : indicesReponses) {
			// Sécurité : On ignore les index hors limites
			if (indexChoisi >= 0 && indexChoisi < this.choix.size()) {
				
				if (this.bonnesReponses.contains(indexChoisi)) {
					// L'élève a coché une BONNE case -> Bonus
					score += baremeJuste;
				} else {
					// L'élève a coché une MAUVAISE case -> Malus
					score += baremeFaux; 
				}
			}
		}
		return score;
	}

	/**
	 * Calcule combien vaut cette question au maximum (si on coche tout juste).
	 * Utile pour ramener la note sur 20 à la fin.
	 */
	public double getScoreMaxPossible(double baremeJuste) {
		// Si la question a 3 bonnes réponses et que chaque juste vaut +2, la question vaut 6.
		return this.bonnesReponses.size() * baremeJuste;
	}

	// --- MÉTHODES DE BASE ---
	
	public void ajouterChoix(String unChoix) {
		this.choix.add(unChoix);
	}
	
	public void ajouterBonneReponse(int index) {
		if (!this.bonnesReponses.contains(index)) {
			this.bonnesReponses.add(index);
		}
	}
	
	// Getters and Setters
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getEnonce() { return enonce; }
	public void setEnonce(String enonce) { this.enonce = enonce; }
	public ArrayList<String> getChoix() { return choix; }
	public void setChoix(ArrayList<String> choix) { this.choix = choix; }
	public ArrayList<Integer> getBonnesReponses() { return bonnesReponses; }
	public void setBonnesReponses(ArrayList<Integer> bonnesReponses) { this.bonnesReponses = bonnesReponses; }
	public String getMedia() { return media; }
	public void setMedia(String media) { this.media = media; }
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", enonce=" + enonce + "]";
	}
}