package org.resources.Repositories;
import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.assets.DBManager;
import org.resources.Models.RoleUtilisateur;

import jakarta.ejb.Asynchronous;
import jakarta.ejb.Stateless;

@Stateless
public class RoleUtilisateurRepository {
	Connection connection = DBManager.getInstance().getConnection();
	
	@Asynchronous
	public void add(RoleUtilisateur roleUtilisateur) {
	    String insertQuery = "insert into RoleUtilisateur (nom) VALUES ( '" + roleUtilisateur.getNom() + "')";
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	@Asynchronous
	public void update(RoleUtilisateur roleUtilisateur) {
	    String insertQuery = "update RoleUtilisateur set nom = '" + roleUtilisateur.getNom() + "' where id = " + roleUtilisateur.getId();
	    try {
	    	connection.createStatement().execute(insertQuery);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void delete(int id) {
		String insertQuery = "delete from RoleUtilisateur where id = " + id;
		try {
	    	connection.createStatement().execute(insertQuery);	    	
	    	UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
	    	utilisateurRepository.deleteFromRole(id);
	    }
	    catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Asynchronous
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
	
	@Asynchronous
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
