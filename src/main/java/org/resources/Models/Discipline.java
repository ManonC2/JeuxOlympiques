package org.resources.Models;

public class Discipline {
	private int id ;
	
	
	private String nom;
	
	private boolean paralympique;

	public Discipline(String nom, boolean paralympique) {
		super();
		this.nom = nom;
		this.paralympique = paralympique;
	}

	
	
	public Discipline(int id, String nom, boolean paralympique) {
		super();
		this.id = id;
		this.nom = nom;
		this.paralympique = paralympique;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public boolean isParalympique() {
		return paralympique;
	}

	public void setParalympique(boolean paralympique) {
		this.paralympique = paralympique;
	}
}
