package org.resources.Repositories;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.Utilisateur;

public class UtilisateurRepository {
	Connection connection = DBManager.getInstance().getConnection();
	
	public void add(Utilisateur utilisateur) {
	    String insertQuery = "insert into Utilisateur (email, password, role_id) VALUES ( '" + utilisateur.getEmail() + "', '" + utilisateur.getPassword() + "', " + utilisateur.getRole().getId() + ")";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void update(Utilisateur utilisateur) {
	    String insertQuery = "update Utilisateur set email = '" + utilisateur.getEmail() + "', password = " + utilisateur.getPassword() + ", role_id = "+ utilisateur.getRole().getId() + " where id = " + utilisateur.getId();
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public void delete(int id) {
		String insertQuery = "delete from Utilisateur where id = " + id;
		try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void deleteFromRole(int role_id) {
		String insertQuery = "delete from Utilisateur where role_id = " + role_id;
		try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public List<Utilisateur> findAll() {
		
		List<Utilisateur> liste = new ArrayList<Utilisateur>();
		
		Statement statement;
		try {
			statement = connection.createStatement();
			
			ResultSet rs = statement.executeQuery("select * from Utilisateur");
			
			while(rs.next()) {
				int id = Integer.parseInt(rs.getString("id"));
				String email = rs.getString("email");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				int roleUtilisateurId = Integer.parseInt(rs.getString("role_id"));
				RoleUtilisateurRepository roleUtilisateurRepo = new RoleUtilisateurRepository();
				liste.add(new Utilisateur(id, email, nom, prenom, roleUtilisateurRepo.findById(roleUtilisateurId)));
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return liste;
		
	}
	
	public Utilisateur findById(int id) {
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from Utilisateur where id = " + id);
			
			while(rs.next()) {
				String email = rs.getString("email");
				String nom = rs.getString("nom");
				String prenom = rs.getString("prenom");
				int roleUtilisateurId = Integer.parseInt(rs.getString("role_id"));
				RoleUtilisateurRepository roleUtilisateurRepo = new RoleUtilisateurRepository();
				return new Utilisateur(id, email, nom, prenom, roleUtilisateurRepo.findById(roleUtilisateurId));
			}
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
