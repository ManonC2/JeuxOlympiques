package org.resources.Models;

public class Epreuve {
	
	private int id;
	
	private String nom;
	
	private Discipline discipline;
	
	private CategorieEpreuve categorieEpreuve;

	public Epreuve(String nom, Discipline discipline, CategorieEpreuve categorieEpreuve) {
		super();
		this.nom = nom;
		this.discipline = discipline;
		this.categorieEpreuve = categorieEpreuve;
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

	public Discipline getDiscipline() {
		return discipline;
	}

	public void setDiscipline(Discipline discipline) {
		this.discipline = discipline;
	}

	public CategorieEpreuve getCategorieEpreuve() {
		return categorieEpreuve;
	}

	public void setCategorieEpreuve(CategorieEpreuve categorieEpreuve) {
		this.categorieEpreuve = categorieEpreuve;
	}
}
