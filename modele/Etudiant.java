package modele;

public class Etudiant {
	
	// Attributs
	private int id; // ID de la base de données
	private String nomComplet;
	private String filiere;
	private String niveau;
	
	// Attributs optionnels
	private String matricule;
	private String email;
	
	// Constructors
	
	// minimum constructor
	public Etudiant(String nomComplet, String filiere, String niveau) {
		this.nomComplet = nomComplet;
        this.filiere = filiere;
        this.niveau = niveau;
	}
	
	//Complete constructor
	public Etudiant(int id, String nomComplet, String filiere, String niveau, String matricule, String email) {
		super();
		this.id = id;
		this.nomComplet = nomComplet;
		this.filiere = filiere;
		this.niveau = niveau;
		this.matricule = matricule;
		this.email = email;
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

	

	
	@Override
    public String toString() {
        return "Etudiant [id=" + id + ", nom=" + nomComplet + ", matricule=" + (matricule != null ? matricule : "N/A") + "]";
    }
	
}
