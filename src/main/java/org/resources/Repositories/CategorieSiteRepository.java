package org.resources.Repositories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.CategorieSite;

public class CategorieSiteRepository {
	Connection connection = DBManager.getInstance().getConnection();
	
	
	public void add(CategorieSite categorieSite) {
	    String insertQuery = "insert into CategorieSite (nom) VALUES ( '" + categorieSite.getNom() + "')";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void update(CategorieSite categorieSite) {
	    String insertQuery = "update CategorieSite set nom = '" + categorieSite.getNom() + "' where id = " + categorieSite.getId();
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void delete(int id) {
		String insertQuery = "delete from CategorieSite where id = " + id;
		try {
	    	connection.createStatement().execute(insertQuery);	
	    	SiteRepository siteRepository = new SiteRepository();
	    	siteRepository.deleteFromCategorieSite(id);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public List<CategorieSite> findAll() {
		
		List<CategorieSite> liste = new ArrayList<CategorieSite>();
		
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from CategorieSite");
			
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String nom = rs.getString("nom");
				liste.add(new CategorieSite(id, nom));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
		
	}
	
	public CategorieSite findById(int id) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from CategorieSite where id = " + id);
			
			while(rs.next()) {
				String nom = rs.getString("nom");
				return new CategorieSite(id, nom);
			}
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


}

