package modele;

public class Professeur {
	
	// Attributs obligatoires
	private int id;
	private String nomComplet;
	private String specialite;
	private String email;
	
	// Attribut pour l'authentification
	private String password;
	
	// 1. Constructeur pour la création (sans ID)
	public Professeur(String nomComplet, String specialite, String email, String password) {
		this.nomComplet = nomComplet;
		this.specialite = specialite;
		this.email = email;
		this.password = password;
	}
	
	// 2. Constructeur complet (pour la récupération depuis la DB)
	public Professeur(int id, String nomComplet, String specialite, String email, String password) {
		this.id = id;
		this.nomComplet = nomComplet;
		this.specialite = specialite;
		this.email = email;
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		// Le password est exclu ici pour la sécurité
		return "Professeur [id=" + id + 
			   ", nom complet=" + nomComplet + 
			   ", specialite=" + specialite + 
			   ", email=" + email + "]";
	}
}