package modele;

public class Professeur {
	
	// Attributs
	private int id;
	private String nomComplet;
	private String specialite;
	
	
	// Constructeurs
	// hada ghadi ycreati lina l'object les atts a5rin ghadi nzidohom b Setters
	public Professeur(String nomComplet, String specialite) {
		this.nomComplet = nomComplet;
		this.specialite = specialite;
	}
	
	// constructeurs complet (hada ki3awnk bach tvisualiser l'object dyalk mn DataBase)
	public Professeur(int id, String nomComplet, String specialite) {
		this.id = id;
		this.nomComplet = nomComplet;
		this.specialite = specialite;
	}

	
	
	
	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomComplet() {
		return nomComplet;
	}

	public void setNomComplet(String nomComplet) {
		this.nomComplet = nomComplet;
	}

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}
	
	
	@Override
	public String toString() {
		return "Professeur [id=" + id + ", nom complet=" + nomComplet + ", specialite=" + specialite + "]";
	}
	
	
}
