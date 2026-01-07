package modele;

import java.util.ArrayList;

public class Question {
	// Attributs
	private int id;
	private String enonce;
	private ArrayList<String> choix;
	private ArrayList<Integer> bonnesReponses; //list des reponses correcte
	
	private double pointSiJuste; // si juste ch7al ghadi yzidlk
	private double pointSiFaux;  // si faux ch7al ghadi yn9aslk
	private double pointSiVide;  // ida majawbtichi cheno ghayw9a3
	
	private String media; // Chemin vers image/video
	
	// Constructeurs de creation
	public Question(String enonce) {
		this.enonce = enonce;
		this.choix = new ArrayList<>(); 
		this.bonnesReponses = new ArrayList<>();
		
		// les notes par defaut
		this.pointSiJuste = 1.0;
		this.pointSiFaux = 0.0;
		this.pointSiVide = 0.0;
	}
	
	// Constructeur complet
	public Question(int id, String enonce, double pointSiJuste, double pointSiFaux, double pointSiVide, String media) {
		this.id = id;
		this.enonce = enonce;
		this.pointSiJuste = pointSiJuste;
		this.pointSiFaux = pointSiFaux;
		this.pointSiVide = pointSiVide;
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

	public double getPointSiJuste() {
		return pointSiJuste;
	}

	public void setPointSiJuste(double pointSiJuste) {
		this.pointSiJuste = pointSiJuste;
	}

	public double getPointSiFaux() {
		return pointSiFaux;
	}

	public void setPointSiFaux(double pointSiFaux) {
		this.pointSiFaux = pointSiFaux;
	}

	public double getPointSiVide() {
		return pointSiVide;
	}

	public void setPointSiVide(double pointSiVide) {
		this.pointSiVide = pointSiVide;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}
	
	
	
	@Override
	public String toString() {
		return "Question: " + enonce + 
			   " [Barème: Juste=" + pointSiJuste + ", Faux=" + pointSiFaux + ", Vide=" + pointSiVide + "]" +
			   "\nChoix=" + choix + 
			   "\nCorrects=" + bonnesReponses;
	}
	
	
}
