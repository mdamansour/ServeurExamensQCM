package modele;

import java.util.ArrayList;

public class Question {
	// Attributs
	private int id;
	private String enonce;
	private ArrayList<String> choix;
	private ArrayList<Integer> bonnesReponses; // Liste des index corrects (0, 1, 3...) (ze3ma l IDs dyal les choix lis7a7in)
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
	 * Modified to include 'baremeVide'
	 */
	public double calculerScore(ArrayList<Integer> idsReponses, double baremeJuste, double baremeFaux, double baremeVide) {
	    double score = 0.0;
	    
	    // FIX: Return the specific "empty" score instead of 0.0
	    if (idsReponses == null || idsReponses.isEmpty()) {
	        return baremeVide; 
	    }

	    for (int idChoisi : idsReponses) {
	        if (this.bonnesReponses.contains(idChoisi)) {
	            score += baremeJuste;
	        } else {
	            score += baremeFaux; 
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
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEnonce() {
		return enonce;
	}

	public void setEnonce(String enonce) {
		this.enonce = enonce;
	}

	public ArrayList<String> getChoix() {
		return choix;
	}

	public void setChoix(ArrayList<String> choix) {
		this.choix = choix;
	}

	public ArrayList<Integer> getBonnesReponses() {
		return bonnesReponses;
	}

	public void setBonnesReponses(ArrayList<Integer> bonnesReponses) {
		this.bonnesReponses = bonnesReponses;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", enonce=" + enonce + "]";
	}


}