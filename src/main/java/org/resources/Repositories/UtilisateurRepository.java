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
import org.resources.Models.Utilisateur;

public class UtilisateurRepository {
Connection connection = DBManager.getInstance().getConnection();

	public void add(Utilisateur utilisateur) {
	    String insertQuery = "insert into Utilisateur (login, password, role_id) VALUES ( '" + utilisateur.getLogin() + "', '" + utilisateur.getPassword() + "', " + utilisateur.getRole().getId() + ")";
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
				String nom = rs.getString("login");
				String ville = rs.getString("password");
				int roleUtilisateurId = Integer.parseInt(rs.getString("role_id"));
				
				RoleUtilisateurRepository roleUtilisateurRepo = new RoleUtilisateurRepository();
				
				liste.add(new Utilisateur(id, nom, ville, roleUtilisateurRepo.findById(roleUtilisateurId)));
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
				String nom = rs.getString("login");
				String ville = rs.getString("password	");
				int roleUtilisateurId = Integer.parseInt(rs.getString("role_id"));
				
				RoleUtilisateurRepository roleUtilisateurRepo = new RoleUtilisateurRepository();
				
				return new Utilisateur(id, nom, ville, roleUtilisateurRepo.findById(roleUtilisateurId));
			}
		}
		catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
