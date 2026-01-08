package modele;

public class Resultat {
	private int id;
	private String nomEtudiant; // On stocke le nom direct pour l'affichage
	private double note;
	private String date;

	public Resultat(int id, String nomEtudiant, double note, String date) {
		this.id = id;
		this.nomEtudiant = nomEtudiant;
		this.note = note;
		this.date = date;
	}

	public String toString() {
		return "📄 Étudiant : " + nomEtudiant + " | Note : " + String.format("%.2f", note) + "/20 (" + date + ")";
	}

	// Getters
	public String getNomEtudiant() { return nomEtudiant; }
	public double getNote() { return note; }
}