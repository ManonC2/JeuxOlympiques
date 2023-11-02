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
import org.resources.Models.CategorieEpreuve;
import org.resources.Models.RoleUtilisateur;

public class RoleUtilisateurRepository {
Connection connection = DBManager.getInstance().getConnection();
	
	public void add(RoleUtilisateur roleUtilisateur) {
	    String insertQuery = "insert into RoleUtilisateur (nom) VALUES ( '" + roleUtilisateur.getNom() + "')";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public List<RoleUtilisateur> findAll() {
		
		List<RoleUtilisateur> liste = new ArrayList<RoleUtilisateur>();
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from RoleUtilisateur");
			
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String nom = rs.getString("nom");
	
				
				liste.add(new RoleUtilisateur(id, nom));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
		
	}
	
	public RoleUtilisateur findById(int id) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from RoleUtilisateur where id = " + id);
			
			while(rs.next()) {
				String nom = rs.getString("nom");
				return new RoleUtilisateur(id, nom);
			}
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
