package org.resources.Models;

public class Site {
	private int id;
	
	private String nom;
	
	private String ville;
	
	private CategorieSite categorieSite;

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

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public CategorieSite getCategorieSite() {
		return categorieSite;
	}

	public void setCategorieSite(CategorieSite categorieSite) {
		this.categorieSite = categorieSite;
	}
}
