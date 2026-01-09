package modele;

public class Etudiant {
	
	// Attributs obligatoires
	private int id; 
	private String nomComplet;
	private String filiere;
	private String niveau;
	private String matricule;
	private String email;
	
	// Attribut pour l'authentification
	private String password;

	// 1. Constructeur pour la création (quand l'ID est auto-généré par la DB)
	public Etudiant(String nomComplet, String filiere, String niveau, String matricule, String email, String password) {
		this.nomComplet = nomComplet;
		this.filiere = filiere;
		this.niveau = niveau;
		this.matricule = matricule;
		this.email = email;
		this.password = password;
	}

	// 2. Constructeur complet (utile pour récupérer les données depuis la DB avec l'ID)
	public Etudiant(int id, String nomComplet, String filiere, String niveau, String matricule, String email, String password) {
		this.id = id;
		this.nomComplet = nomComplet;
		this.filiere = filiere;
		this.niveau = niveau;
		this.matricule = matricule;
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

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
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
		// On n'inclut PAS le password ici pour des raisons de sécurité (Logs, Debugging)
		return "Etudiant [id=" + id + 
			   ", nom=" + nomComplet + 
			   ", filiere=" + filiere + 
			   ", niveau=" + niveau + 
			   ", matricule=" + matricule + 
			   ", email=" + email + "]";
	}
}