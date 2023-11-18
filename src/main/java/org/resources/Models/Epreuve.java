package org.resources.Models;

public class Epreuve {
	
	private int id;
	
	private String nom;
	
	private Discipline discipline;
	
	private CategorieEpreuve categorieEpreuve;
	
	boolean collectif;

	public Epreuve(String nom, Discipline discipline, CategorieEpreuve categorieEpreuve, boolean collectif) {
		super();
		this.nom = nom;
		this.discipline = discipline;
		this.categorieEpreuve = categorieEpreuve;
		this.collectif = collectif;
	}

	public Epreuve(int id, String nom, Discipline discipline, CategorieEpreuve categorieEpreuve, boolean collectif) {
		super();
		this.id = id;
		this.nom = nom;
		this.discipline = discipline;
		this.categorieEpreuve = categorieEpreuve;
		this.collectif = collectif;
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
	
	public boolean getCollectif() {
		return collectif;
	}

	public void setCollectif(boolean collectif) {
		this.collectif = collectif;
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
