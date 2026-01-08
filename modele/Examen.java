package modele;

import java.util.ArrayList;

public class Examen {
	
	// Attributs
	private int id;
	private String titre;
	private String filiere;
	private String niveau;
	private Professeur professeur;
	private ArrayList<Question> questions;
	
	private double pointSiJuste; // si juste ch7al ghadi yzidlk
	private double pointSiFaux;  // si faux ch7al ghadi yn9aslk
	private double pointSiVide;  // ida majawbtichi cheno ghayw9a3
	
	
	// Constructeurs de creation
	public Examen(String titre, String filiere, String niveau, Professeur professeur) {
		this.titre = titre;
		this.filiere = filiere;
		this.niveau = niveau;
		this.professeur = professeur;
		this.questions = new ArrayList<>();
		
		this.pointSiJuste = 1.0;
		this.pointSiFaux = 0.0;
		this.pointSiVide = 0.0;
	}
	
	public Examen(int id, String titre, String filiere, String niveau, Professeur professeur, double pointSiJuste, double pointSiFaux, double pointSiVide) {
		this.id = id;
		this.titre = titre;
		this.filiere = filiere;
		this.niveau = niveau;
		this.professeur = professeur;
		this.questions = new ArrayList<>();
		
		this.pointSiJuste = pointSiJuste;
		this.pointSiFaux = pointSiFaux;
		this.pointSiVide = pointSiVide;
	}
	
	// methodes
		// add questions to th exam
	public void ajouterQuestion(Question q) {
		this.questions.add(q);
	}
	
		// changer le bareme
	public void setBareme(double juste, double faux, double vide) {
		this.pointSiJuste = juste;
		this.pointSiFaux = faux;
		this.pointSiVide = vide;
	}
	
		// Note maximale possible
	public double getNoteTotale() {
		return this.questions.size() * this.pointSiJuste;
	}
	
	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public String getNiveau() {
		return niveau;
	}

	public void setNiveau(String niveau) {
		this.niveau = niveau;
	}

	public Professeur getProfesseur() {
		return professeur;
	}

	public void setProfesseur(Professeur professeur) {
		this.professeur = professeur;
	}

	public ArrayList<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
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
	
	
	@Override
	public String toString() {
		return "Examen [id=" + id + ", titre=" + titre + 
			   ", cible=" + niveau + " " + filiere + 
			   ", Barème=( Juste +" + pointSiJuste + " / Faux " + pointSiFaux + " / Vide " + pointSiVide + ")]";
	}
	
	
	
	
}
