package org.resources.Repositories;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.RoleUtilisateur;
import org.resources.Models.Site;

public class SiteRepository {
Connection connection = DBManager.getInstance().getConnection();
	
	public void add(Site site) {
	    String insertQuery = "insert into Site (nom, ville, categorie_id) VALUES ( '" + site.getNom() + "', '" + site.getVille() + "', " + site.getCategorieSite().getId() + ")";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public List<Site> findAll() {
		
		List<Site> liste = new ArrayList<Site>();
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from Site");
			
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String nom = rs.getString("nom");
				String ville = rs.getString("ville");
				int categorieSiteId = Integer.parseInt(rs.getString("categorie_id"));
				
				CategorieSiteRepository categorieSiteRepo = new CategorieSiteRepository();
				
				liste.add(new Site(id, nom, ville, categorieSiteRepo.findById(categorieSiteId)));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
		
	}
	
	public Site findById(int id) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Site where id = " + id);
			
			while(rs.next()) {
				String nom = rs.getString("nom");
				String ville = rs.getString("ville");
				int categorieSiteId = Integer.parseInt(rs.getString("categorie_id"));
				
				CategorieSiteRepository categorieSiteRepo = new CategorieSiteRepository();
				
				return new Site(id, nom, ville, categorieSiteRepo.findById(categorieSiteId));
			}
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
