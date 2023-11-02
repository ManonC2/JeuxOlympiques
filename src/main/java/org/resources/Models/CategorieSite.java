package org.resources.Models;

public class CategorieSite {
	private int id;

	private String nom;
	
	public CategorieSite(String nom) {
		super();
		this.nom = nom;
	} 
	
	
	
	public CategorieSite(int id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
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
}
