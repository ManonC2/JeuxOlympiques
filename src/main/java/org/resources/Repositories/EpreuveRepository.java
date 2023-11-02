package org.resources.Repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.*;

public class EpreuveRepository {
	

	Connection connection = DBManager.getInstance().getConnection();
	
	public void add(Epreuve epreuve) {
	    String insertQuery = "insert into Epreuve (nom, discipline_id, categorieEpreuve_id) VALUES ( '" + epreuve.getNom() + "'," + epreuve.getDiscipline().getId() + ", "+ epreuve.getCategorieEpreuve().getId() +")";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public Epreuve findById(int id) {
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from Epreuve where id = " + id);
			
			while(rs.next()) {
				String nom = rs.getString("nom");
				int disciplineId = Integer.parseInt(rs.getString("discipline_id"));
				int categorieEpreuveId = Integer.parseInt(rs.getString("categorieEpreuve_id"));
				
				DisciplineRepository disciplineRepo = new DisciplineRepository();
				CategorieEpreuveRepository categorieEpreuveRepo = new CategorieEpreuveRepository();
				
				return new Epreuve(id, nom, disciplineRepo.findById(disciplineId), categorieEpreuveRepo.findById(categorieEpreuveId));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Epreuve updateEpreuve(Epreuve epreuve) {
		//TODO rédiger méthode 
		
		return epreuve;
	}
	
	public void deleteEpreuve(int id) {
		Epreuve epreuve = findById(id);
		
		//TODO rédiger méthode
	}
}
