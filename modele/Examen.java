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
}
