package modele;

public class Resultat {
	private int id;
	private int idExamen;
	private int idEtudiant;
	private String nomEtudiant; // On va le récupérer via une jointure SQL
	private double note;

	public Resultat(int id, int idExamen, int idEtudiant, String nomEtudiant, double note) {
		this.id = id;
		this.idExamen = idExamen;
		this.idEtudiant = idEtudiant;
		this.nomEtudiant = nomEtudiant;
		this.note = note;
	}

	// Getters  (no setters 7itach mabghinchi nbedlo no9ta apres exams, safi saalat)
	public int getId() {
		return id;
	}
	
	public int getIdExamen() {
		return idExamen;
	}
	
	public int getIdEtudiant() {
		return idEtudiant; 
	}
	
	public String getNomEtudiant() {
		return nomEtudiant; 
	}
	
	public double getNote() {
		return note;
	}

	@Override
	public String toString() {
		return "Étudiant : " + nomEtudiant + " | Note : " + note + "/20";
	}
}