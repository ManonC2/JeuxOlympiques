package org.resources.Repositories;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.CategorieEpreuve;


public class CategorieEpreuveRepository {
	
	Connection connection = DBManager.getInstance().getConnection();
	
	public void add(CategorieEpreuve categorieEpreuve) {
	    String insertQuery = "insert into CategorieEpreuve (nom) VALUES ( '" + categorieEpreuve.getNom() + "')";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	public List<CategorieEpreuve> findAll() {
		
		List<CategorieEpreuve> liste = new ArrayList<CategorieEpreuve>();
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from CategorieEpreuve");
			
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String nom = rs.getString("nom");
	
				
				liste.add(new CategorieEpreuve(id, nom));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
		
	}
	
	public CategorieEpreuve findById(int id) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from CategorieEpreuve where id = " + id);
			
			while(rs.next()) {
				String nom = rs.getString("nom");
				return new CategorieEpreuve(id, nom);
			}
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
