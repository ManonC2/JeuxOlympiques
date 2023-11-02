package org.resources.Models;

public class RoleUtilisateur {

	private int id;
	
	private String nom;
	
	public RoleUtilisateur(String nom) {
		super();
		this.nom = nom;
	}
	
	public RoleUtilisateur(int id, String nom) {
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
