package org.resources.Repositories;

import org.resources.Models.*;

public class EpreuveRepository {
	public Epreuve addEpreuve(String nom, Discipline discipline, CategorieEpreuve categorieEpreuve) {
		
		Epreuve epreuve = new Epreuve(nom, discipline, categorieEpreuve);
		
		// TODO add à la BDD
		
		return epreuve;
	}
	
	public Epreuve findEpreuveById(int id) {
		Epreuve epreuve = new Epreuve(null, null, null);
		
		//TODO rédiger méthode 
		
		return epreuve;
	}
	
	public Epreuve updateEpreuve(Epreuve epreuve) {
		//TODO rédiger méthode 
		
		return epreuve;
	}
	
	public void deleteEpreuve(int id) {
		Epreuve epreuve = findEpreuveById(id);
		
		//TODO rédiger méthode
	}
}
