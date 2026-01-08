package modele;

import java.util.ArrayList;

public class Question {
	// Attributs
	private int id;
	private String enonce;
	private ArrayList<String> choix;
	private ArrayList<Integer> bonnesReponses; //list des reponses correcte

	private String media; // Chemin vers image/video
	
	// Constructeurs de creation
	public Question(String enonce) {
		this.enonce = enonce;
		this.choix = new ArrayList<>(); 
		this.bonnesReponses = new ArrayList<>();
	}
	
	// Constructeur complet
	public Question(int id, String enonce, String media) {
		this.id = id;
		this.enonce = enonce;
		this.media = media;
		this.choix = new ArrayList<>();
		this.bonnesReponses = new ArrayList<>();
	}
	
	
	
	
	// getters and setters
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
		return "Question [id=" + id + ", enonce=" + enonce + ", choix=" + choix + "]";
	}
	
	
}
