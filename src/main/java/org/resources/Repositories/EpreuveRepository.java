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
	
	public void update(Epreuve epreuve) {
	    String insertQuery = "update Epreuve set nom = '" + epreuve.getNom() + "', discipline_id = " + epreuve.getDiscipline().getId() + ", categorieEpreuve_id = "+ epreuve.getCategorieEpreuve().getId() + " where id = " + epreuve.getId();
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void delete(int id) {
		String insertQuery = "delete from Epreuve where id = " + id;
		try {
	    	connection.createStatement().execute(insertQuery);
	    	SessionRepository sessionRepository = new SessionRepository();
	    	sessionRepository.deleteFromEpreuve(id);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void deleteFromCategorieEpreuve(int categorieEpreuve_id) {
		String insertQuery = "delete from Epreuve where categorieEpreuve_id = " + categorieEpreuve_id;
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Epreuve where categorieEpreuve_id = " + categorieEpreuve_id);
			SessionRepository sessionRepository = new SessionRepository();
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				sessionRepository.deleteFromEpreuve(id);
			}
			
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void deleteFromDiscipline(int discipline_id) {
		String insertQuery = "delete from Epreuve where discipline_id = " + discipline_id;
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Epreuve where discipline_id = " + discipline_id);
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				SessionRepository sessionRepository = new SessionRepository();
				sessionRepository.deleteFromEpreuve(id);
			}
			
	    	connection.createStatement().execute(insertQuery);
		}
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	public List<Epreuve> findAll() {
		
		List<Epreuve> liste = new ArrayList<Epreuve>();
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from Epreuve ORDER BY discipline_id");
			
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String nom = rs.getString("nom");
				int disciplineId = Integer.parseInt(rs.getString("discipline_id"));
				int categorieEpreuveId = Integer.parseInt(rs.getString("categorieEpreuve_id"));
				
				DisciplineRepository disciplineRepo = new DisciplineRepository();
				CategorieEpreuveRepository categorieEpreuveRepo = new CategorieEpreuveRepository();
				
				liste.add(new Epreuve(id, nom, disciplineRepo.findById(disciplineId), categorieEpreuveRepo.findById(categorieEpreuveId)));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
		
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
}
