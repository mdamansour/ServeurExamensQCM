package modele;

public class Resultat {
	private int id;
	private int idExamen;
	private int idEtudiant;
	private double note;
	private String datePassage;

	public Resultat(int id, int idExamen, int idEtudiant, double note, String datePassage) {
		this.id = id;
		this.idExamen = idExamen;
		this.idEtudiant = idEtudiant;
		this.note = note;
		this.datePassage = datePassage;
	}

	public int getId() {
		return id;
	}
	
	public int getIdExamen() {
		return idExamen;
	}
	
	public int getIdEtudiant() {
		return idEtudiant; 
	}
	
	public double getNote() {
		return note;
	}

	public String getDatePassage() {
		return datePassage;
	}

	@Override
	public String toString() {
		return "Etudiant ID : " + idEtudiant + " | Note : " + note + "/20";
	}
}